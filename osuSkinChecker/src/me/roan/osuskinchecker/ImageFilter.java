package me.roan.osuskinchecker;

import java.io.File;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import me.roan.osuskinchecker.ini.Setting;
import me.roan.osuskinchecker.ini.SkinIni;
import me.roan.osuskinchecker.ini.Version;

/**
 * Represent a filter that matches image files.
 * @author Roan
 * @see Filter
 * @see ImageMeta
 */
public class ImageFilter extends Filter<ImageMeta>{
	/**
	 * Whether or not multiple versions of the
	 * image described by this filter
	 * object can exist, where extra files are named by adding
	 * <code>n</code> to the name. Where <code>n</code> is an
	 * integer <code>&gt;= 0</code>.
	 */
	protected boolean animatedNoDash = false;
	/**
	 * Whether or not the SD/HD distinction
	 * does not apply to the image matched
	 * by this filter.
	 */
	protected boolean single = false;
	/**
	 * Last version the image described by this filter
	 * was used in. If the <tt>skin.ini</tt> version is
	 * higher than this, this file is a legacy file.
	 */
	protected Version maxVersion = null;
	/**
	 * The skin.ini setting the custom path is tied to.
	 */
	protected String customProperty = null;
	/**
	 * The default resource location for the custom path.
	 */
	protected String customDefault = null;
	/**
	 * Specifies the custom file location or the default
	 * location if there is no custom one.
	 */
	protected String[] customPath;
	/**
	 * The mania key count the custom path setting is for.
	 */
	protected int customKeyCount = -1;
	/**
	 * Override filter, if the file described by the other
	 * filter exists, the file described by this filter
	 * is not required.
	 */
	protected Filter<?> override = null;
	/**
	 * Name of the file that overrides the file
	 * matches by this filter.
	 */
	protected String overrideName = null;
	/**
	 * Specifies the override mode. If this flag is
	 * <code>true</code> then the override file has
	 * to be present for this filter to activate. If
	 * the flag is <code>false</code>
	 */
	protected boolean overrideMode = false;
	
	/**
	 * Constructs a new ImageFilter with the given arguments.
	 * @param args The filter construction arguments.
	 */
	public ImageFilter(String[] args){
		super(args);
		int c = 0;
		while(c < args.length){
			switch(args[c]){
			case "M":
				animatedNoDash = true;
				break;
			case "S":
				single = true;
				break;
			case "L":
				maxVersion = Version.fromString(args[++c]);
				break;
			case "P":
				customKeyCount = Integer.parseInt(args[++c]);
				//$FALL-THROUGH$
			case "C":
				customProperty = args[++c];
				customDefault = args[++c];
				break;
			case "O":
				overrideMode = Boolean.parseBoolean(args[++c]);
				overrideName = args[++c];
				break;
			}
			c++;
		}
	}
	
	@Override
	public void reset(SkinIni ini){
		super.reset(ini);
		if(customProperty != null){
			Setting<?> pathSetting = ini.find(customProperty, customKeyCount);
			if(pathSetting != null && pathSetting.isEnabled()){
				//path separator is currently hard coded in the skin.ini format
				customPath = pathSetting.getValue().toString().split("/");
			}else{
				customPath = new String[]{customDefault};
			}
		}
	}
	
	/**
	 * Checks if any matched file in this filter
	 * is a HD image.
	 * @return Whether or not this filter matched
	 *         a HD image.
	 */
	public boolean hasHD(){
		for(ImageMeta meta : matches){
			if(meta.isHD()){
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if any matched file in this filter
	 * is a SD image.
	 * @return Whether or not this filter matched
	 *         a SD image.
	 */
	public boolean hasSD(){
		for(ImageMeta meta : matches){
			if(meta.isSD()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if any matched file in this filter
	 * is an animated image.
	 * @return Whether or not this filter matched
	 *         an animated image.
	 */
	public boolean isAnimated(){
		for(ImageMeta meta : matches){
			if(meta.isAnimated()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Constructs a string stating the frames that are animated
	 * from all the files that matched this filter.
	 * @return A string stated all the animated frames.
	 */
	public String getFrameString(){
		int[] nums = matches.stream().mapToInt(ImageMeta::getSequenceNumber).filter(i->i >= 0).distinct().sorted().toArray();
		StringJoiner buffer = new StringJoiner(", ");
		int start = nums[0];
		int last = nums[0];
		for(int i = 1; i < nums.length; i++){
			if(nums[i] != last + 1){
				buffer.add(start + "-" + last);
				start = nums[i];
			}
			last = nums[i];
		}
		if(start == 0){
			buffer.add(last == 0 ? "1 frame" : ((last + 1) + " frames"));
			return buffer.toString();
		}else{
			buffer.add(start + "-" + last);
			return "frames " + buffer.toString();
		}
	}
	
	/**
	 * Checks if all SD images matched by
	 * this filter are empty.
	 * @return True if all matched SD images
	 *         are empty, false otherwise. If
	 *         no SD images were matched false
	 *         is returned.
	 */
	public boolean allEmptySD(){
		boolean none = true;
		for(ImageMeta meta : matches){
			if(meta.isSD()){
				none = false;
				if(!meta.isEmpty()){
					return false;
				}
			}
		}
		return !none;
	}
	
	/**
	 * Checks whether or not the image matched by this filter
	 * is no longer supported in the given current version.
	 * @param current The version of the skin being checked.
	 * @return True if the images matched by this filter are
	 *         legacy files with respect to the skin.
	 */
	public boolean isLegacy(Version current){
		return maxVersion != null && !maxVersion.isAfterOrSame(current);
	}
	
	/**
	 * Whether or not this file is currently being overridden
	 * by a different file.
	 * @return True if this file is currently being overridden.
	 */
	public boolean isOverriden(){
		return (overrideName == null) ? false : (overrideMode ^ override.hasMatch());
	}

	@Override
	protected ImageMeta matches(File file, String fn, Deque<String> path){
		boolean hd = false;
		
		//name without base
		String extra;
		
		//verify custom path and name
		if(customProperty != null){
			if(customPath.length > 1 || !path.isEmpty()){
				if(customPath.length - 1 == path.size()){
					Iterator<String> iter = path.iterator();
					for(int i = customPath.length - 2; i >= 0; i--){
						if(!iter.next().equalsIgnoreCase(customPath[i])){
							return null;
						}
					}
					if(fn.startsWith((customPath[customPath.length - 1] + name).toLowerCase(Locale.ROOT))){
						extra = fn.substring(customPath[customPath.length - 1].length() + name.length());
					}else{
						return null;
					}
				}else{
					return null;
				}
			}else{
				if(fn.startsWith((customDefault + name).toLowerCase(Locale.ROOT))){
					extra = fn.substring(customDefault.length() + name.length());
				}else{
					return null;
				}
			}
		}else{
			//strip file name
			extra = fn.substring(name.length());
		}
		
		//strip @2x
		if(extra.endsWith("@2x")){
			extra = extra.substring(0, extra.length() - 3);
			hd = true;
		}
		
		//if nothing special accept
		if(extra.length() == 0){
			return new ImageMeta(file, hd);
		}else{
			if(animatedDash){
				if(extra.startsWith("-")){
					try{
						return new ImageMeta(file, hd, Integer.parseInt(extra.substring(1)));
					}catch(NumberFormatException e){
						return null;
					}
				}else{
					return null;
				}
			}else if(animatedNoDash){
				try{
					return new ImageMeta(file, hd, Integer.parseInt(extra));
				}catch(NumberFormatException e){
					return null;
				}
			}else{
				return null;
			}
		}
	}
	
	@Override
	public String toString(){
		return customProperty == null ? super.toString() : (customPath[customPath.length - 1] + name);
	}

	@Override
	public Model getModel(List<Filter<?>> filters){
		return new ImageModel(filters);
	}

	@Override
	protected boolean allowNonRoot(){
		return customProperty != null;
	}

	@Override
	protected boolean show(Version version){
		if(!SkinChecker.checkLegacy && isLegacy(version)){
			return false;
		}else if(SkinChecker.showAll){
			return true;
		}else{
			if(isOverriden()){
				return false;
			}
			
			if(SkinChecker.checkHD && !hasHD()){
				return !(SkinChecker.ignoreEmpty && allEmptySD());
			}
			
			if(SkinChecker.checkSD && !hasSD()){
				return !(SkinChecker.ignoreSD && hasHD());
			}
		}
		return false;
	}

	@Override
	protected void link(List<Filter<?>> filters){
		if(overrideName != null){
			for(Filter<?> filter : filters){
				if(filter.name.equals(overrideName)){
					override = filter;
					break;
				}
			}
		}
	}

	@Override
	public List<File> getMatchedFiles(){
		return matches.stream().map(ImageMeta::getFile).collect(Collectors.toList());
	}
}

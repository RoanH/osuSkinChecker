package me.roan.osuskinchecker;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageIO;

/**
 * ImageInfo objects are used to describe an
 * image file and check whether or not a
 * matching image file exists.
 * @author Roan
 * @see Info
 * @see ImageModel
 */
public final class ImageInfo implements Info{
	/**
	 * Whether or not the SD/HD distinction
	 * does not apply to the image described
	 * by this information object
	 */
	private boolean singleImage = false;
	/**
	 * Whether or not the image described by this
	 * information object is a legacy file and not 
	 * used anymore by recent skins.
	 */
	private boolean legacy = false;
	/**
	 * Whether or not multiple versions of the
	 * image described by this information
	 * object can exist, where extra files are named by adding
	 * <code>-n</code> to the name. Where <code>n</code> is an
	 * integer <code>>= 0</code>.
	 */
	protected boolean variableWithDash = false;
	/**
	 * Whether or not multiple versions of the
	 * image described by this information
	 * object can exist, where extra files are named by adding
	 * <code>n</code> to the name. Where <code>n</code> is an
	 * integer <code>>= 0</code>.
	 */
	protected boolean variableWithoutDash = false;
	 /**
	  * Whether or not the image could be located in a 
	  * different folder relative to the base folder
	  */
	private boolean customPath = false;
	/**
	 * Whether or not the image file itself could
	 * be located somewhere else relative to the base folder
	 */
	private boolean customPathPrefix = false;
	/**
	 * If there is a possible custom path for the file
	 * described by this object then this ID specifies
	 * the alternative location to search for a matching
	 * file
	 */
	private int customID = -1;
	/**
	 * Boolean to store whether or not a SD image
	 * exists that matches the criteria specified
	 * by this information object. This variable is
	 * <code>null</code> if no checks have been executed yet.
	 */
	private Boolean hasSD = null;
	/**
	 * Boolean to store whether or not a HD image
	 * exists that matches the criteria specified
	 * by this information object. This variable is
	 * <code>null</code> if no checks have been executed yet.
	 */
	private Boolean hasHD = null;
	/**
	 * Boolean to store whether or not a missing
	 * HD image for a file could be ignored because
	 * its SD variant is empty
	 */
	protected boolean ignored = false;
	/**
	 * Boolean to store whether or not the match
	 * found for this image is animated
	 */
	protected boolean animated = false;
	/**
	 * A list of allowed extensions for the
	 * file described by this information object
	 */
	private String[] extensions;
	/**
	 * The base name of the file described
	 * by this information object
	 */
	protected String name;
	
	/**
	 * Creates an information object
	 * for a file as specified by its
	 * database entry.
	 * @param line The database entry
	 *        specifying the properties
	 *        for this information object
	 */
	public ImageInfo(String line){
		String[] data = line.split(" ");
		if(!data[0].equals("-")){
			char[] args = data[0].toUpperCase(Locale.ROOT).toCharArray();
			for(char option : args){
				switch(option){
				case 'N':
					variableWithDash = true;
					break;
				case 'M':
					variableWithoutDash = true;
					break;
				case 'S':
					singleImage = true;
					break;
				case 'C':
					customPath = true;
					customID = Integer.parseInt(data[1]);
					break;
				case 'P':
					customPathPrefix = true;
					customID = Integer.parseInt(data[1]);
					break;
				case 'L':
					legacy = true;
					break;
				}
			}
		}
		this.extensions = data[1 + (customPath ? 1 : 0)].split(",");
		this.name = data[2 + (customPath ? 1 : 0)];
	}

	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public boolean show(){
		if(SkinChecker.showAll){
			return !legacy || SkinChecker.checkLegacy;
		}else{
			if(legacy && !SkinChecker.checkLegacy){
				return false;
			}else{
				hasHDVersion();
				boolean hd = hasHD;
				return (SkinChecker.checkSD ? !hasSDVersion() : false) || (SkinChecker.checkHD ? ((ignored && SkinChecker.ignoreEmpty) ? false : !hd) : false);
			}
		}
	}
	
	/**
	 * Checks whether or not a SD
	 * version exists of the image
	 * described by this information
	 * object.
	 * @return Whether or not a SD
	 *         version exists.
	 */
	protected boolean hasSDVersion(){
		if(hasSD == null){
			for(String ext : extensions){
				if(checkForFile(SkinChecker.skinFolder, name, false, ext, variableWithDash, variableWithoutDash, customPath, customPathPrefix) != null){
					hasSD = true;
				}
			}
			if(hasSD == null){
				hasSD = false;
			}
		}
		return hasSD;
	}
	
	/**
	 * Checks whether or not a HD
	 * version exists of the image
	 * described by this information
	 * object. An image is considered 
	 * a HD image if it's name ends 
	 * in <code>@2x</code>.
	 * @return Whether or not a HD
	 *         version exists.
	 */
	protected String hasHDVersion(){
		if(singleImage){
			hasHD = true;
			return "N/A";
		}
		if(hasHD == null){
			for(String ext : extensions){
				if(checkForFile(SkinChecker.skinFolder, name, true, ext, variableWithDash, variableWithoutDash, customPath, customPathPrefix) != null){
					hasHD = true;
				}
			}
			if(hasHD == null){
				hasHD = false;
			}
		}
		return hasHD ? "Yes" : "No";
	}
	
	/**
	 * Check to see if a specific image exists 
	 * given a specific set of conditions
	 * @param folder The folder the image is located in
	 * @param name The base name of the image
	 * @param hd Whether or not to check for an HD version of
	 *        the image. The name of the HD variant of an image
	 *        end with <code>@2x</code>.
	 * @param extension The extension of the image
	 * @param variableDash Whether or not multiple versions of the
	 *        image can exist, where extra files are named by adding
	 *        <code>-n</code> to the name. Where <code>n</code> is an
	 *        integer <code>>= 0</code>.
	 * @param variableNoDash Whether or not multiple versions of the
	 *        image can exist, where extra files are named by adding
	 *        <code>n</code> to the name. Where <code>n</code> is an
	 *        integer <code>>= 0</code>.
	 * @param custom Whether or not the image could be located in a 
	 *        different folder relative to the base folder.
	 * @param customPrefix Whether or not the image file itself could
	 *        be located somewhere else relative to the base folder.
	 * @return A file that matches all the criteria or <code>null</code>
	 *         if none were found.
	 */
	private File checkForFile(File folder, String name, boolean hd, String extension, boolean variableDash, boolean variableNoDash, boolean custom, boolean customPrefix){
		File match;
		if(hd){
			if(hasSD == null || hasSD == true){
				File sdver = checkForFile(folder, name, false, extension, variableDash, variableNoDash, custom, customPrefix);
				if(sdver != null && isEmptyImage(sdver)){
					ignored = true;
					return null;
				}
			}
			extension = "@2x." + extension;
		}else{
			extension = "." + extension;
		}
		if(variableDash && (match = new File(folder, name + "-0" + extension)).exists()){
			animated = true;
			return match;
		}
		if(variableNoDash && (match = new File(folder, name + "0" + extension)).exists()){
			animated = true;
			return match;
		}
		if((match = new File(folder, name + extension)).exists()){
			return match;
		}
		if((custom || customPrefix) && this.customID != -1){
			if(customPrefix){
				return checkForFile(SkinChecker.customPathing.get(this.customID), name, hd, extension, variableDash, variableNoDash, false, false);
			}else{
				File f = SkinChecker.customPathing.get(this.customID);
				return f == null ? null : checkForFile(f.getParentFile(), f.getName(), hd, extension.substring(hd ? 4 : 1), variableDash, variableNoDash, false, false);
			}
		}
		return null;
	}
	
	/**
	 * Check if an image is empty.
	 * An Image is considered empty if it's
	 * size is under 4096 bytes and it's dimensions
	 * are 1 by 1.
	 * @param img The image file to check
	 * @return Whether or not the image is empty
	 */
	private static boolean isEmptyImage(File img){
		if(img.length() > 4096){
			return false;
		}
		try {
			BufferedImage image = ImageIO.read(img);
			return image.getHeight() + image.getWidth() == 2;
		} catch (IOException e) {
			return false;
		}
	}
}
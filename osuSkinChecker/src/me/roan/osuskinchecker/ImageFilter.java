package me.roan.osuskinchecker;

import java.io.File;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

import me.roan.osuskinchecker.ini.Setting;
import me.roan.osuskinchecker.ini.SkinIni;
import me.roan.osuskinchecker.ini.Version;

public class ImageFilter extends Filter{
	/**
	 * Whether or not multiple versions of the
	 * image described by this filter
	 * object can exist, where extra files are named by adding
	 * <code>n</code> to the name. Where <code>n</code> is an
	 * integer <code>&gt;= 0</code>.
	 */
	protected boolean animatedNoDash = false;
	protected boolean single = false;
	/**
	 * Last version the image described by this filter
	 * was used in. If the <tt>skin.ini</tt> version is
	 * higher than this, this file is a legacy file.
	 */
	protected Version maxVersion = null;//XXX latest?
	protected String customProperty = null;
	protected String customDefault = null;
	protected String[] customPath;
	protected int customKeyCount = -1;
	/**
	 * Override filter, if the file described by the other
	 * filter exists, the file described by this filter
	 * is not required.
	 */
	protected Filter override = null;
	protected String overrideName = null;
	//true = need, false = ignored
	protected boolean overrideMode = false;
	
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
		Setting<?> pathSetting = ini.find(customProperty, customKeyCount);
		if(pathSetting != null && pathSetting.isEnabled()){
			//path separator is currently hard coded in the skin.ini format
			customPath = pathSetting.getValue().toString().split("/");
		}else{
			customPath = new String[]{customDefault};
		}
	}
	
	public void hasHD(){
		//TODO ...
	}




	@Override
	protected boolean matches(File file, String fn, Deque<String> path){
		
		
		
		
		//TODO handle non root
		fn = fn.substring(name.length());
		if(fn.length() == 0){
			return true;
		}else{
			
		}
		
		return false;
	}











	@Override
	public Model getModel(List<Filter> filters){
		return new ImageModel(filters);
	}

	@Override
	protected boolean allowNonRoot(){
		return customProperty != null;
	}
}

package me.roan.osuskinchecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class Filter{
	
	protected List<File> matches = new ArrayList<File>();
	/**
	 * Base file name without animation sequence number or extension.
	 */
	protected String name;
	/**
	 * Allowed file extensions.
	 */
	private String[] extensions;
	/**
	 * Whether or not multiple versions of the
	 * image described by this filter object can exist, 
	 * where extra files are named by adding
	 * <code>-n</code> to the name. Where <code>n</code> is an
	 * integer <code>&gt;= 0</code>.
	 */
	protected boolean animatedDash;

	public Filter(String[] args){
		for(String arg : args){
			if(arg.equals("N")){
				animatedDash = true;
				break;
			}
		}
		this.extensions = args[args.length - 2].split(",");
		this.name = args[args.length - 1];
	}
		
	public boolean check(File file){
		//check extension here, delegate other checks to subclass
		String fn = file.getName();
		for(String ext : extensions){
			if(fn.endsWith("." + ext)){
				if(matches(file, fn.substring(0, fn.length() - 1 - ext.length()))){
					matches.add(file);
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	
	public void reset(){
		matches.clear();
	}
	
	//at least one match (mostly for sound)
	public boolean hasMatch(){
		return !matches.isEmpty();
	}
	
	public abstract Model getModel(List<Filter> filters);

	protected abstract boolean matches(File file, String fn);
	
	@Override
	public String toString(){
		return name;
	}
}

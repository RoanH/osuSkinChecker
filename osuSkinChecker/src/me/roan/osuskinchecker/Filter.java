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

	public Filter(String line){
		String[] data = line.split(" ");
		if(!data[0].equals("-")){
			char[] args = data[0].toUpperCase(Locale.ROOT).toCharArray();
			for(char option : args){
				switch(option){
				case 'N':
					animatedDash = true;
					break;
				}
			}
		}
		this.extensions = data[1].split(",");
		this.name = data[2];
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
	
	public abstract Model getModel(List<Filter> filters);

	protected abstract boolean matches(File file, String fn);
	
	@Override
	public String toString(){
		return name;
	}
}

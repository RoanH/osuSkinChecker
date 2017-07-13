package me.roan.osuskinchecker;

import java.io.File;
import java.util.Locale;

public final class SoundInfo implements Info{
	boolean variableWithDash = false;
	Boolean exists = null;

	String[] extensions;

	String name;
	
	public SoundInfo(String line){
		String[] data = line.split(" ");
		if(!data[0].equals("-")){
			char[] args = data[0].toUpperCase(Locale.ROOT).toCharArray();
			for(char option : args){
				switch(option){
				case 'N':
					variableWithDash = true;
					break;
				}
			}
		}
		this.extensions = data[1].split(",");
		this.name = data[2];
	}

	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public boolean show(){
		if(SkinChecker.showAll){
			return true;
		}else{
			return !exists();
		}
	}
	
	/**
	 * Checks whether or not a sound
	 * file exists that matches
	 * the criteria described by
	 * this information object. 
	 * @return Whether or not a sound
	 *         file was found.
	 */
	protected boolean exists(){
		if(exists == null){
			exists = false;
			for(String ext : extensions){
				if(checkForFile(SkinChecker.skinFolder, name, ext, variableWithDash)){
					exists = true;
					break;
				}
			}
		}
		return exists;
	}
	
	/**
	 * Check to see if a file exists that
	 * matches the given conditions.
	 * @param folder The folder the file should be in
	 * @param name The base name of the file
	 * @param extension The extension of the file
	 * @param variableDash Whether or not multiple versions of the
	 *        image can exist, where extra files are named by adding
	 *        <code>-n</code> to the name. Where <code>n</code> is an
	 *        integer <code>>= 0</code>.
	 * @return True if a file exists that matches the given conditions
	 *         false otherwise.
	 */
	private boolean checkForFile(File folder, String name, final String extension, boolean variableDash){
		if(new File(folder, name + "." + extension).exists()){
			return true;
		}
		if(variableDash && new File(folder, name + "-0." + extension).exists()){
			return true;
		}
		return false;
	}
}
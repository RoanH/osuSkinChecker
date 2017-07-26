package me.roan.osuskinchecker;

import java.io.File;
import java.util.Locale;

/**
 * SoundInfo objects are used to describe a
 * sound file and check whether or not a
 * matching sound file exists.
 * @author Roan
 * @see Info
 * @see SoundModel
 */
public final class SoundInfo implements Info{
	/**
	 * Whether or not multiple versions of the
	 * sound file described by this information
	 * object can exist, where extra files are named by adding
	 * <code>-n</code> to the name. Where <code>n</code> is an
	 * integer <code>>= 0</code>.
	 */
	private boolean variableWithDash = false;
	/**
	 * Boolean to store whether or not a file
	 * exists that matches the criteria specified
	 * by this information object. This variable is
	 * <code>null</code> if no checks have been executed yet.
	 */
	private Boolean exists = null;
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
	public void reset(){
		exists = null;
		exists();
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
	 *        sound file can exist, where extra files are named by adding
	 *        <code>-n</code> to the name. Where <code>n</code> is an
	 *        integer <code>>= 0</code>.
	 * @return True if a file exists that matches the given conditions
	 *         false otherwise.
	 */
	private boolean checkForFile(File folder, String name, final String extension, boolean variableDash){
		File file;
		if((file = new File(folder, name + "." + extension)).exists()){
			SkinChecker.allFiles.remove(file);
			return true;
		}
		if(variableDash && (file = new File(folder, name + "-0." + extension)).exists()){
			SkinChecker.allFiles.remove(file);
			int c = 1;
			while((file = new File(folder, name + "-" + c + "." + extension)).exists()){
				SkinChecker.allFiles.remove(file);
				c++;
			}
			return true;
		}
		return false;
	}
}
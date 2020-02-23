package me.roan.osuskinchecker;

import java.io.File;
import java.util.List;
import java.util.Locale;

/**
 * SoundInfo objects are used to describe a
 * sound file and check whether or not a
 * matching sound file exists.
 * @author Roan
 * @see Info
 * @see SoundModel
 */
public final class SoundFilter extends Filter{

	

	public SoundFilter(String line){
		super(line);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public String toString(){
//		return name;
//	}

	@Override
	public void reset(){
		//exists = null;
		exists();
	}

//	@Override
//	public boolean show(){
//		if(SkinChecker.showAll){
//			return true;
//		}else{
//			return !exists();
//		}
//	}

	/**
	 * Checks whether or not a sound
	 * file exists that matches
	 * the criteria described by
	 * this information object. 
	 * @return Whether or not a sound
	 *         file was found.
	 */
	protected boolean exists(){
//		if(exists == null){
//			exists = false;
//			for(String ext : extensions){
//				if(checkForFile(SkinChecker.skinFolder, name, ext, variableWithDash)){
//					exists = true;
//					break;
//				}
//			}
//		}
	//	return exists;
		return false;
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
	 *        integer <code>&gt;= 0</code>.
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

	@Override
	public Model getModel(List<Filter> filters){
		// TODO Auto-generated method stub
		return new SoundModel(filters);
	}

	@Override
	protected boolean matches(File file){
		// TODO Auto-generated method stub
		return false;
	}
}
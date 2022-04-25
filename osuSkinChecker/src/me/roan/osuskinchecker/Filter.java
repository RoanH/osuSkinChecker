package me.roan.osuskinchecker;

import java.io.File;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Locale;

import me.roan.osuskinchecker.ini.SkinIni;
import me.roan.osuskinchecker.ini.Version;

/**
 * Abstract base class for filters.
 * @author Roan
 * @param <T> The metadata type.
 */
public abstract class Filter<T>{
	/**
	 * List of offered files that matched this filter.
	 */
	protected List<T> matches = new ArrayList<T>();
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
	protected boolean animatedDash = false;

	/**
	 * Constructs a new Filter with the given arguments.
	 * @param args The filter construction arguments.
	 */
	public Filter(String[] args){
		for(String arg : args){
			if(arg.equals("N")){
				animatedDash = true;
				break;
			}
		}
		this.extensions = args[args.length - 2].split(",");
		this.name = args[args.length - 1].toLowerCase(Locale.ROOT);
		if(this.name.equals("-")){
			this.name = "";
		}
	}

	/**
	 * Checks if the given file with the given file
	 * path matches this filter.
	 * @param file The file to check.
	 * @param path The file path.
	 * @return True if the file matched this filter,
	 *         false otherwise.
	 */
	public boolean check(File file, Deque<String> path){
		//check extension here, delegate other checks to subclass
		String fn = file.getName().toLowerCase(Locale.ROOT);
		for(String ext : extensions){
			if(fn.endsWith("." + ext)){
				if((fn.startsWith(name) && path.isEmpty()) || allowNonRoot()){
					T meta = matches(file, fn.substring(0, fn.length() - 1 - ext.length()), path);
					if(meta != null){
						matches.add(meta);
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * Removes all matches from this filter
	 * and reinitialises all cached data.
	 * @param ini The <tt>skin.ini</tt>.
	 */
	public void reset(SkinIni ini){
		matches.clear();
	}
	
	/**
	 * Checks if at least one offered file
	 * matched this filter.
	 * @return True if at least one match was found.
	 */
	public boolean hasMatch(){
		return !matches.isEmpty();
	}
	
	/**
	 * Gets a display model for this filter type.
	 * @param filters The filters to add to the model.
	 * @return A model with the given filteres.
	 */
	public abstract Model getModel(List<Filter<?>> filters);

	/**
	 * Checks if the given file matches this filter.
	 * @param file The file to check.
	 * @param fn The base name of this file with
	 *        no extension and the name stripped
	 *        if root is not allowed.
	 * @param path The file path inside the skin folder.
	 * @return A description of the matched file or <code>
	 *         null</code> if this file did not match the filter.
	 */
	protected abstract T matches(File file, String fn, Deque<String> path);
	
	/**
	 * Whether or not this filter matches files with a non
	 * default name or that are located in sub folders.
	 * @return True if this filter allows non root files.
	 */
	protected abstract boolean allowNonRoot();
	
	/**
	 * Called to determine if this file
	 * should be listed in the tables.
	 * @param version The <tt>skin.ini</tt>
	 *        version used in this skin.
	 * @return Whether or not to show this
	 *         file in the GUI.
	 */
	protected abstract boolean show(Version version);
	
	/**
	 * Used to setup possible filter relations after all
	 * filters have been created.
	 * @param filters A list of all created filters.
	 */
	protected abstract void link(List<Filter<?>> filters);
	
	/**
	 * Gets a list of all the files that matched this filter.
	 * @return A list of all the files that matched this filter.
	 */
	public abstract List<File> getMatchedFiles();
	
	@Override
	public String toString(){
		return name;
	}
}

package me.roan.osuskinchecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Filter{
	
	protected List<File> matches = new ArrayList<File>();
	/**
	 * Base file name without animation sequence number or extension.
	 */
	private String name;
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
	
	public void check(File file){
		if(matches(file)){
			matches.add(file);
		}
	}
	
	public void reset(){
		matches.clear();
	}
	
	public abstract Model getModel(List<Filter> filters);

	protected abstract boolean matches(File file);
}

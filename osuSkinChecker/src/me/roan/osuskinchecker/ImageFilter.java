package me.roan.osuskinchecker;

import java.io.File;
import java.util.List;

import me.roan.osuskinchecker.ini.Version;

public class ImageFilter extends Filter{
	
	public ImageFilter(String[] args){
		super(args);
		// TODO Auto-generated constructor stub
	}











	/**
	 * Whether or not multiple versions of the
	 * image described by this filter
	 * object can exist, where extra files are named by adding
	 * <code>n</code> to the name. Where <code>n</code> is an
	 * integer <code>&gt;= 0</code>.
	 */
	protected boolean animatedNoDash;
	protected boolean SdOnly;
	/**
	 * Last version the image described by this filter
	 * was used in. If the <tt>skin.ini</tt> version is
	 * higher than this, this file is a legacy file.
	 */
	protected Version maxVersion;
	protected String customPath;
	protected String customPathMania;
	/**
	 * Override filter, if the file described by the other
	 * filter exists, the file described by this filter
	 * is not required.
	 */
	protected Filter override;
	
	
	
	
	
	
	
	
	
	public void hasHD(){
		//TODO ...
	}











	@Override
	protected boolean matches(File file, String fn){
		// TODO Auto-generated method stub
		return false;
	}











	@Override
	public Model getModel(List<Filter> filters){
		return new ImageModel(filters);
	}
}

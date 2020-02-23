package me.roan.osuskinchecker;

import java.io.File;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Locale;

import me.roan.osuskinchecker.ini.SkinIni;
import me.roan.osuskinchecker.ini.Version;

public abstract class Filter<T>{
	
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
	
	public void reset(SkinIni ini){
		matches.clear();
	}
	
	//at least one match (mostly for sound)
	public boolean hasMatch(){
		return !matches.isEmpty();
	}
	
	public abstract Model getModel(List<Filter<?>> filters);

	protected abstract T matches(File file, String fn, Deque<String> path);
	
	protected abstract boolean allowNonRoot();
	
	protected abstract boolean show(Version version);
	
	@Override
	public String toString(){
		return name;
	}
}

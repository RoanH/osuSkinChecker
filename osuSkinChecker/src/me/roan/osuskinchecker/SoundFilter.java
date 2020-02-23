package me.roan.osuskinchecker;

import java.io.File;
import java.util.List;

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
	}
	
	@Override
	public Model getModel(List<Filter> filters){
		return new SoundModel(filters);
	}

	@Override
	protected boolean matches(File file, String fn){
		if(fn.startsWith(name)){
			fn = fn.substring(name.length());
			if(fn.length() == 0){
				return true;
			}else{
				return fn.matches("-[0-9]+");
			}
		}else{
			return false;
		}
	}
}
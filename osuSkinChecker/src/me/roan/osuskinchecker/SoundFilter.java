package me.roan.osuskinchecker;

import java.io.File;
import java.util.Deque;
import java.util.List;

import me.roan.osuskinchecker.ini.Version;

/**
 * SoundFilter objects are used to describe a
 * sound file and check whether or not a
 * matching sound file exists.
 * @author Roan
 * @see Filter
 * @see SoundModel
 */
public final class SoundFilter extends Filter<File>{

	public SoundFilter(String[] args){
		super(args);
	}
	
	@Override
	public Model getModel(List<Filter<?>> filters){
		return new SoundModel(filters);
	}

	@Override
	protected File matches(File file, String fn, Deque<String> path){
		fn = fn.substring(name.length());
		if(fn.length() == 0){
			return file;
		}else{
			if(animatedDash && fn.matches("-[0-9]+")){
				return file;
			}else{
				return null;
			}
		}
	}

	@Override
	protected boolean allowNonRoot(){
		return false;
	}

	@Override
	protected boolean show(Version version){
		return SkinChecker.showAll || !hasMatch();
	}

	@Override
	protected void link(List<Filter<?>> filters){
	}
}
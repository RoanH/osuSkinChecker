package dev.roanh.osuskinchecker;

import java.io.File;
import java.util.Deque;
import java.util.List;
import java.util.regex.Pattern;

import dev.roanh.osuskinchecker.ini.Version;

/**
 * SoundFilter objects are used to describe a
 * sound file and check whether or not a
 * matching sound file exists.
 * @author Roan
 * @see Filter
 * @see SoundModel
 */
public final class SoundFilter extends Filter<File>{
	/**
	 * Sequence regex.
	 */
	private static final Pattern SEQ_REGEX = Pattern.compile("-[0-9]+");

	/**
	 * Constructs a new SoundFilter with the given
	 * construction arguments.
	 * @param args The filter construction arguments.
	 */
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
			if(animatedDash && SEQ_REGEX.matcher(fn).matches()){
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

	@Override
	public List<File> getMatchedFiles(){
		return matches;
	}
}
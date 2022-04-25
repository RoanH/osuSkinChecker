package me.roan.osuskinchecker.ini;

/**
 * Represents a comment, empty line
 * or any other line in the skin.ini
 * that is not a setting
 * @author Roan
 */
public class Comment extends Setting<String>{

	/**
	 * Constructs a new comment from the
	 * given line of text
	 * @param line The comment text
	 */
	protected Comment(String line){
		super(null, line);
		setEnabled(true);
	}
	
	@Override
	protected boolean wasUpdated(){
		return true;
	}

	@Override
	public String toString(){
		return getValue();
	}
}

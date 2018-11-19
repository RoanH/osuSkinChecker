package me.roan.osuskinchecker;

public class Comment extends Setting<String>{

	protected Comment(String line){
		super(null, line);
		setEnabled(true);
	}

	@Override
	public String toString(){
		return getValue();
	}
}

package me.roan.osuskinchecker;

import java.util.ArrayList;
import java.util.List;

public class Section{

	public final String name;
	protected final List<Setting<?>> data = new ArrayList<Setting<?>>();
	
	protected Section(String name){
		this.name = name;
	}
	
	protected boolean isMania(){
		return name != null && name.contains("Mania");
	}
}

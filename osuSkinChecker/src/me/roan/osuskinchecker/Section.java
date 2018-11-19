package me.roan.osuskinchecker;

import java.util.ArrayList;
import java.util.List;

import me.roan.osuskinchecker.SkinIni.ManiaIni;

public class Section{

	protected final String name;
	protected ManiaIni mania;
	protected final List<Setting<?>> data = new ArrayList<Setting<?>>();
	
	protected Section(String name){
		this.name = name;
	}
	
	protected boolean isMania(){
		return mania != null;
	}
}

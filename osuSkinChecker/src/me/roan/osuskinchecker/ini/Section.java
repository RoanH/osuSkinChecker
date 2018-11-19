package me.roan.osuskinchecker.ini;

import java.util.ArrayList;
import java.util.List;

import me.roan.osuskinchecker.ini.SkinIni.ManiaIni;

public class Section{

	protected final String name;
	protected ManiaIni mania;
	protected final List<Setting<?>> data = new ArrayList<Setting<?>>();
	
	protected Section(String name){
		this.name = name;
	}
	
	protected boolean isMania(){
		return mania != null || (name != null && name.trim().equals("[Mania]"));
	}
}

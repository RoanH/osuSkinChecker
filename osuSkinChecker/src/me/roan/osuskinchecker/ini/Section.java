package me.roan.osuskinchecker.ini;

import java.util.ArrayList;
import java.util.List;

import me.roan.osuskinchecker.ini.SkinIni.ManiaIni;

/**
 * Represents a section in the skin.ini
 * @author Roan
 */
public class Section{
	/**
	 * The name of this skin.ini section
	 */
	protected final String name;
	/**
	 * The mania configuration for this
	 * section if this is a mania section
	 */
	protected ManiaIni mania;
	/**
	 * The settings under this section
	 */
	protected final List<Setting<?>> data = new ArrayList<Setting<?>>();
	
	/**
	 * Constructs a new section with
	 * the given name
	 * @param name The name for this section
	 */
	protected Section(String name){
		this.name = name;
	}
	
	/**
	 * Checks if this section is a
	 * mania section
	 * @return Whether or not this section
	 *         is a mania section
	 */
	protected boolean isMania(){
		return mania != null || (name != null && name.trim().equals("[Mania]"));
	}
}

package dev.roanh.osuskinchecker.ini;

import dev.roanh.osuskinchecker.ini.SkinIni.Printable;

/**
 * Enum for all the different NoteBodyStyle options
 * @author Roan
 * @see Printable
 */
public enum Version implements Printable{
	/**
	 * Version 1.0
	 */
	V1("1", "(Old style)"),
	/**
	 * Version 2.0
	 */
	V2("2", "(Basic new style)"),
	/**
	 * Version 2.1
	 */
	V21("2.1", "(Taiko position changes)"),
	/**
	 * Version 2.2
	 */
	V22("2.2", "(UI changes)"),
	/**
	 * Version 2.3
	 */
	V23("2.3", "(New Catch catcher style)"),
	/**
	 * Version 2.4
	 */
	V24("2.4", "(Mania stage scaling reduction)"),
	/**
	 * Version 2.5
	 */
	V25("2.5", "(Mania upscroll and column improvements)"),
	/**
	 * Version 2.6
	 */
	V26("2.6", "(No added functionality)"),
	/**
	 * Version 2.7
	 */
	V27("2.7", "(No added functionality)"),
	/**
	 * Latest version
	 */
	LATEST("latest", "(For personal skins)");

	/**
	 * The display name for this setting
	 */
	public final String name;
	/**
	 * The extra information for this setting
	 */
	public final String extra;

	/**
	 * Constructs a new Version with the
	 * given name and information
	 * @param name The display name for this version
	 * @param extra The description for this version
	 */
	private Version(String name, String extra){
		this.name = name;
		this.extra = extra;
	}
	
	/**
	 * Parses the given input for a Version
	 * @param str The input to parse
	 * @return The Version parsed from the given input string
	 */
	public static Version fromString(String str){
		switch(str){
		case "1":
			return V1;
		case "2":
		case "2.0":
			return V2;
		case "2.1":
			return V21;
		case "2.2":
			return V22;
		case "2.3":
			return V23;
		case "2.4":
			return V24;
		case "2.5":
			return V25;
		case "2.6":
			return V26;
		case "2.7":
			return V27;
		case "latest":
			return LATEST;
		default:
			SkinIni.usedDefault.add("Version");
			return V1;
		}
	}
	
	/**
	 * Compares this version to the given other version.
	 * @param other The version to compare to.
	 * @return True if this version is after 
	 *         or the same as the given other version.
	 */
	public boolean isAfterOrSame(Version other){
		return this.ordinal() >= other.ordinal();
	}

	@Override
	public String toString(){
		return name + " " + extra;
	}

	@Override
	public String print(){
		return name;
	}
}
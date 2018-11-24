package me.roan.osuskinchecker.ini;

import java.util.StringJoiner;

import me.roan.osuskinchecker.ini.SkinIni.Printable;

/**
 * Represents a setting in the skin.ini
 * @author Roan
 * @param <T> The type of the value for this setting
 */
public class Setting<T>{
	/**
	 * The name of this setting
	 */
	private String name;
	/**
	 * The current value for this setting
	 */
	private T value;
	/**
	 * Whether or not this setting is enabled
	 * that is whether or not it will be written
	 */
	private boolean enabled = true;
	/**
	 * Whether or not this setting was updated
	 * during the initialisation phase
	 */
	private boolean wasUpdated = false;
	/**
	 * Whether or not to only allow a single update
	 * to this setting
	 */
	protected static boolean singleUpdateMode = false;
	/**
	 * Whether or not this setting was already indexed
	 */
	protected boolean added = false;
	
	/**
	 * Constructs a new setting with the
	 * given name and value
	 * @param name The name for this setting
	 * @param def The current value for this setting
	 */
	protected Setting(String name, T def){
		value = def;
		this.name = name;
	}
	
	/**
	 * Constructs a new setting with the
	 * given name, value and enabled state
	 * @param name The name for this setting
	 * @param enabled Whether or not this setting
	 *        is currently enabled
	 * @param def The current value for this setting
	 */
	protected Setting(String name, boolean enabled, T def){
		this(name, def);
		this.enabled = enabled;
	}
	
	/**
	 * Gets the name of this setting
	 * @return The name of this setting
	 */
	protected String getName(){
		return name;
	}
	
	/**
	 * Gets the value of this setting
	 * @return The current value of this setting
	 */
	protected T getValue(){
		return value;
	}
	
	/**
	 * Enables or disables this setting
	 * @param enabled The enabled state for this setting
	 */
	protected void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	/**
	 * Updates the value for this setting
	 * @param newValue The new value to set
	 *        if allowed
	 * @return This setting
	 */
	protected Setting<T> update(T newValue){		
		if(!(wasUpdated && singleUpdateMode)){
			if(singleUpdateMode){
				enabled = true;
				wasUpdated = true;
			}
			value = newValue;
		}
		return this;
	}
	
	/**
	 * Checks whether this setting is
	 * currently enabled
	 * @return Whether this setting is
	 *         enabled or not
	 */
	protected boolean isEnabled(){
		return enabled;
	}
	
	@Override
	public String toString(){
		if(value instanceof Printable){
			return name + ": " + ((Printable)value).print();
		}else if(value instanceof Boolean){
			return name + ": " + (((Boolean)value) ? "1" : "0");
		}else if(value instanceof double[]){
			StringJoiner joiner = new StringJoiner(",");
			for(double d : (double[])value){
				joiner.add(String.valueOf(d));
			}
			return name + ": " + joiner.toString();
		}else{
			return name + ": " + value.toString();
		}
	}
}

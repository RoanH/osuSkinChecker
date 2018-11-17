package me.roan.osuskinchecker;

public class Setting<T>{

	private T value;
	private boolean enabled = true;
	
	protected Setting(T def){
		value = def;
	}
	
	protected Setting(boolean enabled, T def){
		this(def);
		this.enabled = enabled;
	}
	
	protected T getValue(){
		return value;
	}
	
	protected void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	protected void update(T newValue){
		value = newValue;
	}
	
	protected boolean isEnabled(){
		return enabled;
	}
}

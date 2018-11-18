package me.roan.osuskinchecker;

import java.util.StringJoiner;

import me.roan.osuskinchecker.SkinIni.Printable;

public class Setting<T>{

	private String name;
	private T value;
	private boolean enabled = true;
	
	protected Setting(String name, T def){
		value = def;
		this.name = name;
	}
	
	protected Setting(String name, boolean enabled, T def){
		this(name, def);
		this.enabled = enabled;
	}
	
	protected String getName(){
		return name;
	}
	
	protected T getValue(){
		return value;
	}
	
	protected void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	protected Setting<T> update(T newValue){
		value = newValue;
		return this;
	}
	
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
			return joiner.toString();
		}else{
			return name + ": " + value.toString();
		}
	}
}

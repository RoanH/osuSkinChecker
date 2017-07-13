package me.roan.osuskinchecker;

import java.io.File;
import java.util.Locale;

public final class SoundInfo implements Info{
	boolean variableWithDash = false;
	Boolean exists = null;

	String[] extensions;

	String name;

	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public boolean show(){
		if(SkinChecker.showAll){
			return true;
		}else{
			return !exists();
		}
	}
	
	boolean exists(){
		if(exists == null){
			exists = false;
			for(String ext : extensions){
				if(checkForFile(SkinChecker.skinFolder, name, ext, variableWithDash)){
					exists = true;
					break;
				}
			}
		}
		return exists;
	}
	
	private boolean checkForFile(File folder, String name, final String extension, boolean variableDash){
		if(new File(folder, name + "." + extension).exists()){
			return true;
		}
		if(variableDash && new File(folder, name + "-0." + extension).exists()){
			return true;
		}
		return false;
	}

	public SoundInfo(String line){
		String[] data = line.split(" ");
		if(!data[0].equals("-")){
			char[] args = data[0].toUpperCase(Locale.ROOT).toCharArray();
			for(char option : args){
				switch(option){
				case 'N':
					variableWithDash = true;
					break;
				}
			}
		}
		this.extensions = data[1].split(",");
		this.name = data[2];
	}
}
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
		boolean match = false;
		for(File file : folder.listFiles()){
			String fileName = file.getName().toLowerCase(Locale.ROOT);
			if(fileName.startsWith(name) && fileName.toLowerCase().endsWith("." + extension)){
				if(variableDash){
					String n = fileName.substring(name.length());
					if(n.startsWith("-")){
						n = n.substring((variableDash ? 1 : 0));
					}
					n = n.substring(0, n.length() - 1 - extension.length());
					if(n.isEmpty()){
						match = true;
						break;
					}
					boolean number = false;
					try{
						Integer.parseInt(n);
						number = true;
					}catch(NumberFormatException e){
					}
					match = number;
					if(match){
						break;
					}else{
						continue;
					}
				}else{
					match = true;
					break;
				}
			}else{
				continue;
			}
		}
		return match;
	}

	public SoundInfo init(String line){
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
		return this;
	}
}
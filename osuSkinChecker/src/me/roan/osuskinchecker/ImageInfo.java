package me.roan.osuskinchecker;

import java.io.File;
import java.util.Locale;

public final class ImageInfo implements Info{
	boolean singleImage = false;
	boolean legacy = false;
	boolean variableWithDash = false;
	boolean variableWithoutDash = false;
	boolean customPath = false;
	boolean customPathPrefix = false;
	int customID = -1;
	Boolean hasSD = null;
	Boolean hasHD = null;

	String[] extensions;

	String name;

	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public boolean show(){
		if(SkinChecker.showAll){
			return !legacy || SkinChecker.checkLegacy;
		}else{
			if(legacy && !SkinChecker.checkLegacy){
				return false;
			}else{
				return (SkinChecker.checkSD ? !hasSDVersion() : false) || (SkinChecker.checkHD ? !hasHDVersion() : false);
			}
		}
	}
	
	boolean hasSDVersion(){
		if(hasSD == null){
			for(String ext : extensions){
				if(checkForFile(SkinChecker.skinFolder, name, false, ext, variableWithDash, variableWithoutDash, customPath, customPathPrefix)){
					hasSD = true;
				}
			}
			if(hasSD == null){
				hasSD = false;
			}
		}
		return hasSD;
	}
	
	boolean hasHDVersion(){
		if(hasHD == null){
			if(singleImage){
				hasHD = true;
			}
			for(String ext : extensions){
				if(checkForFile(SkinChecker.skinFolder, name, true, ext, variableWithDash, variableWithoutDash, customPath, customPathPrefix)){
					hasHD = true;
				}
			}
			if(hasHD == null){
				hasHD = false;
			}
		}
		return hasHD;
	}
	
	private boolean checkForFile(File folder, String name, boolean hd, String extension, boolean variableDash, boolean variableNoDash, boolean custom, boolean customPrefix){
		boolean match = false;
		if(hd){
			extension = "@2x." + extension;
		}else{
			extension = "." + extension;
		}
		if(new File(folder, name + extension).exists()){
			return true;
		}
		if(variableDash && new File(folder, name + "-0" + extension).exists()){
			return true;
		}
		if(variableNoDash && new File(folder, name + "0" + extension).exists()){
			return true;
		}
		if(!match && (custom || customPrefix) && this.customID != -1){
			if(customPrefix){
				return checkForFile(SkinChecker.customPathing.get(this.customID), name, hd, extension, variableDash, variableNoDash, false, false);
			}else{
				File f = SkinChecker.customPathing.get(this.customID);
				return f == null ? false : checkForFile(f.getParentFile(), f.getName(), hd, extension, variableDash, variableNoDash, false, false);
			}
		}
		return match;
	}
	
	/*private boolean checkForFile(File folder, String name, boolean hd, final String extension, boolean variableDash, boolean variableNoDash, boolean custom, boolean customPrefix){
		boolean match = false;
		for(File file : folder.listFiles()){
			String fileName = file.getName().toLowerCase(Locale.ROOT);
			if(fileName.startsWith(name) && fileName.toLowerCase().endsWith((hd ? "@2x." : ".") + extension)){
				if(!hd && fileName.endsWith("@2x." + extension)){
					continue;
				}
				if(variableDash || variableNoDash){
					String n = fileName.substring(name.length());
					if(n.startsWith("-")){
						n = n.substring((variableDash ? 1 : 0));
					}
					n = n.substring(0, n.length() - (hd ? 4 : 1) - extension.length());
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
		if(!match && (custom || customPrefix) && this.customID != -1){
			if(customPrefix){
				return checkForFile(SkinChecker.customPathing.get(this.customID), name, hd, extension, variableDash, variableNoDash, false, false);
			}else{
				File f = SkinChecker.customPathing.get(this.customID);
				return f == null ? false : checkForFile(f.getParentFile(), f.getName(), hd, extension, variableDash, variableNoDash, false, false);
			}
		}
		return match;
	}*/

	public ImageInfo(String line){
		String[] data = line.split(" ");
		if(!data[0].equals("-")){
			char[] args = data[0].toUpperCase(Locale.ROOT).toCharArray();
			for(char option : args){
				switch(option){
				case 'N':
					variableWithDash = true;
					break;
				case 'M':
					variableWithoutDash = true;
					break;
				case 'S':
					singleImage = true;
					break;
				case 'C':
					customPath = true;
					customID = Integer.parseInt(data[1]);
					break;
				case 'P':
					customPath = true;
					customID = Integer.parseInt(data[1]);
					break;
				case 'L':
					legacy = true;
					break;
				}
			}
		}
		this.extensions = data[1 + (customPath ? 1 : 0)].split(",");
		this.name = data[2 + (customPath ? 1 : 0)];
	}
}
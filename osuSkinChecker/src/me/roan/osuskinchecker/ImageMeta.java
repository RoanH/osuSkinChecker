package me.roan.osuskinchecker;

import java.io.File;

public class ImageMeta{
	private final File file;
	private final boolean hd;
	private final int sequenceNum;
	
	protected ImageMeta(File file, boolean hd){
		this(file, hd, -1);
	}
	
	protected ImageMeta(File file, boolean hd, int seq){
		this.file = file;
		this.hd = hd;
		this.sequenceNum = seq;
	}
	
	public boolean isHD(){
		return hd;
	}
	
	public boolean isSD(){
		return !hd;
	}
	
	//empty?
}

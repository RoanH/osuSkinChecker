package me.roan.osuskinchecker;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

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
	
	public boolean isEmpty(){
		return isEmptyImage(file);//TODO cache
	}
	
	/**
	 * Checks if an image is empty.
	 * An Image is considered empty if 
	 * it's dimensions are 1 by 1 or if
	 * it does not exist.
	 * @param img The image file to check
	 * @return Whether or not the image is empty
	 */
	private static boolean isEmptyImage(File img){
		if(img.exists()){
			try{
				Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix(img.getName().substring(img.getName().lastIndexOf('.') + 1));
				while(readers.hasNext()){
					ImageReader reader = readers.next();
					reader.setInput(ImageIO.createImageInputStream(img));
					return reader.getWidth(0) == 1 && reader.getHeight(0) == 1;
				}
				return false;
			}catch(IOException e){
				return false;
			}
		}else{
			return true;
		}
	}
}

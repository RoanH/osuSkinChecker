package me.roan.osuskinchecker;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

/**
 * Metadata class desribing a filter match.
 * @author Roan
 * @see ImageFilter
 */
public class ImageMeta{
	/**
	 * The image file.
	 */
	private final File file;
	/**
	 * Whether or not this is a HD image file.
	 */
	private final boolean hd;
	/**
	 * The animation sequence number for this file.
	 */
	private final int sequenceNum;
	
	/**
	 * Constructs a new ImageMeta with the
	 * given file and hd flag.
	 * @param file The file the metadata is for.
	 * @param hd If the given file is a HD file.
	 */
	protected ImageMeta(File file, boolean hd){
		this(file, hd, -1);
	}
	
	/**
	 * Constructs a new ImageMeta with the
	 * given file, hd flag and sequence number.
	 * @param file The file the metadata is for.
	 * @param hd If the given file is a HD file.
	 * @param seq The animation sequence number.
	 */
	protected ImageMeta(File file, boolean hd, int seq){
		this.file = file;
		this.hd = hd;
		this.sequenceNum = seq;
	}
	
	/**
	 * Checks if this is a HD file.
	 * @return True if this file is a HD file.
	 */
	public boolean isHD(){
		return hd;
	}
	
	/**
	 * Checks if this is a SD file.
	 * @return True if this file is a SD file.
	 */
	public boolean isSD(){
		return !hd;
	}
	
	/**
	 * Checks if this is an animated file.
	 * @return True if this file is an animated file.
	 */
	public boolean isAnimated(){
		return sequenceNum != -1;
	}
	
	/**
	 * Checks if this is an empty file.
	 * @return True if this file is an empty file.
	 */
	public boolean isEmpty(){
		return isEmptyImage(file);
	}
	
	/**
	 * Gets the animation sequence number for this file.
	 * @return The animation sequence number.
	 */
	public int getSequenceNumber(){
		return sequenceNum;
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

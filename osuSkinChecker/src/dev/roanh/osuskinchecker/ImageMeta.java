package dev.roanh.osuskinchecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * Metadata class desribing a filter match.
 * @author Roan
 * @see ImageFilter
 */
public class ImageMeta{
	/**
	 * The image file.
	 */
	private final Path file;
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
	protected ImageMeta(Path file, boolean hd){
		this(file, hd, -1);
	}
	
	/**
	 * Constructs a new ImageMeta with the
	 * given file, hd flag and sequence number.
	 * @param file The file the metadata is for.
	 * @param hd If the given file is a HD file.
	 * @param seq The animation sequence number.
	 */
	protected ImageMeta(Path file, boolean hd, int seq){
		this.file = file;
		this.hd = hd;
		this.sequenceNum = seq;
	}
	
	/**
	 * Gets the file this ImageMeta is for.
	 * @return The file for this ImageMeta.
	 */
	public Path getFile(){
		return file;
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
	private static boolean isEmptyImage(Path img){
		if(Files.exists(img)){
			String name = img.getFileName().toString();
			if(name.contains(".")){
				Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix(name.substring(name.lastIndexOf('.') + 1));
				while(readers.hasNext()){
					ImageReader reader = readers.next();
					try(ImageInputStream in = ImageIO.createImageInputStream(Files.newInputStream(img))){
						reader.setInput(in);
						boolean empty = reader.getWidth(0) == 1 && reader.getHeight(0) == 1;
						reader.dispose();
						return empty;
					}catch(IOException e){
						return false;
					}
				}
				return false;
			}
		}
		return true;
	}
}

package me.roan.osuskinchecker;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

/**
 * ImageInfoOld objects are used to describe an
 * image file and check whether or not a
 * matching image file exists.
 * @author Roan
 * @see InfoOld
 * @see ImageModel
 */
@Deprecated
public final class ImageInfoOld{
	/**
	 * Whether or not the SD/HD distinction
	 * does not apply to the image described
	 * by this information object
	 */
	private boolean singleImage = false;
	/**
	 * Whether or not the image described by this
	 * information object is a legacy file and not 
	 * used anymore by recent skins.
	 */
	private boolean legacy = false;
	/**
	 * Whether or not multiple versions of the
	 * image described by this information
	 * object can exist, where extra files are named by adding
	 * <code>-n</code> to the name. Where <code>n</code> is an
	 * integer <code>&gt;= 0</code>.
	 */
	protected boolean variableWithDash = false;
	/**
	 * Whether or not multiple versions of the
	 * image described by this information
	 * object can exist, where extra files are named by adding
	 * <code>n</code> to the name. Where <code>n</code> is an
	 * integer <code>&gt;= 0</code>.
	 */
	protected boolean variableWithoutDash = false;
	/**
	 * The skin.ini setting the custom path is tied to
	 */
	protected String customProperty = null;
	/**
	 * The default resource location for the custom path
	 */
	protected String customDefault = null;
	/**
	 * The mania key count this custom path setting is for
	 */
	protected int customKeyCount = -1;
	/**
	 * Boolean to store whether or not a missing
	 * HD image for a file could be ignored because
	 * its SD variant is empty
	 */
	protected boolean ignored = false;
	/**
	 * Boolean to store whether or not the match
	 * found for this image is animated
	 */
	protected boolean animated = false;
	/**
	 * A list of allowed extensions for the
	 * file described by this information object
	 */
	private String[] extensions;
	/**
	 * The base name of the file described
	 * by this information object
	 */
	protected String name;
	/**
	 * Boolean to store whether or not the
	 * entire image could be ignored
	 */
	private boolean ignore = false;
	/**
	 * If this image is an animation the number
	 * of frames in the animation
	 */
	protected int frames;
	/**
	 * Full name custom path included for this image
	 */
	private String fullName = null;
	/**
	 * Whether or not this image is empty
	 */
	private boolean empty = true;
}
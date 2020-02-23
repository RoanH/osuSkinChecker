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
}
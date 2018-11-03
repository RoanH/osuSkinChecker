package me.roan.osuskinchecker;

/**
 * Interface to expose the {@link #show()}
 * method of information objects for both
 * images and sound files where the criteria
 * for whether or not to show a file differ.
 * @author RoanH
 */
public abstract interface Info{
	/**
	 * Called to determine if this file
	 * should be listed in the tables.
	 * @return Whether or not to show this
	 *         file in the GUI.
	 */
	public abstract boolean show();

	/**
	 * Resets all stored data
	 * for this information object
	 */
	public abstract void reset();
}
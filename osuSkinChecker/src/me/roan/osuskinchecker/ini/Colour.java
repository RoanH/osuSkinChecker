package me.roan.osuskinchecker.ini;

import java.awt.Color;

/**
 * Wrapper around the standard Java
 * Colour class to work better with
 * osu!. And to allow more control over
 * the internal representation of colors.
 * @author Roan
 * @see Color
 */
public class Colour{
	/**
	 * The 'red' component of this colour
	 */
	private int r;
	/**
	 * The 'green' component of this colour
	 */
	private int g;
	/**
	 * The 'blue' component of this colour
	 */
	private int b;
	/**
	 * The 'alpha' component of this colour
	 */
	private int a;
	
	/**
	 * Constructs a new colour from
	 * the given Java Color
	 * @param color The Java AWT Color
	 * @see Color
	 */
	public Colour(Color color){
		this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	/**
	 * Constructs a new colour from the
	 * given red, blue and green values.
	 * @param r The red component for this colour
	 * @param g The green component for this colour
	 * @param b The blue component for this colour
	 */
	public Colour(int r, int g, int b){
		this(r, g, b, 255);
	}

	/**
	 * Constructs a new colour from the
	 * given red, blue, green and alpha values.
	 * @param r The red component for this colour
	 * @param g The green component for this colour
	 * @param b The blue component for this colour
	 * @param a The alpha component for this colour
	 */
	public Colour(int r, int g, int b, int a){
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	/**
	 * Updates this colour with a new alpha values
	 * @param alpha The new alpha values, in range 0.0 - 100.0
	 */
	public final void update(double alpha){
		this.a = (int)Math.round(alpha * 2.55D);
	}
	
	/**
	 * Updates the red, green and blue component
	 * for this colour with components from the given colour.
	 * @param override The colour to take attributes from
	 */
	public final void update(Colour override){
		this.r = override.r;
		this.g = override.g;
		this.b = override.b;
	}

	/**
	 * Converts this colour to a
	 * Java AWT Color
	 * @return A Java AWT Color with the same
	 *         color as this colour
	 * @see Color
	 */
	public final Color toColor(){
		return new Color(r, g, b, a);
	}
	
	/**
	 * Gets the red component for this colour
	 * @return The red component for this colour
	 */
	public final int getRed(){
		return r;
	}
	
	/**
	 * Gets the green component for this colour
	 * @return The green component for this colour
	 */
	public final int getGreen(){
		return g;
	}
	
	/**
	 * Gets the blue component for this colour
	 * @return The blue component for this colour
	 */
	public final int getBlue(){
		return b;
	}
	
	/**
	 * Gets the alpha component for this colour
	 * @return The alpha component for this colour
	 */
	public final int getAlpha(){
		return a;
	}
	
	/**
	 * Gets the alpha component for this colour
	 * as a percentage between 0.0 and 100.0
	 * @return The alpha value as a percentage
	 */
	public final double getAlphaPercentage(){
		return a / 2.55;
	}
	
	/**
	 * Checks to see is this colour has
	 * an alpha component
	 * @return Whether this component has an alpha component
	 */
	public final boolean hasAlpha(){
		return a != 255;
	}
	
	@Override
	public String toString(){
		if(hasAlpha()){
			return r + "," + g + "," + b + "," + a;
		}else{
			return r + "," + g + "," + b;
		}
	}
}

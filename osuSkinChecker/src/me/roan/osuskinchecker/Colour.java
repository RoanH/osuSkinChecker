package me.roan.osuskinchecker;

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
	private int r;
	private int g;
	private int b;
	private int a;
	
	public Colour(Color color){
		this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	public Colour(int r, int g, int b){
		this(r, g, b, 255);
	}

	public Colour(int r, int g, int b, int a){
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public final void update(double alpha){
		this.a = (int)Math.round(alpha * 2.55D);
	}
	
	public final void update(Colour override){
		this.r = override.r;
		this.g = override.g;
		this.b = override.b;
	}

	public final Color toColor(){
		return new Color(r, g, b, a);
	}
	
	public final int getRed(){
		return r;
	}
	
	public final int getGreen(){
		return g;
	}
	
	public final int getBlue(){
		return b;
	}
	
	public final int getAlpha(){
		return a;
	}
	
	public final double getAlphaPercentage(){
		return a / 2.55;
	}
	
	public final boolean hasAlpha(){
		return a != 255;
	}
}

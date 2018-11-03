package me.roan.osuskinchecker;

public class Color{
	private int r;
	private int g;
	private int b;
	private int a;
	
	public Color(java.awt.Color color){
		this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	public Color(int r, int g, int b){
		this(r, g, b, 255);
	}

	public Color(int r, int g, int b, int a){
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public final java.awt.Color toColor(){
		return new java.awt.Color(r, g, b, a);
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
	
	public final boolean hasAlpha(){
		return a != 255;
	}
}

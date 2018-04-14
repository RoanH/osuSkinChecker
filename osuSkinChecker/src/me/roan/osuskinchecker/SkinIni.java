package me.roan.osuskinchecker;

import java.awt.Color;
import java.util.List;

public class SkinIni {
	//general
	protected String name;
	protected String author;
	protected String version;
	protected boolean cursorExpand;
	protected boolean cursorCentre;
	protected boolean cursorRotate;
	protected boolean cursorTrailRotate;
	protected int AnimationFramerate;//non negative
	
	//combo bursts
	protected boolean layeredHitSounds;
	protected boolean comboBurstRandom;
	protected int[] customComboBurstSounds;//list of ints, positive values only
	
	//standard
	protected boolean hitCircleOverlayAboveNumber;
	protected int sliderStyle;//1 or 2
	protected boolean sliderBallFlip;
	protected boolean AllowSliderBallTint;
	protected boolean SpinnerNoBlink;
	protected boolean SpinnerFadePlayfield;
	protected boolean SpinnerFrequencyModulate;
	
	//colours
	protected Color songSelectActiveText;
	protected Color songSelectInactiveText;
	protected Color MenuGlow;
	
	protected Color starBreakAdditive;
	protected Color inputOverlayText;
	
	protected Color sliderBall;
	protected Color sliderTrackOverride;
	protected Color sliderBorder;
	protected Color spinnerBackground;
	
	protected Color Combo1;
	protected Color Combo2;
	protected Color Combo3;
	protected Color Combo4;
	protected Color Combo5;//5-8 are null by default
	protected Color Combo6;
	protected Color Combo7;
	protected Color Combo8;
	
	//fonts
	protected String hitCirclePrefix;
	protected int HitCircleOverlap;//negative allowed
	
	protected String scorePrefix;
	protected int scoreOverlap;//negative allowed
	
	protected String comboPrefix;
	protected int comboOverlap;//negative allowed
	
	//ctb
	protected Color hyperDash;
	protected Color hyperDashFruit;
	protected Color hyperDashAfterImage;
	
	protected List<ManiaIni> mania;
	
	protected final class ManiaIni{
		protected int keys;//non negative
		protected int columnStart;
		protected int columnRight;
		protected int[] columnSpacing;//n-1 numbers
		protected int[] columnWidth;//n numbers
		protected int[] columnLineWidth;//n+1 numbers
		protected double barlineHeight;
		protected int[] lightingNWidth;//n numbers
		protected int[] lightingLWidth;//n numbers
		protected int widthForNoteHeightScale;
		protected int hitPosition;
		protected int lightPosition;
		protected int scorePosition;
		protected int comboPosition;
		protected boolean judgementLine;
		protected int specialStyle;//0, 1 or 2
		protected int comboBurstStyle;//0, 1 or 2
		protected boolean splitStages;
		protected int stageSeparation;
		protected boolean separateScore;
		protected boolean keysUnderNotes;
		protected boolean upsideDown;
		//TODO finish all the options especially the column specific ones
	}
}

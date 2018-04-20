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
		protected boolean keyFlipWhenUpsideDown;
		protected boolean noteFlipWhenUpsideDown;
		protected int noteBodyStyle;//0, 1, 2
		protected Color colourColumnLine;//rgb(a)
		protected Color colourBarline;//rgb(a)
		protected Color colourJudgementLine;
		protected Color colourKeyWarning;
		protected Color colourHold;//rgb(a)
		protected Color colourBreak;
		
		protected Column[] columns;
		
		protected String stageLeft;
		protected String stageRight;
		protected String stageBottom;
		protected String stageHint;
		protected String stageLight;
		protected String lightingN;
		protected String lightingL;
		protected String warningArrow;
		
		protected String hit0;
		protected String hit50;
		protected String hit100;
		protected String hit200;
		protected String hit300;
		protected String hit300g;
		
		protected final class Column{
			protected int key;
			
			protected boolean keyFlipWhenUpsideDown;
			protected boolean keyFlipWhenUpsideDownD;
			protected boolean noteFlipWhenUpsideDown;
			protected boolean noteFlipWhenUpsideDownH;
			protected boolean noteFlipWhenUpsideDownL;
			protected boolean noteFlipWhenUpsideDownT;
			
			protected int noteBodyStyle;//0, 1, 2
			
			protected Color colour;//rgb(a)
			protected Color colourLight;//rgb
			
			protected String keyImage;
			protected String keyImageD;
			protected String noteImage;
			protected String noteImageH;
			protected String noteImageL;
			protected String noteImageT;
		}
	}
}

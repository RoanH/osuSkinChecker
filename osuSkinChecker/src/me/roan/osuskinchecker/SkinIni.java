package me.roan.osuskinchecker;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringJoiner;

public class SkinIni {
	//general
	protected String name = "-";
	protected String author = "-";
	protected Version version = Version.LATEST;
	protected boolean cursorExpand = true;
	protected boolean cursorCentre = true;
	protected boolean cursorRotate = true;
	protected boolean cursorTrailRotate = true;
	protected int animationFramerate = -1;//non negative
	
	//combo bursts
	protected boolean layeredHitSounds = true;
	protected boolean comboBurstRandom = false;
	protected int[] customComboBurstSounds = null;//list of ints, positive values only
	
	//standard
	protected boolean hitCircleOverlayAboveNumber = true;
	protected int sliderStyle = 2;//1 or 2
	protected boolean sliderBallFlip = false;
	protected boolean allowSliderBallTint = false;
	protected boolean spinnerNoBlink = false;
	protected boolean spinnerFadePlayfield = false;
	protected boolean spinnerFrequencyModulate = true;
	
	//colours
	protected Color songSelectActiveText = Color.BLACK;
	protected Color songSelectInactiveText = Color.WHITE;
	protected Color menuGlow = new Color(0, 78, 155);
	
	protected Color starBreakAdditive = new Color(255, 182, 193);
	protected Color inputOverlayText = Color.BLACK;
	
	protected Color sliderBall = new Color(2, 170, 255);
	protected Color sliderTrackOverride = null;
	protected Color sliderBorder = Color.BLACK;
	protected Color spinnerBackground = new Color(100, 100, 100);
	
	protected Color combo1 = new Color(255, 192, 0);
	protected Color combo2 = null;
	protected Color combo3 = null;
	protected Color combo4 = null;
	protected Color combo5 = null;
	protected Color combo6 = null;
	protected Color combo7 = null;
	protected Color combo8 = null;
	
	//fonts
	protected String hitCirclePrefix = "default";
	protected int hitCircleOverlap = -2;//negative allowed
	
	protected String scorePrefix = "score";
	protected int scoreOverlap = -2;//negative allowed
	
	protected String comboPrefix = "score";
	protected int comboOverlap = -2;//negative allowed
	
	//ctb
	protected Color hyperDash = Color.RED;
	protected Color hyperDashFruit = null;
	protected Color hyperDashAfterImage = null;
	
	protected ManiaIni[] mania = new ManiaIni[10];
	
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
	
	public void readIni(File file) throws IOException{//TODO catch unused fields to check for spelling mistakes
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		while((line = reader.readLine()) != null){
			String[] args = line.split(": ", 2);
			switch(args[0]){
			//[Mania]
			case "[Mania]":
				readMania(reader);
				break;
			//[General]
			case "Name":
				name = args[1];
				break;
			case "Author":
				author = args[1];
				break;
			case "Version":
				version = Version.fromString(args[1]);
				break;
			case "CursorExpand":
				cursorExpand = args[1].equals("1");
				break;
			case "CursorCentre":
				cursorCentre = args[1].equals("1");
				break;
			case "CursorRotate":
				cursorRotate = args[1].equals("1");
				break;
			case "CursorTrailRotate":
				cursorTrailRotate = args[1].equals("1");
				break;
			case "AnimationFramerate":
				animationFramerate = Math.max(0, Integer.parseInt(args[1]));
				break;
			case "LayeredHitSounds":
				layeredHitSounds = args[1].equals("1");
				break;
			case "ComboBurstRandom":
				comboBurstRandom = args[1].equals("1");
				break;
			case "CustomComboBurstSounds":
				//TODO how to parse this one
				break;
			case "HitCircleOverlayAboveNumber":
				hitCircleOverlayAboveNumber = args[1].equals("1");
				break;
			case "SliderStyle":
				sliderStyle = Math.min(2, Math.max(0, Integer.parseInt(args[1])));
				break;
			case "SliderBallFlip":
				sliderBallFlip = args[1].equals("1");
				break;
			case "AllowSliderBallTint":
				allowSliderBallTint = args[1].equals("1");
				break;
			case "SpinnerNoBlink":
				spinnerNoBlink = args[1].equals("1");
				break;
			case "SpinnerFadePlayfield":
				spinnerFadePlayfield = args[1].equals("1");
				break;
			case "SpinnerFrequencyModulate":
				spinnerFrequencyModulate = args[1].equals("1");
				break;
			//[Colours]
			case "SongSelectActiveText":
				songSelectActiveText = parseColor(args[1]);
				break;
			case "SongSelectInactiveText":
				songSelectInactiveText = parseColor(args[1]);
				break;
			case "MenuGlow":
				menuGlow = parseColor(args[1]);
				break;
			case "StarBreakAdditive":
				starBreakAdditive = parseColor(args[1]);
				break;
			case "InputOverlayText":
				inputOverlayText = parseColor(args[1]);
				break;
			case "SliderBall":
				sliderBall = parseColor(args[1]);
				break;
			case "SliderTrackOverride":
				sliderTrackOverride = parseColor(args[1]);
				break;
			case "SliderBorder":
				sliderBorder = parseColor(args[1]);
				break;
			case "SpinnerBackground":
				spinnerBackground = parseColor(args[1]);
				break;
			case "Combo1":
				combo1 = parseColor(args[1]);
				break;
			case "Combo2":
				combo2 = parseColor(args[1]);
				break;
			case "Combo3":
				combo3 = parseColor(args[1]);
				break;
			case "Combo4":
				combo4 = parseColor(args[1]);
				break;
			case "Combo5":
				combo5 = parseColor(args[1]);
				break;
			case "Combo6":
				combo6 = parseColor(args[1]);
				break;
			case "Combo7":
				combo7 = parseColor(args[1]);
				break;
			case "Combo8":
				combo8 = parseColor(args[1]);
				break;
			//[Fonts]
			case "HitCirclePrefix":
				hitCirclePrefix = args[1];
				break;
			case "HitCircleOverlap":
				hitCircleOverlap = Integer.parseInt(args[1]);
				break;
			case "ScorePrefix":
				scorePrefix = args[1];
				break;
			case "ScoreOverlap":
				scoreOverlap = Integer.parseInt(args[1]);
				break;
			case "ComboPrefix":
				comboPrefix = args[1];
				break;
			case "ComboOverlap":
				comboOverlap = Integer.parseInt(args[1]);
				break;
			//[CatchTheBeat]
			case "HyperDash":
				hyperDash = parseColor(args[1]);
				break;
			case "HyperDashFruit":
				hyperDashFruit = parseColor(args[1]);
				break;
			case "HyperDashAfterImage":
				hyperDashAfterImage = parseColor(args[1]);
				break;
			//[Mania]
			}
			
			
		}
	}
	
	private void readMania(BufferedReader reader){
		
	}
	
	private Color parseColor(String arg){
		String[] args = arg.split(",");
		if(args.length <= 3){
			return new Color(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		}else{
			return new Color(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		}
	}
	
	public void writeIni(File file) throws FileNotFoundException{
		PrintWriter writer = new PrintWriter(new FileOutputStream(file));
		
		writer.println("[General]");
		writer.println("Name: " + name);
		writer.println("Author: " + author);
		writer.println("Version: " + version.name);
		writer.println();
		writer.println("CursorExpand: " + (cursorExpand ? 1 : 0));
		writer.println("CursorCentre: " + (cursorCentre ? 1 : 0));
		writer.println("CursorRotate: " + (cursorRotate ? 1 : 0));
		writer.println("CursorTrailRotate: " + (cursorTrailRotate ? 1 : 0));
		if(animationFramerate != -1){
			writer.println("AnimationFramerate: " + animationFramerate);
		}
		writer.println("LayeredHitSounds: " + (layeredHitSounds ? 1 : 0));
		writer.println("ComboBurstRandom: " + (comboBurstRandom ? 1 : 0));
		if(customComboBurstSounds != null){
			writer.println("CustomComboBurstSounds: " + arrayToList(customComboBurstSounds));
		}
		writer.println("HitCircleOverlayAboveNumber: " + (hitCircleOverlayAboveNumber ? 1 : 0));
		writer.println("SliderStyle: " + sliderStyle);
		
		writer.flush();
		writer.close();
	}
	
	private static final String arrayToList(int[] array){
		StringJoiner joiner = new StringJoiner(",");
		for(int i : array){
			joiner.add(String.valueOf(i));
		}
		return joiner.toString();
	}
	
	protected enum Version{
		V1("1", "(old style)"),
		V2("2", "(basic new style)"),
		V25("2.5", "(derrived new style)"),
		LATEST("latest", "(for personal skins)");
		
		public final String name;
		public final String extra;
		
		private Version(String name, String extra){
			this.name = name;
			this.extra = extra;
		}
		
		private static Version fromString(String str){
			switch(str){
			case "1":
				return V1;
			case "2":
				return V2;
			case "latest":
				return LATEST;
				default:
					if(str.startsWith("2.")){
						return V25;
					}else{
						return null;
					}
			}
		}
		
		@Override
		public String toString(){
			return name + " " + extra;
		}
	}
}

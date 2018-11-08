package me.roan.osuskinchecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringJoiner;

import me.roan.osuskinchecker.SkinIni.ManiaIni.Column;

public class SkinIni{
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
	protected Color songSelectActiveText = new Color(0, 0, 0);
	protected Color songSelectInactiveText = new Color(255, 255, 255);
	protected Color menuGlow = new Color(0, 78, 155);

	protected Color starBreakAdditive = new Color(255, 182, 193);
	protected Color inputOverlayText = new Color(0, 0, 0);

	protected Color sliderBall = new Color(2, 170, 255);
	protected Color sliderTrackOverride = null;
	protected Color sliderBorder = new Color(0, 0, 0);
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
	protected Color hyperDash = new Color(255, 0, 0);
	protected Color hyperDashFruit = null;
	protected Color hyperDashAfterImage = null;

	protected ManiaIni[] mania = new ManiaIni[10];

	public final void createManiaConfiguration(int keys){
		mania[keys - 1] = new ManiaIni(keys);
	}

	protected static final class ManiaIni{
		protected int keys;//non negative
		protected double columnStart = 136.0D;
		protected double columnRight = 19.0D;
		protected double[] columnSpacing;//n-1 numbers
		protected double[] columnWidth;//n numbers
		protected double[] columnLineWidth;//n+1 numbers
		protected double barlineHeight = 1.2D;
		protected double[] lightingNWidth = null;//n numbers
		protected double[] lightingLWidth = null;//n numbers
		protected double widthForNoteHeightScale = -1.0D;
		protected int hitPosition = 402;
		protected int lightPosition = 413;
		protected int scorePosition = 325;
		protected int comboPosition = 111;
		protected boolean judgementLine = true;
		protected int specialStyle = 0;//0, 1 or 2
		protected int comboBurstStyle = 1;//0, 1 or 2
		protected Boolean splitStages = null;
		protected int stageSeparation = 40;
		protected boolean separateScore = true;
		protected boolean keysUnderNotes = false;
		protected boolean upsideDown = false;
		protected boolean keyFlipWhenUpsideDown = true;
		protected boolean noteFlipWhenUpsideDown = true;
		protected int noteBodyStyle = 1;//0, 1, 2
		protected Color colourColumnLine = new Color(255, 255, 255, 255);
		protected Color colourBarline = new Color(255, 255, 255, 255);
		protected Color colourJudgementLine = new Color(255, 255, 255);
		protected Color colourKeyWarning = new Color(0, 0, 0);
		protected Color colourHold = new Color(255, 191, 51, 255);
		protected Color colourBreak = new Color(255, 0, 0);

		protected Column[] columns;

		protected String stageLeft = "mania-stage-left";
		protected String stageRight = "mania-stage-right";
		protected String stageBottom = "mania-stage-bottom";
		protected String stageHint = "mania-stage-hint";
		protected String stageLight = "mania-stage-light";
		protected String lightingN = "LightingN";
		protected String lightingL = "LightingL";
		protected String warningArrow = "mania-warningarrow";

		protected String hit0 = "mania-hit0";
		protected String hit50 = "mania-hit50";
		protected String hit100 = "mania-hit100";
		protected String hit200 = "mania-hit200";
		protected String hit300 = "mania-hit300";
		protected String hit300g = "mania-hit300g";

		private ManiaIni(int keys){
			this.keys = keys;
			columnSpacing = fillArray(keys - 1, 0.0D);
			columnWidth = fillArray(keys, 30.0D);
			columnLineWidth = fillArray(keys + 1, 2.0D);
			columns = new Column[keys];
			for(int i = 0; i < keys; i++){
				columns[i] = new Column();
			}
		}

		private static final double[] fillArray(int len, double value){
			double[] array = new double[len];
			for(int i = 0; i < len; i++){
				array[i] = value;
			}
			return array;
		}

		protected static final class Column{
			protected int key;

			protected Boolean keyFlipWhenUpsideDown = null;
			protected Boolean keyFlipWhenUpsideDownD = null;
			protected Boolean noteFlipWhenUpsideDown = null;
			protected Boolean noteFlipWhenUpsideDownH = null;
			protected Boolean noteFlipWhenUpsideDownL = null;
			protected Boolean noteFlipWhenUpsideDownT = null;

			protected int noteBodyStyle = -1;//0, 1, 2, -1=undefined

			protected Color colour = new Color(0, 0, 0, 255);
			protected Color colourLight = new Color(255, 255, 255);

			protected String keyImage = null;
			protected String keyImageD = null;
			protected String noteImage = null;
			protected String noteImageH = null;
			protected String noteImageL = null;
			protected String noteImageT = null;
		}
	}

	public void readIni(File file) throws IOException{//TODO catch unused fields to check for spelling mistakes
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		while((line = reader.readLine()) != null){
			if(line.trim().isEmpty() || line.startsWith("//")){
				continue;
			}
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
			}
		}
	}

	private void readMania(BufferedReader reader) throws IOException{
		String line;
		do{
			line = reader.readLine();
		}while(line.trim().isEmpty() || line.startsWith("//"));
		int keys = Integer.parseInt(line.trim().substring(5).trim());
		
		ManiaIni ini = new ManiaIni(keys);
		mania[keys - 1] = ini;
		
		while((line = reader.readLine()) != null){
			if(line.trim().isEmpty() || line.startsWith("//")){
				continue;
			}
			String[] args = line.split(":");
			args[1] = args[1].trim();
			switch(args[0]){
			case "ColumnStart":
				ini.columnStart = Math.max(0.0D, Double.parseDouble(args[1]));
				break;
			case "ColumnRight":
				ini.columnRight = Math.max(0.0D, Double.parseDouble(args[1]));
				break;
			case "ColumnSpacing":
				ini.columnSpacing = parseList(args[1], keys - 1);
				break;
			case "ColumnWidth":
				ini.columnWidth = parseList(args[1], keys);
				break;
			case "ColumnLineWidth":
				ini.columnLineWidth = parseList(args[1], keys + 1);
				break;
			case "BarlineHeight":
				ini.barlineHeight = Math.max(0.0D, Double.parseDouble(args[1]));
				break;
			case "LightingNWidth":
				ini.lightingNWidth = parseList(args[1], keys);
				break;
			case "LightingLWidth":
				ini.lightingLWidth = parseList(args[1], keys);
				break;
			case "WidthForNoteHeightScale":
				ini.widthForNoteHeightScale = Math.max(0.0D, Double.parseDouble(args[1]));
				break;
			case "HitPosition":
				ini.hitPosition = Math.max(0, Integer.parseInt(args[1]));
				break;
			case "LightPosition":
				ini.lightPosition = Math.max(0, Integer.parseInt(args[1]));
				break;
			case "ScorePosition":
				ini.scorePosition = Math.max(0, Integer.parseInt(args[1]));
				break;
			case "ComboPosition":
				ini.comboPosition = Math.max(0, Integer.parseInt(args[1]));
				break;
			case "JudgementLine":
				ini.judgementLine = args[1].equals("1");
				break;
			case "SpecialStyle":
				ini.specialStyle = Math.max(0, Math.min(2, Integer.parseInt(args[1])));
				break;
			case "ComboBurstStyle":
				ini.comboBurstStyle = Math.max(0, Math.min(2, Integer.parseInt(args[1])));
				break;
			case "SplitStages":
				ini.splitStages = args[1].equals("1");
				break;
			case "StageSeparation":
				ini.stageSeparation = Math.max(0, Integer.parseInt(args[1]));
				break;
			case "SeparateScore":
				ini.separateScore = args[1].equals("1");
				break;
			case "KeysUnderNotes":
				ini.keysUnderNotes = args[1].equals("1");
				break;
			case "UpsideDown":
				ini.upsideDown = args[1].equals("1");
				break;
			case "KeyFlipWhenUpsideDown":
				ini.keyFlipWhenUpsideDown = args[1].equals("1");
				break;
			case "NoteFlipWhenUpsideDown":
				ini.noteFlipWhenUpsideDown = args[1].equals("1");
				break;
			}
		}
	}
	
	private double[] parseList(String data, int expected){
		String[] args = data.split(",");
		if(args.length != expected){
			throw new IllegalArgumentException("Illegal number of arguments on line: " + data);
		}
		double[] values = new double[args.length];
		for(int i = 0; i < values.length; i++){
			values[i] = Math.max(0.0D, Double.parseDouble(args[i].trim()));
		}
		return values;
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
		writer.println("SliderBallFlip: " + (sliderBallFlip ? 1 : 0));
		writer.println("AllowSliderBallTint" + (allowSliderBallTint ? 1 : 0));
		writer.println("SpinnerNoBlink" + (spinnerNoBlink ? 1 : 0));
		writer.println("SpinnerFadePlayfield" + (spinnerFadePlayfield ? 1 : 0));
		writer.println("SpinnerFrequencyModulate" + (spinnerFrequencyModulate ? 1 : 0));
		writer.println();

		writer.println("[Colours]");
		writer.println("SongSelectActiveText: " + rgb(songSelectActiveText));
		writer.println("SongSelectInactiveText: " + rgb(songSelectInactiveText));
		writer.println("MenuGlow: " + rgb(menuGlow));
		writer.println("StarBreakAdditive: " + rgb(starBreakAdditive));
		writer.println("InputOverlayText: " + rgb(inputOverlayText));
		writer.println("SliderBall: " + rgb(sliderBall));
		if(sliderTrackOverride != null){
			writer.println("SliderTrackOverride: " + rgb(sliderTrackOverride));
		}
		writer.println("SliderBorder: " + rgb(sliderBorder));
		writer.println("SpinnerBackground: " + rgb(spinnerBackground));
		writer.println("Combo1: " + rgb(combo1));
		if(combo2 != null){
			writer.println("Combo2: " + rgb(combo2));
		}
		if(combo3 != null){
			writer.println("Combo3: " + rgb(combo3));
		}
		if(combo4 != null){
			writer.println("Combo4: " + rgb(combo4));
		}
		if(combo5 != null){
			writer.println("Combo5: " + rgb(combo5));
		}
		if(combo6 != null){
			writer.println("Combo6: " + rgb(combo6));
		}
		if(combo7 != null){
			writer.println("Combo7: " + rgb(combo7));
		}
		if(combo8 != null){
			writer.println("Combo8: " + rgb(combo8));
		}
		writer.println();

		writer.println("[Fonts]");
		writer.println("HitCirclePrefix: " + hitCirclePrefix);
		writer.println("HitCircleOverlap: " + hitCircleOverlap);
		writer.println("ScorePrefix: " + scorePrefix);
		writer.println("ScoreOverlap: " + scoreOverlap);
		writer.println("ComboPrefix: " + comboPrefix);
		writer.println("ComboOverlap: " + comboOverlap);
		writer.println();

		writer.println("[CatchTheBeat]");
		writer.println("HyperDash: " + hyperDash);
		if(hyperDashFruit != null){
			writer.println("HyperDashFruit: " + hyperDashFruit);
		}
		if(hyperDashAfterImage != null){
			writer.println("HyperDashAfterImage: " + hyperDashAfterImage);
		}
		writer.println();

		for(ManiaIni ini : mania){
			if(ini != null){
				writer.println("[Mania]");
				writer.println("Keys: " + ini.keys);
				writer.println("ColumnStart: " + ini.columnStart);
				writer.println("ColumnRight: " + ini.columnRight);
				if(ini.keys != 1){
					writer.println("ColumnSpacing: " + arrayToList(ini.columnSpacing));
				}
				writer.println("ColumnWidth: " + arrayToList(ini.columnWidth));
				writer.println("ColumnLineWidth: " + arrayToList(ini.columnLineWidth));
				writer.println("BarlineHeight: " + ini.barlineHeight);
				if(ini.lightingNWidth != null){
					writer.println("LightingNWidth: " + arrayToList(ini.lightingNWidth));
				}
				if(ini.lightingLWidth != null){
					writer.println("LightingLWidth: " + arrayToList(ini.lightingLWidth));
				}
				if(ini.widthForNoteHeightScale != -1.0D){
					writer.println("WidthForNoteHeightScale: " + ini.widthForNoteHeightScale);
				}
				writer.println("HitPosition: " + ini.hitPosition);
				writer.println("LightPosition: " + ini.lightPosition);
				writer.println("ScorePosition: " + ini.scorePosition);
				writer.println("ComboPosition: " + ini.comboPosition);
				writer.println("JudgementLine: " + (ini.judgementLine ? 1 : 0));
				writer.println("SpecialStyle: " + ini.specialStyle);
				writer.println("ComboBurstStyle: " + ini.comboBurstStyle);
				if(ini.splitStages != null){
					writer.println("SplitStages: " + (ini.splitStages ? 1 : 0));
				}
				writer.println("StageSeparation: " + ini.stageSeparation);
				writer.println("SeparateScore: " + (ini.separateScore ? 1 : 0));
				writer.println("KeysUnderNotes: " + (ini.keysUnderNotes ? 1 : 0));
				writer.println("UpsideDown: " + (ini.upsideDown ? 1 : 0));
				writer.println("KeyFlipWhenUpsideDown: " + (ini.keyFlipWhenUpsideDown ? 1 : 0));
				writer.println("NoteFlipWhenUpsideDown: " + (ini.noteFlipWhenUpsideDown ? 1 : 0));
				writer.println("NoteBodyStyle: " + ini.noteBodyStyle);
				writer.println("ColourColumnLine: " + rgba(ini.colourColumnLine));
				writer.println("ColourBarline: " + rgba(ini.colourBarline));
				writer.println("ColourJudgementLine: " + rgb(ini.colourJudgementLine));
				writer.println("ColourKeyWarning: " + rgb(ini.colourKeyWarning));
				writer.println("ColourHold: " + rgba(ini.colourHold));
				writer.println("ColourBreak: " + rgb(ini.colourBreak));
				writer.println("StageLeft: " + ini.stageLeft);
				writer.println("StageRight: " + ini.stageRight);
				writer.println("StageBottom: " + ini.stageBottom);
				writer.println("StageHint: " + ini.stageHint);
				writer.println("StageLight: " + ini.stageLight);
				writer.println("LightingN: " + ini.lightingN);
				writer.println("LightingL: " + ini.lightingL);
				writer.println("WarningArrow: " + ini.warningArrow);
				writer.println("Hit0: " + ini.hit0);
				writer.println("Hit50: " + ini.hit50);
				writer.println("Hit100: " + ini.hit100);
				writer.println("Hit200: " + ini.hit200);
				writer.println("Hit300: " + ini.hit300);
				writer.println("Hit300g: " + ini.hit300g);
				
				for(int i = 0; i < ini.columns.length; i++){
					Column col = ini.columns[i];

					if(col.keyFlipWhenUpsideDown != null){
						writer.println("KeyFlipWhenUpsideDown" + col.key + ": " + (col.keyFlipWhenUpsideDown ? 1 : 0));
					}
					if(col.keyFlipWhenUpsideDownD != null){
						writer.println("KeyFlipWhenUpsideDown" + col.key + "D: " + (col.keyFlipWhenUpsideDownD ? 1 : 0));
					}
					if(col.noteFlipWhenUpsideDown != null){
						writer.println("NoteFlipWhenUpsideDown" + col.key + ": " + (col.noteFlipWhenUpsideDown ? 1 : 0));
					}
					if(col.noteFlipWhenUpsideDownH != null){
						writer.println("NoteFlipWhenUpsideDown" + col.key + "H: " + (col.noteFlipWhenUpsideDownH ? 1 : 0));
					}
					if(col.noteFlipWhenUpsideDownL != null){
						writer.println("NoteFlipWhenUpsideDown" + col.key + "L: " + (col.noteFlipWhenUpsideDownL ? 1 : 0));
					}
					if(col.noteFlipWhenUpsideDownT != null){
						writer.println("NoteFlipWhenUpsideDown" + col.key + "T: " + (col.noteFlipWhenUpsideDownT ? 1 : 0));
					}
					if(col.noteBodyStyle != -1){
						writer.println("NoteBodyStyle" + col.key + ": " + col.noteBodyStyle);
					}
					if(col.colour != null){
						writer.println("Colour" + col.key + ": " + rgba(col.colour));
					}
					if(col.colourLight != null){
						writer.println("ColourLight" + col.key + ": " + rgb(col.colourLight));
					}
					if(col.keyImage != null){
						writer.println("KeyImage" + col.key + ": " + col.keyImage);
					}
					if(col.keyImageD != null){
						writer.println("KeyImage" + col.key + "D: " + col.keyImageD);
					}
					if(col.noteImage != null){
						writer.println("NoteImage" + col.key + ": " + col.noteImage);
					}
					if(col.noteImageH != null){
						writer.println("NoteImage" + col.key + "H: " + col.noteImageH);
					}
					if(col.noteImageL != null){
						writer.println("NoteImage" + col.key + "L: " + col.noteImageL);
					}
					if(col.noteImageT != null){
						writer.println("NoteImage" + col.key + "T: " + col.noteImageT);
					}
				}
				
				writer.println();
			}
		}

		writer.flush();
		writer.close();
	}

	private static final String rgb(Color color){
		return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
	}

	private static final String rgba(Color color){
		if(!color.hasAlpha()){
			return rgb(color);
		}else{
			return rgb(color) + "," + color.getAlpha();
		}
	}

	private static final String arrayToList(double[] array){
		StringJoiner joiner = new StringJoiner(",");
		for(double d : array){
			joiner.add(String.valueOf(d));
		}
		return joiner.toString();
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

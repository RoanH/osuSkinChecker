package me.roan.osuskinchecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class SkinIni{
	private static boolean usedDefault = false;
	private List<Section> data;
	
	//general
	protected final Setting<String> name = new Setting<String>("Name", "-");
	protected final Setting<String> author = new Setting<String>("Author", "-");
	protected final Setting<Version> version = new Setting<Version>("Version", Version.V1);
	protected final Setting<Boolean> cursorExpand = new Setting<Boolean>("CursorExpand", true);
	protected final Setting<Boolean> cursorCentre = new Setting<Boolean>("CursorCentre", true);
	protected final Setting<Boolean> cursorRotate = new Setting<Boolean>("CursorRotate", true);
	protected final Setting<Boolean> cursorTrailRotate = new Setting<Boolean>("CursorTrailRotate", true);
	protected final Setting<Integer> animationFramerate = new Setting<Integer>("AnimationFramerate", false, 1);//non negative

	//combo bursts
	protected final Setting<Boolean> layeredHitSounds = new Setting<Boolean>("LayeredHitSounds", true);
	protected final Setting<Boolean> comboBurstRandom = new Setting<Boolean>("ComboBurstRandom", false);
	protected final Setting<String> customComboBurstSounds = new Setting<String>("CustomComboBurstSounds", false, "");//list of ints, positive values only

	//standard
	protected final Setting<Boolean> hitCircleOverlayAboveNumber = new Setting<Boolean>("HitCircleOverlayAboveNumber", true);
	protected final Setting<SliderStyle> sliderStyle = new Setting<SliderStyle>("SliderStyle", SliderStyle.GRADIENT);//1 or 2
	protected final Setting<Boolean> sliderBallFlip = new Setting<Boolean>("SliderBallFlip", false);
	protected final Setting<Boolean> allowSliderBallTint = new Setting<Boolean>("AllowSliderBallTint", false);
	protected final Setting<Boolean> spinnerNoBlink = new Setting<Boolean>("SpinnerNoBlink", false);
	protected final Setting<Boolean> spinnerFadePlayfield = new Setting<Boolean>("SpinnerFadePlayfield", false);
	protected final Setting<Boolean> spinnerFrequencyModulate = new Setting<Boolean>("SpinnerFrequencyModulate", true);

	//colours
	protected final Setting<Colour> songSelectActiveText = new Setting<Colour>("SongSelectActiveText", new Colour(0, 0, 0));
	protected final Setting<Colour> songSelectInactiveText = new Setting<Colour>("SongSelectInactiveText", new Colour(255, 255, 255));
	protected final Setting<Colour> menuGlow = new Setting<Colour>("MenuGlow", new Colour(0, 78, 155));
	
	protected final Setting<Colour> starBreakAdditive = new Setting<Colour>("StarBreakAdditive", new Colour(255, 182, 193));
	protected final Setting<Colour> inputOverlayText = new Setting<Colour>("InputOverlayText", new Colour(0, 0, 0));
	
	protected final Setting<Colour> sliderBall = new Setting<Colour>("SliderBall", new Colour(2, 170, 255));
	protected final Setting<Colour> sliderTrackOverride = new Setting<Colour>("SliderTrackOverride", new Colour(0, 0, 0));
	protected final Setting<Colour> sliderBorder = new Setting<Colour>("SliderBorder", new Colour(0, 0, 0));
	protected final Setting<Colour> spinnerBackground = new Setting<Colour>("SpinnerBackground", new Colour(100, 100, 100));
	
	protected final Setting<Colour> combo1 = new Setting<Colour>("Combo1", new Colour(255, 192, 0));
	protected final Setting<Colour> combo2 = new Setting<Colour>("Combo2", new Colour(0, 0, 0));
	protected final Setting<Colour> combo3 = new Setting<Colour>("Combo3", new Colour(0, 0, 0));
	protected final Setting<Colour> combo4 = new Setting<Colour>("Combo4", new Colour(0, 0, 0));
	protected final Setting<Colour> combo5 = new Setting<Colour>("Combo5", new Colour(0, 0, 0));
	protected final Setting<Colour> combo6 = new Setting<Colour>("Combo6", new Colour(0, 0, 0));
	protected final Setting<Colour> combo7 = new Setting<Colour>("Combo7", new Colour(0, 0, 0));
	protected final Setting<Colour> combo8 = new Setting<Colour>("Combo8", new Colour(0, 0, 0));

	//fonts
	protected final Setting<String> hitCirclePrefix = new Setting<String>("HitCirclePrefix", "default");
	protected final Setting<Integer> hitCircleOverlap = new Setting<Integer>("HitCircleOverlap", -2);//negative allowed

	protected final Setting<String> scorePrefix = new Setting<String>("ScorePrefix", "score");
	protected final Setting<Integer> scoreOverlap = new Setting<Integer>("ScoreOverlap", -2);//negative allowed

	protected final Setting<String> comboPrefix = new Setting<String>("ComboPrefix", "score");
	protected final Setting<Integer> comboOverlap = new Setting<Integer>("ComboOverlap", -2);//negative allowed

	//ctb
	protected final Setting<Colour> hyperDash = new Setting<Colour>("HyperDash", new Colour(255, 0, 0));
	protected final Setting<Colour> hyperDashFruit = new Setting<Colour>("HyperDashFruit", new Colour(0, 0, 0));
	protected final Setting<Colour> hyperDashAfterImage = new Setting<Colour>("HyperDashAfterImage", new Colour(0, 0, 0));

	protected final ManiaIni[] mania = new ManiaIni[ManiaIni.MAX_KEYS];

	public final void createManiaConfiguration(int keys){
		mania[keys - 1] = new ManiaIni(keys);
	}

	protected static final class ManiaIni{
		protected static final int MAX_KEYS = 10;
		protected int keys;//non negative
		protected final Setting<Double> columnStart = new Setting<Double>("ColumnStart", 136.0D);
		protected final Setting<Double> columnRight = new Setting<Double>("ColumnRight", 19.0D);
		protected final Setting<double[]> columnSpacing = new Setting<double[]>("ColumnSpacing", null);//n-1 numbers
		protected final Setting<double[]> columnWidth = new Setting<double[]>("ColumnWidth", null);//n numbers
		protected final Setting<double[]> columnLineWidth = new Setting<double[]>("ColumnLineWidth", null);//n+1 numbers
		protected final Setting<Double> barlineHeight = new Setting<Double>("BarlineHeight", 1.2D);
		protected final Setting<double[]> lightingNWidth = new Setting<double[]>("LightingNWidth", null);//n numbers
		protected final Setting<double[]> lightingLWidth = new Setting<double[]>("LightingLWidth", null);//n numbers
		protected final Setting<Double> widthForNoteHeightScale = new Setting<Double>("WidthForNoteHeightScale", -1.0D);
		protected final Setting<Integer> hitPosition = new Setting<Integer>("HitPosition", 402);
		protected final Setting<Integer> lightPosition = new Setting<Integer>("LightPosition", 413);
		protected final Setting<Integer> scorePosition = new Setting<Integer>("ScorePosition", 325);
		protected final Setting<Integer> comboPosition = new Setting<Integer>("ComboPosition", 111);
		protected final Setting<Boolean> judgementLine = new Setting<Boolean>("JudgementLine", true);
		protected final Setting<SpecialStyle> specialStyle = new Setting<SpecialStyle>("SpecialStyle", SpecialStyle.NONE);//0, 1 or 2
		protected final Setting<ComboBurstStyle> comboBurstStyle = new Setting<ComboBurstStyle>("ComboBurstStyle", ComboBurstStyle.RIGHT);//0, 1 or 2
		protected final Setting<Boolean> splitStages = new Setting<Boolean>("SplitStages", false, true);
		protected final Setting<Double> stageSeparation = new Setting<Double>("StageSeparation", 40.0D);
		protected final Setting<Boolean> separateScore = new Setting<Boolean>("SeparationScore", true);
		protected final Setting<Boolean> keysUnderNotes = new Setting<Boolean>("KeysUnderNotes", false);
		protected final Setting<Boolean> upsideDown = new Setting<Boolean>("UpsideDown", false);
		protected final Setting<Boolean> keyFlipWhenUpsideDown = new Setting<Boolean>("KeyFlipWhenUpsideDown", true);
		protected final Setting<Boolean> noteFlipWhenUpsideDown = new Setting<Boolean>("NoteFlipWhenUpsideDown", true);
		protected final Setting<NoteBodyStyle> noteBodyStyle = new Setting<NoteBodyStyle>("NoteBodyStyle", NoteBodyStyle.CASCADINGTH);//0, 1, 2
		protected final Setting<Colour> colourColumnLine = new Setting<Colour>("ColourColumnLine", new Colour(255, 255, 255, 255));
		protected final Setting<Colour> colourBarline = new Setting<Colour>("ColourBarline", new Colour(255, 255, 255, 255));
		protected final Setting<Colour> colourJudgementLine = new Setting<Colour>("ColourJudgementLine", new Colour(255, 255, 255));
		protected final Setting<Colour> colourKeyWarning = new Setting<Colour>("ColourKeyWarning", new Colour(0, 0, 0));
		protected final Setting<Colour> colourHold = new Setting<Colour>("ColourHold", new Colour(255, 191, 51, 255));
		protected final Setting<Colour> colourBreak = new Setting<Colour>("ColourBreak", new Colour(255, 0, 0));

		protected Column[] columns;

		protected final Setting<String> stageLeft = new Setting<String>("StageLeft", "mania-stage-left");
		protected final Setting<String> stageRight = new Setting<String>("StageRight", "mania-stage-right");
		protected final Setting<String> stageBottom = new Setting<String>("StageBottom", "mania-stage-bottom");
		protected final Setting<String> stageHint = new Setting<String>("StageHint", "mania-stage-hint");
		protected final Setting<String> stageLight = new Setting<String>("StageLight", "mania-stage-light");
		protected final Setting<String> lightingN = new Setting<String>("LightingN", "LightingN");
		protected final Setting<String> lightingL = new Setting<String>("LightingL", "LightingL");
		protected final Setting<String> warningArrow = new Setting<String>("WarningArrow", "mania-warningarrow");

		protected final Setting<String> hit0 = new Setting<String>("Hit0", "mania-hit0");
		protected final Setting<String> hit50 = new Setting<String>("Hit50", "mania-hit50");
		protected final Setting<String> hit100 = new Setting<String>("Hit100", "mania-hit100");
		protected final Setting<String> hit200 = new Setting<String>("Hit200", "mania-hit200");
		protected final Setting<String> hit300 = new Setting<String>("Hit300", "mania-hit300");
		protected final Setting<String> hit300g = new Setting<String>("Hit300g", "mania-hit300g");

		private ManiaIni(int keys){
			this.keys = keys;
			columnSpacing.update(fillArray(keys - 1, 0.0D));
			columnWidth.update(fillArray(keys, 30.0D));
			columnLineWidth.update(fillArray(keys + 1, 2.0D));
			lightingNWidth.update(fillArray(keys, 0.0D));
			lightingLWidth.update(fillArray(keys, 0.0D));
			columns = new Column[keys];
			for(int i = 0; i < keys; i++){
				columns[i] = new Column(keys + 1);
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
			protected final int key;

			protected final Setting<Boolean> keyFlipWhenUpsideDown;
			protected final Setting<Boolean> keyFlipWhenUpsideDownD;
			protected final Setting<Boolean> noteFlipWhenUpsideDown;
			protected final Setting<Boolean> noteFlipWhenUpsideDownH;
			protected final Setting<Boolean> noteFlipWhenUpsideDownL;
			protected final Setting<Boolean> noteFlipWhenUpsideDownT;

			protected final Setting<NoteBodyStyle> noteBodyStyle;

			protected final Setting<Colour> colour;
			protected final Setting<Colour> colourLight;

			protected final Setting<String> keyImage;
			protected final Setting<String> keyImageD;
			protected final Setting<String> noteImage;
			protected final Setting<String> noteImageH;
			protected final Setting<String> noteImageL;
			protected final Setting<String> noteImageT;
			
			private Column(int key){
				this.key = key;
				
				keyFlipWhenUpsideDown = new Setting<Boolean>("KeyFlipWhenUpsideDown" + key, false, true);
				keyFlipWhenUpsideDownD = new Setting<Boolean>("KeyFlipWhenUpsideDown" + key + "D", false, true);
				noteFlipWhenUpsideDown = new Setting<Boolean>("NoteFlipWhenUpsideDown" + key, false, true);
				noteFlipWhenUpsideDownH = new Setting<Boolean>("NoteFlipWhenUpsideDown" + key + "H", false, true);
				noteFlipWhenUpsideDownL = new Setting<Boolean>("NoteFlipWhenUpsideDown" + key + "L", false, true);
				noteFlipWhenUpsideDownT = new Setting<Boolean>("NoteFlipWhenUpsideDown" + key + "T", false, true);

				noteBodyStyle = new Setting<NoteBodyStyle>("NoteBodyStyle" + key, false, NoteBodyStyle.CASCADINGTH);//0, 1, 2

				colour = new Setting<Colour>("Colour" + key, new Colour(0, 0, 0, 255));
				colourLight = new Setting<Colour>("ColourLight" + key, new Colour(255, 255, 255));

				keyImage = new Setting<String>("KeyImage" + key, false, "");
				keyImageD = new Setting<String>("KeyImage" + key + "D", false, "");
				noteImage = new Setting<String>("NoteImage" + key, false, "");
				noteImageH = new Setting<String>("NoteImage" + key + "H", false, "");
				noteImageL = new Setting<String>("NoteImage" + key + "L", false, "");
				noteImageT = new Setting<String>("NoteImage" + key + "T", false, "");
			}
		}
	}
	
	public void readIni(File file) throws IOException{
		usedDefault = false;
		data = new ArrayList<Section>();
		Section section = new Section(null);
		data.add(section);
		Pattern header = Pattern.compile("\\[.+\\]");
		ManiaIni maniaIni = null;
		Setting.singleUpdateMode = true;
		
		String line = null;
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
			while((line = reader.readLine()) != null){
				if(header.matcher(line.trim()).matches()){
					section = new Section(line.trim());
					data.add(section);
					if(section.isMania()){
						while((line = reader.readLine()) != null){
							if(line.trim().isEmpty() || line.startsWith("//")){
								section.data.add(new Comment(line));
							}else{
								if(line.startsWith("Keys:")){
									try{
										int keys = Integer.parseInt(line.substring(5).trim());
										if(keys >= 1 && keys <= ManiaIni.MAX_KEYS){
											mania[keys - 1] = (maniaIni = new ManiaIni(keys));
											break;
										}else{
											throw new IllegalArgumentException("Unsupported key count: " + keys);
										}
									}catch(NumberFormatException e){
										throw new IllegalArgumentException("Mania key count is not a number!");
									}
								}else{
									throw new IllegalArgumentException("First field in a mania section is not the key count!");
								}
							}
						}
						if(line == null){
							throw new IllegalArgumentException("Mania config does not define a key count!");
						}
					}
				}else if(section.isMania()){
					section.data.add(parseMania(maniaIni, line));
				}else{
					section.data.add(parse(line));
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
			throw new IllegalArgumentException("Line: " + line, e);
		}
		
		Setting.singleUpdateMode = false;
		
		if(usedDefault){
			JOptionPane.showMessageDialog(SkinChecker.frame, "Skin.ini fields were found that couldn't be parsed. Default values were used.", "Skin Checker", JOptionPane.WARNING_MESSAGE);
		}
	}

	public Setting<?> parse(String line) throws IOException{
		if(line.trim().isEmpty() || line.startsWith("//")){
			return new Comment(line);
		}
		String[] args = line.split(":", 2);
		args[1] = args[1].trim();
		switch(args[0]){
		//[General]
		case "Name":
			return name.update(args[1]);
		case "Author":
			return author.update(args[1]);
		case "Version":
			return version.update(Version.fromString(args[1]));
		case "CursorExpand":
			return parseBoolean(cursorExpand, args[1]);
		case "CursorCentre":
			return parseBoolean(cursorCentre, args[1]);
		case "CursorRotate":
			return parseBoolean(cursorRotate, args[1]);
		case "CursorTrailRotate":
			return parseBoolean(cursorTrailRotate, args[1]);
		case "AnimationFramerate":
			return parseInt(animationFramerate, args[1], 1);
		case "LayeredHitSounds":
			return parseBoolean(layeredHitSounds, args[1]);
		case "ComboBurstRandom":
			return parseBoolean(comboBurstRandom, args[1]);
		case "CustomComboBurstSounds":
			return customComboBurstSounds.update(args[1].replaceAll(" ", ""));
		case "HitCircleOverlayAboveNumber":
			return parseBoolean(hitCircleOverlayAboveNumber, args[1]);
		case "SliderStyle":
			return sliderStyle.update(SliderStyle.fromString(args[1]));
		case "SliderBallFlip":
			return parseBoolean(sliderBallFlip, args[1]);
		case "AllowSliderBallTint":
			return parseBoolean(allowSliderBallTint, args[1]);
		case "SpinnerNoBlink":
			return parseBoolean(spinnerNoBlink, args[1]);
		case "SpinnerFadePlayfield":
			return parseBoolean(spinnerFadePlayfield, args[1]);
		case "SpinnerFrequencyModulate":
			return parseBoolean(spinnerFrequencyModulate, args[1]);
		//[Colours]
		case "SongSelectActiveText":
			return parseColor(songSelectActiveText, args[1]);
		case "SongSelectInactiveText":
			return parseColor(songSelectInactiveText, args[1]);
		case "MenuGlow":
			return parseColor(menuGlow, args[1]);
		case "StarBreakAdditive":
			return parseColor(starBreakAdditive, args[1]);
		case "InputOverlayText":
			return parseColor(inputOverlayText, args[1]);
		case "SliderBall":
			return parseColor(sliderBall, args[1]);
		case "SliderTrackOverride":
			return parseColor(sliderTrackOverride, args[1]);
		case "SliderBorder":
			return parseColor(sliderBorder, args[1]);
		case "SpinnerBackground":
			return parseColor(spinnerBackground, args[1]);
		case "Combo1":
			return parseColor(combo1, args[1]);
		case "Combo2":
			return parseColor(combo2, args[1]);
		case "Combo3":
			return parseColor(combo3, args[1]);
		case "Combo4":
			return parseColor(combo4, args[1]);
		case "Combo5":
			return parseColor(combo5, args[1]);
		case "Combo6":
			return parseColor(combo6, args[1]);
		case "Combo7":
			return parseColor(combo7, args[1]);
		case "Combo8":
			return parseColor(combo8, args[1]);
		//[Fonts]
		case "HitCirclePrefix":
			return hitCirclePrefix.update(args[1]);
		case "HitCircleOverlap":
			return parseInt(hitCircleOverlap, args[1]);
		case "ScorePrefix":
			return scorePrefix.update(args[1]);
		case "ScoreOverlap":
			return parseInt(scoreOverlap, args[1]);
		case "ComboPrefix":
			return comboPrefix.update(args[1]);
		case "ComboOverlap":
			return parseInt(comboOverlap, args[1]);
		//[CatchTheBeat]
		case "HyperDash":
			return parseColor(hyperDash, args[1]);
		case "HyperDashFruit":
			return parseColor(hyperDashFruit, args[1]);
		case "HyperDashAfterImage":
			return parseColor(hyperDashAfterImage, args[1]);
		default:
			return new Comment(line);
		}
	}

	private Setting<?> parseMania(ManiaIni ini, String line) throws IOException{
		if(line.trim().isEmpty() || line.startsWith("//")){
			return new Comment(line);
		}
		String[] args = line.split(":");
		args[1] = args[1].trim();
		switch(args[0]){
		case "ColumnStart":
			return parseDouble(ini.columnStart, args[1], 0.0D);
		case "ColumnRight":
			return parseDouble(ini.columnRight, args[1], 0.0D);
		case "ColumnSpacing":
			return parseList(ini.columnSpacing, args[1], ini.keys - 1);
		case "ColumnWidth":
			return parseList(ini.columnWidth, args[1], ini.keys);
		case "ColumnLineWidth":
			return parseList(ini.columnLineWidth, args[1], ini.keys + 1);
		case "BarlineHeight":
			return parseDouble(ini.barlineHeight, args[1], 0.0D);
		case "LightingNWidth":
			return parseList(ini.lightingNWidth, args[1], ini.keys);
		case "LightingLWidth":
			return parseList(ini.lightingLWidth, args[1], ini.keys);
		case "WidthForNoteHeightScale":
			return parseDouble(ini.widthForNoteHeightScale, args[1], 0.0D);
		case "HitPosition":
			return parseInt(ini.hitPosition, args[1], 0);
		case "LightPosition":
			return parseInt(ini.lightPosition, args[1], 0);
		case "ScorePosition":
			return parseInt(ini.scorePosition, args[1], 0);
		case "ComboPosition":
			return parseInt(ini.comboPosition, args[1], 0);
		case "JudgementLine":
			return parseBoolean(ini.judgementLine, args[1]);
		case "SpecialStyle":
			return ini.specialStyle.update(SpecialStyle.fromString(args[1]));
		case "ComboBurstStyle":
			return ini.comboBurstStyle.update(ComboBurstStyle.fromString(args[1]));
		case "SplitStages":
			return parseBoolean(ini.splitStages, args[1]);
		case "StageSeparation":
			return parseDouble(ini.stageSeparation, args[1], 0);
		case "SeparateScore":
			return parseBoolean(ini.separateScore, args[1]);
		case "KeysUnderNotes":
			return parseBoolean(ini.keysUnderNotes, args[1]);
		case "UpsideDown":
			return parseBoolean(ini.upsideDown, args[1]);
		case "ColourColumnLine":
			return parseColor(ini.colourColumnLine, args[1]);
		case "ColourBarline":
			return parseColor(ini.colourBarline, args[1]);
		case "ColourJudgementLine":
			return parseColor(ini.colourJudgementLine, args[1]);
		case "ColourKeyWarning":
			return parseColor(ini.colourKeyWarning, args[1]);
		case "ColourHold":
			return parseColor(ini.colourHold, args[1]);
		case "ColourBreak":
			return parseColor(ini.colourBreak, args[1]);
		case "StageLeft":
			return ini.stageLeft.update(args[1]);
		case "StageRight":
			return ini.stageRight.update(args[1]);
		case "StageBottom":
			return ini.stageBottom.update(args[1]);
		case "StageHint":
			return ini.stageHint.update(args[1]);
		case "StageLight":
			return ini.stageLight.update(args[1]);
		case "LightingN":
			return ini.lightingN.update(args[1]);
		case "LightingL":
			return ini.lightingL.update(args[1]);
		case "WarningArrow":
			return ini.warningArrow.update(args[1]);
		case "Hit0":
			return ini.hit0.update(args[1]);
		case "Hit50":
			return ini.hit50.update(args[1]);
		case "Hit100":
			return ini.hit100.update(args[1]);
		case "Hit200":
			return ini.hit200.update(args[1]);
		case "Hit300":
			return ini.hit300.update(args[1]);
		case "Hit300g":
			return ini.hit300g.update(args[1]);
		default:
			if(args[0].startsWith("KeyFlipWhenUpsideDown")){
				args[0] = args[0].substring(21);
				if(args[0].isEmpty()){
					return parseBoolean(ini.keyFlipWhenUpsideDown, args[1]);
				}else if(args[0].endsWith("D")){
					return parseBoolean(ini.columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].keyFlipWhenUpsideDownD, args[1]);
				}else{
					return parseBoolean(ini.columns[Integer.parseInt(args[0])].keyFlipWhenUpsideDown, args[1]);
				}
			}else if(args[0].startsWith("NoteFlipWhenUpsideDown")){
				args[0] = args[0].substring(22);
				if(args[0].isEmpty()){
					return parseBoolean(ini.noteFlipWhenUpsideDown, args[1]);
				}else if(args[0].endsWith("H")){
					return parseBoolean(ini.columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].noteFlipWhenUpsideDownH, args[1]);
				}else if(args[0].endsWith("L")){
					return parseBoolean(ini.columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].noteFlipWhenUpsideDownL, args[1]);
				}else if(args[0].endsWith("T")){
					return parseBoolean(ini.columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].noteFlipWhenUpsideDownT, args[1]);
				}else{
					return parseBoolean(ini.columns[Integer.parseInt(args[0])].noteFlipWhenUpsideDown, args[1]);
				}
			}else if(args[0].startsWith("NoteBodyStyle")){
				args[0] = args[0].substring(13);
				if(args[0].isEmpty()){
					return ini.noteBodyStyle.update(NoteBodyStyle.fromString(args[1]));
				}else{
					return ini.columns[Integer.parseInt(args[0])].noteBodyStyle.update(NoteBodyStyle.fromString(args[1]));
				}
			}else if(args[0].startsWith("ColourLight")){
				return parseColor(ini.columns[Integer.parseInt(args[0].substring(11)) - 1].colourLight, args[1]);
			}else if(args[0].startsWith("Colour")){
				return parseColor(ini.columns[Integer.parseInt(args[0].substring(6)) - 1].colour, args[1]);
			}else if(args[0].startsWith("KeyImage")){
				args[0] = args[0].substring(8);
				if(args[0].endsWith("D")){
					return ini.columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].keyImageD.update(args[1]);
				}else{
					return ini.columns[Integer.parseInt(args[0])].keyImage.update(args[1]);
				}
			}else if(args[0].startsWith("NoteImage")){
				args[0] = args[0].substring(9);
				if(args[0].endsWith("H")){
					return ini.columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].noteImageH.update(args[1]);
				}else if(args[0].endsWith("T")){
					return ini.columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].noteImageT.update(args[1]);
				}else if(args[0].endsWith("L")){
					return ini.columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].noteImageL.update(args[1]);
				}else{
					return ini.columns[Integer.parseInt(args[0])].noteImage.update(args[1]);
				}
			}
			return new Comment(line);
		}
	}

	private Setting<Double> parseDouble(Setting<Double> setting, String line, double min){
		return parseDouble(setting, line, min, Double.MAX_VALUE);
	}
	
	private Setting<Double> parseDouble(Setting<Double> setting, String line, double min, double max){
		try{
			double val = Double.parseDouble(line);
			if(val >= min && val <= max){
				return setting.update(val);
			}else{
				usedDefault = true;
			}
		}catch(NumberFormatException e){
			usedDefault = true;
		}
		return setting;
	}
	
	private Setting<Integer> parseInt(Setting<Integer> setting, String line){
		return parseInt(setting, line, Integer.MIN_VALUE);
	}
	
	private Setting<Integer> parseInt(Setting<Integer> setting, String line, int min){
		return parseInt(setting, line, min, Integer.MAX_VALUE);
	}
	
	private Setting<Integer> parseInt(Setting<Integer> setting, String line, int min, int max){
		try{
			int val = Integer.parseInt(line);
			if(val >= min && val <= max){
				return setting.update(val);
			}else{
				usedDefault = true;
			}
		}catch(NumberFormatException e){
			usedDefault = true;
		}
		return setting;
	}
	
	private Setting<Boolean> parseBoolean(Setting<Boolean> setting, String line){
		if(line.equals("1") || line.equals("0")){
			return setting.update(line.equals("1"));
		}else{
			usedDefault = true;
			return setting;
		}
	}
	
	private Setting<double[]> parseList(Setting<double[]> setting, String data, int expected){
		String[] args = data.split(",");
		if(args.length != expected){
			usedDefault = true;
			return setting;
		}
		for(int i = 0; i < expected; i++){
			setting.getValue()[i] = Math.max(0.0D, Double.parseDouble(args[i].trim()));
		}
		return setting;
	}

	private Setting<Colour> parseColor(Setting<Colour> setting, String arg){
		String[] args = arg.split(",");
		try{
			if(args.length == 3){
				return setting.update(new Colour(Integer.parseInt(args[0].trim()), Integer.parseInt(args[1].trim()), Integer.parseInt(args[2].trim())));
			}else if(args.length == 4){
				return setting.update(new Colour(Integer.parseInt(args[0].trim()), Integer.parseInt(args[1].trim()), Integer.parseInt(args[2].trim()), Integer.parseInt(args[3].trim())));
			}else{
				usedDefault = true;
			}
		}catch(NumberFormatException e){
			usedDefault = true;
		}
		return setting;
	}

	public void writeIni(File file) throws FileNotFoundException{
		PrintWriter writer = new PrintWriter(new FileOutputStream(file));

		for(Section section : data){
			if(section.name != null){
				writer.println(section.name);
			}
			for(Setting<?> setting : section.data){
				if(setting.isEnabled()){
					writer.println(setting);
				}
			}
		}

		writer.flush();
		writer.close();
	}
	
	protected enum SliderStyle implements Printable{
		SEGMENTS(1, "Segments"),
		GRADIENT(2, "Gradient");
		
		private final String name;
		private final int id;
		
		private SliderStyle(int id, String name){
			this.name = name;
			this.id = id;
		}
		
		private static final SliderStyle fromString(String str){
			switch(str){
			case "1":
				return SEGMENTS;
			case "2":
				return GRADIENT;
			default:
				usedDefault = true;
				return GRADIENT;
			}
		}
	
		@Override
		public String toString(){
			return name;
		}

		@Override
		public String print(){
			return String.valueOf(id);
		}
	}
	
	protected enum SpecialStyle implements Printable{
		NONE(0, "None"),
		LEFT(1, "Left lane SP - outer lanes DP"),
		RIGHT(2, "Right lane SP - outer lanes DP");
		
		private final String name;
		private final int id;
		
		private SpecialStyle(int id, String name){
			this.name = name;
			this.id = id;
		}
		
		private static final SpecialStyle fromString(String line){
			switch(line.toLowerCase(Locale.ROOT)){
			case "0":
			case "none":
				return NONE;
			case "1":
			case "left":
				return LEFT;
			case "2":
			case "right":
				return RIGHT;
			default:
				usedDefault = true;
				return NONE;
			}
		}
		
		@Override
		public String toString(){
			return name;
		}

		@Override
		public String print(){
			return String.valueOf(id);
		}
	}
	
	protected enum ComboBurstStyle implements Printable{
		LEFT(0, "Left"),
		RIGHT(1, "Right"),
		BOTH(2, "Both");
		
		private final String name;
		private final int id;
		
		private ComboBurstStyle(int id, String name){
			this.name = name;
			this.id = id;
		}
		
		private static final ComboBurstStyle fromString(String line){
			switch(line.toLowerCase(Locale.ROOT)){
			case "0":
			case "left":
				return LEFT;
			case "1":
			case "right":
				return RIGHT;
			case "2":
			case "both":
				return BOTH;
			default:
				usedDefault = true;
				return RIGHT;
			}
		}
		
		@Override
		public String toString(){
			return name;
		}

		@Override
		public String print(){
			return String.valueOf(id);
		}
	}
	
	protected enum NoteBodyStyle implements Printable{
		STRETCHED(0, "Stretched to fit the whole length"),
		CASCADINGTH(1, "Cascading from tail to head"),
		CASCADINGHT(2, "Cascading from head to tail");

		private final String name;
		private final int id;
		
		private NoteBodyStyle(int id, String name){
			this.name = name;
			this.id = id;
		}
		
		private static NoteBodyStyle fromString(String line){
			switch(line){
			case "0":
				return STRETCHED;
			case "1":
				return CASCADINGTH;
			case "2":
				return CASCADINGHT;
			default:
				return CASCADINGTH;
			}
		}

		@Override
		public String toString(){
			return name;
		}
		
		@Override
		public String print(){
			return String.valueOf(id);
		}
	}

	protected enum Version implements Printable{
		V1("1", "(Old style)"),
		V2("2", "(Basic new style)"),
		V21("2.1", "(Taiko position changes)"),
		V22("2.2", "(UI changes)"),
		V23("2.3", "(New Catch catcher style)"),
		V24("2.4", "(Mania stage scaling reduction)"),
		V25("2.5", "(Mania upscroll and column improvements)"),
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
			case "2.0":
				return V2;
			case "2.1":
				return V21;
			case "2.2":
				return V22;
			case "2.3":
				return V23;
			case "2.4":
				return V24;
			case "2.5":
				return V25;
			case "latest":
				return LATEST;
			default:
				usedDefault = true;
				return V1;
			}
		}

		@Override
		public String toString(){
			return name + " " + extra;
		}

		@Override
		public String print(){
			return name;
		}
	}
	
	protected static abstract interface Printable{
		
		public abstract String print();
	}
}

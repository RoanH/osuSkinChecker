package dev.roanh.osuskinchecker.ini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.regex.Pattern;

import dev.roanh.util.Dialog;

/**
 * Main class that reflects all the
 * possible skin.ini settings
 * @author Roan
 * @see ManiaIni
 */
public class SkinIni{
	/**
	 * Field for which default values were used
	 * during the parsing
	 */
	static List<String> usedDefault = new ArrayList<String>();
	/**
	 * All the setting in this skin.ini
	 */
	private List<Section> data;
	/**
	 * The original file location of this skin.ini
	 */
	public Path ini;
	/**
	 * [General]<br>
	 * <code>Name: &lt; skin name &gt;</code>
	 */
	protected final Setting<String> name = new Setting<String>("Name", "-");
	/**
	 * [General]<br>
	 * <code>Author: &lt; author name &gt;</code>
	 */
	protected final Setting<String> author = new Setting<String>("Author", "-");
	/**
	 * [General]<br>
	 * <code>Version: &lt; version &gt;</code>
	 * @see Version
	 */
	protected final Setting<Version> version = new Setting<Version>("Version", Version.V1);
	/**
	 * [General]<br>
	 * <code>CursorExpand: &lt; 0:1 &gt;</code>
	 */
	protected final Setting<Boolean> cursorExpand = new Setting<Boolean>("CursorExpand", true);
	/**
	 * [General]<br>
	 * <code>CursorCentre: &lt; 0:1 &gt;</code>
	 */
	protected final Setting<Boolean> cursorCentre = new Setting<Boolean>("CursorCentre", true);
	/**
	 * [General]<br>
	 * <code>CursorRotate: &lt; 0:1 &gt;</code>
	 */
	protected final Setting<Boolean> cursorRotate = new Setting<Boolean>("CursorRotate", true);
	/**
	 * [General]<br>
	 * <code>CursorTrailRotate: &lt; 0:1 &gt;</code>
	 */
	protected final Setting<Boolean> cursorTrailRotate = new Setting<Boolean>("CursorTrailRotate", true);
	/**
	 * [General]<br>
	 * <code>animationFramerate: &lt; framerate &gt;</code>
	 */
	protected final Setting<Integer> animationFramerate = new Setting<Integer>("AnimationFramerate", false, 1);
	/**
	 * [General]<br>
	 * <code>LayeredHitSounds: &lt; 0:1 &gt;</code>
	 */
	protected final Setting<Boolean> layeredHitSounds = new Setting<Boolean>("LayeredHitSounds", true);
	/**
	 * [General]<br>
	 * <code>ComboBurstRandom: &lt; 0:1 &gt;</code>
	 */
	protected final Setting<Boolean> comboBurstRandom = new Setting<Boolean>("ComboBurstRandom", false);
	/**
	 * [General]<br>
	 * <code>CustomComboBurstSounds: &lt; comma separated list &gt;</code>
	 */
	protected final Setting<String> customComboBurstSounds = new Setting<String>("CustomComboBurstSounds", false, "");
	/**
	 * [General]<br>
	 * <code>HitCircleOverlayAboveNumber: &lt; 0:1 &gt;</code>
	 */
	protected final Setting<Boolean> hitCircleOverlayAboveNumber = new Setting<Boolean>("HitCircleOverlayAboveNumber", true);
	/**
	 * [General]<br>
	 * <code>SliderStyle: &lt; style &gt;</code>
	 * @see SliderStyle
	 */
	protected final Setting<SliderStyle> sliderStyle = new Setting<SliderStyle>("SliderStyle", SliderStyle.GRADIENT);
	/**
	 * [General]<br>
	 * <code>SliderBallFlip: &lt; 0:1 &gt;</code>
	 */
	protected final Setting<Boolean> sliderBallFlip = new Setting<Boolean>("SliderBallFlip", false);
	/**
	 * [General]<br>
	 * <code>SliderBallFrames: &lt; positive integer &gt;</code>
	 */
	protected final Setting<Integer> sliderBallFrames = new Setting<Integer>("SliderBallFrames", false, 1);
	/**
	 * [General]<br>
	 * <code>AllowSliderBallTint: &lt; 0:1 &gt;</code>
	 */
	protected final Setting<Boolean> allowSliderBallTint = new Setting<Boolean>("AllowSliderBallTint", false);
	/**
	 * [General]<br>
	 * <code>SpinnerNoBlink: &lt; 0:1 &gt;</code>
	 */
	protected final Setting<Boolean> spinnerNoBlink = new Setting<Boolean>("SpinnerNoBlink", false);
	/**
	 * [General]<br>
	 * <code>SpinnerFadePlayfield: &lt; 0:1 &gt;</code>
	 */
	protected final Setting<Boolean> spinnerFadePlayfield = new Setting<Boolean>("SpinnerFadePlayfield", false);
	/**
	 * [General]<br>
	 * <code>SpinnerFrequencyModulate: &lt; 0:1 &gt;</code>
	 */
	protected final Setting<Boolean> spinnerFrequencyModulate = new Setting<Boolean>("SpinnerFrequencyModulate", true);
	/**
	 * [Colours]<br>
	 * <code>SongSelectActiveText: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> songSelectActiveText = new Setting<Colour>("SongSelectActiveText", new Colour(0, 0, 0));
	/**
	 * [Colours]<br>
	 * <code>SongSelectInactiveText: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> songSelectInactiveText = new Setting<Colour>("SongSelectInactiveText", new Colour(255, 255, 255));
	/**
	 * [Colours]<br>
	 * <code>MenuGlow: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> menuGlow = new Setting<Colour>("MenuGlow", new Colour(0, 78, 155));
	/**
	 * [Colours]<br>
	 * <code>StarBreakAdditive: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> starBreakAdditive = new Setting<Colour>("StarBreakAdditive", new Colour(255, 182, 193));
	/**
	 * [Colours]<br>
	 * <code>InputOverlayText: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> inputOverlayText = new Setting<Colour>("InputOverlayText", new Colour(0, 0, 0));
	/**
	 * [Colours]<br>
	 * <code>SliderBall: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> sliderBall = new Setting<Colour>("SliderBall", new Colour(2, 170, 255));
	/**
	 * [Colours]<br>
	 * <code>SliderTrackOverride: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> sliderTrackOverride = new Setting<Colour>("SliderTrackOverride", new Colour(0, 0, 0));
	/**
	 * [Colours]<br>
	 * <code>SliderBorder: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> sliderBorder = new Setting<Colour>("SliderBorder", new Colour(0, 0, 0));
	/**
	 * [Colours]<br>
	 * <code>SpinnerBackground: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> spinnerBackground = new Setting<Colour>("SpinnerBackground", new Colour(100, 100, 100));
	/**
	 * [Colours]<br>
	 * <code>Combo1: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> combo1 = new Setting<Colour>("Combo1", new Colour(255, 192, 0));
	/**
	 * [Colours]<br>
	 * <code>Combo2: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> combo2 = new Setting<Colour>("Combo2", false, new Colour(0, 0, 0));
	/**
	 * [Colours]<br>
	 * <code>Combo3: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> combo3 = new Setting<Colour>("Combo3", false, new Colour(0, 0, 0));
	/**
	 * [Colours]<br>
	 * <code>Combo4: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> combo4 = new Setting<Colour>("Combo4", false, new Colour(0, 0, 0));
	/**
	 * [Colours]<br>
	 * <code>Combo5: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> combo5 = new Setting<Colour>("Combo5", false, new Colour(0, 0, 0));
	/**
	 * [Colours]<br>
	 * <code>Combo6: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> combo6 = new Setting<Colour>("Combo6", false, new Colour(0, 0, 0));
	/**
	 * [Colours]<br>
	 * <code>Combo7: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> combo7 = new Setting<Colour>("Combo7", false, new Colour(0, 0, 0));
	/**
	 * [Colours]<br>
	 * <code>Combo8: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> combo8 = new Setting<Colour>("Combo8", false, new Colour(0, 0, 0));
	/**
	 * [Fonts]<br>
	 * <code>HitCirclePrefix: &lt; prefix name &gt;</code>
	 */
	protected final Setting<String> hitCirclePrefix = new Setting<String>("HitCirclePrefix", "default");
	/**
	 * [Fonts]<br>
	 * <code>HitCircleOverlap: &lt; overlap amount &gt;</code>
	 */
	protected final Setting<Integer> hitCircleOverlap = new Setting<Integer>("HitCircleOverlap", -2);//negative allowed
	/**
	 * [Fonts]<br>
	 * <code>ScorePrefix: &lt; prefix name &gt;</code>
	 */
	protected final Setting<String> scorePrefix = new Setting<String>("ScorePrefix", "score");
	/**
	 * [Fonts]<br>
	 * <code>ScoreOverlap: &lt; overlap amount &gt;</code>
	 */
	protected final Setting<Integer> scoreOverlap = new Setting<Integer>("ScoreOverlap", -2);//negative allowed
	/**
	 * [Fonts]<br>
	 * <code>ComboPrefix: &lt; prefix name &gt;</code>
	 */
	protected final Setting<String> comboPrefix = new Setting<String>("ComboPrefix", "score");
	/**
	 * [Fonts]<br>
	 * <code>ComboOverlap: &lt; overlap amount &gt;</code>
	 */
	protected final Setting<Integer> comboOverlap = new Setting<Integer>("ComboOverlap", -2);//negative allowed
	/**
	 * [CatchTheBeat]<br>
	 * <code>HyperDash: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> hyperDash = new Setting<Colour>("HyperDash", new Colour(255, 0, 0));
	/**
	 * [CatchTheBeat]<br>
	 * <code>HyperDashFruit: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> hyperDashFruit = new Setting<Colour>("HyperDashFruit", false, new Colour(0, 0, 0));
	/**
	 * [CatchTheBeat]<br>
	 * <code>HyperDashAfterImage: &lt; rgb color &gt;</code>
	 */
	protected final Setting<Colour> hyperDashAfterImage = new Setting<Colour>("HyperDashAfterImage", false, new Colour(0, 0, 0));
	/**
	 * All mania configurations from 1 to {@link ManiaIni#MAX_KEYS}
	 */
	protected final ManiaIni[] mania = new ManiaIni[ManiaIni.MAX_KEYS];
	
	/**
	 * Reads the given skin.ini file and
	 * update all the relevant setting fields
	 * @param file The skin.ini file to read
	 * @throws IOException When an IOException occurs
	 */
	public void readIni(Path file) throws IOException{
		ini = file;
		usedDefault.clear();
		data = new ArrayList<Section>();
		Section section = new Section(null);
		data.add(section);
		Pattern header = Pattern.compile("\\[.+\\]");
		ManiaIni maniaIni = null;
		Setting.singleUpdateMode = true;
		
		Map<String, Setting<?>[]> all = new HashMap<String, Setting<?>[]>();
		all.put("[General]", new Setting<?>[]{
			name,
			author,
			version,
			cursorExpand,
			cursorCentre,
			cursorRotate,
			cursorTrailRotate,
			animationFramerate,
			layeredHitSounds,
			comboBurstRandom,
			customComboBurstSounds,
			hitCircleOverlayAboveNumber,
			sliderStyle,
			sliderBallFlip,
			sliderBallFrames,
			allowSliderBallTint,
			spinnerNoBlink,
			spinnerFadePlayfield,
			spinnerFrequencyModulate
		});
		all.put("[Colours]", new Setting<?>[]{
			songSelectActiveText,
			songSelectInactiveText,
			menuGlow,
			starBreakAdditive,
			inputOverlayText,
			sliderBall,
			sliderTrackOverride,
			sliderBorder,
			spinnerBackground,
			combo1,
			combo2,
			combo3,
			combo4,
			combo5,
			combo6,
			combo7,
			combo8
		});
		all.put("[Fonts]", new Setting<?>[]{
			hitCirclePrefix,
			hitCircleOverlap,
			scorePrefix,
			scoreOverlap,
			comboPrefix,
			comboOverlap
		});
		all.put("[CatchTheBeat]", new Setting<?>[]{
			hyperDash,
			hyperDashFruit,
			hyperDashAfterImage
		});
		
		String line = null;
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file), StandardCharsets.UTF_8))){
			while((line = reader.readLine()) != null){
				line = line.replaceFirst("^[\t ]*", "");
				if(header.matcher(line.trim()).matches()){
					int index = section.data.size() - 1;
					Setting<?> last = index < 0 ? null : section.data.get(index);
					if(last instanceof Comment && ((Comment)last).getValue().trim().isEmpty()){
						section.data.remove(index);
					}
					if(section.isMania()){
						for(Setting<?> setting : section.mania.getAll()){
							if(!setting.added){
								section.data.add(setting);
							}
						}
					}else{
						for(Setting<?> setting : all.getOrDefault(section.name, new Setting<?>[0])){
							if(!setting.added){
								section.data.add(setting);
							}
						}
					}
					if(last instanceof Comment && ((Comment)last).getValue().trim().isEmpty()){
						section.data.add(last);
					}
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
										if(keys >= 1 && keys <= ManiaIni.MAX_KEYS && !(keys >= 10 && keys % 2 != 0)){
											mania[keys - 1] = (maniaIni = new ManiaIni(keys));
											section.mania = maniaIni;
											break;
										}else{
											throw new IllegalArgumentException("Invalid key count: " + keys);
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
				}else{
					Setting<?> setting;
					if(line.trim().isEmpty() || line.startsWith("//") || section.name == null){
						setting = new Comment(line);
					}else{
						switch(section.name){
						case "[Mania]":
							setting = maniaIni.parseMania(line);
							break;
						case "[CatchTheBeat]":
							setting = parseCtb(line);
							break;
						case "[Fonts]":
							setting = parseFonts(line);
							break;
						case "[Colours]":
							setting = parseColours(line);
							break;
						case "[General]":
							setting = parseGeneral(line);
							break;
						default:
							throw new IllegalArgumentException("The " + section.name + " section is not a valid skin.ini section.");
						}
					}
					setting.added = true;
					section.data.add(setting);
				}
			}	
		}catch(Throwable e){
			e.printStackTrace();
			throw new IllegalArgumentException("Error on line: " + line + "\nCause: " + e.getMessage(), e);
		}
		
		outer: for(Entry<String, Setting<?>[]> cat : all.entrySet()){
			for(Section s : data){
				if(cat.getKey().equals(s.name)){
					continue outer;
				}
			}
			Section missed = new Section(cat.getKey());
			data.add(missed);
			for(Setting<?> setting : cat.getValue()){
				missed.data.add(setting);
			}
		}
		
		Setting.singleUpdateMode = false;
				
		if(usedDefault.size() != 0){
			StringJoiner defaults = new StringJoiner(", ");
			for(String setting : usedDefault){
				defaults.add(setting);
			}
			Dialog.showMessageDialog("Skin.ini fields were found that couldn't be parsed. Default values were used for the following fields:\n" + defaults.toString());
		}
	}
	
	/**
	 * Writes this skin.ini configuration
	 * to the given file
	 * @param file The file to write to (will overwrite).
	 * @throws IOException When an IO exception occurs.
	 */
	public void writeIni(Path file) throws IOException{
		try(PrintWriter writer = new PrintWriter(new OutputStreamWriter(Files.newOutputStream(file), StandardCharsets.UTF_8))){
			for(Section section : data){
				if(section.name != null){
					writer.println(section.name);
					if(section.isMania()){
						writer.println("Keys: " + section.mania.keys);
					}
				}
				for(Setting<?> setting : section.data){
					if(setting.isEnabled() && setting.wasUpdated()){
						writer.println(setting);
					}
				}
			}

			writer.flush();
		}
	}
	
	/**
	 * Creates a mania configuration section
	 * for the given key count
	 * @param keys The key count to create
	 *        a configuration section for
	 */
	public final void createManiaConfiguration(int keys){
		ManiaIni ini = new ManiaIni(keys);
		mania[keys - 1] = ini;
		Section s = new Section("[Mania]");
		s.mania = ini;
		data.add(s);
	}

	/**
	 * Parses a double setting with a
	 * given minimum constraint if this
	 * constraint is not met the old
	 * value for the given setting will
	 * remain. If the given line data does
	 * not represent a valid double value
	 * the old value will remain in place 
	 * @param setting The setting to update
	 * @param line The line that contains the double data
	 * @param min The minimum value required
	 *        for the new value to be valid
	 * @return The updated setting
	 */
	private static Setting<Double> parseDouble(Setting<Double> setting, String line, double min){
		return parseDouble(setting, line, min, Double.MAX_VALUE);
	}
	
	/**
	 * Parses a double setting with a
	 * given minimum and maximum constraint 
	 * if these constraints are not met the 
	 * old value for the given setting will
	 * remain. If the given line data does
	 * not represent a valid double value
	 * the old value will remain in place
	 * @param setting The setting to update
	 * @param line The line that contains the double data
	 * @param min The minimum value required
	 *        for the new value to be valid
	 * @param max The maximum value allowed
	 *        for the new value to be valid
	 * @return The updated setting
	 */
	private static Setting<Double> parseDouble(Setting<Double> setting, String line, double min, double max){
		try{
			double val = Double.parseDouble(line);
			if(val >= min && val <= max){
				return setting.update(val);
			}else{
				usedDefault.add(setting.getName());
			}
		}catch(NumberFormatException e){
			usedDefault.add(setting.getName());
		}
		return setting;
	}
	
	/**
	 * Parses an integer setting.
	 * If the given line data does
	 * not represent a valid integer value
	 * the old value will remain in place
	 * @param setting The setting to update
	 * @param line The line that contains the integer data
	 * @return The updated setting
	 */
	private static Setting<Integer> parseInt(Setting<Integer> setting, String line){
		return parseInt(setting, line, Integer.MIN_VALUE);
	}
	
	/**
	 * Parses an integer setting.
	 * If the given line data does
	 * not represent a valid integer value
	 * the old value will remain in place
	 * @param setting The setting to update
	 * @param line The line that contains the integer data
	 * @param min The minimum value required
	 *        for the new value to be valid
	 * @return The updated setting
	 */
	private static Setting<Integer> parseInt(Setting<Integer> setting, String line, int min){
		return parseInt(setting, line, min, Integer.MAX_VALUE);
	}
	
	/**
	 * Parses an integer setting.
	 * If the given line data does
	 * not represent a valid integer value
	 * the old value will remain in place
	 * @param setting The setting to update
	 * @param line The line that contains the integer data
	 * @param min The minimum value required
	 *        for the new value to be valid
	 * @param max The maximum value allowed
	 *        for the new value to be valid
	 * @return The updated setting
	 */
	private static Setting<Integer> parseInt(Setting<Integer> setting, String line, int min, int max){
		try{
			int val = Integer.parseInt(line);
			if(val >= min && val <= max){
				return setting.update(val);
			}else{
				usedDefault.add(setting.getName());
			}
		}catch(NumberFormatException e){
			usedDefault.add(setting.getName());
		}
		return setting;
	}
	
	/**
	 * Parses a boolean setting.
	 * If the provided line data does not
	 * represent a valid boolean value the
	 * old value remains in place.
	 * @param setting The setting to update
	 * @param line The line that contains the boolean data
	 * @return The updated setting
	 */
	private static Setting<Boolean> parseBoolean(Setting<Boolean> setting, String line){
		if(line.equals("1") || line.equals("0")){
			return setting.update(line.equals("1"));
		}else{
			usedDefault.add(setting.getName());
			return setting;
		}
	}
	
	/**
	 * Parses a list setting.
	 * If the provided line data not
	 * represent a list with the expected
	 * number of values the old value
	 * will remain in place
	 * @param setting The setting to update
	 * @param data The line that contains the list data
	 * @param expected The expected number of
	 *        arguments in the given list
	 * @return The updated setting
	 */
	private static Setting<double[]> parseList(Setting<double[]> setting, String data, int expected){
		String[] args = data.split(",");
		if(args.length != expected){
			usedDefault.add(setting.getName());
			return setting;
		}
		for(int i = 0; i < expected; i++){
			setting.getValue()[i] = Math.max(0.0D, Double.parseDouble(args[i].trim()));
		}
		return setting;
	}

	/**
	 * Parses a color setting.
	 * If the provided argument does
	 * not represent a valid color the
	 * old value will remain in place
	 * @param setting The setting to update
	 * @param arg The argument that represents
	 *        the color data
	 * @return The updated setting
	 */
	private static Setting<Colour> parseColor(Setting<Colour> setting, String arg){
		String[] args = arg.split(",");
		try{
			if(args.length == 3){
				return setting.update(new Colour(Integer.parseInt(args[0].trim()), Integer.parseInt(args[1].trim()), Integer.parseInt(args[2].trim())));
			}else if(args.length == 4){
				return setting.update(new Colour(Integer.parseInt(args[0].trim()), Integer.parseInt(args[1].trim()), Integer.parseInt(args[2].trim()), Integer.parseInt(args[3].trim())));
			}else{
				usedDefault.add(setting.getName());
			}
		}catch(NumberFormatException e){
			usedDefault.add(setting.getName());
		}
		return setting;
	}
	
	/**
	 * Parses the given string and updates the setting
	 * that it represents in the [CatchTheBeat] section.
	 * @param line The line to parse
	 * @return The setting that
	 *         was updated or created
	 * @throws IllegalArgumentException If the given line
	 *         was not a valid setting for this section
	 * @see Setting
	 */
	private Setting<?> parseCtb(String line){
		String[] args = line.split(":", 2);
		args[1] = args[1].trim();
		switch(args[0].trim()){
		case "HyperDash":
			return parseColor(hyperDash, args[1]);
		case "HyperDashFruit":
			return parseColor(hyperDashFruit, args[1]);
		case "HyperDashAfterImage":
			return parseColor(hyperDashAfterImage, args[1]);
		default:
			throw new IllegalArgumentException("Illegal command in the [CatchTheBeat] section: " + line);
		}
	}
	
	/**
	 * Searches though all the settings in
	 * the skin.ini and find one with the
	 * given name.
	 * @param name The name of the setting
	 *        to search for.
	 * @param customKeyCount The mania key count to look
	 *        in or -1 if the setting is not a mania setting
	 * @return The setting or null if it was not found.
	 */
	public Setting<?> find(String name, int customKeyCount){
		for(Section section : data){
			if((!section.isMania() && customKeyCount == -1) || (section.isMania() && section.mania.keys == customKeyCount)){
				for(Setting<?> setting : section.data){
					if(!(setting instanceof Comment) && setting.getName().equals(name)){
						return setting;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Parses the given string and updates the setting
	 * that it represents in the [General] section.
	 * @param line The line to parse
	 * @return The setting that
	 *         was updated or created
	 * @throws IllegalArgumentException If the given line
	 *         was not a valid setting for this section
	 * @see Setting
	 */
	private Setting<?> parseGeneral(String line){
		String[] args = line.split(":", 2);
		args[1] = args[1].trim();
		switch(args[0].trim()){
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
		case "HitCircleOverlayAboveNumer":
		case "HitCircleOverlayAboveNumber":
			return parseBoolean(hitCircleOverlayAboveNumber, args[1]);
		case "SliderStyle":
			return sliderStyle.update(SliderStyle.fromString(args[1]));
		case "SliderBallFlip":
			return parseBoolean(sliderBallFlip, args[1]);
		case "SliderBallFrames":
			return parseInt(sliderBallFrames, args[1], 1);
		case "AllowSliderBallTint":
			return parseBoolean(allowSliderBallTint, args[1]);
		case "SpinnerNoBlink":
			return parseBoolean(spinnerNoBlink, args[1]);
		case "SpinnerFadePlayfield":
			return parseBoolean(spinnerFadePlayfield, args[1]);
		case "SpinnerFrequencyModulate":
			return parseBoolean(spinnerFrequencyModulate, args[1]);
		default:
			throw new IllegalArgumentException("Illegal command in the [General] section: " + line);
		}
	}
	
	/**
	 * Parses the given string and updates the setting
	 * that it represents in the [Fonts] section.
	 * @param line The line to parse
	 * @return The setting that
	 *         was updated or created
	 * @throws IllegalArgumentException If the given line
	 *         was not a valid setting for this section
	 * @see Setting
	 */
	private Setting<?> parseFonts(String line){
		String[] args = line.split(":", 2);
		args[1] = args[1].trim();
		switch(args[0].trim()){
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
		default:
			throw new IllegalArgumentException("Illegal command in the [Fonts] section: " + line);
		}
	}
	
	/**
	 * Parses the given string and updates the setting
	 * that it represents in the [Colours] section.
	 * @param line The line to parse
	 * @return The setting that
	 *         was updated or created
	 * @throws IllegalArgumentException If the given line
	 *         was not a valid setting for this section
	 * @see Setting
	 */
	private Setting<?> parseColours(String line){
		String[] args = line.split(":", 2);
		args[1] = args[1].trim();
		switch(args[0].trim()){
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
		default:
			throw new IllegalArgumentException("Illegal command in the [Colours] section: " + line);
		}
	}
	
	/**
	 * Class that reflects all the setting
	 * in a mania section of the skin.ini
	 * @author Roan
	 */
	protected static final class ManiaIni{
		/**
		 * The maximum key count that will be parsed
		 * by this program
		 */
		protected static final int MAX_KEYS = 18;
		/**
		 * The key count this mania configuration is for
		 */
		protected final int keys;//non negative
		/**
		 * [Mania]<br>
		 * <code>ColumnStart: &lt; offset &gt;</code>
		 */
		protected final Setting<Double> columnStart = new Setting<Double>("ColumnStart", 136.0D);
		/**
		 * [Mania]<br>
		 * <code>ColumnRight: &lt; offset &gt;</code>
		 */
		protected final Setting<Double> columnRight = new Setting<Double>("ColumnRight", 19.0D);
		/**
		 * [Mania]<br>
		 * <code>ColumnSpacing: &lt; list of {@link #keys} - 1 numbers &gt;</code>
		 */
		protected final Setting<double[]> columnSpacing = new Setting<double[]>("ColumnSpacing", null);//n-1 numbers
		/**
		 * [Mania]<br>
		 * <code>ColumnWidth: &lt; list of {@link #keys} numbers &gt;</code>
		 */
		protected final Setting<double[]> columnWidth = new Setting<double[]>("ColumnWidth", null);//n numbers
		/**
		 * [Mania]<br>
		 * <code>ColumnLineWidth: &lt; list of {@link #keys} + 1 numbers &gt;</code>
		 */
		protected final Setting<double[]> columnLineWidth = new Setting<double[]>("ColumnLineWidth", null);//n+1 numbers
		/**
		 * [Mania]<br>
		 * <code>BarlineHeight: &lt; height &gt;</code>
		 */
		protected final Setting<Double> barlineHeight = new Setting<Double>("BarlineHeight", 1.2D);
		/**
		 * [Mania]<br>
		 * <code>LightingNWidth: &lt; list of {@link #keys} numbers &gt;</code>
		 */
		protected final Setting<double[]> lightingNWidth = new Setting<double[]>("LightingNWidth", null);//n numbers
		/**
		 * [Mania]<br>
		 * <code>LightingLWidth: &lt; list of {@link #keys} numbers &gt;</code>
		 */
		protected final Setting<double[]> lightingLWidth = new Setting<double[]>("LightingLWidth", null);//n numbers
		/**
		 * [Mania]<br>
		 * <code>WidthForNoteHeightScale: &lt; width &gt;</code>
		 */
		protected final Setting<Double> widthForNoteHeightScale = new Setting<Double>("WidthForNoteHeightScale", false, 1.0D);
		/**
		 * [Mania]<br>
		 * <code>HitPosition: &lt; height &gt;</code>
		 */
		protected final Setting<Integer> hitPosition = new Setting<Integer>("HitPosition", 402);
		/**
		 * [Mania]<br>
		 * <code>LightPosition: &lt; height &gt;</code>
		 */
		protected final Setting<Integer> lightPosition = new Setting<Integer>("LightPosition", 413);
		/**
		 * [Mania]<br>
		 * <code>ScorePosition: &lt; height &gt;</code>
		 */
		protected final Setting<Integer> scorePosition = new Setting<Integer>("ScorePosition", 325);
		/**
		 * [Mania]<br>
		 * <code>ComboPosition: &lt; height &gt;</code>
		 */
		protected final Setting<Integer> comboPosition = new Setting<Integer>("ComboPosition", 111);
		/**
		 * [Mania]<br>
		 * <code>JudgementLine: &lt; 0:1 &gt;</code>
		 */
		protected final Setting<Boolean> judgementLine = new Setting<Boolean>("JudgementLine", true);
		/**
		 * [Mania]<br>
		 * <code>LightFramePerSecond: &lt; unknown &gt;</code>
		 */
		protected final Setting<Integer> lightFramePerSecond = new Setting<Integer>("LightFramePerSecond", false, 1);
		/**
		 * [Mania]<br>
		 * <code>SpecialStyle: &lt; style &gt;</code>
		 * @see SpecialStyle
		 */
		protected final Setting<SpecialStyle> specialStyle = new Setting<SpecialStyle>("SpecialStyle", SpecialStyle.NONE);
		/**
		 * [Mania]<br>
		 * <code>ComboBurstStyle: &lt; style &gt;</code>
		 * @see ComboBurstStyle
		 */
		protected final Setting<ComboBurstStyle> comboBurstStyle = new Setting<ComboBurstStyle>("ComboBurstStyle", ComboBurstStyle.RIGHT);
		/**
		 * [Mania]<br>
		 * <code>SplitStages: &lt; 0:1 &gt;</code>
		 */
		protected final Setting<Boolean> splitStages = new Setting<Boolean>("SplitStages", false, true);
		/**
		 * [Mania]<br>
		 * <code>StageSeparation: &lt; space &gt;</code>
		 */
		protected final Setting<Double> stageSeparation = new Setting<Double>("StageSeparation", 40.0D);
		/**
		 * [Mania]<br>
		 * <code>SeparateScore: &lt; 0:1 &gt;</code>
		 */
		protected final Setting<Boolean> separateScore = new Setting<Boolean>("SeparateScore", true);
		/**
		 * [Mania]<br>
		 * <code>KeysUnderNotes: &lt; 0:1 &gt;</code>
		 */
		protected final Setting<Boolean> keysUnderNotes = new Setting<Boolean>("KeysUnderNotes", false);
		/**
		 * [Mania]<br>
		 * <code>UpsideDown: &lt; 0:1 &gt;</code>
		 */
		protected final Setting<Boolean> upsideDown = new Setting<Boolean>("UpsideDown", false);
		/**
		 * [Mania]<br>
		 * <code>KeyFlipWhenUpsideDown: &lt; 0:1 &gt;</code>
		 */
		protected final Setting<Boolean> keyFlipWhenUpsideDown = new Setting<Boolean>("KeyFlipWhenUpsideDown", true);
		/**
		 * [Mania]<br>
		 * <code>NoteFlipWhenUpsideDown: &lt; 0:1 &gt;</code>
		 */
		protected final Setting<Boolean> noteFlipWhenUpsideDown = new Setting<Boolean>("NoteFlipWhenUpsideDown", true);
		/**
		 * [Mania]<br>
		 * <code>NoteBodyStyle: &lt; style &gt;</code>
		 * @see NoteBodyStyle
		 */
		protected final Setting<NoteBodyStyle> noteBodyStyle = new Setting<NoteBodyStyle>("NoteBodyStyle", NoteBodyStyle.CASCADINGTH);
		/**
		 * [Mania]<br>
		 * <code>ColourColumnLine: &lt; rgba color &gt;</code>
		 */
		protected final Setting<Colour> colourColumnLine = new Setting<Colour>("ColourColumnLine", new Colour(255, 255, 255, 255));
		/**
		 * [Mania]<br>
		 * <code>ColourBarline: &lt; rgba color &gt;</code>
		 */
		protected final Setting<Colour> colourBarline = new Setting<Colour>("ColourBarline", new Colour(255, 255, 255, 255));
		/**
		 * [Mania]<br>
		 * <code>ColourJudgementLine: &lt; rgb color &gt;</code>
		 */
		protected final Setting<Colour> colourJudgementLine = new Setting<Colour>("ColourJudgementLine", new Colour(255, 255, 255));
		/**
		 * [Mania]<br>
		 * <code>ColourKeyWarning: &lt; rgb color &gt;</code>
		 */
		protected final Setting<Colour> colourKeyWarning = new Setting<Colour>("ColourKeyWarning", new Colour(0, 0, 0));
		/**
		 * [Mania]<br>
		 * <code>ColourHold: &lt; rgba color &gt;</code>
		 */
		protected final Setting<Colour> colourHold = new Setting<Colour>("ColourHold", new Colour(255, 191, 51, 255));
		/**
		 * [Mania]<br>
		 * <code>ColourBreak: &lt; rgb color &gt;</code>
		 */
		protected final Setting<Colour> colourBreak = new Setting<Colour>("ColourBreak", new Colour(255, 0, 0));
		/**
		 * [Mania]<br>
		 * <code>StageLeft: &lt; image path &gt;</code>
		 */
		protected final Setting<String> stageLeft = new Setting<String>("StageLeft", "mania-stage-left");
		/**
		 * [Mania]<br>
		 * <code>StageRight: &lt; image path &gt;</code>
		 */
		protected final Setting<String> stageRight = new Setting<String>("StageRight", "mania-stage-right");
		/**
		 * [Mania]<br>
		 * <code>StageBottom: &lt; image path &gt;</code>
		 */
		protected final Setting<String> stageBottom = new Setting<String>("StageBottom", "mania-stage-bottom");
		/**
		 * [Mania]<br>
		 * <code>StageHint: &lt; image path &gt;</code>
		 */
		protected final Setting<String> stageHint = new Setting<String>("StageHint", "mania-stage-hint");
		/**
		 * [Mania]<br>
		 * <code>StageLight: &lt; image path &gt;</code>
		 */
		protected final Setting<String> stageLight = new Setting<String>("StageLight", "mania-stage-light");
		/**
		 * [Mania]<br>
		 * <code>LightingN: &lt; image path &gt;</code>
		 */
		protected final Setting<String> lightingN = new Setting<String>("LightingN", "LightingN");
		/**
		 * [Mania]<br>
		 * <code>LightingL: &lt; image path &gt;</code>
		 */
		protected final Setting<String> lightingL = new Setting<String>("LightingL", "LightingL");
		/**
		 * [Mania]<br>
		 * <code>WarningArrow: &lt; image path &gt;</code>
		 */
		protected final Setting<String> warningArrow = new Setting<String>("WarningArrow", "mania-warningarrow");
		/**
		 * [Mania]<br>
		 * <code>Hit0: &lt; image path &gt;</code>
		 */
		protected final Setting<String> hit0 = new Setting<String>("Hit0", "mania-hit0");
		/**
		 * [Mania]<br>
		 * <code>Hit50: &lt; image path &gt;</code>
		 */
		protected final Setting<String> hit50 = new Setting<String>("Hit50", "mania-hit50");
		/**
		 * [Mania]<br>
		 * <code>Hit100: &lt; image path &gt;</code>
		 */
		protected final Setting<String> hit100 = new Setting<String>("Hit100", "mania-hit100");
		/**
		 * [Mania]<br>
		 * <code>Hit200: &lt; image path &gt;</code>
		 */
		protected final Setting<String> hit200 = new Setting<String>("Hit200", "mania-hit200");
		/**
		 * [Mania]<br>
		 * <code>Hit300: &lt; image path &gt;</code>
		 */
		protected final Setting<String> hit300 = new Setting<String>("Hit300", "mania-hit300");
		/**
		 * [Mania]<br>
		 * <code>Hit300g: &lt; image path &gt;</code>
		 */
		protected final Setting<String> hit300g = new Setting<String>("Hit300g", "mania-hit300g");
		/**
		 * Columns specific mania configurations
		 */
		protected Column[] columns;

		/**
		 * Constructs a new ManiaIni for the
		 * given key count
		 * @param keys The key count for this
		 *        Mania configuration
		 */
		private ManiaIni(int keys){
			this.keys = keys;
			columnSpacing.update(fillArray(keys - 1, 0.0D));
			columnWidth.update(fillArray(keys, 30.0D));
			columnLineWidth.update(fillArray(keys + 1, 2.0D));
			lightingNWidth.update(fillArray(keys, 0.0D));
			lightingLWidth.update(fillArray(keys, 0.0D));
			columns = new Column[keys];
			for(int i = 0; i < keys; i++){
				columns[i] = new Column(i + 1);
			}
		}

		/**
		 * Creates an array of size <code>
		 * len</code> and fills it with
		 * <code>value</code>
		 * @param len The length of the array to create
		 * @param value The value to fill the array with
		 * @return The newly created array
		 */
		private static final double[] fillArray(int len, double value){
			double[] array = new double[len];
			for(int i = 0; i < len; i++){
				array[i] = value;
			}
			return array;
		}
		
		/**
		 * Parses the given string and updates the
		 * setting that it represents.
		 * @param line The line to parse
		 * @return The setting that
		 *         was updated or created
		 * @throws IOException When an IOException occurs
		 * @see Setting
		 */
		private Setting<?> parseMania(String line) throws IOException{
			String[] args = line.split(":", 2);
			args[1] = args[1].trim();
			switch(args[0].trim()){
			case "ColumnStart":
				return parseDouble(columnStart, args[1], 0.0D);
			case "ColumnRight":
				return parseDouble(columnRight, args[1], 0.0D);
			case "ColumnSpacing":
				return parseList(columnSpacing, args[1], keys - 1);
			case "ColumnWidth":
				return parseList(columnWidth, args[1], keys);
			case "ColumnLineWidth":
				return parseList(columnLineWidth, args[1], keys + 1);
			case "BarlineHeight":
				return parseDouble(barlineHeight, args[1], 0.0D);
			case "LightingNWidth":
				return parseList(lightingNWidth, args[1], keys);
			case "LightingLWidth":
				return parseList(lightingLWidth, args[1], keys);
			case "WidthForNoteHeightScale":
				return parseDouble(widthForNoteHeightScale, args[1], 0.0D);
			case "HitPosition":
				return parseInt(hitPosition, args[1], 0);
			case "LightPosition":
				return parseInt(lightPosition, args[1], 0);
			case "ScorePosition":
				return parseInt(scorePosition, args[1], 0);
			case "ComboPosition":
				return parseInt(comboPosition, args[1], 0);
			case "JudgementLine":
				return parseBoolean(judgementLine, args[1]);
			case "LightFramePerSecond":
				return parseInt(lightFramePerSecond, args[1], 1);
			case "SpecialStyle":
				return specialStyle.update(SpecialStyle.fromString(args[1]));
			case "ComboBurstStyle":
				return comboBurstStyle.update(ComboBurstStyle.fromString(args[1]));
			case "SplitStages":
				return parseBoolean(splitStages, args[1]);
			case "StageSeparation":
				return parseDouble(stageSeparation, args[1], 0);
			case "SeparateScore":
				return parseBoolean(separateScore, args[1]);
			case "KeysUnderNotes":
				return parseBoolean(keysUnderNotes, args[1]);
			case "UpsideDown":
				return parseBoolean(upsideDown, args[1]);
			case "ColourColumnLine":
				return parseColor(colourColumnLine, args[1]);
			case "ColourBarline":
				return parseColor(colourBarline, args[1]);
			case "ColourJudgementLine":
				return parseColor(colourJudgementLine, args[1]);
			case "ColourKeyWarning":
				return parseColor(colourKeyWarning, args[1]);
			case "ColourHold":
				return parseColor(colourHold, args[1]);
			case "ColourBreak":
				return parseColor(colourBreak, args[1]);
			case "StageLeft":
				return stageLeft.update(args[1]);
			case "StageRight":
				return stageRight.update(args[1]);
			case "StageBottom":
				return stageBottom.update(args[1]);
			case "StageHint":
				return stageHint.update(args[1]);
			case "StageLight":
				return stageLight.update(args[1]);
			case "LightingN":
				return lightingN.update(args[1]);
			case "LightingL":
				return lightingL.update(args[1]);
			case "WarningArrow":
				return warningArrow.update(args[1]);
			case "Hit0":
				return hit0.update(args[1]);
			case "Hit50":
				return hit50.update(args[1]);
			case "Hit100":
				return hit100.update(args[1]);
			case "Hit200":
				return hit200.update(args[1]);
			case "Hit300":
				return hit300.update(args[1]);
			case "Hit300g":
				return hit300g.update(args[1]);
			default:
				if(args[0].startsWith("KeyFlipWhenUpsideDown")){
					args[0] = args[0].substring(21);
					if(args[0].isEmpty()){
						return parseBoolean(keyFlipWhenUpsideDown, args[1]);
					}else if(args[0].endsWith("D")){
						return parseBoolean(columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].keyFlipWhenUpsideDownD, args[1]);
					}else{
						return parseBoolean(columns[Integer.parseInt(args[0])].keyFlipWhenUpsideDown, args[1]);
					}
				}else if(args[0].startsWith("NoteFlipWhenUpsideDown")){
					args[0] = args[0].substring(22);
					if(args[0].isEmpty()){
						return parseBoolean(noteFlipWhenUpsideDown, args[1]);
					}else if(args[0].endsWith("H")){
						return parseBoolean(columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].noteFlipWhenUpsideDownH, args[1]);
					}else if(args[0].endsWith("L")){
						return parseBoolean(columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].noteFlipWhenUpsideDownL, args[1]);
					}else if(args[0].endsWith("T")){
						return parseBoolean(columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].noteFlipWhenUpsideDownT, args[1]);
					}else{
						return parseBoolean(columns[Integer.parseInt(args[0])].noteFlipWhenUpsideDown, args[1]);
					}
				}else if(args[0].startsWith("NoteBodyStyle")){
					args[0] = args[0].substring(13);
					if(args[0].isEmpty()){
						return noteBodyStyle.update(NoteBodyStyle.fromString(args[1]));
					}else{
						return columns[Integer.parseInt(args[0])].noteBodyStyle.update(NoteBodyStyle.fromString(args[1]));
					}
				}else if(args[0].startsWith("ColourLight")){
					return parseColor(columns[Integer.parseInt(args[0].substring(11)) - 1].colourLight, args[1]);
				}else if(args[0].startsWith("Colour")){
					return parseColor(columns[Integer.parseInt(args[0].substring(6)) - 1].colour, args[1]);
				}else if(args[0].startsWith("KeyImage")){
					args[0] = args[0].substring(8);
					if(args[0].endsWith("D")){
						return columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].keyImageD.update(args[1]);
					}else{
						return columns[Integer.parseInt(args[0])].keyImage.update(args[1]);
					}
				}else if(args[0].startsWith("NoteImage")){
					args[0] = args[0].substring(9);
					if(args[0].endsWith("H")){
						return columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].noteImageH.update(args[1]);
					}else if(args[0].endsWith("T")){
						return columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].noteImageT.update(args[1]);
					}else if(args[0].endsWith("L")){
						return columns[Integer.parseInt(args[0].substring(0, args[0].length() - 1))].noteImageL.update(args[1]);
					}else{
						return columns[Integer.parseInt(args[0])].noteImage.update(args[1]);
					}
				}
				throw new IllegalArgumentException("Illegal command in the [Mania] section: " + line);
			}
		}

		/**
		 * Gets a list of all the setting both
		 * enabled and disabled that are allowed
		 * for this Mania configuration.
		 * @return A list of all the
		 *         Mania configuration settings
		 */
		private List<Setting<?>> getAll(){
			List<Setting<?>> all = new ArrayList<Setting<?>>();
			all.add(barlineHeight);
			all.add(colourBarline);
			all.add(colourBreak);
			all.add(colourColumnLine);
			all.add(colourHold);
			all.add(colourJudgementLine);
			all.add(colourKeyWarning);
			all.add(columnLineWidth);
			all.add(columnRight);
			all.add(columnSpacing);
			all.add(columnStart);
			all.add(columnWidth);
			all.add(comboBurstStyle);
			all.add(comboPosition);
			all.add(hit0);
			all.add(hit50);
			all.add(hit100);
			all.add(hit200);
			all.add(hit300);
			all.add(hit300g);			
			all.add(hitPosition);
			all.add(judgementLine);
			all.add(keyFlipWhenUpsideDown);
			all.add(keysUnderNotes);
			all.add(lightingL);
			all.add(lightingLWidth);
			all.add(lightingN);
			all.add(lightingNWidth);
			all.add(lightPosition);
			all.add(noteBodyStyle);
			all.add(noteFlipWhenUpsideDown);
			all.add(scorePosition);
			all.add(separateScore);
			all.add(specialStyle);
			all.add(splitStages);
			all.add(stageBottom);
			all.add(stageHint);
			all.add(stageLeft);
			all.add(stageRight);
			all.add(stageLight);
			all.add(stageSeparation);
			all.add(upsideDown);
			all.add(warningArrow);
			all.add(widthForNoteHeightScale);
			for(Column c : columns){
				all.add(c.colour);
				all.add(c.colourLight);
				all.add(c.keyFlipWhenUpsideDown);
				all.add(c.keyFlipWhenUpsideDownD);
				all.add(c.keyImage);
				all.add(c.keyImageD);
				all.add(c.noteBodyStyle);
				all.add(c.noteFlipWhenUpsideDown);
				all.add(c.noteFlipWhenUpsideDownH);
				all.add(c.noteFlipWhenUpsideDownL);
				all.add(c.noteFlipWhenUpsideDownT);
				all.add(c.noteImage);
				all.add(c.noteImageH);
				all.add(c.noteImageL);
				all.add(c.noteImageT);
			}
			return all;
		}
		
		/**
		 * Class that reflects all the
		 * mania setting for a specific
		 * key count and column
		 * @author Roan
		 */
		protected static final class Column{
			/**
			 * [Mania]<br>
			 * <code>KeyFlipWhenUpsideDown#: &lt; 0:1 &gt;</code>
			 */
			protected final Setting<Boolean> keyFlipWhenUpsideDown;
			/**
			 * [Mania]<br>
			 * <code>KeyFlipWhenUpsideDown#D: &lt; 0:1 &gt;</code>
			 */
			protected final Setting<Boolean> keyFlipWhenUpsideDownD;
			/**
			 * [Mania]<br>
			 * <code>NoteFlipWhenUpsideDown#: &lt; 0:1 &gt;</code>
			 */
			protected final Setting<Boolean> noteFlipWhenUpsideDown;
			/**
			 * [Mania]<br>
			 * <code>NoteFlipWhenUpsideDown#H: &lt; 0:1 &gt;</code>
			 */
			protected final Setting<Boolean> noteFlipWhenUpsideDownH;
			/**
			 * [Mania]<br>
			 * <code>NoteFlipWhenUpsideDown#L: &lt; 0:1 &gt;</code>
			 */
			protected final Setting<Boolean> noteFlipWhenUpsideDownL;
			/**
			 * [Mania]<br>
			 * <code>NoteFlipWhenUpsideDown#T: &lt; 0:1 &gt;</code>
			 */
			protected final Setting<Boolean> noteFlipWhenUpsideDownT;
			/**
			 * [Mania]<br>
			 * <code>NoteBodyStyle#: &lt; style &gt;</code>
			 * @see NoteBodyStyle
			 */
			protected final Setting<NoteBodyStyle> noteBodyStyle;
			/**
			 * [Mania]<br>
			 * <code>Colour#: &lt; rgba color &gt;</code>
			 */
			protected final Setting<Colour> colour;
			/**
			 * [Mania]<br>
			 * <code>ColourLight#: &lt; rgb color &gt;</code>
			 */
			protected final Setting<Colour> colourLight;
			/**
			 * [Mania]<br>
			 * <code>KeyImage#: &lt; path &gt;</code>
			 */
			protected final Setting<String> keyImage;
			/**
			 * [Mania]<br>
			 * <code>KeyImage#D: &lt; path &gt;</code>
			 */
			protected final Setting<String> keyImageD;
			/**
			 * [Mania]<br>
			 * <code>NoteImage#: &lt; path &gt;</code>
			 */
			protected final Setting<String> noteImage;
			/**
			 * [Mania]<br>
			 * <code>NoteImage#H: &lt; path &gt;</code>
			 */
			protected final Setting<String> noteImageH;
			/**
			 * [Mania]<br>
			 * <code>NoteImage#L: &lt; path &gt;</code>
			 */
			protected final Setting<String> noteImageL;
			/**
			 * [Mania]<br>
			 * <code>NoteImage#T: &lt; path &gt;</code>
			 */
			protected final Setting<String> noteImageT;
			
			/**
			 * Constructs a new column setting object
			 * for the given key number.
			 * @param key The column these setting are for.
			 */
			private Column(int key){
				colour = new Setting<Colour>("Colour" + key, new Colour(0, 0, 0, 255));
				colourLight = new Setting<Colour>("ColourLight" + key, new Colour(255, 255, 255));
				
				key--;
				
				keyFlipWhenUpsideDown = new Setting<Boolean>("KeyFlipWhenUpsideDown" + key, false, true);
				keyFlipWhenUpsideDownD = new Setting<Boolean>("KeyFlipWhenUpsideDown" + key + "D", false, true);
				noteFlipWhenUpsideDown = new Setting<Boolean>("NoteFlipWhenUpsideDown" + key, false, true);
				noteFlipWhenUpsideDownH = new Setting<Boolean>("NoteFlipWhenUpsideDown" + key + "H", false, true);
				noteFlipWhenUpsideDownL = new Setting<Boolean>("NoteFlipWhenUpsideDown" + key + "L", false, true);
				noteFlipWhenUpsideDownT = new Setting<Boolean>("NoteFlipWhenUpsideDown" + key + "T", false, true);

				noteBodyStyle = new Setting<NoteBodyStyle>("NoteBodyStyle" + key, false, NoteBodyStyle.CASCADINGTH);

				keyImage = new Setting<String>("KeyImage" + key, false, "");
				keyImageD = new Setting<String>("KeyImage" + key + "D", false, "");
				noteImage = new Setting<String>("NoteImage" + key, false, "");
				noteImageH = new Setting<String>("NoteImage" + key + "H", false, "");
				noteImageL = new Setting<String>("NoteImage" + key + "L", false, "");
				noteImageT = new Setting<String>("NoteImage" + key + "T", false, "");
			}
		}
	}
	
	/**
	 * Enum for all the different SliderStyle options
	 * @author Roan
	 * @see Printable
	 */
	protected enum SliderStyle implements Printable{
		/**
		 * Segments style
		 */
		SEGMENTS(1, "Segments"),
		/**
		 * Gradients style
		 */
		GRADIENT(2, "Gradient");
		
		/**
		 * Display name of this option
		 */
		private final String name;
		/**
		 * In file ID of this 
		 */
		private final int id;
		
		/**
		 * Constructs a new SliderStyle
		 * with the given id and name
		 * @param id The id of the setting
		 * @param name The display name of the setting
		 */
		private SliderStyle(int id, String name){
			this.name = name;
			this.id = id;
		}
		
		/**
		 * Parses the given string
		 * to a SliderStyle
		 * @param str The input string to parse
		 * @return The SliderStyle the input was
		 *         parsed to
		 */
		private static final SliderStyle fromString(String str){
			switch(str){
			case "1":
				return SEGMENTS;
			case "2":
				return GRADIENT;
			default:
				usedDefault.add("SliderStyle");
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
	
	/**
	 * Enum for all the different SpecialStyle options
	 * @author Roan
	 * @see Printable
	 */
	protected enum SpecialStyle implements Printable{
		/**
		 * No special style
		 */
		NONE(0, "None"),
		/**
		 * Left lane SP style
		 */
		LEFT(1, "Left lane SP - outer lanes DP"),
		/**
		 * Right lane SP style
		 */
		RIGHT(2, "Right lane SP - outer lanes DP");
		
		/**
		 * The display name for this setting
		 */
		private final String name;
		/**
		 * The file ID for this setting
		 */
		private final int id;
		
		/**
		 * Constructs a new SpecialStyle with
		 * the given ID and display name
		 * @param id The ID for this setting
		 * @param name The display name for this setting
		 */
		private SpecialStyle(int id, String name){
			this.name = name;
			this.id = id;
		}
		
		/**
		 * Parses the given string to a SpecialStyle
		 * @param line The input string to parse
		 * @return The SpecialStyle parsed from the given input string
		 */
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
				usedDefault.add("SpecialStyle");
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
	
	/**
	 * Enum for all the different ComboBurstStyle options
	 * @author Roan
	 * @see Printable
	 */
	protected enum ComboBurstStyle implements Printable{
		/**
		 * Left setting
		 */
		LEFT(0, "Left"),
		/**
		 * Right setting
		 */
		RIGHT(1, "Right"),
		/**
		 * Both setting
		 */
		BOTH(2, "Both");
		
		/**
		 * The display name for this setting
		 */
		private final String name;
		/**
		 * The file ID for this setting
		 */
		private final int id;
		
		/**
		 * Constructs a new ComboBurstStyle with
		 * the given ID and display name
		 * @param id The ID for this ComboBurstStyle
		 * @param name The display name for this setting
		 */
		private ComboBurstStyle(int id, String name){
			this.name = name;
			this.id = id;
		}
		
		/**
		 * Parses the given input for a ComboBurstStyle
		 * @param line The input to parse
		 * @return The ComboBurstStyle parsed from the given input string
		 */
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
				usedDefault.add("ComboBurstStyle");
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
	
	/**
	 * Enum for all the different NoteBodyStyle options
	 * @author Roan
	 * @see Printable
	 */
	protected enum NoteBodyStyle implements Printable{
		/**
		 * Stretched option
		 */
		STRETCHED(0, "Stretched to fit the whole length"),
		/**
		 * Cascading tail to head option
		 */
		CASCADINGTH(1, "Cascading from tail to head"),
		/**
		 * Cascading head to tail option
		 */
		CASCADINGHT(2, "Cascading from head to tail");

		/**
		 * The display name for this setting
		 */
		private final String name;
		/**
		 * The file ID for this setting
		 */
		private final int id;
		
		/**
		 * Constructs a new NoteBodyStyle with
		 * the given ID and display name
		 * @param id The ID for this NoteBodyStyle
		 * @param name The display name for this setting
		 */
		private NoteBodyStyle(int id, String name){
			this.name = name;
			this.id = id;
		}
		
		/**
		 * Parses the given input for a NoteBodyStyle
		 * @param line The input to parse
		 * @return The NoteBodyStyle parsed from the given input string
		 */
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

	/**
	 * Interface that indicates that the
	 * object has a special {@link #print()}
	 * method that returns a configuration
	 * style version of the data.
	 * @author Roan
	 */
	protected static abstract interface Printable{
		/**
		 * Gets the configuration style formatted
		 * data for this object
		 * @return The configuration style formatted
		 *         data for this object
		 */
		public abstract String print();
	}
}

package me.roan.osuskinchecker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import me.roan.osuskinchecker.SkinIni.ManiaIni;
import me.roan.osuskinchecker.SkinIni.SliderStyle;
import me.roan.osuskinchecker.SkinIni.Version;
import me.roan.osuskinchecker.SplitLayout.ScrollPane;

public class SkinIniTab extends JTabbedPane{
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -5575803475931849256L;

	public void init(SkinIni ini){
		removeAll();
		add("General", new GeneralTab(ini));
		add("Gameplay", new GameplayTab(ini));
		add("osu! Standard", new StandardTab(ini));
		add("osu! Catch", new CtbTab(ini));
		add("osu! Mania", new ManiaTab(ini));
	}

	private static final class GeneralTab extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -4554355388349363415L;

		private GeneralTab(SkinIni ini){
			super(new BorderLayout());
			JPanel content = new ScrollPane();
			BoxLayout layout = new BoxLayout(content, BoxLayout.Y_AXIS);
			content.setLayout(layout);
			this.add(new JScrollPane(content), BorderLayout.CENTER);

			content.add(Box.createVerticalStrut(2));
			content.add(new TextEditor("Skin name", ini.name));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new TextEditor("Author name", ini.author));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ComboBoxEditor<Version>("Version", ini.version, Version.values()));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("CursorExpand", "should the cursor expand when clicking", ini.cursorExpand));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("CursorCentre", "should the cursor be centered", "false implies the top left corner", ini.cursorCentre));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("CursorRotate", "should the cursor rotate", ini.cursorRotate));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("CursorTrailRotate", "should the cursor trail rotate",  ini.cursorTrailRotate));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new IntegerSpinnerEditor("AnimationFramerate", "how many frame per second for most animations", ini.animationFramerate, 1));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("SongSelectActiveText", "what colour should the text of active panels be tinted", ini.songSelectActiveText));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("SongSelectInactiveText", "what colour should the text of inactive panels be tinted", ini.songSelectInactiveText));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("MenuGlow", "what colour should the spectrum bars be coloured in",  ini.menuGlow));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new TextEditor("ScorePrefix", "what prefix to use for the score numbers", ini.scorePrefix));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new IntegerSpinnerEditor("ScoreOverlap", "by how many pixels should the score numbers overlap", ini.scoreOverlap));
			content.add(Box.createVerticalStrut(2));

			content.add(new JPanel(new BorderLayout()));
		}
	}

	private static final class StandardTab extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -639789728841322306L;

		private StandardTab(SkinIni ini){
			super(new BorderLayout());
			JPanel content = new ScrollPane();
			BoxLayout layout = new BoxLayout(content, BoxLayout.Y_AXIS);
			content.setLayout(layout);
			this.add(new JScrollPane(content), BorderLayout.CENTER);

			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("HitCircleOverlayAboveNumber", "should the hitcircleoverlay be drawn above the numbers", "false implies that they will be drawn below the overlay", ini.hitCircleOverlayAboveNumber));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ComboBoxEditor<SliderStyle>("SliderStyle", "what filling to use for sliders", ini.sliderStyle, SliderStyle.values()));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("SliderBallFlip", "should the sliderball flip at the reverse arrow", ini.sliderBallFlip));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("AllowSliderBallTint", "should the sliderball be tinted in combo colours", ini.allowSliderBallTint));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("SpinnerNoBlink", "should the highest bar of the metre always be visible", ini.spinnerNoBlink));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("SpinnerFadePlayfield", "should the spinner add black bars during spins", ini.spinnerFadePlayfield));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("SpinnerFrequencyModulate", "should the spinnerspin sound pitch go up while spinning", ini.spinnerFrequencyModulate));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("SliderBall", "what colour should the slider ball be coloured", ini.sliderBall));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("SliderTrackOverride", "what colour should the slider body be coloured", ini.sliderTrackOverride, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("SliderBorder", "what colour should be used for sliderborders", ini.sliderBorder));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("SpinnerBackground", "what colour should be added to the spinner-background", ini.spinnerBackground));
			content.add(Box.createVerticalStrut(2));

			content.add(new JPanel(new BorderLayout()));
		}
	}

	private static final class GameplayTab extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -5006508447206574607L;

		private GameplayTab(SkinIni ini){
			super(new BorderLayout());
			JPanel content = new ScrollPane();
			BoxLayout layout = new BoxLayout(content, BoxLayout.Y_AXIS);
			content.setLayout(layout);
			this.add(new JScrollPane(content), BorderLayout.CENTER);

			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("LayeredHitSounds", "should hitnormal sounds always be played", ini.layeredHitSounds));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("ComboBurstRandom", "should combursts be shown in a random order", "false implies that they will appear in order", ini.comboBurstRandom));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new SplitLayout());
				JCheckBox enabled = new JCheckBox("", ini.customComboBurstSounds != null);
				panel.add(new JLabel(" CustomComboBurstSounds (on which combo marks should comboburst sounds play): "));
				JPanel list = new JPanel(new BorderLayout());
				list.add(enabled, BorderLayout.LINE_START);
				list.add(new JLabel("(comma separated)"), BorderLayout.LINE_END);
				ListField field = new ListField(ini.customComboBurstSounds.getValue());
				enabled.addActionListener((e)->{
					ini.customComboBurstSounds.setEnabled(enabled.isSelected());
				});
				field.addActionListener((e)->{
					ini.customComboBurstSounds.update(field.getText());
				});
				list.add(field, BorderLayout.CENTER);
				panel.add(list);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("StarBreakAdditive", "what colour should be added to star2 during breaks", ini.starBreakAdditive));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("InputOverlayText", "what colour should the input keys be tinted", ini.inputOverlayText));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("Combo1", "what colour is used for the last combo", ini.combo1));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("Combo2", "what colour is used for the first combo", ini.combo2, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("Combo3", "what colour is used for the second combo", ini.combo3, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("Combo4", "what colour is used for the third combo", ini.combo4, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("Combo5", "what colour is used for the fourth combo", ini.combo5, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("Combo6", "what colour is used for the fifth combo", ini.combo6, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("Combo7", "what colour is used for the sixth combo", ini.combo7, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("Combo8", "what colour is used for the seventh combo", ini.combo8, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new SplitLayout());
				panel.add(new JLabel(" Hit Circle Prefix (what prefix to use for hitcircle numbers): "));
				JTextField field = new JTextField(ini.hitCirclePrefix);
				field.addKeyListener(new KeyListener(){

					@Override
					public void keyTyped(KeyEvent e){
					}

					@Override
					public void keyPressed(KeyEvent e){
					}

					@Override
					public void keyReleased(KeyEvent e){
						ini.hitCirclePrefix = field.getText();
					}
				});
				panel.add(field);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new SplitLayout());
				panel.add(new JLabel(" Hit Circle Overlap (by how many pixels should the hitcircle numbers overlap): "));
				JSpinner spinner = new JSpinner(new SpinnerNumberModel(ini.hitCircleOverlap, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
				spinner.addChangeListener((e)->{
					ini.hitCircleOverlap = (Integer)spinner.getValue();
				});
				panel.add(spinner);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new SplitLayout());
				panel.add(new JLabel(" Combo Prefix (what prefix to use for combo numbers): "));
				JTextField field = new JTextField(ini.comboPrefix);
				field.addKeyListener(new KeyListener(){

					@Override
					public void keyTyped(KeyEvent e){
					}

					@Override
					public void keyPressed(KeyEvent e){
					}

					@Override
					public void keyReleased(KeyEvent e){
						ini.comboPrefix = field.getText();
					}
				});
				panel.add(field);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new SplitLayout());
				panel.add(new JLabel(" Combo Overlap (by how many pixels should the combo numbers overlap): "));
				JSpinner spinner = new JSpinner(new SpinnerNumberModel(ini.comboOverlap, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
				spinner.addChangeListener((e)->{
					ini.comboOverlap = (Integer)spinner.getValue();
				});
				panel.add(spinner);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));

			content.add(new JPanel(new BorderLayout()));
		}
	}

	private static final class CtbTab extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -5006508447206574607L;

		private CtbTab(SkinIni ini){
			super(new BorderLayout());
			JPanel content = new ScrollPane();
			BoxLayout layout = new BoxLayout(content, BoxLayout.Y_AXIS);
			content.setLayout(layout);
			this.add(new JScrollPane(content), BorderLayout.CENTER);
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new SplitLayout());
				panel.add(new JLabel(" Hyper Dash (what colour is used for the dash): "));
				panel.add(new ColorSelector(ini.hyperDash, (color)->{
					ini.hyperDash = color;
				}));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new SplitLayout());
				JCheckBox enabled = new JCheckBox("", ini.hyperDashFruit != null);
				JPanel settings = new JPanel(new BorderLayout());
				settings.add(enabled, BorderLayout.LINE_START);
				panel.add(new JLabel(" Hyper Dash Fruit (what colour is used for the fruits): "));
				ColorSelector selector = new ColorSelector(ini.hyperDashFruit, (color)->{
					if(enabled.isSelected()){
						ini.hyperDashFruit = color;
					}
				});
				settings.add(selector, BorderLayout.CENTER);
				enabled.addActionListener((e)->{
					if(enabled.isSelected()){
						ini.hyperDashFruit = selector.color;
					}else{
						ini.hyperDashFruit = null;
					}
				});
				panel.add(settings);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new SplitLayout());
				JCheckBox enabled = new JCheckBox("", ini.hyperDashAfterImage != null);
				JPanel settings = new JPanel(new BorderLayout());
				settings.add(enabled, BorderLayout.LINE_START);
				panel.add(new JLabel(" Hyper Dash After Image (what colour is used for the after images): "));
				ColorSelector selector = new ColorSelector(ini.hyperDashAfterImage, (color)->{
					if(enabled.isSelected()){
						ini.hyperDashAfterImage = color;
					}
				});
				settings.add(selector, BorderLayout.CENTER);
				enabled.addActionListener((e)->{
					if(enabled.isSelected()){
						ini.hyperDashAfterImage = selector.color;
					}else{
						ini.hyperDashAfterImage = null;
					}
				});
				panel.add(settings);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));

			content.add(new JPanel(new BorderLayout()));
		}
	}

	private static final class ManiaTab extends JTabbedPane{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 337165984317388690L;

		private ManiaTab(SkinIni ini){
			for(int i = 1; i <= 10; i++){
				ManiaKeyTab tab = new ManiaKeyTab();
				if(ini.mania[i - 1] != null){
					tab.init(ini.mania[i - 1], i);
				}else{
					JButton add = new JButton("Create " + i + "K mania configuration");
					final int keys = i;
					add.addActionListener((e)->{
						ini.createManiaConfiguration(keys);
						tab.init(ini.mania[keys - 1], keys);
						tab.revalidate();
						tab.repaint();
					});
					tab.add(add);
				}
				add(i + (i == 1 ? " Key" : " Keys"), tab);
			}
		}

		private static final class ManiaKeyTab extends JPanel{
			/**
			 * Serial ID
			 */
			private static final long serialVersionUID = -6820855921720231002L;

			private void init(ManiaIni ini, int keys){
				removeAll();
				setLayout(new BorderLayout());
				JPanel content = new ScrollPane();
				BoxLayout layout = new BoxLayout(content, BoxLayout.Y_AXIS);
				content.setLayout(layout);
				this.add(new JScrollPane(content), BorderLayout.CENTER);
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Column Start (where does the left column start): "));
					JSpinner spinner = new JSpinner(new SpinnerNumberModel(ini.columnStart, 0.0D, Double.MAX_VALUE, 1.0D));
					spinner.addChangeListener((event)->{
						ini.columnStart = (double)spinner.getValue();
					});
					panel.add(spinner);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Column Right (up to which point can columns be drawn): "));
					JSpinner spinner = new JSpinner(new SpinnerNumberModel(ini.columnRight, 0.0D, Double.MAX_VALUE, 1.0D));
					spinner.addChangeListener((event)->{
						ini.columnRight = (double)spinner.getValue();
					});
					panel.add(spinner);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				if(ini.keys != 1){
					content.add(Box.createVerticalStrut(2));
					{
						JPanel panel = new JPanel(new SplitLayout());
						panel.add(new JLabel(" Column Spacing (distance between columns): "));
						panel.add(new DoubleArray(ini.columnSpacing));
						content.add(panel);
					}
					content.add(Box.createVerticalStrut(2));
					content.add(new JSeparator());
				}
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Column Width (widths of the columns): "));
					panel.add(new DoubleArray(ini.columnWidth));
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Column Line Width (widths of the column separators): "));
					panel.add(new DoubleArray(ini.columnLineWidth));
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Barline Height (thickness of the barline): "));
					JSpinner spinner = new JSpinner(new SpinnerNumberModel(ini.barlineHeight, 0.0D, Double.MAX_VALUE, 1.0D));
					spinner.addChangeListener((event)->{
						ini.barlineHeight = (double)spinner.getValue();
					});
					panel.add(spinner);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.lightingNWidth != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Lighting N Width (widths of LightingN for the columns): "));
					final double[] array = new double[ini.keys];
					settings.add(new DoubleArray(array));
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.lightingNWidth = array;
						}else{
							ini.lightingNWidth = null;
						}
					});
					panel.add(settings);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.lightingLWidth != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Lighting L Width (widths of LightingL for the columns): "));
					final double[] array = new double[ini.keys];
					settings.add(new DoubleArray(array));
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.lightingLWidth = array;
						}else{
							ini.lightingLWidth = null;
						}
					});
					panel.add(settings);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.widthForNoteHeightScale != -1.0D);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Width For Note Height Scale (height for all notes if columns have individual widths): "));
					JSpinner spinner = new JSpinner(new SpinnerNumberModel(Math.max(ini.widthForNoteHeightScale, 0.0D), 0.0D, Double.MAX_VALUE, 1.0D));
					spinner.addChangeListener((event)->{
						if(enabled.isSelected()){
							ini.widthForNoteHeightScale = (double)spinner.getValue();
						}
					});
					settings.add(spinner, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.widthForNoteHeightScale = (double)spinner.getValue();
						}else{
							ini.widthForNoteHeightScale = -1.0D;
						}
					});
					panel.add(settings);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Hit Position (at what height should the judgement line be drawn): "));
					JSpinner spinner = new JSpinner(new SpinnerNumberModel(ini.hitPosition, 0, Integer.MAX_VALUE, 1));
					spinner.addChangeListener((event)->{
						ini.hitPosition = (int)spinner.getValue();
					});
					panel.add(spinner);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Light Position (at what height should the stage lights be drawn): "));
					JSpinner spinner = new JSpinner(new SpinnerNumberModel(ini.lightPosition, 0, Integer.MAX_VALUE, 1));
					spinner.addChangeListener((event)->{
						ini.lightPosition = (int)spinner.getValue();
					});
					panel.add(spinner);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Score Position (at what height should the hitbursts be drawn): "));
					JSpinner spinner = new JSpinner(new SpinnerNumberModel(ini.scorePosition, 0, Integer.MAX_VALUE, 1));
					spinner.addChangeListener((event)->{
						ini.scorePosition = (int)spinner.getValue();
					});
					panel.add(spinner);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Combo Position (at what height should the combo be drawn): "));
					JSpinner spinner = new JSpinner(new SpinnerNumberModel(ini.comboPosition, 0, Integer.MAX_VALUE, 1));
					spinner.addChangeListener((event)->{
						ini.comboPosition = (int)spinner.getValue();
					});
					panel.add(spinner);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Judgement Line (should a solid line be drawn above the StageHint): "));
					JCheckBox box = new JCheckBox("", ini.judgementLine);
					panel.add(box);
					box.addActionListener(e->{
						ini.judgementLine = box.isSelected();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				if(ini.keys % 2 == 0 && ini.keys >= 4){
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					{
						JPanel panel = new JPanel(new SplitLayout());
						panel.add(new JLabel(" Special Style (what special style (if avaible) should be used for this keycount): "));
						JComboBox<String> box = new JComboBox<String>(new String[]{
							"None",
							"Left lane SP - outer lanes DP",
							"Right lane SP - outer lanes DP"
						});
						box.setSelectedIndex(ini.specialStyle);
						panel.add(box);
						box.addActionListener(e->{
							ini.specialStyle = box.getSelectedIndex();
						});
						content.add(panel);
					}
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Combo Burst Style (on what side should combobursts appear): "));
					JComboBox<String> box = new JComboBox<String>(new String[]{
						"Left",
						"Right",
						"Both"
					});
					box.setSelectedIndex(ini.comboBurstStyle);
					panel.add(box);
					box.addActionListener(e->{
						ini.comboBurstStyle = box.getSelectedIndex();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.splitStages != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Split Stages (should the stage be split into 2 stages): "));
					JComboBox<String> box = new JComboBox<String>(new String[]{
						"No splitting / forced SP",
						"Splitting / forced DP"
					});
					box.setSelectedIndex(ini.splitStages == null ? 0 : (ini.splitStages ? 1 : 0));
					box.addActionListener((event)->{
						if(enabled.isSelected()){
							ini.splitStages = box.getSelectedIndex() == 1;
						}
					});
					settings.add(box, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.splitStages = box.getSelectedIndex() == 1;
						}else{
							ini.splitStages = null;
						}
					});
					panel.add(settings);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Stage Separation (when splitted what should be the distance between the stages): "));
					JSpinner spinner = new JSpinner(new SpinnerNumberModel(ini.stageSeparation, 0.0D, Double.MAX_VALUE, 1.0D));
					spinner.addChangeListener((event)->{
						ini.stageSeparation = (double)spinner.getValue();
					});
					panel.add(spinner);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Separate Score (should hitburst only be shown on the stage it was scored): "));
					JCheckBox box = new JCheckBox("", ini.separateScore);
					panel.add(box);
					box.addActionListener(e->{
						ini.separateScore = box.isSelected();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Keys Under Notes (should keys be covered by notes when passed by them): "));
					JCheckBox box = new JCheckBox("", ini.keysUnderNotes);
					panel.add(box);
					box.addActionListener(e->{
						ini.keysUnderNotes = box.isSelected();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Upside Down (should the stage be flipped): "));
					JCheckBox box = new JCheckBox("", ini.upsideDown);
					panel.add(box);
					box.addActionListener(e->{
						ini.upsideDown = box.isSelected();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Key Flip When Upside Down (should keys be flipped when the stage is flipped): "));
					JCheckBox box = new JCheckBox("", ini.keyFlipWhenUpsideDown);
					panel.add(box);
					box.addActionListener(e->{
						ini.keyFlipWhenUpsideDown = box.isSelected();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.columns[col].keyFlipWhenUpsideDown != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Key Flip When Upside Down " + col + " (should the unpressed key in column " + (col + 1) + " be flipped when the stage is flipped): "));
					JComboBox<String> box = new JComboBox<String>(new String[]{
						"Don't flip",
						"Flipped"
					});
					box.setSelectedIndex(ini.columns[i].keyFlipWhenUpsideDown == null ? 1 : (ini.columns[col].keyFlipWhenUpsideDown ? 1 : 0));
					box.addActionListener((event)->{
						if(enabled.isSelected()){
							ini.columns[col].keyFlipWhenUpsideDown = box.getSelectedIndex() == 1;
						}
					});
					settings.add(box, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].keyFlipWhenUpsideDown = box.getSelectedIndex() == 1;
						}else{
							ini.columns[col].keyFlipWhenUpsideDown = null;
						}
					});
					panel.add(settings);
					content.add(panel);
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.columns[col].keyFlipWhenUpsideDownD != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Key Flip When Upside Down " + col + "D (should the pressed key in column " + (col + 1) + " be flipped when the stage is flipped): "));
					JComboBox<String> box = new JComboBox<String>(new String[]{
						"Don't flip",
						"Flipped"
					});
					box.setSelectedIndex(ini.columns[i].keyFlipWhenUpsideDownD == null ? 1 : (ini.columns[col].keyFlipWhenUpsideDownD ? 1 : 0));
					box.addActionListener((event)->{
						if(enabled.isSelected()){
							ini.columns[col].keyFlipWhenUpsideDownD = box.getSelectedIndex() == 1;
						}
					});
					settings.add(box, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].keyFlipWhenUpsideDownD = box.getSelectedIndex() == 1;
						}else{
							ini.columns[col].keyFlipWhenUpsideDownD = null;
						}
					});
					panel.add(settings);
					content.add(panel);
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Note Flip When Upside Down (should notes be flipped when the stage is flipped): "));
					JCheckBox box = new JCheckBox("", ini.noteFlipWhenUpsideDown);
					panel.add(box);
					box.addActionListener(e->{
						ini.noteFlipWhenUpsideDown = box.isSelected();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.columns[col].noteFlipWhenUpsideDown != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Note Flip When Upside Down " + col + " (should the note in column " + (col + 1) + " be flipped when the stage is flipped): "));
					JComboBox<String> box = new JComboBox<String>(new String[]{
						"Don't flip",
						"Flipped"
					});
					box.setSelectedIndex(ini.columns[i].noteFlipWhenUpsideDown == null ? 1 : (ini.columns[col].noteFlipWhenUpsideDown ? 1 : 0));
					box.addActionListener((event)->{
						if(enabled.isSelected()){
							ini.columns[col].noteFlipWhenUpsideDown = box.getSelectedIndex() == 1;
						}
					});
					settings.add(box, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].noteFlipWhenUpsideDown = box.getSelectedIndex() == 1;
						}else{
							ini.columns[col].noteFlipWhenUpsideDown = null;
						}
					});
					panel.add(settings);
					content.add(panel);
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.columns[col].noteFlipWhenUpsideDownH != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Note Flip When Upside Down " + col + "H (should the head of the note in column " + (col + 1) + " be flipped when the stage is flipped): "));
					JComboBox<String> box = new JComboBox<String>(new String[]{
						"Don't flip",
						"Flipped"
					});
					box.setSelectedIndex(ini.columns[i].noteFlipWhenUpsideDownH == null ? 1 : (ini.columns[col].noteFlipWhenUpsideDownH ? 1 : 0));
					box.addActionListener((event)->{
						if(enabled.isSelected()){
							ini.columns[col].noteFlipWhenUpsideDownH = box.getSelectedIndex() == 1;
						}
					});
					settings.add(box, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].noteFlipWhenUpsideDownH = box.getSelectedIndex() == 1;
						}else{
							ini.columns[col].noteFlipWhenUpsideDownH = null;
						}
					});
					panel.add(settings);
					content.add(panel);
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.columns[col].noteFlipWhenUpsideDownL != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Note Flip When Upside Down " + col + "L (should the body of the note in column " + (col + 1) + " be flipped when the stage is flipped): "));
					JComboBox<String> box = new JComboBox<String>(new String[]{
						"Don't flip",
						"Flipped"
					});
					box.setSelectedIndex(ini.columns[i].noteFlipWhenUpsideDownL == null ? 1 : (ini.columns[col].noteFlipWhenUpsideDownL ? 1 : 0));
					box.addActionListener((event)->{
						if(enabled.isSelected()){
							ini.columns[col].noteFlipWhenUpsideDownL = box.getSelectedIndex() == 1;
						}
					});
					settings.add(box, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].noteFlipWhenUpsideDownL = box.getSelectedIndex() == 1;
						}else{
							ini.columns[col].noteFlipWhenUpsideDownL = null;
						}
					});
					panel.add(settings);
					content.add(panel);
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.columns[col].noteFlipWhenUpsideDownT != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Note Flip When Upside Down " + col + "T (should the tail of the note in column " + (col + 1) + " be flipped when the stage is flipped): "));
					JComboBox<String> box = new JComboBox<String>(new String[]{
						"Don't flip",
						"Flipped"
					});
					box.setSelectedIndex(ini.columns[i].noteFlipWhenUpsideDownT == null ? 1 : (ini.columns[col].noteFlipWhenUpsideDownT ? 1 : 0));
					box.addActionListener((event)->{
						if(enabled.isSelected()){
							ini.columns[col].noteFlipWhenUpsideDownT = box.getSelectedIndex() == 1;
						}
					});
					settings.add(box, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].noteFlipWhenUpsideDownT = box.getSelectedIndex() == 1;
						}else{
							ini.columns[col].noteFlipWhenUpsideDownT = null;
						}
					});
					panel.add(settings);
					content.add(panel);
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Note Body Style (What style should be used for all the hold note bodies): "));
					JComboBox<String> box = new JComboBox<String>(new String[]{
						"Stretched to fit the whole length",
						"Cascading from tail to head",
						"Cascading from head to tail"
					});
					box.setSelectedIndex(ini.noteBodyStyle);
					panel.add(box);
					box.addActionListener(e->{
						ini.noteBodyStyle = box.getSelectedIndex();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.columns[col].noteBodyStyle != -1);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Note Body Style " + col + " (What style should be used for the hold note " + (col + 1) + " body): "));
					JComboBox<String> box = new JComboBox<String>(new String[]{
						"Stretched to fit the whole length",
						"Cascading from tail to head",
						"Cascading from head to tail"
					});
					box.setSelectedIndex(ini.columns[i].noteBodyStyle == -1 ? 1 : ini.columns[col].noteBodyStyle);
					box.addActionListener((event)->{
						if(enabled.isSelected()){
							ini.columns[col].noteBodyStyle = box.getSelectedIndex();
						}
					});
					settings.add(box, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].noteBodyStyle = box.getSelectedIndex();
						}else{
							ini.columns[col].noteBodyStyle = -1;
						}
					});
					panel.add(settings);
					content.add(panel);
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					JPanel settings = new JPanel(new BorderLayout());
					panel.add(new JLabel(" Colour Column Line (what colour should be used for the column lines): "));
					JSpinner alpha = new JSpinner(new SpinnerNumberModel(ini.colourColumnLine == null ? 100.0D : ini.colourColumnLine.getAlphaPercentage(), 0.0D, 100.0D, 1.0D));
					JPanel alphaPanel = new JPanel(new BorderLayout());
					alphaPanel.add(new JLabel(" Opacity: "), BorderLayout.LINE_START);
					alphaPanel.add(alpha, BorderLayout.CENTER);
					alphaPanel.add(new JLabel(" %"), BorderLayout.LINE_END);
					ColorSelector color = new ColorSelector(ini.colourColumnLine, (c)->{
						ini.colourColumnLine.update(c);
					});
					alpha.addChangeListener((e)->{
						ini.colourColumnLine.update((double)alpha.getValue());
					});
					settings.add(color, BorderLayout.CENTER);
					settings.add(alphaPanel, BorderLayout.LINE_END);
					panel.add(settings);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					JPanel settings = new JPanel(new BorderLayout());
					panel.add(new JLabel(" Colour Bar Line (what colour should be used for the bar line separator): "));
					JSpinner alpha = new JSpinner(new SpinnerNumberModel(ini.colourBarline == null ? 100.0D : ini.colourBarline.getAlphaPercentage(), 0.0D, 100.0D, 1.0D));
					JPanel alphaPanel = new JPanel(new BorderLayout());
					alphaPanel.add(new JLabel(" Opacity: "), BorderLayout.LINE_START);
					alphaPanel.add(alpha, BorderLayout.CENTER);
					alphaPanel.add(new JLabel(" %"), BorderLayout.LINE_END);
					ColorSelector color = new ColorSelector(ini.colourBarline, (c)->{
						ini.colourBarline.update(c);
					});
					alpha.addChangeListener((e)->{
						ini.colourBarline.update((double)alpha.getValue());
					});
					settings.add(color, BorderLayout.CENTER);
					settings.add(alphaPanel, BorderLayout.LINE_END);
					panel.add(settings);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Colour Judgement Line (what colour should be used for the judgement line): "));
					ColorSelector selector = new ColorSelector(ini.colourJudgementLine, (color)->{
						ini.colourJudgementLine = color;
					});
					panel.add(selector);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Colour Key Warning (what colour should be used for keybinding reminders): "));
					ColorSelector selector = new ColorSelector(ini.colourKeyWarning, (color)->{
						ini.colourKeyWarning = color;
					});
					panel.add(selector);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					JPanel settings = new JPanel(new BorderLayout());
					panel.add(new JLabel(" Colour Hold (what colour should be used for the combo counter during holds): "));
					JSpinner alpha = new JSpinner(new SpinnerNumberModel(ini.colourHold == null ? 100.0D : ini.colourHold.getAlphaPercentage(), 0.0D, 100.0D, 1.0D));
					JPanel alphaPanel = new JPanel(new BorderLayout());
					alphaPanel.add(new JLabel(" Opacity: "), BorderLayout.LINE_START);
					alphaPanel.add(alpha, BorderLayout.CENTER);
					alphaPanel.add(new JLabel(" %"), BorderLayout.LINE_END);
					ColorSelector color = new ColorSelector(ini.colourHold, (c)->{
						ini.colourHold.update(c);
					});
					alpha.addChangeListener((e)->{
						ini.colourHold.update((double)alpha.getValue());
					});
					settings.add(color, BorderLayout.CENTER);
					settings.add(alphaPanel, BorderLayout.LINE_END);
					panel.add(settings);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Colour Break (what colour should be used for the combo counter when it breaks): "));
					ColorSelector selector = new ColorSelector(ini.colourBreak, (color)->{
						ini.colourBreak = color;
					});
					panel.add(selector);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JPanel settings = new JPanel(new BorderLayout());
					panel.add(new JLabel(" Colour " + (col + 1) + " (what colour should be used for the lane of column " + (col + 1) + "): "));
					JSpinner alpha = new JSpinner(new SpinnerNumberModel(ini.colourHold == null ? 100.0D : ini.columns[col].colour.getAlphaPercentage(), 0.0D, 100.0D, 1.0D));
					JPanel alphaPanel = new JPanel(new BorderLayout());
					alphaPanel.add(new JLabel(" Opacity: "), BorderLayout.LINE_START);
					alphaPanel.add(alpha, BorderLayout.CENTER);
					alphaPanel.add(new JLabel(" %"), BorderLayout.LINE_END);
					ColorSelector color = new ColorSelector(ini.columns[col].colour, (c)->{
						ini.columns[col].colour.update(c);
					});
					alpha.addChangeListener((e)->{
						ini.columns[col].colour.update((double)alpha.getValue());
					});
					settings.add(color, BorderLayout.CENTER);
					settings.add(alphaPanel, BorderLayout.LINE_END);
					panel.add(settings);
					content.add(panel);	
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Colour Light " + (col + 1) + " (what colour should be used for the lighting of column " + (col + 1) + "): "));
					ColorSelector color = new ColorSelector(ini.columns[col].colourLight, (c)->{
						ini.columns[col].colourLight.update(c);
					});
					panel.add(color);
					content.add(panel);	
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Stage Left (what is the name of the left stage image): "));
					PathField field = new PathField(ini.stageLeft);
					panel.add(field);
					field.addActionListener((e)->{
						ini.stageLeft = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Stage Right (what is the name of the right stage image): "));
					PathField field = new PathField(ini.stageRight);
					panel.add(field);
					field.addActionListener((e)->{
						ini.stageRight = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Stage Bottom (what is the name of the bottom stage image): "));
					PathField field = new PathField(ini.stageBottom);
					panel.add(field);
					field.addActionListener((e)->{
						ini.stageBottom = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Stage Hint (what is the name of the stage hint image): "));
					PathField field = new PathField(ini.stageHint);
					panel.add(field);
					field.addActionListener((e)->{
						ini.stageHint = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Stage Light (what is the name of the stage light image): "));
					PathField field = new PathField(ini.stageLight);
					panel.add(field);
					field.addActionListener((e)->{
						ini.stageLight = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Lighting N (what is the name of the note lighting image): "));
					PathField field = new PathField(ini.lightingN);
					panel.add(field);
					field.addActionListener((e)->{
						ini.lightingN = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Lighting L (what is the name of the hold note lighting image): "));
					PathField field = new PathField(ini.lightingL);
					panel.add(field);
					field.addActionListener((e)->{
						ini.lightingL = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Warning Arrow (what is the name of the warning arrow image): "));
					PathField field = new PathField(ini.warningArrow);
					panel.add(field);
					field.addActionListener((e)->{
						ini.warningArrow = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Hit 0 (what is the name of the hit0 image): "));
					PathField field = new PathField(ini.hit0);
					panel.add(field);
					field.addActionListener((e)->{
						ini.hit0 = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Hit 50 (what is the name of the hit50 image): "));
					PathField field = new PathField(ini.hit50);
					panel.add(field);
					field.addActionListener((e)->{
						ini.hit50 = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Hit 100 (what is the name of the hit100 image): "));
					PathField field = new PathField(ini.hit100);
					panel.add(field);
					field.addActionListener((e)->{
						ini.hit100 = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Hit 200 (what is the name of the hit200 image): "));
					PathField field = new PathField(ini.hit200);
					panel.add(field);
					field.addActionListener((e)->{
						ini.hit200 = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Hit 300 (what is the name of the hit300 image): "));
					PathField field = new PathField(ini.hit300);
					panel.add(field);
					field.addActionListener((e)->{
						ini.hit300 = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					panel.add(new JLabel(" Hit 300g (what is the name of the hit300g image): "));
					PathField field = new PathField(ini.hit300g);
					panel.add(field);
					field.addActionListener((e)->{
						ini.hit300g = field.getText();
					});
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.columns[col].keyImage != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Key Image " + col + " (what is the name for the unpressed key image of column " + (col + 1) + "): "));
					PathField field = new PathField(ini.columns[col].keyImage);
					field.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].keyImage = field.getText();
						}
					});
					settings.add(field, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].keyImage = field.getText();
						}else{
							ini.columns[col].keyImage = null;
						}
					});
					panel.add(settings);
					content.add(panel);
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.columns[col].keyImageD != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Key Image " + col + " D (what is the name for the pressed key image of column " + (col + 1) + "): "));
					PathField field = new PathField(ini.columns[col].keyImageD);
					field.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].keyImageD = field.getText();
						}
					});
					settings.add(field, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].keyImageD = field.getText();
						}else{
							ini.columns[col].keyImageD = null;
						}
					});
					panel.add(settings);
					content.add(panel);
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.columns[col].noteImage != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Note Image " + col + " (what is the name for the note image of column " + (col + 1) + "): "));
					PathField field = new PathField(ini.columns[col].noteImage);
					field.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].noteImage = field.getText();
						}
					});
					settings.add(field, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].noteImage = field.getText();
						}else{
							ini.columns[col].noteImage = null;
						}
					});
					panel.add(settings);
					content.add(panel);
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.columns[col].noteImageH != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Note Image " + col + " H (what is the name for the hold note head image of column " + (col + 1) + "): "));
					PathField field = new PathField(ini.columns[col].noteImageH);
					field.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].noteImageH = field.getText();
						}
					});
					settings.add(field, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].noteImageH = field.getText();
						}else{
							ini.columns[col].noteImageH = null;
						}
					});
					panel.add(settings);
					content.add(panel);
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.columns[col].noteImageL != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Note Image " + col + " L (what is the name for the hold note body image of column " + (col + 1) + "): "));
					PathField field = new PathField(ini.columns[col].noteImageL);
					field.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].noteImageL = field.getText();
						}
					});
					settings.add(field, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].noteImageL = field.getText();
						}else{
							ini.columns[col].noteImageL = null;
						}
					});
					panel.add(settings);
					content.add(panel);
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.columns[col].noteImageT != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" Note Image " + col + " T (what is the name for the hold note tail image of column " + (col + 1) + "): "));
					PathField field = new PathField(ini.columns[col].noteImageT);
					field.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].noteImageT = field.getText();
						}
					});
					settings.add(field, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						if(enabled.isSelected()){
							ini.columns[col].noteImageT = field.getText();
						}else{
							ini.columns[col].noteImageT = null;
						}
					});
					panel.add(settings);
					content.add(panel);
					content.add(Box.createVerticalStrut(2));
				}

				content.add(new JPanel(new BorderLayout()));
			}
		}
	}
	
	private static final class ColorEditor extends JPanel{
		
		private ColorEditor(String name, String hint, Setting<Colour> setting){
			this(name, hint, setting, false);
		}
		
		private ColorEditor(String name, String hint, Setting<Colour> setting, boolean toggle){
			super(new SplitLayout());
			JPanel settings = new JPanel(new BorderLayout());
			add(new JLabel(" " + name + " (" + hint + "): "));
			ColorSelector selector = new ColorSelector(setting);
			settings.add(selector, BorderLayout.CENTER);
			if(toggle){
				JCheckBox enabled = new JCheckBox("", setting.isEnabled());
				settings.add(enabled, BorderLayout.LINE_START);
				enabled.addActionListener((e)->{
					setting.setEnabled(enabled.isSelected());
				});
			}
			add(settings);
		}
	}
	
	private static final class IntegerSpinnerEditor extends JPanel{
		
		private IntegerSpinnerEditor(String name, String hint, Setting<Integer> setting){
			this(name, hint, setting, Integer.MIN_VALUE, false);
		}
		
		private IntegerSpinnerEditor(String name, String hint, Setting<Integer> setting, int min){
			this(name, hint, setting, min, Integer.MAX_VALUE, false);
		}
		
		private IntegerSpinnerEditor(String name, String hint, Setting<Integer> setting, boolean toggle){
			this(name, hint, setting, Integer.MIN_VALUE,toggle);
		}
		
		private IntegerSpinnerEditor(String name, String hint, Setting<Integer> setting, int min, boolean toggle){
			this(name, hint, setting, min, Integer.MAX_VALUE, toggle);
		}

		private IntegerSpinnerEditor(String name, String hint, Setting<Integer> setting, int min, int max, boolean toggle){
			super(new SplitLayout());
			add(new JLabel(" " + name + " (" + hint + "): "));
			JPanel p = new JPanel(new BorderLayout());
			JSpinner spinner = new JSpinner(new SpinnerNumberModel((int)setting.getValue(), min, max, 1));
			p.add(spinner, BorderLayout.CENTER);
			spinner.addChangeListener((e)->{
				setting.update((Integer)spinner.getValue());
			});
			if(toggle){
				JCheckBox enabled = new JCheckBox("", setting.isEnabled());
				p.add(enabled, BorderLayout.LINE_START);
				enabled.addActionListener((e)->{
					setting.setEnabled(enabled.isSelected());
				});
			}
			add(p);
		}
	}
	
	private static final class CheckBoxEditor extends JPanel{
		
		private CheckBoxEditor(String name, String hint, Setting<Boolean> setting){
			this(name, hint, null, setting);
		}
		
		private CheckBoxEditor(String name, String hint, String extra, Setting<Boolean> setting){
			super(new SplitLayout());
			add(new JLabel(" " + name + " (" + hint + "): "));
			JCheckBox check = extra == null ? new JCheckBox() : new JCheckBox(extra);
			check.setSelected(setting.getValue());
			check.addItemListener((e)->{
				setting.update(check.isSelected());
			});
			add(check);
		}
	}
	
	private static final class ComboBoxEditor<T> extends JPanel{
		
		private ComboBoxEditor(String name, Setting<T> setting, T[] values){
			this(name, null, setting, values);
		}
		
		@SuppressWarnings("unchecked")
		private ComboBoxEditor(String name, String hint, Setting<T> setting, T[] values){
			super(new SplitLayout());
			if(hint == null){
				add(new JLabel(" " + name + ": "));
			}else{
				add(new JLabel(" " + name + " (" + hint + "): "));
			}
			JComboBox<T> box = new JComboBox<T>(values);
			box.setSelectedItem(setting.getValue());
			box.addItemListener((e)->{
				setting.update((T)box.getSelectedItem());
			});
			add(box);
		}
	}
	
	private static final class TextEditor extends JPanel{
		
		private TextEditor(String name, Setting<String> setting){
			this(name, null, setting);
		}
		
		private TextEditor(String name, String hint, Setting<String> setting){
			this(name, hint, setting, false);
		}
		
		private TextEditor(String name, String hint, Setting<String> setting, boolean toggle){
			super(new SplitLayout());
			JPanel settings = new JPanel(new BorderLayout());
			if(hint == null){
				add(new JLabel(" " + name + ": "));
			}else{
				add(new JLabel(" " + name + " (" + hint + "): "));
			}
			PathField field = new PathField(setting.getValue());
			field.addActionListener((e)->{
				setting.update(field.getText());
			});
			if(toggle){
				settings.add(field, BorderLayout.CENTER);
				JCheckBox enabled = new JCheckBox("", setting.isEnabled());
				settings.add(enabled, BorderLayout.LINE_START);
				enabled.addActionListener((e)->{
					setting.setEnabled(enabled.isSelected());
				});
			}
			add(settings);
		}
	}
	
	private static final class PathField extends JTextField{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -4519936454813151564L;
		
		private PathField(String text){
			super(text);
		}
	}

	private static final class ColorSelector extends JPanel implements MouseListener{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 3286760817738876568L;
		private Setting<Colour> color;

		private ColorSelector(Setting<Colour> def){
			color = def;
			this.setBackground(color.getValue().toColor());
			this.addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent arg0){
			Color c = JColorChooser.showDialog(this, "Colour Chooser", color.getValue().toColor());
			if(c != null){
				this.setBackground(c);
				color.update(new Colour(c));
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0){
		}

		@Override
		public void mouseExited(MouseEvent arg0){
		}

		@Override
		public void mousePressed(MouseEvent arg0){
		}

		@Override
		public void mouseReleased(MouseEvent arg0){
		}
	}

	private static final class DoubleArray extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 3145876156701959606L;

		private DoubleArray(double[] data){
			this.setLayout(new GridLayout(1, data.length, 2, 0));
			for(int i = 0; i < data.length; i++){
				JSpinner spinner = new JSpinner(new SpinnerNumberModel(data[i], 0.0D, Double.MAX_VALUE, 1.0D));
				final int field = i;
				spinner.addChangeListener((e)->{
					data[field] = (double)spinner.getValue();
				});
				this.add(spinner);
			}
		}
	}
	
	private static final class ListField extends JTextField{	
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -4456019117693719163L;

		private ListField(String text){
			super(text);
			((PlainDocument)this.getDocument()).setDocumentFilter(new ListFilter());
		}
		
		@Override
		public String getText(){
			String text = super.getText();
			if(!text.isEmpty() && text.charAt(text.length() - 1) == ','){
				return text.substring(0, text.length() - 1);
			}else{
				return text;
			}
		}
		
		private static final class ListFilter extends DocumentFilter{
			
			private static final Pattern filter = Pattern.compile("^(\\d+,)*\\d*$");
			
			@Override
			public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException{
				replace(fb, offset, 0, text, attr);
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException{
				if(filter.matcher(fb.getDocument().getText(0, offset) + text + fb.getDocument().getText(offset + length, fb.getDocument().getLength() - offset - length)).matches()){
					fb.replace(offset, length, text, attrs);
				}
			}
			
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException{				
				if(filter.matcher(fb.getDocument().getText(0, offset) + fb.getDocument().getText(offset + length, fb.getDocument().getLength() - offset - length)).matches()){
					fb.remove(offset, length);
				}
			}
		}
	}
}

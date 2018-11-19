package me.roan.osuskinchecker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
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

import me.roan.osuskinchecker.SkinIni.ComboBurstStyle;
import me.roan.osuskinchecker.SkinIni.ManiaIni;
import me.roan.osuskinchecker.SkinIni.NoteBodyStyle;
import me.roan.osuskinchecker.SkinIni.SliderStyle;
import me.roan.osuskinchecker.SkinIni.SpecialStyle;
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
			content.add(new TextEditor("HitCirclePrefix", "what prefix to use for hitcircle numbers", ini.hitCirclePrefix));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new IntegerSpinnerEditor("HitCircleOverlap", "by how many pixels should the hitcircle numbers overlap", ini.hitCircleOverlap));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new TextEditor("ComboPrefix", "what prefix to use for combo numbers", ini.comboPrefix));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new IntegerSpinnerEditor("ComboOverlap", "by how many pixels should the combo numbers overlap", ini.comboOverlap));
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
			content.add(new ColorEditor("HyperDash", "what colour is used for the dash", ini.hyperDash));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("HyperDashFruit", "what colour is used for the fruits", ini.hyperDashFruit, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("HyperDashAfterImage", "what colour is used for the after images", ini.hyperDashAfterImage, true));
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
				content.add(new DoubleSpinnerEditor("ColumnStart", "where does the left column start", ini.columnStart));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleSpinnerEditor("ColumnRight", "up to which point can columns be drawn", ini.columnRight));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				if(ini.keys != 1){
					content.add(Box.createVerticalStrut(2));
					content.add(new DoubleArrayEditor("ColumnSpacing", "distance between columns", ini.columnSpacing));
					content.add(Box.createVerticalStrut(2));
					content.add(new JSeparator());
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleArrayEditor("ColumnWidth", "widths of the columns", ini.columnWidth));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleArrayEditor("ColumnLineWidth", "widths of the column separators", ini.columnLineWidth));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleSpinnerEditor("BarlineHeight", "thickness of the barline", ini.barlineHeight));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleArrayEditor("LightingNWidth", "widths of LightingN for the columns", ini.lightingNWidth));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleArrayEditor("LightingLWidth", "widths of LightingL for the column", ini.lightingLWidth));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleSpinnerEditor("WidthForNoteHeightScale", "height for all notes if columns have individual widths", ini.widthForNoteHeightScale));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new IntegerSpinnerEditor("HitPosition", "at what height should the judgement line be drawn", ini.hitPosition, 0));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new IntegerSpinnerEditor("LightPosition", "at what height should the stage lights be drawn", ini.lightPosition, 0));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new IntegerSpinnerEditor("ScorePosition", "at what height should the hitbursts be drawn", ini.scorePosition, 0));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new IntegerSpinnerEditor("ComboPosition", "at what height should the combo be drawn", ini.comboPosition, 0));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new CheckBoxEditor("JudgementLine", "should a solid line be drawn above the StageHint", ini.judgementLine));
				content.add(Box.createVerticalStrut(2));
				if(ini.keys % 2 == 0 && ini.keys >= 4){
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new ComboBoxEditor<SpecialStyle>("SpecialStyle", "what special style (if avaible) should be used for this keycount", ini.specialStyle, SpecialStyle.values()));
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new ComboBoxEditor<ComboBurstStyle>("ComboBurstStyle", "on what side should combobursts appear", ini.comboBurstStyle, ComboBurstStyle.values()));
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
					box.setSelectedIndex(ini.splitStages.getValue() ? 1 : 0);
					box.addActionListener((event)->{
						ini.splitStages.setEnabled(box.getSelectedIndex() == 1);
					});
					settings.add(box, BorderLayout.CENTER);
					enabled.addActionListener((e)->{
						ini.splitStages.setEnabled(enabled.isSelected());
					});
					panel.add(settings);
					content.add(panel);
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleSpinnerEditor("StageSeparation", "when splitted what should be the distance between the stages", ini.stageSeparation, 0.0D));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new CheckBoxEditor("SeparateScore", "should hitburst only be shown on the stage it was scored", ini.separateScore));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new CheckBoxEditor("KeysUnderNotes", "should keys be covered by notes when passed by them", ini.keysUnderNotes));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new CheckBoxEditor("UpsideDown", "should the stage be flipped", ini.upsideDown));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new CheckBoxEditor("KeyFlipWhenUpsideDown", "should keys be flipped when the stage is flipped", ini.keyFlipWhenUpsideDown));
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new FlipEditor("KeyFlipWhenUpsideDown" + col, "should the unpressed key in column " + (col + 1) + " be flipped when the stage is flipped", ini.columns[col].keyFlipWhenUpsideDown));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new FlipEditor("KeyFlipWhenUpsideDown" + col + "D", "should the pressed key in column " + (col + 1) + " be flipped when the stage is flipped", ini.columns[col].keyFlipWhenUpsideDownD));
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new CheckBoxEditor("NoteFlipWhenUpsideDown", "should notes be flipped when the stage is flipped", ini.noteFlipWhenUpsideDown));
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new FlipEditor("NoteFlipWhenUpsideDown" + col, "should the note in column " + (col + 1) + " be flipped when the stage is flipped", ini.columns[col].noteFlipWhenUpsideDown));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new FlipEditor("NoteFlipWhenUpsideDown" + col + "H", "should the head of the note in column " + (col + 1) + " be flipped when the stage is flipped", ini.columns[col].noteFlipWhenUpsideDownH));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new FlipEditor("NoteFlipWhenUpsideDown" + col + "L", "should the body of the note in column " + (col + 1) + " be flipped when the stage is flipped", ini.columns[col].noteFlipWhenUpsideDownL));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new FlipEditor("NoteFlipWhenUpsideDown" + col + "T", "should the tail of the note in column " + (col + 1) + " be flipped when the stage is flipped", ini.columns[col].noteFlipWhenUpsideDownT));
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new ComboBoxEditor<NoteBodyStyle>("NoteBodyStyle", "what style should be used for all the hold note bodies", ini.noteBodyStyle, NoteBodyStyle.values()));
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new ComboBoxEditor<NoteBodyStyle>("NoteBodyStyle" + i, "what style should be used for the hold note " + (i + 1) + " body", ini.columns[i].noteBodyStyle, NoteBodyStyle.values(), true));
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new AlphaColorEditor("ColourColumnLine", "what colour should be used for the column lines", ini.colourColumnLine));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new AlphaColorEditor("ColourBarLine", "what colour should be used for the bar line separator", ini.colourBarline));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new ColorEditor("ColourJudgementLine", "what colour should be used for the judgement line", ini.colourJudgementLine));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new ColorEditor("ColourKeyWarning", "what colour should be used for keybinding reminders", ini.colourKeyWarning));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new AlphaColorEditor("ColourHold", "what colour should be used for the combo counter during holds", ini.colourHold));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new ColorEditor("ColourBreak", "what colour should be used for the combo counter when it breaks", ini.colourBreak));
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new AlphaColorEditor("Colour" + (col + 1), "what colour should be used for the lane of column " + (col + 1), ini.columns[col].colour));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new ColorEditor("ColourLight" + (col + 1), "what colour should be used for the lighting of column " + (col + 1), ini.columns[col].colourLight));
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("StageLeft", "what is the name of the left stage image", ini.stageLeft));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("StageRight", "what is the name of the right stage image", ini.stageRight));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("StageBottom", "what is the name of the bottom stage image", ini.stageBottom));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("StageHint", "what is the name of the stage hint image", ini.stageHint));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("StageLight", "what is the name of the stage light image", ini.stageLight));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("LightingN", "what is the name of the note lighting image", ini.lightingN));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("LightingL", "what is the name of the hold note lighting image", ini.lightingL));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("WarningArrow", "what is the name of the warning arrow image", ini.warningArrow));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("Hit0", "what is the name of the hit0 image", ini.hit0));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("Hit50", "what is the name of the hit50 image", ini.hit50));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("Hit100", "what is the name of the hit100 image", ini.hit100));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("Hit200", "what is the name of the hit200 image", ini.hit200));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("Hit300", "what is the name of the hit300 image", ini.hit300));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("Hit300g", "what is the name of the hit300g image", ini.hit300g));
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new PathEditor("KeyImage" + col, "what is the name for the unpressed key image of column " + (col + 1), ini.columns[col].keyImage, true));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new PathEditor("KeyImage" + col + "D", "what is the name for the pressed key image of column " + (col + 1), ini.columns[col].keyImageD, true));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new PathEditor("NoteImage" + col, "what is the name for the note image of column " + (col + 1), ini.columns[col].noteImage, true));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new PathEditor("NoteImage" + col + "H", "what is the name for the hold note head image of column " + (col + 1), ini.columns[col].noteImageH, true));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new PathEditor("NoteImage" + col + "L", "what is the name for the hold note body image of column " + (col + 1), ini.columns[col].noteImageL, true));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new PathEditor("NoteImage" + col + "T", "what is the name for the hold note tail image of column " + (col + 1), ini.columns[col].noteImageT, true));
					content.add(Box.createVerticalStrut(2));
				}

				content.add(new JPanel(new BorderLayout()));
			}
		}
	}
	
	private static final class FlipEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -4635968938790424674L;

		private FlipEditor(String name, String hint, Setting<Boolean> setting){
			super(new SplitLayout());
			JCheckBox enabled = new JCheckBox("", setting.getValue());
			JPanel settings = new JPanel(new BorderLayout());
			settings.add(enabled, BorderLayout.LINE_START);
			add(new JLabel(" " + name + " (" + hint + "): "));
			JComboBox<String> box = new JComboBox<String>(new String[]{
				"Don't flip",
				"Flipped"
			});
			box.setSelectedIndex(setting.getValue() ? 1 : 0);
			box.addActionListener((event)->{
				setting.update(box.getSelectedIndex() == 1);
			});
			settings.add(box, BorderLayout.CENTER);
			enabled.addActionListener((e)->{
				setting.setEnabled(enabled.isSelected());
			});
			add(settings);
		}
	}
	
	private static final class ColorEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -1171023992187065714L;

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
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -8823113464197619825L;

		private IntegerSpinnerEditor(String name, String hint, Setting<Integer> setting){
			this(name, hint, setting, Integer.MIN_VALUE, false);
		}
		
		private IntegerSpinnerEditor(String name, String hint, Setting<Integer> setting, int min){
			this(name, hint, setting, min, Integer.MAX_VALUE, false);
		}
		
		private IntegerSpinnerEditor(String name, String hint, Setting<Integer> setting, boolean toggle){
			this(name, hint, setting, Integer.MIN_VALUE, toggle);
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
	
	private static final class DoubleSpinnerEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -1377612278897030930L;

		private DoubleSpinnerEditor(String name, String hint, Setting<Double> setting){
			this(name, hint, setting, -Double.MAX_VALUE, false);
		}
		
		private DoubleSpinnerEditor(String name, String hint, Setting<Double> setting, double min){
			this(name, hint, setting, min, Double.MAX_VALUE, false);
		}
		
		private DoubleSpinnerEditor(String name, String hint, Setting<Double> setting, boolean toggle){
			this(name, hint, setting, -Double.MAX_VALUE, Double.MAX_VALUE, toggle);
		}
		
		private DoubleSpinnerEditor(String name, String hint, Setting<Double> setting, double min, boolean toggle){
			this(name, hint, setting, min, Double.MAX_VALUE, toggle);
		}
		
		private DoubleSpinnerEditor(String name, String hint, Setting<Double> setting, double min, double max, boolean toggle){
			super(new SplitLayout());
			JPanel settings = new JPanel(new BorderLayout());
			add(new JLabel(" " + name + " (" + hint + "): "));
			JSpinner spinner = new JSpinner(new SpinnerNumberModel((double)setting.getValue(), min, max, 1.0D));
			spinner.addChangeListener((event)->{
				setting.update((double)spinner.getValue());
			});
			settings.add(spinner, BorderLayout.CENTER);
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
	
	private static final class CheckBoxEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -4819354663525782395L;

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
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 8946535893069224896L;

		private ComboBoxEditor(String name, Setting<T> setting, T[] values){
			this(name, null, setting, values);
		}
		
		private ComboBoxEditor(String name, String hint, Setting<T> setting, T[] values){
			this(name, hint, setting, values, false);
		}
		
		@SuppressWarnings("unchecked")
		private ComboBoxEditor(String name, String hint, Setting<T> setting, T[] values, boolean toggle){
			super(new SplitLayout());
			JPanel settings = new JPanel(new BorderLayout());
			if(hint == null){
				add(new JLabel(" " + name + ": "));
			}else{
				add(new JLabel(" " + name + " (" + hint + "): "));
			}
			JComboBox<T> box = new JComboBox<T>(values);
			box.setSelectedItem(setting.getValue());
			settings.add(box, BorderLayout.CENTER);
			box.addActionListener((e)->{
				setting.update((T)box.getSelectedItem());
			});
			if(toggle){
				JCheckBox enabled = new JCheckBox("", setting.isEnabled());
				enabled.addActionListener((event)->{
					setting.setEnabled(enabled.isSelected());
				});
				settings.add(enabled, BorderLayout.LINE_START);
			}
			add(settings);
		}
	}
	
	private static final class AlphaColorEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -5486062097516828519L;

		private AlphaColorEditor(String name, String hint, Setting<Colour> setting){
			super(new SplitLayout());
			JPanel settings = new JPanel(new BorderLayout());
			add(new JLabel(" " + name + " (" + hint + "): "));
			JSpinner alpha = new JSpinner(new SpinnerNumberModel(setting.getValue().getAlphaPercentage(), 0.0D, 100.0D, 1.0D));
			JPanel alphaPanel = new JPanel(new BorderLayout());
			alphaPanel.add(new JLabel(" Opacity: "), BorderLayout.LINE_START);
			alphaPanel.add(alpha, BorderLayout.CENTER);
			alphaPanel.add(new JLabel(" %"), BorderLayout.LINE_END);
			ColorSelector color = new ColorSelector(setting);
			alpha.addChangeListener((e)->{
				setting.getValue().update((double)alpha.getValue());
			});
			settings.add(color, BorderLayout.CENTER);
			settings.add(alphaPanel, BorderLayout.LINE_END);
			add(settings);
		}
	}
	
	private static final class TextEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 3436381071498830198L;

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
			JTextField field = new JTextField(setting.getValue());
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
	
	private static final class DoubleArrayEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 2821189742176611077L;

		private DoubleArrayEditor(String name, String hint, Setting<double[]> setting){
			this(name, hint, setting, false);
		}
		
		private DoubleArrayEditor(String name, String hint, Setting<double[]> setting, boolean toggle){
			super(new SplitLayout());
			JPanel settings = new JPanel(new BorderLayout());
			add(new JLabel(" " + name + " (" + hint + "): "));
			settings.add(new DoubleArray(setting.getValue()));
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
	
	private static final class PathEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -5682762745185860450L;

		private PathEditor(String name, String hint, Setting<String> setting){
			this(name, hint, setting, false);
		}
		
		private PathEditor(String name, String hint, Setting<String> setting, boolean toggle){
			super(new SplitLayout());
			JPanel settings = new JPanel(new BorderLayout());
			add(new JLabel(" " + name + " (" + hint + "): "));
			PathField field = new PathField(setting.getValue());
			field.addActionListener((e)->{
				setting.update(field.getText());
			});
			settings.add(field, BorderLayout.CENTER);
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

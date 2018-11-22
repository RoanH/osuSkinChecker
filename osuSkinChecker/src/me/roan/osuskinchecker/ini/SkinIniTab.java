package me.roan.osuskinchecker.ini;

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

import me.roan.osuskinchecker.ini.SkinIni.ComboBurstStyle;
import me.roan.osuskinchecker.ini.SkinIni.ManiaIni;
import me.roan.osuskinchecker.ini.SkinIni.NoteBodyStyle;
import me.roan.osuskinchecker.ini.SkinIni.SliderStyle;
import me.roan.osuskinchecker.ini.SkinIni.SpecialStyle;
import me.roan.osuskinchecker.ini.SkinIni.Version;
import me.roan.osuskinchecker.ini.SplitLayout.ScrollPane;

/**
 * GUI based editor component
 * for all the settings possible
 * in a skin configuration
 * @author Roan
 * @see SkinIni
 */
public class SkinIniTab extends JTabbedPane{
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -5575803475931849256L;

	/**
	 * Initialises all the tabs in this
	 * component for the given skin configuration
	 * @param ini The skin configuration to
	 *        initialise the editors for
	 */
	public void init(SkinIni ini){
		removeAll();
		add("General", new GeneralTab(ini));
		add("Gameplay", new GameplayTab(ini));
		add("osu! Standard", new StandardTab(ini));
		add("osu! Catch", new CtbTab(ini));
		add("osu! Mania", new ManiaTab(ini));
	}

	/**
	 * GUI based editor tab for the
	 * general settings in
	 * a skin configuration
	 * @author Roan
	 * @see SkinIni
	 */
	private static final class GeneralTab extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -4554355388349363415L;

		/**
		 * Constructs a new GeneralTab for
		 * the given skin configuration
		 * @param ini The skin configuration
		 *        to create an editor for
		 */
		private GeneralTab(SkinIni ini){
			super(new BorderLayout());
			JPanel content = new ScrollPane();
			BoxLayout layout = new BoxLayout(content, BoxLayout.Y_AXIS);
			content.setLayout(layout);
			this.add(new JScrollPane(content), BorderLayout.CENTER);

			content.add(Box.createVerticalStrut(2));
			content.add(new TextEditor(ini.name));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new TextEditor(ini.author));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ComboBoxEditor<Version>(ini.version, Version.values()));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("should the cursor expand when clicking", ini.cursorExpand));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("should the cursor be centered", "false implies the top left corner", ini.cursorCentre));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("should the cursor rotate", ini.cursorRotate));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("should the cursor trail rotate",  ini.cursorTrailRotate));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new IntegerSpinnerEditor("how many frame per second for most animations", ini.animationFramerate, 1));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour should the text of selected song carousel panels be tinted", ini.songSelectActiveText));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour should the text of not selected song carousel panels be tinted", ini.songSelectInactiveText));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour should the spectrum bars be coloured in",  ini.menuGlow));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new TextEditor("what prefix to use for the score numbers", ini.scorePrefix));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new IntegerSpinnerEditor("by how many pixels should the score numbers overlap, positive means closer together", ini.scoreOverlap));
			content.add(Box.createVerticalStrut(2));

			content.add(new JPanel(new BorderLayout()));
		}
	}

	/**
	 * GUI based editor tab for the
	 * osu!standard related settings in
	 * a skin configuration
	 * @author Roan
	 * @see SkinIni
	 */	
	private static final class StandardTab extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -639789728841322306L;

		/**
		 * Constructs a new StandardTab for
		 * the given skin configuration
		 * @param ini The skin configuration
		 *        to create an editor for
		 */
		private StandardTab(SkinIni ini){
			super(new BorderLayout());
			JPanel content = new ScrollPane();
			BoxLayout layout = new BoxLayout(content, BoxLayout.Y_AXIS);
			content.setLayout(layout);
			this.add(new JScrollPane(content), BorderLayout.CENTER);

			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("should the hitcircleoverlay be drawn above the numbers", "false implies that they will be drawn below the overlay", ini.hitCircleOverlayAboveNumber));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ComboBoxEditor<SliderStyle>("what filling to use for sliders", ini.sliderStyle, SliderStyle.values()));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("should the sliderball flip at the reverse arrow", ini.sliderBallFlip));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("should the sliderball be tinted in combo colours", ini.allowSliderBallTint));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("should the highest bar of the metre always be visible", ini.spinnerNoBlink));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("should the spinner add black bars during spins", ini.spinnerFadePlayfield));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("should the spinnerspin sound pitch go up while spinning", ini.spinnerFrequencyModulate));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour should the slider ball be coloured", ini.sliderBall));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour should the slider body be coloured", ini.sliderTrackOverride, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour should be used for sliderborders", ini.sliderBorder));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour should be added to the spinner-background", ini.spinnerBackground));
			content.add(Box.createVerticalStrut(2));

			content.add(new JPanel(new BorderLayout()));
		}
	}

	/**
	 * GUI based editor tab for the
	 * gameplay related settings in
	 * a skin configuration
	 * @author Roan
	 * @see SkinIni
	 */
	private static final class GameplayTab extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -5006508447206574607L;

		/**
		 * Constructs a new GameplayTab for
		 * the given skin configuration
		 * @param ini The skin configuration
		 *        to create an editor for
		 */
		private GameplayTab(SkinIni ini){
			super(new BorderLayout());
			JPanel content = new ScrollPane();
			BoxLayout layout = new BoxLayout(content, BoxLayout.Y_AXIS);
			content.setLayout(layout);
			this.add(new JScrollPane(content), BorderLayout.CENTER);

			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("should hitnormal sounds always be played", ini.layeredHitSounds));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new CheckBoxEditor("should combursts be shown in a random order", "false implies that they will appear in order", ini.comboBurstRandom));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new SplitLayout());
				JCheckBox enabled = new JCheckBox("", ini.customComboBurstSounds != null);
				panel.add(new JLabel(" " + ini.customComboBurstSounds.getName() + " (on which combo marks should comboburst sounds play): "));
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
			content.add(new ColorEditor("what colour should be added to star2 during breaks", ini.starBreakAdditive));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour should the input keys be tinted", ini.inputOverlayText));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour is used for the last combo", ini.combo1));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour is used for the first combo", ini.combo2, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour is used for the second combo", ini.combo3, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour is used for the third combo", ini.combo4, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour is used for the fourth combo", ini.combo5, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour is used for the fifth combo", ini.combo6, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour is used for the sixth combo", ini.combo7, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour is used for the seventh combo", ini.combo8, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new TextEditor("what prefix to use for hitcircle numbers", ini.hitCirclePrefix));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new IntegerSpinnerEditor("by how many pixels should the hitcircle numbers overlap, positive means closer together", ini.hitCircleOverlap));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new TextEditor("what prefix to use for combo numbers", ini.comboPrefix));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new IntegerSpinnerEditor("by how many pixels should the combo numbers overlap, positive means closer together", ini.comboOverlap));
			content.add(Box.createVerticalStrut(2));

			content.add(new JPanel(new BorderLayout()));
		}
	}

	/**
	 * GUI based editor tab for the
	 * <code>[CatchTheBeat]</code> section in
	 * a skin configuration
	 * @author Roan
	 * @see SkinIni
	 */
	private static final class CtbTab extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -5006508447206574607L;

		/**
		 * Constructs a new CtbTab for
		 * the given skin configuration
		 * @param ini The skin configuration
		 *        to create an editor for
		 */
		private CtbTab(SkinIni ini){
			super(new BorderLayout());
			JPanel content = new ScrollPane();
			BoxLayout layout = new BoxLayout(content, BoxLayout.Y_AXIS);
			content.setLayout(layout);
			this.add(new JScrollPane(content), BorderLayout.CENTER);
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour is used for the dash", ini.hyperDash));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour is used for the fruits", ini.hyperDashFruit, true));
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			content.add(new ColorEditor("what colour is used for the after images", ini.hyperDashAfterImage, true));
			content.add(Box.createVerticalStrut(2));

			content.add(new JPanel(new BorderLayout()));
		}
	}

	/**
	 * GUI based editor tab for all the
	 * <code>[Mania]</code> sections in
	 * a skin configuration
	 * @author Roan
	 * @see SkinIni
	 * @see ManiaIni
	 * @see ManiaKeyTab
	 */
	private static final class ManiaTab extends JTabbedPane{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 337165984317388690L;

		/**
		 * Constructs a new ManiaTab for the
		 * Mania configurations in the given skin
		 * configuration. Empty tabs are created
		 * for key counts that do not yet exist
		 * in the skin configuration
		 * @param ini The skin configuration to
		 *        make an editor for
		 * @see ManiaIni
		 * @see SkinIni
		 */
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

		/**
		 * GUI based editor tab for a {@link ManiaIni}
		 * @author Roan
		 * @see ManiaIni
		 */
		private static final class ManiaKeyTab extends JPanel{
			/**
			 * Serial ID
			 */
			private static final long serialVersionUID = -6820855921720231002L;

			/**
			 * Initialises this ManiaKeyTab with the
			 * given mania configuration and key count
			 * @param ini The mania configuration this tab is for
			 * @param keys The key count this tab is for
			 */
			private void init(ManiaIni ini, int keys){
				removeAll();
				setLayout(new BorderLayout());
				JPanel content = new ScrollPane();
				BoxLayout layout = new BoxLayout(content, BoxLayout.Y_AXIS);
				content.setLayout(layout);
				this.add(new JScrollPane(content), BorderLayout.CENTER);
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleSpinnerEditor("where does the left column start", ini.columnStart));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleSpinnerEditor("up to which point can columns be drawn", ini.columnRight));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				if(ini.keys != 1){
					content.add(Box.createVerticalStrut(2));
					content.add(new DoubleArrayEditor("distance between columns", ini.columnSpacing));
					content.add(Box.createVerticalStrut(2));
					content.add(new JSeparator());
				}
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleArrayEditor("widths of the columns", ini.columnWidth));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleArrayEditor("widths of the column separators", ini.columnLineWidth));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleSpinnerEditor("thickness of the barline", ini.barlineHeight));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleArrayEditor("widths of LightingN for the columns", ini.lightingNWidth));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleArrayEditor("widths of LightingL for the column", ini.lightingLWidth));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new DoubleSpinnerEditor("height for all notes if columns have individual widths", ini.widthForNoteHeightScale));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new IntegerSpinnerEditor("at what height should the judgement line be drawn", ini.hitPosition, 0));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new IntegerSpinnerEditor("at what height should the stage lights be drawn", ini.lightPosition, 0));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new IntegerSpinnerEditor("at what height should the hitbursts be drawn", ini.scorePosition, 0));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new IntegerSpinnerEditor("at what height should the combo be drawn", ini.comboPosition, 0));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new CheckBoxEditor("should a solid line be drawn above the StageHint", ini.judgementLine));
				content.add(Box.createVerticalStrut(2));
				if(ini.keys % 2 == 0 && ini.keys >= 4){
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new ComboBoxEditor<SpecialStyle>("what special style (if avaible) should be used for this keycount", ini.specialStyle, SpecialStyle.values()));
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new ComboBoxEditor<ComboBurstStyle>("on what side should combobursts appear", ini.comboBurstStyle, ComboBurstStyle.values()));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				{
					JPanel panel = new JPanel(new SplitLayout());
					JCheckBox enabled = new JCheckBox("", ini.splitStages != null);
					JPanel settings = new JPanel(new BorderLayout());
					settings.add(enabled, BorderLayout.LINE_START);
					panel.add(new JLabel(" " + ini.splitStages.getName() + " (should the stage be split into 2 stages): "));
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
				content.add(new DoubleSpinnerEditor("when splitted what should be the distance between the stages", ini.stageSeparation, 0.0D));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new CheckBoxEditor("should hitburst only be shown on the stage it was scored", ini.separateScore));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new CheckBoxEditor("should keys be covered by notes when passed by them", ini.keysUnderNotes));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new CheckBoxEditor("should the stage be flipped", ini.upsideDown));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new CheckBoxEditor("should keys be flipped when the stage is flipped", ini.keyFlipWhenUpsideDown));
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new FlipEditor("should the unpressed key in column " + (col + 1) + " be flipped when the stage is flipped", ini.columns[col].keyFlipWhenUpsideDown));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new FlipEditor("should the pressed key in column " + (col + 1) + " be flipped when the stage is flipped", ini.columns[col].keyFlipWhenUpsideDownD));
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new CheckBoxEditor("should notes be flipped when the stage is flipped", ini.noteFlipWhenUpsideDown));
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new FlipEditor("should the note in column " + (col + 1) + " be flipped when the stage is flipped", ini.columns[col].noteFlipWhenUpsideDown));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new FlipEditor("should the head of the note in column " + (col + 1) + " be flipped when the stage is flipped", ini.columns[col].noteFlipWhenUpsideDownH));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new FlipEditor("should the body of the note in column " + (col + 1) + " be flipped when the stage is flipped", ini.columns[col].noteFlipWhenUpsideDownL));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new FlipEditor("should the tail of the note in column " + (col + 1) + " be flipped when the stage is flipped", ini.columns[col].noteFlipWhenUpsideDownT));
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new ComboBoxEditor<NoteBodyStyle>("what style should be used for all the hold note bodies", ini.noteBodyStyle, NoteBodyStyle.values()));
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new ComboBoxEditor<NoteBodyStyle>("what style should be used for the hold note " + (i + 1) + " body", ini.columns[i].noteBodyStyle, NoteBodyStyle.values(), true));
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new AlphaColorEditor("what colour should be used for the column lines", ini.colourColumnLine));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new AlphaColorEditor("what colour should be used for the bar line separator", ini.colourBarline));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new ColorEditor("what colour should be used for the judgement line", ini.colourJudgementLine));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new ColorEditor("what colour should be used for keybinding reminders", ini.colourKeyWarning));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new AlphaColorEditor("what colour should be used for the combo counter during holds", ini.colourHold));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new ColorEditor("what colour should be used for the combo counter when it breaks", ini.colourBreak));
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new AlphaColorEditor("what colour should be used for the lane of column " + (col + 1), ini.columns[col].colour));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new ColorEditor("what colour should be used for the lighting of column " + (col + 1), ini.columns[col].colourLight));
					content.add(Box.createVerticalStrut(2));
				}
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the left stage image", ini.stageLeft));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the right stage image", ini.stageRight));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the bottom stage image", ini.stageBottom));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the stage hint image", ini.stageHint));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the stage light image", ini.stageLight));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the note lighting image", ini.lightingN));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the hold note lighting image", ini.lightingL));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the warning arrow image", ini.warningArrow));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the hit0 image", ini.hit0));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the hit50 image", ini.hit50));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the hit100 image", ini.hit100));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the hit200 image", ini.hit200));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the hit300 image", ini.hit300));
				content.add(Box.createVerticalStrut(2));
				content.add(new JSeparator());
				content.add(Box.createVerticalStrut(2));
				content.add(new PathEditor("what is the name of the hit300g image", ini.hit300g));
				content.add(Box.createVerticalStrut(2));
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new PathEditor("what is the name for the unpressed key image of column " + (col + 1), ini.columns[col].keyImage, true));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new PathEditor("what is the name for the pressed key image of column " + (col + 1), ini.columns[col].keyImageD, true));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new PathEditor("what is the name for the note image of column " + (col + 1), ini.columns[col].noteImage, true));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new PathEditor("what is the name for the hold note head image of column " + (col + 1), ini.columns[col].noteImageH, true));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new PathEditor("what is the name for the hold note body image of column " + (col + 1), ini.columns[col].noteImageL, true));
					content.add(Box.createVerticalStrut(2));
				}
				for(int i = 0; i < ini.keys; i++){
					final int col = i;
					content.add(new JSeparator());
					content.add(Box.createVerticalStrut(2));
					content.add(new PathEditor("what is the name for the hold note tail image of column " + (col + 1), ini.columns[col].noteImageT, true));
					content.add(Box.createVerticalStrut(2));
				}

				content.add(new JPanel(new BorderLayout()));
			}
		}
	}
	
	/**
	 * Special case combo box editor for the
	 * <code>Flip</code>, <code>Don't flip</code>
	 * boolean field.
	 * @author Roan
	 * @see Setting
	 */
	private static final class FlipEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -4635968938790424674L;

		/**
		 * Constructs a new FlipEditor for the given setting
		 * and with the given hint.
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @see Setting
		 */
		private FlipEditor(String hint, Setting<Boolean> setting){
			super(new SplitLayout());
			JCheckBox enabled = new JCheckBox("", setting.getValue());
			JPanel settings = new JPanel(new BorderLayout());
			settings.add(enabled, BorderLayout.LINE_START);
			add(new JLabel(" " + setting.getName() + " (" + hint + "): "));
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
	
	/**
	 * Editor for rgb color type settings
	 * @author Roan
	 * @see Setting
	 * @see Colour
	 */
	private static final class ColorEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -1171023992187065714L;

		/**
		 * Constructs a new ColorEditor for the given setting
		 * and with the given hint.
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @see Setting
		 */
		private ColorEditor(String hint, Setting<Colour> setting){
			this(hint, setting, false);
		}
		
		/**
		 * Constructs a new ColorEditor for the given setting
		 * and with the given hint. If toggle is true the
		 * editor will allow the user to disable or enable the setting
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @param toggle Whether or not this editor should have an enabled/disable toggle
		 * @see Setting
		 */
		private ColorEditor(String hint, Setting<Colour> setting, boolean toggle){
			super(new SplitLayout());
			JPanel settings = new JPanel(new BorderLayout());
			add(new JLabel(" " + setting.getName() + " (" + hint + "): "));
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
	
	/**
	 * Editor for integer type settings
	 * @author Roan
	 * @see Setting
	 */
	private static final class IntegerSpinnerEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -8823113464197619825L;

		/**
		 * Constructs a new IntegerSpinnerEditor for the given setting
		 * and with the given hint.
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @see Setting
		 */
		private IntegerSpinnerEditor(String hint, Setting<Integer> setting){
			this(hint, setting, Integer.MIN_VALUE, false);
		}
		
		/**
		 * Constructs a new IntegerSpinnerEditor for the given setting
		 * and with the given hint and minimum value.
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @param min The minimum value for the setting
		 * @see Setting
		 */
		private IntegerSpinnerEditor(String hint, Setting<Integer> setting, int min){
			this(hint, setting, min, Integer.MAX_VALUE, false);
		}
		
		/**
		 * Constructs a new IntegerSpinnerEditor for the given setting
		 * and with the given hint. If toggle is true the
		 * editor will allow the user to disable or enable the setting
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @param toggle Whether or not this editor should have an enabled/disable toggle
		 * @see Setting
		 */
		private IntegerSpinnerEditor(String hint, Setting<Integer> setting, boolean toggle){
			this(hint, setting, Integer.MIN_VALUE, toggle);
		}
		
		/**
		 * Constructs a new IntegerSpinnerEditor for the given setting
		 * and with the given hint and minimum value. If toggle is true the
		 * editor will allow the user to disable or enable the setting
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @param min The minimum value for the setting
		 * @param toggle Whether or not this editor should have an enabled/disable toggle
		 * @see Setting
		 */
		private IntegerSpinnerEditor(String hint, Setting<Integer> setting, int min, boolean toggle){
			this(hint, setting, min, Integer.MAX_VALUE, toggle);
		}

		/**
		 * Constructs a new IntegerSpinnerEditor for the given setting
		 * and with the given hint and constraints. If toggle is true the
		 * editor will allow the user to disable or enable the setting
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @param min The minimum value for the setting
		 * @param max the maximum value for the setting
		 * @param toggle Whether or not this editor should have an enabled/disable toggle
		 * @see Setting
		 */
		private IntegerSpinnerEditor(String hint, Setting<Integer> setting, int min, int max, boolean toggle){
			super(new SplitLayout());
			add(new JLabel(" " + setting.getName() + " (" + hint + "): "));
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
	
	/**
	 * Editor for double type settings
	 * @author Roan
	 * @see Setting
	 */
	private static final class DoubleSpinnerEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -1377612278897030930L;

		/**
		 * Constructs a new DoubleSpinnerEditor for the given setting
		 * and with the given hint.
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @see Setting
		 */
		private DoubleSpinnerEditor(String hint, Setting<Double> setting){
			this(hint, setting, -Double.MAX_VALUE, false);
		}
		
		/**
		 * Constructs a new DoubleSpinnerEditor for the given setting
		 * and with the given hint and minimum value.
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @param min The minimum value for the setting
		 * @see Setting
		 */
		private DoubleSpinnerEditor(String hint, Setting<Double> setting, double min){
			this(hint, setting, min, Double.MAX_VALUE, false);
		}
		
		/**
		 * Constructs a new DoubleSpinnerEditor for the given setting
		 * and with the given hint. If toggle is true the
		 * editor will allow the user to disable or enable the setting
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @param toggle Whether or not this editor should have an enabled/disable toggle
		 * @see Setting
		 */
		private DoubleSpinnerEditor(String name, String hint, Setting<Double> setting, boolean toggle){
			this(hint, setting, -Double.MAX_VALUE, Double.MAX_VALUE, toggle);
		}
		
		/**
		 * Constructs a new DoubleSpinnerEditor for the given setting
		 * and with the given hint and minimum value. If toggle is true the
		 * editor will allow the user to disable or enable the setting
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @param min The minimum value for the setting
		 * @param toggle Whether or not this editor should have an enabled/disable toggle
		 * @see Setting
		 */
		private DoubleSpinnerEditor(String hint, Setting<Double> setting, double min, boolean toggle){
			this(hint, setting, min, Double.MAX_VALUE, toggle);
		}
		
		/**
		 * Constructs a new DoubleSpinnerEditor for the given setting
		 * and with the given hint and constraints. If toggle is true the
		 * editor will allow the user to disable or enable the setting
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @param min The minimum value for the setting
		 * @param max the maximum value for the setting
		 * @param toggle Whether or not this editor should have an enabled/disable toggle
		 * @see Setting
		 */
		private DoubleSpinnerEditor(String hint, Setting<Double> setting, double min, double max, boolean toggle){
			super(new SplitLayout());
			JPanel settings = new JPanel(new BorderLayout());
			add(new JLabel(" " + setting.getName() + " (" + hint + "): "));
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
	
	/**
	 * Editor for boolean type settings
	 * @author Roan
	 * @see Setting
	 */
	private static final class CheckBoxEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -4819354663525782395L;

		/**
		 * Constructs a new CheckBoxEditor for the given setting
		 * and with the given hint. If toggle is true the
		 * editor will allow the user to disable or enable the setting
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @see Setting
		 */
		private CheckBoxEditor(String hint, Setting<Boolean> setting){
			this(hint, null, setting);
		}
		
		/**
		 * Constructs a new CheckBoxEditor for the given setting
		 * and with the given hint. The given extra value is displayed
		 * to the right of the check box itself. If toggle is true the
		 * editor will allow the user to disable or enable the setting
		 * @param hint The hint for this setting
		 * @param extra The text to display to the right of the check box
		 * @param setting The setting to modify
		 * @see Setting
		 */
		private CheckBoxEditor(String hint, String extra, Setting<Boolean> setting){
			super(new SplitLayout());
			add(new JLabel(" " + setting.getName() + " (" + hint + "): "));
			JCheckBox check = extra == null ? new JCheckBox() : new JCheckBox(extra);
			check.setSelected(setting.getValue());
			check.addItemListener((e)->{
				setting.update(check.isSelected());
			});
			add(check);
		}
	}
	
	/**
	 * Editor for option list based settings
	 * @author Roan
	 * @param <T> The type of the list options
	 * @see Setting
	 */
	private static final class ComboBoxEditor<T> extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 8946535893069224896L;

		/**
		 * Constructs a new ComboBoxEditor for the given setting
		 * @param setting The setting to modify
		 * @param values The list of values to allow the user to choose from
		 * @see Setting
		 */
		private ComboBoxEditor(Setting<T> setting, T[] values){
			this(null, setting, values);
		}
		
		/**
		 * Constructs a new ComboBoxEditor for the given setting
		 * and with the given hint and value list.
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @param values The list of values to allow the user to choose from
		 * @see Setting
		 */
		private ComboBoxEditor(String hint, Setting<T> setting, T[] values){
			this(hint, setting, values, false);
		}
		
		/**
		 * Constructs a new ComboBoxEditor for the given setting
		 * and with the given hint and value list. If toggle is true the
		 * editor will allow the user to disable or enable the setting
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @param values The list of values to allow the user to choose from
		 * @param toggle Whether or not this editor should have an enabled/disable toggle
		 * @see Setting
		 */
		@SuppressWarnings("unchecked")
		private ComboBoxEditor(String hint, Setting<T> setting, T[] values, boolean toggle){
			super(new SplitLayout());
			JPanel settings = new JPanel(new BorderLayout());
			if(hint == null){
				add(new JLabel(" " + setting.getName() + ": "));
			}else{
				add(new JLabel(" " + setting.getName() + " (" + hint + "): "));
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
	
	/**
	 * Editor for rgba color type settings
	 * @author Roan
	 * @see Setting
	 * @see Colour
	 */
	private static final class AlphaColorEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -5486062097516828519L;

		/**
		 * Constructs a new AlphaColorEditor for the given setting
		 * and with the given hint.
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @see Setting
		 */
		private AlphaColorEditor(String hint, Setting<Colour> setting){
			super(new SplitLayout());
			JPanel settings = new JPanel(new BorderLayout());
			add(new JLabel(" " + setting.getName() + " (" + hint + "): "));
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
	
	/**
	 * Editor for text type settings
	 * @author Roan
	 * @see Setting
	 */
	private static final class TextEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 3436381071498830198L;
		
		/**
		 * Constructs a new TextEditor for the given setting.
		 * @param setting The setting to modify
		 * @see Setting
		 */
		private TextEditor(Setting<String> setting){
			this(null, setting);
		}
		
		/**
		 * Constructs a new TextEditor for the given setting
		 * and with the given hint.
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @see Setting
		 */
		private TextEditor(String hint, Setting<String> setting){
			super(new SplitLayout());
			if(hint == null){
				add(new JLabel(" " + setting.getName() + ": "));
			}else{
				add(new JLabel(" " + setting.getName() + " (" + hint + "): "));
			}
			JTextField field = new JTextField(setting.getValue());
			field.addActionListener((e)->{
				setting.update(field.getText());
			});
			add(field);
		}
	}
	
	/**
	 * Editor for double array type settings
	 * @author Roan
	 * @see Setting
	 */
	private static final class DoubleArrayEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 2821189742176611077L;

		/**
		 * Constructs a new DoubleArrayEditor for the given setting
		 * and with the given hint.
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @see Setting
		 */
		private DoubleArrayEditor(String hint, Setting<double[]> setting){
			this(hint, setting, false);
		}
		
		/**
		 * Constructs a new DoubleArrayEditor for the given setting
		 * and with the given hint. If toggle is true the
		 * editor will allow the user to disable or enable the setting
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @param toggle Whether or not this editor should have an enabled/disable toggle
		 * @see Setting
		 */
		private DoubleArrayEditor(String hint, Setting<double[]> setting, boolean toggle){
			super(new SplitLayout());
			JPanel settings = new JPanel(new BorderLayout());
			add(new JLabel(" " + setting.getName() + " (" + hint + "): "));
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
	
	/**
	 * Editor for file path type settings
	 * @author Roan
     * @see Setting
	 */
	private static final class PathEditor extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -5682762745185860450L;
		
		/**
		 * Constructs a new PathEditor for the given setting
		 * and with the given hint.
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @see Setting
		 */
		private PathEditor(String hint, Setting<String> setting){
			this(hint, setting, false);
		}
		
		/**
		 * Constructs a new PathEditor for the given setting
		 * and with the given hint. If toggle is true the
		 * editor will allow the user to disable or enable the setting
		 * @param hint The hint for this setting
		 * @param setting The setting to modify
		 * @param toggle Whether or not this editor should have an enabled/disable toggle
		 * @see Setting
		 */
		private PathEditor(String hint, Setting<String> setting, boolean toggle){
			super(new SplitLayout());
			JPanel settings = new JPanel(new BorderLayout());
			add(new JLabel(" " + setting.getName() + " (" + hint + "): "));
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
	
	/**
	 * Specialised text field for file path
	 * input, currently no different from a
	 * regular text field and only exists 
	 * because is localises future changes
	 * @author Roan
	 */
	private static final class PathField extends JTextField{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -4519936454813151564L;
		
		/**
		 * Constructs a new PathField
		 * with the given text
		 * @param text The current text to display
		 */
		private PathField(String text){
			super(text);
		}
	}

	/**
	 * Editor for a Colour Setting
	 * @author Roan
	 * @see Setting
	 */
	private static final class ColorSelector extends JPanel implements MouseListener{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 3286760817738876568L;
		/**
		 * The Setting that is modified by this editor
		 */
		private Setting<Colour> color;

		/**
		 * Constructs a new ColorSelector for
		 * the given Setting
		 * @param setting The setting to make an editor for
		 * @see Setting
		 */
		private ColorSelector(Setting<Colour> setting){
			color = setting;
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

	/**
	 * Helpers class for editing the values in a double array
	 * @author Roan
	 */
	private static final class DoubleArray extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 3145876156701959606L;

		/**
		 * Constructs a new DoubleArray
		 * with the given data
		 * @param data The current array data
		 */
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
	
	/**
	 * Specialized text field that only allows
	 * a comma separated list of positive integers as input
	 * @author Roan
	 */
	private static final class ListField extends JTextField{	
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -4456019117693719163L;

		/**
		 * Constructs a new ListField
		 * with the given text
		 * @param text The current field text
		 */
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
		
		/**
		 * Filter that only allows a comma separed list
		 * of positive integers as the field content
		 * @author Roan
		 * @see ListField
		 */
		private static final class ListFilter extends DocumentFilter{
			/**
			 * Regex that only allows a comma separated list of positive integers
			 */
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

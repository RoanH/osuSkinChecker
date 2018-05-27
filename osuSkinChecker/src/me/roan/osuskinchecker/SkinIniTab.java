package me.roan.osuskinchecker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import me.roan.osuskinchecker.SkinIni.Version;

public class SkinIniTab extends JTabbedPane{
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -5575803475931849256L;
		
	public void init(SkinIni ini){
		removeAll();
		add("General", new GeneralTab(ini));
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
			JPanel content = new JPanel();
			BoxLayout layout = new BoxLayout(content, BoxLayout.Y_AXIS);
			content.setLayout(layout);
			this.add(new JScrollPane(content), BorderLayout.CENTER);
			
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Skin name: "));
				JTextField name = new JTextField(ini.name);
				name.addKeyListener(new KeyListener(){

					@Override
					public void keyTyped(KeyEvent e) {					
					}

					@Override
					public void keyPressed(KeyEvent e) {					
					}

					@Override
					public void keyReleased(KeyEvent e) {
						ini.name = name.getText();
					}
				});
				panel.add(name);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Author name: "));
				JTextField author = new JTextField(ini.author);
				author.addKeyListener(new KeyListener(){

					@Override
					public void keyTyped(KeyEvent e) {					
					}

					@Override
					public void keyPressed(KeyEvent e) {					
					}

					@Override
					public void keyReleased(KeyEvent e) {
						ini.author = author.getText();
					}
				});
				panel.add(author);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Version: "));
				JComboBox<Version> version = new JComboBox<Version>(Version.values());
				version.setSelectedItem(ini.version);
				version.addItemListener((e)->{
					ini.version = (Version) version.getSelectedItem();
				});
				panel.add(version);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Cursor Expand (should the cursor expand when clicking): "));
				JCheckBox check = new JCheckBox();
				check.setSelected(ini.cursorExpand);
				check.addItemListener((e)->{
					ini.cursorExpand = check.isSelected();
				});
				panel.add(check);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Cursor Centre (should the cursor be centered): "));
				JCheckBox check = new JCheckBox("(false implies the top left corner)");
				check.setSelected(ini.cursorCentre);
				check.addItemListener((e)->{
					ini.cursorCentre = check.isSelected();
				});
				panel.add(check);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Cursor Rotate (should the cursor rotate): "));
				JCheckBox check = new JCheckBox();
				check.setSelected(ini.cursorRotate);
				check.addItemListener((e)->{
					ini.cursorRotate = check.isSelected();
				});
				panel.add(check);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Cursor Trail Rotate (should the cursor trail rotate): "));
				JCheckBox check = new JCheckBox();
				check.setSelected(ini.cursorTrailRotate);
				check.addItemListener((e)->{
					ini.cursorTrailRotate = check.isSelected();
				});
				panel.add(check);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Animation Framerate (how many frames per second for most animations): "));
				panel.add(new JLabel("TODO"));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Layered Hit Sounds (should hitnormal sounds always be played): "));
				JCheckBox check = new JCheckBox();
				check.setSelected(ini.layeredHitSounds);
				check.addItemListener((e)->{
					ini.layeredHitSounds = check.isSelected();
				});
				panel.add(check);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Combo Burst Random (should combust be shown in a random order): "));
				JCheckBox check = new JCheckBox("(false implies that they will appear in order)");
				check.setSelected(ini.comboBurstRandom);
				check.addItemListener((e)->{
					ini.comboBurstRandom = check.isSelected();
				});
				panel.add(check);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Custom Combo Burst Sounds (on which combo marks should the comboburst sounds be played): "));
				panel.add(new JLabel("TODO comma sep"));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Song Select Active Text (what colour should the text of active panels be tinted): "));
				panel.add(new ColorSelector(ini.songSelectActiveText, (color)->{
					ini.songSelectActiveText = color;
				}));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Song Select Inactive Text (what colour should the text of inactive panels be tinted): "));
				panel.add(new ColorSelector(ini.songSelectInactiveText, (color)->{
					ini.songSelectInactiveText = color;
				}));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Menu Glow (what colour should the spectrum bars be coloured in): "));
				panel.add(new ColorSelector(ini.menuGlow, (color)->{
					ini.menuGlow = color;
				}));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Star Break Additive (what colour should be added to star2 during breaks): "));
				panel.add(new ColorSelector(ini.starBreakAdditive, (color)->{
					ini.starBreakAdditive = color;
				}));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Input Overlay Text (what colour should the input keys be tinted): "));
				panel.add(new ColorSelector(ini.inputOverlayText, (color)->{
					ini.inputOverlayText = color;
				}));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Combo 1 (what colour is used for the last combo): "));
				panel.add(new ColorSelector(ini.combo1, (color)->{
					ini.combo1 = color;
				}));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Combo 2 (what colour is used for the first combo): "));
				panel.add(new ColorSelector(ini.combo2, (color)->{
					ini.combo2 = color;
				}));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Combo 3 (what colour is used for the second combo): "));
				panel.add(new ColorSelector(ini.combo3, (color)->{
					ini.combo3 = color;
				}));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Combo4 (what colour is used for the third combo): "));
				panel.add(new ColorSelector(ini.combo4, (color)->{
					ini.combo4 = color;
				}));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				JCheckBox enabled = new JCheckBox("", ini.combo5 != null);
				JPanel settings = new JPanel(new BorderLayout());
				settings.add(enabled, BorderLayout.LINE_START);
				panel.add(new JLabel(" Combo 5 (what colour is used for the fourth combo): "));
				ColorSelector selector = new ColorSelector(ini.combo5, (color)->{
					if(enabled.isSelected()){
						ini.combo5 = color;
					}
				});
				settings.add(selector, BorderLayout.CENTER);
				enabled.addActionListener((e)->{
					if(enabled.isSelected()){
						ini.combo5 = selector.color;
					}else{
						ini.combo5 = null;
					}
				});
				panel.add(settings);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				JCheckBox enabled = new JCheckBox("", ini.combo6 != null);
				JPanel settings = new JPanel(new BorderLayout());
				settings.add(enabled, BorderLayout.LINE_START);
				panel.add(new JLabel(" Combo 6 (what colour is used for the fifth combo): "));
				ColorSelector selector = new ColorSelector(ini.combo6, (color)->{
					if(enabled.isSelected()){
						ini.combo6 = color;
					}
				});
				settings.add(selector, BorderLayout.CENTER);
				enabled.addActionListener((e)->{
					if(enabled.isSelected()){
						ini.combo6 = selector.color;
					}else{
						ini.combo6 = null;
					}
				});
				panel.add(settings);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				JCheckBox enabled = new JCheckBox("", ini.combo7 != null);
				JPanel settings = new JPanel(new BorderLayout());
				settings.add(enabled, BorderLayout.LINE_START);
				panel.add(new JLabel(" Combo 7 (what colour is used for the sixth combo): "));
				ColorSelector selector = new ColorSelector(ini.combo7, (color)->{
					if(enabled.isSelected()){
						ini.combo7 = color;
					}
				});
				settings.add(selector, BorderLayout.CENTER);
				enabled.addActionListener((e)->{
					if(enabled.isSelected()){
						ini.combo7 = selector.color;
					}else{
						ini.combo7 = null;
					}
				});
				panel.add(settings);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				JCheckBox enabled = new JCheckBox("", ini.combo8 != null);
				JPanel settings = new JPanel(new BorderLayout());
				settings.add(enabled, BorderLayout.LINE_START);
				panel.add(new JLabel(" Combo 8 (what colour is used for the seventh combo): "));
				ColorSelector selector = new ColorSelector(ini.combo8, (color)->{
					if(enabled.isSelected()){
						ini.combo8 = color;
					}
				});
				settings.add(selector, BorderLayout.CENTER);
				enabled.addActionListener((e)->{
					if(enabled.isSelected()){
						ini.combo8 = selector.color;
					}else{
						ini.combo8 = null;
					}
				});
				panel.add(settings);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Hit Circle Prefix (what prefix to use for hitcircle numbers): "));
				JTextField field = new JTextField(ini.hitCirclePrefix);
				field.addKeyListener(new KeyListener(){

					@Override
					public void keyTyped(KeyEvent e) {					
					}

					@Override
					public void keyPressed(KeyEvent e) {					
					}

					@Override
					public void keyReleased(KeyEvent e) {
						ini.hitCirclePrefix = field.getText();
					}
				});
				panel.add(field);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			
			content.add(new JPanel(new BorderLayout()));
		}
	}
	
	private static final class StandardTab extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -639789728841322306L;
		
		public StandardTab(SkinIni ini) {
			super(new BorderLayout());
			JPanel content = new JPanel();
			BoxLayout layout = new BoxLayout(content, BoxLayout.Y_AXIS);
			content.setLayout(layout);
			this.add(new JScrollPane(content), BorderLayout.CENTER);
			
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Hit Circle Overlay Above Number (should the hitcircleoverlay be drawn above the numbers): "));
				JCheckBox check = new JCheckBox("(false implies that they will be drawn below the overlay)");
				check.setSelected(ini.hitCircleOverlayAboveNumber);
				check.addItemListener((e)->{
					ini.hitCircleOverlayAboveNumber = check.isSelected();
				});
				panel.add(check);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Slider Style (what filling to use for sliders): "));
				JComboBox<String> sel = new JComboBox<String>(new String[] {"Segments", "Gradient"});
				sel.setSelectedIndex(ini.sliderStyle - 1);
				sel.addItemListener((e)->{
					ini.sliderStyle = sel.getSelectedIndex() - 1;
				});
				panel.add(sel);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Slider Ball Flip (should the sliderball flip at the reverse arrow): "));
				JCheckBox check = new JCheckBox();
				check.setSelected(ini.sliderBallFlip);
				check.addItemListener((e)->{
					ini.sliderBallFlip = check.isSelected();
				});
				panel.add(check);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Allow Slider Ball Tint (should the sliderball be tinted in combo colours): "));
				JCheckBox check = new JCheckBox();
				check.setSelected(ini.allowSliderBallTint);
				check.addItemListener((e)->{
					ini.allowSliderBallTint = check.isSelected();
				});
				panel.add(check);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Spinner No Blink (should the highest bar of the metre always be visible): "));
				JCheckBox check = new JCheckBox();
				check.setSelected(ini.spinnerNoBlink);
				check.addItemListener((e)->{
					ini.spinnerNoBlink = check.isSelected();
				});
				panel.add(check);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Spinner Fade Playfield (should the spinner add black bars during spins): "));
				JCheckBox check = new JCheckBox();
				check.setSelected(ini.spinnerFadePlayfield);
				check.addItemListener((e)->{
					ini.spinnerFadePlayfield = check.isSelected();
				});
				panel.add(check);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Spinner Frequency Modulate (should the spinnerspin sound pitch go up while spinning): "));
				JCheckBox check = new JCheckBox();
				check.setSelected(ini.spinnerFrequencyModulate);
				check.addItemListener((e)->{
					ini.spinnerFrequencyModulate = check.isSelected();
				});
				panel.add(check);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Slider Ball (what colour should the slider ball be coloured): "));
				panel.add(new ColorSelector(ini.sliderBall, (color)->{
					ini.sliderBall = color;
				}));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				JCheckBox enabled = new JCheckBox("", ini.sliderTrackOverride != null);
				JPanel settings = new JPanel(new BorderLayout());
				settings.add(enabled, BorderLayout.LINE_START);
				panel.add(new JLabel(" Slider Track Override (what colour should the slider body be coloured): "));
				ColorSelector selector = new ColorSelector(ini.sliderTrackOverride, (color)->{
					if(enabled.isSelected()){
						ini.sliderTrackOverride = color;
					}
				});
				settings.add(selector, BorderLayout.CENTER);
				enabled.addActionListener((e)->{
					if(enabled.isSelected()){
						ini.sliderTrackOverride = selector.color;
					}else{
						ini.sliderTrackOverride = null;
					}
				});
				panel.add(settings);
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Slider Border (what colour should be used for sliderborders): "));
				panel.add(new ColorSelector(ini.sliderBorder, (color)->{
					ini.sliderBorder = color;
				}));
				content.add(panel);
			}
			content.add(Box.createVerticalStrut(2));
			content.add(new JSeparator());
			content.add(Box.createVerticalStrut(2));
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel(" Spinner Background (what colour should be added to the spinner-background): "));
				panel.add(new ColorSelector(ini.spinnerBackground, (color)->{
					ini.spinnerBackground = color;
				}));
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
		
		public CtbTab(SkinIni ini) {

		}
	}
	
	private static final class ManiaTab extends JTabbedPane{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 337165984317388690L;
		
		public ManiaTab(SkinIni ini) {
			
		}
	}
	
	private static final class ColorSelector extends JPanel implements MouseListener {
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 3286760817738876568L;
		private Color color;
		private ColorEvent handler;
		
		private ColorSelector(Color def, ColorEvent handler) {
			this.setBackground(def == null ? (color = Color.BLACK) : (color = def));
			this.addMouseListener(this);
			this.handler = handler;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			Color c = JColorChooser.showDialog(this, "Color Chooser", color);
			if(c != null) {
				this.color = c;
				this.setBackground(c);
				handler.colorChanged(c);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {			
		}
		
		@FunctionalInterface
		private static abstract interface ColorEvent{
			
			public abstract void colorChanged(Color color);
		}
	}
}

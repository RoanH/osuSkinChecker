package me.roan.osuskinchecker;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

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
			JScrollPane pane = new JScrollPane(content);
			pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			this.add(pane, BorderLayout.CENTER);
			
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
				check.setSelected(ini.layeredHitSounds);
				check.addItemListener((e)->{
					ini.layeredHitSounds = check.isSelected();
				});
				panel.add(check);
				content.add(panel);
			}
			
			content.add(new JPanel(new BorderLayout()));
		}
	}
	
	private static final class StandardTab extends JPanel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -639789728841322306L;
		
		public StandardTab(SkinIni ini) {

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
}

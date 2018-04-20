package me.roan.osuskinchecker;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

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
			
			{
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel("Skin name: "));
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
			
			{
				content.add(Box.createVerticalStrut(5));
				JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
				panel.add(new JLabel("Author name: "));
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

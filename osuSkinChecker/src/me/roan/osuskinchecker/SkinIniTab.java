package me.roan.osuskinchecker;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class SkinIniTab extends JTabbedPane{
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -5575803475931849256L;
	
	protected SkinIniTab(){
		add("General", new GeneralTab());
		add("osu! Standard", new StandardTab());
		add("osu! Catch", new CtbTab());
		add("osu! Mania", new ManiaTab());
	}
	
	private static final class GeneralTab extends JPanel{
		
	}
	
	private static final class StandardTab extends JPanel{
		
	}
	
	private static final class CtbTab extends JPanel{
		
	}
	
	private static final class ManiaTab extends JTabbedPane{
		
	}
}

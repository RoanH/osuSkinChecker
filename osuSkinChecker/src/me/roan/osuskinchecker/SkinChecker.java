package me.roan.osuskinchecker;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class SkinChecker {

	//https://docs.google.com/spreadsheets/d/1bhnV-CQRMy3Z0npQd9XSoTdkYxz0ew5e648S00qkJZ8/edit#gid=2074725196

	//options:
	//N=variable amount
	//M=variable amount not preceded by a -
	//S=single image only no higher/lower res
	//C=custom path
	//P=custom path prefix
	//L=legacy
	//-= n/a

	private static final Map<String, Map<String, List<Info>>> imagesMap = new HashMap<String, Map<String, List<Info>>>();
	private static final Map<String, Map<String, List<Info>>> soundsMap = new HashMap<String, Map<String, List<Info>>>();
	static File skinFolder;
	static boolean checkSD = true;
	static boolean checkHD = false;
	static boolean checkLegacy = false;
	static boolean showAll = false;
	static Map<Integer, File> customPathing = new HashMap<Integer, File>();
	private static List<Model> listeners = new ArrayList<Model>();
	private static final JFrame frame = new JFrame("Skin Checker for osu!");
	private static JLabel skin;
	private static JTabbedPane imageTabs;
	private static JTabbedPane soundTabs;

	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable t) {
		}
		try {
			readDatabase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		imageTabs = new JTabbedPane();
		soundTabs = new JTabbedPane();
		skin = new JLabel("<html><i>no skin selected</i></html>");
		buildGUI();
	}

	public static void checkSkin(File folder) throws IOException{
		if(folder == null){
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setMultiSelectionEnabled(false);
			if(chooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION){
				JOptionPane.showMessageDialog(frame, "No skin selected!", "Skin Checker", JOptionPane.ERROR_MESSAGE);
				return;
			}
			skinFolder = chooser.getSelectedFile();
		}else{
			skinFolder = folder;
		}
		skin.setText(skinFolder.getName());
		
		if(!new File(skinFolder, "skin.ini").exists()){
			JOptionPane.showMessageDialog(frame, "This folder doesn't have a skin.ini file.\nWithout this file this skin won't even be recognized as a skin!\nAdd a skin.ini and then run this program again.", "Skin Checker", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		mapToTabs(imageTabs, imagesMap);
		mapToTabs(soundTabs, soundsMap);
			
		parseINI();
	}
	
	/**
	 * Check the SkinChecker version to see
	 * if we are running the latest version
	 * @return The latest version
	 */
	private static final String checkVersion(){
		try{ 			
			HttpURLConnection con = (HttpURLConnection) new URL("https://api.github.com/repos/RoanH/osuSkinChecker/tags").openConnection(); 			
			con.setRequestMethod("GET"); 		
			con.setConnectTimeout(10000); 					   
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream())); 	
			String line = reader.readLine(); 		
			reader.close(); 	
			String[] versions = line.split("\"name\":\"v");
			int max_main = 3;
			int max_sub = 0;
			String[] tmp;
			for(int i = 1; i < versions.length; i++){
				tmp = versions[i].split("\",\"")[0].split("\\.");
				if(Integer.parseInt(tmp[0]) > max_main){
					max_main = Integer.parseInt(tmp[0]);
					max_sub = Integer.parseInt(tmp[1]);
				}else if(Integer.parseInt(tmp[0]) < max_main){
					continue;
				}else{
					if(Integer.parseInt(tmp[1]) > max_sub){
						max_sub = Integer.parseInt(tmp[1]);
					}
				}
			}
			return "v" + max_main + "." + max_sub;
		}catch(Exception e){ 	
			return null;
			//No Internet access or something else is wrong,
			//No problem though since this isn't a critical function
		}
	}
	
	private static void parseINI() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(skinFolder, "skin.ini"))));
		String line;
		while((line = reader.readLine()) != null){
			if(line.startsWith("ScorePrefix:") || line.startsWith("ComboPrefix:")){
				customPathing.put(2, new File(skinFolder, line.substring(12).trim()));
			}else if(line.startsWith("HitCirclePrefix:")){
				customPathing.put(1, new File(skinFolder, line.substring(16).trim()));
			}else if(line.trim().equals("[Mania]")){
				int keys = -1;
				while(true){
					reader.mark(100);
					line = reader.readLine();
					if(line == null || line.startsWith("[")){
						reader.reset();
						break;
					}
					String[] args = line.split(":");
					if(args[0].startsWith("Keys")){
						keys = Integer.parseInt(args[1].trim());
					}else if(args[0].startsWith("KeyImage")){
						if(args[0].endsWith("D")){
							customPathing.put(keys * 100 + 22, new File(skinFolder, args[1].trim()));
						}else{
							customPathing.put(keys * 100 + 21, new File(skinFolder, args[1].trim()));
						}
					}else if(args[0].startsWith("NoteImage")){
						if(args[0].endsWith("H")){
							customPathing.put(keys * 100 + 32, new File(skinFolder, args[1].trim()));
						}else if(args[0].endsWith("T")){
							customPathing.put(keys * 100 + 33, new File(skinFolder, args[1].trim()));
						}else if(args[0].endsWith("L")){
							customPathing.put(keys * 100 + 34, new File(skinFolder, args[1].trim()));
						}else{
							customPathing.put(keys * 100 + 31, new File(skinFolder, args[1].trim()));
						}
					}else if(args[0].startsWith("StageLeft")){
						customPathing.put(keys * 100 + 11, new File(skinFolder, args[1].trim()));
					}else if(args[0].startsWith("StageRight")){
						customPathing.put(keys * 100 + 12, new File(skinFolder, args[1].trim()));
					}else if(args[0].startsWith("StageBottom")){
						customPathing.put(keys * 100 + 13, new File(skinFolder, args[1].trim()));
					}else if(args[0].startsWith("StageHint")){
						customPathing.put(keys * 100 + 14, new File(skinFolder, args[1].trim()));
					}else if(args[0].startsWith("StageLight")){
						customPathing.put(keys * 100 + 15, new File(skinFolder, args[1].trim()));
					}else if(args[0].startsWith("LightingN")){
						customPathing.put(keys * 100 + 17, new File(skinFolder, args[1].trim()));
					}else if(args[0].startsWith("LightingL")){
						customPathing.put(keys * 100 + 18, new File(skinFolder, args[1].trim()));
					}else if(args[0].startsWith("WarningArrow")){
						customPathing.put(keys * 100 + 16, new File(skinFolder, args[1].trim()));
					}else if(args[0].startsWith("Hit0")){
						customPathing.put(keys * 100 + 41, new File(skinFolder, args[1].trim()));
					}else if(args[0].startsWith("Hit50")){
						customPathing.put(keys * 100 + 42, new File(skinFolder, args[1].trim()));
					}else if(args[0].startsWith("Hit100")){
						customPathing.put(keys * 100 + 43, new File(skinFolder, args[1].trim()));
					}else if(args[0].startsWith("Hit200")){
						customPathing.put(keys * 100 + 44, new File(skinFolder, args[1].trim()));
					}else if(args[0].startsWith("Hit300")){
						customPathing.put(keys * 100 + 45, new File(skinFolder, args[1].trim()));
					}else if(args[0].startsWith("Hit300g")){
						customPathing.put(keys * 100 + 6, new File(skinFolder, args[1].trim()));
					}
				}
			}
		}
		reader.close();
	}

	public static void buildGUI(){
		JPanel content = new JPanel(new BorderLayout());
		JTabbedPane categories = new JTabbedPane();
				
		categories.add("Images", imageTabs);
		categories.add("Sounds", soundTabs);
		
		categories.setBorder(BorderFactory.createTitledBorder("Files"));
		content.add(categories);
		
		JPanel controls = new JPanel(new GridLayout(5, 1, 0, 0));
		JCheckBox chd = new JCheckBox("Report images that are missing a HD version.", false);
		JCheckBox csd = new JCheckBox("Report images that are missing a SD version.", true);
		JCheckBox call = new JCheckBox("Report all files (show files that aren't missing in the skin).", false);
		JCheckBox clegacy = new JCheckBox("Report missing legacy files.", false);
		JCheckBox cempty = new JCheckBox("Ignore a missing HD image if an 'empty' SD image exists.", false);//TODO implement
		controls.add(chd);
		controls.add(csd);
		controls.add(call);
		controls.add(clegacy);
		controls.add(cempty);
		chd.addActionListener((e)->{
			checkHD = chd.isSelected();
			for(Model m : listeners){
				m.updateView();
			}
		});
		csd.addActionListener((e)->{
			checkSD = csd.isSelected();
			for(Model m : listeners){
				m.updateView();
			}
		});
		call.addActionListener((e)->{
			showAll = call.isSelected();
			for(Model m : listeners){
				m.updateView();
			}
		});
		clegacy.addActionListener((e)->{
			checkLegacy = clegacy.isSelected();
			for(Model m : listeners){
				m.updateView();
			}
		});
		
		JPanel buttons = new JPanel(new GridLayout(3, 1));
		JButton openSkin = new JButton("Open skin");
		JButton openFolder = new JButton("Open skin folder for the current skin");
		JButton recheck = new JButton("Re-check skin");
		buttons.add(openSkin);
		buttons.add(openFolder);
		buttons.add(recheck);
		openSkin.addActionListener((e)->{
			try {
				checkSkin(null);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		openFolder.addActionListener((e)->{
			if(skinFolder != null){
				try {
					Desktop.getDesktop().open(skinFolder);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(frame, "No skin currently selected!", "Skin Checker", JOptionPane.ERROR_MESSAGE);
			}
		});
		recheck.addActionListener((e)->{
			if(skinFolder != null){
				try {
					checkSkin(skinFolder);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(frame, "No skin currently selected!", "Skin Checker", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JPanel flow = new JPanel();
		flow.setBorder(BorderFactory.createTitledBorder("Controls"));
		flow.add(buttons);
		
		JPanel side = new JPanel(new BorderLayout());
		skin.setBorder(BorderFactory.createTitledBorder("Skin"));
		skin.setFont(new Font("Dialog", Font.BOLD, 12));
		skin.setHorizontalAlignment(SwingConstants.CENTER);
		side.add(flow, BorderLayout.CENTER);
		side.add(skin, BorderLayout.PAGE_END);
		
		JPanel controlPanel = new JPanel(new BorderLayout());
		controlPanel.add(controls, BorderLayout.CENTER);
		controlPanel.add(side, BorderLayout.LINE_END);
		controls.setBorder(BorderFactory.createTitledBorder("Filter"));
		content.add(controlPanel, BorderLayout.PAGE_START);
		
		JPanel links = new JPanel(new GridLayout(2, 1, 0, 4));
		links.setBorder(BorderFactory.createTitledBorder("Links"));
		JLabel forumPost = new JLabel("<html>Forum post: <font color=blue><u><i></i></u></font></html>");//TODO fill in forum link
		JLabel sheet = new JLabel("<html>Spreadsheet with information on each file: <font color=blue><u><i>https://docs.google.com/spreadsheets/d/1bhnV-CQRMy3Z0npQd9XSoTdkYxz0ew5e648S00qkJZ8/edit</font></i></u></html>");
		links.add(forumPost);
		links.add(sheet);
		forumPost.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(Desktop.isDesktopSupported()){
					try {//TODO fill in link
						Desktop.getDesktop().browse(new URL("").toURI());
					} catch (IOException | URISyntaxException e1) {
						//pity
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		sheet.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(Desktop.isDesktopSupported()){
					try {
						Desktop.getDesktop().browse(new URL("https://docs.google.com/spreadsheets/d/1bhnV-CQRMy3Z0npQd9XSoTdkYxz0ew5e648S00qkJZ8/edit").toURI());
					} catch (IOException | URISyntaxException e1) {
						//pity
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		JPanel lower = new JPanel(new BorderLayout());
		lower.add(links, BorderLayout.CENTER);
		JPanel version = new JPanel(new GridLayout(2, 1, 0, 4));
		version.add(new JLabel("Running version: v1.0"));//XXX version
		String ver = checkVersion();
		version.add(new JLabel("Latest version: " + (ver == null ? "Unknown" : ver)));
		version.setBorder(BorderFactory.createTitledBorder("Version"));
		lower.add(version, BorderLayout.LINE_END);
		content.add(lower, BorderLayout.PAGE_END);
		
		frame.add(content);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private static void mapToTabs(JTabbedPane tabs, Map<String, Map<String, List<Info>>> map){
		tabs.removeAll();
		for(Entry<String, Map<String, List<Info>>> entry : map.entrySet()){
			JTabbedPane inner = new JTabbedPane();
			for(Entry<String, List<Info>> e : entry.getValue().entrySet()){
				inner.add(e.getKey(), new JScrollPane(getTableData(e.getValue())));
			}
			tabs.add(entry.getKey(), inner);
		}
	}
	
	private static JTable getTableData(final List<Info> info){
		JTable table = new JTable();
		Model model = info.get(0) instanceof ImageInfo ? new ImageModel(info) : (info.get(0) instanceof SoundInfo ? new SoundModel(info) : null);
		listeners.add(model);
		table.setModel(model);
		return table;
	}

	private static void readDatabase() throws IOException{
		imagesMap.put("Menu", readDataFile("menu.txt", false));
		imagesMap.put("osu!", readDataFile("osu.txt", false));
		imagesMap.put("Taiko", readDataFile("taiko.txt", false));
		imagesMap.put("Mania", readDataFile("mania.txt", false));
		imagesMap.put("Catch", readDataFile("catch.txt", false));
		imagesMap.put("Miscellaneous", readDataFile("misc.txt", false));
		imagesMap.put("Gameplay", readDataFile("gameplay.txt", false));
		soundsMap.put("Gameplay", readDataFile("gameplay-sounds.txt", true));
		soundsMap.put("Menu", readDataFile("menu-sounds.txt", true));
		soundsMap.put("osu!", readDataFile("osu-sounds.txt", true));
		soundsMap.put("Taiko", readDataFile("taiko-sounds.txt", true));
	}

	private static Map<String, List<Info>> readDataFile(String name, boolean isSound) throws IOException{
		Map<String, List<Info>> data = new HashMap<String, List<Info>>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(name)));
		List<Info> writing = null;
		String line;
		while((line = reader.readLine()) != null){
			if(line.trim().isEmpty()){
				continue;
			}else if(line.startsWith("===>")){
				writing = new ArrayList<Info>();
				data.put(line.substring(4).trim(), writing);
			}else{
				if(isSound){
					writing.add(new SoundInfo(line));
				}else{
					writing.add(new ImageInfo(line));
				}
			}
		}
		return data;
	}
}

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * This program can be used to see what
 * elements a skin skins and to see what
 * elements are still missing from a skin.
 * It can also be used to find missing HD
 * images or vice versa.
 * @author Roan
 */
public class SkinChecker {
	/**
	 * Layered map with the information
	 * about all the images
	 */
	private static final Map<String, Map<String, List<Info>>> imagesMap = new HashMap<String, Map<String, List<Info>>>();
	/**
	 * Layered map with the information
	 * about all the sound files
	 */
	private static final Map<String, Map<String, List<Info>>> soundsMap = new HashMap<String, Map<String, List<Info>>>();
	/**
	 * Folder of the skin currently being checked
	 */
	protected static File skinFolder;
	/**
	 * Whether or not to check for missing SD images
	 */
	protected static boolean checkSD = true;
	/**
	 * Whether or not to check for missing HD images
	 */
	protected static boolean checkHD = false;
	/**
	 * Whether or not to check for missing legacy images
	 */
	protected static boolean checkLegacy = false;
	/**
	 * Whether or not to show all files regardless of whether
	 * they are present or not
	 */
	protected static boolean showAll = false;
	/**
	 * Whether or not to ignore missing HD files of
	 * images that have an empty SD image
	 */
	protected static boolean ignoreEmpty = true;
	/**
	 * Map, mapping all the custom path ID's to the File
	 * specified in the skin.ini
	 */
	protected static Map<Integer, File> customPathing = new HashMap<Integer, File>();
	/**
	 * List of all the tables model that is used to
	 * update the tables when the filter changes
	 */
	private static List<Model> listeners = new ArrayList<Model>();
	/**
	 * Main frame
	 */
	private static final JFrame frame = new JFrame("Skin Checker for osu!");
	/**
	 * The JLabel that displays the name of the skin
	 * currently being checked
	 */
	private static JLabel skin;
	/**
	 * The JTabbedPane that lists all the images
	 */
	private static JTabbedPane imageTabs;
	/**
	 * The JTabbedPane that lists all the sound files
	 */
	private static JTabbedPane soundTabs;
	/**
	 * The JFileChooser used to ask the user
	 * for skin
	 */
	private static JFileChooser chooser;
	/**
	 * Initial list of all the files in the skin folder
	 */
	protected static List<File> allFiles = new ArrayList<File>();
	/**
	 * List of all the information objects
	 */
	private static List<Info> info = new ArrayList<Info>();
	/**
	 * Model for the list that displays files
	 * that should not be in the skin
	 */
	private static DefaultListModel<String> foreignFiles = new DefaultListModel<String>();
	/**
	 * Whether or not to ignore a missing
	 * SD image when a HD version exists
	 */
	protected static boolean ignoreSD = false;
	private static SkinIniTab iniTab;

	/**
	 * Main method
	 * @param args No valid command line options
	 */
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
		
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setMultiSelectionEnabled(false);
		imageTabs = new JTabbedPane();
		soundTabs = new JTabbedPane();
		skin = new JLabel("<html><i>no skin selected</i></html>");
		buildGUI();
	}

	/**
	 * Builds the GUI
	 */
	public static void buildGUI(){
		try {
			frame.setIconImage(ImageIO.read(ClassLoader.getSystemResource("skinchecker.png")));
		} catch (IOException e2) {
		}
		JPanel content = new JPanel(new BorderLayout());
		JTabbedPane categories = new JTabbedPane();
		
		JPanel foreign = new JPanel(new BorderLayout());
		foreign.add(new JScrollPane(new JList<String>(foreignFiles)), BorderLayout.CENTER);
		foreign.add(new JLabel("This is a list of files that are in the skin folder but serve no purpose."), BorderLayout.PAGE_START);
		
		JPanel ini = new JPanel(new BorderLayout());
		ini.add(new JLabel("Settings with an additional leading checkbox require you to check this check box if you want to use the setting. Otherwise the setting is left as 'undefined'."), BorderLayout.PAGE_START);
		ini.add(iniTab = new SkinIniTab(), BorderLayout.CENTER);
				
		categories.add("Images", imageTabs);
		categories.add("Sounds", soundTabs);
		categories.add("Skin configuration", ini);
		categories.add("Foreign Files", foreign);
		
		categories.setBorder(BorderFactory.createTitledBorder("Files"));
		content.add(categories);
		
		JPanel controls = new JPanel(new GridLayout(6, 1, 0, 0));
		JCheckBox chd = new JCheckBox("Report images that are missing a HD version.", false);
		JCheckBox csd = new JCheckBox("Report images that are missing a SD version.", true);
		JCheckBox call = new JCheckBox("Report all files (show files that aren't missing in the skin).", false);
		JCheckBox clegacy = new JCheckBox("Report missing legacy files.", false);
		JCheckBox cempty = new JCheckBox("Ignore a missing HD image if an 'empty' SD image exists.", true);
		JCheckBox cisd = new JCheckBox("Ignore a missing SD image if a HD image exists.", false);
		controls.add(chd);
		controls.add(csd);
		controls.add(call);
		controls.add(clegacy);
		controls.add(cempty);
		controls.add(cisd);
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
		cempty.addActionListener((e)->{
			ignoreEmpty = cempty.isSelected();
			for(Model m : listeners){
				m.updateView();
			}
		});
		cisd.addActionListener((e)->{
			ignoreSD = cisd.isSelected();
			for(Model m : listeners){
				m.updateView();
			}
		});
		
		JPanel buttons = new JPanel(new GridLayout(4, 1));
		JButton openSkin = new JButton("Open skin");
		JButton openFolder = new JButton("Open skin folder for the selected skin");
		JButton recheck = new JButton("Recheck skin");
		JButton print = new JButton("Write list of missing files to file");
		buttons.add(openSkin);
		buttons.add(openFolder);
		buttons.add(recheck);
		buttons.add(print);
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
		print.addActionListener((e)->{
			if(skinFolder != null){
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				if(chooser.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION){
					return;
				}
				File dest = chooser.getSelectedFile();
				try {
					final PrintWriter writer = new PrintWriter(new FileOutputStream(dest));
					writer.println("========== Images ==========");
					for(Entry<String, Map<String, List<Info>>> m : imagesMap.entrySet()){
						for(Entry<String, List<Info>> ml : m.getValue().entrySet()){
							for(Info mli : ml.getValue()){
								if(mli.show()){
									writer.println('[' + m.getKey() + "|" + ml.getKey() + "]: " + ((ImageInfo)mli).name);
								}
							}
						}
					}
					writer.println();
					writer.println("========== Sounds ==========");
					for(Entry<String, Map<String, List<Info>>> m : soundsMap.entrySet()){
						for(Entry<String, List<Info>> ml : m.getValue().entrySet()){
							for(Info mli : ml.getValue()){
								if(mli.show()){
									writer.println('[' + m.getKey() + "|" + ml.getKey() + "]: " + ((SoundInfo)mli).name);
								}
							}
						}
					}
					writer.flush();
					writer.close();
					JOptionPane.showMessageDialog(frame, "File list succesfully exported", "Skin Checker", JOptionPane.INFORMATION_MESSAGE);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(frame, "An error occured: " + e1.getMessage(), "Skin Checker", JOptionPane.ERROR_MESSAGE);
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
		JLabel forumPost = new JLabel("<html>Forum post: <font color=blue><u><i>https://osu.ppy.sh/community/forums/topics/617168</i></u></font></html>");
		JLabel sheet = new JLabel("<html>Spreadsheet with information on each file: <font color=blue><u><i>https://docs.google.com/spreadsheets/d/1bhnV-CQRMy3Z0npQd9XSoTdkYxz0ew5e648S00qkJZ8/edit</font></i></u></html>");
		links.add(forumPost);
		links.add(sheet);
		forumPost.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(Desktop.isDesktopSupported()){
					try {
						Desktop.getDesktop().browse(new URL("https://osu.ppy.sh/community/forums/topics/617168").toURI());
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
		version.add(new JLabel("Running version: v1.6"));//XXX version
		JLabel latest = new JLabel("Latest version: loading...");
		new Thread(()->{
			String ver = checkVersion();
			latest.setText("Lastest version: " + (ver == null ? "Unknown" : ver));
		}).start();
		version.add(latest);
		version.setBorder(BorderFactory.createTitledBorder("Version"));
		lower.add(version, BorderLayout.LINE_END);
		content.add(lower, BorderLayout.PAGE_END);
		
		frame.add(content);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * Check the given skin folder and
	 * displays the results in the GUI
	 * @param folder The skin folder to check
	 * @throws IOException When an IOException occurs
	 */
	public static void checkSkin(File folder) throws IOException{
		if(folder == null){
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
		
		SkinIni skinIni = new SkinIni();
		skinIni.readIni(new File(skinFolder, "skin.ini"));
		iniTab.init(skinIni);
		
		allFiles.clear();
		addAllFiles(skinFolder);
		allFiles.remove(new File(skinFolder, "skin.ini"));
		
		parseINI();
		
		for(Info i : info){
			i.reset();
		}
		
		mapToTabs(imageTabs, imagesMap);
		mapToTabs(soundTabs, soundsMap);
		
		foreignFiles.clear();
		int offset = 1 + skinFolder.toString().length();
		for(File file : allFiles){
			foreignFiles.addElement(file.toString().substring(offset));
		}
	}
	
	private static void addAllFiles(File dir){
		for(File f : dir.listFiles()){
			if(f.isDirectory()){
				addAllFiles(f);
			}else{
				allFiles.add(f);
			}
		}
	}
	
	/**
	 * This subroutine read the skin.ini file and maps
	 * custom file paths to a map with a specific ID
	 * @throws IOException When an IOException occurs
	 */
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
							customPathing.put(keys * 100 + 22 + 10000 * Integer.parseInt(args[0].substring(args[0].length() - 2, args[0].length() - 1)), new File(skinFolder, args[1].trim()));
						}else{
							customPathing.put(keys * 100 + 21 + 10000 * Integer.parseInt(args[0].substring(args[0].length() - 1)), new File(skinFolder, args[1].trim()));
						}
					}else if(args[0].startsWith("NoteImage")){
						if(args[0].endsWith("H")){
							customPathing.put(keys * 100 + 32 + 10000 * Integer.parseInt(args[0].substring(args[0].length() - 2, args[0].length() - 1)), new File(skinFolder, args[1].trim()));
						}else if(args[0].endsWith("T")){
							customPathing.put(keys * 100 + 33 + 10000 * Integer.parseInt(args[0].substring(args[0].length() - 2, args[0].length() - 1)), new File(skinFolder, args[1].trim()));
						}else if(args[0].endsWith("L")){
							customPathing.put(keys * 100 + 34 + 10000 * Integer.parseInt(args[0].substring(args[0].length() - 2, args[0].length() - 1)), new File(skinFolder, args[1].trim()));
						}else{
							customPathing.put(keys * 100 + 31 + 10000 * Integer.parseInt(args[0].substring(args[0].length() - 1)), new File(skinFolder, args[1].trim()));
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

	/**
	 * Converts the given map of information
	 * objects to a set of tabbedpanes for
	 * the GUI
	 * @param tabs The tabbed pane to map the data to
	 * @param map The data to map to the tabs
	 */
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
	
	/**
	 * Creates a JTable with the correct model
	 * to display the given list of information
	 * objects and registers listeners.
	 * @param info The table data
	 * @return The newly created JTable
	 */
	private static JTable getTableData(final List<Info> info){
		JTable table = new JTable();
		Model model = info.get(0) instanceof ImageInfo ? new ImageModel(info) : (info.get(0) instanceof SoundInfo ? new SoundModel(info) : null);
		listeners.add(model);
		table.setModel(model);
		return table;
	}

	/**
	 * Reads all the data from the data files to layered maps
	 * @throws IOException When an IO Exception occurs
	 */
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
		readDataFile("mania-extra.txt", false);
	}

	/**
	 * Read the internal database to create a layered
	 * mapping of all the files in a given database file
	 * @param name The name of the database file to read
	 * @param isSound Whether or not the given database file
	 *        contains sound entries. (this is used to distinguish
	 *        between image and sound files)
	 * @return A layered map of all the file descriptors
	 * @throws IOException When an IOException occurs
	 */
	private static Map<String, List<Info>> readDataFile(String name, boolean isSound) throws IOException{
		Map<String, List<Info>> data = new HashMap<String, List<Info>>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(name)));
		List<Info> writing = null;
		String line;
		while((line = reader.readLine()) != null){
			if(line.trim().isEmpty()){
				continue;
			}else if(line.startsWith("===>")){
				if(writing != null){
					info.addAll(writing);
				}
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
		info.addAll(writing);
		reader.close();
		return data;
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
			con.addRequestProperty("Accept", "application/vnd.github.v3+json");
			con.setConnectTimeout(10000); 					   
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream())); 	
			String line = reader.readLine(); 		
			reader.close(); 	
			String[] versions = line.split("\"name\":\"v");
			int max_main = 0;
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
}

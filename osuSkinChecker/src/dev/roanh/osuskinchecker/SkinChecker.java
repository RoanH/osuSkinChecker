package dev.roanh.osuskinchecker;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;

import dev.roanh.osuskinchecker.ini.SkinIni;
import dev.roanh.osuskinchecker.ini.SkinIniTab;
import dev.roanh.osuskinchecker.ini.SplitLayout;
import dev.roanh.osuskinchecker.ini.Version;
import dev.roanh.util.ClickableLink;
import dev.roanh.util.Dialog;
import dev.roanh.util.ExclamationMarkPath;
import dev.roanh.util.FileSelector;
import dev.roanh.util.FileSelector.FileExtension;
import dev.roanh.util.Util;

/**
 * This program can be used to see what
 * elements a skin skins and to see what
 * elements are still missing from a skin.
 * It can also be used to find missing HD
 * images or vice versa.
 * @author Roan
 */
public class SkinChecker{
	/**
	 * File extension that matches <code>.txt</code> files.
	 */
	private static final FileExtension txtExtension = FileSelector.registerFileExtension("Text file", "txt");
	/**
	 * Layered map with the information
	 * about all the images.
	 */
	private static final Map<String, Map<String, List<Filter<?>>>> imagesMap = new HashMap<String, Map<String, List<Filter<?>>>>();
	/**
	 * Layered map with the information
	 * about all the sound files.
	 */
	private static final Map<String, Map<String, List<Filter<?>>>> soundsMap = new HashMap<String, Map<String, List<Filter<?>>>>();
	/**
	 * List of all loaded filters.
	 */
	private static final List<Filter<?>> filters = new ArrayList<Filter<?>>();
	/**
	 * Folder of the skin currently being checked.
	 */
	private static Path skinFolder;
	/**
	 * Whether or not to check for missing SD images.
	 */
	protected static boolean checkSD = true;
	/**
	 * Whether or not to check for missing HD images.
	 */
	protected static boolean checkHD = false;
	/**
	 * Whether or not to check for missing legacy images.
	 */
	protected static boolean checkLegacy = false;
	/**
	 * Whether or not to show all files regardless of whether
	 * they are present or not.
	 */
	protected static boolean showAll = false;
	/**
	 * Whether or not to ignore missing HD files of
	 * images that have an empty SD image.
	 */
	protected static boolean ignoreEmpty = true;
	/**
	 * List of all the tables model that is used to
	 * update the tables when the filter changes.
	 */
	private static List<Model> listeners = new ArrayList<Model>();
	/**
	 * Main frame.
	 */
	private static final JFrame frame = new JFrame("Skin Checker for osu!");
	/**
	 * The JLabel that displays the name of the skin
	 * currently being checked.
	 */
	private static JLabel skin;
	/**
	 * The JTabbedPane that lists all the images.
	 */
	private static JTabbedPane imageTabs;
	/**
	 * The JTabbedPane that lists all the sound files.
	 */
	private static JTabbedPane soundTabs;
	/**
	 * Model for the list that displays files
	 * that should not be in the skin.
	 */
	private static DefaultListModel<String> foreignFiles = new DefaultListModel<String>();
	/**
	 * Whether or not to ignore a missing
	 * SD image when a HD version exists.
	 */
	protected static boolean ignoreSD = false;
	/**
	 * The tab showing all the skin.ini options and editors.
	 */
	private static SkinIniTab iniTab;
	/**
	 * The skin.ini settings for the skin currently loaded.
	 */
	protected static SkinIni skinIni = null;
	/**
	 * Version set in the currently loaded <tt>skin.ini</tt>.
	 */
	protected static Version version = null;

	/**
	 * Main method
	 * @param args No valid command line options
	 */
	public static void main(String[] args){
		ExclamationMarkPath.check(args);
		Util.installUI();

		try{
			readDatabase();
			for(Filter<?> filter : filters){
				filter.link(filters);
			}
		}catch(IOException e){
			e.printStackTrace();
		}

		imageTabs = new JTabbedPane();
		mapToTabs(imageTabs, imagesMap);
		imageTabs.insertTab("All", null, buildAllTab(imagesMap), null, 0);
		imageTabs.setSelectedIndex(0);

		soundTabs = new JTabbedPane();
		mapToTabs(soundTabs, soundsMap);
		soundTabs.insertTab("All", null, new JScrollPane(getTableData(soundsMap.values().stream().flatMap(m->m.values().stream()).flatMap(List::stream).collect(Collectors.toList()))), null, 0);
		soundTabs.setSelectedIndex(0);

		skin = new JLabel("<html><i>no skin selected</i></html>");
		buildGUI();
	}

	/**
	 * Builds the GUI
	 */
	public static void buildGUI(){
		try{
			Image icon = ImageIO.read(ClassLoader.getSystemResource("skinchecker.png"));
			frame.setIconImage(icon);
			Dialog.setDialogIcon(icon);
		}catch(IOException e2){
		}
		Dialog.setDialogTitle("Skin Checker");
		Dialog.setParentFrame(frame);
		JPanel content = new JPanel(new BorderLayout());
		JTabbedPane categories = new JTabbedPane();

		JPanel foreign = new JPanel(new BorderLayout());
		foreign.add(new JScrollPane(new JList<String>(foreignFiles)), BorderLayout.CENTER);
		foreign.add(new JLabel("This is a list of files that are in the skin folder but serve no purpose."), BorderLayout.PAGE_START);

		JPanel ini = new JPanel(new BorderLayout());
		ini.add(new JLabel("Settings with an additional leading checkbox require you to check this check box if you want to use the setting. Otherwise the setting is left as 'undefined'."), BorderLayout.PAGE_START);
		ini.add(iniTab = new SkinIniTab(), BorderLayout.CENTER);
		JPanel saveButtons = new JPanel(new GridLayout(1, 2));
		JButton save = new JButton("Save skin.ini");
		ActionListener defaultSave = (e)->{
			if(skinIni != null){
				try{
					skinIni.writeIni(skinIni.ini);
				}catch(IOException e1){
					Dialog.showErrorDialog("An error occurred while writing the new skin.ini!");
				}
				Dialog.showMessageDialog("Succesfully saved the skin.ini file.");
			}
		};
		save.addActionListener(defaultSave);
		JButton saveBack = new JButton("Save skin.ini and backup the current version");
		saveBack.addActionListener((e)->{
			if(skinIni != null){
				try{
					Files.move(skinIni.ini, skinIni.ini.getParent().resolve("backup-" + getDateTime() + ".ini"), StandardCopyOption.REPLACE_EXISTING);
				}catch(IOException e2){
					Dialog.showErrorDialog("Failed to create a backup!");
					return;
				}
				defaultSave.actionPerformed(e);
			}
		});
		saveButtons.add(save);
		saveButtons.add(saveBack);
		ini.add(saveButtons, BorderLayout.PAGE_END);

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
			updateView();
		});
		csd.addActionListener((e)->{
			checkSD = csd.isSelected();
			updateView();
		});
		call.addActionListener((e)->{
			showAll = call.isSelected();
			updateView();
		});
		clegacy.addActionListener((e)->{
			checkLegacy = clegacy.isSelected();
			updateView();
		});
		cempty.addActionListener((e)->{
			ignoreEmpty = cempty.isSelected();
			updateView();
		});
		cisd.addActionListener((e)->{
			ignoreSD = cisd.isSelected();
			updateView();
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
			try{
				checkSkin(null);
			}catch(IOException e1){
				e1.printStackTrace();
			}
		});
		openFolder.addActionListener((e)->{
			if(skinFolder != null){
				try{
					Desktop.getDesktop().open(skinFolder.toFile());
				}catch(IOException e1){
					e1.printStackTrace();
				}
			}else{
				Dialog.showErrorDialog("No skin currently selected!");
			}
		});
		recheck.addActionListener((e)->{
			if(skinFolder != null){
				try{
					checkSkin(skinFolder);
				}catch(IOException e1){
					e1.printStackTrace();
				}
			}else{
				Dialog.showErrorDialog("No skin currently selected!");
			}
		});
		print.addActionListener((e)->{
			if(skinFolder != null){
				Path dest = Dialog.showFileSaveDialog(txtExtension, "foreign files");
				if(dest == null){
					return;
				}
				
				try{
					final PrintWriter writer = new PrintWriter(Files.newOutputStream(dest, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE, StandardOpenOption.CREATE));
					writer.println("========== Images ==========");
					for(Entry<String, Map<String, List<Filter<?>>>> m : imagesMap.entrySet()){
						for(Entry<String, List<Filter<?>>> ml : m.getValue().entrySet()){
							for(Filter<?> mli : ml.getValue()){
								if(mli.show(version)){
									writer.println('[' + m.getKey() + "|" + ml.getKey() + "]: " + mli.toString());
								}
							}
						}
					}
					writer.println();
					writer.println("========== Sounds ==========");
					for(Entry<String, Map<String, List<Filter<?>>>> m : soundsMap.entrySet()){
						for(Entry<String, List<Filter<?>>> ml : m.getValue().entrySet()){
							for(Filter<?> mli : ml.getValue()){
								if(mli.show(version)){
									writer.println('[' + m.getKey() + "|" + ml.getKey() + "]: " + mli.toString());
								}
							}
						}
					}
					writer.flush();
					writer.close();
					Dialog.showMessageDialog("File list succesfully exported");
				}catch(IOException e1){
					Dialog.showErrorDialog("An error occured: " + e1.getMessage());
				}

			}else{
				Dialog.showErrorDialog("No skin currently selected!");
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

		JPanel links = new JPanel(new GridLayout(3, 1));
		links.setBorder(BorderFactory.createTitledBorder("Links"));
		JLabel sheet = new JLabel("<html>Spreadsheet&nbsp;with&nbsp;information&nbsp;on&nbsp;each&nbsp;file:&nbsp;<font color=blue><u>https://docs.google.com/spreadsheets/d/1bhnV-CQRMy3Z0npQd9XSoTdkYxz0ew5e648S00qkJZ8</font></u></html>");
		JLabel wiki = new JLabel("<html>osu!&nbsp;wiki&nbsp;page&nbsp;on&nbsp;the&nbsp;skin.ini:&nbsp;<font color=blue><u>https://osu.ppy.sh/help/wiki/Skinning/skin.ini</font></u></html>");
		JLabel tutorial = new JLabel("<html>Skinning&nbsp;tutorial:&nbsp;<font color=blue><u>https://skinship.xyz/tutorial/introduction</font></u></html>");
		links.add(sheet);
		links.add(wiki);
		links.add(tutorial);
		
		JPanel info = new JPanel(new GridLayout(2, 1));
		info.add(Util.getVersionLabel("osuSkinChecker", "v3.4"));//XXX the version number - don't forget build.gradle
		JPanel linksProgram = new JPanel(new GridLayout(1, 2, -2, 0));
		JLabel forum = new JLabel("<html><font color=blue><u>Forums</u></font> -</html>", SwingConstants.RIGHT);
		JLabel git = new JLabel("<html>- <font color=blue><u>GitHub</u></font></html>", SwingConstants.LEFT);
		//JLabel 
		linksProgram.add(forum);
		linksProgram.add(git);
		
		wiki.addMouseListener(new ClickableLink("https://osu.ppy.sh/help/wiki/Skinning/skin.ini"));
		sheet.addMouseListener(new ClickableLink("https://docs.google.com/spreadsheets/d/1bhnV-CQRMy3Z0npQd9XSoTdkYxz0ew5e648S00qkJZ8/edit"));
		forum.addMouseListener(new ClickableLink("https://osu.ppy.sh/community/forums/topics/617168"));
		git.addMouseListener(new ClickableLink("https://github.com/RoanH/osuSkinChecker"));
		tutorial.addMouseListener(new ClickableLink("https://skinship.xyz/tutorial/introduction"));
		
		JPanel right = new JPanel(new GridLayout(2, 1));
		right.setBorder(BorderFactory.createTitledBorder("Information"));
		right.add(linksProgram);
		right.add(info);
		
		JPanel lower = new JPanel(new SplitLayout());
		lower.add(links);
		lower.add(right);
		content.add(lower, BorderLayout.PAGE_END);

		frame.add(content);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		new DropTarget(frame, new DropTargetListener(){

			@Override
			public void dragEnter(DropTargetDragEvent dtde){				
			}

			@Override
			public void dragOver(DropTargetDragEvent dtde){
			}

			@Override
			public void dropActionChanged(DropTargetDragEvent dtde){
			}

			@Override
			public void dragExit(DropTargetEvent dte){
			}

			@Override
			public void drop(DropTargetDropEvent dtde){
				if(dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
					try{
						dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
						@SuppressWarnings("unchecked")
						List<File> files = (List<File>)dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
						if(files.size() > 0 && files.get(0).isDirectory()){
							checkSkin(files.get(0).toPath());
						}
					}catch(UnsupportedFlavorException | IOException e){
						//Pity, but not important
					}
				}
			}
		});
	}
	
	/**
	 * Updates the information displayed in the tabs
	 * based on the new settings.
	 */
	private static void updateView(){
		if(version != null){
			for(Model m : listeners){
				m.updateView(version);
			}
		}
	}

	/**
	 * Check the given skin folder and
	 * displays the results in the GUI
	 * @param folder The skin folder to check
	 * @throws IOException When an IOException occurs
	 */
	public static void checkSkin(Path folder) throws IOException{
		if(folder == null){
			Path selected = Dialog.showFolderOpenDialog();
			if(selected == null || Files.notExists(selected)){
				Dialog.showErrorDialog("No skin selected!");
				return;
			}else{
				folder = selected;
			}
		}

		Path iniFile = folder.resolve("skin.ini");
		
		if(Files.notExists(iniFile)){
			int option = Dialog.showDialog("This folder doesn't have a skin.ini file.\nWithout this file this skin won't even be recognized as a skin!", new String[]{"OK", "Add empty skin.ini"});
			if(option == 1){
				Files.createFile(iniFile);
			}else{
				return;
			}
		}
		
		skinFolder = folder;
		skin.setText(skinFolder.getFileName().toString());

		executeChecks(skinFolder, iniFile);
	}
	
	/**
	 * Checks the skin denoted by the given folder and ini file.
	 * @param skinFolder The skin folder.
	 * @param ini The <tt>skin.ini</tt> file.
	 */
	private static void executeChecks(Path skinFolder, Path ini){
		skinIni = new SkinIni();
		try{
			skinIni.readIni(ini);
		}catch(Throwable e){
			try{
				Path err = Paths.get("error-" + getDateTime() + ".txt");
				List<String> errl = new ArrayList<String>(30);
				errl.add(e.toString());
				for(StackTraceElement elem : e.getStackTrace()){
					errl.add("	" + elem.toString());
				}
				Files.write(err, errl, StandardOpenOption.CREATE_NEW);
				Dialog.showErrorDialog("An error occurred while reading the skin.ini\nThe error was saved to: " + err.toAbsolutePath().toString() + "\n" + e.getMessage());
			}catch(Exception e1){
				Dialog.showErrorDialog("An internal error occurred!");
			}
			return;
		}
		iniTab.init(skinIni);
		version = (Version)skinIni.find("Version", -1).getValue();

		for(Filter<?> filter : filters){
			filter.reset(skinIni);
		}

		Deque<String> path = new ArrayDeque<String>();
		List<Path> foreign = new ArrayList<Path>();
		try{
			checkAllFiles(skinFolder, path, foreign);
		}catch(IOException e){
			Dialog.showErrorDialog("An internal error occurred parsing the given skin!\nCause: " + e.getMessage());
			return;
		}
		
		for(Model m : listeners){
			m.updateView(version);
		}
		
		foreignFiles.clear();
		int offset = 1 + skinFolder.toString().length();
		for(Path file : foreign){
			if(!file.equals(ini)){
				foreignFiles.addElement(file.toString().substring(offset));
			}
		}
		for(Filter<?> filter : filters){
			if(filter instanceof ImageFilter){
				if(((ImageFilter)filter).isLegacy(version)){
					for(Path file : filter.getMatchedFiles()){
						foreignFiles.addElement(file.toString().substring(offset));
					}
				}
			}
		}
	}
	
	/**
	 * Checks all the files in the given directory again the filters.
	 * @param dir The directory the parse.
	 * @param path The path stack.
	 * @param foreign A list of files that did not match any filter.
	 * @throws IOException When an IO exception occurs.
	 */
	private static void checkAllFiles(Path dir, Deque<String> path, List<Path> foreign) throws IOException{
		try(DirectoryStream<Path> files = Files.newDirectoryStream(dir)){
			for(Path f : files){
				if(Files.isDirectory(f)){
					path.push(f.getFileName().toString());
					checkAllFiles(f, path, foreign);
					path.pop();
				}else if(Files.isRegularFile(f)){
					//if none then foreign for sure
					//no short circuiting because numbers can count for two filters, score and combo
					boolean found = false;
					for(Filter<?> filter : filters){
						found |= filter.check(f, path);
					}
					if(!found){
						foreign.add(f);
					}
				}
			}
		}
	}
	
	/**
	 * Converts the given map of information
	 * objects to a set of tabbedpanes for
	 * the GUI
	 * @param tabs The tabbed pane to map the data to
	 * @param map The data to map to the tabs
	 */
	private static void mapToTabs(JTabbedPane tabs, Map<String, Map<String, List<Filter<?>>>> map){
		tabs.removeAll();
		List<Filter<?>> all = new ArrayList<Filter<?>>(filters.size());
		for(Entry<String, Map<String, List<Filter<?>>>> entry : map.entrySet()){
			JTabbedPane inner = new JTabbedPane();
			if(entry.getKey().equals("Mania")){
				//The mania tab is special and splits the sub items with an extra layer of tabs
				Map<String, JTabbedPane> keys = new HashMap<String, JTabbedPane>();
				for(int i = 1; i <= 18; i++){
					if(i < 10 || (i >= 10 && i % 2 == 0)){
						JTabbedPane pane = new JTabbedPane();
						inner.addTab(i + "K", pane);
						keys.put(i + "K", pane);
					}
				}
				
				for(Entry<String, List<Filter<?>>> e : entry.getValue().entrySet()){
					String[] args = e.getKey().split(" - ");
					keys.get(args[0]).add(args[1], new JScrollPane(getTableData(e.getValue())));
				}
			}else{
				for(Entry<String, List<Filter<?>>> e : entry.getValue().entrySet()){
					all.addAll(e.getValue());
					inner.add(e.getKey(), new JScrollPane(getTableData(e.getValue())));
				}
			}
			tabs.add(entry.getKey(), inner);
		}
	}
	
	/**
	 * Builds the images tab 'All' tab with gamemode filters.
	 * @param map The image filter data.
	 * @return The constructed 'All' tab.
	 */
	private static JPanel buildAllTab(Map<String, Map<String, List<Filter<?>>>> map){
		JPanel content = new JPanel(new BorderLayout());
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		JCheckBox standard = new JCheckBox("Standard", true);
		JCheckBox ctb = new JCheckBox("Catch", true);
		JCheckBox mania = new JCheckBox("Mania", true);
		JCheckBox taiko = new JCheckBox("Taiko", true);
		
		buttons.add(new JLabel(" Included gamemodes "));
		buttons.add(new JSeparator(JSeparator.VERTICAL));
		buttons.add(standard);
		buttons.add(new JSeparator(JSeparator.VERTICAL));
		buttons.add(ctb);
		buttons.add(new JSeparator(JSeparator.VERTICAL));
		buttons.add(mania);
		buttons.add(new JSeparator(JSeparator.VERTICAL));
		buttons.add(taiko);
		buttons.add(new JPanel(new BorderLayout()));
		
		JTable table = getTableData(map.values().stream().flatMap(m->m.values().stream()).flatMap(List::stream).collect(Collectors.toList()));
		Model model = (Model)table.getModel();
		TableRowSorter<Model> sorter = new TableRowSorter<Model>(model);
		sorter.setRowFilter(new RowFilter<Model, Integer>(){
			@Override
			public boolean include(Entry<? extends Model, ? extends Integer> entry){
				Filter<?> filter = model.get(entry.getIdentifier());
				
				if(!standard.isSelected() && imagesMap.get("osu!").values().stream().flatMap(List::stream).anyMatch(filter::equals)){
					return false;
				}
				
				if(!ctb.isSelected() && imagesMap.get("Catch").values().stream().flatMap(List::stream).anyMatch(filter::equals)){
					return false;
				}
				
				if(!taiko.isSelected() && imagesMap.get("Taiko").values().stream().flatMap(List::stream).anyMatch(filter::equals)){
					return false;
				}
				
				if(!mania.isSelected() && imagesMap.get("Mania").values().stream().flatMap(List::stream).anyMatch(filter::equals)){
					return false;
				}
				
				return true;
			}
		});
		table.setRowSorter(sorter);
		
		standard.addActionListener(e->model.fireTableDataChanged());
		ctb.addActionListener(e->model.fireTableDataChanged());
		taiko.addActionListener(e->model.fireTableDataChanged());
		mania.addActionListener(e->model.fireTableDataChanged());
		
		content.add(buttons, BorderLayout.PAGE_START);
		content.add(new JScrollPane(table), BorderLayout.CENTER);
		
		return content;
	}

	/**
	 * Creates a JTable with the correct model
	 * to display the given list of information
	 * objects and registers listeners.
	 * @param info The table data
	 * @return The newly created JTable
	 */
	private static JTable getTableData(final List<Filter<?>> info){
		JTable table = new JTable();
		Model model = info.get(0) instanceof ImageFilter ? new ImageModel(info) : new SoundModel(info);
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
	private static Map<String, List<Filter<?>>> readDataFile(String name, boolean isSound) throws IOException{
		Map<String, List<Filter<?>>> data = new LinkedHashMap<String, List<Filter<?>>>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(name)));
		List<Filter<?>> writing = null;
		String line;
		while((line = reader.readLine()) != null){
			if(line.trim().isEmpty()){
				continue;
			}else if(line.startsWith("===>")){
				if(writing != null){
					filters.addAll(writing);
				}
				writing = new ArrayList<Filter<?>>();
				data.put(line.substring(4).trim(), writing);
			}else{
				String[] args = line.split(" +");
				writing.add(isSound ?  new SoundFilter(args) : new ImageFilter(args));
			}
		}
		filters.addAll(writing);
		reader.close();
		return data;
	}
	
	/**
	 * Gets the current time and date as a string
	 * in the <code>yyyy-MM-dd_HH.mm.ss</code> format
	 * @return The current time and date
	 */
	private static final String getDateTime(){
		return DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss").withZone(ZoneId.systemDefault()).format(Instant.now(Clock.systemDefaultZone()));
	}
}

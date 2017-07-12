package me.roan.osuskinchecker;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class SkinChecker {

	//https://puu.sh/woI9Q/03bc8befea.txt
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
	private static File skinFolder;
	private static boolean checkSD = true;
	private static boolean checkHD = false;
	private static boolean checkLegacy = false;
	private static boolean showAll = false;
	private static Map<Integer, File> customPathing = new HashMap<Integer, File>();

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
		try {
			checkSkin(new File("C:\\Users\\RoanH\\Documents\\osu!\\Skins\\Roan v4.0"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void checkSkin(File skinDir) throws IOException{
		//TODO check missing .ini
		skinFolder = skinDir;
			
		parseINI();
		
		buildGUI();
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
		JFrame frame = new JFrame("Skin Checker for osu!");
		JPanel content = new JPanel(new BorderLayout());
		JTabbedPane categories = new JTabbedPane();
				
		categories.add("Images", mapToTabs(imagesMap));
		categories.add("Sounds", mapToTabs(soundsMap));
		
		categories.setBorder(BorderFactory.createTitledBorder("Files"));
		content.add(categories);
		
		JPanel controls = new JPanel(new GridLayout(4, 1, 0, 0));
		JCheckBox chd = new JCheckBox("Report files that are missing a HD version (only applies to images).", false);
		JCheckBox csd = new JCheckBox("Report files that are missing a SD version (only applies to images).", true);
		JCheckBox call = new JCheckBox("Report all files (show files that aren't missing in the skin).", false);
		JCheckBox clegacy = new JCheckBox("Report missing legacy files.", false);
		controls.add(chd);
		controls.add(csd);
		controls.add(call);
		controls.add(clegacy);
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

		controls.setBorder(BorderFactory.createTitledBorder("Filter"));
		content.add(controls, BorderLayout.PAGE_START);
		
		frame.add(content);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private static JTabbedPane mapToTabs(Map<String, Map<String, List<Info>>> map){
		JTabbedPane tabs = new JTabbedPane();
		tabs.removeAll();
		for(Entry<String, Map<String, List<Info>>> entry : map.entrySet()){
			JTabbedPane inner = new JTabbedPane();
			for(Entry<String, List<Info>> e : entry.getValue().entrySet()){
				inner.add(e.getKey(), new JScrollPane(getTableData(e.getValue())));
			}
			tabs.add(entry.getKey(), inner);
		}
		return tabs;
	}
	
	private static List<Model> listeners = new ArrayList<Model>();
	
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
					writing.add(new SoundInfo().init(line));
				}else{
					writing.add(new ImageInfo().init(line));
				}
			}
		}
		return data;
	}
	
	private static class ImageModel extends Model{
		
		public ImageModel(List<Info> list) {
			super(list);
		}

		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public int getColumnCount(){
			return 3;
		}
		
		@Override
		public String getColumnName(int col){
			switch(col){
			case 0:
				return "Filename";
			case 1:
				return "SD version present";
			case 2:
				return "HD version present";
			}
			return null;
		}
		
		
		@Override
		public Object getValueAt(int row, int col){
			try{
				switch(col){
				case 0:
					return view.get(row);
				case 1: 
					return ((ImageInfo)view.get(row)).hasSDVersion();
				case 2: 
					return ((ImageInfo)view.get(row)).hasHDVersion();
				}
			}catch(Exception e){
				return "error";
			}
			return null;
		}	
	};
	
	private static class SoundModel extends Model{
		
		public SoundModel(List<Info> list) {
			super(list);
		}

		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 2L;
		
		@Override
		public int getColumnCount(){
			return 2;
		}
		
		@Override
		public String getColumnName(int col){
			switch(col){
			case 0:
				return "Filename";
			case 1:
				return "Exists";
			}
			return null;
		}
		
		@Override
		public Object getValueAt(int row, int col){
			try{
				switch(col){
				case 0:
					return view.get(row);
				case 1: 
					return ((SoundInfo)view.get(row)).exists();
				}
			}catch(Exception e){
				return "error";
			}
			return null;
		}	
	};
	
	private static abstract class Model extends DefaultTableModel{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 1L;
		protected List<Info> view = new ArrayList<Info>();
		private List<Info> data;
		
		public Model(List<Info> list){
			data = list;
			updateView();
		}
		
		protected void updateView(){
			view.clear();
			for(Info i : data){
				if(i.show()){
					view.add(i);
				}
			}
			this.fireTableDataChanged();
		}
		
		@Override
		public int getRowCount(){
			return view == null ? 0 : view.size();
		}	
		
		@Override
		public boolean isCellEditable(int row, int col){
			return false;
		}
	};

	private static abstract interface Info{

		public abstract Info init(String data);
		
		public abstract boolean show();
	}

	private static final class ImageInfo implements Info{
		boolean singleImage = false;
		boolean legacy = false;
		boolean variableWithDash = false;
		boolean variableWithoutDash = false;
		boolean customPath = false;
		boolean customPathPrefix = false;
		int customID = -1;
		Boolean hasSD = null;
		Boolean hasHD = null;

		String[] extensions;

		String name;

		@Override
		public String toString(){
			return name;
		}
		
		@Override
		public boolean show(){
			if(showAll){
				return !legacy || checkLegacy;
			}else{
				if(legacy && !checkLegacy){
					return false;
				}else{
					return (checkSD ? !hasSDVersion() : false) || (checkHD ? !hasHDVersion() : false);
				}
			}
		}
		
		private boolean hasSDVersion(){
			if(hasSD == null){
				for(String ext : extensions){
					if(checkForFile(skinFolder, name, false, ext, variableWithDash, variableWithoutDash, customPath, customPathPrefix)){
						hasSD = true;
					}
				}
				if(hasSD == null){
					hasSD = false;
				}
			}
			return hasSD;
		}
		
		private boolean hasHDVersion(){
			if(hasHD == null){
				if(singleImage){
					hasHD = true;
				}
				for(String ext : extensions){
					if(checkForFile(skinFolder, name, true, ext, variableWithDash, variableWithoutDash, customPath, customPathPrefix)){
						hasHD = true;
					}
				}
				if(hasHD == null){
					hasHD = false;
				}
			}
			return hasHD;
		}
		
		private boolean checkForFile(File folder, String name, boolean hd, final String extension, boolean variableDash, boolean variableNoDash, boolean custom, boolean customPrefix){
			boolean match = false;
			for(File file : folder.listFiles()){
				String fileName = file.getName().toLowerCase(Locale.ROOT);
				if(fileName.startsWith(name) && fileName.toLowerCase().endsWith((hd ? "@2x." : ".") + extension)){
					if(!hd && fileName.endsWith("@2x." + extension)){
						continue;
					}
					if(variableDash || variableNoDash){
						String n = fileName.substring(name.length());
						if(n.startsWith("-")){
							n = n.substring((variableDash ? 1 : 0));
						}
						n = n.substring(0, n.length() - (hd ? 4 : 1) - extension.length());
						if(n.isEmpty()){
							match = true;
							break;
						}
						boolean number = false;
						try{
							Integer.parseInt(n);
							number = true;
						}catch(NumberFormatException e){
						}
						match = number;
						if(match){
							break;
						}else{
							continue;
						}
					}else{
						match = true;
						break;
					}
				}else{
					continue;
				}
			}
			if(!match && (custom || customPrefix) && this.customID != -1){
				if(customPrefix){
					return checkForFile(customPathing.get(this.customID), name, hd, extension, variableDash, variableNoDash, false, false);
				}else{
					File f = customPathing.get(this.customID);
					return f == null ? false : checkForFile(f.getParentFile(), f.getName(), hd, extension, variableDash, variableNoDash, false, false);
				}
			}
			return match;
		}

		public ImageInfo init(String line){
			String[] data = line.split(" ");
			if(!data[0].equals("-")){
				char[] args = data[0].toUpperCase(Locale.ROOT).toCharArray();
				for(char option : args){
					switch(option){
					case 'N':
						variableWithDash = true;
						break;
					case 'M':
						variableWithoutDash = true;
						break;
					case 'S':
						singleImage = true;
						break;
					case 'C':
						customPath = true;
						customID = Integer.parseInt(data[1]);
						break;
					case 'P':
						customPath = true;
						customID = Integer.parseInt(data[1]);
						break;
					case 'L':
						legacy = true;
						break;
					}
				}
			}
			this.extensions = data[1 + (customPath ? 1 : 0)].split(",");
			this.name = data[2 + (customPath ? 1 : 0)];
			return this;
		}
	}
	
	private static final class SoundInfo implements Info{
		boolean variableWithDash = false;
		Boolean exists = null;

		String[] extensions;

		String name;

		@Override
		public String toString(){
			return name;
		}
		
		@Override
		public boolean show(){
			if(showAll){
				return true;
			}else{
				return !exists();
			}
		}
		
		private boolean exists(){
			if(exists == null){
				exists = false;
				for(String ext : extensions){
					if(checkForFile(skinFolder, name, ext, variableWithDash)){
						exists = true;
						break;
					}
				}
			}
			return exists;
		}
		
		private boolean checkForFile(File folder, String name, final String extension, boolean variableDash){
			boolean match = false;
			for(File file : folder.listFiles()){
				String fileName = file.getName().toLowerCase(Locale.ROOT);
				if(fileName.startsWith(name) && fileName.toLowerCase().endsWith("." + extension)){
					if(variableDash){
						String n = fileName.substring(name.length());
						if(n.startsWith("-")){
							n = n.substring((variableDash ? 1 : 0));
						}
						n = n.substring(0, n.length() - 1 - extension.length());
						if(n.isEmpty()){
							match = true;
							break;
						}
						boolean number = false;
						try{
							Integer.parseInt(n);
							number = true;
						}catch(NumberFormatException e){
						}
						match = number;
						if(match){
							break;
						}else{
							continue;
						}
					}else{
						match = true;
						break;
					}
				}else{
					continue;
				}
			}
			return match;
		}

		public SoundInfo init(String line){
			String[] data = line.split(" ");
			if(!data[0].equals("-")){
				char[] args = data[0].toUpperCase(Locale.ROOT).toCharArray();
				for(char option : args){
					switch(option){
					case 'N':
						variableWithDash = true;
						break;
					}
				}
			}
			this.extensions = data[1].split(",");
			this.name = data[2];
			return this;
		}
	}
}

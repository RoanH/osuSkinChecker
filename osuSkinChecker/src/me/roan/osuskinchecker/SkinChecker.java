package me.roan.osuskinchecker;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SkinChecker {

	//https://puu.sh/woI9Q/03bc8befea.txt
	//https://docs.google.com/spreadsheets/d/1bhnV-CQRMy3Z0npQd9XSoTdkYxz0ew5e648S00qkJZ8/edit#gid=2074725196

	//options:
	//N=variable amount
	//M=variable amount not preceded by a -
	//S=single image only no higher/lower res
	//C=custom path
	//L=legacy
	//-= n/a

	private static final Map<String, Map<String, List<Info>>> imagesMap = new HashMap<String, Map<String, List<Info>>>();
	private static File skinFolder;
	private static boolean checkSD = true;
	private static boolean checkHD = false;
	private static boolean checkLegacy = false;
	private static boolean showAll = false;

	public static void main(String[] args){

		checkSkin(skinFolder = new File("C:\\Users\\RoanH\\Documents\\osu!\\Skins\\Roan v4.0"));
	}

	public static void checkSkin(File skinDir){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable t) {
		}

		//for(File f : skinDir.listFiles()){
		//	System.out.println(f.getName());
		//}
		try {
			readDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buildGUI();


	}

	public static void buildGUI(){
		JFrame frame = new JFrame("Skin Checker for osu!");
		JPanel content = new JPanel(new BorderLayout());
		JTabbedPane categories = new JTabbedPane();
		categories.add("Images", mapToTabs(imagesMap));


		content.add(categories);

		frame.add(content);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static boolean checkForFile(String name, String extension){
		return new File(skinFolder, name + '.' + extension).exists();
	}

	private static JTabbedPane mapToTabs(Map<String, Map<String, List<Info>>> map){
		JTabbedPane tabs = new JTabbedPane();
		for(Entry<String, Map<String, List<Info>>> entry : map.entrySet()){
			JTabbedPane inner = new JTabbedPane();
			for(Entry<String, List<Info>> e : entry.getValue().entrySet()){
				e.getValue().removeIf((item)->{
					return !item.show();
				});
				if(!e.getValue().isEmpty()){
					inner.add(e.getKey(), new JScrollPane(getTableData(e.getValue())));
				}
			}
			tabs.add(entry.getKey(), inner);
		}
		return tabs;
	}
	
	private static JTable getTableData(List<Info> info){
		JTable table = new JTable();
		TableModel model = new DefaultTableModel(){
			/**
			 * Serial ID
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public int getRowCount(){
				return info.size();
			}
			
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
			public boolean isCellEditable(int row, int col){
				return false;
			}
			
			@Override
			public Object getValueAt(int row, int col){
				try{
					switch(col){
					case 0:
						return info.get(row);
					case 1: 
						return ((ImageInfo)info.get(row)).hasSDVersion();
					case 2: 
						return ((ImageInfo)info.get(row)).hasHDVersion();
					}
				}catch(Exception e){
					return "error";
				}
				return null;
			}	
		};
		table.setModel(model);
		
		return table;
	}

	private static void readDatabase() throws IOException{
		imagesMap.put("Menu", readDataFile("menu.txt"));
		imagesMap.put("osu!", readDataFile("osu.txt"));
		imagesMap.put("Taiko", readDataFile("taiko.txt"));
		imagesMap.put("Mania", readDataFile("mania.txt"));
		imagesMap.put("Catch", readDataFile("catch.txt"));
		imagesMap.put("Miscellaneous", readDataFile("misc.txt"));
		imagesMap.put("Gameplay", readDataFile("gameplay.txt"));
	}

	private static Map<String, List<Info>> readDataFile(String name) throws IOException{
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
				writing.add(new ImageInfo().init(line));
			}
		}
		return data;
	}

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
				if(legacy && !checkLegacy){
					return false;
				}else{
					return (checkSD ? !hasSDVersion() : false) || (checkHD ? !hasHDVersion() : false);
				}
			}
		}
		
		private boolean hasSDVersion(){//TODO test variable and path
			for(String ext : extensions){
				if(checkForFile(name, ext)){
					return true;
				}
			}
			return false;
		}
		
		private boolean hasHDVersion(){
			if(singleImage){
				return true;
			}
			for(String ext : extensions){
				if(checkForFile(name + "@2x", ext)){
					return true;
				}
			}
			return false;
		}

		public ImageInfo init(String line){
			String[] data = line.split(" ", 3);
			this.extensions = data[1].split(",");
			this.name = data[2];
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
						break;
					case 'L':
						legacy = true;
						break;
					}
				}
			}
			return this;
		}
	}
}

package me.roan.osuskinchecker;

import java.util.List;

/**
 * This class determines how a list of
 * {@link ImageInfo} objects are displayed.
 * @author Roan
 */
public class ImageModel extends Model{
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -4101484259624457322L;

	/**
	 * Constructs a new ImageModel with
	 * the given list of data to display.
	 * @param list A list with data to display
	 *        the {@link Info} objects should
	 *        be of the {@link ImageInfo} type.
	 * @see Info
	 * @see Model
	 * @see ImageInfo
	 */
	public ImageModel(List<Info> list) {
		super(list);
	}

	@Override
	public int getColumnCount(){
		return 4;
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
		case 3:
			return "Animated";
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
				return ((ImageInfo)view.get(row)).hasSDVersion() == true ? "Yes" : "No";
			case 2: 
				ImageInfo info = ((ImageInfo)view.get(row));
				return (info.ignored && SkinChecker.ignoreEmpty) ? "Ignored" : (info.hasHDVersion() == true ? "Yes" : "No");
			case 3:
				ImageInfo i = ((ImageInfo)view.get(row));
				return (i.variableWithDash || i.variableWithoutDash) ? (i.animated ? "Yes" : "No") : "N/A";
			}
		}catch(Exception e){
			return "Error";
		}
		return null;
	}	
}
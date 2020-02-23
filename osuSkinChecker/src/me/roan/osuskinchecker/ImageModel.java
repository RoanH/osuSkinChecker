package me.roan.osuskinchecker;

import java.util.List;

/**
 * This class determines how a list of
 * {@link ImageFilter} objects are displayed.
 * @author Roan
 * @see Model
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
	 *        the {@link Filter} objects should
	 *        be of the {@link ImageFilter} type.
	 * @see InfoOld
	 * @see Model
	 * @see ImageInfoOld
	 */
	public ImageModel(List<Filter<?>> list){
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
		ImageFilter filter = (ImageFilter)view.get(row);
		try{
			switch(col){
			case 0:
				return filter;//TODO handle custom names
			case 1:
				return filter.hasSD();
			case 2:
				return (filter.allEmptyImages() && SkinChecker.ignoreEmpty) ? "Ignored" : filter.hasHD();
			case 3:
				if(filter.animatedDash || filter.animatedNoDash){
					if(filter.isAnimated()){
						return "Yes: " + filter.getFrameString();
					}else{
						return "No";
					}
				}else{
					return "N/A";
				}
			}
		}catch(Exception e){
			return "Error";
		}
		return null;
	}
}
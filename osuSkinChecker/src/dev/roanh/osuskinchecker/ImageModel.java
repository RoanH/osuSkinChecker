package dev.roanh.osuskinchecker;

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
	 * the given list of filters to display.
	 * @param list A list with filters to display
	 *        the {@link Filter} objects should
	 *        be of the {@link ImageFilter} type.
	 * @see Filter
	 * @see Model
	 * @see ImageFilter
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
		switch(col){
		case 0:
			return filter;
		case 1:
			return filter.isOverriden() ? "Ignored" : (filter.hasSD() ? "Yes" : "No");
		case 2:
			if(filter.single){
				return "N/A";
			}else if((filter.allEmptySD() && SkinChecker.ignoreEmpty) || filter.isOverriden()){
				return "Ignored";
			}else{
				return filter.hasHD() ? "Yes" : "No";
			}
		case 3:
			if(filter.animatedDash || filter.animatedNoDash){
				return filter.isAnimated() ? ("Yes: " + filter.getFrameString()) : "No";
			}else{
				return "N/A";
			}
		}
		return null;
	}
}
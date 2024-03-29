package dev.roanh.osuskinchecker;

import java.util.List;

/**
 * This class determines how a list of
 * {@link SoundFilter} objects are displayed.
 * @author Roan
 * @see Model
 */
public class SoundModel extends Model{
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -7254967007592437528L;

	/**
	 * Constructs a new SoundModel with
	 * the given list of data to display.
	 * @param list A list with data to display.
	 * @see Filter
	 * @see Model
	 */
	public SoundModel(List<Filter<?>> list){
		super(list);
	}

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
		switch(col){
		case 0:
			return view.get(row);
		case 1:
			return view.get(row).hasMatch() ? "Yes" : "No";
		}
		return null;
	}
}
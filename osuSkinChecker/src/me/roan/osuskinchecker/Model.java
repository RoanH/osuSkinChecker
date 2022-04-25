package me.roan.osuskinchecker;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import me.roan.osuskinchecker.ini.Version;

/**
 * Abstract base class for table models
 * that predefines filtering subroutines
 * and handles some basic table properties
 * @author Roan
 * @see SoundModel
 * @see ImageModel
 */
public abstract class Model extends DefaultTableModel{
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 5912216650613737949L;
	/**
	 * The table data for this table model.
	 */
	private List<Filter<?>> data;
	/**
	 * The filtered table data for this table model.
	 */
	protected List<Filter<?>> view = new ArrayList<Filter<?>>();

	/**
	 * Creates a new Model with the given
	 * list of information objects as its
	 * table data.
	 * @param list The table data for this
	 *        table model.
	 */
	public Model(List<Filter<?>> list){
		data = list;
	}

	/**
	 * Updates the view for this model.
	 * This includes filtering the table
	 * data and repainting the table.
	 * @param version The <tt>skin.ini</tt>
	 *        version used in this skin.
	 */
	protected void updateView(Version version){
		view.clear();
		for(Filter<?> i : data){
			if(i.show(version)){
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
}
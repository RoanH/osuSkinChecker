package me.roan.osuskinchecker;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

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
	 * The table data for this table model
	 */
	private List<? extends Filter> data;
	/**
	 * The filtered table data for this table model
	 */
	protected List<Filter> view = new ArrayList<Filter>();

	/**
	 * Creates a new Model with the given
	 * list of information objects as its
	 * table data
	 * @param list The table data for this
	 *        table model
	 */
	public Model(List<? extends Filter> list){
		data = list;
		updateView();
	}

	/**
	 * Updates the view for this model.
	 * This includes filtering the table
	 * data and repainting the table.
	 */
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
}
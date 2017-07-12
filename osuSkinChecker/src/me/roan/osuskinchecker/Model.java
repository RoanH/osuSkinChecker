package me.roan.osuskinchecker;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public abstract class Model extends DefaultTableModel{
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
}
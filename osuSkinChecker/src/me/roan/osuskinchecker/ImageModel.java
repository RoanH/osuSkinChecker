package me.roan.osuskinchecker;

import java.util.List;

public class ImageModel extends Model{
	
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
}
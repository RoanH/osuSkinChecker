package me.roan.osuskinchecker.ini;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * Simple layout manager that only accepts
 * two components and divides the space in
 * a 2/3 and 1/3 area.
 * @author Roan
 */
public class SplitLayout implements LayoutManager2{
	/**
	 * The first component in this layout
	 * that gets the 2/3 area
	 */
	private Component first;
	/**
	 * The second component in this layout
	 * that gets the 1/3 area
	 */
	private Component second;
	/**
	 * The preferred height for this layout
	 */
	private int height;
	/**
	 * 1/3 of the preferred width for this layout
	 */
	private int pref;

	@Override
	public void addLayoutComponent(String name, Component comp){
		if(first == null){
			first = comp;
			pref = Math.max(pref, comp.getPreferredSize().width / 2);
		}else{
			second = comp;
			pref = Math.max(pref, comp.getPreferredSize().width);
		}
		height = Math.max(height, comp.getPreferredSize().height);
	}

	@Override
	public void removeLayoutComponent(Component comp){
	}

	@Override
	public Dimension preferredLayoutSize(Container parent){
		return new Dimension(pref * 3, height);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent){
		return new Dimension(0, height);
	}

	@Override
	public void layoutContainer(Container parent){
		first.setBounds(0, 0, (int)((parent.getWidth() / 3.0) * 2.0), parent.getHeight());
		second.setBounds((int)((parent.getWidth() / 3.0) * 2.0), 0, (int)(parent.getWidth() / 3.0), parent.getHeight());
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints){
		addLayoutComponent("", comp);
	}

	@Override
	public Dimension maximumLayoutSize(Container target){
		return new Dimension(Integer.MAX_VALUE, height);
	}

	@Override
	public float getLayoutAlignmentX(Container target){
		return 0.5F;
	}

	@Override
	public float getLayoutAlignmentY(Container target){
		return 0.5F;
	}

	@Override
	public void invalidateLayout(Container target){
	}

	/**
	 * Custom Scrollable to set a workable
	 * scroll speed
	 * @author Roan
	 * @see Scrollable
	 */
	protected static final class ScrollPane extends JPanel implements Scrollable{
		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = -5413484937511138066L;

		@Override
		public Dimension getPreferredScrollableViewportSize(){
			return new Dimension(this.getParent().getPreferredSize().width, this.getPreferredSize().height);
		}

		@Override
		public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2){
			return getScrollableUnitIncrement(arg0, arg1, arg2);
		}

		@Override
		public boolean getScrollableTracksViewportHeight(){
			return false;
		}

		@Override
		public boolean getScrollableTracksViewportWidth(){
			return true;
		}

		@Override
		public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2){
			return this.getComponentCount() == 0 ? 1 : ((this.getHeight() / this.getComponentCount()) * 2);
		}
	}
}

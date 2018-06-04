package me.roan.osuskinchecker;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;

public class SplitLayout implements LayoutManager, LayoutManager2{
	
	private Component first;
	private Component second;
	private int height;
	private int pref;

	@Override
	public void addLayoutComponent(String name, Component comp) {
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
	public void removeLayoutComponent(Component comp) {		
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(pref, height);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(0, height);
	}

	@Override
	public void layoutContainer(Container parent) {
		first.setBounds(0, 0, (int)((parent.getWidth() / 3.0) * 2.0), parent.getHeight());
		second.setBounds((int)((parent.getWidth() / 3.0) * 2.0), 0, (int)(parent.getWidth() / 3.0), parent.getHeight());
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		addLayoutComponent("", comp);		
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, height);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5F;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5F;
	}

	@Override
	public void invalidateLayout(Container target) {		
	}
}

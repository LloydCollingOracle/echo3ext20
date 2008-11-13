package org.sgodden.echo.ext20;

import nextapp.echo.app.Border;
import nextapp.echo.extras.app.event.TreeExpansionEvent;
import nextapp.echo.extras.app.event.TreeExpansionListener;
import nextapp.echo.extras.app.tree.TreeColumnModel;
import nextapp.echo.extras.app.tree.TreeModel;

public class Tree extends nextapp.echo.extras.app.Tree implements TreeExpansionListener {
	
	public static final String HAS_BORDER_PROPERTY = "hasBorder";
	
	public Tree() {
		this(null);
	}
	
	public Tree(TreeModel treeModel) {
		this(treeModel, null);
	}
	
	public Tree(TreeModel treeModel, TreeColumnModel columnModel) {
		super(treeModel, columnModel);
		setHeaderVisible(true);
		setCellRenderer(new DefaultTreeCellRenderer());
		addTreeExpansionListener(this);
	}
	
	/**
	 * The ext20 component does not support borders
	 */
	@Override
	public void setBorder(Border border) {
		throw new UnsupportedOperationException("The ext20 tree component does not support specific styles of borders");
	}
	
	public void setHasBorder(boolean b) {
		set(HAS_BORDER_PROPERTY, b);
	}
	
	public boolean hasBorder() {
		return (Boolean)get(HAS_BORDER_PROPERTY);
	}
	
	/**
	 * The ext component does not support the 'dotted' style lines,
	 * so this will throw an UnsupportedOperationException when that
	 * value is passed.
	 */
	@Override
	public void setLineStyle(int style) {
		if (LINE_STYLE_DOTTED == style)
			throw new UnsupportedOperationException("The ext20 Tree does not support the dotted line style");
		super.setLineStyle(style);
	}

	public void treeCollapsed(TreeExpansionEvent arg0) {
		invalidate();
	}

	public void treeExpanded(TreeExpansionEvent arg0) {
		invalidate();
	}
}

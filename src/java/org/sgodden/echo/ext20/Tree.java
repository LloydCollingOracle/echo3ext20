package org.sgodden.echo.ext20;

import nextapp.echo.app.Border;
import nextapp.echo.extras.app.event.TreeExpansionEvent;
import nextapp.echo.extras.app.event.TreeExpansionListener;
import nextapp.echo.extras.app.tree.TreeColumnModel;
import nextapp.echo.extras.app.tree.TreeModel;

/**
 * Implementation of an Ext tree component.
 * @author Lloyd Colling.
 * 
 * @TODO implement multi-select using the checkboxes on the client
 *
 */
@SuppressWarnings("serial")
public class Tree extends nextapp.echo.extras.app.Tree implements TreeExpansionListener {

    public static final String INPUT_EXPAND = "expand";
    public static final String EXPANSION_LISTENERS_CHANGED_PROPERTY = "expansionListeners";
    
    public static final String PROPERTY_HAS_BORDER = "hasBorder";
    public static final String PROPERTY_SHOW_CHECKBOXES = "showCheckBoxes";
    public static final String PROPERTY_SHOW_ROOT_NODE = "showRootNode";
    public static final String PROPERTY_ACTION_ON_SELECT = "doActionOnSelect";
    /**
     * Whether a select event should be generated when
     * the user right clicks on an unselected row to bring up
     * the context menu.
     */
    public static final String PROPERTY_SELECT_ON_CONTEXT = "selectOnContext";
    
    private Menu contextMenu;
    
    public Tree() {
        this(null);
    }
    
    public Tree(TreeModel treeModel) {
        this(treeModel, null);
    }
    
    public Tree(TreeModel treeModel, TreeColumnModel columnModel) {
        super(treeModel, columnModel);
        setHeaderVisible(true);
        setActionOnSelect(true);
        setCellRenderer(new DefaultTreeCellRenderer());
        addTreeExpansionListener(this);
    }
    
    @Override
    public void addTreeExpansionListener(TreeExpansionListener l) {
        super.addTreeExpansionListener(l);
        firePropertyChange(EXPANSION_LISTENERS_CHANGED_PROPERTY, null, l);
    }
    
    /**
     * The ext20 component does not support borders
     */
    @Override
    public void setBorder(Border border) {
        throw new UnsupportedOperationException("The ext20 tree component does not support specific " +
                "styles of borders");
    }
    
    public void setHasBorder(boolean b) {
        set(PROPERTY_HAS_BORDER, b);
    }
    
    public boolean hasBorder() {
        return (Boolean)get(PROPERTY_HAS_BORDER);
    }
    
    /**
     * The ext component does not support the 'dotted' style lines,
     * so this will throw an UnsupportedOperationException when that
     * value is passed.
     */
    @Override
    public void setLineStyle(int style) {
        if (LINE_STYLE_DOTTED == style)
            throw new UnsupportedOperationException("The ext20 Tree does not support the dotted" +
                    " line style");
        super.setLineStyle(style);
    }

    public void treeCollapsed(TreeExpansionEvent arg0) {
        invalidate();
    }

    public void treeExpanded(TreeExpansionEvent arg0) {
        invalidate();
    }
    
    public Menu getContextMenu() {
        return contextMenu;
    }
    
    public void setContextMenu(Menu menu) {
        if (contextMenu != null)
            remove(contextMenu);
        this.contextMenu = menu;
        if (menu != null)
            add(contextMenu);
    }
    
    @Override
    protected Renderer createRenderer() {
        return new ContextMenuAwareRenderer();
    }
    
    protected class ContextMenuAwareRenderer extends Renderer {

        @Override
        protected void fullUpdate() {
            init();
            int remaining = 0;
            if (contextMenu != null)
                remaining++;
            
            while (getComponentCount() > remaining) {
                if (getComponent(0) != contextMenu) {
                    remove(getComponent(0));
                } else {
                    remove(getComponent(1));
                }
            }
            treePathToComponentCache.clear();
            rowToTreePathCache.clear();
            row = 0;
            doRender();
        }
    }

    public boolean isShowCheckBoxes() {
        return Boolean.TRUE.equals(get(PROPERTY_SHOW_CHECKBOXES));
    }

    public void setShowCheckBoxes(boolean showCheckBoxes) {
        set(PROPERTY_SHOW_CHECKBOXES, Boolean.valueOf(showCheckBoxes));
    }

    public boolean isShowRootNode() {
        return Boolean.TRUE.equals(get(PROPERTY_SHOW_ROOT_NODE));
    }

    public void setShowRootNode(boolean showRootNode) {
        set(PROPERTY_SHOW_ROOT_NODE, Boolean.valueOf(showRootNode));
    }

    /**
     * Determines whether a select event should be generated when
     * the user right clicks on an unselected row to bring up
     * the context menu.
     * @return
     */
    public boolean isSelectOnContext() {
        return Boolean.TRUE.equals(get(PROPERTY_SELECT_ON_CONTEXT));
    }

    public void setSelectOnContext(boolean selectOnContext) {
        set(PROPERTY_SELECT_ON_CONTEXT, Boolean.valueOf(selectOnContext));
    }

    /**
     * Determines whether a selection change should generate an action event
     * @return
     */
    public boolean isActionOnSelect() {
        return Boolean.TRUE.equals(get(PROPERTY_ACTION_ON_SELECT));
    }

    public void setActionOnSelect(boolean actionOnSelect) {
        set(PROPERTY_ACTION_ON_SELECT, Boolean.valueOf(actionOnSelect));
    }
}

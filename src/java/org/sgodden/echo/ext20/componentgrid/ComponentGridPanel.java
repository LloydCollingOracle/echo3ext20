/* =================================================================
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 2.1 of the License, or (at your option) any later version.
#
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public
# License along with this library; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
#
# ================================================================= */
package org.sgodden.echo.ext20.componentgrid;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nextapp.echo.app.Component;
import nextapp.echo.app.IllegalChildException;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.ChangeEvent;
import nextapp.echo.app.event.ChangeListener;
import nextapp.echo.app.event.TableModelEvent;
import nextapp.echo.app.event.TableModelListener;
import nextapp.echo.app.list.DefaultListSelectionModel;
import nextapp.echo.app.list.ListSelectionModel;
import nextapp.echo.app.table.EditableTableModel;
import nextapp.echo.app.table.TableModel;

import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.Menu;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.SelectionMode;
import org.sgodden.echo.ext20.Toolbar;
import org.sgodden.echo.ext20.grid.ColumnConfiguration;
import org.sgodden.echo.ext20.grid.ColumnListener;
import org.sgodden.echo.ext20.grid.ColumnModel;
import org.sgodden.echo.ext20.grid.DefaultGridCellRenderer;
import org.sgodden.echo.ext20.grid.GridCellRenderer;
import org.sgodden.echo.ext20.grid.PagingToolbar;
import org.sgodden.echo.ext20.grid.PagingToolbarClient;
import org.sgodden.echo.ext20.grid.RemoveColumnEvent;
import org.sgodden.echo.ext20.grid.SortableTableModel;

/**
 * <p>A GridPanel that renders its contents as components.</p>
 * 
 * <p>This differs from the {@link org.sgodden.echo.ext20.grid.GridPanel} which renders it's contents using
 * JavaScript fragments.</p>
 * 
 * <p>The advantages of using this component is that you can have components that respond to user actions
 * by calling the server, and you have complete control over how the contents are rendered without having
 * to resort to JavaScript fragments.</p>
 * 
 * <p>The disadvantages are:<br/>
 * <ul>
 * <li>Grouping does not work. ExtJS seems to group according to the rendered value; since the rendered 
 * values for this component are all DIVs with unique ids, it views each cell as containing a unique value.</li>
 * <li>Editors do not work. Using the ColumnConfiguration editor property means that after a value changes, the grid 
 * blanks out.</li>
 * <li>Performance. This has been a proof-of-concept so far, and there are multiple calls to doRender when the 
 * component is created, so time is wasted.</li>
 * </ul>
 * </p>
 * @author Lloyd Colling
 * 
 */
@SuppressWarnings( { "serial" })
public class ComponentGridPanel extends Panel implements TableModelListener,
        PagingToolbarClient {

    public static final String ACTION_COMMAND_PROPERTY = "actionCommand";
    public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";
    public static final String COLUMN_MODEL_PROPERTY = "columnModel";
    public static final String INPUT_ACTION = "action";
    public static final String MODEL_CHANGED_PROPERTY = "model";
    public static final String PAGE_OFFSET_PROPERTY = "pageOffset";
    public static final String SELECT_ACTION = "select";
    public static final String SELECTION_CHANGED_PROPERTY = "selection";
    public static final String SELECTION_MODE = "selectionMode";
    public static final String SELECTION_MODEL_CHANGED_PROPERTY = "selectionModel";
    public static final String SORT_ACTION = "sort";
    public static final String SORT_FIELD_PROPERTY = "sortField";
    public static final String SORT_LISTENERS_PROPERTY = "sort";
    public static final String SORT_ORDER_PROPERTY = "sortDirection";
    public static final String SET_SIZE_COLUMNS_TO_GRID_PROPERTY = "forceFit";
    public static final String COLUMN_ADDED = "columnAdd";
    public static final String COLUMN_REMOVED = "columnRemove";
    public static final String COLUMN_LISTENERS = "columnListeners";
    public static final String GROUP_ACTION = "group";
    public static final String SHOW_CHECKBOX = "showCheckbox";
    public static final String HIDE_HEADERS = "hideHeaders";
    public static final String EDITCELLCONTENTS = "editcellcontents";
    public static final String PROPERTY_MODEL = "model";
    public static final String HAS_HEADER_CONTEXT_MENU_PROPERTY = "hasHeaderContextMenu";
    public static final String HAS_ROW_CONTEXT_MENU_PROPERTY = "hasRowContextMenu";
    public static final String HAS_CELL_CONTEXT_MENU_PROPERTY = "hasCellContextMenu";
    public static final String DEFAULT_RENDERER_CHANGED_PROPERTY = "defaultRenderer";
    public static final String ROW_COUNT_PROPERTY = "rowCount";
    
    public static final ComponentGridCellRenderer DEFAULT_CELL_RENDERER = new DefaultComponentGridCellRenderer();

    private int pageSize;
    private ListSelectionModel selectionModel;
    private boolean suppressChangeNotifications;
    private boolean notifySelect = false;
    private boolean valid;
    private boolean rendering;
    private Map<Class<?>, ComponentGridCellRenderer> defaultRendererMap = new HashMap<Class<?>, ComponentGridCellRenderer>();
    private GridCellRenderer modelValueRenderer = new DefaultGridCellRenderer();
    private Menu headerContextMenu;
    private Menu rowContextMenu;
    private Menu cellContextMenu;
    
    private List<Component> renderedChildren = new LinkedList<Component>();
    private Toolbar bottomToolbar;
    private Toolbar topToolbar;

    /**
     * Local handler for list selection events.
     */
    private ChangeListener listSelectionListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            if (!suppressChangeNotifications) {
                firePropertyChange(SELECTION_CHANGED_PROPERTY, null, null);
            }
        }
    };

    /**
     * Constructs a new grid panel.
     */
    public ComponentGridPanel() {
        super();
        setBorder(true);
        setSelectionModel(new DefaultListSelectionModel());
        setSelectionMode(SelectionMode.MULTIPLE_INTERVAL_SELECTION);
        setPageOffset(0);
        setComplexProperty(COLUMN_MODEL_PROPERTY, true);
        setContextMenuStatusAndChildren();
    }

    /**
     * Constructs a new grid panel.
     * 
     * @param columnModel
     *            the column model.
     */
    public ComponentGridPanel(ColumnModel columnModel) {
        this();
        setColumnModel(columnModel);
        setContextMenuStatusAndChildren();
    }

    /**
     * Constructs a new grid panel.
     * 
     * @param columnModel
     *            the column model.
     * @param tableModel
     *            the table model.
     */
    public ComponentGridPanel(ColumnModel columnModel, TableModel tableModel) {
        this(columnModel);
        setModel(tableModel);
        setContextMenuStatusAndChildren();
    }

    /**
     * Sets the showCheckbox property which if enabled shows checkboxes on the
     * GridPanel.
     */
    public void setShowCheckbox(Boolean showCheckbox) {
        set(SHOW_CHECKBOX, showCheckbox);
        setSelectionMode(SelectionMode.MULTIPLE_INTERVAL_SELECTION);
    }

    public boolean getShowCheckbox() {
        Boolean b = (Boolean) get(SHOW_CHECKBOX);
        return (b == null) ? false : b.booleanValue();
    }

    /**
     * Adds an <code>ActionListener</code> to the <code>Table</code>.
     * <code>ActionListener</code>s will be invoked when the user selects a row.
     * 
     * @param l
     *            the <code>ActionListener</code> to add
     */
    public void addActionListener(ActionListener l) {
        getEventListenerList().addListener(ActionListener.class, l);
        // Notification of action listener changes is provided due to
        // existence of hasActionListeners() method.
        firePropertyChange(ACTION_LISTENERS_CHANGED_PROPERTY, null, l);
    }

    /**
     * Fires an action event to all listeners.
     */
    private void fireActionEvent() {
        if (!hasEventListenerList()) {
            return;
        }
        EventListener[] listeners = getEventListenerList().getListeners(
                ActionListener.class);
        ActionEvent e = null;
        for (int i = 0; i < listeners.length; ++i) {
            if (e == null) {
                e = new ActionEvent(this,
                        (String) getRenderProperty(ACTION_COMMAND_PROPERTY));
            }
            ((ActionListener) listeners[i]).actionPerformed(e);
        }
    }

    /**
     * Returns the action command which will be provided in
     * <code>ActionEvent</code>s fired by this <code>Table</code>.
     * 
     * @return the action command
     */
    public String getActionCommand() {
        return (String) get(ACTION_COMMAND_PROPERTY);
    }

    /**
     * Returns the column model for the table.
     * 
     * @return the column model for the table.
     */
    public ColumnModel getColumnModel() {
        return ((ColumnModel) get(COLUMN_MODEL_PROPERTY));
    }

    /**
     * Returns whether a server event should be generated immediately upon the
     * user selecting a grid row.
     * 
     * @return whether to generate a server event.
     */
    public boolean isNotifySelect() {
        return this.notifySelect;
    }

    /**
     * Returns the offset to the current page, in the case that the page size
     * has been set.
     * 
     * @return the offset to the current page.
     */
    public int getPageOffset() {
        return (Integer) get(PAGE_OFFSET_PROPERTY);
    }

    /**
     * Returns the page size, or 0 if the table is not paged.
     * 
     * @return the page size, or 0 if the table is not paged.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Returns the selected indices.
     * 
     * @return the selected indices.
     */
    public int[] getSelectedIndices() {
        int size = getModel().getRowCount();
        List<Integer> list = new ArrayList<Integer>(); 
        for (int i = 0; i < size; i++) {
            if (selectionModel.isSelectedIndex(i)) {
                list.add(i);
            }
        }
        int[] ret = new int[list.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    /**
     * Returns the selection mode.
     * 
     * @return the selection mode.
     */
    public SelectionMode getSelectionMode() {
        SelectionMode ret;
        String mode = (String) get(SELECTION_MODE);
        if (mode.equals("S")) {
            ret = SelectionMode.SINGLE_SELECTION;
        } else if (mode.equals("SI")) {
            ret = SelectionMode.SINGLE_INTERVAL_SELECTION;
        } else if (mode.equals("MI")) {
            ret = SelectionMode.MULTIPLE_INTERVAL_SELECTION;
        } else {
            throw new IllegalArgumentException("Unknown selection mode: "
                    + mode);
        }
        return ret;

    }

    /**
     * Returns the row selection model.
     * 
     * @return the selection model
     */
    public ListSelectionModel getSelectionModel() {
        return selectionModel;
    }

    /**
     * Returns the name of the field by which the data should be sorted.
     * 
     * @return the name of the field by which the data should be sorted.
     */
    public String getSortField() {
        return (String) get(SORT_FIELD_PROPERTY);
    }

    /**
     * Returns the order by which the field specified in
     * {@link #setSortField(java.lang.String)} should be sorted.
     * 
     * @return the sort order.
     */
    public boolean getSortOrder() {
        boolean ret = true;

        String sortString = (String) get(SORT_ORDER_PROPERTY);
        if ("ASC".equals(sortString)) {
            ret = true;
        } else if ("DESC".equals(sortString)) {
            ret = false;
        }

        return ret;
    }

    /**
     * Returns the grids forceFit config.
     * 
     * @return is the grid forceFit.
     */
    public boolean getSetSizeColumnsToGrid() {
        return (Boolean) get(SET_SIZE_COLUMNS_TO_GRID_PROPERTY);
    }

    /**
     * Returns the grid's table model.
     * 
     * @return the table model.
     */
    public TableModel getModel() {
        return (TableModel) get(PROPERTY_MODEL);
    }

    /**
     * Returns whether any <code>ActionListener</code>s are registered.
     * 
     * @return true if any action listeners are registered
     */
    public boolean hasActionListeners() {
        return getEventListenerList().getListenerCount(ActionListener.class) != 0;
    }

    /**
     * Returns whether the model is sortable.
     * 
     * @return true if the model is sortable, false if not.
     */
    public boolean isModelSortable() {
        return (getModel() instanceof SortableTableModel);
    }

    /**
     * @see nextapp.echo.app.Component#processInput(java.lang.String,
     *      java.lang.Object)
     */
    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (inputName.equals(SELECTION_CHANGED_PROPERTY)) {
            setSelectedIndices((int[]) inputValue);
        } else if (INPUT_ACTION.equals(inputName)) {
            fireActionEvent();
        } else if (SORT_FIELD_PROPERTY.equals(inputName)) {
            setSortField((String) inputValue);
        } else if (SORT_ORDER_PROPERTY.equals(inputName)) {
            String value = (String) inputValue;
            if (value.equals("ASC")) {
                setSortAscending(true);
            } else if (value.equals("DESC")) {
                setSortAscending(false);
            } else {
                throw new IllegalArgumentException("Unknown sort order: "
                        + value);
            }
        } else if (SORT_ACTION.equals(inputName)) {
            doSort();
        } else if (COLUMN_ADDED.equals(inputName)) {
            fireColumnAddedEvent();
        } else if (COLUMN_REMOVED.equals(inputName)) {
            fireColumnRemovedEvent((Integer) inputValue);
        } else if (GROUP_ACTION.equals(inputName)) {
            doSort();
        }
    }

    protected void doSort() {
        if (getModel() instanceof SortableTableModel) {
            ColumnModel colModel = getColumnModel();
            String[] columnIndices = new String[colModel.getColumnCount()];
            boolean[] ascending = new boolean[colModel.getColumnCount()];
            ColumnConfiguration group = null;
            for (ColumnConfiguration cc : colModel) {
                if (cc.getGrouping())
                    group = cc;
            }

            ColumnConfiguration sort = null;
            if (getSortField() != null) {
                for (ColumnConfiguration cc : colModel) {
                    if (cc.getDataIndex().equals(getSortField())) {
                        sort = cc;
                    }
                }
            }

            // the standard columns are offset in the output array
            // if we're grouping or sorting
            int offset = 0;
            if (group == null && sort != null)
                offset = 1;
            else if (group != null && sort != null && group != sort)
                offset = 2;
            else if (group != null && sort != null && group == sort)
                offset = 1;

            for (int i = 0; i < columnIndices.length; i++) {
                ColumnConfiguration cc = colModel.getColumn(i);
                String sequence = cc.getDataIndex();

                // work out where in the output array the column goes
                int index = -1;
                if (cc == group) {
                    index = 0;
                } else if (cc == sort) {
                    if (group == null) {
                        index = 0;
                    } else {
                        index = 1;
                    }
                } else {
                    index = offset++;
                }

                columnIndices[index] = sequence;

                ascending[index] = "ASCENDING".equals(cc.getSortDirection())
                        || "ASC".equals(cc.getSortDirection());
            }
            suppressChangeNotifications = true;
            ((SortableTableModel) getModel()).sort(columnIndices, ascending);
            suppressChangeNotifications = false;
            firePropertyChange(MODEL_CHANGED_PROPERTY, null, getModel()); // a

            // used for retrieving the size of the groups in the model
//            if (group != null && getModel() instanceof GroupingTableModel) {
//                ((GroupingTableModel)getModel()).doGrouping(true);
//            }
            getSelectionModel().clearSelection();
        } else {
            throw new IllegalStateException(
                    "Request to group/sort table made, "
                            + "but model is not sortable");
        }
    }

    /**
     * Removes the specified action listener.
     * 
     * @param l
     *            the listener to remove.
     */
    public void removeActionListener(ActionListener l) {
        if (!hasEventListenerList()) {
            return;
        }
        getEventListenerList().removeListener(ActionListener.class, l);
        // Notification of action listener changes is provided due to
        // existence of hasActionListeners() method.
        firePropertyChange(ACTION_LISTENERS_CHANGED_PROPERTY, l, null);

    }

    /**
     * Sets the bottom toolbar, which must be an instance of
     * {@link PagingToolbar}.
     * 
     * @param toolbar
     */
    @Override
    public void setBottomToolbar(Toolbar toolbar) {
        if (!(toolbar instanceof PagingToolbar)) {
            throw new IllegalArgumentException(
                    "Toolbar must be"
                            + " an instance of org.sgodden.echo.ext20.grid.PagingToolbar");
        }
        if (getModel() == null || pageSize == 0) {
            throw new IllegalStateException("Initialise the model and"
                    + " page size before setting the paging toolbar");
        }
        
        if (toolbar != null) {
            toolbar.setPosition(Toolbar.Position.BOTTOM);
        }
        this.bottomToolbar = toolbar;
        ((PagingToolbar) toolbar).initialise(getModel(), pageSize, this);
        this.valid = false;
    }
    
    @Override
    public Toolbar getBottomToolbar() {
        return this.bottomToolbar;
    }

    /**
     * Sets the column model for the table. WARNING: this forces a full re-draw
     * of the grid.
     * 
     * @param columnModel
     *            the column model for the table.
     */
    public void setColumnModel(final ColumnModel columnModel) {
        set(COLUMN_MODEL_PROPERTY, columnModel);
        // ensure listeners are notified of a column model change
        firePropertyChange(COLUMN_MODEL_PROPERTY, null, columnModel);
    }

    /**
     * Sets whether a server event should be generated immediately upon the user
     * selecting a row. selects a row.
     * 
     * @param notify
     *            whether to generate a server event.
     */
    public void setNotifySelect(boolean notify) {
        this.notifySelect = notify;
    }

    /**
     * Sets the offset to the first record in the model that is being shown in
     * the grid view.
     * 
     * @param pageOffset
     *            the offset to the first record in the model.
     */
    public void setPageOffset(int pageOffset) {
        set(PAGE_OFFSET_PROPERTY, pageOffset);
        tableChanged(null);
        doRender();
    }

    /**
     * Sets the size of the page that should be displayed when not displaying
     * the entire table model at once. A size of 0 means that the table should
     * not be paged.
     * 
     * @param pageSize
     *            the size of the page, or 0 to not page.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        tableChanged(null);
    }

    /**
     * Selects only the specified row indices.
     * 
     * @param selectedIndices
     *            the indices to select
     */
    public void setSelectedIndices(int[] selectedIndices) {
        // Temporarily suppress the Tables selection event notifier.
        suppressChangeNotifications = true;
        selectionModel.clearSelection();
        for (int i = 0; i < selectedIndices.length; ++i) {
            selectionModel.setSelectedIndex(selectedIndices[i], true);
        }
        // End temporary suppression.
        suppressChangeNotifications = false;
        firePropertyChange( SELECTION_CHANGED_PROPERTY, null, selectionModel);
    }

    /**
     * Sets the selection mode for this grid.
     * 
     * @param mode
     *            the selection mode.
     */
    public void setSelectionMode(SelectionMode mode) {
        switch (mode) {
        case SINGLE_SELECTION:
            set(SELECTION_MODE, "S");
            getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            break;
        case SINGLE_INTERVAL_SELECTION:
            set(SELECTION_MODE, "SI");
            getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_SELECTION);
            break;
        case MULTIPLE_INTERVAL_SELECTION:
            set(SELECTION_MODE, "MI");
            getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_SELECTION);
        }
    }

    /**
     * Sets the row selection model. The selection model may not be null.
     * 
     * @param newValue
     *            the new selection model
     */
    public void setSelectionModel(ListSelectionModel newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException(
                    "Selection model may not be null.");
        }
        ListSelectionModel oldValue = selectionModel;
        if (oldValue != null) {
            oldValue.removeChangeListener(listSelectionListener);
        }
        newValue.addChangeListener(listSelectionListener);
        selectionModel = newValue;
        firePropertyChange(SELECTION_MODEL_CHANGED_PROPERTY, oldValue, newValue);
    }

    /**
     * Sets the forceFit property of the grid panel.
     * 
     * @param setSizeColumnsToGrid
     */
    public void setSetSizeColumnsToGrid(Boolean setSizeColumnsToGrid) {
        set(SET_SIZE_COLUMNS_TO_GRID_PROPERTY, setSizeColumnsToGrid);
    }

    /**
     * Sets the name of the field by which the data will be sorted, and sets the
     * sort sequence to ascending.
     * 
     * @param sortField
     *            the name of the field to sort by.
     */
    public void setSortField(String sortField) {
        set(SORT_FIELD_PROPERTY, sortField);
        setSortAscending(true);
    }

    /**
     * Sets whether the field specified in
     * {@link #setSortField(java.lang.String)} should be sorted ascending
     * (true), or descending (false).
     * 
     * @param ascending
     *            true to sort ascending, false to sort descending.
     */
    public void setSortAscending(boolean ascending) {
        if (ascending) {
            set(SORT_ORDER_PROPERTY, "ASC");
        } else {
            set(SORT_ORDER_PROPERTY, "DESC");
        }
    }

    /**
     * Sets the data store from an echo table model.
     * 
     * @param tableModel
     *            the table model.
     */
    public void setModel(TableModel tableModel) {
        if (tableModel == null) {
            throw new IllegalArgumentException("table model may not be null");
        }

        set(PROPERTY_MODEL, tableModel);
        set(ROW_COUNT_PROPERTY, tableModel.getRowCount());
        
        // just in case they set the same table model
        tableModel.removeTableModelListener(this); 
        tableModel.addTableModelListener(this);

        // always force change
        tableChanged(null); 
    }

    /**
     * Sets whether the grid panel should enable editing of the contents of the
     * table cells. This should only be called when an EditableTableModel has
     * been set as the model of the table.
     * 
     * @param editCells
     */
    public void setEditCellContents(boolean editCells) {
        if (!(getModel() instanceof EditableTableModel))
            throw new IllegalStateException(
                    "Cannot set a grid into editable mode without an editable table model");
        set(EDITCELLCONTENTS, Boolean.valueOf(editCells));
    }

    /**
     * Whether the grid panel should enable editing of the contents of the table
     * cells.
     * 
     * @return
     */
    public boolean getEditCellContents() {
        return Boolean.TRUE.equals(get(EDITCELLCONTENTS));
    }

    /**
     * Forces a client-side refresh of the table when the table model changes.
     */
    public void tableChanged(TableModelEvent e) {
        invalidate();
        if (!suppressChangeNotifications) {
            /*
             * a bodge but we're not interested in the old and new values anyway
             */
            firePropertyChange(MODEL_CHANGED_PROPERTY, null, getModel()); 
        }
    }

    public void addColumnListener(ColumnListener l) {
        getEventListenerList().addListener(ColumnListener.class, l);
        // Notification of action listener changes is provided due to
        // existence of hasActionListeners() method.
        firePropertyChange(COLUMN_LISTENERS, null, l);
    }

    /**
     * Removes the specified column listener.
     * 
     * @param l
     *            the listener to remove.
     */
    public void removeColumnListener(ColumnListener l) {
        if (!hasEventListenerList()) {
            return;
        }
        getEventListenerList().removeListener(ColumnListener.class, l);
        firePropertyChange(COLUMN_LISTENERS, l, null);
    }

    public boolean hasColumnListeners() {
        return getEventListenerList().getListenerCount(ColumnListener.class) != 0;
    }

    private void fireColumnRemovedEvent(Integer columnIndex) {
        RemoveColumnEvent rce = null;
        for (EventListener listener : getEventListenerList().getListeners(
                ColumnListener.class)) {
            if (rce == null)
                rce = new RemoveColumnEvent(this, columnIndex.intValue());
            ((ColumnListener) listener).removeColumn(rce);
        }
    }

    private void fireColumnAddedEvent() {
        for (EventListener listener : getEventListenerList().getListeners(
                ColumnListener.class)) {
            ((ColumnListener) listener).addColumn();
        }
    }

    /**
     * Returns whether the headers of the grid are currently hidden
     * 
     * @return
     */
    public boolean getHideHeaders() {
        Boolean b = (Boolean) get(HIDE_HEADERS);
        return Boolean.TRUE.equals(b);
    }

    public void setHideHeaders(Boolean b) {
        set(HIDE_HEADERS, b);
    }

    public Menu getHeaderContextMenu() {
        return headerContextMenu;
    }

    public void setHeaderContextMenu(Menu headerContextMenu) {
        this.headerContextMenu = headerContextMenu;
        setContextMenuStatusAndChildren();
    }

    public Menu getRowContextMenu() {
        return rowContextMenu;
    }

    public void setRowContextMenu(Menu rowContextMenu) {
        this.rowContextMenu = rowContextMenu;
        setContextMenuStatusAndChildren();
    }

    public Menu getCellContextMenu() {
        return cellContextMenu;
    }

    public void setCellContextMenu(Menu cellContextMenu) {
        this.cellContextMenu = cellContextMenu;
        setContextMenuStatusAndChildren();
    }
    
    private void setContextMenuStatusAndChildren() {
        invalidate();
        set(HAS_CELL_CONTEXT_MENU_PROPERTY, Boolean.valueOf(cellContextMenu != null));
        set(HAS_ROW_CONTEXT_MENU_PROPERTY, Boolean.valueOf(rowContextMenu != null));
        set(HAS_HEADER_CONTEXT_MENU_PROPERTY, Boolean.valueOf(headerContextMenu != null));
    }

    
    /**
     * Renders the contents of the table model as children of this
     * grid panel
     */
    public void validate() {
        super.validate();
        while (!valid) {
            valid = true;
            doRender();
        }
    }

    
    /**
     * Re-renders changed rows.
     */
    protected void doRender() {
        try {
            rendering = true;
            for (Component c : renderedChildren) {
                remove(c);
            }
            renderedChildren.clear();
        } finally {
            rendering = false;
        }
        if (getModel() == null)
            return;
        
        int rowCount = getModel().getRowCount();
        int columnCount = getColumnModel().getColumnCount();
        
        ColumnConfiguration[] tableColumns = new ColumnConfiguration[columnCount];
        ComponentGridCellRenderer[] columnRenderers = new ComponentGridCellRenderer[columnCount];
        
        // retrieve the appropriate renderers for the columns
        for (int columnIndex = 0; columnIndex < columnCount; ++columnIndex) {
            tableColumns[columnIndex] = getColumnModel().getColumn(columnIndex);
            
            ComponentGridCellRenderer renderer = tableColumns[columnIndex].getGridCellRenderer();
            if (renderer == null) {
                Class<?> columnClass = getModel().getColumnClass(columnIndex);
                renderer = getDefaultRenderer(columnClass);
                if (renderer == null) {
                    renderer = DEFAULT_CELL_RENDERER;
                }
            }
            columnRenderers[columnIndex] = renderer;
        }
        
        int position = getPageOffset();
        int maxRow = Math.min(position + getPageSize(), rowCount);
        
        int initialComponentIndexAdjustment = position * getColumnModel().getColumnCount();
        
        // render the model as components
        for (int rowIndex = position; rowIndex < maxRow; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < columnCount; ++columnIndex) {
                Object modelValue = getModel().getValueAt(columnIndex, rowIndex);
                Component renderedComponent 
                        = columnRenderers[columnIndex].renderCell(this, modelValue, columnIndex, rowIndex);
                if (renderedComponent == null || !renderedComponent.isVisible()) {
                    renderedComponent = new Label();
                }
                try {
                    rendering = true;
                    renderedChildren.add(renderedComponent);
                    add(renderedComponent, ((rowIndex * columnCount) + columnIndex) - initialComponentIndexAdjustment);
                } finally {
                    rendering = false;
                }
            }
        }
        
        // add the context menus
        if (cellContextMenu != null && this.indexOf(cellContextMenu) == -1) {
            try {
                rendering = true;
                add(cellContextMenu);
            } finally {
                rendering = false;
            }
        }
        if (rowContextMenu != null && this.indexOf(rowContextMenu) == -1) {
            try {
                rendering = true;
                add(rowContextMenu);
            } finally {
                rendering = false;
            }
        }
        if (headerContextMenu != null && this.indexOf(headerContextMenu) == -1) {
            try {
                rendering = true;
                add(headerContextMenu);
            } finally {
                rendering = false;
            }
        }
        
        // add the toolbars
        if (getToolbar() != null && this.indexOf(getToolbar()) == -1) {
            try { 
                rendering = true; 
                add(getToolbar()); 
            } finally { 
                rendering = false;
            }
        }
        if (getBottomToolbar() != null && this.indexOf(getBottomToolbar()) == -1) {
            try { 
                rendering = true; 
                add(getBottomToolbar()); 
            } finally { 
                rendering = false;
            }
        }
    }
    
    /**
     * Returns the default <code>ComponentGridCellRenderer</code> for the specified 
     * column class.  The default renderer will be used in the event that
     * a <code>TableColumn</code> does not provide a specific renderer.
     * 
     * @param columnClass the column <code>Class</code>
     * @return the <code>ComponentGridCellRenderer</code>
     */
    public ComponentGridCellRenderer getDefaultRenderer(Class<?> columnClass) {
        return defaultRendererMap.get(columnClass);
    }

    /**
     * Sets the default <code>ComponentGridCellRenderer</code> for the specified 
     * column class.  The default renderer will be used in the event that
     * a <code>ColumnConfiguration</code> does not provide a specific renderer.
     * 
     * @param columnClass the column <code>Class</code>
     * @param newValue the <code>ComponentGridCellRenderer</code>
     */
    public void setDefaultRenderer(Class<?> columnClass, ComponentGridCellRenderer newValue) {
        invalidate();
        if (newValue == null) {
            defaultRendererMap.remove(columnClass);
        } else {
            defaultRendererMap.put(columnClass, newValue);
        }
        firePropertyChange(DEFAULT_RENDERER_CHANGED_PROPERTY, null, null);
    }

    /**
     * Marks the grid panel as needing to be re-rendered.
     */
    protected void invalidate() {
        valid = false;
    }
    
    /**
     * @see nextapp.echo.app.Component#add(nextapp.echo.app.Component, int)
     */
    public void add(Component c, int n) throws IllegalChildException {
        if (!rendering) {
            throw new IllegalStateException("Programmatic addition or removal of GridPanel children is prohibited.");
        }
        super.add(c, n);
    }

    /**
     * Used to render the tableModel for transfer to the client-side
     * @return
     */
    public GridCellRenderer getModelValueRenderer() {
        return modelValueRenderer;
    }

    public void setModelValueRenderer(GridCellRenderer modelValueRenderer) {
        this.modelValueRenderer = modelValueRenderer;
    }
    
    @Override
    public void setToolbar(Toolbar t) {
        t.setPosition(Toolbar.Position.TOP);
        this.topToolbar = t;
        valid = false;
    }
    
    public Toolbar getToolbar() {
        return this.topToolbar;
    }
}
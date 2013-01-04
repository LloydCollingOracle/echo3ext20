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
package org.sgodden.echo.ext20.grid;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import nextapp.echo.app.Component;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.util.CollectionUtils;
import org.sgodden.echo.ext20.Menu;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.SelectionMode;
import org.sgodden.echo.ext20.Toolbar;
import org.sgodden.echo.ext20.util.ListSelectionUtil;
import org.sgodden.query.models.SortableTableModel;
import java.util.Iterator;

/**
 * An ext GridPanel.
 * <p/>
 * Code example: <pre class="code"> 
 * List<ColumnConfiguration> cols = new ArrayList<ColumnConfiguration>(); 
 * cols.add(new ColumnConfiguration("User ID", "userid")); 
 * cols.add(new ColumnConfiguration("Name", "name")); 
 * ColumnModel columnModel = new ColumnModel(cols);
 * 
 * TableModel model = new DefaultTableModel(
 *   data, // simple Object[][] of your data
 *   new String[]{"id", "userid", "name", "date"}
 *   );
 * 
 * gridPanel = new GridPanel(columnModel, model);
 * 
 * gridPanel.addActionListener(new ActionListener(){ 
 *   public void actionPerformed(ActionEvent e) {
 *     Object[] data = gridPanel.getSelectedRow();
 * ... 
 *   } 
 * }); </pre>
 * 
 * @author sgodden
 * 
 */
@SuppressWarnings( { "serial" })
public class GridPanel extends Panel implements TableModelListener,
        PagingToolbarClient {

    public static final String PROPERTY_ACTION_COMMAND = "actionCommand";
    public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";
    public static final String PROPERTY_COLUMN_MODEL = "columnModel";
    public static final String INPUT_ACTION = "action";
    public static final String PROPERTY_MODEL_CHANGED = "model";
    public static final String PROPERTY_PAGE_OFFSET = "pageOffset";
    public static final String SELECT_ACTION = "select";
    public static final String PROPERTY_SELECTION_CHANGED = "selection";
    public static final String PROPERTY_SELECTION_MODE = "selectionMode";
    public static final String PROPERTY_SELECTION_MODEL_CHANGED = "selectionModel";
    public static final String SORT_ACTION = "sort";
    public static final String PROPERTY_SORT_FIELD = "sortField";
    public static final String SORT_LISTENERS_PROPERTY = "sort";
    public static final String PROPERTY_SORT_ORDER = "sortDirection";
    public static final String PROPERTY_STRIPE_ROWS = "stripeRows";
    public static final String PROPERTY_SET_SIZE_COLUMNS_TO_GRID = "forceFit";
    public static final String COLUMN_ADDED = "columnAdd";
    public static final String COLUMN_REMOVED = "columnRemove";
    public static final String COLUMN_LISTENERS = "columnListeners";
    public static final String GROUP_ACTION = "group";
    public static final String PROPERTY_SHOW_CHECKBOX = "showCheckbox";
    public static final String PROPERTY_HIDE_HEADERS = "hideHeaders";
    public static final String PROPERTY_EDITCELLCONTENTS = "editcellcontents";
    public static final String PROPERTY_MODEL = "model";
    public static final String PROPERTY_HAS_HEADER_CONTEXT_MENU = "hasHeaderContextMenu";
    public static final String PROPERTY_HAS_ROW_CONTEXT_MENU = "hasRowContextMenu";
    public static final String PROPERTY_HAS_CELL_CONTEXT_MENU = "hasCellContextMenu";
    public static final String PROPERTY_LOADING_MESSAGE = "loadingMsg";
    public static final String PROPERTY_ALLOW_GROUPING = "allowGrouping";
    public static final String PROPERTY_ALLOW_COLUMN_MOVE = "allowColumnMove";
    public static final String PROPERTY_FORCE_FIT = "forceFit";
    public static final String PROPERTY_AUTO_EXPAND_COLUMN = "autoExpandColumn";
    public static final String PROPERTY_AUTO_FILL = "autoFill";    
    public static final String COLUMN_FILTER_ACTION = "columnfilterchanged";

    public static final String PROPERTY_SORT_ASC_TEXT = "sortAscText";
    public static final String PROPERTY_SORT_DESC_TEXT = "sortDescText";
    public static final String PROPERTY_COLUMNS_TEXT = "columnsText";
    public static final String PROPERTY_REMOVE_COLUMN_TEXT = "removeColumnText";
    public static final String PROPERTY_ADD_COLUMN_TEXT = "addColumnText";

    private static final transient Log LOG = LogFactory
            .getLog(GridPanel.class);

    private int pageSize;
    private ListSelectionModel selectionModel;
    private boolean suppressChangeNotifications;
    private boolean notifySelect = false;
    private GridCellRenderer gridCellRenderer = new DefaultGridCellRenderer();
    private Menu headerContextMenu;
    private Menu rowContextMenu;
    private Menu cellContextMenu;

    /**
     * Sets the showCheckbox property which if enabled shows checkboxes on the
     * GridPanel.
     */
    public void setShowCheckbox(Boolean showCheckbox) {
        set(PROPERTY_SHOW_CHECKBOX, showCheckbox);
        setSelectionMode(SelectionMode.MULTIPLE_INTERVAL_SELECTION);
    }

    public boolean getShowCheckbox() {
        Boolean b = (Boolean) get(PROPERTY_SHOW_CHECKBOX);
        return (b == null) ? false : b.booleanValue();
    }

    /**
     * Local handler for list selection events.
     */
    private ChangeListener listSelectionListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            if (!suppressChangeNotifications) {
                firePropertyChange(PROPERTY_SELECTION_CHANGED, null, selectionModel);
            }
        }
    };

    /**
     * Constructs a new grid panel.
     */
    public GridPanel() {
        super();
        setBorder(true);
        setSelectionModel(new DefaultListSelectionModel());
        setSelectionMode(SelectionMode.MULTIPLE_INTERVAL_SELECTION);
        setPageOffset(0);
        setContextMenuStatusAndChildren();
        setLoadingMsg("Loading...");
        setAllowGrouping(true);
        setAllowColumnMove(false);
        setForceFit(false);
        setAutoFill(false);

        // set the default text
        set(PROPERTY_SORT_ASC_TEXT, "Sort Ascending");
        set(PROPERTY_SORT_DESC_TEXT, "Sort Descending");
        set(PROPERTY_COLUMNS_TEXT, "Columns");
        set(PROPERTY_REMOVE_COLUMN_TEXT, "Remove Column");
        set(PROPERTY_ADD_COLUMN_TEXT, "Add Column");
    }

    /**
     * Constructs a new grid panel.
     * 
     * @param columnModel
     *            the column model.
     */
    public GridPanel(ColumnModel columnModel) {
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
    public GridPanel(ColumnModel columnModel, TableModel tableModel) {
        this(columnModel);
        setModel(tableModel);
        setContextMenuStatusAndChildren();
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
                        (String) getRenderProperty(PROPERTY_ACTION_COMMAND));
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
        return (String) get(PROPERTY_ACTION_COMMAND);
    }

    /**
     * Returns the column model for the table.
     * 
     * @return the column model for the table.
     */
    public ColumnModel getColumnModel() {
        return ((ColumnModel) get(PROPERTY_COLUMN_MODEL));
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
        return (Integer) get(PROPERTY_PAGE_OFFSET);
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
        String mode = (String) get(PROPERTY_SELECTION_MODE);
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
        return (String) get(PROPERTY_SORT_FIELD);
    }

    /**
     * Returns the order by which the field specified in
     * {@link #setSortField(java.lang.String)} should be sorted.
     * 
     * @return the sort order.
     */
    public boolean getSortOrder() {
        boolean ret = true;

        String sortString = (String) get(PROPERTY_SORT_ORDER);
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
        return (Boolean) get(PROPERTY_SET_SIZE_COLUMNS_TO_GRID);
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
        if (inputName.equals(PROPERTY_SELECTION_CHANGED)) {
            doSetSelectedIndices((int[]) inputValue);
            if (notifySelect) {
                fireActionEvent();
            }
        } else if (INPUT_ACTION.equals(inputName)) {
            fireActionEvent();
        } else if (PROPERTY_SORT_FIELD.equals(inputName)) {
            setSortField((String) inputValue);
        } else if (PROPERTY_SORT_ORDER.equals(inputName)) {
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
        } else if(COLUMN_FILTER_ACTION.equals(inputName)) {
        	fireColumnFilteredEvent((Integer)inputValue);
        } else if (COLUMN_ADDED.equals(inputName)) {
            fireColumnAddedEvent();
        } else if (COLUMN_REMOVED.equals(inputName)) {
            fireColumnRemovedEvent((Integer) inputValue);
        } else if (GROUP_ACTION.equals(inputName)) {
            doSort();
        } else if (PROPERTY_COLUMN_MODEL.equals(inputName)) {
            setColumnModel((ColumnModel)inputValue);
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
            Object maintainedSelection = null;
            if (getModel() instanceof SelectionMaintainerModel) {
                maintainedSelection = ((SelectionMaintainerModel)getModel()).getPersistentSelection(this);
            }
            ((SortableTableModel) getModel()).sort(columnIndices, ascending);

            if (getModel() instanceof SelectionMaintainerModel) {
                ((SelectionMaintainerModel)getModel()).applyPersistentSelection(this, maintainedSelection);
                firePropertyChange(PROPERTY_SELECTION_CHANGED, null, getSelectionModel());
            }
            suppressChangeNotifications = false;
            
            firePropertyChange(PROPERTY_MODEL_CHANGED, null, getModel()); // a

            // used for retrieving the size of the groups in the model
//            if (group != null && getModel() instanceof GroupingTableModel) {
//                ((GroupingTableModel)getModel()).doGrouping(true);
//            }
            //getSelectionModel().clearSelection();
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
        super.setBottomToolbar(toolbar);
        ((PagingToolbar) toolbar).initialise(getModel(), pageSize, this);
    }

    /**
     * Sets the column model for the table. WARNING: this forces a full re-draw
     * of the grid.
     * 
     * @param columnModel
     *            the column model for the table.
     */
    public void setColumnModel(final ColumnModel columnModel) {
        set(PROPERTY_COLUMN_MODEL, columnModel);
        // ensure listeners are notified of a column model change
        firePropertyChange(PROPERTY_COLUMN_MODEL, null, columnModel);
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
        set(PROPERTY_PAGE_OFFSET, pageOffset);
        tableChanged(null);
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
        doSetSelectedIndices(selectedIndices);
        firePropertyChange( PROPERTY_SELECTION_CHANGED, null, selectionModel);
    }
    
    private void doSetSelectedIndices(int[] selectedIndices) {
        // Temporarily suppress the Tables selection event notifier.
        suppressChangeNotifications = true;
        selectionModel.clearSelection();
        for (int i = 0; i < selectedIndices.length; ++i) {
            selectionModel.setSelectedIndex(selectedIndices[i], true);
        }
        // End temporary suppression.
        suppressChangeNotifications = false;
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
            set(PROPERTY_SELECTION_MODE, "S");
            getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            break;
        case SINGLE_INTERVAL_SELECTION:
            set(PROPERTY_SELECTION_MODE, "SI");
            getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_SELECTION);
            break;
        case MULTIPLE_INTERVAL_SELECTION:
            set(PROPERTY_SELECTION_MODE, "MI");
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
        firePropertyChange(PROPERTY_SELECTION_MODEL_CHANGED, oldValue, newValue);       
    }

    /**
     * Sets the forceFit property of the grid panel.
     * 
     * @param setSizeColumnsToGrid
     */
    public void setSetSizeColumnsToGrid(Boolean setSizeColumnsToGrid) {
        set(PROPERTY_SET_SIZE_COLUMNS_TO_GRID, setSizeColumnsToGrid);
    }

    /**
     * Sets the name of the field by which the data will be sorted, and sets the
     * sort sequence to ascending.
     * 
     * @param sortField
     *            the name of the field to sort by.
     */
    public void setSortField(String sortField) {
        set(PROPERTY_SORT_FIELD, sortField);
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
            set(PROPERTY_SORT_ORDER, "ASC");
        } else {
            set(PROPERTY_SORT_ORDER, "DESC");
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
        boolean tableChanging = tableModel != getModel();

        set(PROPERTY_MODEL, tableModel);
        tableModel.removeTableModelListener(this); // just in case they set the
        // same table model
        tableModel.addTableModelListener(this);

        if (tableChanging) { 
        	Iterator<ColumnConfiguration> iter = getColumnModel().iterator();
            setSortField("NONE");
            // Find the first column which has either ASC or DESC sortDirection
            // and set it to be the sort field
            boolean sortColFound = false;
            while (iter.hasNext()) {
                ColumnConfiguration col = iter.next();
                if(col.getSortDirection()!=null && !"NONE".equals(col.getSortDirection())) {
                    setSortField(col.getDataIndex());
                    setSortAscending("ASC".equals(col.getSortDirection()));
                    sortColFound=true;
                }
                else if (col.getSortDirection()==null || sortColFound==true) {
                	// Once sort column found set all other columns to be NONE sorted
                	col.setSortDirection("NONE");
                }
            }
            // No column was found with ascending or descending sort direction,
            // so default to sort on the first column
            if("NONE".equals(getSortField())) {
                setSortField(getColumnModel().getColumn(0).getDataIndex());
                getColumnModel().getColumn(0).setSortDirection("ASC");
                setSortAscending(true);
            }
            if(isModelSortable()) {
                doSort();
            }
        }
        tableChanged(null); // always
        // force
        // change
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
        set(PROPERTY_EDITCELLCONTENTS, Boolean.valueOf(editCells));
    }

    /**
     * Whether the grid panel should enable editing of the contents of the table
     * cells.
     * 
     * @return
     */
    public boolean getEditCellContents() {
        return Boolean.TRUE.equals(get(PROPERTY_EDITCELLCONTENTS));
    }

    /**
     * Forces a client-side refresh of the table when the table model changes.
     */
    public void tableChanged(TableModelEvent e) {
        if (!suppressChangeNotifications) {
            //getSelectionModel().clearSelection();
            firePropertyChange(PROPERTY_SELECTION_CHANGED, null, selectionModel);
            firePropertyChange(PROPERTY_MODEL_CHANGED, null, getModel()); // a
            // bodge
            // but
            // we're
            // not
            // interested
            // in the
            // old and
            // new
            // values
            // anyway
        }
    }

    public GridCellRenderer getGridCellRenderer() {
        return gridCellRenderer;
    }

    public void setGridCellRenderer(GridCellRenderer gridCellRenderer) {
        this.gridCellRenderer = gridCellRenderer;
    }

    public void addColumnListener(ColumnListener l) {
        getEventListenerList().addListener(ColumnListener.class, l);
        // Notification of action listener changes is provided due to
        // existence of hasActionListeners() method.
        firePropertyChange(COLUMN_LISTENERS, null, getEventListenerList().getListeners(ColumnListener.class));
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
    
    private void fireColumnFilteredEvent(Integer columnIndex) {
    	FilterColumnEvent fce = null;
    	if(fce == null) {
    		fce = new FilterColumnEvent(this, columnIndex.intValue());
    	}
    	
    	for (EventListener listener : getEventListenerList().getListeners(
                ColumnListener.class)) {    		
            ((ColumnListener) listener).filterColumn(fce);
    	}
    }

    /**
     * Returns whether the headers of the grid are currently hidden
     * 
     * @return
     */
    public boolean getHideHeaders() {
        Boolean b = (Boolean) get(PROPERTY_HIDE_HEADERS);
        return Boolean.TRUE.equals(b);
    }

    public void setHideHeaders(Boolean b) {
        set(PROPERTY_HIDE_HEADERS, b);
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
        set(PROPERTY_HAS_CELL_CONTEXT_MENU, Boolean.valueOf(cellContextMenu != null));
        set(PROPERTY_HAS_ROW_CONTEXT_MENU, Boolean.valueOf(rowContextMenu != null));
        set(PROPERTY_HAS_HEADER_CONTEXT_MENU, Boolean.valueOf(headerContextMenu != null));
        
        for (Component c : getComponents()) {
            remove(c);
        }
        
        if (cellContextMenu != null) {
            add(cellContextMenu);
        }
        if (rowContextMenu != null) {
            add(rowContextMenu);
        }
        if (headerContextMenu != null) {
            add(headerContextMenu);
        }
        
        if (getToolbar() != null)
            add(getToolbar());
        if (getBottomToolbar() != null)
            add(getBottomToolbar());
    }

    public void setStripeRows( boolean stripeRows) {
        set( PROPERTY_STRIPE_ROWS, stripeRows);
    }
    
    public boolean getStripeRows() {
        return (Boolean) get( PROPERTY_STRIPE_ROWS);
    }

    public String getLoadingMsg() {
        return (String)get(PROPERTY_LOADING_MESSAGE);
    }

    public void setLoadingMsg(String loadingMsg) {
        set(PROPERTY_LOADING_MESSAGE, loadingMsg);
    }

    public boolean getAllowGrouping() {
        return Boolean.TRUE.equals(get(PROPERTY_ALLOW_GROUPING));
    }
    
    public void setAllowGrouping(boolean allowGrouping) {
        set(PROPERTY_ALLOW_GROUPING, Boolean.valueOf(allowGrouping));
    }
    
    

    public boolean getAllowColumnMove() {
		return Boolean.TRUE.equals(get(PROPERTY_ALLOW_COLUMN_MOVE));
	}
    
    public void setAllowColumnMove(boolean allowColumnMove) {
        set(PROPERTY_ALLOW_COLUMN_MOVE, Boolean.valueOf(allowColumnMove));
    }
    
    public boolean getForceFit() {
        return Boolean.TRUE.equals(get(PROPERTY_FORCE_FIT));
    }

    public void setForceFit(boolean forceFit) {
        set(PROPERTY_FORCE_FIT, Boolean.valueOf(forceFit));
    }
    
    public String getAutoExpandColumn() {
    	return (String) get(PROPERTY_AUTO_EXPAND_COLUMN);
    }
    
    public void setAutoExpandColumn(String columnId) {
    	set(PROPERTY_AUTO_EXPAND_COLUMN, columnId);
    }
    
    public boolean getAutoFill() {
        return Boolean.TRUE.equals(get(PROPERTY_AUTO_FILL));
    }

    public void setAutoFill(boolean autoFill) {
        set(PROPERTY_AUTO_FILL, Boolean.valueOf(autoFill));
    }
    
    @Override
    public void validate() {
        super.validate();
        if (getColumnModel() != null && getModel() != null && getModel().getRowCount() > 0) {
            List<String> colModelNames = new ArrayList<String>();
            for (ColumnConfiguration cc : getColumnModel()) {
                colModelNames.add(cc.getDataIndex());
            }
            
            List<String> tableModelNames = new ArrayList<String>();
            for (int i = 0; i < getModel().getColumnCount(); i++) {
                tableModelNames.add(getModel().getColumnName(i));
            }
            
            if (!tableModelNames.containsAll(colModelNames)) {
                throw new IllegalStateException("The table model does not contain all of the identifiers of the column model; the column model data indexes are [" + colModelNames + "], the the table model column names are [" + tableModelNames + "]");
            }
            if (!colModelNames.containsAll(tableModelNames)) {
                LOG.warn("The table model contain all of the identifiers of the column model and some extras (more data being sent over network than required!); the column model data indexes are [" + colModelNames + "], the the table model column names are [" + tableModelNames + "]");
            }
        }
    }
}

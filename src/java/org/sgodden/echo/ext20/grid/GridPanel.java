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

import java.util.EventListener;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.ChangeEvent;
import nextapp.echo.app.event.ChangeListener;
import nextapp.echo.app.event.TableModelEvent;
import nextapp.echo.app.event.TableModelListener;
import nextapp.echo.app.list.DefaultListSelectionModel;
import nextapp.echo.app.list.ListSelectionModel;
import nextapp.echo.app.table.TableModel;

import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.SelectionMode;
import org.sgodden.echo.ext20.Toolbar;
import org.sgodden.ui.models.BackingObjectDataModel;
import org.sgodden.ui.models.SortableTableModel;

/**
 * An ext GridPanel. It uses swing table models, since these provide a complete
 * model API, and it is portable since it is shipped with the JVM. An adapter
 * takes care of converting these into ext stores.
 * <p/>
 * Code example: <pre class="code"> List<ColumnConfiguration> cols = new
 * ArrayList<ColumnConfiguration>(); cols.add(new ColumnConfiguration("User ID",
 * "userid")); cols.add(new ColumnConfiguration("Name", "name")); ColumnModel
 * columnModel = new ColumnModel(cols);
 * 
 * TableModel model = new DefaultTableModel( data, // simple Object[][] of your
 * data new String[]{"id", "userid", "name", "date"});
 * 
 * gridPanel = new GridPanel(columnModel, model);
 * 
 * gridPanel.addActionListener(new ActionListener(){ public void
 * actionPerformed(ActionEvent e) { Object[] data = gridPanel.getSelectedRow();
 * ... } }); </pre>
 * 
 * @author sgodden
 * 
 */
@SuppressWarnings( { "serial" })
public class GridPanel extends Panel implements TableModelListener,
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

    private TableModel tableModel;
    private int pageSize;
    private ListSelectionModel selectionModel;
    private boolean suppressChangeNotifications;
    private boolean notifySelect = false;
    private int[] selectedIndices;
    private GridCellRenderer gridCellRenderer = new DefaultGridCellRenderer();
    
	/**
	 * Sets the showCheckbox property which if enabled shows checkboxes on the
	 * GridPanel.
	 */
	public void setShowCheckbox(Boolean showCheckbox) {
		set(SHOW_CHECKBOX, showCheckbox);
	}

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
    public GridPanel() {
        super();
        setBorder(true);
        setSelectionMode(SelectionMode.MULTIPLE_INTERVAL_SELECTION);
        setSelectionModel(new DefaultListSelectionModel());
        setPageOffset(0);
        setComplexProperty(COLUMN_MODEL_PROPERTY, true);
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
        return selectedIndices;
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
     * Convenience method to return the selected backing object in the case that
     * the model implements {@link BackingObjectDataModel}.
     * 
     * @return
     */
    public Object getSelectedBackingObject() {
        if (!(tableModel instanceof BackingObjectDataModel)) {
            throw new IllegalStateException("Backing object does not"
                    + " implement BackingObjectDataModel");
        }
        Object ret = null;
        if (selectionModel.getMinSelectedIndex() > -1) {
            ret = ((BackingObjectDataModel) tableModel)
                    .getBackingObjectForRow(selectionModel
                            .getMinSelectedIndex());
        }
        return ret;
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
    public TableModel getTableModel() {
        return tableModel;
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
        return (getTableModel() instanceof SortableTableModel);
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
        if (getTableModel() instanceof SortableTableModel) {
            ColumnModel colModel = getColumnModel();
            String[] columnIndices = new String[colModel.getColumnCount()];
            boolean[] ascending = new boolean[colModel.getColumnCount()];
            ColumnConfiguration group = null;
            for (ColumnConfiguration cc : colModel) {
                if (cc.getGrouping())
                    group = cc;;
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

                ascending[index] = "ASCENDING".equals(cc
                        .getSortDirection())
                        || "ASC".equals(cc.getSortDirection());
            }
            ((SortableTableModel) getTableModel()).sort(columnIndices,
                    ascending);
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
        if (getTableModel() == null || pageSize == 0) {
            throw new IllegalStateException("Initialise the model and"
                    + " page size before setting the paging toolbar");
        }
        super.setBottomToolbar(toolbar);
        ((PagingToolbar) toolbar).initialise(getTableModel(), pageSize, this);
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
    private void setSelectedIndices(int[] selectedIndices) {
        this.selectedIndices = selectedIndices;
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
            set(SELECTION_MODE, "S");
            break;
        case SINGLE_INTERVAL_SELECTION:
            set(SELECTION_MODE, "SI");
            break;
        case MULTIPLE_INTERVAL_SELECTION:
            set(SELECTION_MODE, "MI");
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
     * Sets the data store from a Swing {@link TableModel}.
     * 
     * @param tableModel
     *            the table model.
     */
    public void setModel(TableModel tableModel) {
        if (tableModel == null) {
            throw new IllegalArgumentException("table model may not be null");
        }

        this.tableModel = tableModel;
        tableModel.removeTableModelListener(this); // just in case they set the
        // same table model
        tableModel.addTableModelListener(this);

        tableChanged(null); // always
        // force
        // change
    }

    boolean firingTableChanged = false;
    /**
     * Forces a client-side refresh of the table when the table model changes.
     */
    public void tableChanged(TableModelEvent e) {
        if (!firingTableChanged) {
            firingTableChanged = true;
            firePropertyChange(MODEL_CHANGED_PROPERTY, null, tableModel); // a bodge
            // but
            // we're
            // not
            // interested
            // in the
            // old and
            // new
            // values
            // anyway
            firingTableChanged = false;
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
     * @return
     */
    public boolean getHideHeaders() {
        Boolean b = (Boolean)get(HIDE_HEADERS);
        return Boolean.TRUE.equals(b);
    }
    
    public void setHideHeaders(Boolean b) {
        set(HIDE_HEADERS, b);
    }

}
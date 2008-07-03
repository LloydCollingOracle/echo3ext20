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

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.SortOrder;
import org.sgodden.echo.ext20.Toolbar;
import org.sgodden.echo.ext20.ToolbarSeparator;
import org.sgodden.echo.ext20.ToolbarTextItem;
import org.sgodden.echo.ext20.models.SortableTableModel;

/**
 * An ext GridPanel.  It uses swing table models, since these provide a complete
 * model API, and it is portable since it is shipped with the JVM.  An adapter takes
 * care of converting these into ext stores.
 * <p/>
 * Code example:
 * <pre class="code">
List<ColumnConfiguration> cols = new ArrayList<ColumnConfiguration>();
cols.add(new ColumnConfiguration("User ID", "userid"));
cols.add(new ColumnConfiguration("Name", "name"));
ColumnModel columnModel = new ColumnModel(cols);

TableModel model = new DefaultTableModel(
    data, // simple Object[][] of your data
    new String[]{"id", "userid", "name", "date"});

gridPanel = new GridPanel(columnModel, model);

gridPanel.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e) {
    	Object[] data = gridPanel.getSelectedRow();
    	...
    }
});
 * </pre>
 * 
 * @author sgodden
 *
 */
@SuppressWarnings({"serial","unchecked"})
public class GridPanel
        extends Panel 
        implements TableModelListener {
    
    public static final String ACTION_COMMAND_PROPERTY = "actionCommand";
    public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";
    public static final String COLUMN_MODEL_PROPERTY = "columnModel";
    public static final String GROUP_FIELD_PROPERTY="groupField";
    public static final String INPUT_ACTION = "action";
    public static final String MODEL_CHANGED_PROPERTY="model";
    public static final String PAGE_OFFSET_PROPERTY="pageOffset";
    public static final String SELECTION_CHANGED_PROPERTY = "selection";
    public static final String SELECTION_MODEL_CHANGED_PROPERTY = "selectionModel";
    public static final String SORT_ACTION = "sort";
    public static final String SORT_FIELD_PROPERTY = "sortField";
    public static final String SORT_LISTENERS_PROPERTY = "sort";
    public static final String SORT_ORDER_PROPERTY = "sortDirection"; 
    
    private TableModel tableModel;
    private int pageSize;
    private ListSelectionModel selectionModel;
    private boolean suppressChangeNotifications;

    /**
     * Local handler for list selection events.
     */
    private ListSelectionListener listSelectionListener = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
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
        setSelectionModel(new DefaultListSelectionModel());
        setPageOffset(0);
    }
    
    /**
     * Constructs a new grid panel.
     * @param columnModel the column model.
     */
    public GridPanel(ColumnModel columnModel) {
    	this();
        setColumnModel(columnModel);
    }

    /**
     * Constructs a new grid panel.
     * @param columnModel the column model.
     * @param tableModel the table model.
     */
    public GridPanel(ColumnModel columnModel, TableModel tableModel) {
        this(columnModel);
        setTableModel(tableModel);
    }

    /**
     * Adds an <code>ActionListener</code> to the <code>Table</code>.
     * <code>ActionListener</code>s will be invoked when the user
     * selects a row.
     *
     * @param l the <code>ActionListener</code> to add
     */
    public void addActionListener(ActionListener l) {
        getEventListenerList().addListener(ActionListener.class, l);
        // Notification of action listener changes is provided due to
        // existence of hasActionListeners() method.
        firePropertyChange(ACTION_LISTENERS_CHANGED_PROPERTY, null, l);
    }

    private void addPagingToolbar() {
        
        Toolbar toolbar = new PagingToolbar(
                getTableModel(),
                pageSize,
                new PagingToolbarClient() {

            public void setPageOffset(int pageOffset) {
                GridPanel.this.setPageOffset(pageOffset);
            }
        });
        setBottomToolbar(toolbar);
    }

    /**
     * Fires an action event to all listeners.
     */
    private void fireActionEvent() {
        if (!hasEventListenerList()) {
            return;
        }
        EventListener[] listeners = getEventListenerList().getListeners(ActionListener.class);
        ActionEvent e = null;
        for (int i = 0; i < listeners.length; ++i) {
            if (e == null) {
                e = new ActionEvent(this, (String) getRenderProperty(ACTION_COMMAND_PROPERTY));
            }
            ((ActionListener) listeners[i]).actionPerformed(e);
        }
    }

    /**
     * Returns the action command which will be provided in
     * <code>ActionEvent</code>s fired by this
     * <code>Table</code>.
     *
     * @return the action command
     */
    public String getActionCommand() {
        return (String) getComponentProperty(ACTION_COMMAND_PROPERTY);
    }
    
    /**
     * Returns the column model for the table.
     * @return the column model for the table.
     */
    public ColumnModel getColumnModel() {
        return ((ColumnModel)getComponentProperty(COLUMN_MODEL_PROPERTY));
    }

    /**
     * Returns the offset to the current page, in the case that the
     * page size has been set.
     * @return the offset to the current page.
     */
    public int getPageOffset() {
        return (Integer) getComponentProperty(PAGE_OFFSET_PROPERTY);
    }

    /**
     * Returns the page size, or 0 if the table is not paged.
     * @return the page size, or 0 if the table is not paged.
     */
    public int getPageSize() {
       return pageSize;
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
     * @return the name of the field by which the data should be sorted.
     */
    public String getSortField() {
        return (String) getComponentProperty(SORT_FIELD_PROPERTY);
    }

    /**
     * Returns the order by which the field specified in
     * {@link #setSortField(java.lang.String)} should be sorted.
     * @return the sort order.
     */
    public SortOrder getSortOrder() {
        SortOrder ret = null;

        String sortString = (String) getComponentProperty(SORT_ORDER_PROPERTY);
        if ("ASC".equals(sortString)) {
            ret = SortOrder.ASCENDING;
        }
        else if ("DESC".equals(sortString)) {
            ret = SortOrder.DESCENDING;
        }

        return ret;
    }
    
    /**
     * Returns the grid's table model.
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
     * @see nextapp.echo.app.Component#processInput(java.lang.String, java.lang.Object)
     */
    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (inputName.equals(SELECTION_CHANGED_PROPERTY)) {
            setSelectedIndices((int[]) inputValue);
        }
        else if (INPUT_ACTION.equals(inputName)) {
            fireActionEvent();
        }
        else if (SORT_FIELD_PROPERTY.equals(inputName)) {
            setSortField((String)inputValue);
        }
        else if (SORT_ORDER_PROPERTY.equals(inputName)) {
            String value = (String) inputValue;
            if (value.equals("ASC")) {
                setSortOrder(SortOrder.ASCENDING);
            }
            else if (value.equals("DESC")) {
                setSortOrder(SortOrder.DESCENDING);
            }
            else {
                throw new IllegalArgumentException("Unknown sort order: "
                        + value);
            }
        }
        else if (SORT_ACTION.equals(inputName)) {
            if (getTableModel() instanceof SortableTableModel) {
                int columnIndex = getColumnModel()
                        .getIndexForDataIndex(getSortField());
                ((SortableTableModel) getTableModel()).sort(
                        columnIndex, getSortOrder());
                getSelectionModel().clearSelection();
            }
            else {
                throw new IllegalStateException("Request to sort table made, " +
                        "but model is not sortable");
            }
        }
    }

    /**
     * Removes the specified action listener.
     * @param l the listener to remove.
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
     * Sets the column model for the table.
     * @param columnModel the column model for the table.
     */
    public void setColumnModel(ColumnModel columnModel) {
        setComponentProperty(COLUMN_MODEL_PROPERTY, columnModel);
    }

    /**
     * Sets the name of the column in the data model by which to
     * group the table.
     * @param groupField the name of the column by which to group.
     */
    public void setGroupField(String groupField) {
        setComponentProperty(GROUP_FIELD_PROPERTY, groupField);
    }

    /**
     * Sets the offset to the first record in the model that is being shown
     * in the grid view.
     * @param pageOffset the offset to the first record in the model.
     */
    private void setPageOffset(int pageOffset) {
        setComponentProperty(PAGE_OFFSET_PROPERTY, pageOffset);
        tableChanged(null);
    }

    /**
     * Sets the size of the page that should be displayed when not displaying
     * the entire table model at once.  A size of 0 means that the table
     * should not be paged.
     * @param pageSize the size of the page, or 0 to not page.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;

        if (pageSize > 0 && getTableModel() != null) {
            addPagingToolbar();
        }
    }
    
    /**
     * Sets the data store from a Swing {@link TableModel}.
     * @param tableModel the table model.
     */
    public void setTableModel(TableModel tableModel) {
    	if (tableModel == null) {
    		throw new IllegalArgumentException("table model may not be null");
    	}
    	
    	this.tableModel = tableModel;
    	tableModel.removeTableModelListener(this); // just in case they set the same table model
    	tableModel.addTableModelListener(this);

        if (pageSize > 0) {
            if (getBottomToolbar() == null) {
                addPagingToolbar();
            } else {
                ((PagingToolbar) getBottomToolbar()).setTableModel(tableModel);
            }
        }

    	firePropertyChange(MODEL_CHANGED_PROPERTY, null, tableModel); // always force change
    }


    /**
     * Selects only the specified row indices.
     * 
     * @param selectedIndices the indices to select
     */
    private void setSelectedIndices(int[] selectedIndices) {
        // Temporarily suppress the Tables selection event notifier.
        suppressChangeNotifications = true;
        selectionModel.clearSelection();
        for (int i = 0; i < selectedIndices.length; ++i) {
            selectionModel.addSelectionInterval(selectedIndices[i],
                    selectedIndices[i]);
        }
        // End temporary suppression.
        suppressChangeNotifications = false;
        firePropertyChange(SELECTION_CHANGED_PROPERTY, null, selectedIndices);
    }

    /**
     * Sets the row selection model.
     * The selection model may not be null.
     *
     * @param newValue the new selection model
     */
    public void setSelectionModel(ListSelectionModel newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("Selection model may not be null.");
        }
        ListSelectionModel oldValue = selectionModel;
        if (oldValue != null) {
            oldValue.removeListSelectionListener(listSelectionListener);
        }
        newValue.addListSelectionListener(listSelectionListener);
        selectionModel = newValue;
        firePropertyChange(SELECTION_MODEL_CHANGED_PROPERTY, oldValue, newValue);
    }
    /**
     * Sets the name of the field by which the data will be sorted.
     * @param sortField the name of the field to sort by.
     */
    public void setSortField(String sortField) {
        setComponentProperty(SORT_FIELD_PROPERTY, sortField);
    }
    
    /**
     * Sets the order by which the field specified in
     * {@link #setSortField(java.lang.String)} should be sorted.
     * @param sortOrder the sort order.
     */
    public void setSortOrder(SortOrder sortOrder) {
        switch (sortOrder) {
            case ASCENDING:
                setComponentProperty(SORT_ORDER_PROPERTY, "ASC");
                break;
            case DESCENDING:
                setComponentProperty(SORT_ORDER_PROPERTY, "DESC");
                break;
            default:
                throw new Error("Invalid sort order: " + sortOrder);
        }
    }

    /**
     * Forces a client-side refresh of the table when the table model changes.
     */
    public void tableChanged(TableModelEvent e) {
        firePropertyChange(MODEL_CHANGED_PROPERTY, null, tableModel); // a bodge but we're not interested in the old and new values anyway
    }

}
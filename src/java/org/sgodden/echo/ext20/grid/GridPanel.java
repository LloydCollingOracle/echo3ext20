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
import nextapp.echo.app.list.DefaultListSelectionModel;
import nextapp.echo.app.list.ListSelectionModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.data.SimpleStore;

/**
 * An ext GridPanel.
 * <p/>
 * Code example:
 * <pre class="code">
List<ColumnConfiguration> cols = new ArrayList<ColumnConfiguration>();
cols.add(new ColumnConfiguration("User ID", "userid"));
cols.add(new ColumnConfiguration("Name", "name"));
ColumnModel columnModel = new ColumnModel(cols);

SimpleStore store = new SimpleStore(
    data, // simple Object[][] of your data
    0,
    new String[]{"id", "userid", "name", "date"});

gridPanel = new GridPanel(columnModel, store);

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
public class GridPanel
        extends Panel {

    private static final transient Log log = LogFactory.getLog(GridPanel.class);
    public static final String PROPERTY_COLUMN_MODEL = "columnModel";
    public static final String PROPERTY_SIMPLE_STORE = "simpleStore";
    public static final String PROPERTY_ACTION_COMMAND = "actionCommand";
    public static final String INPUT_ACTION = "action";
    public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";
    public static final String SELECTION_CHANGED_PROPERTY = "selection";
    public static final String SELECTION_MODEL_CHANGED_PROPERTY = "selectionModel";
    private ListSelectionModel selectionModel;
    private boolean suppressChangeNotifications;
    
    /**
     * Constructs a new grid panel.
     * @param columnModel the column model.
     */
    public GridPanel(ColumnModel columnModel) {
    	super();
        setColumnModel(columnModel);
        setSelectionModel(new DefaultListSelectionModel());
    }

    /**
     * Constructs a new grid panel.
     * @param columnModel the column model.
     * @param simpleStore the data store.
     */
    public GridPanel(ColumnModel columnModel, SimpleStore simpleStore) {
        this(columnModel);
        setSimpleStore(simpleStore);
    }

    /**
     * Sets the column model for the table.
     * @param columnModel the column model for the table.
     */
    public void setColumnModel(ColumnModel columnModel) {
        setProperty(PROPERTY_COLUMN_MODEL, columnModel);
    }

    /**
     * Sest the data store for the table.
     * @param simpleStore the data store for the table.
     */
    public void setSimpleStore(SimpleStore simpleStore) {
        setProperty(PROPERTY_SIMPLE_STORE, simpleStore);
    }

    /**
     * Returns the data store for the table.
     * @return the data store for the table.
     */
    public SimpleStore getSimpleStore() {
        return (SimpleStore) getProperty(PROPERTY_SIMPLE_STORE);
    }

    /**
     * Returns the action command which will be provided in 
     * <code>ActionEvent</code>s fired by this 
     * <code>Table</code>.
     * 
     * @return the action command
     */
    public String getActionCommand() {
        return (String) getProperty(PROPERTY_ACTION_COMMAND);
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

    /**
     * Returns the row selection model.
     * 
     * @return the selection model
     */
    public ListSelectionModel getSelectionModel() {
        return selectionModel;
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
            oldValue.removeChangeListener(changeHandler);
        }
        newValue.addChangeListener(changeHandler);
        selectionModel = newValue;
        firePropertyChange(SELECTION_MODEL_CHANGED_PROPERTY, oldValue, newValue);
    }

    /**
     * Determines the any <code>ActionListener</code>s are registered.
     * 
     * @return true if any action listeners are registered
     */
    public boolean hasActionListeners() {
        return getEventListenerList().getListenerCount(ActionListener.class) != 0;
    }

    /**
     * @see nextapp.echo.app.Component#processInput(java.lang.String, java.lang.Object)
     */
    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (inputName.equals(SELECTION_CHANGED_PROPERTY)) {
            setSelectedIndices((int[]) inputValue);
        } else if (INPUT_ACTION.equals(inputName)) {
            fireActionEvent();
        }
    }

    /**
     * Selects only the specified row indices.
     * 
     * @param selectedIndices the indices to select
     */
    private void setSelectedIndices(int[] selectedIndices) {
        // Temporarily suppress the Tables selection event notifier.
        suppressChangeNotifications = true;
        ListSelectionModel selectionModel = getSelectionModel();
        selectionModel.clearSelection();
        for (int i = 0; i < selectedIndices.length; ++i) {
            selectionModel.setSelectedIndex(selectedIndices[i], true);
        }
        // End temporary suppression.
        suppressChangeNotifications = false;
        firePropertyChange(SELECTION_CHANGED_PROPERTY, null, selectedIndices);
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
                e = new ActionEvent(this, (String) getRenderProperty(PROPERTY_ACTION_COMMAND));
            }
            ((ActionListener) listeners[i]).actionPerformed(e);
        }
    }
    /**
     * Local handler for list selection events.
     */
    private ChangeListener changeHandler = new ChangeListener() {

        /** Serial Version UID. */
        private static final long serialVersionUID = 20070101L;

        /**
         * @see nextapp.echo.app.event.ChangeListener#stateChanged(nextapp.echo.app.event.ChangeEvent)
         */
        public void stateChanged(ChangeEvent e) {
            if (!suppressChangeNotifications) {
                firePropertyChange(SELECTION_CHANGED_PROPERTY, null, null);
            }
        }
    };
}

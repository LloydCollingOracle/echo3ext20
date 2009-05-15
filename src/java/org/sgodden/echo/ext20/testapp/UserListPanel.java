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
package org.sgodden.echo.ext20.testapp;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.ChangeEvent;
import nextapp.echo.app.event.ChangeListener;
import nextapp.echo.app.list.DefaultListModel;
import nextapp.echo.app.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.CheckboxField;
import org.sgodden.echo.ext20.ComboBox;
import org.sgodden.echo.ext20.Menu;
import org.sgodden.echo.ext20.MenuItem;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.SelectionMode;
import org.sgodden.echo.ext20.SplitButton;
import org.sgodden.echo.ext20.TextArea;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.Toolbar;
import org.sgodden.echo.ext20.ToolbarButton;
import org.sgodden.echo.ext20.ToolbarFill;
import org.sgodden.echo.ext20.ToolbarSeparator;
import org.sgodden.echo.ext20.ToolbarTextItem;
import org.sgodden.echo.ext20.grid.ColumnConfiguration;
import org.sgodden.echo.ext20.grid.ColumnModel;
import org.sgodden.echo.ext20.grid.DefaultColumnConfiguration;
import org.sgodden.echo.ext20.grid.DefaultColumnModel;
import org.sgodden.echo.ext20.grid.DefaultSortableTableModel;
import org.sgodden.echo.ext20.grid.GridPanel;
import org.sgodden.echo.ext20.grid.PagingToolbar;
import org.sgodden.echo.ext20.layout.FitLayout;

/**
 * A panel which displays a list of users.
 * 
 * @author sgodden
 */
@SuppressWarnings({"serial"})
public class UserListPanel
        extends Panel {

    private static final transient Log LOG = LogFactory.getLog(UserListPanel.class);
    private GridPanel userGridPanel;
    private int startIndex = 1;
    private int rows = 35;
    
    private DefaultSortableTableModel tableModel;
    
    private ToolbarButton tbButton;
    private static ImageReference ref1 = new ResourceImageReference(
        "/resources/images/fam/icons/cog.png");
    private static ImageReference ref2 = new ResourceImageReference(
        "/resources/images/fam/icons/cog_error.png");
    private ImageReference currentImageRef;

    public UserListPanel() {
        super(new FitLayout());
        setBorder(false);
        setRenderId("userList");
        setTitle("User list");

        add(makeGridPanel());

        addKeyPressListener("enter", new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                LOG.info("Enter key was pressed");
            }
        });
        
        SplitButton sb = new SplitButton("test", new ResourceImageReference(
                "/resources/images/fam/icons/cog.png"));
        addButton(sb);
        
        Button changeButton = new Button("Change the data model");
        changeButton.setIcon(new ResourceImageReference(
                "/resources/images/fam/icons/cog.png"));
        addButton(changeButton);
        changeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                startIndex += 10;
                rows +=10;
                userGridPanel.setModel(makeTableModel());
            }});
        
        Button changeIconButton = new Button("Change the button icon");
        addButton(changeIconButton);
        changeIconButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                if (ref1 == currentImageRef) {
                    tbButton.setIcon(ref2);
                    currentImageRef = ref2;
                }
                else {
                    tbButton.setIcon(ref1);
                    currentImageRef = ref1;
                }
            }});
    }

    /**
     * Returns the underlying grid panel.
     * @return
     */
    public GridPanel getGridPanel() {
        return userGridPanel;
    }
    
    private GridPanel makeGridPanel() {
        List<ColumnConfiguration> cols = new ArrayList<ColumnConfiguration>();
        
        cols.add(new DefaultColumnConfiguration("User ID", 200, true, "userid", false));
        ColumnConfiguration nameCol = new DefaultColumnConfiguration("Name",
                100, true, "name", false) {
            @Override
            public Class getColumnClass() {
                return String.class;
            }
        };
        nameCol.setEditorComponent(new TextField());
        cols.add(nameCol);

        ColumnConfiguration adminCol = new DefaultColumnConfiguration(
                "Is Admin?", 200, true, "isadmin", false) {
            @Override
            public Class getColumnClass() {
                return Boolean.class;
            }
        };
        adminCol.setEditorComponent(new CheckboxField());
        cols.add(adminCol);
        ColumnConfiguration sexCol = new DefaultColumnConfiguration( "Sex", 200, true, "sex", false) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
        };
        sexCol.setEditorComponent( new ComboBox( new DefaultListModel( new String[]{ "male", "female"})));
        cols.add( sexCol);
        ColumnConfiguration mottoCol = new DefaultColumnConfiguration( "Motto", 200, true, "motto", false) {
        	@Override
        	public Class<?> getColumnClass() {
        		return String.class;
        	}
        };
        mottoCol.setEditorComponent( new TextArea());
        cols.add( mottoCol);
        ColumnModel columnModel = new DefaultColumnModel(cols);
        
        userGridPanel = new GridPanel(columnModel);
        userGridPanel.setPageSize(20);
        userGridPanel.setModel(makeTableModel());
        userGridPanel.setToolbar(makeToolbar());
        userGridPanel.setBottomToolbar(new PagingToolbar());
        /*
         * Don't bother with grouping for now since the models
         * don't play properly with it, rendering it effectively useless
         */
        //userGridPanel.setGroupField("role");
        userGridPanel.setSortField("userid");
        // ensure list selections are notified immediately
        userGridPanel.setNotifySelect(true);
        // don't allow multiple row selection
        userGridPanel.setSelectionMode(SelectionMode.SINGLE_SELECTION);
        //
        userGridPanel.setShowCheckbox(true);
        userGridPanel.setEditCellContents(true);
        
        userGridPanel.getSelectionModel().addChangeListener(
                new ChangeListener(){
                    public void stateChanged(ChangeEvent e) {
                        LOG.info("A row was selected or deselected");
                    }}
        );
        
        return userGridPanel;
        
    }
    
    private TableModel makeTableModel() {
        /*
         * This is a fairly stupid example, where the data itself
         * is used as the backing data for each row.
         * This is purely to prove that the backing object implementation
         * is working.  In real life, the backing objects would most likely
         * be the domain objects themselves, or their ids.
         */
        Object[][] data = makeData();
        tableModel = new DefaultSortableTableModel(
                data,
                makeColumnNames());
        tableModel.sort(0, true);
        return tableModel;
    }
    
    private String[] makeColumnNames() {
        return new String[] {"userid", "name", "isadmin", "sex", "motto"};
    }

    /**
     * Adds a listener to be notifies when a row is actioned.
     *
     * @param listener the listener to be added.
     */
    public void addActionListener(ActionListener listener) {
        userGridPanel.addActionListener(listener);
    }

    /**
     * Returns the (only) selected row index of the grid.
     */
    public int getSelectedIndex() {
        return userGridPanel.getSelectionModel().getMinSelectedIndex();
    }

    /**
     * Returns the selected row of data.
     * @return the selected row of data.
     */
    public Object[] getSelectedRow() {
    	Object[] ret = new Object[tableModel.getColumnCount()];
    	for (int i = 0, j = tableModel.getColumnCount(); i < j; i++) {
    		ret[i] = tableModel.getValueAt(getSelectedIndex(), i);
    	}
        return ret;
    }

    /**
     * Makes dummy data for users.
     * @param startIndex the start index for the number placed into the text.
     * @return the dummy data.
     */
    private Object[][] makeData() {
        Object[][] data = new Object[rows][];

        for (int i = 0; i < data.length; i++) {
            Object[] row = new Object[5];
            row[0] = "User id  " + (startIndex + i);
            row[1] = "Name " + (startIndex + i);
            row[2] = Boolean.valueOf(i % 2 == 0);
            row[3] = i % 2 == 0 ? "male" : "female";
            row[4] = "A long long long words, \n with many many many \nlines";
            data[i] = row;
        }

        return data;
    }

    private Toolbar makeToolbar() {
        Toolbar ret = new Toolbar();

        tbButton = new ToolbarButton();
        tbButton.setIcon(ref1);
        currentImageRef = ref1;
        tbButton.setTooltip("Show configuration options");
        tbButton.setText("Configure");
        
        tbButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                LOG.info("Toolbar button was pressed");
            }
        });

        tbButton.setMenu(makeMenu());

        ToolbarButton button2 = new ToolbarButton("Button2");
        button2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                LOG.info("Button2 was pressed");
            }
        });

        ret.add(tbButton);
        ret.add(new ToolbarSeparator());
        ret.add(new ToolbarFill());
        ret.add(new ToolbarSeparator());
        ret.add(new ToolbarTextItem("Some text"));
        ret.add(button2);
        ret.add(new ToolbarSeparator());

        TextField tf = new TextField();
        tf.setEmptyText("Enter search criteria");
        ret.add(tf);
        
        ToolbarButton tbButton2 = new ToolbarButton("Menu 2");
        ret.add(tbButton2);
        tbButton2.setMenu(makeMenu());
        

        return ret;
    }

    private Menu makeMenu() {
        Menu ret = new Menu();

        MenuItem item1 = new MenuItem("Item 1");
        ret.add(item1);

        item1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                LOG.info("Menu item was clicked");
            }
        });

        return ret;
    }
}
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
import java.util.Date;
import java.util.List;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Menu;
import org.sgodden.echo.ext20.MenuItem;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.Toolbar;
import org.sgodden.echo.ext20.ToolbarButton;
import org.sgodden.echo.ext20.ToolbarFill;
import org.sgodden.echo.ext20.ToolbarSeparator;
import org.sgodden.echo.ext20.ToolbarTextItem;
import org.sgodden.echo.ext20.data.SimpleStore;
import org.sgodden.echo.ext20.grid.ColumnConfiguration;
import org.sgodden.echo.ext20.grid.ColumnModel;
import org.sgodden.echo.ext20.grid.GridPanel;
import org.sgodden.echo.ext20.layout.BorderLayout;
import org.sgodden.echo.ext20.layout.BorderLayoutData;
import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.TableLayout;

/**
 * A panel which displays a list of users.
 * 
 * @author sgodden
 */
public class UserListPanel
        extends Panel {

    private static final transient Log log = LogFactory.getLog(UserListPanel.class);
    private GridPanel userGridPanel;
    private Object[][] data = makeData();

    public UserListPanel() {
        super(new FitLayout());
        setBorder(false);
        setRenderId("userList");

        List<ColumnConfiguration> cols = new ArrayList<ColumnConfiguration>();
        cols.add(new ColumnConfiguration("User ID", "userid"));
        cols.add(new ColumnConfiguration("Name", "name"));
        ColumnModel columnModel = new ColumnModel(cols);

        SimpleStore store = new SimpleStore(
                data,
                0,
                new String[]{"id", "userid", "name", "date"});

        userGridPanel = new GridPanel(columnModel, store);
        userGridPanel.setToolbar(makeToolbar());
        add(userGridPanel);

        addKeyPressListener("enter", new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                log.info("Enter key was pressed");
            }
        });

    }

    /**
     * Adds a listener to be notifies when a row is actioned.
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
        return data[getSelectedIndex()];
    }

    /**
     * Makes dummy data for users.
     * @return the dummy data.
     */
    private Object[][] makeData() {
        int rows = 10;

        Object[][] ret = new Object[rows][];

        for (int i = 0; i < ret.length; i++) {
            Object[] row = new Object[4];
            row[0] = String.valueOf(i);
            row[1] = "User id asdasdasdasdasd " + i;
            row[2] = "Name asdasdasdasdasdasd " + i;
            row[3] = new Date();
            ret[i] = row;
        }

        return ret;
    }

    private Toolbar makeToolbar() {
        Toolbar ret = new Toolbar();

        ToolbarButton button = new ToolbarButton("Button");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                log.info("Toolbar button was pressed");
            }
        });

        button.setMenu(makeMenu());

        ToolbarButton button2 = new ToolbarButton("Button2");
        button2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                log.info("Button2 was pressed");
            }
        });

        ret.add(button);
        ret.add(new ToolbarSeparator());
        ret.add(new ToolbarFill());
        ret.add(new ToolbarSeparator());
        ret.add(new ToolbarTextItem("Some text"));
        ret.add(button2);
        ret.add(new ToolbarSeparator());

        TextField tf = new TextField();
        tf.setEmptyText("Enter search criteria");
        ret.add(tf);

        return ret;
    }

    private Menu makeMenu() {
        Menu ret = new Menu();

        MenuItem item1 = new MenuItem("Item 1");
        ret.add(item1);
        item1.setIconClass("icon-folder-add");

        item1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                log.info("Menu item was clicked");
            }
        });

        return ret;
    }
}

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

import java.util.Date;

import nextapp.echo.app.Button;
import nextapp.echo.app.CheckBox;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Label;
import nextapp.echo.app.RadioButton;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.grid.GridPanel;

/**
 * This is an alternative application content pane
 * whch demonstrates combined use of echo3 components
 * and ext components.
 * <p/>
 * FIXME - this is not up to date and does not currently work.
 * 
 * @author sgodden
 */
@SuppressWarnings({"serial"})
public class ApplicationContentPane2
        extends ContentPane implements ActionListener {

    private static final long serialVersionUID = 20080103L;
    private static final Log log = LogFactory.getLog(ApplicationContentPane2.class);
    /**
     * Disabled text field used for status feedback.
     */
    private TextField statusField;
    /**
     * Number of button clicks performed.
     */
    private int buttonClicks = 0;
    /**
     * Panel which toggles between user list and user edit
     */
    private SplitPane mainSplitPane;
    private SplitPane userListPanel;
    private GridPanel userGridPanel;
    private Grid userEditPanel;

    public ApplicationContentPane2() {
        super();
        //add(new HtmlPanel("<h1>HTML PANEL!!!</h1>"));
        //addViewport();
        addMainSplitPane();
    }
    
    private void addMainSplitPane() {
        
        mainSplitPane = new SplitPane(SplitPane.ORIENTATION_HORIZONTAL);
        mainSplitPane.setSeparatorPosition(new Extent(143));
        mainSplitPane.setSeparatorWidth(new Extent(2));
        mainSplitPane.add(createWestPanel());
        mainSplitPane.add(createUserListPanel());
        
        add(mainSplitPane);
    }

    /**
     * Creates a silly panel for the west region.
     * @return
     */
    private Column createWestPanel() {
        Column ret = new Column();
        ret.setRenderId("westPanel");
        
        Panel imagePanel = new Panel();
        imagePanel.setHtml(
                "<img src='http://demo.nextapp.com/echo3csjs/image/Logo.png'></img>");
        imagePanel.setRenderId("northImagePanel");
        ret.add(imagePanel);
        
        Panel titlePanel = new Panel();
        titlePanel.setHtml("Some navigation here");
        titlePanel.setRenderId("northTitlePanel");
        titlePanel.setPadding("5px");
        ret.add(titlePanel);

        return ret;
    }

    /**
     * Creates a panel which toggles between a list of users, and 
     * an edit panel for the selected user. 
     * @return
     */
    private SplitPane createUserListPanel() {
        final Object[][] data = makeData();

        userListPanel = createUserList(data);

        userGridPanel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // remove the list panel
                mainSplitPane.remove(userListPanel);
                // get the selected row of the data
                int selectedRow = userGridPanel.getSelectionModel()
                        .getMinSelectedIndex(); // only one row can be selected
                // create the form panel with that data
                createUserEditPanel(data[selectedRow]);
                mainSplitPane.add(userEditPanel);
            }
        });


        return userListPanel;
    }

    /**
     * Creates a panel which toggles between a list of users and an edit panel
     * for a particular user.
     * @return
     */
    private SplitPane createUserList(Object[][] data) {
        
        SplitPane ret = new SplitPane(SplitPane.ORIENTATION_VERTICAL);
        ret.setRenderId("userList");
        
        final Grid filterOptions = new Grid(2);
        ret.add(filterOptions);
        
        final TextField tf = new TextField();
        tf.setText("Asdasd");
        filterOptions.add(tf);
        
        Button button = new Button("Search");
        filterOptions.add(button);
        
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                filterOptions.remove(tf);
                TextField tf2 = new TextField();
                tf2.setText("Werer");
                filterOptions.add(tf2);
            }
        });
        
//        List<ColumnConfiguration> cols = new ArrayList<ColumnConfiguration>();
//        cols.add(new ColumnConfiguration("User ID", "userid"));
//        cols.add(new ColumnConfiguration("Name", "name"));
//        ColumnModel columnModel = new ColumnModel(cols);

//        DefaultSimpleStore store = new DefaultSimpleStore(
//                data,
//                0,
//                new String[]{"id", "userid", "name", "date"});
//
//        Panel panel = new Panel(new FitLayout());
//        ret.add(panel);
//        
//        userGridPanel = new GridPanel(columnModel, store);
//        panel.add(userGridPanel);
        
        return ret;
    }
    
    /**
     * Creates a form panel to edit the selected user.
     * @return
     */
    private void createUserEditPanel(Object[] data) {
        userEditPanel = new Grid(2);
        userEditPanel.setRenderId("userEditPanel");
        
        userEditPanel.add(new Label("Code"));
        final TextField codeField = new TextField();
        codeField.setText((String)data[1]);
        userEditPanel.add(codeField);
        
        userEditPanel.add(new Label("Name"));
        final TextField nameField = new TextField();
        nameField.setText((String)data[2]);
        userEditPanel.add(nameField);
        
                
        userEditPanel.add(new Label("Enabled"));
        final CheckBox enabledField = new CheckBox();
        enabledField.setSelected(true);
        userEditPanel.add(enabledField);
        
                
        userEditPanel.add(new Label("Role"));
        final RadioButton userRoleButton = new RadioButton();
        userRoleButton.setSelected(true);
        userEditPanel.add(userRoleButton);

        userEditPanel.add(new Label(" "));
        final RadioButton adminRoleButton = new RadioButton();
        userEditPanel.add(adminRoleButton);

        Button cancelButton = new Button("Cancel");
        userEditPanel.add(cancelButton);

        // and add a listener to the cancel button which removes that form panel
        // and puts the list back
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainSplitPane.remove(userEditPanel);
                mainSplitPane.add(userListPanel);
            }
        });
        
        Button saveButton = new Button("Save");
        userEditPanel.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // print out field values to make sure they have transferred correctly
                log.info("Save button pressed:");
                log.info("  codeField: " + codeField.getText());
                log.info("  nameField: " + nameField.getText());
                log.info("  enabled: " + enabledField.isSelected());
                
                mainSplitPane.remove(userEditPanel);
                mainSplitPane.add(userListPanel);
            }
        });
        
    }

    public void actionPerformed(ActionEvent arg0) {
        statusField.setText("You clicked the button " + ++buttonClicks + " times.");
    }
    
    /**
     * Makes enough data to test the eval bug in Firefox.
     * @return
     */
    private Object[][] makeData() {
        int rows = 50;
        
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
}

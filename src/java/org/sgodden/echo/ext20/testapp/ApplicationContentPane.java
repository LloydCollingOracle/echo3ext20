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


import nextapp.echo.app.ContentPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Menu;
import org.sgodden.echo.ext20.MenuItem;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TabbedPane;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.Toolbar;
import org.sgodden.echo.ext20.ToolbarButton;
import org.sgodden.echo.ext20.ToolbarFill;
import org.sgodden.echo.ext20.ToolbarSeparator;
import org.sgodden.echo.ext20.ToolbarTextItem;
import org.sgodden.echo.ext20.layout.BorderLayout;
import org.sgodden.echo.ext20.layout.BorderLayoutData;
import org.sgodden.echo.ext20.layout.ColumnLayout;
import org.sgodden.echo.ext20.layout.FitLayout;

/**
 * Application content pane for the test application.
 * 
 * @author sgodden
 */
public class ApplicationContentPane
        extends ContentPane implements ActionListener {

    private static final long serialVersionUID = 20080103L;
    private static final Log log = LogFactory.getLog(ApplicationContentPane.class);
    /**
     * Disabled text field used for status feedback.
     */
    private TextField statusField;
    /**
     * Number of button clicks performed.
     */
    private int buttonClicks = 0;

    public ApplicationContentPane() {
        super();
        addViewport();
    }

    /**
     * Adds the main viewport (the overall screen).
     */
    private void addViewport() {

        /*
         * For some reason, this is all only working
         * if the outer panel has fit layout.  Border layout
         * just seems to go wrong.
         */
        Panel outer = new Panel(new FitLayout());
        outer.setBorder(false);
        outer.setRenderId("viewport");
        add(outer);

        Panel main = new Panel(new BorderLayout());
        outer.add(main);
        main.setRenderId("main");

        statusField = new TextField("Click the button and see what happens");
        statusField.setEnabled(false);
        statusField.setRenderId("southPanelTextField");
        statusField.setLayoutData(new BorderLayoutData(BorderLayout.SOUTH));
        main.add(statusField);

        main.add(createWestPanel());
        
        TabbedPane tabs = createTabbedPane();
        tabs.setLayoutData(new BorderLayoutData(BorderLayout.CENTER));
        main.add(tabs);

        Button button = new Button("Press me!");
        button.setRenderId("button");

        button.addActionListener(this);

    }
    
    private Panel createNorthPanel() {
        Panel ret = new Panel(new ColumnLayout());
        ret.setLayoutData(new BorderLayoutData(BorderLayout.NORTH));
        
        Panel imagePanel = new Panel();
        
        imagePanel.setHtml(
                "<img style='float: left;' src='http://demo.nextapp.com/echo3csjs/image/Logo.png'></img>");
        imagePanel.setRenderId("northImagePanel");
        ret.add(imagePanel);

        Panel titlePanel = new Panel();
        titlePanel.setHtml("<h1>NORTH</h1>");
        titlePanel.setRenderId("northTitlePanel");
        ret.add(titlePanel);
        
        return ret;
    }

    /**
     * Creates a silly panel for the west region.
     * @return
     */
    private Panel createWestPanel() {
        Panel ret = new Panel();
        ret.setTitle("Navigation");
        ret.setWidth(143); // FIXME should not have to do this
        ret.setCollapsible(true);
        ret.setBorder(true);
        ret.setLayoutData(new BorderLayoutData(BorderLayout.WEST));
        ret.setRenderId("westPanel");
        
        Panel imagePanel = new Panel();
        imagePanel.setHtml(
                "<img src='http://demo.nextapp.com/echo3csjs/image/Logo.png'></img>");
        imagePanel.setRenderId("northImagePanel");
        imagePanel.setBorder(false);
        ret.add(imagePanel);
        
        Panel suppliersPanel = new Panel();
        
        suppliersPanel.setHtml("Suppliers");
        ret.add(suppliersPanel);

        return ret;
    }

    /**
     * Creates the tabs that go in the centre region.
     * @return
     */
    private TabbedPane createTabbedPane() {
        TabbedPane ret = new TabbedPane();
        ret.setRenderId("mainTabs");

        Panel centerPanel = new Panel();
        centerPanel.setHtml("<h1>Welcome</h1>" +
                "<p>This test application is very rudimentary right now.</p>" +
                "<p/>" +
                "<p>I will put some details here about what it does soon</p>.");
        centerPanel.setTitle("Welcome");
        ret.add(centerPanel);

        Panel panel2 = new Panel();
        panel2.setHtml("<h1>PANEL 2</h1><p><em>I am the second center panel</em></p>");
        panel2.setTitle("Panel 2");
        ret.add(panel2);

        ret.add(new UserPanel());
        
        ret.add(new WindowTest());
        
        ret.add(new PortalTest());
        
        ret.add(new TabbedPaneTest());

        return ret;
    }
    
    private Menu makeMenu(){
        Menu ret = new Menu();
        
        MenuItem item1 = new MenuItem("Item 1");
        ret.add(item1);
        
        item1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                log.info("Menu item was clicked");
            }
        });
        
        return ret;
    }

    public void actionPerformed(ActionEvent arg0) {
        statusField.setValue("You clicked the button " + ++buttonClicks + " times.");
    }
}

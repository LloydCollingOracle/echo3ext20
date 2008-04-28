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


import nextapp.echo.app.Border;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
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
import org.sgodden.echo.ext20.layout.AccordionLayout;
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

        main.add(createNorthPanel());
        
        main.add(createWestPanel());
        
        TabbedPane tabs = createTabbedPane();
        tabs.setLayoutData(new BorderLayoutData(BorderLayout.CENTER));
        main.add(tabs);

        Button button = new Button("Press me!");
        button.setRenderId("button");

        button.addActionListener(this);

    }
    
    private Panel createNorthPanel() {
        Panel ret = new Panel();
        ret.setBorder(false);
        ret.setTransparent(true);
        ret.setLayoutData(new BorderLayoutData(BorderLayout.NORTH));
        
        Panel imagePanel = new Panel();
        imagePanel.setTransparent(true);
        imagePanel.setBorder(false);
        imagePanel.setHtml(
                "<a href='http://echo.nextapp.com'><img style='float: left;' src='http://demo.nextapp.com/echo3csjs/image/Logo.png'></img></a>");
        imagePanel.setRenderId("northImagePanel");
        ret.add(imagePanel);
        
        return ret;
    }

    /**
     * Creates a silly panel for the west region.
     * @return
     */
    private Panel createWestPanel() {
        Panel ret = new Panel(new AccordionLayout());
        ret.setTitle("Navigation");
        ret.setWidth(143); // FIXME should not have to do this
        ret.setCollapsible(true);
        ret.setBorder(true);
        ret.setLayoutData(new BorderLayoutData(BorderLayout.WEST));
        ret.setRenderId("westPanel");
        
        final Panel navigationPanel = new Panel("Core Echo3");
        ret.add(navigationPanel);
        
        final Column col = new Column();
        col.setBackground(new Color(200, 200, 200));
        col.setInsets(new Insets(5));
        col.setCellSpacing(new Extent(5));
        navigationPanel.add(col);
        
        final nextapp.echo.app.Button button = makeEchoButton("Push me");
        col.add(button);
        button.setBackground(Color.LIGHTGRAY);
        
        button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				col.remove(button);
			}
		});
        
        final nextapp.echo.app.Button button2 = makeEchoButton("And me");
        col.add(button2);
        button2.setBackground(Color.LIGHTGRAY);

        return ret;
    }
    
    private nextapp.echo.app.Button makeEchoButton(String text) {
    	nextapp.echo.app.Button button = new nextapp.echo.app.Button(text);
    	button.setInsets(new Insets(2));
    	button.setBorder(new Border(1, Color.DARKGRAY, Border.STYLE_SOLID));
    	return button;
    }

    /**
     * Creates the tabs that go in the centre region.
     * @return
     */
    private TabbedPane createTabbedPane() {
        TabbedPane ret = new TabbedPane();
        ret.setRenderId("mainTabs");

        Panel welcomePanel = new Panel();
        welcomePanel.setPadding("5px");
        welcomePanel.setHtml("<h1><u>Welcome to the Echo3 / Ext20 test application</u></h1>" +
                "<br/>" +
                "<p>This test application is very rudimentary right now.</p>" +
                "<br/>" +
                "<p>Each tab contains examples of a particular component type.</p>");
        welcomePanel.setTitle("Welcome");
        ret.add(welcomePanel);

        ret.add(new UserPanel(true));
        ret.add(new WindowTest());
        ret.add(new PortalTest());
        ret.add(new TabbedPaneTest());
        ret.add(new RelativePositioningTest());
        ret.add(new LayoutTest());

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

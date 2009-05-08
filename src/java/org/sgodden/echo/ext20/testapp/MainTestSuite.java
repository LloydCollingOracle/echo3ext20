package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TabbedPane;

/**
 * The original main test suite, where each test is on a tab.
 * 
 * @author sgodden
 */
@SuppressWarnings("serial")
public class MainTestSuite extends TabbedPane {

    public MainTestSuite() {
        super();
        setRenderId("mainTabs");

        Panel welcomePanel = new Panel();
        welcomePanel.setPadding("5px");
        welcomePanel
                .setHtml("<h1><u>Welcome to the Echo3 / Ext20 test application</u></h1>"
                        + "<br/>"
                        + "<p>This test application is very rudimentary right now.</p>"
                        + "<br/>"
                        + "<p>Each tab contains examples of a particular component type.</p>");
        welcomePanel.setTitle("Welcome");
        add(welcomePanel);

        add(new UserPanel(true));
        add( new HtmlPanelTest());
        add( new MultiSelectTest());
        add(new WindowTest());
        add(new PortalTest());
        add(new TabbedPaneTest());
        add(new LayoutTest());
        add(new StylesheetTest());
        add(new GroovyTest());
        add(new TreeTest());
        add(new FieldGroupTest());
        add(new ComboListTest());
        add(new ButtonGroupTest());
        add(new EffectsTest());
    }
}
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
        add(new WelcomePanel());
        
        add( new TimeFieldTest());
        add(new UserPanel(true));
        add( new HtmlPanelTest());
        add(new HtmlEditorTest());
        add( new MultiSelectTest());
        add( new ToolbarButtonChangingTest());
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
        add(new ContextMenuTest());
    }
}
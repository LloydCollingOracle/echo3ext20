package org.sgodden.echo.ext20;

import junit.framework.TestCase;

/**
 * Unit tests for the TabbedPane component
 * @author Lloyd Colling
 *
 */
public class TabbedPaneTest extends TestCase {

    /**
     * Tests that when a component is removed from a tabbed pane, the active tab index
     * does not go out of bounds.
     * @throws Exception
     */
    public void testActiveIndexChangeWhenRemovingComponent() throws Exception {
        TabbedPane p = new TabbedPane();
        
        Container a = new Panel();
        Container b = new Panel();
        Container c = new Panel();
        p.add(a);
        p.add(b);
        p.add(c);
        
        assertEquals("Tabbed Pane is changing tab index when components added",
                0, p.getActiveTabIndex());
        
        p.setActiveTabIndex(2);
        assertEquals("Tabbed Pane is not responding to setActiveTabIndex calls",
                2, p.getActiveTabIndex());
        
        p.remove(c);
        assertTrue("Tabbed Pane is not detecting invalid active tab index values after component removal",
                p.getActiveTabIndex() < p.getComponentCount());

        p.setActiveTabIndex(0);
        p.add(c);
        p.setActiveTabIndex(1);
        p.processInput(TabbedPane.TAB_CLOSE_EVENT, Integer.valueOf(1));

        assertTrue("Tabbed Pane is not detecting invalid active tab index values after tab closing",
                p.getActiveTabIndex() < p.getComponentCount());
        
        
    }
}

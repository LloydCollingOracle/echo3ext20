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

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Container;
import org.sgodden.echo.ext20.DeferredUiCreate;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TabbedPane;
import org.sgodden.echo.ext20.layout.BorderLayout;
import org.sgodden.echo.ext20.layout.BorderLayoutData;
import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.TableLayout;

/**
 * Provides tests for the {@link TabbedPane} component.
 * 
 * @author sgodden
 * 
 */
@SuppressWarnings({"serial"})
public class TabbedPaneTest extends Panel {

    private static final transient Log log = LogFactory
            .getLog(TabbedPaneTest.class);

    public TabbedPaneTest() {
        super(new FitLayout(), "Tabbed pane");
        setRenderId("tabbedPaneTest");
        createUI();
    }

    public void createUI() {
        Container outer = new Panel(new BorderLayout());
        add(outer);

        final TabbedPane tabs = new TabbedPane();
        tabs.setRenderId("tabbedPaneTestTabs");
        addPanel(tabs);

        Container northPanel = new Panel(new TableLayout(2));
        northPanel.setLayoutData(new BorderLayoutData(BorderLayout.NORTH));
        outer.add(northPanel);

        Button newTabButton = new Button("Add new tab");
        northPanel.add(newTabButton);
        newTabButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                addPanel(tabs);
            }
        });

        Button removeLastButton = new Button("Remove last tab");
        northPanel.add(removeLastButton);
        removeLastButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (tabs.getComponentCount() > 0) {
                    tabs
                            .remove(tabs
                                    .getComponent(tabs.getComponentCount() - 1));
                }
            }
        });

        tabs.setLayoutData(new BorderLayoutData(BorderLayout.CENTER));
        outer.add(tabs);
    }

    private void addPanel(TabbedPane tabs) {
        Panel newPanel = new Panel();
        int index = tabs.getComponentCount() + 1;
        newPanel.setTitle("Tab " + index);
        newPanel.setHtml("Text for tab " + index);

        tabs.add(newPanel);

        log.info(tabs.getComponentCount());

        tabs.setActiveTabIndex(tabs.getComponentCount() - 1);
    }

}

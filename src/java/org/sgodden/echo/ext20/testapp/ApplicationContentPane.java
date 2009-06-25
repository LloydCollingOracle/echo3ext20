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

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Border;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.ApplicationWaitIndicator;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.AccordionLayout;
import org.sgodden.echo.ext20.layout.BorderLayout;
import org.sgodden.echo.ext20.layout.BorderLayoutData;
import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.TableLayout;
import org.sgodden.echo.ext20.layout.TableLayoutData;
import org.sgodden.echo.ext20.testapp.regression.BorderLayoutTest;
import org.sgodden.echo.ext20.testapp.regression.FocusTextAreaTest;
import org.sgodden.echo.ext20.testapp.regression.RemoveEchoFromExtTest;
import org.sgodden.echo.ext20.testapp.regression.TextFieldActionTest;

/**
 * Application content pane for the test application.
 * 
 * @author sgodden
 */
@SuppressWarnings("serial")
public class ApplicationContentPane extends ContentPane {

    // private static final Log log =
    // LogFactory.getLog(ApplicationContentPane.class);
    /**
     * The component being displayed in the centre.
     */
    private Component centreComponent;
    /**
     * The container for the centre component.
     */
    private Panel centreContainer;

    public ApplicationContentPane() {
        super();
        addViewport();
    }

    /**
     * Adds the main viewport (the overall screen).
     */
    private void addViewport() {

        /*
         * For some reason, this is all only working if the outer panel has fit
         * layout. Border layout just seems to go wrong.
         */
        Panel outer = new Panel(new FitLayout());
        outer.setBorder(false);
        outer.setRenderId("viewport");
        add(outer);

        Panel mainPanel = new Panel(new BorderLayout());
        outer.add(mainPanel);
        mainPanel.setRenderId("main");

        mainPanel.add(createNorthPanel());

        mainPanel.add(createWestPanel());

        centreContainer = new Panel(new FitLayout());
        centreContainer
                .setLayoutData(new BorderLayoutData(BorderLayout.CENTER));
        mainPanel.add(centreContainer);

        showCentreComponent(new WelcomePanel());
    }

    private Panel createNorthPanel() {
        Panel ret = new Panel();
        ret.setHeight(52);
        ret.setBorder(false);
        TableLayout layout = new TableLayout();
        layout.setFullWidth(true);
        ret.setLayout(layout);
        ret.setLayoutData(new BorderLayoutData(BorderLayout.NORTH));
        ret.setBodyBackground(new Color(84, 84, 84));

        Panel imagePanel = new Panel();
        TableLayoutData tld = new TableLayoutData();
        tld.setCellAlign("left");
        imagePanel.setLayoutData(tld);
        imagePanel.setBodyTransparent(true);
        imagePanel.setBorder(false);
        imagePanel
                .setHtml("<a href='http://echo.nextapp.com'><img style='float: left;' src='http://demo.nextapp.com/echo3csjs/image/Logo.png'></img></a>");
        imagePanel.setRenderId("northImagePanel");
        ret.add(imagePanel);

        ApplicationWaitIndicator wait = new ApplicationWaitIndicator();
        tld = new TableLayoutData();
        tld.setCellAlign("right");
        wait.setLayoutData(tld);
        ret.add(wait);

        return ret;
    }

    /**
     * Creates a silly panel for the west region.
     * 
     * @return
     */
    private Panel createWestPanel() {
        Panel ret = new Panel(new AccordionLayout());
        ret.setBodyBackground(new Color(220, 220, 220));
        ret.setTitle("Navigation");
        ret.setWidth(143);
        ret.setCollapsible(true);
        ret.setBorder(true);
        ret.setLayoutData(new BorderLayoutData(BorderLayout.WEST));
        ret.setRenderId("westPanel");

        final Panel coreEcho3Panel = new Panel("Test suites");
        coreEcho3Panel.setBodyTransparent(true);
        coreEcho3Panel.setAutoScroll(true);
        ret.add(coreEcho3Panel);

        final Column col = new Column();
        col.setInsets(new Insets(5));
        col.setCellSpacing(new Extent(5));
        coreEcho3Panel.add(col);

        col.add(makeTestButton("ToolTips", ToolTipTest.class));
        col.add(makeTestButton("CustomComboBox", CustomComboBoxTest.class));
        col.add(makeTestButton("Time field", TimeFieldTest.class));
        col.add(makeTestButton("Grid and form", UserPanel.class));
        col.add(makeTestButton("HTML Panel", HtmlPanelTest.class));
        col.add(makeTestButton("HTML Editor", HtmlEditorTest.class));
        col.add(makeTestButton("Multi select", MultiSelectTest.class));
        col.add(makeTestButton("Change toolbar buttons",
                ToolbarButtonChangingTest.class));
        col.add(makeTestButton("Window", WindowTest.class));
        col.add(makeTestButton("Portal", PortalTest.class));
        col.add(makeTestButton("Tab panel", TabbedPaneTest.class));
        col.add(makeTestButton("Layouts", LayoutTest.class));
        col.add(makeTestButton("Stylesheet", StylesheetTest.class));
        col.add(makeTestButton("Groovy", GroovyTest.class));
        col.add(makeTestButton("Tree", TreeTest.class));
        col.add(makeTestButton("Field group", FieldGroupTest.class));
        col.add(makeTestButton("Combo list", ComboListTest.class));
        col.add(makeTestButton("Button group", ButtonGroupTest.class));
        col.add(makeTestButton("Effects", EffectsTest.class));
        col.add(makeTestButton("Context menu", ContextMenuTest.class));
        col.add(makeTestButton("Non-focusable buttons",
                FocusableButtonTest.class));
        col.add(makeTestButton("Form grid", FormGridTest.class));
        col.add(makeTestButton("Bloated test suite", MainTestSuite.class));

        Panel regressionPanel = new Panel("Regression tests");
        ret.add(regressionPanel);
        regressionPanel.setBodyTransparent(true);

        Column col2 = new Column();
        regressionPanel.add(col2);
        col2.setInsets(new Insets(5));

        col2.add(makeTestButton("Focus Text Areas", FocusTextAreaTest.class));
        col2.add(makeTestButton("Removal bug 1", RemoveEchoFromExtTest.class));
        col2
                .add(makeTestButton("Lazy render bug 1",
                        "org.sgodden.echo.ext20.testapp.regression.UpdatedNonRenderedGridTest"));
        col2
                .add(makeTestButton("Combo box model update",
                        "org.sgodden.echo.ext20.testapp.regression.ComboBoxModelUpdatePanel"));
        col2.add(makeTestButton("Button in BorderLayout, issue 28",
                BorderLayoutTest.class));
        col2.add(makeTestButton("TextField Action, issue 29",
                TextFieldActionTest.class));

        return ret;
    }

    private void showCentreComponent(Component c) {
        if (centreComponent != null) {
            centreContainer.remove(centreComponent);
        }
        centreComponent = c;
        centreContainer.add(c);
    }

    private nextapp.echo.app.Button makeTestButton(String text,
            final Class<? extends Component> testClass) {
        nextapp.echo.app.Button button = new nextapp.echo.app.Button(text);
        button.setInsets(new Insets(2));
        button.setBackground(Color.LIGHTGRAY);
        button.setBorder(new Border(1, Color.DARKGRAY, Border.STYLE_SOLID));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    showCentreComponent(testClass.newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return button;
    }

    private nextapp.echo.app.Button makeTestButton(String text,
            final String groovyClassName) {
        nextapp.echo.app.Button button = new nextapp.echo.app.Button(text);
        button.setInsets(new Insets(2));
        button.setBackground(Color.LIGHTGRAY);
        button.setBorder(new Border(1, Color.DARKGRAY, Border.STYLE_SOLID));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    showCentreComponent(makeGroovyComponent(groovyClassName));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return button;
    }

    private Component makeGroovyComponent(String className) {
        AppInstance app = (AppInstance) ApplicationInstance.getActive();
        return (Component) app.getGroovyObjectInstance(className);
    }
}

package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.Label;
import nextapp.echo.app.Window;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Container;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TabbedPane;
import org.sgodden.echo.ext20.command.DoPanelLayoutCommand;
import org.sgodden.echo.ext20.layout.AccordionLayout;
import org.sgodden.echo.ext20.layout.BorderLayout;
import org.sgodden.echo.ext20.layout.BorderLayoutData;
import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.TableLayout;
import org.sgodden.echo.ext20.testapp.layout.ColumnLayoutTest;
import org.sgodden.echo.ext20.testapp.layout.TableLayoutTest;
import org.sgodden.echo.ext20.testapp.layout.TableLayoutTest2;

/**
 * Layout tests.
 * 
 * @author sgodden
 *
 */
@SuppressWarnings({"serial"})
public class LayoutTest extends Panel {
    
    public LayoutTest(){
        super(new FitLayout(), "Layouts");
        createUI();
    }

    public void createUI() {
        
        Container outer = new Panel(new BorderLayout());
        add(outer);
        
        Container north = new Panel();
        
        north.setHtml("The tabs below contain examples of the supported layouts");
        north.setLayoutData(new BorderLayoutData(BorderLayout.NORTH));
        outer.add(north);
        
        TabbedPane tabs = new TabbedPane();
        tabs.setLayoutData(new BorderLayoutData(BorderLayout.CENTER));
        outer.add(tabs);
        
        tabs.add(new AccordionLayoutTest( false));
        tabs.add(new AccordionLayoutTest( true));
        tabs.add(new ColumnLayoutTest());
        tabs.add(new TableLayoutTest());
        tabs.add(new TableLayoutTest2());
        tabs.add(new DoPanelLayoutTest());
    }
    
    private static class AccordionLayoutTest 
            extends Panel
            {
        
        private AccordionLayoutTest( boolean hideCollapseTool){
            super(new AccordionLayout( hideCollapseTool), "Accordion");
            createUI();
        }

        public void createUI() {
            add(createPanel("Panel 1"));
            add(createPanel("Panel 2"));
            add(createPanel("Panel 3"));
            add( createBeforeExpandEventPanel( false));
            add( createBeforeExpandEventPanel( true));
            
        }

		private Container createBeforeExpandEventPanel( boolean expansible) {
			String text = "This panel will "+ (expansible?"":"not ") + "expand";
            Panel panel = new Panel( text);
            panel.setBaseCssClass("customcss");
            panel.setHtml(text);
            panel.setExpansible( expansible);
            panel.addBeforeExpandListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.out.println( "I am clicked, I got the event!");
				}
			});
			return panel;
		}
        
        private Container createPanel(String text) {
            Panel ret = new Panel(text);
            ret.setBaseCssClass("customcss");
            ret.setHtml(text);
            return ret;
        }
    }
    
    private static class DoPanelLayoutTest 
            extends Panel
            {
        
        private DoPanelLayoutTest(){
            super(new BorderLayout(), "DoPanelLayout");
            createUI();
        }

        public void createUI() {
            final Container northPanel = new Panel(new TableLayout(1));
            northPanel.setLayoutData(new BorderLayoutData(BorderLayout.NORTH));
            add(northPanel);
            
            Container centerPanel = new Panel();
            centerPanel.add(new Label("This is the center panel!"));
            centerPanel.setLayoutData(new BorderLayoutData(BorderLayout.CENTER));
            add(centerPanel);
            
            Button addRemoveExtraPanelButton = new Button("Add/Remove Panel");
            final Container childPanel = new Panel();
            childPanel.add(new Label("Child Panel"));
            addRemoveExtraPanelButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent arg0) {
                    boolean isAdded = false;
                    for (int i = 0; i < northPanel.getComponentCount(); i++) {
                        if (northPanel.getComponent(i) == childPanel)
                            isAdded = true;
                    }
                    
                    if (isAdded)
                        northPanel.remove(childPanel);
                    else
                        northPanel.add(childPanel);
                    Window.getActive().enqueueCommand(new DoPanelLayoutCommand(DoPanelLayoutTest.this));
                }});
            
            northPanel.add(addRemoveExtraPanelButton);
            northPanel.add(new Label("This is the north panel!"));
        }
    }

}

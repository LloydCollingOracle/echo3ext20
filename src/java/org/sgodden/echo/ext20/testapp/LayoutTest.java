package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.DeferredUiCreate;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TabbedPane;
import org.sgodden.echo.ext20.layout.AccordionLayout;
import org.sgodden.echo.ext20.layout.BorderLayout;
import org.sgodden.echo.ext20.layout.BorderLayoutData;
import org.sgodden.echo.ext20.layout.FitLayout;

/**
 * Tests out positioning relative to other components.
 * 
 * @author sgodden
 *
 */
public class LayoutTest 
		extends Panel
		implements DeferredUiCreate {
	
	public LayoutTest(){
		super(new FitLayout(), "Layouts");
	}

	public void createUI() {
		
		Panel outer = new Panel(new BorderLayout());
		add(outer);
		
		Panel north = new Panel();
		north.setHtml("The tabs below contain examples of the supported layouts");
		north.setLayoutData(new BorderLayoutData(BorderLayout.NORTH));
		outer.add(north);
		
		TabbedPane tabs = new TabbedPane();
		tabs.setLayoutData(new BorderLayoutData(BorderLayout.CENTER));
		outer.add(tabs);
		
		tabs.add(new AccordionLayoutTest());
		
	}
	
	private static class AccordionLayoutTest 
			extends Panel
			{
		
		private AccordionLayoutTest(){
			super(new AccordionLayout(), "Accordion");
			createUI();
		}

		public void createUI() {
			add(createPanel("Panel 1"));
			add(createPanel("Panel 2"));
			add(createPanel("Panel 3"));
		}
		
		private Panel createPanel(String text) {
			Panel ret = new Panel(text);
			ret.setHtml(text);
			return ret;
		}
	}

}

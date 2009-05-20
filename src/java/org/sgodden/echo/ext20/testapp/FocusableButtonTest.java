package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.Window;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.apache.log4j.Logger;
import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.layout.TableLayout;

@SuppressWarnings("serial")
public class FocusableButtonTest extends Panel 
		implements ActionListener {
	
	private static final transient Logger LOG = Logger.getLogger(FocusableButtonTest.class);
	
	public FocusableButtonTest() {
		super("Focusable / non-focusable button test");
		TableLayout tl = new TableLayout(1);
		tl.setCellPadding(10);
		tl.setCellSpacing(10);
		setLayout(tl);
		
		Button nonFocusable = new Button("I am not focusable");
		nonFocusable.setFocusable(false);
		nonFocusable.addActionListener(this);
		add(nonFocusable);
		
		TextField tf = new TextField("Focus me and click the button above");
		add(tf);
		
		Button focusable = new Button("I am focusable");
		focusable.addActionListener(this);
		add(focusable);
		
	}

	public void actionPerformed(ActionEvent arg0) {
		LOG.info("Focused component is: " + Window.getActive().getFocusedComponent());
	}
}

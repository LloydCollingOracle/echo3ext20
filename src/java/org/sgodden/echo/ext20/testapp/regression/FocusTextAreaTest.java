package org.sgodden.echo.ext20.testapp.regression;

import nextapp.echo.app.Window;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextArea;
import org.sgodden.echo.ext20.layout.TableLayout;

@SuppressWarnings("serial")
public class FocusTextAreaTest extends Panel {
	
	public FocusTextAreaTest() {
		super("Test focusing of text areas");
		setLayout(new TableLayout(1));
		
		final TextArea ta1 = new TextArea("Text area 1");
		add(ta1);
		
		final TextArea ta2 = new TextArea("Text area 2");
		add(ta2);
		
		Button focusTa1 = new Button("Focus first text area");
		addButton(focusTa1);
		focusTa1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Window.getActive().setFocusedComponent(ta1);
			}});
		
		Button focusTa2 = new Button("Focus second text area");
		addButton(focusTa2);
		focusTa2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Window.getActive().setFocusedComponent(ta2);
			}});
		
		Window.getActive().setFocusedComponent(ta1);
	}

}

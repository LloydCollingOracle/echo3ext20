package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.apache.log4j.Logger;
import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.Window;
import org.sgodden.echo.ext20.layout.TableLayout;

@SuppressWarnings("serial")
public class FocusTest extends Panel 
		implements ActionListener {
	
	private static final transient Logger LOG = Logger.getLogger(FocusTest.class);
	
	private Button displayDialogButton;
	
	public FocusTest() {
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
		
		displayDialogButton = new Button("Dialog focus test");
		displayDialogButton.addActionListener(this);
		add(displayDialogButton);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(displayDialogButton)) {
			add(new FocusTestDialog());
		}
		else {
			LOG.info("Focused component is: " + nextapp.echo.app.Window.getActive().getFocusedComponent());
		}
	}
	
	public static class FocusTestDialog extends Window {
		private TextField textField;
		public void init() {
			setTitle("Focus test dialog");
			TableLayout tl = new TableLayout(1);
			tl.setCellPadding(10);
			tl.setCellSpacing(10);
			setLayout(tl);
			
			textField = new TextField("I should be focused");
			add(textField);
			//textField.focus();
			
			Button closeButton = new Button("Close");
			closeButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					close();
				}});
			addButton(closeButton);
			closeButton.focus();
		}
	}
}

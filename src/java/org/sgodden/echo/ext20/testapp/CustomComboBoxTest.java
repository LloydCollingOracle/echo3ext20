package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.CustomComboBox;
import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;

public class CustomComboBoxTest extends Panel {
	public CustomComboBoxTest() {
		super( "CustomComboBox Test");
		createUI();
	}

	private void createUI() {
		Panel panel = new Panel();
		final CustomComboBox box = new CustomComboBox( panel);
		box.setValue( "ABC");
		add( box);

		panel.add( new Label( "Input your name:"));
		final TextField textField = new TextField( box.getValue());
		panel.add( textField);
		Button button = new Button( "OK");
		button.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				box.setValue( textField.getValue());
			}
		});
		panel.add( button);
	}
}

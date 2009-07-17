package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.layout.FormLayout;

public class ValidateTest extends Panel {
	public ValidateTest() {
		super( "Validate Test and Long Title Test");
		setLayout( new FormLayout());
		createUI();
	}

	private void createUI() {
		TextField field = new TextField();
		field.setRegExp( "^\\d*$");
		field.setRegexpFailureText( "Digits only");
		field.setFieldLabel( "digits only, validate after input");
		add( field);

		TextField field2 = new TextField();
		field2.setMaskRe( "^\\d*$");
		field2.setRegexpFailureText( "Digits only");
		field2.setFieldLabel( "digits only, can't input");
		add( field2);
		
		Button button = new Button("ok");
		add( button);
	}
}

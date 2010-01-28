package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.NumberField;
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

		NumberField numberField = new NumberField();
		numberField.setFieldLabel("Must be a number in the range 1 to 24");
		numberField.setMinValue(1);
		numberField.setMinText("value too small");
		numberField.setMaxValue(24);
		numberField.setMaxText("value too large");
		add( numberField);
	}
}

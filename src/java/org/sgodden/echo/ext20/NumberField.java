package org.sgodden.echo.ext20;

/**
 * A number field
 * 
 * @author icumberland
 *
 */
@SuppressWarnings( { "serial" })
public class NumberField extends TextField {
	public NumberField() {
		super();
	}
	
	public NumberField(String text) {
		super(text);
	}
	
	public NumberField(String text, String fieldLabel) {
		super(text, fieldLabel);
	}
}

package org.sgodden.echo.ext20;

public class CustomComboBox extends ExtComponent {
	public static final String PROPERTY_VALUE = "value";
	
	public CustomComboBox( ExtComponent dropDownComponent) {
		add( dropDownComponent);
	}
	
	public String getValue() {
		return (String) get( PROPERTY_VALUE);
	}
	
	/**
	 * The dropdown will collapse when the value changed.
	 * @param value
	 */
	public void setValue( String value) {
		set( PROPERTY_VALUE, value);
	}
}

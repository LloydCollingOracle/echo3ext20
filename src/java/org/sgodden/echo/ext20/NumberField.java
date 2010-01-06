package org.sgodden.echo.ext20;

/**
 * A number field
 * 
 * @author icumberland
 *
 */
@SuppressWarnings( { "serial" })
public class NumberField extends TextField {
	
    public static final String DECIMAL_PRECISION = "decimalPrecision";
	
	public NumberField() {
		super();
	}
	
	public NumberField(String text) {
		super(text);
	}
	
	public NumberField(String text, String fieldLabel) {
		super(text, fieldLabel);
	}
	
    /**
     * Sets the maximum precision to display after the decimal separator (defaults to 2)
     * 
     * @param decimalPrecision
     *            the maximum precision to display after the decimal separator.
     */
    public void setDecimalPrecision(int decimalPrecision) {
        set(DECIMAL_PRECISION, decimalPrecision);
    }
}

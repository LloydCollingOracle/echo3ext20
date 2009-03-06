package org.sgodden.echo.ext20;

/**
 * Interface for all form field types.
 * 
 * @author rcharlton
 */
public interface Field {

	/**
	 * Sets whether the form field is valid.
	 * @param valid whether the form field is valid.
	 */
	public void setIsValid(boolean valid);

	/**
	 * Sets the error text to be displayed to the user for the form field.
	 * @param invalidText the error text.
	 */
	public void setInvalidText(String invalidText);
}

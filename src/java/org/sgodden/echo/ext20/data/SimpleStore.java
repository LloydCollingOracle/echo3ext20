package org.sgodden.echo.ext20.data;

/**
 * An interface representing an ext simple store.
 * @author sgodden
 *
 */
public interface SimpleStore {
	
	/**
	 * Returns the rows of data.
	 * @return the rows of data.
	 */
	public Object[][] getData();
	
	/**
	 * Returns the index of the column which provides the row identifier, or
	 * <code>null</code> if there isn't one. 
	 * @return the index of the column which provides the row identifier, or
	 * <code>null</code> if there isn't one.
	 */
	public Integer getId();
	
	/**
	 * Returns the array of field names, which map by their position to the columns
	 * in the store data.
	 * @return the array of field names.
	 */
	public String[] getFields();
	
	/**
	 * Returns the number of rows.
	 * @return the number of rows.
	 */
	public int getSize();
	

}

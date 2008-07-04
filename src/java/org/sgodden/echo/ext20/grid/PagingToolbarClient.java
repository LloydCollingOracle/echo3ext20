package org.sgodden.echo.ext20.grid;

/**
 * An interface describing the contract for a client of a paging toolbar.
 * @author sgodden
 */
public interface PagingToolbarClient {

    /**
     * Informs the client that the page offset has changed.
     */
    public void setPageOffset(int pageOffset);

    /**
     * Returns the current page size.
     * @return the current page size.
     */
    public int getPageSize();

    /**
     * Sets the new page size.
     * @param pageSize the new page size.
     */
    public void setPageSize(int pageSize);

}
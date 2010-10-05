package org.sgodden.echo.ext20.grid;

import java.util.EventObject;

public class FilterColumnEvent extends EventObject {
	
	private int column;
	
	public FilterColumnEvent(Object source, int column) {
        super(source);
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}

package org.sgodden.echo.ext20.grid;

import java.util.EventObject;

@SuppressWarnings("serial")
public class RemoveColumnEvent extends EventObject {
    
    int column;

    public RemoveColumnEvent(Object source, int column) {
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

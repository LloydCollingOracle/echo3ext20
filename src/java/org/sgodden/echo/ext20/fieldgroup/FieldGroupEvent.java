package org.sgodden.echo.ext20.fieldgroup;

import java.util.EventObject;

/**
 * Event fired when a Field Group Container will add
 * or remove a field group.
 * 
 * The action can be cancelled by calling setActionCancelled(true).
 * 
 * @author Lloyd Colling
 *
 */
public class FieldGroupEvent extends EventObject {
    
    boolean actionCancelled = false;
    int index = -1;

    public FieldGroupEvent(Object source, int index) {
        super(source);
        this.index = index;
    }

    public boolean isActionCancelled() {
        return actionCancelled;
    }

    public void setActionCancelled(boolean cancelled) {
        this.actionCancelled = cancelled;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}

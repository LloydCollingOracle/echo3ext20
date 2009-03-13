package org.sgodden.echo.ext20.command;

import nextapp.echo.app.Command;
import nextapp.echo.app.Component;

/**
 * A Web Application Container-specific <code>Command</code> to 
 * scroll the specified container into view.
 */
public class ScrollIntoViewCommand 
implements Command {
    /**
     * The id of the component to scroll into view.
     */
    private String toScrollId;
    
    /**
     * The id of the scrolling component.
     */
    private String scrollingComponentId;
    
    /**
     * Creates a new <code>ScrollIntoViewCommand</code>.
     * 
     * @param c the component to scroll into view.
     */
    public ScrollIntoViewCommand(Component toScrollIntoView, Component scrollingComponent) {
        super();
        this.toScrollId = toScrollIntoView.getId();
        this.scrollingComponentId = scrollingComponent.getId();
    }
    
    public String getToScrollId(){
        return this.toScrollId;
    }
    
    public String getScrollingComponentId(){
        return this.scrollingComponentId;
    }
}

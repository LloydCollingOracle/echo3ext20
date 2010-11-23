package org.sgodden.echo.ext20.command;

import nextapp.echo.app.Command;
import nextapp.echo.app.Component;

/**
 * A Web Application <code>Command</code> to 
 * highlight a component
 */
public class DoHighlightComponentCommand 
implements Command {
    /**
     * The panel to call layout on
     */
    private Component component;
    
    public DoHighlightComponentCommand(Component componentToHighlight) {
        super();
        this.component = componentToHighlight;
    }
    
    public String getComponentId(){
        return component.getContainingWindow().getId() + "." + component.getRenderId();
    }
    
    public String getColour(){
        return "ffffff";
    }
    
    public int getDuration(){
        return 2;
    }
    
    public Component getComponent() {
        return component;
    }
}

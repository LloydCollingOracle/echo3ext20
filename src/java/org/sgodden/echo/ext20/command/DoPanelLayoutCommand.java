package org.sgodden.echo.ext20.command;

import nextapp.echo.app.Command;

import org.sgodden.echo.ext20.Container;

/**
 * A Web Application <code>Command</code> to 
 * force a Panel to perform a call to doLayout
 */
public class DoPanelLayoutCommand 
implements Command {
    /**
     * The panel to call layout on
     */
    private Container panel;
    
    public DoPanelLayoutCommand(Container panelToLayout) {
        super();
        this.panel = panelToLayout;
    }
    
    public String getPanelId(){
        return panel.getContainingWindow().getId() + "." + panel.getRenderId();
    }
    
    public Container getPanel() {
        return panel;
    }
}

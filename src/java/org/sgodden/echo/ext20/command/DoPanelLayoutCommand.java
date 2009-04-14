package org.sgodden.echo.ext20.command;

import nextapp.echo.app.Command;

import org.sgodden.echo.ext20.Panel;

/**
 * A Web Application <code>Command</code> to 
 * force a Panel to perform a call to doLayout
 */
public class DoPanelLayoutCommand 
implements Command {
    /**
     * The id of the panel to call layout on
     */
    private String panelId;
    
    public DoPanelLayoutCommand(Panel panelToLayout) {
        super();
        this.panelId = panelToLayout.getContainingWindow().getId() + "." + panelToLayout.getRenderId();
    }
    
    public String getPanelId(){
        return this.panelId;
    }
}

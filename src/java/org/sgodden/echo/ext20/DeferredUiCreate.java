package org.sgodden.echo.ext20;

/**
 * A component which defers creation of its UI (that is, does not create it within its
 * constructor).
 * <p/>
 * This allows, for example, a tabbed pane to defer creation
 * of a tab's contents to the point where the tab is shown.
 *  
 * @author sgodden
 *
 */
public interface DeferredUiCreate {
    
    /**
     * Instructs the component to create the UI (to add its inner components).
     * <p/>
     * This method will only be called once.  It is the container's responsibility
     * to ensure this. 
     */
    public void createUI();

}

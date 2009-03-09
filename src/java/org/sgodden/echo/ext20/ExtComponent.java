package org.sgodden.echo.ext20;

import nextapp.echo.app.Component;

/**
 * Abstract subclass of component that introduces behavioural options
 * that are specific to ext components.
 * @author Lloyd Colling
 *
 */
@SuppressWarnings("serial")
public abstract class ExtComponent extends Component {

    /**
     * Sets whether the tab close icon is visible when this 
     * component is added to a tabbed pane
     */
    public static final String CLOSABLE_PROPERTY = "closable";

    /**
     * An optional extra CSS class that will be added to this component's Element (defaults to '').
     */
    public static final String CSS_CLASS = "cssClass";
    
    /**
     * An optional extra CSS class that will be added to this component's container (defaults to '').
     */
    public static final String CONTAINER_CSS_CLASS = "containerCssClass";
    
    public ExtComponent() {
        super();
    }
    
    /**
     * Sets an optional extra CSS class that will be added to this component's Element (defaults to '').
     * @param cls the extra CSS class
     */
    public void setCssClass(String cls) {
        set(CSS_CLASS,cls);
    }
    
    public String getCssClass() {
        return (String)get(CSS_CLASS);
    }
    
    /**
     * Returns whether the component is closable.
     * A closable component can be removed from a tabbed pane.
     * @return
     */
    public boolean getClosable() {
        Object val = get(CLOSABLE_PROPERTY);
        return val == null ? Boolean.FALSE : (Boolean)val;
    }
    
    public void setClosable(boolean closable) {
        set(CLOSABLE_PROPERTY, Boolean.valueOf(closable));
    }
    
    /**
     * An optional extra CSS class that will be added to this component's container (defaults to '').
     * @param cls the extra CSS class
     */
    public void setContainerCssClass(String cls) {
        set(CONTAINER_CSS_CLASS,cls);
    }
    
    public String getContainerCssClass() {
        return (String)get(CONTAINER_CSS_CLASS);
    }
}

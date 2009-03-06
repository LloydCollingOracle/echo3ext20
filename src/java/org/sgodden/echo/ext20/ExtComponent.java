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
     * Sets the base css for this panel. (Defaults to 'x-panel')
     */
    public static final String BASE_CSS_CLASS = "baseCssClass";
    
    public ExtComponent() {
        super();
    }
    
    /**
     * @param The base css class to be used for this panel. This 
     * class should be defined in the css file for this application
     */
    public void setBaseCssClass(String baseCssClass) {
        set(BASE_CSS_CLASS,baseCssClass);
    }
    
    public String getBaseCssClass() {
        return (String)get(BASE_CSS_CLASS);
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
}

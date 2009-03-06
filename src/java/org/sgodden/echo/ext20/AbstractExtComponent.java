package org.sgodden.echo.ext20;

import nextapp.echo.app.Component;

/**
 * Abstract sub-class of {@link Component}.
 * @author rcharlton
 */
public class AbstractExtComponent extends Component{
	
	public AbstractExtComponent(){
		super();
	}

    /**
     * Sets the base css for this panel. (Defaults to 'x-panel')
     */
    public static final String BASE_CSS_CLASS = "baseCssClass";
    
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
}

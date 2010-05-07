package org.sgodden.echo.ext20;

import nextapp.echo.app.Component;

/**
 * A Label component which allows HTML markup.
 * 
 * @author mwhittaker
 */
public class HtmlLabel extends Label {

	//search for ^[^*]*<br within core - consider change to new Label().setHtml() to keep intended html
	
	private static final long serialVersionUID = 1L;
	
	 /**
     * The HTML markup to display in the label.
     */
    public static final String PROPERTY_HTML = "html";
    
    private Component component;

    /**
     * Constructs a blank label
     */
    public HtmlLabel() {
    	this(null);
    }

    /**
     * Constructs a label containing HTML markup.
     * @param labelText
     */
    public HtmlLabel(String labelText) {
        super();
        setText(labelText);
    }
    
    /**
     * @param text the HTML markup to set on this label
     */
    public void setText(String text) {
    	
        set(PROPERTY_HTML, text);
    }

    /**
     * Sets the Label for a specific component
     * @param component the Component with which to associate this Label.
     */
    public void setLabelFor(Component component) {
        this.component = component;
    }

    /**
     * gets the Label for a specific component
     * @return the Component with which this Label is associated
     */
    public Component getLabelFor() {
        return this.component;
    }
      
    /**
     * Gets the HTML markup for this label
     * @return the HTML markup of this label
     */
    public String getText() {
        return (String)get(PROPERTY_HTML);
    }
}

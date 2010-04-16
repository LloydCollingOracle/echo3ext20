package org.sgodden.echo.ext20;

import nextapp.echo.app.Component;

/**
 * A Label component.
 * 
 * @author bwoods
 */
@SuppressWarnings("serial")
public class Label extends ExtComponent {
	
    /**
     * The text to display in the label.
     */
    public static final String PROPERTY_TEXT = "text";
    
    private Component component;

    /**
     * Constructs a blank label
     */
    public Label() {
    	this(null);
    }

    /**
     * Constructs a label containing text.
     * 
     * @param labelText
     */
    public Label(String labelText) {
        super();
        setText(labelText);
    }

    public void setText(String labelText) {
        set(PROPERTY_TEXT, labelText);
    }

    public void setLabelFor(Component component) {
        this.component = component;
    }

    public Component getLabelFor() {
        return this.component;
    }
    
    public String getText() {
        return (String)get(PROPERTY_TEXT);
    }
}

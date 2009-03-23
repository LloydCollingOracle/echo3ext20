package org.sgodden.echo.ext20;

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
    public static final String TEXT_PROPERTY = "text";

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
        set(TEXT_PROPERTY, labelText);
    }

    public void setText(String labelText) {
        set(TEXT_PROPERTY, labelText);
    }
}

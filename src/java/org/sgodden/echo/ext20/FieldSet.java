/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sgodden.echo.ext20;

import org.sgodden.echo.ext20.layout.FormLayout;
import org.sgodden.echo.ext20.layout.Layout;

/**
 * A container used for grouping fields.
 * <p/>
 * Sample code:
 * <pre class="code">
 *   FieldSet fieldSet = new FieldSet();
 *   fieldSet.setTitle("My field set");
 *   fieldSet.add(new TextField("", "Name");
 *   ...
 * </pre>
 * 
 * @author sgodden
 */
@SuppressWarnings({"serial"})
public class FieldSet 
        extends Panel {
    
    public static final String CHECKBOX_TOGGLE_PROPERTY = "checkboxToggle";
    
    /**
     * Creates a new field set, using a form layout.
     */
    public FieldSet() {
        super(new FormLayout());
        setBorder(true);
    }
    
    /**
     * Creates a new field set, using the specified layout.
     * @param layout
     */
    public FieldSet(Layout layout) {
        super(layout);
        setBorder(true);
    }
    
    /**
     * Specified whether to render a checkbox into the fieldset frame 
     * just in front of the legend.
     * @param toggle whether to add a checkbox.
     */
    public void setCheckboxToggle(boolean toggle) {
        setComponentProperty(CHECKBOX_TOGGLE_PROPERTY, toggle);
    }

}

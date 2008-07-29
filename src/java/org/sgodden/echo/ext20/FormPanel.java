package org.sgodden.echo.ext20;

import nextapp.echo.app.Component;
import nextapp.echo.app.Label;

import org.sgodden.echo.ext20.layout.TableLayout;
import org.sgodden.echo.ext20.layout.TableLayoutData;

/**
 * A panel used to display forms.  Internally, this uses
 * a table layout, and provides convenience methods to add
 * a field label and component together.
 * @author sgodden
 */
@SuppressWarnings("serial")
public class FormPanel extends Panel {
    
    /**
     * Creates a new form panel to hold a single column
     * of form fields.
     */
    public FormPanel(){
        super();
        setColumns(1);
    }
    
    /**
     * Creates a new form panel to hold the specified
     * number of columns of form fields.
     * @param cols the number of columns of form fields.
     */
    public FormPanel(int cols) {
        super();
        setColumns(cols);
    }
    
    /**
     * Adds the specified component, with the specified
     * field label.
     * @param c the component to add.
     * @param fieldLabel the field label, or <code>null</code>
     * for no field label.
     */
    public void add(Component c, String fieldLabel) {
        Label l;
        if (fieldLabel != null) {
            l = new Label(fieldLabel + ":");
        }
        else {
            l = new Label(" ");
        }
        TableLayoutData tld = new TableLayoutData();
        tld.setCellAlign("right");
        tld.setCellVAlign("top");
        l.setLayoutData(tld);
        super.add(l);
        
        tld = new TableLayoutData();
        tld.setCellVAlign("top");
        c.setLayoutData(tld);
        super.add(c);
    }
    
    /**
     * Adds the specified component, with no field label.
     */
    public void add(Component c) {
        add(c, null);
    }
    
    /**
     * A convenience method to enable one-shot specification of
     * form components, for example in a groovy script.
     * <p/>
     * You will normally want to use this method, rather than
     * {@link #setComponents(Component[]), which would result in
     * no field label for all of your form components.
     * @param formFields the form fields.
     */
    public void setFormComponents(FormField[] formFields) {
        for (FormField field : formFields) {
            add(field.getField(), field.getLabel());
        }
    }

    /**
     * Sets the number of columns of form components that this form
     * will have.  Note that this container uses a table layout
     * internally, and handles multiplying the requested number of columns
     * by 2 in order to have a label and field for each form component.
     * <p/>
     * Therefore, if you want two columns of form components, pass 2 as
     * the argument to this method, not 4.
     * @param columns the number of columns of form components.
     */
    public void setColumns(int columns) {
        TableLayout tl = new TableLayout(columns * 2);
        tl.setFullWidth(false);
        tl.setCellSpacing(5);
        setLayout(tl);
    }
    
    /**
     * Enumeration of field label positions in relation to
     * their components.
     * @author sgodden
     */
    public static enum FieldLabelPosition {
        /**
         * Indicates that the field label should be placed
         * in the leading position in relation to its component,
         * that is, to the left in a LTR language, and to the right
         * in a RTL language.
         */
        LEADING,
        /**
         * Indicates that the field label should be placed
         * above its component.
         */
        ABOVE
    }

}

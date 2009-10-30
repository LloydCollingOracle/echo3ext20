package org.sgodden.echo.ext20;

import java.util.HashSet;
import java.util.Set;

import nextapp.echo.app.Component;

import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.TableLayout;
import org.sgodden.echo.ext20.layout.TableLayoutData;

/**
 * A panel used to display forms. Internally, this uses a table layout, and
 * provides convenience methods to add a field label and component together.
 * 
 * @author sgodden
 */
@SuppressWarnings("serial")
public class FormGrid extends Panel {
    /**
     * Creates a new form panel to hold a single column of form fields.
     */
    public FormGrid() {
        this(1);
    }

    /**
     * Creates a new form panel to hold the specified number of columns of form
     * fields. If there are more than one columns, a vertical rule separator is
     * specified.
     * 
     * @param cols
     *            the number of columns of form fields.
     */
    public FormGrid(int cols) {
        this(cols, null);
    }

    /**
     * Creates a new form panel to hold the specified number of columns of form
     * fields. If there are more than one columns, a vertical rule separator is
     * specified.
     * 
     * @param cols
     *            the number of columns of form fields.
     * @param columnWidths the width, in pixels of each column.  The length of
     * this array should <code>cols * 2</code>, because a form grid creates
     * two columns per column of fields, one for the label, and one for the field
     * itself.
     */
    public FormGrid(int cols, int[] columnWidths) {
        super();
        setColumns(cols, columnWidths);
        if (getBaseCssClass() != null)
            setBaseCssClass(getBaseCssClass());
    }

    /**
     * Provides the base css class for the form grid
     */
    @Override
    public String getBaseCssClass() {
        return null;
    }

    /**
     * Provides the css class for the field label column
     */
    protected String getFieldLabelColumnCssCls() {
        return null;
    }

    /**
     * Provides the css class for the component column
     * 
     * @return
     */
    protected String getComponentColumnCssCls() {
        return null;
    }

    /**
     * Adds the specified component, with the specified field label.
     * 
     * @param c
     *            the component to add.
     * @param fieldLabel
     *            the field label, or <code>null</code> for no field label.
     */
    public Label add(Component c, String fieldLabel) {
    	return add(c, fieldLabel, 1);
    }
    
    /**
     * Adds the specified component, with the specified label.
     * @param component the component.
     * @param fieldLabel the label for the component.
     * @param componentColspan the colspan of the component.
     * @return the Label component created for the field label.
     */
    public Label add(Component component, String fieldLabel, int componentColspan) {
        Label l;
        if (fieldLabel != null) {
            l = new Label(fieldLabel + ":");
        } else {
            l = new Label(" ");
        }
        l.setLabelFor(component);
        TableLayoutData tld = new TableLayoutData();
        tld.setCellAlign("left");
        tld.setCellVAlign("top");
        if (getFieldLabelColumnCssCls() != null)
            tld.setCellCls(getFieldLabelColumnCssCls());
        l.setLayoutData(tld);
        super.add(l);

        tld = new TableLayoutData();
        tld.setCellVAlign("top");
        tld.setColSpan(componentColspan);
        if (getComponentColumnCssCls() != null)
            tld.setCellCls(getComponentColumnCssCls());
        component.setLayoutData(tld);
        super.add(component);

        return l;    	
    }

    /**
     * Adds the specified component, with the specified field label.
     * 
     * @param c
     *            the component to add.
     * @param fieldLabel
     *            the field label, or <code>null</code> for no field label.
     */
    public void add(Component c, Component fieldLabel) {
    	add(c, fieldLabel, 1);
    }
    
    /**
     * Adds the specified component, with the specified label.
     * @param component the component.
     * @param fieldLabel the label for the component.
     * @param componentColspan the colspan of the component.
     */
    public void add(Component c, Component fieldLabel, int componentColspan) {
        TableLayoutData tld = new TableLayoutData();
        tld.setCellAlign("left");
        tld.setCellVAlign("top");
        if (getFieldLabelColumnCssCls() != null)
            tld.setCellCls(getFieldLabelColumnCssCls());
        fieldLabel.setLayoutData(tld);
        if (fieldLabel instanceof Label) {
            ((Label)fieldLabel).setLabelFor(c);
        }
        super.add(fieldLabel);

        tld = new TableLayoutData();
        tld.setColSpan(componentColspan);
        tld.setCellVAlign("top");
        if (getComponentColumnCssCls() != null)
            tld.setCellCls(getComponentColumnCssCls());
        c.setLayoutData(tld);
        super.add(c);
    }

    /**
     * Adds the specified component, with no field label.
     */
    public void add(Component c) {
        add(c, (String) null);
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
     * Sets the number of columns of form components that this form will have.
     * Note that this container uses a table layout internally, and handles
     * multiplying the requested number of columns by 2 in order to have a label
     * and field for each form component.
     * <p/>
     * Therefore, if you want two columns of form components, pass 2 as the
     * argument to this method, not 4.
     * 
     * @param columns
     *            the number of columns of form components.
     */
    public void setColumns(int columns, int[] columnWidths) {
        int columnsRequired;
        columnsRequired = columns * 2;
        TableLayout tl = new TableLayout(columnsRequired);
        tl.setFullWidth(false);
        tl.setCellSpacing(10);
        if (columnWidths != null) {
        	tl.setColumnWidths(columnWidths);
        }
        setLayout(tl);
    }

    /**
     * Enumeration of field label positions in relation to their components.
     * 
     * @author sgodden
     */
    public static enum FieldLabelPosition {
        /**
         * Indicates that the field label should be placed in the leading
         * position in relation to its component, that is, to the left in a LTR
         * language, and to the right in a RTL language.
         */
        LEADING,
        /**
         * Indicates that the field label should be placed above its component.
         */
        ABOVE
    }

    /**
     * Adds the specified component without field label and sets 
     * the colspan of the component to the supplied colspan.
     */
    public void addFieldWithoutLabel(Component c, int componentColspan){
        TableLayoutData tld = new TableLayoutData();
        tld.setColSpan(componentColspan);
        tld.setCellVAlign("top");
        c.setLayoutData(tld);
        super.add(c);
    }
    
    /**
     * Adds the specified component without field label and sets 
     * the colspan of the component to 2.
     */
    public void addFieldWithoutLabel(Component c){
        addFieldWithoutLabel(c, 2);
    }
}

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

    private boolean separatorsRequired = true;
    private int columnsAdded = 0;
    private int columnCount = 0;
    private int separatorsAdded = 0;
    private int separatorCount = 0;
    private Set<ColumnSeparator> separators = new HashSet<ColumnSeparator>();

    /**
     * Creates a new form panel to hold a single column of form fields.
     */
    public FormGrid() {
        this(1, false);
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
        this(cols, (cols > 1));
    }

    /**
     * Creates a new form panel to hold the specified number of columns of form
     * fields with an optional vertical rule separator.
     */
    public FormGrid(int cols, boolean separatorsRequired) {
        super();
        columnCount = cols;
        this.separatorsRequired = separatorsRequired;
        setColumns(cols);
        setBaseCssClass("form-grid");
    }

    /**
     * Adds the specified component, with the specified field label.
     * 
     * @param c
     *            the component to add.
     * @param fieldLabel
     *            the field label, or <code>null</code> for no field label.
     */
    public void add(Component c, String fieldLabel) {
        Label l;
        if (fieldLabel != null) {
            l = new Label(fieldLabel + ":");
        } else {
            l = new Label(" ");
        }
        TableLayoutData tld = new TableLayoutData();
        tld.setCellAlign("left");
        tld.setCellVAlign("top");
        l.setLayoutData(tld);
        super.add(l);

        tld = new TableLayoutData();
        tld.setCellVAlign("top");
        c.setLayoutData(tld);
        super.add(c);
        addSeparatorAndCss(l);
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
        TableLayoutData tld = new TableLayoutData();
        tld.setCellAlign("left");
        tld.setCellVAlign("top");
        fieldLabel.setLayoutData(tld);
        super.add(fieldLabel);

        tld = new TableLayoutData();
        tld.setCellVAlign("top");
        c.setLayoutData(tld);
        super.add(c);
        if (separatorsRequired)
            addSeparatorAndCss(fieldLabel);
    }

    /**
     * Adds the specified component, with no field label.
     */
    public void add(Component c) {
        add(c, (String) null);
    }

    /**
     * Add column separator if more than one column and pad cells to the right
     * of the separator.
     * 
     * @param l
     *            the label following the separator.
     */
    private void addSeparatorAndCss(Component l) {
        if (separatorsRequired && addSeparator()) {
            super.add(new ColumnSeparator(1));
        }
        if (separatorsRequired && isNewRow()) {
            updateRowSpan();
            if (l instanceof ExtComponent)
                ((ExtComponent) l).setCssClass("standard-field-label-padded");
        } else {
            if (l instanceof ExtComponent)
                ((ExtComponent) l).setCssClass("standard-field-label");
        }
    }

    /**
     * After each new row is added, update the row span of the separator
     * components.
     */
    private void updateRowSpan() {
        for (ColumnSeparator cs : separators) {
            cs.setRowSpan(cs.getRowSpan() + 1);
        }
    }

    /**
     * Is the cell being added the first in a new row?
     * 
     * @return boolean isNewRow
     */
    private boolean isNewRow() {
        columnsAdded++;
        if (columnsAdded < columnCount || columnCount == 1) {
            return false;
        } else {
            columnsAdded = 0;
            return true;
        }
    }

    /**
     * Should a separator cell be added.
     * 
     * @return boolean add a separator.
     */
    private boolean addSeparator() {
        if (separatorsAdded < separatorCount) {
            separatorsAdded++;
            return true;
        } else {
            return false;
        }
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
    public void setColumns(int columns) {
        int columnsRequired;
        if (separatorsRequired) {
            columnsRequired = (columns * 2) + (columns - 1);
        } else {
            columnsRequired = columns * 2;
        }
        TableLayout tl = new TableLayout(columnsRequired);
        tl.setFullWidth(false);
        tl.setCellSpacing(10);
        setLayout(tl);
        if (columns > 1 && separatorsRequired) {
            separatorCount = columns - 1;
        }
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
     * Creates a panel that is styled as a vertical rule separator.
     * 
     * @author bwoods
     * 
     */
    private class ColumnSeparator extends Panel {
        private TableLayoutData tld = new TableLayoutData();

        private ColumnSeparator(int rowSpan) {
            setLayout(new FitLayout());
            this.setRowSpan(rowSpan);
            this.tld.setCellCls("form-grid-separator");
            setLayoutData(tld);
            separators.add(this);
        }

        private void setRowSpan(int rowSpan) {
            tld.setRowSpan(rowSpan);
        }

        private int getRowSpan() {
            return tld.getRowSpan();
        }
    }
}

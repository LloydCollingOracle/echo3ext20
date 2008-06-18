/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sgodden.echo.ext20;

import org.sgodden.echo.ext20.data.SimpleStore;

/**
 * A combo box control with support for autocomplete.
 * <p/>
 * Code example:
 * <pre class="code">
Object[][] data = new Object[2][2];
data[0] = new String[]{"admin", "Administrator"}; // id, description
data[1] = new String[]{"user", "User"};

SimpleStore store = new SimpleStore(
  data,
  0,
  new String[]{"id", "description"});

ComboBox combo = new ComboBox(store);
combo.setFieldLabel("Role");
combo.setDisplayField("description");
combo.setValueField("id");
combo.setTypeAhead(true);

add(combo);
 * </pre>
 * <p/>
 * TODO - selection listener.
 * 
 * @author sgodden
 */
@SuppressWarnings({"serial"})
public class ComboBox 
        extends TextField {
    
    public static final String DISPLAY_FIELD_PROPERTY = "displayField";
    public static final String EDITABLE_PROPERTY = "editable";
    public static final String FORCE_SELECTION_PROPERTY = "forceSelection";
    public static final String STORE_PROPERTY = "store";
    public static final String TYPE_AHEAD_PROPERTY = "typeAhead";
    public static final String VALUE_FIELD_PROPERTY = "valueField";

    /**
     * Creates a new combo box.
     * @param store the store from which to populate the entries.
     */
    public ComboBox(SimpleStore store) {
        super();
        setStore(store);
    }
    
    /**
     * Creates a new combo box.
     * @param store the store from which to populate the entries.
     * @param fieldLabel the field label to be displayed in a form.
     */
    public ComboBox(SimpleStore store, String fieldLabel) {
        this(store);
    }
    
    /**
     * Sets the name of the field in the simple store whose
     * data should be displayed in the drop-down list.
     * @param displayField the name of the store field to display.
     */
    public void setDisplayField(String displayField) {
        setProperty(DISPLAY_FIELD_PROPERTY, displayField);
    }
    
    /**
     * Sets whether the combo box is editable.
     * @param editable whether the combo box is editable.
     */
    public void setEditable(boolean editable) {
        setProperty(EDITABLE_PROPERTY, editable);
    }
    
    /**
     * Sets whether it is mandatory to select one of the entries.
     * @param forceSelection whether a selection is mandatory.
     */
    public void setForceSelection(boolean forceSelection) {
        setProperty(FORCE_SELECTION_PROPERTY, forceSelection);
    }
    
    /**
     * Sets the store from which to populate the entries.
     * @param store the store.
     */
    public void setStore(SimpleStore store) {
        setProperty(STORE_PROPERTY, store);
    }
    
    /**
     * Sets whether type ahead should be enabled (defaults to true).
     * @param typeAhead whether type ahead should be enabled.
     */
    public void setTypeAhead(boolean typeAhead) {
        setProperty(TYPE_AHEAD_PROPERTY, typeAhead);
    }
    
    /**
     * Sets the name of the field from the store which should be returned as 
     * the selected value.
     * @param valueField the the name of the field from the store which should be returned as 
     * the selected value. 
     */
    public void setValueField(String valueField) {
        setProperty(VALUE_FIELD_PROPERTY, valueField);
    }

}
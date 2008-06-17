package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.layout.GridLayoutData;
import nextapp.echo.app.layout.RowLayoutData;

import org.sgodden.echo.ext20.Button;

/**
 * A grid designed to show forms, that is, having 2 columns, the first having a
 * field label, and the second a field.
 * <p>
 * N.B. - do not use the {@link #add(Component)} method directly when using this
 * component. Instead, use the {@link #addFormField(String, Component)} method,
 * which will ensure the correct layout data is placed on the child components.
 * <p/> In order to display a form button, call the {{@link #addButton(Button)}
 * method.
 * @author sgodden
 */
@SuppressWarnings("serial")
public class Form extends Column {

    private Grid grid;
    private Row buttons;

    /**
     * Constructs a new form.
     */
    public Form() {
        super();
        grid = new Grid(2);
        add(grid);
    }
    
    public Form(Integer cols) {
        super();
        grid = new Grid(cols);
        add(grid);
    }

    public void addBlankCell(){
        addFormField("", null);
    }
    
    /**
     * Adds a field and its label.
     * @param fieldLabel the field label.
     * @param field the field itself.
     */
    public void addFormField(String fieldLabel, Component field) {
        Label label = new Label(fieldLabel);

        GridLayoutData gld = new GridLayoutData();
        gld.setInsets(new Insets(5));
            gld.setAlignment(new Alignment(Alignment.TRAILING, Alignment.CENTER));

        label.setLayoutData(gld);

        grid.add(label);
        if (field != null){
            grid.add(field);
        }else{
            grid.add(new Label(""));
        }
    }

    /**
     * Adds a button to the button bar.
     * @param button the button to add.
     */
    public void addButton(Button button) {
        if (buttons == null) {
            buttons = new Row();
            buttons.setAlignment(new Alignment(Alignment.TRAILING,
                    Alignment.CENTER));
            add(buttons);
        }
        RowLayoutData rld = new RowLayoutData();
        rld.setInsets(new Insets(3));
        button.setLayoutData(rld);
        buttons.add(button);
    }
}

package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.list.DefaultListModel;

import org.sgodden.echo.ext20.AutocompleteCombo;
import org.sgodden.echo.ext20.AutocompleteComboWithTrigger;
import org.sgodden.echo.ext20.AutocompleteTextArea;
import org.sgodden.echo.ext20.Container;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.AutocompleteComboWithTrigger.TriggerActionListener;
import org.sgodden.echo.ext20.models.ListModelRemoteAutocompleteModel;

/**
 * Test panel for a auto-completing remote loading combobox
 * 
 * @author Lloyd Colling
 */
public class AutocompleteComboBoxTest extends Panel {
	public AutocompleteComboBoxTest() {
		super( "AutocompleteComboBox Test");
	}

	@Override
	public void init() {
	    DefaultListModel listModel = new DefaultListModel(new String[] {
	            "Apples",
	            "Bananas",
	            "Cabbage",
	            "Carrots",
	            "Cherrys",
	            "Cucumber",
	            "Eggplant",
	            "Grapes",
	            "Kumquats",
	            "Lettuce",
	            "Melons",
	            "Nectarines",
	            "Oranges",
	            "Parsnip",
	            "Peach",
	            "Peppers",
	            "Spring Onion",
	            "Tomato"
	    });
		AutocompleteCombo box = new AutocompleteCombo();
		box.setRemoteModel(new ListModelRemoteAutocompleteModel(listModel));
		box.setListTitle("Fruit & Vegetables");
		box.setListPageSize(1);
		add(box);
		
		Container c = new Container();
		c.setHeight(200);
		add(c);
		
		AutocompleteComboWithTrigger boxt = new AutocompleteComboWithTrigger();
        boxt.setRemoteModel(new ListModelRemoteAutocompleteModel(listModel));
        boxt.setListTitle("Fruit & Vegetables");
        boxt.addTriggerActionListener(new TriggerActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("Trigger pressed!");
            }
        });
        add(boxt);
        
        c = new Container();
        c.setHeight(200);
        add(c);
		
		listModel = new DefaultListModel(new String[] {
                "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">",
                "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">",
                "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\" \"http://www.w3.org/TR/html4/frameset.dtd\">",
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">",
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">",
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">"
        });

		AutocompleteTextArea box2 = new AutocompleteTextArea();
        box2.setRemoteModel(new ListModelRemoteAutocompleteModel(listModel));
        box2.setListWidth(400);
        box2.setListMaxHeight(300);
        box2.setListTitle("Doctype List");
        box2.setListCssClass("wrapping-combo-list");
        add(box2);
        
        box.setFocusPreviousId(box2.getRenderId());
        box2.setFocusNextId(box.getRenderId());
	}
}

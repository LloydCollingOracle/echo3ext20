package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.list.DefaultListModel;

import org.sgodden.echo.ext20.AutocompleteCombo;
import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Container;
import org.sgodden.echo.ext20.CustomComboBox;
import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.models.ListModelRemoteAutocompleteModel;

/**
 * Test panel for a auto-completing remote loading combobox
 * 
 * @author Lloyd Colling
 */
public class AutocompleteComboBoxTest extends Panel {
	public AutocompleteComboBoxTest() {
		super( "AutocompleteComboBox Test");
		createUI();
	}

	private void createUI() {
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
		final AutocompleteCombo box = new AutocompleteCombo();
		box.setRemoteModel(new ListModelRemoteAutocompleteModel(listModel));
		box.setListTitle("Fruit & Vegetables");
		box.setListPageSize(1);
		
		add(box);
	}
}

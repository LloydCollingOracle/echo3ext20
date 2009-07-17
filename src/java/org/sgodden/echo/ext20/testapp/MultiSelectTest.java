package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.list.DefaultListModel;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.MultiSelectComboBox;
import org.sgodden.echo.ext20.Panel;

public class MultiSelectTest extends Panel {
	public MultiSelectTest() {
		super( "Multi Select Test");
		createUI();
	}

	public void createUI() {		
		DefaultListModel model = new DefaultListModel( new String[]{ "aaaa", "bbb", "ccc"});
		createDemo(model, 1);
		createDemo(model, 2);
		createDemo(model, 3);
		createDemo(model, 4);
		createDemo(model, 5);
		createDemo(model, 6);
		createDemo(model, 7);
		
	}

	private void createDemo(DefaultListModel model, int selectionMode) {
		final MultiSelectComboBox multiSelectComboBox = new MultiSelectComboBox( model);

		setSelection(multiSelectComboBox, selectionMode);
		
		add( multiSelectComboBox);
		Button button = new Button( "Ok");
		add( new Label( "Selected value is: "));
		final Label result = new Label("");
		add( result);
		button.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				result.setText( multiSelectComboBox.getValue());
			}
		});
		add( button);
	}

	private void setSelection(final MultiSelectComboBox multiSelectComboBox, int mode) {
		switch( mode) {
		case 1:
			multiSelectComboBox.getSelectionModel().setSelectedIndex( 1, true);
			multiSelectComboBox.getSelectionModel().setSelectedIndex( 2, true);
			break;
		case 2:
			multiSelectComboBox.setValue( "aaaa,ccc");
			break;
		case 3:
			multiSelectComboBox.setMultiSelect( false);
			multiSelectComboBox.setValue( "bbb"); // in the list
			break;
		case 4:
			multiSelectComboBox.setMultiSelect( false);
			multiSelectComboBox.setValue( "haha"); // not in the list
			break;
		case 5:
			multiSelectComboBox.setMultiSelect( false);
			multiSelectComboBox.setEditable( false);
			multiSelectComboBox.setValue( "bbb"); // in the list
			break;
		case 6:
			multiSelectComboBox.setSeparator( ";"); 
			multiSelectComboBox.setValue( "bbb"); 
			break;
		case 7:
			multiSelectComboBox.setAllowBlank( false);
			multiSelectComboBox.setBlankText( "Can't be blank");
			break;
		}
	}
}

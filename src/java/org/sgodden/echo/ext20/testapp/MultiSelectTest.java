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
		setAutoScroll( true);
		createUI();
	}

	public void createUI() {		
		DefaultListModel model = new DefaultListModel( new String[]{ "aaaa", "bbb", "ccc"});
		for( int mode=1; mode<15; mode++)
			createDemo(model, mode);		
	}
	private MultiSelectComboBox slaveSingleCombo;
	private MultiSelectComboBox slaveMultiCombo;
	private MultiSelectComboBox disableCombo;
	
	private void createDemo(DefaultListModel model, int mode) {
		final MultiSelectComboBox multiSelectComboBox = new MultiSelectComboBox( model);
		if ( mode == 2) slaveMultiCombo = multiSelectComboBox;
		if ( mode == 5) slaveSingleCombo = multiSelectComboBox;
		if ( mode == 8) disableCombo = multiSelectComboBox;
 		Label label = new Label( "Selected value is: ");

		setSelection(multiSelectComboBox, mode, label);
		
		add( multiSelectComboBox);
		Button button = new Button( "Ok");
		add( label);
		final Label result = new Label("");
		add( result);
		button.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				result.setText( multiSelectComboBox.getValue());
			}
		});
		add( button);
	}

	private void setSelection(final MultiSelectComboBox multiSelectComboBox, int mode, final Label label) {
		switch( mode) {
		case 1:
			label.setText("Simple multi select test with id selected. Result:");
			multiSelectComboBox.getSelectionModel().setSelectedIndex( 1, true);
			multiSelectComboBox.getSelectionModel().setSelectedIndex( 2, true);
			break;
		case 2:
			label.setText("Simple multi select test with value selected. Result:");
			multiSelectComboBox.setValue( "aaaa,ccc");
			break;
		case 3:
			label.setText("Simple single select test with value in the list. Result:");
			multiSelectComboBox.setMultiSelect( false);
			multiSelectComboBox.setValue( "bbb"); // in the list
			break;
		case 4:
			label.setText("Simple single select test with value not in the list. Result:");
			multiSelectComboBox.setMultiSelect( false);
			multiSelectComboBox.setValue( "haha"); // not in the list
			break;
		case 5:
			label.setText("Single select not editable. Result:");
			multiSelectComboBox.setMultiSelect( false);
			multiSelectComboBox.setEditable( false);
			multiSelectComboBox.setValue( "bbb"); // in the list
			break;
		case 6:
			label.setText("Multi select with other separator. Result:");
			multiSelectComboBox.setSeparator( ";"); 
			multiSelectComboBox.setValue( "bbb"); 
			break;
		case 7:
			label.setText("Select can't be blank. Result:");
			multiSelectComboBox.setAllowBlank( false);
			multiSelectComboBox.setBlankText( "Can't be blank");
			break;
		case 8:
			label.setText("Single select disabled. Result:");
			multiSelectComboBox.setMultiSelect( false);
			multiSelectComboBox.setValue( "Read only!"); 
			multiSelectComboBox.setEnabled( false);
			break;
		case 9:
			label.setText("Multi select with action listener. Result:");
			multiSelectComboBox.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {			
					label.setText( "Multi select with action listener, The data is changed to " + multiSelectComboBox.getValue() +". Result: ");
					slaveSingleCombo.setValue( "slave value" + multiSelectComboBox.getValue());
					disableCombo.setEnabled( !disableCombo.isEnabled());
					disableCombo.setValue( "It's " + (disableCombo.isEnabled() ? "enabled" : "disabled") + " now!");
					slaveMultiCombo.setValue( multiSelectComboBox.getValue());
				}
			});
			break;
		case 10:
			label.setText("Single select with action listener. Result:");
			multiSelectComboBox.setMultiSelect( false);
			multiSelectComboBox.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					label.setText( "Single select with action listener, The data is changed to " + multiSelectComboBox.getValue() +". Result: ");
				}
			});
			break;
		case 11:
		    label.setText("ComboBox with number as value, single select");
		    multiSelectComboBox.setModel( new DefaultListModel( new Integer[]{ 1, 2, 3}));
		    multiSelectComboBox.setMultiSelect( false);
		    break;
		case 12:
            label.setText("ComboBox with number string as value, single select");
            multiSelectComboBox.setModel( new DefaultListModel( new String[]{ "1", "2", "3"}));
            multiSelectComboBox.setMultiSelect( false);
		    break;
		case 13:
		    label.setText("ComboBox with number as value, multi select");
		    multiSelectComboBox.setModel( new DefaultListModel( new Integer[]{ 1, 2, 3}));
		    break;
		case 14:
		    label.setText("ComboBox with number string as value, multi select");
		    multiSelectComboBox.setModel( new DefaultListModel( new String[]{ "1", "2", "3"}));
		    break;
		}
	}
}

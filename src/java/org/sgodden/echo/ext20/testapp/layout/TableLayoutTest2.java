package org.sgodden.echo.ext20.testapp.layout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import nextapp.echo.app.Component;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.list.DefaultListModel;
import nextapp.echo.app.list.ListModel;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.ComboBox;
import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.TableLayout;

/**
 * Test for {@link TableLayout}.
 * @author sgodden
 */
@SuppressWarnings("serial")
public class TableLayoutTest2 
        extends Panel {
    
	private int index = 0;
    private Panel inner;
    private Button removeRowButton;
    
    public TableLayoutTest2(){
        super("Table 2");
        
        setLayout(new FitLayout());
        
        inner = new Panel();
        add(inner);
        inner.setAutoScroll(true);
        
        TableLayout layout = new TableLayout(5);
        // try out most of the options
        layout.setFullWidth(true);
        layout.setCellPadding(10, 10, 0, 10);
        layout.setBorder(true);
        inner.setLayout(layout);
        
        /*
         * Create components for the first row.
         */
        addRow(makeRow());
        
        addButton(makeAddRowButton());
        
        removeRowButton = makeRemoveRowButton();
        addButton(removeRowButton);
        
        addButton(makeRemoveAllAndAddButton());
        addButton(makeInsertInMiddleButton());
        addButton(makeDeleteSecondRowButton());
        addButton(makeSwapRowsButton());
        
    }
    
    private Button makeAddRowButton() {
        Button ret = new Button("Add a row of labels");
        ret.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	addRow(makeRow());
                removeRowButton.setEnabled(true);
            }});
        return ret;
    }
    
    private Button makeRemoveRowButton() {
        final Button ret = new Button("Remove last row");
        ret.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                if (inner.getComponentCount() >= 5) {
                    inner.remove(inner.getComponentCount()-1);
                    inner.remove(inner.getComponentCount()-1);
                    inner.remove(inner.getComponentCount()-1);
                    inner.remove(inner.getComponentCount()-1);
                    inner.remove(inner.getComponentCount()-1);
                }
                if (inner.getComponentCount() == 0) {
                    ret.setEnabled(false);
                }
            }});
        return ret;
    }
    
    private Button makeRemoveAllAndAddButton() {
        final Button ret = new Button("Remove all and add");
        ret.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	inner.removeAll();
            	index = 0;
                addRow(makeRow());
            }});
        return ret;
    }
    
    private Button makeInsertInMiddleButton() {
        final Button ret = new Button("Insert second row");
        ret.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	Component[] row = makeRow();
                inner.add(row[0], 5);
                inner.add(row[1], 6);
                inner.add(row[2], 7);
                inner.add(row[3], 8);
                inner.add(row[4], 9);
            }});
        return ret;    	
    }
    
    private Button makeDeleteSecondRowButton() {
        final Button ret = new Button("Delete second row");
        ret.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                inner.remove(5);
                inner.remove(5);
                inner.remove(5);
                inner.remove(5);
                inner.remove(5);
            }});
        return ret;    	
    }
    
    private Button makeSwapRowsButton() {
    	final Button ret = new Button("Swap rows 2 and 3");
    	ret.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				List<Component> comps = new ArrayList<Component>();
				comps.add(inner.getComponent(5));
				comps.add(inner.getComponent(6));
				comps.add(inner.getComponent(7));
				comps.add(inner.getComponent(8));
				comps.add(inner.getComponent(9));
				
				inner.remove(5);
				inner.remove(5);
				inner.remove(5);
				inner.remove(5);
				inner.remove(5);
				
				inner.add(comps.get(0), 10);
				inner.add(comps.get(1), 11);
				inner.add(comps.get(2), 12);
				inner.add(comps.get(3), 13);
				inner.add(comps.get(4), 14);
			}
		});
    	return ret;
    }
    
    private void addRow(Component[] row) {
    	for (Component c : row) {
    		inner.add(c);
    	}
    }
    
    private Component[] makeRow() {
    	Component[] ret = new Component[5];
    	String text = "Blah " + ++index;
    	ret[0] = new ComboBox(makeComboBoxModel());
    	ret[1] = new TextField(text);
    	ret[2] = new TextField(text);
    	ret[3] = new TextField(text);
    	ret[4] = new TextField(text);
    	return ret;
    }
    
    private ListModel makeComboBoxModel() {
    	DefaultListModel ret = new DefaultListModel();
    	ret.add("FOO");
    	ret.add("BAR");
    	return ret;
    }

}

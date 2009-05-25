package org.sgodden.echo.ext20.testapp.layout;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Component;
import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;
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
        
        TableLayout layout = new TableLayout(3);
        // try out most of the options
        layout.setFullWidth(true);
        layout.setCellPadding(10, 10, 0, 10);
        layout.setBorder(true);
        inner.setLayout(layout);
        
        /*
         * Create components for the first row.
         */
        inner.add(makeLabel());
        inner.add(makeLabel());
        inner.add(makeLabel());
        
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
                inner.add(makeLabel());
                inner.add(makeLabel());
                inner.add(makeLabel());
                removeRowButton.setEnabled(true);
            }});
        return ret;
    }
    
    private Button makeRemoveRowButton() {
        final Button ret = new Button("Remove last row");
        ret.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                if (inner.getComponentCount() >= 3) {
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
                inner.add(makeLabel());
                inner.add(makeLabel());
                inner.add(makeLabel());
            }});
        return ret;
    }
    
    private Button makeInsertInMiddleButton() {
        final Button ret = new Button("Insert second row");
        ret.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                inner.add(makeLabel(), 3);
                inner.add(makeLabel(), 4);
                inner.add(makeLabel(), 5);
            }});
        return ret;    	
    }
    
    private Button makeDeleteSecondRowButton() {
        final Button ret = new Button("Delete second row");
        ret.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                inner.remove(3);
                inner.remove(3);
                inner.remove(3);
            }});
        return ret;    	
    }
    
    private Button makeSwapRowsButton() {
    	final Button ret = new Button("Swap rows 2 and 3");
    	ret.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				List<Component> comps = new ArrayList<Component>();
				comps.add(inner.getComponent(3));
				comps.add(inner.getComponent(4));
				comps.add(inner.getComponent(5));
				
				inner.remove(3);
				inner.remove(3);
				inner.remove(3);
				
				inner.add(comps.get(0), 6);
				inner.add(comps.get(1), 7);
				inner.add(comps.get(2), 8);
			}
		});
    	return ret;
    }
    
    private Label makeLabel() {
    	return new Label("Label " + ++index);
    }

}

package org.sgodden.echo.ext20.testapp.layout;

import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
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
    
	Panel inner;
	Button removeButton;
	Button removeRowButton;
	
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
        inner.add(new Label("Row 1 Column 1"));
        inner.add(new Label("Row 1 Column 2"));
        inner.add(new Label("Row 1 Column 3"));
        
        addButton(makeAddButton());
        removeButton = makeRemoveButton();
        addButton(removeButton);
        
        addButton(makeAddRowButton());
        removeRowButton = makeRemoveRowButton();
        addButton(removeRowButton);
        
    }
    
    private Button makeAddButton() {
		Button ret = new Button("Add a label");
		ret.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				inner.add(new Label("Another label"));
				removeButton.setEnabled(true);
			}});
		return ret;
	}
    
    private Button makeAddRowButton() {
		Button ret = new Button("Add a row of labels");
		ret.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				inner.add(new TextField("text field 1"));
				inner.add(new TextField("text field 2"));
				inner.add(new TextField("text field 3"));
				removeRowButton.setEnabled(true);
			}});
		return ret;
	}
    
    private Button makeRemoveButton() {
		final Button ret = new Button("Remove last component");
		ret.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (inner.getComponentCount() > 0) {
					inner.remove(inner.getComponentCount()-1);
				}
				if (inner.getComponentCount() == 1) {
					ret.setEnabled(false);
				}
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
				if (inner.getComponentCount() <= 3) {
					ret.setEnabled(false);
				}
			}});
		return ret;
	}

}

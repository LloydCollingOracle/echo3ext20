package org.sgodden.echo.ext20.testapp.layout;

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
    
	Panel inner;
	Button removeButton;
	
    public TableLayoutTest2(){
        super("Table 2");
        
        setLayout(new FitLayout());
        
        inner = new Panel();
        add(inner);
        
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

}

package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.CheckboxField;
import org.sgodden.echo.ext20.FieldSet;
import org.sgodden.echo.ext20.FormGrid;
import org.sgodden.echo.ext20.HtmlEditor;
import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextArea;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.TableLayoutData;

public class FormGridTest extends Panel {
	
	public FormGridTest() {
		super("Form grid");
		setLayout(new FitLayout());
		
		Panel container = new Panel();
		container.setAutoScroll(true);
		add(container);
		
		FieldSet fs1 = new FieldSet();
		fs1.setTitle("With row span");
		container.add(fs1);
		
        FieldSet fs2 = new FieldSet();
        fs2.setTitle("Without row span, with insertion and removal of values");
        container.add(fs2);
		
        {
    		final FormGrid grid = new FormGrid(2, new int[]{100, 300});
    		grid.add(new TextField(), "Field 1");
    		grid.add(new TextField(), "Field 2");
    		TextArea ta = new TextArea();
    		ta.setSize(700, 200);
    		Label l = grid.add(ta, "Text area",3);
    		((TableLayoutData)ta.getLayoutData()).setRowSpan(2);
            ((TableLayoutData)l.getLayoutData()).setRowSpan(2);
    		
    		grid.add(new TextField(), "Field 3");
    		grid.add(new TextField(), "Field 4");
    		final CheckboxField check = new CheckboxField(false);
            grid.add(check, "Field 5");
            grid.add(new TextField(), "Field 6");
            grid.add(new TextField(), "Field 7");
    		
    		fs1.add(grid);
        }
        
        {
            final FormGrid grid = new FormGrid(2, new int[]{100, 300});
            grid.add(new TextField(), "Field 1");
            grid.add(new TextField(), "Field 2");
            TextArea ta = new TextArea();
            ta.setSize(700, 200);
            Label l2 = grid.add(ta, "Text area",3);
            
            grid.add(new TextField(), "Field 3");
            grid.add(new TextField(), "Field 4");
            final CheckboxField check = new CheckboxField(false);
            grid.add(check, "Field 5");
            grid.add(new TextField(), "Field 6");
            grid.add(new TextField(), "Field 7");
            
            final TextField field8 = new TextField();
            final TextField field9 = new TextField();
            final Label label8 = new Label("Field 8");
            final Label label9 = new Label("Field 9");
            check.addActionListener(new ActionListener() {
                
                public void actionPerformed(ActionEvent arg0) {
                    if (grid.indexOf(field8) > -1) {
                        // remove
                        grid.remove(label8);
                        grid.remove(field8);
                        grid.remove(label9);
                        grid.remove(field9);
                    } else {
                        // add
                        int index = grid.indexOf(check);
                        grid.add(field9, index+1);
                        grid.add(label9, index+1);
                        grid.add(field8, index+1);
                        grid.add(label8, index+1);
                    }
                }
            });
            
            fs2.add(grid);
        }
	}
}

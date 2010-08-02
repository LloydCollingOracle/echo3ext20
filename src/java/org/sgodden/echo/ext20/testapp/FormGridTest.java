package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.CheckboxField;
import org.sgodden.echo.ext20.FormGrid;
import org.sgodden.echo.ext20.HtmlEditor;
import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextArea;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.layout.FitLayout;

public class FormGridTest extends Panel {
	
	public FormGridTest() {
		super("Form grid");
		setLayout(new FitLayout());
		
		final FormGrid grid = new FormGrid(2, new int[]{100, 300});
		grid.add(new TextField(), "Field 1");
		grid.add(new TextField(), "Field 2");
		TextArea ta = new TextArea();
		ta.setSize(700, 200);
		grid.add(ta, "Text area", 3);
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
		
		add(grid);
	}
}

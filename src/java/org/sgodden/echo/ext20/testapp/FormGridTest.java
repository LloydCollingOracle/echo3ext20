package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.FormGrid;
import org.sgodden.echo.ext20.HtmlEditor;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextArea;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.layout.FitLayout;

public class FormGridTest extends Panel {
	
	public FormGridTest() {
		super("Form grid");
		setLayout(new FitLayout());
		
		FormGrid grid = new FormGrid(2, new int[]{100, 300});
		grid.add(new TextField(), "Field 1");
		grid.add(new TextField(), "Field 2");
		TextArea ta = new TextArea();
		ta.setSize(700, 200);
		grid.add(ta, "Text area", 3);
		grid.add(new TextField(), "Field 3");
		grid.add(new TextField(), "Field 4");
		
		add(grid);
	}
}

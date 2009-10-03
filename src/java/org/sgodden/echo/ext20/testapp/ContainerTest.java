package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.Container;
import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.TableLayout;

@SuppressWarnings("serial")
public class ContainerTest extends Panel {
	
	public ContainerTest() {
		super("Container");
		setLayout(new FitLayout());
		
		Container ct = new Container(new TableLayout(2));
		add(ct);
		
		ct.add(new Label("Label 1"));
		ct.add(new Label("Label 2"));
		ct.add(new Label("Label 3"));
		ct.add(new Label("Label 4"));
	}
}

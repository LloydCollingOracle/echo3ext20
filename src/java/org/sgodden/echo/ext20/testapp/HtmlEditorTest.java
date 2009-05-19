package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.HtmlEditor;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.FitLayout;

public class HtmlEditorTest extends Panel {
	
	public HtmlEditorTest() {
		super("Html editor");
		setLayout(new FitLayout());
		
		final HtmlEditor editor = new HtmlEditor();
		add(editor);
		
		editor.setText("<b>Here is some initial text</b>");
		
		Button printTextButton = new Button("Show text");
		printTextButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				System.out.println("The text is: " + editor.getText());
			}});
		
		addButton(printTextButton);
	}
}

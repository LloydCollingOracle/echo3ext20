package org.sgodden.echo.ext20;

import org.sgodden.echo.ext20.layout.Layout;

public class HtmlPanel extends Panel {
	public HtmlPanel() {
		this("");
	}

	public HtmlPanel(String title) {
		super( title);
	}
	
	 /**
     * Creates a new HTML panel.
     * @param layout the layout for the panel.
     */
    public HtmlPanel(Layout layout) {
        super(layout);
    }
    
	@Override
	public void setLayout(Layout layout) {
		throw new RuntimeException( "Don't need layout here");
	}
}

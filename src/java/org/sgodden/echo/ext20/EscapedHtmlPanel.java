package org.sgodden.echo.ext20;

import org.apache.commons.lang.StringEscapeUtils;
import org.sgodden.echo.ext20.layout.Layout;

/**
 * An HtmlPanel with HTML entity escaping behaviour.  Any HTML set on the panel
 * will be escaped so that, other than line breaks, it will not to render as 
 * HTML within the browser.
 * @author mwhittaker
 */
public class EscapedHtmlPanel extends HtmlPanel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Create an escaped HTML panel.
	 */
	public EscapedHtmlPanel() {
		this("");
	}

	/**
	 * Create an escaped HTML panel.
	 * @param title the title to set
	 */
	public EscapedHtmlPanel(String title) {
		super(title);
	}
	
	/**
	 * Create an escaped HTML panel.
	 * @param layout the layout to set
	 */
	public EscapedHtmlPanel(Layout layout) {
		super(layout);
	}

	@Override
	public void setLayout(Layout layout) {
		throw new UnsupportedOperationException( "Don't need layout here");
	}
	
	/**
	 * Sets the html on this panel.  HTML will be encoded using entity encoding.
	 * New lines will be preserved.
	 * @param html the html to set
	 */
	@Override
	public void setHtml(String html) {
        String escaped = StringEscapeUtils.
        	escapeHtml(html).replaceAll("\\n", "<br>");
		super.setHtml(escaped);
	}
	
	
}

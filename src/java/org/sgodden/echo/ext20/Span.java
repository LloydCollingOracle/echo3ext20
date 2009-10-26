package org.sgodden.echo.ext20;

import nextapp.echo.app.Component;

/**
 * An extremely simple component that renders a span containing
 * the specified HTML text.
 * 
 * @author Lloyd Colling
 *
 */
public class Span extends Component {

    private static final long serialVersionUID = 20091026L;
    
    public static final String PROPERTY_HTML = "html";
    
    public Span(String html) {
        super();
        setHtml(html);
    }
    
    public Span() {
        super();
    }
    
    public void setHtml(String html) {
        set(PROPERTY_HTML, html);
    }
    
    public String getHtml() {
        return (String)get(PROPERTY_HTML);
    }
}

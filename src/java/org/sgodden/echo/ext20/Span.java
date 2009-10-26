package org.sgodden.echo.ext20;

import nextapp.echo.app.Component;

public class Span extends Component {

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

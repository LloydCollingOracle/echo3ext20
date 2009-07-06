package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.HtmlLayoutData;
import org.sgodden.echo.ext20.HtmlPanel;
import org.sgodden.echo.ext20.TextField;

public class HtmlPanelTest extends HtmlPanel {
    private TextField textField1 = new TextField("HtmlPanel");
    private final TextField textField2 = new TextField("Hello from field2");
    private SimplePanelWithLabelAndField simplePanel = new SimplePanelWithLabelAndField();
    private SimplePanelWithLabelAndField simplePanel2 = new SimplePanelWithLabelAndField();

    public HtmlPanelTest() {
        super("Test HtmlPanel");
        setHtml("<html><body><div>This is a HtmlPanel include a field <div id=\"field1\"></div></div> And another HtmlPanel <div id=\"simplePanel\"></div> And again <div id=\"simplePanel2\"></div> and <br/> <div id=\"field2\"></div></div></body></html>");
        textField1.setLayoutData(new HtmlLayoutData("field1"));
        add(textField1);
        simplePanel.setLayoutData(new HtmlLayoutData("simplePanel"));
        add(simplePanel);
        simplePanel2.setLayoutData(new HtmlLayoutData("simplePanel2"));
        add(simplePanel2);
        textField2.setLayoutData(new HtmlLayoutData("field2"));
        textField2.setRenderId("testfield2");
        add(textField2);

    }
}

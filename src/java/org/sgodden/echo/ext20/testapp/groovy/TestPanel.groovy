package org.sgodden.echo.ext20.testapp.groovy;

import nextapp.echo.app.Color;
import nextapp.echo.app.Label;

import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.layout.TableLayout;
import nextapp.echo.app.Component

public class TestPanel extends Panel {

    public TestPanel() {
        title = "A groovy panel";
        border = true;
        layout = new TableLayout([
            columns: 2,
            fullWidth: true
        ]);
        components = [
            new Label(
                text: "Label 1"
            ),
            new Label(
                text: "Label 2"
            ),
            new Label(
                text: "Label 3"
            ),
            makePanel2(),
            new TestEchoGrid()
        ];
    }
    
    private Panel makePanel2() {
        Panel ret = new Panel([
            background: Color.BLUE,
            bodyBackground: Color.BLUE,
            html: "Hello!!",
            buttons: [
                new Button([
                    text: "Click me",
                    actionPerformed: {evt -> println "You clicked me $evt"}
                ])
            ]
        ]);
    
        return ret;
    }

}
package org.sgodden.echo.ext20.testapp.groovy;

import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Label;

public class TestEchoGrid extends Grid {

    public TestEchoGrid() {
        size = 2;
        background = Color.BLUE;
        width = new Extent(100, Extent.PERCENT);
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
            new Button(
                text: "Click me",
                actionPerformed: {evt -> println "You clicked me: $evt"}
            )
        ];
    }

}
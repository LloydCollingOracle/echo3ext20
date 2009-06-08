package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.Panel;

@SuppressWarnings("serial")
public class WelcomePanel extends Panel {

    public WelcomePanel() {
        setPadding("5px");
        setHtml("<h1><u>Welcome to the Echo3 / Ext20 test application</u></h1>"
                        + "<br/>"
                        + "<p>This test application is very rudimentary right now.</p>"
                        + "<br/>"
                        + "<p>Each tab contains examples of a particular component type.</p>");
        setTitle("Welcome");
    }

}
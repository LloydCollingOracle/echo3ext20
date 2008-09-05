package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Component;
import nextapp.echo.app.Label;
import org.sgodden.echo.ext20.Panel;

/**
 * A simple test showing components created using groovy.
 * @author sgodden
 */
@SuppressWarnings("serial")
public class GroovyTest extends Panel {

    public GroovyTest() {
        setTitle("Groovy");

        add(new Label("The following panel was created using groovy"));

        AppInstance app = (AppInstance) ApplicationInstance.getActive();
        add((Component) app.getGroovyObjectInstance(
                "org.sgodden.echo.ext20.testapp.groovy.TestPanel"));
    }

}

package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.FieldGroupContainer;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.fieldgroup.FieldGroupFactory;
import org.sgodden.echo.ext20.layout.FitLayout;

import nextapp.echo.app.Component;
import nextapp.echo.app.Label;


public class FieldGroupTest extends Panel {

    public FieldGroupTest() {
        super("Field Group");
        setRenderId("FieldGroupTest");
        FieldGroupFactory textPanelFactory = new FieldGroupFactory() {

            public Component getFieldGroup(int index) {
                Panel p = new Panel(new FitLayout());
                p.setBodyTransparent(true);
                p.add(new Label("Group " + index));
                p.add(new TextField(10));
                p.setHeight(48);
                return p;
            }};
            
        FieldGroupContainer cont = new FieldGroupContainer();
        cont.setFieldGroupFactory(textPanelFactory);
        cont.setRenderId("FieldGroupComponent");
        
        add(cont);
    }
}

package org.sgodden.echo.ext20.testapp.regression;

import java.util.Calendar;

import nextapp.echo.app.Column;
import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.DateField;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.TimeField;
import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.FormLayout;

/**
 * Tests the bug whereby removing a echo3 component
 * from an ext container causes an exception within
 * the echo3 synchronisation engine.
 * @author sgodden
 *
 */
@SuppressWarnings("serial")
public class RemoveEchoFromExtTest extends Panel {
    
    private int clicks;
    private Panel form;
    
    public RemoveEchoFromExtTest() {
        super(new FitLayout());
        setTitle("Blah");
        setPadding("10px");
        
        final Column container = new Column();
        add(container);
        //container.setWidth(new Extent(100, Extent.PERCENT));
        
        Label heading = new Label("Click the button bottom right - if the numbers" +
                " just keep incrementing, then all is working");
        container.add(heading);
        
        form = makeExtForm();
        container.add(new TimeField(Calendar.getInstance()));
        
        Button button = new Button("Remove and re-add the grid");
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                clicks++;
//                remove(fs);
//                add(makeFieldSet());
                container.remove(form);
                form = makeExtForm();
                container.add(form);
            }});
        
        addButton(button);
    }
    
    private Panel makeExtForm() {
        
        Panel ct = new Panel(new FitLayout());
        
        Panel ret = new Panel(new FormLayout());
        ct.add(ret);
        
        TextField tf = new TextField("Text field " + clicks);
        tf.setFieldLabel("Field heading " + clicks);
        ret.add(tf);
        
        DateField df = new DateField();
        df.setFieldLabel("Date field");
        ret.add(df);
        
        TimeField tmf = new TimeField(Calendar.getInstance());
        ret.add(tmf);
        
        return ct;
    }

}

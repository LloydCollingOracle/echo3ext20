package org.sgodden.echo.ext20.testapp.regression;

import java.util.Calendar;

import nextapp.echo.app.Column;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.DateField;
import org.sgodden.echo.ext20.TimeField;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.layout.FitLayout;

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
    private Column column;
    
    public RemoveEchoFromExtTest() {
        super(new FitLayout());
        
        add(makeColumn());
        
        Button button = new Button("Remove and re-add the grid");
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                clicks++;
                remove(column);
                add(makeColumn());
            }});
        
        addButton(button);
    }
    
    private Column makeColumn() {
        column = new Column();
        column.add(new Label("Click the button bottom right - if the numbers" +
        " just keep incrementing, then all is working"));
        column.add(makeGrid());
        return column;
    }
    
    private Grid makeGrid() {
        Grid ret = new Grid(2);
        ret.setRenderId("theGrid");
        
        ret.add(new Label("Field heading " + clicks));
        
        TextField tf = new TextField("Text field " + clicks);
        tf.setRenderId("theTextField");
        ret.add(tf);
        
        ret.add(new Label("Date field"));
        ret.add(new DateField());

        ret.add(new Label("Time field"));
        ret.add(new TimeField(Calendar.getInstance()));
        
        return ret;
    }

}

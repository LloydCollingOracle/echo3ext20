package org.sgodden.echo.ext20.testapp.regression;

import nextapp.echo.app.Column;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
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
    
    public RemoveEchoFromExtTest() {
        super(new FitLayout());
        
        add(makeColumn());
        
        Button button = new Button("Remove and re-add the grid");
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                clicks++;
                removeAll();
                add(makeColumn());
            }});
        
        addButton(button);
    }
    
    private Column makeColumn() {
        Column ret = new Column();
        ret.add(new Label("Click the button bottom right - if the numbers" +
        " just keep incrementing, then all is working"));
        ret.add(makeGrid());
        return ret;
    }
    
    private Grid makeGrid() {
        Grid ret = new Grid(2);
        ret.setRenderId("theGrid");
        ret.add(new Label("Field heading " + clicks));
        TextField tf = new TextField("Text field " + clicks);
        tf.setRenderId("theTextField");
        ret.add(tf);
        return ret;
    }

}

package org.sgodden.echo.ext20.testapp;

import java.util.Calendar;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.DateField;
import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TimeField;

@SuppressWarnings("serial")
public class TimeFieldTest extends Panel {
	public TimeFieldTest() {
		super( "TimeFieldTest");
		createUI();
	}

	public void createUI() {
	    final DateField dateField = new DateField( Calendar.getInstance());
	    add( dateField);
	    Button nextDay = new Button( "Next Day");
	    nextDay.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Calendar cal = dateField.getCalendar();
                // Make a copy. So that it will fire property change when 
                // the cal is changed.
                Calendar copy = Calendar.getInstance();
                copy.setTime( cal.getTime());
                copy.add( Calendar.DAY_OF_MONTH, 1);

                dateField.setCalendar( copy);
            }
        });
	    add( nextDay);
	    
		final TimeField timeField = new TimeField( Calendar.getInstance());
		add( timeField);
		final Label label = new Label( "Result is");
		add( label);
		Button button = new Button( "Submit");
		button.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendar calendar = timeField.getCalendar();				
				label.setText( "Result is " + calendar.get( Calendar.HOUR_OF_DAY) + ":" + calendar.get( Calendar.MINUTE));
			}
		});
		add( button);
		
		final DateField disableField = new DateField( Calendar.getInstance());
		disableField.setEnabled( false);
		add( disableField);
		Button enable = new Button( "Enable it");
		enable.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                disableField.setEnabled( true);
            }
        });
		add( enable);
	}
}

package org.sgodden.echo.ext20.testapp;

import java.util.Calendar;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.DeferredUiCreate;
import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TimeField;

public class TimeFieldTest extends Panel implements DeferredUiCreate {
	public TimeFieldTest() {
		super( "TimeFieldTest");
	}

	@Override
	public void createUI() {
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
	}
}

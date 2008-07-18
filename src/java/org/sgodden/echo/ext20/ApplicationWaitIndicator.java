package org.sgodden.echo.ext20;

import nextapp.echo.app.HttpImageReference;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Label;

@SuppressWarnings("serial")
public class ApplicationWaitIndicator extends Label{

	public static final String PROPERTY_WAIT_ICON = "waitIcon";
	public static final String PROPERTY_NO_WAIT_ICON = "noWaitIcon";
	
	private final static ImageReference defaultWaitIcon = new HttpImageReference("resources/ext/images/default/grid/wait.gif");
	private final static ImageReference defaultNoWaitIcon = new HttpImageReference("resources/ext/images/default/grid/nowait.gif");
	
	public ApplicationWaitIndicator(){
		super(defaultNoWaitIcon);
		setRenderId("applicationWaitIndicator");
		setWaitIcon(defaultWaitIcon);
		setNoWaitIcon(defaultNoWaitIcon);
	}

	public ImageReference getWaitIcon(){
		return (ImageReference) getComponentProperty(PROPERTY_WAIT_ICON);
	}

	public void setWaitIcon(ImageReference waitIcon){
		setComponentProperty(PROPERTY_WAIT_ICON, waitIcon);
	}

	public ImageReference getNoWaitIcon(){
		return (ImageReference) getComponentProperty(PROPERTY_NO_WAIT_ICON);
	}

	public void setNoWaitIcon(ImageReference waitIcon){
		setComponentProperty(PROPERTY_NO_WAIT_ICON, waitIcon);
	}
	
}

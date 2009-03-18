package org.sgodden.echo.ext20;

import nextapp.echo.app.LayoutData;

public class HtmlLayoutData implements LayoutData {

	private static final long serialVersionUID = 7562580448267606100L;

	String locationName;

	public HtmlLayoutData(String locationName) {
		this.setLocationName(locationName);
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}

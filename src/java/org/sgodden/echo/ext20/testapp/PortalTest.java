/* =================================================================
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 2.1 of the License, or (at your option) any later version.
#
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public
# License along with this library; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
#
# ================================================================= */
package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.DeferredUiCreate;
import org.sgodden.echo.ext20.Portal;
import org.sgodden.echo.ext20.PortalColumn;
import org.sgodden.echo.ext20.Portlet;
import org.sgodden.echo.ext20.layout.ColumnLayoutData;

/**
 * Adds a test for the portal component.
 * 
 * @author sgodden
 */
public class PortalTest extends Portal implements DeferredUiCreate {

	public PortalTest() {
		super();
		setTitle("Portal test");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sgodden.echo.ext20.DeferredUiCreate#createUI()
	 */
	public void createUI() {
		addComponents();
	}

	private void addComponents() {
		PortalColumn col1 = new PortalColumn();
		add(col1);
		col1.setLayoutData(new ColumnLayoutData(.5));
		col1.setPadding("5px 5px 0 5px");

		col1.add(makePortlet("Portlet 1", "Portlet 1"));
		col1.add(makePortlet("Portlet 2", "Portlet 2"));

		PortalColumn col2 = new PortalColumn();
		add(col2);
		col2.setLayoutData(new ColumnLayoutData(.5));
		col2.setPadding("5px 5px 0 0");
		col2.add(makePortlet("Portlet 1", "Portlet 1"));
		col2.add(makePortlet("Portlet 2", "Portlet 2"));
	}

	private Portlet makePortlet(String title, String html) {
		Portlet ret = new Portlet();
		ret.setTitle(title);
		ret.setHtml(html);
		ret.setPadding("5px 5px 5px 5px");

		return ret;
	}

}

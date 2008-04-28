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

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.DeferredUiCreate;
import org.sgodden.echo.ext20.Portal;
import org.sgodden.echo.ext20.PortalColumn;
import org.sgodden.echo.ext20.Portlet;
import org.sgodden.echo.ext20.Tool;
import org.sgodden.echo.ext20.Window;
import org.sgodden.echo.ext20.layout.ColumnLayoutData;

/**
 * Adds a test for the portal component.
 * 
 * @author sgodden
 */
public class PortalTest extends Portal implements DeferredUiCreate {
	
	private static final transient Log log = LogFactory.getLog(PortalTest.class);

	public PortalTest() {
		super();
		setTitle("Portal");
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
		col1.setPadding("10px 0 10px 10px");

		col1.add(makePortlet("Portlet 1", "Portlet 1"));
		col1.add(makePortlet("Portlet 2", "Portlet 2"));

		PortalColumn col2 = new PortalColumn();
		add(col2);
		col2.setLayoutData(new ColumnLayoutData(.5));
		col2.setPadding("10px");
		col2.add(makePortlet("Portlet 1", "Portlet 1"));
		col2.add(makePortlet("Portlet 2", "Portlet 2"));
	}

	private Portlet makePortlet(String title, String html) {
		final Portlet ret = new Portlet();
		ret.setTitle(title);
		ret.setHtml(html);
		ret.setPadding("5px");
		
		ret.addToolListener(Tool.GEAR, new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Window window = new Window("Gear tool pressed");
				window.setModal(true);
				window.setWidth(200);
				window.setHeight(50);
				window.setHtml("The gear tool was pressed");
				ret.add(window);
			}
		});
		
		ret.addToolListener(Tool.CLOSE, new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Window window = new Window("Close tool pressed");
				window.setModal(true);
				window.setWidth(200);
				window.setHeight(50);
				window.setHtml("The close tool was pressed");
				ret.add(window);
			}
		});

		return ret;
	}

}

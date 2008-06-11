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
package org.sgodden.echo.ext20;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

/**
 * Abstract superclass for application servlets that wish to use
 * the extjs framework.
 * <p/>
 * This class deals with adding the necessary javascript resources.
 * 
 * @author goddens
 *
 */
@SuppressWarnings("unchecked")
public abstract class AbstractExtAppServlet extends WebContainerServlet {

    private static final long serialVersionUID = 20080107L;
    private static final Service extService;
    private static final Service echoExtService;

    static {
        List resourceList = new ArrayList();
        resourceList.add("ext/adapter/ext/ext-base.js");
        resourceList.add("ext/ext-all-debug.js");
        resourceList.add("ext/portal/Portal.js");
        resourceList.add("ext/portal/PortalColumn.js");
        resourceList.add("ext/portal/Portlet.js");

        String[] resources = new String[resourceList.size()];
        resourceList.toArray(resources);
        extService = JavaScriptService.forResources("Ext20", resources);

        /*
         * Whilst we are in development mode, add all the scripts up
         * front so that they can be seen by firebug.
         * When we go to release, these should be loaded as needed
         * by the rendering peers.
         * (or should they - these are all tiny, so maybe just loading
         * them all up front in one shot is fine).
         */
        resourceList = new ArrayList();
        // ORDER IS IMPORTANT FOR INHERITANCE!
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.Panel.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.Button.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.TextField.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.TextArea.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.ComboBox.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.CheckboxField.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.DateField.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.FieldSet.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.GridPanel.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.HtmlEditor.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.Menu.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.MenuItem.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.Portal.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.PortalColumn.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.Portlet.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.RadioButton.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.TabbedPane.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.TimeField.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.Toolbar.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.ToolbarButton.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.ToolbarFill.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.ToolbarSeparator.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.ToolbarSpacer.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.ToolbarTextItem.js");
        resourceList.add("org/sgodden/echo/ext20/resource/js/Ext20.Window.js");
        resourceList.add("json2.js");

        resources = new String[resourceList.size()];
        resourceList.toArray(resources);
        echoExtService = JavaScriptService.forResources("EchoExt20", resources);
    }

    public AbstractExtAppServlet() {
        super();
        addStartupScript(extService);
        addStartupScript(echoExtService);
        addCssFileName("resources/ext/css/ext-all.css");
        addCssFileName("resources/ext/css/portal.css");
    }
}

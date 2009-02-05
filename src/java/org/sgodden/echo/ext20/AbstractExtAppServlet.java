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
import nextapp.echo.webcontainer.service.CSSStyleSheetService;
import nextapp.echo.webcontainer.service.JavaScriptService;
import nextapp.echo.webcontainer.service.StaticTextService;
import nextapp.echo.webcontainer.util.Resource;

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
    private static final Service extExtensionsService;
    private static final Service echoLabelService;
    private static final Service echoExtService;

    static {
        extService = new JavaScriptService("Ext20", Resource.getResourceAsString("js/ext-all.js"), Resource.getResourceAsByteArray("js/ext-all.js.gz"));
        
        extExtensionsService = new JavaScriptService("Ext20Ext", Resource.getResourceAsString("js/ext20ext-all.js"), Resource.getResourceAsByteArray("js/ext20ext-all.js.gz"));
        
        echoLabelService = JavaScriptService.forResource("Echo.Label", "nextapp/echo/webcontainer/resource/Sync.Label.js");

        echoExtService = new JavaScriptService("EchoExt20", Resource.getResourceAsString("js/echo3ext20-all.js"), Resource.getResourceAsByteArray("js/echo3ext20-all.js.gz"));
    }

    public AbstractExtAppServlet() {
        super();
        addInitScript(extService);
        addInitScript(extExtensionsService);
        addInitScript(echoLabelService);
        addInitScript(echoExtService);
        addInitStyleSheet(CSSStyleSheetService.forResource("ExtAllCSS", "ext/css/ext-all.css", "resources/ext/css/"));
        addInitStyleSheet(CSSStyleSheetService.forResource("ExtPortalCSS", "ext/css/portal.css", "resources/ext/css/"));
        addInitStyleSheet(CSSStyleSheetService.forResource("ExtMultiSelectCSS", "ext/css/Multiselect.css", "resources/ext/css/"));
        addInitStyleSheet(CSSStyleSheetService.forResource("ExtPluginsCSS", "ext/css/Plugins.css", "resources/ext/css/"));
    }
}

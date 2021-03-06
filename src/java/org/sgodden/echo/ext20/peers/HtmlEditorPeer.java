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
package org.sgodden.echo.ext20.peers;

import nextapp.echo.app.Component;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;

import org.sgodden.echo.ext20.HtmlEditor;
import org.sgodden.echo.ext20.TextField;

@SuppressWarnings({"unchecked"})
public class HtmlEditorPeer 
extends ExtComponentPeer {
    
//    protected static final Service HTML_EDITOR_SERVICE = JavaScriptService.forResource("EchoExt20.HtmlEditor", 
//            "org/sgodden/echo/ext20/resource/js/Ext20.HtmlEditor.js");
//    
//    static {
//        WebContainerServlet.getServiceRegistry().add(HTML_EDITOR_SERVICE);
//    }
    
    public HtmlEditorPeer() {
        super();
    }

    public Class getComponentClass() {
        return HtmlEditor.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2HE" : "Ext20HtmlEditor";
    }
    
    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
     */
    public Class getInputPropertyClass(String propertyName) {
        if (HtmlEditor.PROPERTY_TEXT_CHANGED.equals(propertyName)) {
            return String.class;
        }
        return null;
    }
    
    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#storeInputProperty(Context, Component, String, int, Object)
     */
    public void storeInputProperty(Context context, Component component, String propertyName, int propertyIndex, Object newValue) {
        if (propertyName.equals(HtmlEditor.PROPERTY_TEXT_CHANGED)) {
            ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
            clientUpdateManager.setComponentProperty(component, HtmlEditor.PROPERTY_TEXT_CHANGED, newValue);
        }
    }

    
    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(Context)
     */
    public void init(Context context, Component c) {
        super.init(context, c);
        //ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
        //serverMessage.addLibrary(HTML_EDITOR_SERVICE.getId());
    }


}
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
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;

import org.sgodden.echo.ext20.Portlet;

@SuppressWarnings( { "unchecked" })
public class PortletPeer extends PanelPeer {

    // protected static final Service PORTLET_SERVICE =
    // JavaScriptService.forResource("EchoExt20.Portlet",
    // "org/sgodden/echo/ext20/resource/js/Ext20.Portlet.js");
    //
    // static {
    // WebContainerServlet.getServiceRegistry().add(PORTLET_SERVICE);
    // }

    public PortletPeer() {
        super();
        addEvent(new AbstractComponentSynchronizePeer.EventPeer(
                Portlet.INPUT_ACTION, Portlet.ACTION_LISTENERS_CHANGED_PROPERTY) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return ((Portlet) component).hasActionListeners();
            }
        });
    }

    @Override
    public Class getComponentClass() {
        return Portlet.class;
    }

    @Override
    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2PTL" : "Ext20Portlet";
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(Context)
     */
    @Override
    public void init(Context context, Component c) {
        super.init(context, c);
        // ServerMessage serverMessage = (ServerMessage)
        // context.get(ServerMessage.class);
        // serverMessage.addLibrary(PANEL_SERVICE.getId());
    }

    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
     */
    public Class getInputPropertyClass(String propertyName) {
        if (Portlet.PROPERTY_COLUMN.equals(propertyName)) {
            return Integer.class;
        }
        if (Portlet.PROPERTY_ROW.equals(propertyName)) {
            return Integer.class;
        }
        if (Portlet.PROPERTY_COLLAPSED.equals(propertyName)) {
            return Boolean.class;
        }
        else {
        	return super.getInputPropertyClass(propertyName);
        }
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#storeInputProperty(Context,
     *      Component, String, int, Object)
     */
    public void storeInputProperty(Context context, Component component,
            String propertyName, int propertyIndex, Object newValue) {
        if (propertyName.equals(Portlet.PROPERTY_COLUMN)) {
            getClientUpdateManager(context).setComponentProperty(component,
                    Portlet.PROPERTY_COLUMN, newValue);
        } else if (propertyName.equals(Portlet.PROPERTY_ROW)) {
            getClientUpdateManager(context).setComponentProperty(component,
                    Portlet.PROPERTY_ROW, newValue);
        } else if (propertyName.equals(Portlet.PROPERTY_COLLAPSED)) {
            getClientUpdateManager(context).setComponentProperty(component,
                    Portlet.PROPERTY_COLLAPSED, newValue);
        } else {
        	super.storeInputProperty(context, component, propertyName, propertyIndex, newValue);
        }
    }

    private ClientUpdateManager getClientUpdateManager(Context context) {
        return (ClientUpdateManager) context.get(ClientUpdateManager.class);
    }
}
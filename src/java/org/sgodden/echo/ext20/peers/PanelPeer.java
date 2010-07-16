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

import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.util.InsertEntities;


@SuppressWarnings({"unchecked"})
public class PanelPeer
        extends ExtComponentPeer {

//    protected static final Service PANEL_SERVICE = JavaScriptService.forResource("EchoExt20.Panel",
//            "org/sgodden/echo/ext20/resource/js/Ext20.Panel.js");
//
//    static {
//        WebContainerServlet.getServiceRegistry().add(PANEL_SERVICE);
//    }
    
    public PanelPeer() {
        addEvent(
            new AbstractComponentSynchronizePeer.EventPeer(
                    Panel.INPUT_KEYPRESS_ACTION, 
                    Panel.PROPERTY_KEYPRESS_LISTENERS_CHANGED) {
                @Override
                public boolean hasListeners(Context context, Component component) {
                    return ((Panel) component).hasKeyPressListeners();
                }
        });
        
        addEvent(
            new AbstractComponentSynchronizePeer.EventPeer(
                    Panel.INPUT_TOOLCLICK_ACTION, 
                    Panel.PROPERTY_TOOLCLICK_LISTENERS_CHANGED) {
                @Override
                public boolean hasListeners(Context context, Component component) {
                    return ((Panel) component).hasToolListeners();
                }
        });
        addEvent(
        		new AbstractComponentSynchronizePeer.EventPeer(
        				Panel.BEFORE_EXPAND_ACTION, 
        				Panel.PROPERTY_BEFOREEXPAND_LISTENERS_CHANGED) {
        			@Override
        			public boolean hasListeners(Context context, Component component) {
        				return ((Panel) component).hasBeforeExpandListeners();
        			}
        		});

    }

    public Class getComponentClass() {
        return Panel.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2P" : "Ext20Panel";
    }
    
    
    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
     */
    @Override
    public Class getInputPropertyClass(String propertyName) {
        if (Panel.INPUT_KEY_PRESSED.equals(propertyName)) {
            return String.class;
        }
        if (Panel.INPUT_TOOLID_CLICKED.equals(propertyName)) {
            return String.class;
        }
        return null;
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#storeInputProperty(Context, Component, String, int, Object)
     */
    @Override
    public void storeInputProperty(Context context, Component component, String propertyName, int propertyIndex, Object newValue) {
        if (propertyName.equals(Panel.INPUT_KEY_PRESSED)) {
            ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
            clientUpdateManager.setComponentProperty(component, Panel.INPUT_KEY_PRESSED, newValue);
        }
        if (propertyName.equals(Panel.INPUT_TOOLID_CLICKED)) {
            ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
            clientUpdateManager.setComponentProperty(component, Panel.INPUT_TOOLID_CLICKED, newValue);
        }
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(Context)
     */
    public void init(Context context, Component c) {
        super.init(context, c);
    //ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
    //serverMessage.addLibrary(PANEL_SERVICE.getId());
    }
    
    public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
        if (Panel.PROPERTY_TITLE.equals(propertyName)) {
            String title = (String)super.getOutputProperty(context, component, propertyName, propertyIndex);
            return InsertEntities.insertHTMLEntities(title);
        }
        return super.getOutputProperty(context, component, propertyName, propertyIndex);
    }
}

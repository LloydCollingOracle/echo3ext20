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
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import org.sgodden.echo.ext20.AbstractButton;
import org.sgodden.echo.ext20.CheckboxField;

@SuppressWarnings({"serial","unchecked"})
public class CheckboxFieldPeer
        extends ExtComponentPeer {

    protected static final Service TEXT_FIELD_SERVICE = JavaScriptService.forResource("EchoExt20.CheckboxField",
            "org/sgodden/echo/ext20/resource/js/Ext20.CheckboxField.js");

    static {
        WebContainerServlet.getServiceRegistry().add(TEXT_FIELD_SERVICE);
    }

    public CheckboxFieldPeer() {
        super();
        addOutputProperty(CheckboxField.FIELD_LABEL_PROPERTY);
        addOutputProperty(CheckboxField.SELECTED_CHANGED_PROPERTY);
        
        addEvent(new AbstractComponentSynchronizePeer.EventPeer(AbstractButton.INPUT_ACTION, AbstractButton.ACTION_LISTENERS_CHANGED_PROPERTY) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return ((CheckboxField) component).hasActionListeners();
            }
        });

    }

    public Class getComponentClass() {
        return CheckboxField.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2CBF" : "Ext20CheckboxField";
    }

    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
     */
    @Override
    public Class getInputPropertyClass(String propertyName) {
        if (CheckboxField.SELECTED_CHANGED_PROPERTY.equals(propertyName)) {
            return Boolean.class;
        }
        return null;
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#storeInputProperty(Context, Component, String, int, Object)
     */
    @Override
    public void storeInputProperty(Context context, Component component, String propertyName, int propertyIndex, Object newValue) {
        if (propertyName.equals(CheckboxField.SELECTED_CHANGED_PROPERTY)) {
            ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
            clientUpdateManager.setComponentProperty(component, CheckboxField.SELECTED_CHANGED_PROPERTY, newValue);
        }
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(Context)
     */
    @Override
    public void init(Context context, Component c) {
        super.init(context, c);
    //ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
    //serverMessage.addLibrary(TEXT_FIELD_SERVICE.getId());
    }
}

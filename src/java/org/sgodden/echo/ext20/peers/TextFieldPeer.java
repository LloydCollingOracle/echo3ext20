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

import org.sgodden.echo.ext20.TextField;

@SuppressWarnings({"unchecked"})
public class TextFieldPeer
        extends ExtComponentPeer {

//    protected static final Service TEXT_FIELD_SERVICE = JavaScriptService.forResource("EchoExt20.TextField",
//            "org/sgodden/echo/ext20/resource/js/Ext20.TextField.js");
//
//    static {
//        WebContainerServlet.getServiceRegistry().add(TEXT_FIELD_SERVICE);
//    }

    public TextFieldPeer() {
        super();
        addOutputProperty(TextField.PROPERTY_VALUE_CHANGED);
        addEvent(new AbstractComponentSynchronizePeer.EventPeer(TextField.INPUT_ACTION,  TextField.ACTION_LISTENERS_CHANGED_PROPERTY) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                /**
                 * We only need to put a value change listener on the
                 * text field if we have set the notify immediately 
                 * property on the text field component.
                 */
                return ((TextField) component).getNotifyImmediately();
            }
        });
    }

    public Class getComponentClass() {
        return TextField.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2TF" : "Ext20TextField";
    }

    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
     */
    public Class getInputPropertyClass(String propertyName) {
        if (TextField.PROPERTY_VALUE_CHANGED.equals(propertyName)) {
            return String.class;
        }
        if (TextField.PROPERTY_INVALID_TEXT.equals(propertyName)) {
            return String.class;
        }
        else if(TextField.PROPERTY_VALID.equals(propertyName)){
        	return Boolean.class;
        }
        else if(TextField.PROPERTY_NOTIFY_VALUE_IMMEDIATE.equals(propertyName)){
            return Boolean.class;
        }
        return null;
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#storeInputProperty(Context, Component, String, int, Object)
     */
    public void storeInputProperty(Context context, Component component, String propertyName, int propertyIndex, Object newValue) {
        if (propertyName.equals(TextField.PROPERTY_VALUE_CHANGED)) {
            
            if (component instanceof TextField && ((TextField)component).isStripWhitespace() && newValue != null) {
                getClientUpdateManager(context).setComponentProperty(component, TextField.PROPERTY_VALUE_CHANGED, ((String)newValue).trim());
            } else {
                getClientUpdateManager(context).setComponentProperty(component, TextField.PROPERTY_VALUE_CHANGED, newValue);
            }
        }else if(propertyName.equals(TextField.PROPERTY_VALID)){
        	getClientUpdateManager(context).setComponentProperty(component, TextField.PROPERTY_VALID, newValue);
        }else if(propertyName.equals(TextField.PROPERTY_INVALID_TEXT)){
            getClientUpdateManager(context).setComponentProperty(component, TextField.PROPERTY_INVALID_TEXT, newValue);
        }else if(propertyName.equals(TextField.PROPERTY_NOTIFY_VALUE_IMMEDIATE)){
            getClientUpdateManager(context).setComponentProperty(component, TextField.PROPERTY_NOTIFY_VALUE_IMMEDIATE, newValue);
        }
    }
    
    private ClientUpdateManager getClientUpdateManager(Context context){
    	return (ClientUpdateManager) context.get(ClientUpdateManager.class);
    }

    @Override
    public void init(Context context, Component c) {
        super.init(context, c);
    //ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
    //serverMessage.addLibrary(TEXT_FIELD_SERVICE.getId());
    }
}

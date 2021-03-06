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

import org.sgodden.echo.ext20.ComboBox;
import org.sgodden.echo.ext20.data.ListModelAdapter;

@SuppressWarnings({"unchecked"})
public class ComboBoxPeer
        extends TextFieldPeer {

//    protected static final Service COMBO_BOX_SERVICE = JavaScriptService.forResource("EchoExt20.ComboBox",
//            "org/sgodden/echo/ext20/resource/js/Ext20.ComboBox.js");
//
//    static {
//        WebContainerServlet.getServiceRegistry().add(COMBO_BOX_SERVICE);
//    }

    public ComboBoxPeer() {
        super();
        addOutputProperty(ComboBox.MODEL_CHANGED);
        addOutputProperty(ComboBox.PROPERTY_SELECTION_CHANGED);
        setOutputPropertyReferenced(ComboBox.MODEL_CHANGED, true);
        addEvent(new AbstractComponentSynchronizePeer.EventPeer(ComboBox.INPUT_ACTION, ComboBox.PROPERTY_ACTION_LISTENERS_CHANGED) {
            @Override
            public boolean hasListeners(Context context, Component component) {
            	if (((ComboBox) component).getNotifyImmediately()) {
                    return true;
                }
                else {
                    return ((ComboBox) component).hasActionListeners();
                }
            }
        });
    }

    @Override
    public Class getComponentClass() {
        return ComboBox.class;
    }

    @Override
    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2CB" : "Ext20ComboBox";
    }

    /*
     * (non-Javadoc)
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
     */
    @Override
    public Class getInputPropertyClass(String propertyName) {
        if (ComboBox.PROPERTY_RAW_VALUE_CHANGED.equals(propertyName)) {
            return String.class;
        }
        if (ComboBox.PROPERTY_SELECTION_CHANGED.equals(propertyName)) {
            return Integer.class;
        }
        return super.getInputPropertyClass(propertyName);
    }

    
    /*
     * (non-Javadoc)
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getOutputProperty(nextapp.echo.app.util.Context, nextapp.echo.app.Component, java.lang.String, int)
     */
    @Override
    public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
        ComboBox gridPanel = (ComboBox)component;
        if (ComboBox.PROPERTY_SELECTION_CHANGED.equals(propertyName)) {
            return gridPanel.getSelectionModel().getMinSelectedIndex();
        }
        if (ComboBox.MODEL_CHANGED.equals(propertyName)) {
            return new ListModelAdapter(gridPanel);
        }
        return super.getOutputProperty(context, component, propertyName, propertyIndex);
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


    /*
     * (non-Javadoc)
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#storeInputProperty(nextapp.echo.app.util.Context, nextapp.echo.app.Component, java.lang.String, int, java.lang.Object)
     */
    @Override
    public void storeInputProperty(Context context, Component component, String propertyName, int index, Object newValue) {
            ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
        super.storeInputProperty(context, component, propertyName, index, newValue);
        if (ComboBox.PROPERTY_SELECTION_CHANGED.equals(propertyName)) {
            clientUpdateManager.setComponentProperty(component, ComboBox.PROPERTY_SELECTION_CHANGED, newValue);
        }
        else if (propertyName.equals(ComboBox.PROPERTY_RAW_VALUE_CHANGED)) {
            clientUpdateManager.setComponentProperty(component, ComboBox.PROPERTY_RAW_VALUE_CHANGED, newValue);
        }
    }

}

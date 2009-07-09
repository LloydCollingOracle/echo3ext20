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
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import org.sgodden.echo.ext20.ComboBox;
import org.sgodden.echo.ext20.MultiSelect;
import org.sgodden.echo.ext20.data.ListModelAdapter;
import org.sgodden.echo.ext20.util.ListSelectionUtil;

@SuppressWarnings( { "unchecked" })
public class MultiSelectPeer extends AbstractComponentSynchronizePeer {

//    protected static final Service MULTI_SELECT_SERVICE = JavaScriptService
//            .forResource("EchoExt20.MultiSelect",
//                    "org/sgodden/echo/ext20/resource/js/Ext20.MultiSelect.js");
//
//    static {
//        WebContainerServlet.getServiceRegistry().add(MULTI_SELECT_SERVICE);
//    }

    public MultiSelectPeer() {
        super();
        addOutputProperty(MultiSelect.SELECTION_CHANGED_PROPERTY);
        addOutputProperty(MultiSelect.MODEL_CHANGED_PROPERTY);
        addOutputProperty(MultiSelect.COMPLEX_PROPERTY);
        setOutputPropertyReferenced(MultiSelect.MODEL_CHANGED_PROPERTY, true);
        addEvent(new AbstractComponentSynchronizePeer.EventPeer(MultiSelect.INPUT_ACTION, MultiSelect.ACTION_LISTENERS_CHANGED_PROPERTY) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return ((MultiSelect) component).hasActionListeners();
            }
        });
    }

    @Override
    public Class getComponentClass() {
        return MultiSelect.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2MS" : "Ext20MultiSelect";
    }

    /*
     * (non-Javadoc)
     * 
     * @seenextapp.echo.webcontainer.AbstractComponentSynchronizePeer#
     * getInputPropertyClass(java.lang.String)
     */
    @Override
    public Class getInputPropertyClass(String propertyName) {
        if (MultiSelect.SELECTION_CHANGED_PROPERTY.equals(propertyName)) {
            return String.class;
        } else {
            return super.getInputPropertyClass(propertyName);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getOutputProperty
     * (nextapp.echo.app.util.Context, nextapp.echo.app.Component,
     * java.lang.String, int)
     */
    @Override
    public Object getOutputProperty(Context context, Component component,
            String propertyName, int propertyIndex) {
        MultiSelect multiSelect = (MultiSelect) component;
        if (MultiSelect.SELECTION_CHANGED_PROPERTY.equals(propertyName)) {
            return ListSelectionUtil.toString(multiSelect.getSelectionModel(), multiSelect.getModel().size());
        }
        if (MultiSelect.MODEL_CHANGED_PROPERTY.equals(propertyName)) {
            return new ListModelAdapter(multiSelect);
        }
        return super.getOutputProperty(context, component, propertyName,
                propertyIndex);
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(Context)
     */
    @Override
    public void init(Context context, Component c) {
        super.init(context, c);
        ServerMessage serverMessage = (ServerMessage) context
                .get(ServerMessage.class);
//        serverMessage.addLibrary(MULTI_SELECT_SERVICE.getId());
    }

    /*
     * (non-Javadoc)
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#storeInputProperty(nextapp.echo.app.util.Context, nextapp.echo.app.Component, java.lang.String, int, java.lang.Object)
     */
    @Override
    public void storeInputProperty(Context context, Component component, String propertyName, int index, Object newValue) {
            ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
        if (MultiSelect.SELECTION_CHANGED_PROPERTY.equals(propertyName)) {
            int[] selection;
            if(newValue == null) {
                selection = null;
            }
            else{
                selection = ListSelectionUtil.toIntArray((String) newValue);    
            }
            
            clientUpdateManager.setComponentProperty(component, MultiSelect.SELECTION_CHANGED_PROPERTY, selection);
        }
    }

}

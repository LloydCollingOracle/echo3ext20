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

import java.util.Date;

import nextapp.echo.app.Component;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;

import org.sgodden.echo.ext20.DateField;

@SuppressWarnings({"unchecked"})
public class DateFieldPeer
        extends ExtComponentPeer {

    //private static final transient Log log = LogFactory.getLog(DateFieldPeer.class);
    
//    protected static final Service DATE_FIELD_SERVICE = JavaScriptService.forResource("EchoExt20.DateField",
//            "org/sgodden/echo/ext20/resource/js/Ext20.DateField.js");
//
//    static {
//        WebContainerServlet.getServiceRegistry().add(DATE_FIELD_SERVICE);
//    }

    public DateFieldPeer() {
        super();
        addEvent(new AbstractComponentSynchronizePeer.EventPeer(DateField.PROPERTY_DATE_CHANGED, DateField.PROPERTY_ACTION_LISTENERS_CHANGED) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return ((DateField) component).hasActionListeners();
            }
        });
    }

    public Class getComponentClass() {
        return DateField.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2DF" : "Ext20DateField";
    }
    
    @Override
    public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
        Object ret = null;
        if (propertyName.equals(DateField.PROPERTY_DATE_CHANGED)) {
            DateField df = (DateField) component;
            if ( df.getCalendar() == null){
            	return null;
            } else {
            	return df.getCalendar().getTime();	
            }
        } else {
            ret = super.getOutputProperty(context, component, propertyName, propertyIndex);
        }
        
        return ret;
    }

    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
     */
    @Override
    public Class getInputPropertyClass(String propertyName) {
        if (DateField.PROPERTY_DATE_CHANGED.equals(propertyName)) {
            return Date.class;
        }
        return null;
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#storeInputProperty(Context, Component, String, int, Object)
     */
    @Override
    public void storeInputProperty(Context context, Component component, String propertyName, int propertyIndex, Object newValue) {
        if (propertyName.equals(DateField.PROPERTY_DATE_CHANGED)) {
            ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
            clientUpdateManager.setComponentProperty(component, DateField.PROPERTY_DATE_CHANGED, newValue);
        }
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(Context)
     */
    @Override
    public void init(Context context, Component c) {
        super.init(context, c);
    //ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
    //serverMessage.addLibrary(DATE_FIELD_SERVICE.getId());
    }
}

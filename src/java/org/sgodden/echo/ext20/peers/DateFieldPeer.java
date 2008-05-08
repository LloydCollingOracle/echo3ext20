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
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.DateField;

public class DateFieldPeer
        extends ExtComponentPeer {

	private static final transient Log log = LogFactory.getLog(DateFieldPeer.class);
	
    protected static final Service DATE_FIELD_SERVICE = JavaScriptService.forResource("EchoExt20.DateField",
            "/org/sgodden/echo/ext20/resource/js/Ext20.DateField.js");

    static {
        WebContainerServlet.getServiceRegistry().add(DATE_FIELD_SERVICE);
    }

    public DateFieldPeer() {
        super();
        addOutputProperty(DateField.DATE_CHANGED_PROPERTY);
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
        
        if (propertyName.equals(DateField.DATE_CHANGED_PROPERTY)) {
            DateField df = (DateField) component;
            return df.getCalendar().getTime();
        }
        else {
            ret = super.getOutputProperty(context, component, propertyName, propertyIndex);
        }
        
        return ret;
    }

    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
     */
    @Override
    public Class getInputPropertyClass(String propertyName) {
        if (DateField.DATE_CHANGED_PROPERTY.equals(propertyName)) {
            return Date.class;
        }
        return null;
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#storeInputProperty(Context, Component, String, int, Object)
     */
    @Override
    public void storeInputProperty(Context context, Component component, String propertyName, int propertyIndex, Object newValue) {
    	log.info("date: '" + newValue + "'");
        if (propertyName.equals(DateField.DATE_CHANGED_PROPERTY)) {
        	if (newValue == null) {
        		newValue = ""; // prevent NPE in SimpleDateForm.parse
        	}
            ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
            clientUpdateManager.setComponentProperty(component, DateField.DATE_CHANGED_PROPERTY, newValue);
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

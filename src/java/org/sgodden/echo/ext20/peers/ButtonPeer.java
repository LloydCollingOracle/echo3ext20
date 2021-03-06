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

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;

import org.sgodden.echo.ext20.Button;

/**
 * Synchronization peer for {@link Button}.
 */
@SuppressWarnings( { "unchecked" })
public class ButtonPeer extends AbstractButtonPeer {

    // protected static final Service BUTTON_SERVICE =
    // JavaScriptService.forResource("EchoExt20.Button",
    // "org/sgodden/echo/ext20/resource/js/Ext20.Button.js");
    //    
    // static {
    // WebContainerServlet.getServiceRegistry().add(BUTTON_SERVICE);
    // }

    /**
     * Default constructor.
     */
    public ButtonPeer() {
        super();
        addOutputProperty(Button.PROPERTY_FIELDS_TO_LISTEN_TO);
        addOutputProperty(Button.PROPERTY_FIELDS_CHANGED);
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2B" : "Ext20Button";
    }

    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getComponentClass()
     */
    public Class getComponentClass() {
        return Button.class;
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(Context)
     */
    @Override
    public void init(Context context, Component c) {
        super.init(context, c);
        // ServerMessage serverMessage = (ServerMessage)
        // context.get(ServerMessage.class);
        // serverMessage.addLibrary(BUTTON_SERVICE.getId());
    }

    @Override
    public Object getOutputProperty(Context context, Component component,
    		String propertyName, int propertyIndex) {

    	if (Button.PROPERTY_FIELDS_TO_LISTEN_TO.equals(propertyName)){
    		StringBuilder fieldsToListenToIds = new StringBuilder(); 
    		List<Component> compToListenTo = ((Button)component).getFieldsToListenToList();
    		String delimiter = "";
    		for(Component c: compToListenTo){
    			fieldsToListenToIds.append(delimiter);
    			fieldsToListenToIds.append("C.");
    			fieldsToListenToIds.append(c.getContainingWindow().getId());
    			fieldsToListenToIds.append(".");
    			fieldsToListenToIds.append(c.getRenderId());
    			delimiter = ",";
    		}
    		return fieldsToListenToIds.toString();
    	}
    	else {
    		return super.getOutputProperty(context, component, propertyName, propertyIndex);
    	}
    }
}

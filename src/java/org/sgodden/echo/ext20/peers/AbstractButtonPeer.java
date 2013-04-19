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

import org.sgodden.echo.ext20.AbstractButton;
import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.CheckboxField;

/**
 * Synchronization peer for {@link AbstractButton}.
 */
public abstract class AbstractButtonPeer extends ExtComponentPeer {
    
    /**
     * Default constructor.
     */
    public AbstractButtonPeer() {
        super();
        addEvent(new AbstractComponentSynchronizePeer.EventPeer(AbstractButton.INPUT_ACTION, AbstractButton.PROPERTY_ACTION_LISTENERS_CHANGED) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return ((AbstractButton) component).hasActionListeners();
            }
        });
    }
    
    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Class getInputPropertyClass(String propertyName) {
        if (AbstractButton.PROPERTY_PRESSED.equals(propertyName)) {
            return Boolean.class;
        }
        if (Button.PROPERTY_FIELDS_CHANGED.equals(propertyName)) {
            return Boolean.class;
        }
        return null;
    }
    
    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#storeInputProperty(Context, Component, String, int, Object)
     */
    @Override
    public void storeInputProperty(Context context, Component component, String propertyName, int propertyIndex, Object newValue) {
        if (propertyName.equals(AbstractButton.PROPERTY_PRESSED)) {
            ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
            clientUpdateManager.setComponentProperty(component, AbstractButton.PROPERTY_PRESSED, newValue);
        }
        if (propertyName.equals(Button.PROPERTY_FIELDS_CHANGED)) {
            ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
            clientUpdateManager.setComponentProperty(component, Button.PROPERTY_FIELDS_CHANGED, newValue);
        }
    }

}
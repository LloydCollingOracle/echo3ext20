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
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import org.sgodden.echo.ext20.TriggerField;

/**
 * Server side Peer for TriggerField.
 * 
 * @author rcharlton
 */
@SuppressWarnings({"serial","unchecked"})
public class TriggerFieldPeer
        extends TextFieldPeer {

    protected static final Service TRIGGER_FIELD_SERVICE = JavaScriptService.forResource("EchoExt20.TriggerField",
            "org/sgodden/echo/ext20/resource/js/Ext20.TriggerField.js");

    static {
        WebContainerServlet.getServiceRegistry().add(TRIGGER_FIELD_SERVICE);
    }

    public TriggerFieldPeer() {
        super();
        
        addEvent(new AbstractComponentSynchronizePeer.EventPeer(TriggerField.INPUT_ACTION, TriggerField.ACTION_LISTENERS_CHANGED_PROPERTY) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return ((TriggerField) component).hasActionListeners();
            }
        });
    }

    public Class getComponentClass() {
        return TriggerField.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2TR" : "Ext20TriggerField";
    }

    @Override
    public void init(Context context, Component c) {
        super.init(context, c);
    ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
    serverMessage.addLibrary(TRIGGER_FIELD_SERVICE.getId());
    }
}

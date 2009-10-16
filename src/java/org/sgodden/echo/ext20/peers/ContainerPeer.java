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

import org.sgodden.echo.ext20.Container;


@SuppressWarnings({"unchecked"})
public class ContainerPeer
        extends ExtComponentPeer {

//    protected static final Service PANEL_SERVICE = JavaScriptService.forResource("EchoExt20.Container",
//            "org/sgodden/echo/ext20/resource/js/Ext20.Container.js");
//
//    static {
//        WebContainerServlet.getServiceRegistry().add(PANEL_SERVICE);
//    }
    
    public ContainerPeer() {
    	super();
    }

    public Class getComponentClass() {
        return Container.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2C" : "Ext20Container";
    }
    

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(Context)
     */
    public void init(Context context, Component c) {
        super.init(context, c);
    //ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
    //serverMessage.addLibrary(PANEL_SERVICE.getId());
    }
}

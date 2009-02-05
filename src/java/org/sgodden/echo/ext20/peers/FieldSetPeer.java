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
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import org.sgodden.echo.ext20.FieldSet;

@SuppressWarnings({"unchecked"})
public class FieldSetPeer
        extends PanelPeer {

//    protected static final Service FIELDSET_SERVICE = JavaScriptService.forResource("EchoExt20.FieldSet",
//            "org/sgodden/echo/ext20/resource/js/Ext20.FieldSet.js");
//
//    static {
//        WebContainerServlet.getServiceRegistry().add(PANEL_SERVICE);
//    }

    @Override
    public Class getComponentClass() {
        return FieldSet.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2FS" : "Ext20FieldSet";
    }

    @Override
    public void init(Context context, Component c) {
        super.init(context, c);
    //ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
    //serverMessage.addLibrary(PANEL_SERVICE.getId());
    }
}

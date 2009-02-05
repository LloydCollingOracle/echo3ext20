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

import org.sgodden.echo.ext20.TextArea;

@SuppressWarnings({"unchecked"})
public class TextAreaPeer
        extends TextFieldPeer {

//    protected static final Service TEXT_AREA_SERVICE = JavaScriptService.forResource("EchoExt20.TextArea",
//            "org/sgodden/echo/ext20/resource/js/Ext20.TextArea.js");
//
//    static {
//        WebContainerServlet.getServiceRegistry().add(TEXT_AREA_SERVICE);
//    }

    public TextAreaPeer() {
        super();
        addOutputProperty(TextArea.PROPERTY_HEIGHT);
        addOutputProperty(TextArea.PROPERTY_WIDTH);
    }

    public Class getComponentClass() {
        return TextArea.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2TA" : "Ext20TextArea";
    }

    @Override
    public void init(Context context, Component c) {
        super.init(context, c);
    //ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
    //serverMessage.addLibrary(TEXT_FIELD_SERVICE.getId());
    }
}

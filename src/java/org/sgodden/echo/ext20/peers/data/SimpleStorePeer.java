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
package org.sgodden.echo.ext20.peers.data;

import nextapp.echo.app.serial.SerialContext;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.SerialPropertyPeer;
import nextapp.echo.app.util.Context;

import org.sgodden.echo.ext20.data.DefaultSimpleStore;
import org.w3c.dom.Element;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;

public class SimpleStorePeer implements SerialPropertyPeer {

    public void toXml(Context context, Class objectClass, Element propertyElement, Object propertyValue)
            throws SerialException {
        SerialContext serialContext = (SerialContext) context.get(SerialContext.class);
        DefaultSimpleStore simpleStore = (DefaultSimpleStore) propertyValue;
        propertyElement.setAttribute("t",
                (serialContext.getFlags() & SerialContext.FLAG_RENDER_SHORT_NAMES) == 0 ? "Echo2SimpleStore" : "E2SS");
        try {
            propertyElement.setTextContent(JSONMapper.toJSON(simpleStore).render(false));
        } catch (MapperException e) {
            throw new SerialException("Error mapping SimpleStore", e);
        }

    }

    public Object toProperty(Context context, Class objectClass,
            Element propertyElement) throws SerialException {
        throw new UnsupportedOperationException();
    }
}

package org.sgodden.echo.ext20.peers.models;

import nextapp.echo.app.serial.SerialContext;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.util.Context;

import org.sgodden.echo.ext20.models.ListModelRemoteAutocompleteModel;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Serialisation Peer for the ListModelRemoteAutocompleteModel property type
 * 
 * @author Lloyd Colling
 */
public class ListModelRemoteAutocompleteModelPeer extends AbstractRemoteAutocompleteModelPeer {

    @Override
    public Object toProperty(Context arg0, Class arg1, Element arg2)
            throws SerialException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void toXml(Context context, Class objectClass, Element propertyElement, Object propertyValue)
            throws SerialException {
        SerialContext serialContext = (SerialContext) context.get(SerialContext.class);
        ListModelRemoteAutocompleteModel model = (ListModelRemoteAutocompleteModel) propertyValue;
        propertyElement
        .setAttribute(
                "t",
                (serialContext.getFlags() & SerialContext.FLAG_RENDER_SHORT_NAMES) == 0 ? "Echo2RemoteList"
                        : "E2RML");
        Text urlText = serialContext.getDocument().createTextNode(getUrl(context, model));
        propertyElement.appendChild(urlText);
    }
}

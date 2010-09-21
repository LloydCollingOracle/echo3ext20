package org.sgodden.echo.ext20.peers.models;

import org.sgodden.echo.ext20.models.RemoteAutocompleteModel;
import org.sgodden.echo.ext20.service.RemoteListModelService;

import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.SerialPropertyPeer;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.UserInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * Abstract superclass for all RemoteAutocompleteModel Peer's to extend so that the RemoteListModelService
 * only gets registered once.
 * 
 * @author Lloyd Colling
 */
public abstract class AbstractRemoteAutocompleteModelPeer implements SerialPropertyPeer {
    static {
        WebContainerServlet.getServiceRegistry().add(RemoteListModelService.INSTANCE);
    }


    public String getUrl(Context context, RemoteAutocompleteModel model) 
    throws SerialException {
        UserInstance userInstance = (UserInstance) context.get(UserInstance.class);
        userInstance.getIdTable().register(model);
        return "!RLM!" + model.getRenderId();
    }
}

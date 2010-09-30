package org.sgodden.echo.ext20.peers;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import org.sgodden.echo.ext20.AutocompleteComboWithTrigger;

/**
 * Peer for the AutocompleteComboWithTrigger component
 * 
 * @author Lloyd Colling
 */
public class AutocompleteComboWithTriggerPeer extends AutocompleteComboPeer {

    protected static final Service AUTOCOMPLETE_TRIGGER_FIELD_SERVICE = JavaScriptService.forResource("EchoExt20.AutocompleteTR",
            "org/sgodden/echo/ext20/resource/js/Ext20.AutocompleteTrigger.js");

    static {
        WebContainerServlet.getServiceRegistry().add(AUTOCOMPLETE_TRIGGER_FIELD_SERVICE);
    }
    
    public AutocompleteComboWithTriggerPeer() {
        super();
        addEvent(new AbstractComponentSynchronizePeer.EventPeer(AutocompleteComboWithTrigger.PROPERTY_TRIGGER_ACTION, AutocompleteComboWithTrigger.PROPERTY_TRIGGER_LISTENERS_CHANGED) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return ((AutocompleteComboWithTrigger)component).hasTriggerActionListeners();
            }
        });
    }

    public Class getComponentClass() {
        return AutocompleteComboWithTrigger.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2ACTR" : "Ext20AutocompleteComboTrigger";
    }

    @Override
    public void init(Context context, Component c) {
        super.init(context, c);
        ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
        serverMessage.addLibrary(AUTOCOMPLETE_TRIGGER_FIELD_SERVICE.getId());
    }
}

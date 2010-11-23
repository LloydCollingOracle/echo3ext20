package org.sgodden.echo.ext20.peers.command;

import nextapp.echo.app.Command;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractCommandSynchronizePeer;

import org.sgodden.echo.ext20.command.DoHighlightComponentCommand;
import org.sgodden.echo.ext20.command.DoPanelLayoutCommand;

/**
 * Synchronization peer for <code>DoHighlightComponentCommand</code>.
 */
public class DoHighlightComponentCommandPeer extends AbstractCommandSynchronizePeer {

    /**
     * Default constructor.
     */
    public DoHighlightComponentCommandPeer() {
        super();
        addProperty("componentId",
                new AbstractCommandSynchronizePeer.PropertyPeer() {
                    public Object getProperty(Context context, Command command) {
                        return ((DoHighlightComponentCommand) command)
                                .getComponentId();
                    }
                });
        addProperty("colour",new AbstractCommandSynchronizePeer.PropertyPeer() {
            public Object getProperty(Context context, Command command) {
                return ((DoHighlightComponentCommand) command)
                        .getColour();
            }
        });
        addProperty("duration",new AbstractCommandSynchronizePeer.PropertyPeer() {
            public Object getProperty(Context context, Command command) {
                return ((DoHighlightComponentCommand) command)
                        .getDuration();
            }
        });
    }

    /**
     * @see nextapp.echo.webcontainer.AbstractCommandSynchronizePeer#init(nextapp.echo.app.util.Context)
     */
    public void init(Context context) {
    }

    /**
     * @see nextapp.echo.webcontainer.CommandSynchronizePeer#getCommandClass()
     */
    @SuppressWarnings("unchecked")
    public Class getCommandClass() {
        return DoHighlightComponentCommand.class;
    }
}

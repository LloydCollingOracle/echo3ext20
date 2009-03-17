package org.sgodden.echo.ext20.peers.command;

import nextapp.echo.app.Command;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractCommandSynchronizePeer;

import org.sgodden.echo.ext20.command.DoPanelLayoutCommand;

/**
 * Synchronization peer for <code>DoPanelLayoutCommand</code>.
 */
public class DoPanelLayoutCommandPeer extends AbstractCommandSynchronizePeer {

    /**
     * Default constructor.
     */
    public DoPanelLayoutCommandPeer() {
        super();
        addProperty("panelId",
                new AbstractCommandSynchronizePeer.PropertyPeer() {
                    public Object getProperty(Context context, Command command) {
                        return ((DoPanelLayoutCommand) command)
                                .getPanelId();
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
        return DoPanelLayoutCommand.class;
    }
}

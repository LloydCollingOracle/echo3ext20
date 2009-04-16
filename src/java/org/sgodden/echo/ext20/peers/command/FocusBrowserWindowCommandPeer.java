package org.sgodden.echo.ext20.peers.command;

import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractCommandSynchronizePeer;

import org.sgodden.echo.ext20.command.FocusBrowserWindowCommand;

/**
 * Synchronization peer for <code>FocusBrowserWindowCommand</code>.
 */
public class FocusBrowserWindowCommandPeer extends AbstractCommandSynchronizePeer {

    /**
     * Default constructor.
     */
    public FocusBrowserWindowCommandPeer() {
        super();
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
        return FocusBrowserWindowCommand.class;
    }
}

package org.sgodden.echo.ext20.peers.command;

import nextapp.echo.webcontainer.AbstractCommandSynchronizePeer;

import org.sgodden.echo.ext20.command.DoPrintLayoutCommand;

/**
 * Synchronization peer for <code>DoPrintLayoutCommand</code>.
 */
public class DoPrintLayoutCommandPeer extends AbstractCommandSynchronizePeer {
    /**
     * Default constructor.
     */
    public DoPrintLayoutCommandPeer() {
        super();
    }
    /**
     * @see nextapp.echo.webcontainer.CommandSynchronizePeer#getCommandClass()
     */
    @SuppressWarnings("unchecked")
    public Class getCommandClass() {
        return DoPrintLayoutCommand.class;
    }
}

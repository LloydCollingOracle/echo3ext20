package org.sgodden.echo.ext20.peers.command;

import nextapp.echo.webcontainer.AbstractCommandSynchronizePeer;

import org.sgodden.echo.ext20.command.BrowserCloseWithUnloadHandlerCommand;

/**
 * Browser closing command peer
 * @author Lloyd Colling
 *
 */
public class BrowserCloseWithUnloadHandlerCommandPeer extends
    AbstractCommandSynchronizePeer {

    
    /**
     * @see nextapp.echo.webcontainer.CommandSynchronizePeer#getCommandClass()
     */
    public Class getCommandClass() {
        return BrowserCloseWithUnloadHandlerCommand.class;
    }
}

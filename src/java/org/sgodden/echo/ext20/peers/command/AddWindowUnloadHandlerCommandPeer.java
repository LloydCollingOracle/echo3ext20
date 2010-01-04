package org.sgodden.echo.ext20.peers.command;

import nextapp.echo.app.Command;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractCommandSynchronizePeer;

import org.sgodden.echo.ext20.command.AddWindowUnloadHandlerCommand;

/**
 * Synchronization peer for <code>AddWindowUnloadHandlerCommand</code>.
 */
public class AddWindowUnloadHandlerCommandPeer extends
		AbstractCommandSynchronizePeer {

	/**
	 * Default constructor.
	 */
	public AddWindowUnloadHandlerCommandPeer() {
		super();
		addProperty("unloadMessageText",
				new AbstractCommandSynchronizePeer.PropertyPeer() {
					public Object getProperty(Context context, Command command) {
						return ((AddWindowUnloadHandlerCommand) command)
								.getUnloadMessageText();
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
		return AddWindowUnloadHandlerCommand.class;
	}
}

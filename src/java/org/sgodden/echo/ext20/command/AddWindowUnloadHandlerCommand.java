package org.sgodden.echo.ext20.command;

import nextapp.echo.app.Command;

/**
 * A Web Application <code>Command</code> to inform the user that a window is
 * about to be closed.
 */
public class AddWindowUnloadHandlerCommand implements Command {
	/**
	 * The message to display to the user before the window is closed.
	 */
	private String unloadMessageText;

	public AddWindowUnloadHandlerCommand(String unloadMessageText) {
		super();
		this.unloadMessageText = unloadMessageText;
	}

	public String getUnloadMessageText() {
		return this.unloadMessageText;
	}
}

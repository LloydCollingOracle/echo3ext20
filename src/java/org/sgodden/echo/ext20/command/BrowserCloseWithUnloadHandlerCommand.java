package org.sgodden.echo.ext20.command;

import nextapp.echo.app.command.BrowserCloseWindowCommand;

/**
 * Command used to close an open window, enhanced to avoid problems
 * if an {@link AddWindowUnloadHandlerCommand} has been added to it.
 * @author Lloyd Colling
 *
 */
public class BrowserCloseWithUnloadHandlerCommand extends
        BrowserCloseWindowCommand {

}

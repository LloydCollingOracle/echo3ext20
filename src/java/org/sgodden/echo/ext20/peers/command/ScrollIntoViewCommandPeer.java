package org.sgodden.echo.ext20.peers.command;

import nextapp.echo.app.Command;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractCommandSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import org.sgodden.echo.ext20.command.ScrollIntoViewCommand;

/**
 * Synchronization peer for <code>ScrollIntoViewCommand</code>.
 */
public class ScrollIntoViewCommandPeer extends AbstractCommandSynchronizePeer {

    /** The associated client-side JavaScript module <code>Service</code>. */
    protected static final Service SCROLL_INTO_VIEW_SERVICE = JavaScriptService
            .forResource("EchoExt20.ScrollIntoView",
                    "org/sgodden/echo/ext20/resource/js/Ext20.ScrollIntoView.js");

    static {
        WebContainerServlet.getServiceRegistry().add(SCROLL_INTO_VIEW_SERVICE);
    }

    /**
     * Default constructor.
     */
    public ScrollIntoViewCommandPeer() {
        super();
        addProperty("toScrollId",
                new AbstractCommandSynchronizePeer.PropertyPeer() {
                    public Object getProperty(Context context, Command command) {
                        return ((ScrollIntoViewCommand) command)
                                .getToScrollId();
                    }
                });
        addProperty("scrollingComponentId",
                new AbstractCommandSynchronizePeer.PropertyPeer() {
                    public Object getProperty(Context context, Command command) {
                        return ((ScrollIntoViewCommand) command)
                                .getScrollingComponentId();
                    }
                });
    }

    /**
     * @see nextapp.echo.webcontainer.AbstractCommandSynchronizePeer#init(nextapp.echo.app.util.Context)
     */
    public void init(Context context) {
        ServerMessage serverMessage = (ServerMessage) context
                .get(ServerMessage.class);
        serverMessage.addLibrary(SCROLL_INTO_VIEW_SERVICE.getId());
    }

    /**
     * @see nextapp.echo.webcontainer.CommandSynchronizePeer#getCommandClass()
     */
    public Class getCommandClass() {
        return ScrollIntoViewCommand.class;
    }
}

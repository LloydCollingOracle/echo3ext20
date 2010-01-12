/**
 * Command execution peer: ABrowserCloseWithUnloadHandler
 */
Echo.RemoteClient.CommandExec.BrowserCloseWithUnloadHandler = Core.extend(Echo.RemoteClient.CommandExec, {
    
    $static: {

        /** @see Echo.RemoteClient.CommandExecProcessor#execute */
        execute: function(client, commandData) {
            window.onbeforeunload = null;
            window.close();
        }
    },
    
    $load: function() {
        Echo.RemoteClient.CommandExecProcessor.registerPeer("org.sgodden.echo.ext20.command.BrowserCloseWithUnloadHandlerCommand", this);
    }
});


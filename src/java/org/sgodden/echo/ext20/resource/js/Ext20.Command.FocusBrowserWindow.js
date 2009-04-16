/**
 * Command execution peer: Focus Browser Window
 */
Echo.RemoteClient.CommandExec.DoPanelLayout = Core.extend(Echo.RemoteClient.CommandExec, {
    
    $static: {

        /** @see Echo.RemoteClient.CommandExecProcessor#execute */
        execute: function(client, commandData) {
            window.focus();
        }
    },
    
    $load: function() {
        Echo.RemoteClient.CommandExecProcessor.registerPeer("org.sgodden.echo.ext20.command.FocusBrowserWindowCommand", this);
    }
});


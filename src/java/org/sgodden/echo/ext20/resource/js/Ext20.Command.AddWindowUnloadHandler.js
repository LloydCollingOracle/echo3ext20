/**
 * Command execution peer: Add Window Unload Handler
 */
Echo.RemoteClient.CommandExec.AddWindowUnloadHandler = Core.extend(Echo.RemoteClient.CommandExec, {
    
    $static: {

        /** @see Echo.RemoteClient.CommandExecProcessor#execute */
        execute: function(client, commandData) {
            if (!commandData.unloadMessageText) {
                throw new Error("unloadMessageText not specified in AddWindowUnloadHandlerCommand.");
            }
            var comm = new Echo.RemoteClient.CommandExec.AddWindowUnloadHandler();
            comm._msgText = commandData.unloadMessageText;

            window.onbeforeunload = comm.handleWindowClosing.createDelegate(comm);
        }
    },
    
    $load: function() {
        Echo.RemoteClient.CommandExecProcessor.registerPeer("org.sgodden.echo.ext20.command.AddWindowUnloadHandlerCommand", this);
    },
    
    _msgText: null,

    handleWindowClosing: function() {
        return this._msgText;
    }
});


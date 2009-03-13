/**
 * Command execution peer: Scroll into view
 */
Echo.RemoteClient.CommandExec.ScrollIntoView = Core.extend(Echo.RemoteClient.CommandExec, {
    
    $static: {

        /** @see Echo.RemoteClient.CommandExecProcessor#execute */
        execute: function(client, commandData) {
            if (!commandData.toScrollId) {
                throw new Error("toScrollId not specified in ScrollIntoViewCommand.");
            }
            if (!commandData.scrollingComponentId) {
                throw new Error("scrollingComponentId not specified in ScrollIntoViewCommand.");
            }
            Ext.get("toScrollId").scrollIntoView(Ext.get("scrollingComponentId")); 
        }
    },
    
    $load: function() {
        Echo.RemoteClient.CommandExecProcessor.registerPeer("org.sgodden.echo.ext20.peers.command.ScrollIntoViewCommand", this);
    }
});


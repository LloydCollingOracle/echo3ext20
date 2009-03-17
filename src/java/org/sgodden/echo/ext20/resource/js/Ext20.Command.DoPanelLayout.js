/**
 * Command execution peer: Do Panel Layout
 */
Echo.RemoteClient.CommandExec.DoPanelLayout = Core.extend(Echo.RemoteClient.CommandExec, {
    
    $static: {

        /** @see Echo.RemoteClient.CommandExecProcessor#execute */
        execute: function(client, commandData) {
            if (!commandData.panelId) {
                throw new Error("panelId not specified in DoPanelLayoutCommand.");
            }
            var panelId = commandData.panelId;
            var extComponent = Ext.ComponentMgr.get("C." + panelId);
            extComponent.doLayout.defer(10, extComponent); 
        }
    },
    
    $load: function() {
        Echo.RemoteClient.CommandExecProcessor.registerPeer("org.sgodden.echo.ext20.command.DoPanelLayoutCommand", this);
    }
});


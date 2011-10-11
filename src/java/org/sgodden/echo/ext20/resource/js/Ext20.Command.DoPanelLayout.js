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
            if (extComponent != null) {
            	var f = {
            		extComponent: extComponent,
            		run :function() {
	                    var hasDisposedArr = Echo.Render._disposedComponents !== null;
	                    if (!hasDisposedArr) {
	                        // Create map to contain removed components (for peer unloading).
	                        Echo.Render._disposedComponents = {};
	                    }
	                    this.extComponent.doLayout();
	                    if (!hasDisposedArr) {
	                        Echo.Render._disposedComponents = null;
	                    }
	            	}
            	}
            	f.run.defer(10, f);
            } else {
                Core.Debug.consoleWrite("Requested to layout on non-existent component: C." + panelId);
            }
        }
    },
    
    $load: function() {
        Echo.RemoteClient.CommandExecProcessor.registerPeer("org.sgodden.echo.ext20.command.DoPanelLayoutCommand", this);
    }
});


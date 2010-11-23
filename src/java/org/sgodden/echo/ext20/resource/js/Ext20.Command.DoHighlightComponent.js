/**
 * Command execution peer: Do Highlight Component
 */
Echo.RemoteClient.CommandExec.DoHighlightComponent = Core.extend(Echo.RemoteClient.CommandExec, {
    
    $static: {

        /** @see Echo.RemoteClient.CommandExecProcessor#execute */
        execute: function(client, commandData) {
            if (!commandData.componentId) {
                throw new Error("componentId not specified in DoHighlightComponentCommand.");
            }
            var componentId = commandData.componentId;
            var colour = "ffffff";
            var duration = 5;
            if (commandData.colour != null){
            	colour = commandData.colour;
            }
            if (commandData.duration != null){
            	duration = commandData.duration;
            }
            var extComponent = Ext.ComponentMgr.get("C." + componentId);
            if (extComponent != null) {
            	if (extComponent.rendered){
            		Echo.RemoteClient.CommandExec.DoHighlightComponent.highlightRenderedComponent(extComponent, colour, duration);
            	}else{
            		if (extComponent.on){
            			extComponent.on("render", 
            					Echo.RemoteClient.CommandExec.DoHighlightComponent.highlightRenderedComponent.createDelegate(this, [extComponent, colour, duration], false));
            		}
            	}
            } else {
                Core.Debug.consoleWrite("Requested to highlight a non-existent component: C." + componentId);
            }
        },
        
        highlightRenderedComponent: function(extComponent, colour, duration) {
        	if (extComponent.getEl && extComponent.getEl() != null) {
        		extComponent.getEl().highlight(colour,{ duration: duration });
        	}
        }
    },
    
    $load: function() {
        Echo.RemoteClient.CommandExecProcessor.registerPeer("org.sgodden.echo.ext20.command.DoHighlightComponentCommand", this);
    }
});


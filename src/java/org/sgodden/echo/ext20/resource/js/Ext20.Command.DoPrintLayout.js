/**
 * Command execution peer: Do Print Layout
 */
Echo.RemoteClient.CommandExec.DoPrintLayout = Core.extend(Echo.RemoteClient.CommandExec, {
    
    $static: {

        /** @see Echo.RemoteClient.CommandExecProcessor#execute */
        execute: function(client, commandData) {
            if(Ext.isIE) {
                window.document.body.parentNode.style.overflow = "auto";
                window.document.body.style.height = "auto";
                element = window.document.getElementById('approot');
                element.style.height = "auto";
                element.style.position = "static";
                element = element.childNodes[0];
                element.style.height = "auto";
                element.style.position = "static";
                element = element.childNodes[0];
                element.style.position = "static";
            }
        }
    },
    
    $load: function() {
        Echo.RemoteClient.CommandExecProcessor.registerPeer("org.sgodden.echo.ext20.command.DoPrintLayoutCommand", this);
    }
});


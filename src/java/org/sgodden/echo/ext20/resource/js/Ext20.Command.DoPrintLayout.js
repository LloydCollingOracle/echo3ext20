/**
 * Command execution peer: Do Print Layout
 */
Echo.RemoteClient.CommandExec.DoPrintLayout = Core.extend(Echo.RemoteClient.CommandExec, {

    $static: {

        /** @see Echo.RemoteClient.CommandExecProcessor#execute */
        execute: function(client, commandData) {
                element = window.document.getElementById('approot');
                element.className+=element.className? ' print-approot':'print-approot';
                element = element.childNodes[0];
                element.className+=element.className? ' print-approot-child-1':'print-approot-child-1';
                element = element.childNodes[0];
                element.className+=element.className? ' print-approot-child-2':'print-approot-child-2';

                if (navigator.appCodeName == 'Mozilla') {
                    var elements = window.document.getElementsByTagName('div');
                    for (var i = 0; i < elements.length; i++) {
                            elements[i].style.overflow = "visible";
                    }
                }
       }
    },

    $load: function() {
        Echo.RemoteClient.CommandExecProcessor.registerPeer("org.sgodden.echo.ext20.command.DoPrintLayoutCommand", this);
    }
});


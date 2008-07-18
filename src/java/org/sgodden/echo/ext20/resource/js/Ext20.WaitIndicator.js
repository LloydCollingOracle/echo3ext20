/**
 * @class Default wait indicator implementation.
 */
EchoExt20.WaitIndicator = Core.extend(Echo.RemoteClient.WaitIndicator, {

    $static: {
    
        boot: function(client) {
            client.setWaitIndicator(new EchoExt20.WaitIndicator());
            client._preWaitIndicatorDelay = 5;
        }
    },
    
    $load: function() {
        EchoBoot.addInitMethod(this.boot);
    },
    
    activate: function() {
    	alert("activate");
        var waitEl = document.getElementById("c_applicationWaitIndicator");
    	if (waitEl != null) {
    		waitEl.firstChild.src = waitEl.waitIconUrl;
        	//waitEl.style.backgroundImage = "url(" + waitEl.waitIconUrl + ")";
    	}

    },
    
    deactivate: function() {
        var waitEl = document.getElementById("c_applicationWaitIndicator");
    	if (waitEl != null) {
    		waitEl.firstChild.src = waitEl.noWaitIconUrl;
	        //waitEl.style.backgroundImage = "url(" + waitEl.noWaitIconUrl + ")";
	    }
    }
});
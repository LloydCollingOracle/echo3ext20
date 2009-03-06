/**
 * @class Default wait indicator implementation.
 */
EchoExt20.WaitIndicator = Core.extend(Echo.Client.WaitIndicator, {

    $static: {
    
        boot: function(client) {
            client.setWaitIndicator(new EchoExt20.WaitIndicator());
            client._preWaitIndicatorDelay = 5;
            client._waitIndicatorRunnable = new Core.Web.Scheduler.MethodRunnable(Core.method(client, client._waitIndicatorActivate), 
                client._preWaitIndicatorDelay, false);
        }
    },
    
    $load: function() {
        Echo.Boot.addInitMethod(this.boot);
    },
    
    activate: function() {
        var waitEl = document.getElementById("c_applicationWaitIndicator");
    	if (waitEl != null) {
    		waitEl.firstChild.src = EchoExt20.waitIconUrl;
        	//waitEl.style.backgroundImage = "url(" + waitEl.waitIconUrl + ")";
    	}

    },
    
    deactivate: function() {
        var waitEl = document.getElementById("c_applicationWaitIndicator");
    	if (waitEl != null) {
    		waitEl.firstChild.src = EchoExt20.noWaitIconUrl;
	        //waitEl.style.backgroundImage = "url(" + waitEl.noWaitIconUrl + ")";
	    }
    }
});
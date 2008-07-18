EchoExt20.ApplicationWaitIndicator = Core.extend(Echo.Label, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20ApplicationWaitIndicator", this);
        Echo.ComponentFactory.registerType("E2AWI", this);
    },

    componentType: "Ext20ApplicationWaitIndicator"
});

/**
 * Synchronisation peer for application wait indicator.
 */
EchoExt20.ApplicationWaitIndicatorSync = Core.extend(Echo.Sync.Label, {

    $load: function() {
        Echo.Render.registerPeer("Ext20ApplicationWaitIndicator", this);
    },

    renderAdd: function(update, parentElement) {
    	// invoke super class method
	    Echo.Sync.Label.prototype.renderAdd.call(this, update, parentElement);
	    
	    // get the two urls from the component
	    var waitIconUrl = Echo.Sync.ImageReference.getUrl(
        	this.component.get("waitIcon"));
        var noWaitIconUrl = Echo.Sync.ImageReference.getUrl(
        	this.component.get("noWaitIcon"));
	
	    // set them as attributes on the parent element
	    EchoExt20.waitIconUrl = waitIconUrl;
	    EchoExt20.noWaitIconUrl = noWaitIconUrl;
	},
	
	renderDispose: function(update) {
		Echo.Sync.Label.prototype.renderDispose.call(this, update, parentElement);
	}
});
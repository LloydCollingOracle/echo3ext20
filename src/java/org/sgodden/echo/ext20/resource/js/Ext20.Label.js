/**
 * Component implementation for Ext.form.Label.
 */
EchoExt20.Label = Core.extend(EchoExt20.ExtComponent, {

    /**
     * Registers this component on initial load.
     */
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20Label", this);
        Echo.ComponentFactory.registerType("E2L", this);
    },

    componentType: "Ext20Label"
});

/**
* Sync peer for labels.
*/
EchoExt20.LabelSync = Core.extend(EchoExt20.ExtComponentSync, {
    /**
     * Registers the sync peer on initial load.
     */
    $load: function() {
        Echo.Render.registerPeer("Ext20Label", this);
    },

    /**
     * Actually creates the ext component instance.  Overriden by
     * classes such as EchoExt20.GridPanelSync to create their
     * relevant Ext subclass.
     */
    newExtComponentInstance: function(options) {
        return new Ext.form.Label(options);
    },

    /**
     * Render update implementation.
     */
    renderUpdate: function(update){
        EchoExt20.ExtComponentSync.prototype.renderUpdate.call(this, update);
        // check for any property updates
        if (update.getUpdatedProperty("text") != null) {
            this.extComponent.setText(this.component.get("text"), false);
        }
        if (update.getUpdatedProperty("html") != null) {
            //protect against the object not having been rendered yet
            if(this.extComponent.getEl() != null) {
                this.extComponent.getEl().dom.innerHTML = this.component.get("html");
            }
        }
    },

    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
        options["stateful"] = false;
        // process basic properties
        var labelText = this.component.get("text");
        if (labelText != null) {
            options['text'] = labelText;
        }
        var htmlText = this.component.get("html");
        if (htmlText != null) {
            options['html'] = htmlText;
        }

        var extComponent = this.newExtComponentInstance(options);

        return extComponent;
    }

});
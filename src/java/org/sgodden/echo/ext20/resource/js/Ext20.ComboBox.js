/* =================================================================
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 2.1 of the License, or (at your option) any later version.
#
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public
# License along with this library; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
#
# ================================================================= */
/**
 * Component implementation for Ext.form.ComboBox.
 */
EchoExt20.ComboBox = Core.extend(EchoExt20.ExtComponent, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20ComboBox", this);
        Echo.ComponentFactory.registerType("E2CB", this);
    },

    focusable: true,

    componentType: "Ext20ComboBox",

    $virtual: {
        doAction: function() {
            this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
        }
    }
});

/**
 * Synchronisation peer for combo box.
 */
EchoExt20.ComboBoxSync = Core.extend(EchoExt20.TextFieldSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20ComboBox", this);
    },

    /**
     * Whether event firing is suspended (to avoid infinite client-server
     * event processing loops).
     */
    _suspendEvents: false,
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
        
        options["displayField"] = "display";
        options["valueField"] = "value";

    	if (this.component.get("editable") != null) {
             options["editable"] = this.component.get("editable");
        }
    	if (this.component.get("forceSelection") != null) {
             options["forceSelection"] = this.component.get("forceSelection");
        }
    	if (this.component.get("model") != null) {
    	     var store = this.component.get("model");
    	     var simpleStore = new Ext.data.SimpleStore({
                 fields: store.fields,
                 id: store.id,
                 data: store.data
             });
             options["store"] = simpleStore;
        }
    	if (this.component.get("typeAhead") != null) {
            options["typeAhead"] = this.component.get("typeAhead");
        }
        options['mode'] = 'local';
        
        // and then call the superclass method
    	var ret = EchoExt20.TextFieldSync.prototype.createExtComponent.call(
            this, update, options);

        ret.on(
            "render",
            this._setSelection,
            this
        );
        ret.on(
            "select",
            this._handleValueChangeEvent,
            this
        );
        ret.on(
            "select",
            this._handleSelectEvent,
            this
        );

        return ret;
    },

    /**
     * Handles the select event by requestint the component to fire
     * its action event.
     */
    _handleSelectEvent: function() {
        if (!_suspendEvents) {
            var selectedIndex = this.extComponent.getValue();
            this.component.set("selection", parseInt(selectedIndex));
            this.component.doAction();
        }
    },
    
    /**
     * Callled by super.createExtComponent to actually create the 
     * ext component of the correct type.
     */
    newExtComponentInstance: function(options) {
        return new Ext.form.ComboBox(options);
    },

    renderUpdate: function(update) {
        EchoExt20.TextFieldSync.prototype.renderUpdate.call(this, update);
        this._suspendEvents = true;
        this._setSelection();
        this._suspendEvents = false;
    },

    _setSelection: function() {
        if (this.component.get("selection") > -1) {
            this.extComponent.setValue(this.component.get("selection"));
        }
        else {
            this.extComponent.clearValue();
        }
    }

});
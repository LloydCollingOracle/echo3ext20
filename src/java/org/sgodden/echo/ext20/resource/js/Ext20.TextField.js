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
 * Component implementation for Ext.form.TextField.
 */
EchoExt20.TextField = Core.extend(EchoExt20.ExtComponent, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20TextField", this);
        Echo.ComponentFactory.registerType("E2TF", this);
    },

    focusable: true,

    componentType: "Ext20TextField"
});

/**
 * Synchronisation peer for text field.
 */
EchoExt20.TextFieldSync = Core.extend(EchoExt20.ExtComponentSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20TextField", this);
    },

    $virtual: {
        /**
         * Overridable method to actually create the correct type of
         * text field.
         */
        newExtComponentInstance: function(options) {
            return new Ext.form.TextField(options);
        }
    },
    
    _handleValueChangeEventRef: null,
    
    /**
     * Creates a new instance of the sync peer.
     */
    $construct: function() {
    	this._handleValueChangeEventRef = Core.method(this, this._handleValueChangeEvent);
    },
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
        if (this.component.get('value') != null) {
            options['value'] = this.component.get("value");
        }
        if (this.component.get('fieldLabel') != null) {
            options['fieldLabel'] = this.component.get("fieldLabel");
        }
        if (this.component.get("allowBlank") != null) {
            options['allowBlank'] = this.component.get("allowBlank");
        }
        if (this.component.get("emptyText") != null) {
            options['emptyText'] = this.component.get("emptyText");
        }
    	if ( !(this.component.isEnabled()) ) {
            options['disabled'] = true;
    	}
        if (this.component.get("regExp")) {
            options.regex = new RegExp(this.component.get("regExp"));
        }
        if (this.component.get("regExpFailureText")){
            options.regexText = this.component.get("regExpFailureText");
        }
    
    	var extComponent = this.newExtComponentInstance(options);

        /*
         * Ensure that we update the component's value on every keyup, so that
         * if there are key listeners on our ancestors, the correct value
         * gets transferred to the server.
         * 
         * I am assuming that the ext destroy processing removes these, need to prove that.
         */	
        extComponent.on(
            "render",
            function(){
                Core.Web.Event.add(this.extComponent.getEl().dom, 
                    "keyup", this._handleValueChangeEventRef, false);
                // FIXME - it's probably wrong to validate at this point
                extComponent.validate();
            }, 
            this);
		
    	return extComponent;
    },
    
    /**
     * Update the component's value from the value in the ext text field.
     */
    _handleValueChangeEvent: function() {
    	this.component.set("value", this.extComponent.getValue());
    },
    
    /**
     * Handles a server update of the field value.
     */
    renderUpdate: function(update){
        EchoExt20.ExtComponentSync.prototype.renderUpdate.call(this, update);
        this.extComponent.setValue(this.component.get("value"));
    }

});

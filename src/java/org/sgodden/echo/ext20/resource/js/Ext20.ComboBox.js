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
EchoExt20.ComboBox = Core.extend(EchoExt20.ExtComponent, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20ComboBox", this);
        Echo.ComponentFactory.registerType("E2CB", this);
    },

    focusable: true,

    componentType: "Ext20ComboBox"
	
});

EchoExt20.ComboBoxSync = Core.extend(EchoExt20.TextFieldSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20ComboBox", this);
    },
    
    /**
     * Configures the options for the new ext component.
     */
    createExtComponent: function(update, options) {
    
        // add our special options
    	if (this.component.get("displayField") != null) {
             options["displayField"] = this.component.get("displayField");
        }
    	if (this.component.get("editable") != null) {
             options["editable"] = this.component.get("editable");
        }
    	if (this.component.get("forceSelection") != null) {
             options["forceSelection"] = this.component.get("forceSelection");
        }
    	if (this.component.get("store") != null) {
             options["store"] = this.component.get("store");
        }
    	if (this.component.get("typeAhead") != null) {
             options["typeAhead"] = this.component.get("typeAhead");
        }
    	if (this.component.get("valueField") != null) {
             options["valueField"] = this.component.get("valueField");
        }
        options['mode'] = 'local';
        
    	var ret = EchoExt20.TextFieldSync.prototype.createExtComponent.call(
             this, update, options);
			 
			 		
		ret.on(
			"select",
			this._handleBlurEventRef
		);
        
        return ret;
    },
    
    /**
     * Callled by super.createExtComponent to actually create the 
     * ext component of the correct type.
     */
    newExtComponentInstance: function(options) {
        return new Ext.form.ComboBox(options);
    }

});
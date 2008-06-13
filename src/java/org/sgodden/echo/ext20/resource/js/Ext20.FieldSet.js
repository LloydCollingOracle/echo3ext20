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
EchoExt20.FieldSet = Core.extend(EchoExt20.ExtComponent, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20FieldSet", this);
        Echo.ComponentFactory.registerType("E2FS", this);
    },

    componentType: "Ext20FieldSet"
	
});

EchoExt20.FieldSetSync = Core.extend(EchoExt20.PanelSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20FieldSet", this);
    },
    
    /**
     * Configures the options for the new ext component.
     */
    createExtComponent: function(update, options) {
    
	options['autoHeight'] = true; // seems to be necessary to sort out various rendering problems
		
        // add our special option
    	if (this.component.get("checkboxToggle") != null) {
             options["checkboxToggle"] = this.component.get("checkboxToggle");
        }
    	var ret = EchoExt20.PanelSync.prototype.createExtComponent.call(
             this, update, options);
        
        return ret;
    },
    
    /**
     * Callled by super.createExtComponent to actually create the 
     * ext component of the correct type.
     */
    newExtComponentInstance: function(options) {
        return new Ext.form.FieldSet(options);
    }

});

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
 * Component implementation for Ext.form.TextArea.
 */
EchoExt20.TextArea = Core.extend(EchoExt20.TextField, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20TextArea", this);
        Echo.ComponentFactory.registerType("E2TA", this);
    },

    componentType: "Ext20TextArea"
	
});

/**
 * Synchronisation peer for text area.
 */
EchoExt20.TextAreaSync = Core.extend(EchoExt20.TextFieldSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20TextArea", this);
    },
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
    	options["stateful"] = false;
	    if (this.component.get("height")){
            options['height'] = this.component.get("height");
        }
        if (this.component.get("width")){
    	      options['width'] = this.component.get("width");
        }
        
    	var extComponent = EchoExt20.TextFieldSync.prototype.createExtComponent.call(this,update,options);

    	return extComponent;
    },

    newExtComponentInstance: function(options) {
        return new Ext.form.TextArea(options);
    }

});

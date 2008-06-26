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
EchoExt20.PasswordField = Core.extend(EchoExt20.TextField, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20PasswordField", this);
        Echo.ComponentFactory.registerType("E2PF", this);
    },

    componentType: "Ext20PasswordField"
});

/**
 * Synchronisation peer for text field.
 */
EchoExt20.PasswordFieldSync = Core.extend(EchoExt20.TextFieldSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20PasswordField", this);
    },
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
        options.inputType = "password";
    	return EchoExt20.TextFieldSync.prototype.createExtComponent.call(this, update, options);
    }
});

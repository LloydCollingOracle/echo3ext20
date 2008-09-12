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
 * Component implementation for Ext.form.TriggerField.
 */
EchoExt20.TriggerField = Core.extend(EchoExt20.TextField, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20TriggerField", this);
        Echo.ComponentFactory.registerType("E2TR", this);
    },

    componentType: "Ext20TriggerField"
    
});

/**
 * Synchronisation peer for Trigger Field.
 */
EchoExt20.TriggerFieldSync = Core.extend(EchoExt20.TextFieldSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20TriggerField", this);
    },

    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {

        var extComponent = EchoExt20.TextFieldSync.prototype.createExtComponent.call(this,update,options);

        /**
        * echoPeer is required because this.component looses
        * its scope when we overide onTriggerClick.
        */
        extComponent.echoPeer = this;
        extComponent.onTriggerClick = this._handleOnTriggerClick;
        return extComponent;
    },

     /**
     * Overides the onTriggerClick function. Requires
     * an instance level variable of this component in order
     * to call doAction().
     */
    _handleOnTriggerClick: function() {
        this.echoPeer.component.doAction();			
    },

    newExtComponentInstance: function(options) {
        return new Ext.form.TriggerField(options);
    }

});
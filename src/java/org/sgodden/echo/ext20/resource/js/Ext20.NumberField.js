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
 * Component implementation for Ext.form.NumberField.
 */
EchoExt20.NumberField = Core.extend(EchoExt20.TextField, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20NumberField", this);
        Echo.ComponentFactory.registerType("E2NF", this);
    },

    focusable: true,
    
    componentType: "Ext20NumberField"
});

/**
 * Synchronisation peer for text field.
 */
EchoExt20.NumberFieldSync = Core.extend(EchoExt20.TextFieldSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20NumberField", this);
    },

    /**
     * The last valid value
     */
    _lastValid: null,

    /**
     * Handle event required for immediate value
     * notification. 
     */
     _handleNumberValidEvent: function() {
         var newVal = this.extComponent.getValue();
         this._lastValid = newVal;
     },

     /**
      * check for required transformations on blur.
      */
     _handleNumberBlurEvent: function() {
         this.component.set("value", this._lastValid);
         this.extComponent.setValue(this._lastValid);
     },

    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
    	options["stateful"] = false;
        if (this.component.get("decimalPrecision")){
            options['decimalPrecision'] = this.component.get("decimalPrecision");
        }
        if (this.component.get("decimalSeparator")){
            options['decimalSeparator'] = this.component.get("decimalSeparator");
        }
        if (this.component.get("minValue")){
            options['minValue'] = this.component.get("minValue");
        }
        if (this.component.get("minText")){
            options['minText'] = this.component.get("minText");
        }
        if (this.component.get("maxValue")){
            options['maxValue'] = this.component.get("maxValue");
        }
        if (this.component.get("maxText")){
            options['maxText'] = this.component.get("maxText");
        }

        var extComponent = EchoExt20.TextFieldSync.prototype.createExtComponent.call(this,update,options);

        extComponent.on(
                "blur",
                this._handleNumberBlurEvent,
                this);
        extComponent.on(
                    "valid",
                    this._handleNumberValidEvent,
                    this);
    	return extComponent;
    },

    $virtual: {
        /**
         * Overridable method to actually create the correct type of
         * text field.
         */
        newExtComponentInstance: function(options) {
            return new Ext.form.NumberField(options);
        }
    }
});

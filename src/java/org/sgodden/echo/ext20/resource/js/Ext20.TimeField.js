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
 * Component implementation for Ext.form.TimeField.
 */
EchoExt20.TimeField = Core.extend(EchoExt20.ExtComponent, {
    
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20TimeField", this);
        Echo.ComponentFactory.registerType("E2TMF", this);
    },

    focusable: true,
    
    componentType: "Ext20TimeField"
    
});

/**
 * Synchronisation peer for time field.
 */
EchoExt20.TimeFieldSync = Core.extend(EchoExt20.FormFieldSync, {
    
    $load: function() {
        Echo.Render.registerPeer("Ext20TimeField", this);
    },
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
    	options["stateful"] = false;
        var timeFormat = this.component.get("timeFormat");
        if (timeFormat == null) {
            throw new Error("property 'timeFormat' must be specified on time fields");
        }
        options['format'] = timeFormat;

        options['fieldLabel'] = this.component.get("fieldLabel");
        if (this.component.get("allowBlank") != null) {
            options['allowBlank'] = this.component.get("allowBlank");
            options["plugins"] = [Ext.ux.MandatoryField];
        }
        if ( !(this.component.isEnabled()) ) {
            options['disabled'] = true;
        }
        
        var minValue = this.component.get("minValue");
        var maxValue = this.component.get("maxValue");
        var increment = this.component.get("increment");
        
        options['minValue'] = minValue;
        options['maxValue'] = maxValue;
        options['increment'] = increment;
        
        
        options['autoCreate']={tag: "input", type: "text", size: "5", autocomplete: "off"};
        var extComponent = new Ext.form.TimeField(options);

        var time = this.component.get("time");
        if (time != null) {
            extComponent.setValue(time);
        }
        
        extComponent.on(
            "render",
            this._handleTimeFieldOnRender,
            this);
        extComponent.on(
            "change",
            this._handleValueChangeEvent,
            this
        );

        return extComponent;
    },
    
    _handleTimeFieldOnRender: function() {
    	
        EchoExt20.FormFieldSync.prototype._handleOnRender.call(this);
        if (this.component.get("isValid") != null && !(this.component.get("isValid"))){
            if(this.component.get("invalidText") != null) {
            	this.extComponent.markInvalid(this.component.get("invalidText"));
            }
        }
        this.extComponent.getEl().on(
            "keyup",
            this._handleValueChangeEvent,
            this);
        this.extComponent.getEl().on(
            "click",
            this._handleClickEvent,
            this);
    },

    _handleClickEvent: function() {
        this.component.application.setFocusedComponent(this.component);
    },

    /**
     * Handles the blur event by setting the value on the component.
     * <p>
     * FIXME - don't we need to handle the 'select' event too?
     * </p>
     */
    _handleValueChangeEvent: function() {
        var value = this.extComponent.getValue();
        this.component.set("time", value);
    },
    
    /**
     * Handles update of the time value.
     */
    renderUpdate: function(update){
        EchoExt20.ExtComponentSync.prototype.renderUpdate.call(this, update);
        var time = this.component.get("time");
        if (time != null) {
            this.extComponent.setValue(time);
        }
    }
    
});
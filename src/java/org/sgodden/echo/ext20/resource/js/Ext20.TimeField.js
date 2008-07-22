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
        
        var timeFormat = this.component.get("timeFormat");
        if (timeFormat == null) {
            throw new Error("property 'timeFormat' must be specified on time fields");
        }
        options['format'] = timeFormat;

        options['fieldLabel'] = this.component.get("fieldLabel");
        if (this.component.get("allowBlank") != null) {
            options['allowBlank'] = this.component.get("allowBlank");
        }
        if ( !(this.component.isEnabled()) ) {
            options['disabled'] = true;
        }
        
        var extComponent = new Ext.form.TimeField(options);

        var time = this.component.get("time");
        if (time != null) {
            extComponent.setValue(time);
        }
        
        extComponent.on(
            "render",
            this._handleTimeFieldOnRender,
            this);
        
        return extComponent;
    },
    
    _handleTimeFieldOnRender: function() {
        alert("time field");
        EchoExt20.FormFieldSync.prototype._handleOnRender.call(this);
        this.extComponent.getEl().on(
            "keyup",
            this._handleValueChangeEvent,
            this);
        this.extComponent.getEl().on(
            "click",
            this._handleClickEvent,
            this);
        if (this.component.parent.get("layout") != null) {
            if (this.component.parent.get("layout") instanceof EchoExt20.TableLayout) {
                /*
                 * For some reason, ext decides to set IE TDs to relative -1px which
                 * screws up the positioning of form inputs in relation to their trigger
                 * buttons.  If the parent is not a form layout, then we need to remove
                 * this.
                 */
                this.extComponent.getEl().dom.style.top = "0px";
            }
       }
       else {
           /*
            * The parent layout was null, so the width of this form wrapper
            * will not have been set.  Set it to "auto".
            */
            this.extComponent.getEl().parent().dom.style.width = "auto";
       }
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
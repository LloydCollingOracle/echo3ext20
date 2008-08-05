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
EchoExt20.TextFieldSync = Core.extend(EchoExt20.FormFieldSync, {

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
        if (this.component.get("blankText") != null) {
            options['blankText'] = this.component.get("blankText");
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
        if (this.component.get("minLength")){
            options["minLength"] = this.component.get("minLength");
        }
        if (this.component.get("maxLength")){
            options["maxLength"] = this.component.get("maxLength");
        }

        options.selectOnFocus = true;
    
    	var extComponent = this.newExtComponentInstance(options);

        extComponent.on(
            "render",
            this._doOnRender,
            this);

    	return extComponent;
    },

    _doOnRender: function() {
        this.extComponent.getEl().on(
            "keyup",
            this._handleValueChangeEvent,
            this);
        this.extComponent.getEl().on(
            "click",
            this._handleClickEvent,
            this);
        if (this.component.get("size")) {
            this.extComponent.getEl().dom.size =
                this.component.get("size");
        }
    },

    _handleClickEvent: function(evt) {
        var focusedComponent = this.component.application.getFocusedComponent();
        if (!(focusedComponent == this.component)) {
            this.component.application.setFocusedComponent(this.component);
        }
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
        if (this.component.get("isValid") != null && !(this.component.get("isValid"))){
            this.extComponent.markInvalid(this.component.get("invalidText"));
        }
    }

});

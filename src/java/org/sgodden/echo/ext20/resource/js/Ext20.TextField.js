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
    
    componentType: "Ext20TextField",
    
    doAction: function() {
        this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
    }
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
        },
        
        _getComponentValue: function() {
            return this.component.get('value');
        },
        
        _getExtComponentValue: function() {
            return this.extComponent.getValue();
        }
    },
    
    /**
     * The last remembered value that put the field in error
     */
    _invalidValue: null,
    
    /**
     * The last value from the server
     */
    _initialValue: null,
    
    /**
     * The message being displayed when the field is invalid
     */
    _invalidMsg: null,
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
        options['enableKeyEvents'] = true;
        this._initialValue = this._getComponentValue();
        
        if (this.component.get('value') != null) {
            options['value'] = this.component.get("value");
        }
        if (this.component.get('fieldLabel') != null) {
            options['fieldLabel'] = this.component.get("fieldLabel");
        }
        if (this.component.get("allowBlank") != null) {
            options['allowBlank'] = this.component.get("allowBlank");
            options["plugins"] = [Ext.ux.MandatoryField];
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
    	if (this.component.get("maskRe")) {
            options.maskRe = new RegExp(this.component.get("maskRe"));
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
        if (this.component.get("minLengthText")){
            options["minLengthText"] = this.component.get("minLengthText");
        }
        if (this.component.get("maxLength")){
            options["maxLength"] = this.component.get("maxLength");
        }
        if (this.component.get("maxLengthText")){
            options["maxLengthText"] = this.component.get("maxLengthText");
        }
        if (this.component.get("grow")){
            options["grow"] = this.component.get("grow");
        }
        if (this.component.get("growMin")){
            options["growMin"] = this.component.get("growMin");
        }
        if (this.component.get("growMax")){
            options["growMax"] = this.component.get("growMax");
        }
        if (this.component.get("validationDelay")){
            options["validationDelay"] = this.component.get("validationDelay");
        }
        if (this.component.get("isValid") != null && !(this.component.get("isValid"))){
            if(this.component.get("invalidText") != null) {
                if(this._getComponentValue() != null) {
                    this._invalidValue = this._getComponentValue();
            		options["invalidText"] = this.component.get("invalidText")
            	}
            }
        }
        options["validator"] = this._checkMatchesInitialValue.createDelegate(this);
//        if (this.component.get("stripWhitespace")) {
//            options['stripCharsRe'] = /(^\s+|\s+$)/g
//        }
        /**
         * boolean logic has been reversed due to property in ext being readOnly rather than
         * the more consistent property of editable.
         */
        if (this.component.get("editable") != null){
        	if(!this.component.get("editable")){	
        		options['readOnly'] = "true";
        	}
        }
        
        options.selectOnFocus = true; 
        
    	var extComponent = this.newExtComponentInstance(options);

        extComponent.on(
            "render",
            this._doOnRender,
            this);

    	return extComponent;
    },
    
    _checkMatchesInitialValue: function(value) {
        if(this._getExtComponentValue() == this._invalidValue){
			return this.extComponent.invalidText;
		}
		else{
			return true;
		}
    },

    _doOnRender: function() {
        this.extComponent.setValue(this.component.get("value"));
        this.extComponent.getEl().on(
            "keyup",
            this._handleValueChangeEvent,
            this);
        this.extComponent.getEl().on(
            "click",
            this._handleClickEvent,
            this);
        this.extComponent.getEl().on(
            "blur",
            this._handleBlurEvent,
            this);     
        if (this.component.get("size")) {
            this.extComponent.getEl().dom.size =
                this.component.get("size");
        }
        
        if (this.component.get("isValid") != null && !(this.component.get("isValid"))){
        	this.extComponent.validate.defer(100, this.extComponent);
        }
        
        /**
        * Sends the field value back to the server on key up
        * with a given delay. Requires focus to be false
        * or the user would overwrite the value after each
        * delay.
        */
        if (this.component.get("notifyValueImmediate")){
//            this.extComponent.focus = false;
            this.extComponent.on("valid", this._handleValidEvent, this);
        }
    },
    
     /**
     * check for required transformations on blur.
     */
    _handleBlurEvent: function() {
        var newVal = this.extComponent.getValue();
        if (this.component.get("caseRestriction") != null){
            
            if (this.component.get("caseRestriction") == "UPPER"){
                newVal = this.extComponent.getValue().toUpperCase();
            }
            if (this.component.get("caseRestriction") == "LOWER"){
                newVal = this.extComponent.getValue().toLowerCase();
            }
            this.extComponent.setValue(newVal)
        }
        if (this.component.get("stripWhitespace")) {
            newVal = newVal.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
            this.extComponent.setValue(newVal)
        }
        this.component.set("value", newVal);
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
    * Handle event required for immediate value
    * notification. 
    */
    _handleValidEvent: function() {
        var oldValue = this._initialValue;
        if (typeof oldValue == 'undefined')
            oldValue = '';
        
        if (oldValue != this.extComponent.getValue())
            this.component.doAction();
    },

    /**
     * Handles a server update of the field value.
     */
    renderUpdate: function(update){
        EchoExt20.ExtComponentSync.prototype.renderUpdate.call(this, update);
        
        if ( !(this.component.isEnabled()) ) {
        	this.extComponent.setDisabled(true);
    	} else {
        	this.extComponent.setDisabled(false);
    	}
        this.extComponent.setValue(this.component.get("value"));
        this._initialValue = this.component.get("value");
        if (update.getUpdatedProperty("isValid") != null) { // only change it when the server update it.
            if (this.component.get("isValid") != null) {
                 
                if (this.component.get("isValid")) {
                    this.extComponent.clearInvalid();
                } else {
                    this._invalidValue = this.component.get("value");
                    if (this.component.get("invalidText") != null) { 
                        this.extComponent.invalidText = this.component.get("invalidText");
                    	this.extComponent.markInvalid(this.component.get("invalidText"));
                	}
                }
            }
        }
    }

});
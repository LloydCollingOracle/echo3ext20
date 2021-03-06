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
 * Component implementation for Ext.form.Combobox with remote data loading and a trigger button
 */
EchoExt20.AutocompleteComboTrigger = Core.extend(EchoExt20.ExtComponent, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20AutocompleteComboTrigger", this);
        Echo.ComponentFactory.registerType("E2ACTR", this);
    },

    focusable: true,
    
    componentType: "Ext20AutocompleteComboTrigger",

    $virtual: {
        doTriggerAction: function() {
            this.fireEvent({type: "triggerAction", source: this, actionCommand: this.get("actionCommand")});
        },

        doBeforeTriggerAction: function() {
            this.fireEvent({type: "beforeTriggerAction", source: this, actionCommand: this.get("actionCommand")});
        }
    }
});

/**
 * Synchronisation peer for Ext.form.Combobox with remote data loading and a trigger button
 */
EchoExt20.AutocompleteComboTriggerSync = Core.extend(EchoExt20.AutocompleteComboSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20AutocompleteComboTrigger", this);
    },
    
    _comboAndTriggerDisabled: false,
    
    _clickListeners: null,

    $virtual: {
        /**
         * Override-able method to actually create the correct type of
         * component.
         */
        newExtComponentInstance: function(options) {
            this._clickListeners = [];
    		if (this.component.get("autoCreate")) {
    			eval('options.autoCreate = {' + this.component.get("autoCreate") + '};');
    		}
    		options.hideTrigger1=true;
    		options.triggerClass = "x-form-search-trigger";
            var ret = new Ext.ux.TwinCombo(options);
            ret.onTrigger1Click = this._handleTrigger1Click.createDelegate(this);
            ret.onTrigger2Click = this._handleTrigger2Click.createDelegate(this);
            ret.on('beforequery', this._handleBeforeQuery, this);
            return ret;
        }
    },

    addClickListener: function(clickListener) {
        this._clickListeners.push(clickListener);
    },

    removeClickListener: function(clickListener) {
        var idx = this._clickListeners.indexOf(clickListener);
        if (idx != -1) {
            this._clickListeners = this._clickListeners.splice(idx, 1);
        }
    },
    
    _handleTrigger1Click: function() {
    	if (this._comboAndTriggerDisabled === true) {
    	    return;
    	}
    	Ext.form.ComboBox.prototype.onTriggerClick.call(this.extComponent);
    },
    
    _handleTrigger2Click: function() {
    	if (this._comboAndTriggerDisabled === true) {
    	    return;
    	}
        for (var i = 0; i < this._clickListeners.length; i++) {
            this._clickListeners[i](this);
        }
    },
    
    _handleBeforeQuery: function(queryEvent) {
    	if (this._comboAndTriggerDisabled === true) {
		    queryEvent.cancel = true;
		    return false;
		}
    },

    disableComboAndTrigger: function() {
    	this._comboAndTriggerDisabled = true;
    	if (this.extComponent == null) {
    		return;
    	}
    	this.extComponent.getTrigger(1).hide();
    },
    
    enableComboAndTrigger: function() {
    	this._comboAndTriggerDisabled = false;
    	if (this.extComponent == null) {
    		return;
    	}
    	this.extComponent.getTrigger(1).show();
    }
});

Ext.ux.TwinCombo = Ext.extend(Ext.form.ComboBox, {
	onEnable: function() {
		Ext.ux.TwinCombo.superclass.onEnable.apply(this, arguments);
		this.getTrigger(1).show();
	},

	onDisable: function() {
		Ext.ux.TwinCombo.superclass.onDisable.apply(this, arguments);
		this.getTrigger(1).hide();
	},

    onDestroy : function(){
        if(this.trigger){
            this.trigger.removeAllListeners();
            this.trigger.remove();
        }
        if (this.pageTb) {
            this.pageTb.purgeListeners();
            this.pageTb.destroy();
        }
		Ext.ux.TwinCombo.superclass.onDestroy.apply(this, arguments);
    }
});
Ext.ux.TwinCombo.prototype.initComponent = Ext.form.TwinTriggerField.prototype.initComponent;
Ext.ux.TwinCombo.prototype.getTrigger = Ext.form.TwinTriggerField.prototype.getTrigger;
Ext.ux.TwinCombo.prototype.initTrigger = Ext.form.TwinTriggerField.prototype.initTrigger;
Ext.ux.TwinCombo.prototype.onTrigger1Click = Ext.form.ComboBox.prototype.onTriggerClick;

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

    $virtual: {
        /**
         * Override-able method to actually create the correct type of
         * component.
         */
        newExtComponentInstance: function(options) {
    		if (this.component.get("autoCreate")) {
    			eval('options.autoCreate = {' + this.component.get("autoCreate") + '};');
    		}
    		options.hideTrigger1=true;
    		options.triggerClass = "x-form-search-trigger";
            var ret = new Ext.ux.TwinCombo(options);
            ret.onTrigger2Click = this._handleTrigger2Click.createDelegate(this);
            return ret;
        }
    },
    
    _handleTrigger2Click: function() {
    	this.component.doBeforeTriggerAction();
    	this.component.doTriggerAction();
    }
});

Ext.ux.TwinCombo = Ext.extend(Ext.form.ComboBox, {});
Ext.ux.TwinCombo.prototype.initComponent = Ext.form.TwinTriggerField.prototype.initComponent;
Ext.ux.TwinCombo.prototype.getTrigger = Ext.form.TwinTriggerField.prototype.getTrigger;
Ext.ux.TwinCombo.prototype.initTrigger = Ext.form.TwinTriggerField.prototype.initTrigger;
Ext.ux.TwinCombo.prototype.onTrigger1Click = Ext.form.ComboBox.prototype.onTriggerClick;

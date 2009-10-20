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
 * Component implementation for Ext.form.ComboBox.
 */
EchoExt20.ComboBox = Core.extend(EchoExt20.ExtComponent, {
    
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20ComboBox", this);
        Echo.ComponentFactory.registerType("E2CB", this);
    },

    focusable: true,

    componentType: "Ext20ComboBox",

    $virtual: {
        doAction: function() {
            this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
        }
    }
});

/**
 * Synchronisation peer for combo box.
 */
EchoExt20.ComboBoxSync = Core.extend(EchoExt20.TextFieldSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20ComboBox", this);
    },
    
    /**
     * The ext record object used to add and
     * remove records from the store.
     */
    _record: null,

    /**
     * Whether event firing is suspended (to avoid infinite client-server
     * event processing loops).
     */
    _suspendEvents: false,
    
    /**
     * The store used for the ext combo.
     */
    _store: null,
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
        
        options["displayField"] = "display";
        options["valueField"] = "value";
        options["iconField"] = "icon";
        options["triggerAction"] = "all";

        if (this.component.get("editable") != null) {
            options["editable"] = this.component.get("editable");
        }
        if (this.component.get("forceSelection") != null) {
             options["forceSelection"] = this.component.get("forceSelection");
        }
        if (this.component.get("emptyText") != null) {
            options['emptyText'] = this.component.get("emptyText");
        }
        if(this.component.get("rawValue") != null) {
            options['rawValue'] = this.component.get("rawValue");
        }
        /*
         * Get the model.
         */
        if (this.component.get("model") != null) {
            var model = this.component.get("model");
            if (model.data.length > 0 && model.data[0].length == 3) {
                // create the constructor of a record object to parse the model data
                this._record = Ext.data.Record.create([
                    {name:'display', mapping:'display'},
                    {name:'value', mapping:'value'},
                    {name:'icon', mapping:'icon'}
                ]);
                this._store = new Ext.data.SimpleStore({
                    fields: ["display","value", "icon"],
                    id: 1
                   });
                options["plugins"] = new EchoExt20.IconCombo();
            } else {
                // create the constructor of a record object to parse the model data
                this._record = Ext.data.Record.create([
                    {name:'display', mapping:'display'},
                    {name:'value', mapping:'value'}
                ]);
                this._store = new Ext.data.SimpleStore({
                    fields: ["display","value"],
                    id: 1
                   });
            }
            this._updateStore(model);
            options["store"] = this._store;
        }
        else {
            throw new Error("A combo box must have an initial model specified");
        }
        
        if (this.component.get("typeAhead") != null) {
            options["typeAhead"] = this.component.get("typeAhead");
        }
        if (this.component.get("width") != null) {
            options["width"] = this.component.get("width");
        }
        if (this.component.get("listWidth") != null) {
            options["listWidth"] = this.component.get("listWidth");
        }
        if (this.component.get("allowBlank") != null){
            options["allowBlank"] = this.component.get("allowBlank");
            options["plugins"] = [Ext.ux.MandatoryField];
        }
        options['mode'] = 'local';
        
        // and then call the superclass method
        var ret = EchoExt20.TextFieldSync.prototype.createExtComponent.call(
            this, update, options);

        ret.on(
            "blur",
            this._handleRawValueChangeEvent,
            this);

        ret.on(
            "render",
            this._setSelection,
            this
        );
        ret.on(
            "select",
            this._handleValueChangeEvent,
            this
        );
        ret.on(
            "select",
            this._handleSelectEvent,
            this
        );
        ret.on(
            "expand",
            this._handleExpandEvent,
            this
        );
        ret.on(
            "collapse",
            this._handleCollapseEvent,
            this
        );
        ret.on("focus", this._handleFocus, this);
        return ret;
    },
    
    _getComponentValue: function() {
        return this.component.get('selection');
    },
    
    _getExtComponentValue: function() {
        var record = null;
        var v = this.extComponent.getValue();
        var recordIndex = this.extComponent.valueField;
        if(this.extComponent.store.getCount() > 0){
            this.extComponent.store.each(function(r){
                if(r.data[recordIndex] == v){
                    record = r;
                }
            });
        }
        if (record != null)
            return this.extComponent.store.indexOf(record);
        else
            return -1;
    },
    
    /**
     * Ensures the application is notified that this component has been focused
     */
    _handleFocus: function() {
        var focusedComponent = this.component.application.getFocusedComponent();
        if (!(focusedComponent == this.component)) {
            this.component.application.setFocusedComponent(this.component);
        }
    },
    
    /**
     * Update the component's value from the value in the ext text field.
     */
    _handleRawValueChangeEvent: function() {
        if (this.extComponent.getRawValue() == this.component.get("rawValue"))
            return;
        this.component.set("rawValue", this.extComponent.getRawValue());
        this.component.set("selection", -1);
        for (var i = 0; i < this._store.getCount(); i++) {
            if (this._store.getAt(i).get("display") == this.extComponent.getRawValue()) {
                this.component.set("selection", this._store.getAt(i).get("value"));
            }
        }
        this.component.doAction();
    },
    
    /**
     * Handles the collapse event which fires a select event
     * if the selected value is not null.
     */
    _handleCollapseEvent: function() {
        if(this.extComponent.value == null){
            this._handleSelectEvent();
        }
    },

    /**
     * Handles the expand event by requesting the component to fire
     * its action event.
     */
   _handleExpandEvent: function() {
    },

    /**
     * Handles the select event by requesting the component to fire
     * its action event.
     */
    _handleSelectEvent: function(combo, record, index) {
        if (!this._suspendEvents) {
            if (typeof index != "undefined") {
                this.component.set("selection", record.get("value"));
                this.component.set("rawValue", this.extComponent.getRawValue());
                this.component.doAction();
                combo.focus(true, true);
            }
        }
    },
    
    /**
     * Called by super.createExtComponent to actually create the 
     * ext component of the correct type.
     */
    newExtComponentInstance: function(options) {
        return new Ext.form.ComboBox(options);
    },

    renderUpdate: function(update) {
        EchoExt20.TextFieldSync.prototype.renderUpdate.call(this, update);
        this._suspendEvents = true;
        if (update.getUpdatedProperty("model")) {
            this._updateStore(this.component.get("model"))
        }
        this._setSelection();
        
        if(this.component.get("rawValue")) {
            this.extComponent.setRawValue(this.component.get("rawValue"));
        }
        
        if (this.component.get("isValid") != null && !(this.component.get("isValid"))){
            this.extComponent.markInvalid(this.component.get("invalidText"));
        }
        this._suspendEvents = false;
    },

    _setSelection: function() {
        if (this.component.get("selection") > -1) {
            this.extComponent.setValue(this.component.get("selection"));
        }
        else {
            if(this.component.get("rawValue")) {
                this.extComponent.setRawValue(this.component.get("rawValue"));
            }
            else{
                this.extComponent.clearValue();
                this.extComponent.value = null;
            }
        }
    },
    
    _updateStore: function(model) {
        this._store.removeAll();
        for (var i = 0; i < model.data.length; i++) {
            var row = model.data[i];
            if (row.length == 2) {
                var newRecord = new this._record({
                    display: row[0],
                    value: row[1]
                });
                this._store.add(newRecord);
            } else {
                var newRecord = new this._record({
                    display: row[0],
                    value: row[1],
                    icon: row[2]
                });
                this._store.add(newRecord);
            }
        }
    }

});

/**
 * EchoExt20.IconCombo plugin for Ext.form.Combobox
 *
 * @author  Ing. Jozef Sakalos
 * @date    January 7, 2008
 *
 * @class EchoExt20.IconCombo
 * @extends Ext.util.Observable
 */
EchoExt20.IconCombo = function(config) {
    Ext.apply(this, config);
};
 
// plugin code
Ext.extend(EchoExt20.IconCombo, Ext.util.Observable, {
    init:function(combo) {
        Ext.apply(combo, {
            tpl:  '<tpl for=".">'
                + '<div class="x-combo-list-item">'
                + '<img src="{' + combo.iconField + '}"/>'
                + '{' + combo.displayField + '}'
                + '</div></tpl>',
 
            onRender:combo.onRender.createSequence(function(ct, position) {
                // adjust styles
                this.wrap.applyStyles({position:'relative'});
                //this.el.addClass('ux-icon-combo-input');
 
                // add div for icon
                this.icon = Ext.DomHelper.insertFirst(this.el.up('div.x-form-field-wrap'), {
                    tag: 'div', style:'display: inline; vertical-align: middle'
                });
                
                this.iconImg = Ext.DomHelper.append(this.icon, "<img src=\"resources/ext/images/slate/s.gif\" style=\"position: relative; right: 3px;\"/>");
            }), // end of function onRender
 
            setIconCls:function() {
                var rec = this.store.query(this.valueField, this.getValue()).itemAt(0);
                if(rec) {
                    this.iconImg.src = rec.get(this.iconField);
                }
            }, // end of function setIconCls
 
            setValue:combo.setValue.createSequence(function(value) {
                this.setIconCls();
            })
        });
    } // end of function init
}); // end of extend
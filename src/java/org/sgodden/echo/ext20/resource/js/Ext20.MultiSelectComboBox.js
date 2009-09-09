/*
 * ================================================================= # This
 * library is free software; you can redistribute it and/or # modify it under
 * the terms of the GNU Lesser General Public # License as published by the Free
 * Software Foundation; either # version 2.1 of the License, or (at your option)
 * any later version. # # This library is distributed in the hope that it will
 * be useful, # but WITHOUT ANY WARRANTY; without even the implied warranty of #
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU # Lesser
 * General Public License for more details. # # You should have received a copy
 * of the GNU Lesser General Public # License along with this library; if not,
 * write to the Free Software # Foundation, Inc., 51 Franklin Street, Fifth
 * Floor, Boston, MA 02110-1301 USA # #
 * =================================================================
 */
EchoExt20.MultiSelectComboBox = Core.extend(EchoExt20.ExtComponent, {
    $load : function() {
        Echo.ComponentFactory.registerType("MultiSelectComboBox", this);
        Echo.ComponentFactory.registerType("E2MSCB", this);
    },
    focusable: true,
    componentType : "MultiSelectComboBox",
    $virtual: {
        doAction: function() {
            this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
        }
    }
});

EchoExt20.MultiSelectComboBoxSync = Core.extend( EchoExt20.ExtComponentSync, {
    $load : function() {
        Echo.Render.registerPeer("MultiSelectComboBox", this);
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

        if (this.component.get("forceSelection") != null) {
             options["forceSelection"] = this.component.get("forceSelection");
        }
        if (this.component.get("emptyText") != null) {
            options['emptyText'] = this.component.get("emptyText");
        }
        if ( !(this.component.isEnabled()) ) {
            options['disabled'] = true;
            this.component.focusable = false;
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
        
        if (this.component.get("width") != null) {
            options["width"] = this.component.get("width");
        }
        if (this.component.get("listWidth") != null) {
            options["listWidth"] = this.component.get("listWidth");
        }
        if (this.component.get("multiSelect") != null) {
            options['multiSelect']=this.component.get("multiSelect");
        } else {
            options['multiSelect']=true;
        }
        if (this.component.get("editable") != null) {
            options['editable']=this.component.get("editable");
        } else {
            options['editable']=true;
        }
        if (this.component.get("separator") != null) {
            options['separator']=this.component.get("separator");
            options['displaySeparator']=this.component.get("separator");
        } 
        if (this.component.get("allowBlank") != null) {
            options['allowBlank'] = this.component.get("allowBlank");
        }
        if (this.component.get("blankText") != null) {
            options['blankText'] = this.component.get("blankText");
        }
        
        options['mode'] = 'local';
        this.extComponent = this.newExtComponentInstance(options);
        this.extComponent.on(
            "collapse",
            this._handleCollapseEvent,
            this
        );
        this.extComponent.on(
            "change",
            this._onChange,
            this
        );
        this.extComponent.on(
            "clearValue",
            this._onClearValue,
            this
        );
        this._setExtComponentValue();
        return this.extComponent;
    },
    _setExtComponentValue: function() {
        if ( this.component.get("multiSelect") == false) {
            this.extComponent.setValue( this.component.get("rawValue"));
        } else {
            this.extComponent.setValue( this.component.get("selectedValue"));
        }
    },
    _setComponentValue: function() {
        if ( this.component.get("multiSelect") == false) {
            this.component.set( "rawValue", this.extComponent.getRawValue());
        } else {
            this.component.set( "selectedValue", this.extComponent.getValue());
        }
    },
        
    /**
     * Handles the collapse event which fires a select event
     * if the selected value is not null.
     */
    _handleCollapseEvent: function() {
        this._setComponentValue();
        this.component.doAction();  
    },
    
    _onChange: function() {
        this._setComponentValue();
    },
    _onClearValue: function() {
        this._setComponentValue();
        this.component.doAction();
    },
    

    /**
     * Callled by super.createExtComponent to actually create the 
     * ext component of the correct type.
     */
    newExtComponentInstance: function(options) {
        return new Ext.ux.Andrie.Select(options);
    },

    renderUpdate: function(update) {
        this._suspendEvents = true;
        if (update.getUpdatedProperty("model")) {
            this._updateStore(this.component.get("model"))
        }
        if ( this.component.get("multiSelect") == false) {
            if (update.getUpdatedProperty("rawValue") != null) {
                this.extComponent.setValue(update.getUpdatedProperty("rawValue").newValue);
            }
        } else {
            if (update.getUpdatedProperty("selectedValue") != null) {
                this.extComponent.setValue(update.getUpdatedProperty("selectedValue").newValue);
            }
        }
        if (this.component.get("isValid") != null && !(this.component.get("isValid"))){
            if(this.component.get("invalidText") != null) {
                this._invalidValue = this.component.get("value");
                this.extComponent.invalidText = this.component.get("invalidText");
                this.extComponent.markInvalid(this.component.get("invalidText"));
            }
        }
        
        this._suspendEvents = false;
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

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
EchoExt20.CustomComboBox = Core.extend(EchoExt20.ExtComponent, {
    $load : function() {
        Echo.ComponentFactory.registerType("Ext20CustomComboBox", this);
        Echo.ComponentFactory.registerType("E2CCB", this);
    },
    focusable: true,
    componentType : "Ext20CustomComboBox",
/**    $virtual: {
        doAction: function() {
            this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
        }
    }
    */
});

EchoExt20.CustomComboBoxSync = Core.extend( EchoExt20.ExtComponentSync, {
    $load : function() {
        Echo.Render.registerPeer("Ext20CustomComboBox", this);
    },
    
    /**
     * Whether event firing is suspended (to avoid infinite client-server
     * event processing loops).
     */
    _suspendEvents: false,
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
        options["store"]=new Ext.data.SimpleStore({fields:[],data:[[]]});   
        options["editable"] = false;   
        options["mode"] = 'local';   
        options["triggerAction"]='all';   
        options["maxHeight"]= 300;   
        options["tpl"]="<tpl for='.'><div style='height:300px'><div id='dropDownComponent'></div></div></tpl>";   
        options["selectedClass"]= '';   
        options["onSelect"]= Ext.emptyFn;   
        
        this.extComponent = this.newExtComponentInstance(options);
        if(this.component.get("value")) {
            this.extComponent.setValue(this.component.get("value"));
        }
        
        this.extComponent.on(
            "expand",
            this._handleExpandEvent,
            this
        );
        var child = this.component.getComponent(0); // accept only one child
        Echo.Render.renderComponentAdd(update, child, null);
        return this.extComponent;
    },
        
    /**
     * Handles the collapse event which fires a select event
     * if the selected value is not null.
     */
    _handleExpandEvent: function() {
        var child = this.component.getComponent(0); // accept only one child
        var childExtComponent = child.peer.extComponent;
        childExtComponent.render( 'dropDownComponent');
    },
    
    /**
     * Callled by super.createExtComponent to actually create the 
     * ext component of the correct type.
     */
    newExtComponentInstance: function(options) {
        return new Ext.form.ComboBox(options);
    },
    
    renderUpdate: function(update) {
        this._suspendEvents = true;
        if (update.getUpdatedProperty("value")) {
            this.extComponent.setValue(this.component.get("value"));
            this.extComponent.collapse();
        }

        this._suspendEvents = false;
    }
});
    

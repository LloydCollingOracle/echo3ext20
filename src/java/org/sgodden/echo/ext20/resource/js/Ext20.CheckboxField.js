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
 * Component implementation for Ext.form.Checkbox.
 */
EchoExt20.CheckboxField = Core.extend(EchoExt20.ExtComponent, {
    
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20CheckboxField", this);
        Echo.ComponentFactory.registerType("E2CBF", this);
    },
    
    focusable: true,
    
    componentType: "Ext20CheckboxField",
	
    doAction: function() {
        this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
    }
    
});

/**
 * Synchronisation peer for checkbox.
 */
EchoExt20.CheckboxFieldSync = Core.extend(EchoExt20.ExtComponentSync, {
    
    $load: function() {
        Echo.Render.registerPeer("Ext20CheckboxField", this);
    },
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
        var selected = this.component.get("selected");
        
        options['fieldLabel'] = this.component.get("fieldLabel");
        if ( !(this.component.isEnabled()) ) {
            options['disabled'] = true;
        }
        
        var extComponent = new Ext.form.Checkbox(options);
        extComponent.setValue(selected);
        extComponent.on('check', this._handleCheckEvent, this);

        extComponent.on(
            "render",
            function() {
                extComponent.getEl().on(
                    "click",
                    function(){
                        this.component.application.setFocusedComponent(
                            this.component);
                    },
                    this
                    );
            },
            this
            );

        
        return extComponent;
    },
    
    /**
     * Handles (de)selection of the checkbox by updating the component
     * selected value, and firing the event in case there are any
     * listeners.
     */
    _handleCheckEvent: function() {
        var selected = this.extComponent.getValue();
        if (selected != this.component.get("selected")) {
            this.component.set("selected", this.extComponent.getValue());
            this.component.doAction();			
        }
    }
    
});
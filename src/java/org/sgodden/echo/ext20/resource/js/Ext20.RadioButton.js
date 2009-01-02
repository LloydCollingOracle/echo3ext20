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
EchoExt20.RadioButton = Core.extend(EchoExt20.ExtComponent, {
    
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20RadioButton", this);
        Echo.ComponentFactory.registerType("E2RB", this);
    },

    focusable: true,
    
    componentType: "Ext20RadioButton",
    
    $virtual: {
        doAction: function() {
            this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
        }
    }
    
});

EchoExt20.RadioButtonSync = Core.extend(EchoExt20.ExtComponentSync, {
    
    $load: function() {
        Echo.Render.registerPeer("Ext20RadioButton", this);
    },
    
    disableActions : false,
    
    createExtComponent: function(update, options) {
        
        var selected = this.component.get("selected");
        
        options['fieldLabel'] = this.component.get("fieldLabel");
        if ( !(this.component.isEnabled()) ) {
            options['disabled'] = true;
        }
        
        options['name'] = this.component.get("name");
        
        var extComponent = new Ext.form.Radio(options);
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
    
    _handleCheckEvent: function() {
        var oldValue = this.component.get("selected"); 
        this.component.set("selected", this.extComponent.getValue());
        if (this.disableActions == false && this.extComponent.getValue() == true)
            this.component.doAction();
    },
    renderUpdate: function(update) {
        var selected = this.component.get("selected");
        this.disableActions = true;
        this.extComponent.setValue(selected);
        this.disableActions = false;
    }
    
    
});
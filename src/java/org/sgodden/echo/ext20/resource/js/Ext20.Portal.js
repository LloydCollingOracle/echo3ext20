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
EchoExt20.Portal = Core.extend(EchoExt20.ExtComponent, {

    $load: function() {
        Echo.ComponentFactory.registerType("Ext20Portal", this);
        Echo.ComponentFactory.registerType("E2PT", this);
    },
    
    componentType: "Ext20Portal",

    $virtual: {
        doAction: function() {
            this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
        }
    }
});

EchoExt20.PortalSync = Core.extend(EchoExt20.PanelSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20Portal", this);
    },

    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
    	options["stateful"] = false;
        var extComponent = EchoExt20.PanelSync.prototype.createExtComponent.call(this, update, options);
        extComponent.on('drop', this._handleDropEvent, this);
        return extComponent;
    },

    newExtComponentInstance: function(options) {
        return new Ext.ux.Portal(options);
    }, 

    /**
    * Triggered when a portlet is moved
    */
    _handleDropEvent: function() {
        var col = 0;
        for (var x = 0; x < this.extComponent.items.length; x++) {
            var column = this.extComponent.items.get(x);
            var row = 0;
            for (var i = 0; i < column.items.length; i++) {
                var portlet = column.items.get(i);
                portlet.echoComponent.set('column', col);
                portlet.echoComponent.set('row', row);
                row++;
            }
            col++;
        }
        this.component.doAction();
    }
});

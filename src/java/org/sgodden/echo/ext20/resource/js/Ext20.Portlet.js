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
EchoExt20.Portlet = Core.extend(EchoExt20.Panel, {
    
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20Portlet", this);
        Echo.ComponentFactory.registerType("E2PTL", this);
    },
    
    componentType: "Ext20Portlet",

    $virtual: {
        doAction: function() {
            this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
        }
    }
});

EchoExt20.PortletSync = Core.extend(EchoExt20.PanelSync, {
    
    $load: function() {
        Echo.Render.registerPeer("Ext20Portlet", this);
    },

    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
        var extComponent = EchoExt20.PanelSync.prototype.createExtComponent.call(this, update, options);
        extComponent.on('collapse', this._handleCollapseEvent, this);
        extComponent.on('expand', this._handleExpandEvent, this);
        return extComponent;
    },

    newExtComponentInstance: function(options) {
        var ret = new Ext.ux.Portlet(options);
        ret.echoComponent = this.component;
        ret.component = this.component;
        return ret;
    }, 

    /**
    * Triggered when a portlet is collapsed
    */
    _handleCollapseEvent: function() {
        var portlet = this.extComponent;
        portlet.echoComponent.set('collapsed', true);
        this.component.doAction();
    }, 

    /**
    * Triggered when a portlet is expanded
    */
    _handleExpandEvent: function() {
        var portlet = this.extComponent;
        portlet.echoComponent.set('collapsed', false);
        this.component.doAction();
    }
    
});
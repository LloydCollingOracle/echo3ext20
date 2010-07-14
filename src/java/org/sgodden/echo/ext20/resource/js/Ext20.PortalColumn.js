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
EchoExt20.PortalColumn = Core.extend(EchoExt20.ExtComponent, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20PortalColumn", this);
        Echo.ComponentFactory.registerType("E2PC", this);
    },

    componentType: "Ext20PortalColumn"
	
});

EchoExt20.PortalColumnSync = Core.extend(EchoExt20.ExtComponentSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20PortalColumn", this);
    },
    
    createExtComponent: function(update, options) {
    	options["stateful"] = false;
		if (this.component.get("padding") != null) {
			options['style'] = "padding: " + this.component.get("padding"); 
		}

        var ret = new Ext.ux.PortalColumn(options);
        
        this._createChildItems(ret);
        
        return ret;
    },
    
    _createChildItems: function(column, update) {
        for (var i = 0; i < this.component.getComponentCount(); ++i) {
            var child = this.component.getComponent(i);
            Echo.Render.renderComponentAdd(update, child, null); // null because ext components create the necessary extra divs themselves

            // add the ext component created by the peer to the child items array
            var childExtComponent = child.peer.extComponent;
            if (childExtComponent == null) {
                throw new Error("No child ext component was created during renderAdd for component type: " + child.componentType);
            } 
            else {
                column.add(childExtComponent);
            }
        }    	
    }


});

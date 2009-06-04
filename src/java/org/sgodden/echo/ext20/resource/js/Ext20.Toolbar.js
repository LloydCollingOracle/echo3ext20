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
EchoExt20.Toolbar = Core.extend(EchoExt20.ExtComponent, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20Toolbar", this);
        Echo.ComponentFactory.registerType("E2T", this);
    },

    componentType: "Ext20Toolbar"
});

EchoExt20.ToolbarSync = Core.extend(EchoExt20.ExtComponentSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20Toolbar", this);
    },
    
    createExtComponent: function(update, options) {
    	var extComponent = new Ext.Toolbar(options);
        this._createChildItems(extComponent, update);
    	
    	return extComponent;
    },
    
    renderUpdate: function(update) {
        EchoExt20.ExtComponentSync.prototype.renderUpdate.call(this, update);
        var removedChildren = update.getRemovedChildren();
        for (var i = 0; i < removedChildren.length; i++) {
            var child = removedChildren[i];
            Echo.Render.renderComponentDispose(update, child, null);
            this.extComponent.items.remove( child.peer.extComponent);
            child.peer.extComponent.destroy();
        }
        
        var addedChildren = update.getAddedChildren();
        for (var i = 0; i < addedChildren.length; i++) {
            var child = addedChildren[i];
            Echo.Render.renderComponentAdd(update, child, null); // null because ext components create the necessary extra divs themselves
    
            // add the ext component created by the peer to the child items array
            var childExtComponent = child.peer.extComponent;
            if (childExtComponent == null) {
                throw new Error("No child ext component was created during renderAdd for component type: " + child.componentType);
            } 
            else {
                // toolbars fall in a heap if you call add before they have been rendered,
                // so store them up and add them when it is rendered.
                // toAdd.push(childExtComponent);
                this.extComponent.add( childExtComponent);
            }
        }
        
        
    },
    
    _createChildItems: function(extComponent, update) {
        
        var toAdd = [];
        
        for (var i = 0; i < this.component.getComponentCount(); i++) {
            var child = this.component.getComponent(i);
            Echo.Render.renderComponentAdd(update, child, null); // null because ext components create the necessary extra divs themselves

            // add the ext component created by the peer to the child items array
            var childExtComponent = child.peer.extComponent;
            if (childExtComponent == null) {
                throw new Error("No child ext component was created during renderAdd for component type: " + child.componentType);
            } 
            else {
                // toolbars fall in a heap if you call add before they have been rendered,
                // so store them up and add them when it is rendered.
                toAdd.push(childExtComponent);
            }
        }
        
        if (toAdd.length > 0) {
            extComponent.on("render", function(){
                for (var i = 0; i < toAdd.length; i++) {
                    extComponent.add(toAdd[i]);
                }
            });
        }
    }

});

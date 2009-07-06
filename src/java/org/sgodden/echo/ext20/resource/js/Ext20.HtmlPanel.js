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
EchoExt20.HtmlPanel = Core.extend(EchoExt20.Panel, {
    $load : function() {
        Echo.ComponentFactory.registerType("Ext20HtmlPanel", this);
        Echo.ComponentFactory.registerType("E2HP", this);
    },
    componentType : "Ext20HtmlPanel"
});

EchoExt20.HtmlPanelSync = Core.extend(EchoExt20.PanelSync, {
    $load : function() {
        Echo.Render.registerPeer("Ext20HtmlPanel", this);
    },

    _childRendered : false,
    renderDisplay : function(update) {
        EchoExt20.PanelSync.prototype.renderDisplay.call(this, update);
        if (!this.extComponent.rendered) return;
        if (this._childRendered) return;

        for (var i = 0; i < this.component.getComponentCount(); i++) {
            var child = this.component.getComponent(i);

            var layoutData = child.render("layoutData");
            if (!layoutData) {
                throw new Error("For HTML component, child must specify the HtmlLayoutdata");
            }
            var locationName = layoutData["locationName"];
            var element = this.extComponent.getEl();

            if (child.peer.isExtComponent) {
                var childExtComponent = child.peer.extComponent;
                if (childExtComponent == null) {
                    throw new Error("No child ext component was created during renderAdd for component type: "+ child.componentType);
                }

                var locationElement = element.child( "div#" + locationName);
                if (locationElement == null) {
                    throw new Error("Can't find element have id " + locationName + " at the template");
                }
                var toBeReplaced = element.createChild( {id: "Sub"+Echo.Application.generateUid()});
                locationElement.appendChild( toBeReplaced);
                childExtComponent.render( toBeReplaced);
            } else {
                // if it's an echoComponent, remove it frist, then add to the container.
                // child.peer._containerElement.removeChild( child.peer._node);
                Echo.Render.renderComponentDispose(update, child);
                Echo.Render.renderComponentAdd(update, child, element.child( "div#" + locationName, true));
            }
        }
        this._childRendered = true;
    }
});
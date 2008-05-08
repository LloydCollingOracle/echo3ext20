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
// TODO - abstract button class and sync peer
EchoExt20.ToolbarButton = Core.extend(EchoExt20.Button, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20ToolbarButton", this);
        Echo.ComponentFactory.registerType("E2TB", this);
    },

    componentType: "Ext20ToolbarButton"
	
});

EchoExt20.ToolbarButtonSync = Core.extend(EchoExt20.ButtonSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20ToolbarButton", this);
    },
    
    newExtComponentInstance: function(options) {
        var ret = new Ext.Toolbar.Button(options);
        return ret;
    }

});

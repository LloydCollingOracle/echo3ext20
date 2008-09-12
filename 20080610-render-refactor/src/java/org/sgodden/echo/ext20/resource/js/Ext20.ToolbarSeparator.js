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
EchoExt20.ToolbarSeparator = Core.extend(EchoExt20.ExtComponent, {
    
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20ToolbarSeparator", this);
        Echo.ComponentFactory.registerType("E2TS", this);
    },
    
    componentType: "Ext20ToolbarSeparator"
    
});

EchoExt20.ToolbarSeparatorSync = Core.extend(EchoExt20.ExtComponentSync, {
    
    $load: function() {
        Echo.Render.registerPeer("Ext20ToolbarSeparator", this);
    },
    
    createExtComponent: function(update, options) {
        return new Ext.Toolbar.Separator();
    }
    
});

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
EchoExt20.PrintButton = Core.extend(EchoExt20.Button, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20PrintButton", this);
        Echo.ComponentFactory.registerType("E2PB", this);
    },

    componentType: "Ext20PrintButton"
	
});

EchoExt20.PrintButtonSync = Core.extend(EchoExt20.ButtonSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20PrintButton", this);
    },
    
    /**
    * Handles the click event by requesting the component to fire
    * its action event.
    */
    _handleClickEvent: function() {
        window.print();
        EchoExt20.ButtonSync.prototype._handleClickEvent.call(this);
    }

});

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
EchoExt20.MenuItem = Core.extend(EchoExt20.ExtComponent, {
	
    $load: function() {
        EchoApp.ComponentFactory.registerType("Ext20MenuItem", this);
        EchoApp.ComponentFactory.registerType("E2MI", this);
    },

    componentType: "Ext20MenuItem",
    
    $virtual: {
            /**
             * Programatically performs a row click.
             */
            doAction: function() {
                this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
            }
    }
	
});

EchoExt20.MenuItemSync = Core.extend(EchoExt20.ExtComponentSync, {

    $load: function() {
        EchoRender.registerPeer("Ext20MenuItem", this);
    },
    
    _handleClickEventRef: null,
    
    $construct: function() {
    	this._handleClickEventRef = Core.method(this, this._handleClickEvent);
    },

    $virtual: {
        newExtComponentInstance: function(options) {
            return new Ext.menu.Item(options);
        }
    },
    
    createExtComponent: function(update, options) {
        
		if (this.component.get("iconClass") != null) {
			options['iconCls'] = this.component.get("iconClass");
		}

    	options['text'] = this.component.get("text");
    
    	var extComponent = this.newExtComponentInstance(options);
    	extComponent.on('click', this._handleClickEventRef);
    	
    	return extComponent;
    },
    
    _handleClickEvent: function() {
    	this.component.doAction();
    }

});
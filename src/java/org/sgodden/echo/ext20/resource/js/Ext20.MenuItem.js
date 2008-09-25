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
        Echo.ComponentFactory.registerType("Ext20MenuItem", this);
        Echo.ComponentFactory.registerType("E2MI", this);
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
        Echo.Render.registerPeer("Ext20MenuItem", this);
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
    
    if ( !(this.component.isEnabled()) ) {
        options['disabled'] = true;
        this.component.focusable = false;
    }
    
    	var extComponent = this.newExtComponentInstance(options);
    	extComponent.on('click', this._handleClickEventRef);

        if (this.component.render("icon")) {
            extComponent.on("render", this._onRender, this);
        }

    	return extComponent;
    },
    
    _handleClickEvent: function() {
    	this.component.doAction();
    },
    
    _onRender: function() {
    	this._setIconUrl();
    },

    /**
     * Handles a server update of the field value.
     */
    renderUpdate: function(update){
        EchoExt20.ExtComponentSync.prototype.renderUpdate.call(this, update);
        
        this._setIconUrl();
        
        if (this.component.isEnabled()) {
            if (this.extComponent.disabled) {
                this.extComponent.enable();
                this.component.focusable = true;
            }
        }
        else {
            if (!this.extComponent.disabled) {
                this.extComponent.disable();
                this.component.focusable = false;
            }
        }
    },
    
    _setIconUrl: function() {
    	var iconUrl = null;
    	if (this.component.isEnabled() || this.component.get("disabledIcon") == null) {
    		iconUrl = Echo.Sync.ImageReference.getUrl(
                this.component.render("icon"));
    	}
    	else {
    		iconUrl = Echo.Sync.ImageReference.getUrl(
                this.component.render("disabledIcon"));
    	}
    	if (iconUrl != null) {
            var el = this.extComponent.getEl().down("img");
            el.dom.src = iconUrl;

            if (this.component.get("text")) {
                el.addClass("x-menu-item-icon");        
            }
            else {
                el.addClass("x-menu-icon");        
            }
    	}
    }
});
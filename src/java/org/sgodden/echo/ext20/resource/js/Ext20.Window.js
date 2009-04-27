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
EchoExt20.Window = Core.extend(EchoExt20.Panel, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20Window", this);
        Echo.ComponentFactory.registerType("E2W", this);
    },

    componentType: "Ext20Window",
    
    $virtual: {
            /**
             * Programatically performs a row click.
             */
            doWindowClosing: function() {
                this.fireEvent({type: "windowClosing", source: this});
            }
    }
	
});

EchoExt20.WindowSync = Core.extend(EchoExt20.PanelSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20Window", this);
    },
    
    _handleWindowClosingEventRef: null,
    
    $construct: function() {
    	this._handleWindowClosingEventRef = Core.method(this, this._handleWindowClosingEvent);
    },
    
    newExtComponentInstance: function(options) {
        
        options['onEsc'] = Ext.emptyFn;
        
        if (this.component.get("modal") != null) {
            options['modal'] = this.component.get("modal");
        }
        
        if (this.component.get("closable") != null) {
            options['closable'] = this.component.get("closable");
        }
        
    	var extComponent = new Ext.Window(options);
         
        extComponent.on("beforeClose", this._handleWindowClosingEventRef);
    	
    	return extComponent;
    },
    
    _handleWindowClosingEvent: function() {
    	this.component.doWindowClosing();
		
        // we don't want ext to close the window - server-side code has to do it
        return false;
    },

    renderDispose: function(update) {
        EchoExt20.PanelSync.prototype.renderDispose.call(this, update);
        this.extComponent.destroy();
    }

});
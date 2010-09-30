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
/**
 * Component implementation for Ext.Button.
 */
EchoExt20.Button = Core.extend(EchoExt20.ExtComponent, {
    
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20Button", this);
        Echo.ComponentFactory.registerType("E2B", this);
    },
    
    focusable: true,

    componentType: "Ext20Button",
    
    $virtual: {
        doAction: function() {
            this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
        },

        doBeforeAction: function() {
            this.fireEvent({type: "beforeAction", source: this, actionCommand: this.get("actionCommand")});
        }
    }
    
});

/**
 * Synchronisation peer for the ext button component.
 */
EchoExt20.ButtonSync = Core.extend(EchoExt20.ExtComponentSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20Button", this);
    },
    
    _keyMap : null,

    $virtual: {
        /**
         * An overridable method to actually create the correct type of button.
         * Sub-classes will override this (to create split buttons or
         * toolbar buttons for instance).
         */
        newExtComponentInstance: function(options) {
            return new Ext.Button(options);
        },
        _setIconUrl: function() {
            var iconUrl = null;
            if (this.component.isEnabled() || this.component.get("disabledIcon") == null) {
                iconUrl = Echo.Sync.ImageReference.getUrl(
                this.component.get("icon"));
            } else {
                iconUrl = Echo.Sync.ImageReference.getUrl(
                this.component.get("disabledIcon"));
            }
            if (iconUrl != null) {
                var el = this.extComponent.getEl().down("////button"); // the left button td
                el.dom.style.backgroundImage = "url(" + iconUrl + ")";
                el = el.up("table");
                if (this.component.get("text")) {
                    el.addClass("x-btn-text-icon");
                } else {
                    el.addClass("x-btn-icon");
                }
            }
        },
   
        /**
        * Handles the click event by requesting the component to fire
        * its action event.
        */
        _handleClickEvent: function() {
            if (this.component.get("focusable")) {
                this.component.application.setFocusedComponent(this.component);
            }
            this.component.doBeforeAction();
            this.component.doAction();
        }
    },
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
    	options["stateful"] = false;
        if (this.component.get("iconClass") != null) {
                options['iconCls'] = this.component.get("iconClass");
        }
        options['text'] = this.component.get("text");
  
        if (this.component.get("tooltipText") != null) {
            options['tooltip'] = this.component.get("tooltipText");
        }
        
        if ( !(this.component.isEnabled()) ) {
            options['disabled'] = true;
            this.component.focusable = false;
        }
        // We can set the button's template
        if ( this.component.get( "template") != null) {
            options['template'] = new Ext.Template( this.component.get( "template"));
        }

        // see if we have a menu child item
        if (this.component.getComponentCount() == 1) {
            var child = this.component.getComponent(0);
            if (child instanceof EchoExt20.Menu) {
                Echo.Render.renderComponentAdd(update, child, null);
                var menu = child.peer.extComponent;
                if (menu == null) {
                    throw new Error("Menu not created for button");
                }
                options['menu'] = menu;
            }
            else {
                throw new Error("Illegal child added to a button");
            }
        }

        options['enableToggle'] = this.component.get("enableToggle");

        //if this button belongs to a toggle group we set the
        //toggleGroup name and set the enableToggle to true on the button.
        if (this.component.get("toggleGroup")){
            options['enableToggle'] = true;
            options['allowDepress'] = false;
            options['toggleGroup'] = this.component.get("toggleGroup");
        }

        if (this.component.get("pressed")){
            options['pressed'] = true;
        }

        var extComponent = this.newExtComponentInstance(options);
        
        extComponent.on('click', this._handleClickEvent, this);
        extComponent.on("render", this._onRender, this);

        extComponent.on('menutriggerover', this._handleMenuTriggerOver, this);
        extComponent.on('menutriggerout', this._handleMenuTriggerOut, this);
        extComponent.on('toggle', this._handleToggle, this);
        
        return extComponent;
    },
    
    /**
    * shows the menu when the mouse leaves the button if it is a 
    * hover menu button
    */
    _handleMenuTriggerOver: function() {
         if(this.component.get("hoverMenu")){
        	 this._isMenuTriggerOver = true;
             var task = new Ext.util.DelayedTask(
                 function() {
                     if (this._isMenuTriggerOver == true) {
                         this.extComponent.showMenu();
                     }
                 },
                 this);
             task.delay(100);
         }
    },
    
    /**
     * Hides the menu when the mouse leaves the button if it is a 
     * hover menu button.
     */
    _handleMenuTriggerOut: function() {
        this._isMenuTriggerOver = false;
    },
    
    /**
     * Handles the press of a toggle button
     */
    _handleToggle: function() {
        this.component.set("pressed", this.extComponent.pressed);
    },
    
    _onRender: function() {
        if (this.extComponent.rendered == true) {
            if (this.component.get("icon")) {
                this._setIconUrl();
            }
            
            
            if (this.component.getComponentCount() == 1) {
                var child = this.component.getComponent(0);
                if (child instanceof EchoExt20.Menu) {
                    _keyMap = new Ext.KeyMap(this.extComponent.getEl(), {
                        key: Ext.EventObject.CONTEXT_MENU,
                        handler: this._toggleMenu,
                        scope: this,
                        stopEvent: true
                    });
                }
            }
        }
    },
    
    _toggleMenu: function(key, e) {
        e.stopEvent();
        
        if (this.extComponent.hasVisibleMenu()) {
            this.extComponent.hideMenu();
        } else {
            this.extComponent.showMenu();
        }
    },

    /**
     * Handles a server update of the field value.
     */
    renderUpdate: function(update){
        EchoExt20.ExtComponentSync.prototype.renderUpdate.call(this, update);
        
        this._setIconUrl();
        
        var text = this.component.get("text");
        if (text) this.extComponent.setText(text);
        
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
        if (this.component.get("toggleGroup")){
            if (this.component.get("pressed")){
                this.extComponent.toggle(this.component.get("pressed"));
            }
        }
        
        if (update.hasRemovedChildren()) {
            this.extComponent.hideMenu();
            this.extComponent.menu = null;
        }
        
        if (update.hasAddedChildren()) {
            if (this.component.getComponentCount() == 1) {
                var child = this.component.getComponent(0);
                if (child instanceof EchoExt20.Menu) {
                    Echo.Render.renderComponentAdd(update, child, null);
                    var menu = child.peer.extComponent;
                    if (menu == null) {
                        throw new Error("Menu not created for button");
                    }
                    this.extComponent.menu = menu;
                }
                else {
                    throw new Error("Illegal child added to a button");
                }
            }
        }
    },
    

    renderDispose: function(update) {
        EchoExt20.ExtComponentSync.prototype.renderDispose.call(this, update);
        if (this.extComponent != null && this.extComponent.rendered) {
            Ext.ButtonToggleMgr.unregister(this.extComponent);
        }
    }
});

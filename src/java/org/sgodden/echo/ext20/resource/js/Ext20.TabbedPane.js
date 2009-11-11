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
EchoExt20.TabbedPane = Core.extend(EchoExt20.Panel, {
    
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20TabbedPane", this);
        Echo.ComponentFactory.registerType("E2TP", this);
    },
    
    componentType: "Ext20TabbedPane",
    
    doActiveTabChange: function(newTab){
        this.fireEvent({
            type: "activeTabChangeEvent",
            source: this,
            data: newTab
        });
    },
        
    doTabClose: function(data){
        this.fireEvent({
            type: "tabClose",
            source: this,
            data: data
        });
    }
    
});

/**
 * Sync peer for tabbed panes.
 */
EchoExt20.TabbedPaneSync = Core.extend(EchoExt20.PanelSync, {
    
    $load: function() {
        Echo.Render.registerPeer("Ext20TabbedPane", this);
    },
	
    hideWhenAddingChildren: false,
	
    _tabCount: 0,
	
    // used to prevent us notifying the server of a tab change they just told us about
    _tabChangeNotificationSuspended: false,
    
    // used to prevent us vetoing the removal of a tab by a server update
    _tabCloseNotificationSuspended: false,
	
    newExtComponentInstance: function(options) {
        // ignore the first tab change
        this._tabChangeNotificationSuspended = true;

        var ret = new Ext.TabPanel(options);

        
        ret.on(
            "beforetabchange", 
            function(tabPanel, newTab, oldTab){
                if (!(this._tabChangeNotificationSuspended)) {
                    this.component.doActiveTabChange(this.extComponent.items.indexOf(newTab));
                    return false;
                }
                
                this._tabChangeNotificationSuspended = false;
                },
            this
        );

        ret.on(
            "tabchange", 
            function(tabPanel, newTab, oldTab){
            	this._notifyLayoutChanges();
            },
            this
        );
        
        ret.on(
                "beforeremove", 
                this._handleRemoveComponent,
                this
                );
        
        return ret;
    },
    
    _handleRemoveComponent: function(container, child) {
        if (this._tabCloseNotificationSuspended == true)
            return true;
        
        Core.Debug.consoleWrite("synca: " + new Date().getTime());
        for (var i = 0; i < this.component.getComponentCount(); i++) {
            if (this.component.getComponent(i).renderId == child.id)
                this.component.doTabClose(i);
        }
        return false;
    },
    
    createExtComponent: function(update, options) {
        if (this.component.get('plain') != null) {
            options['plain'] = this.component.get("plain");
        }
        options.activeTab = this.component.get("activeTabIndex");
        options.deferredRender = true;
        options.enableTabScroll = true;
        return EchoExt20.PanelSync.prototype.createExtComponent.call(
                this, update, options);
    },
	
    renderUpdate: function(update) {
        var activeTabIndex = this.component.get("activeTabIndex");
        this._tabCloseNotificationSuspended = true;
        this._tabChangeNotificationSuspended = true;
        EchoExt20.PanelSync.prototype.renderUpdate.call(this, update);
        this._tabCloseNotificationSuspended = false;
        this._tabChangeNotificationSuspended = false;

        // we always set the tab index as the child add/removes may have
        // changed the tab index
        var activeExtComponent = this.extComponent.items.get(activeTabIndex);

        this._tabChangeNotificationSuspended = true;
        this.extComponent.setActiveTab(activeExtComponent);
        this._tabChangeNotificationSuspended = false;
    }
    
});

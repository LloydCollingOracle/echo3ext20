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
    
    doActiveTabChange: function(){
        this.fireEvent({
            type: "activeTabChangeEvent",
            source: this
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
	
	newExtComponentInstance: function(options) {
        
        var ret = new Ext.TabPanel(options);
        ret.on(
			"beforetabchange", 
			function(tabPanel, newTab, oldTab){
				if (!(this._tabChangeNotificationSuspended)) {
					this.component.set(
						"activeTabIndex",
						newTab.echoComponent.childIndex
						);
					this.component.doActiveTabChange();
				}
				
				this._tabChangeNotificationSuspended = false;
			},
			this
		);
        ret.on(
			"tabchange", 
			function(tabPanel, newTab, oldTab){
				ret.doLayout();
			},
			this
		);
        
        return ret;
    },
    
    createExtComponent: function(update, options) {
        options['activeTab'] = this.component.get("activeTabIndex");
        return EchoExt20.PanelSync.prototype.createExtComponent.call(
             this, update, options);
    },
	
	renderUpdate: function(update) {
		EchoExt20.PanelSync.prototype.renderUpdate.call(this, update);
		
		if (update.getUpdatedProperty("activeTabIndex") != null) {
			var activeTabIndex = this.component.get("activeTabIndex");
			var activeChild = this.component.getComponent(activeTabIndex);
			var activeExtComponent = activeChild.peer.extComponent;
		
			this._tabChangeNotificationSuspended = true;
		
			this.extComponent.setActiveTab(activeExtComponent);
		}
	}
    
});
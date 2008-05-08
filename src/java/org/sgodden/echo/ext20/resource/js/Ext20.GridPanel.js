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
EchoExt20.GridPanel = Core.extend(EchoExt20.ExtComponent, {
    
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20GridPanel", this);
        Echo.ComponentFactory.registerType("E2GP", this);
    },

    componentType: "Ext20GridPanel",
    focusable: true,

    $virtual: {
            /**
             * Programatically performs a row click.
             */
            doAction: function() {
                this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
            }
    }
    
});

EchoExt20.GridPanelSync = Core.extend(EchoExt20.PanelSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20GridPanel", this);
    },

	_selectedRows: {},
    
    _handleRowSelectEventRef: null,
    _handleRowDeselectEventRef: null,
     
    $construct: function() {
    	this._handleRowSelectEventRef = Core.method(this, this._handleRowSelectEvent);
    	this._handleRowDeselectEventRef = Core.method(this, this._handleRowDeselectEvent);
    },
    
    newExtComponentInstance: function(options) {
        var ret = new Ext.grid.GridPanel(options);
        
        ret.on("rowdblclick", this._handleRowActivation, this);
        
        return ret;
    },
    
    createExtComponent: function(update, options) {

        options["store"] = this.component.get("model");
        options["cm"] = this.component.get("columnModel");
        var sm = new Ext.grid.RowSelectionModel({singleSelect:true});
        sm.on("rowselect", this._handleRowSelectEventRef);
        sm.on("rowdeselect", this._handleRowDeselectEventRef);
        options["sm"] = sm;
        
        options["border"] = true;
        
        options["bbar"] = new Ext.PagingToolbar({
            pageSize: 25,
            store: this.component.get("model"),
            displayInfo: true,
            displayMsg: 'Displaying records {0} - {1} of {2}',
            emptyMsg: "No records to display"
        });

        return EchoExt20.PanelSync.prototype.createExtComponent.call(this, update, options);
    },
	
	renderUpdate: function(update) {
		EchoExt20.PanelSync.prototype.renderUpdate.call(this, update);
		
		var updatedStore = update.getUpdatedProperty("model");
		if (updatedStore != null) {
			this.extComponent.reconfigure(
				this.component.get("model"),
				this.component.get("columnModel"));
		}
	},
    
    _handleRowActivation: function() {
        this.component.doAction();
    },
    
    _handleRowSelectEvent: function(selectionModel, rowIndex, record) {
        // update the selection value
        this._selectedRows[rowIndex] = true;

        // and now update the selection in the component
        var selectionString = "";
        var first = true;

        for (var row in this._selectedRows) {
                if (!first) {
                        selectionString += ",";
                }
                first = false;

                selectionString += row;
        }

        this.component.set("selection", selectionString);
    },
    
    _handleRowDeselectEvent: function(selectionModel, rowIndex, record) {
		// update the selection value
		if (this._selectedRows[rowIndex] != null) {
			delete this._selectedRows[rowIndex];
		}
    }

});

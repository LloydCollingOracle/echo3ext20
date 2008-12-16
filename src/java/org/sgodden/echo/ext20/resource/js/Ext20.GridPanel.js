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

    /**
     * Programatically performs a row click.
     */
    doAction: function() {
        this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
    },

    doSort: function() {
        this.fireEvent({type: "sort", source: this});
    },

    doSelect: function() {
        this.fireEvent({type: "select", source: this});
    }
    
});

/*
 * TODO - write a custom proxy which handles all the load and sort 
 * operations, raising events to the server side when e.g. sort events
 * occur.
 * 
 * So, configure a Store with an ArrayReader, and a custom Proxy
 * which simply returns the array of current data, and which
 * passed back sort events to this sync peer, which raises
 * an event to the server side, which reconstructs the store
 * appropriately.
 * 
 * TODO - ensure that multiple selections across different
 * pages are handled and not lost.
 */
EchoExt20.GridPanelSync = Core.extend(EchoExt20.PanelSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20GridPanel", this);
    },

    _ctrlKeyDown: false,
    _handleSelectionEvents: false,
    _handleSortEvents: false,
    _reconfigureOnRender: false,
    _selectedRows: null,
    
    createExtComponent: function(update, options) {
        this._handleSortEvents = false;
        this._handleSelectionEvents = false;
        
        this._selectedRows = {};

        options["store"] = this._makeStore();
        
        if (this.component.get("forceFit")) {
        		options["viewConfig"] = {forceFit:true}
        }
        
        var view;
        if (this.component.get("groupField")) {
            view = new Ext.grid.GroupingView({
                groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Items" : "Item"]})'
            });
        }
        if (view) {
            options["view"] = view;
        }
		
        options["cm"] = this.component.get("columnModel");
        
        // ext does not support multiple interval selection
        var smode = this.component.get("selectionMode");
        var ss = true;
        if (smode != "S") {
        	ss = false;
        }
        var sm = new Ext.grid.RowSelectionModel({singleSelect: ss});
        
        sm.on("rowselect", this._handleRowSelectEvent, this);
        sm.on("rowdeselect", this._handleRowDeselectEvent, this);
        options["sm"] = sm;
        
        options["border"] = true;
        
        Ext.Element.get("approot").on("keydown",
                this._handleKeyDownEvent, this);
        Ext.Element.get("approot").on("keyup",
                this._handleKeyUpEvent, this);

        this._handleSortEvents = true;
        this._handleSelectionEvents = true;

        var ret = EchoExt20.PanelSync.prototype
                .createExtComponent.call(this, update, options);

        ret.on("rowdblclick", this._handleRowActivation, this);
        ret.on("render",this._handleOnRender,this);
        ret.on("columnmove",this._handleColumnMove,this);
        ret.on("columnresize",this._handleColumnResize,this);
        
        return ret;
    },

    doSort: function(fieldName, sortDirection) {
        this.component.set("sortField", fieldName);
        this.component.set("sortDirection", sortDirection);
        
        var colModel = this.extComponent.getColumnModel();
        var columns = colModel.getColumnsBy(function(columnConfig, index) { return true; });
        for (var i = 0; i < columns.length; i++) {
            if (columns[i].dataIndex == fieldName)
                columns[i].sortDirection = sortDirection;
        }
        this.component.set("columnModel", this.extComponent.getColumnModel());
        this.component.doSort();
    },

    _handleColumnMove: function(oldIndex, newIndex) {
    	this.component.set("columnModel", this.extComponent.getColumnModel());
        this.component.doSort();
    },

    _handleColumnResize: function(colIndex, newSize) {
    	this.component.set("columnModel", this.extComponent.getColumnModel());
    },

    _handleKeyDownEvent: function(evt) {
        if (evt.keyCode == 17) {
            this._ctrlKeyDown = true;
        }
    },

    _handleKeyUpEvent: function(evt) {
        this._ctrlKeyDown = false;
    },

    _handleOnRender: function() {
    	this._handleServerSelections();
    	if (this._reconfigureOnRender) {
	        this.extComponent.reconfigure(
	          this._makeStore(),
	          this.component.get("columnModel")
	        );
    	}
    },
    
    _handleRowActivation: function() {
        this.component.doAction();
    },
    
    _handleRowDeselectEvent: function(selectionModel, rowIndex, record) {
        if (this._handleSelectionEvents) {
            var pageOffset = this.component.get("pageOffset");
            var index = pageOffset + rowIndex;
            if (this._selectedRows[index] != null) {
                delete this._selectedRows[index];
            }
            this._updateRowSelection();
        }
    },
    
    _handleRowSelectEvent: function(selectionModel, rowIndex, record) {
        if (this._handleSelectionEvents) {
            var pageOffset = this.component.get("pageOffset");
            var index = pageOffset + rowIndex;
            if (!this._ctrlKeyDown) {
                this._selectedRows = {};
            }
            this._selectedRows[index] = true;
            this._updateRowSelection();
            this.component.doSelect();
        }
    },

    _handleServerSelections: function() {
        this._selectedRows = {};
        var selectionString = this.component.get("selection");
        if (selectionString) {
            var rows = selectionString.split(",");
            for (var i = 0; i < rows.length; i++) {
                var row = parseInt(rows[i]);
                this._selectedRows[row] = true;
            }
        }

        var first = this.component.get("pageOffset");
        var last = first + this.extComponent.getStore().getCount();
        for (var row in this._selectedRows) {
            if (row >= first && row < last) {
                this.extComponent.getSelectionModel().selectRow(
                        row - first, true);
            }
        }

    },
    
    /**
     * Creates an ext store from the component's model representation.
     */
    _makeStore: function() {
        var model = this.component.get("model");

        /*
         * Create an ArrayReader using the fields
         * provided in the model.
         */
        var recordMappings = [];
        for (var i = 0; i < model.fields.length; i++) {
            recordMappings.push({
                name: model.fields[i],
                mapping: i
            });
        }
        var record = Ext.data.Record.create(recordMappings);
        var reader = new Ext.data.ArrayReader({}, record);
        var proxy = new EchoExt20.GridPanelDataProxy(model.data, this);

        var store;
        

        var sortInfo = null;
        var sortField = this.component.get("sortField");
        if (sortField) {
            var sortDirection = this.component.get("sortDirection");
            if (sortDirection == null) {
                sortDirection = "ASC";
            }
            sortInfo = {
                field: sortField,
                direction: sortDirection
            }
        } else {
            var colModel = this.component.get("columnModel");
            var firstCol = colModel.getColumnsBy(function(columnConfig, index) { return true; })[0];
            sortInfo = {
                field: firstCol.header,
                direction: firstCol.sortDirection
            }
        }

        var groupField = this.component.get("groupField");
        if (groupField) {
            store = new Ext.data.GroupingStore({
                groupField: groupField,
                proxy: proxy,
                reader: reader,
                sortInfo: sortInfo,
                remoteSort: true
            });
        }
        else {
            store = new Ext.data.Store({
                reader: reader,
                proxy: proxy,
                sortInfo: sortInfo,
                remoteSort: true
            });
        }

        store.load();
        
        return store;
    },
    
    newExtComponentInstance: function(options) {
        var ret = new Ext.grid.GridPanel(options);
        return ret;
    },

    renderDispose: function(update) {
        EchoExt20.PanelSync.prototype.renderDispose.call(this, update);
        Ext.Element.get("approot").un("keydown",
                this._handleKeyDownEvent);
        Ext.Element.get("approot").un("keyup",
                this._handleKeyUpEvent);
    },
	
    renderUpdate: function(update) {
        EchoExt20.PanelSync.prototype.renderUpdate.call(this, update);

        // suspend event handling while we are manipulating
        this._handleSortEvents = false;
        this._handleSelectionEvents = false;
		
        var updatedStore = update.getUpdatedProperty("model");
        if (updatedStore != null) {
        	/*
        	 * The grid can only reconfigure itself if it is rendered.
        	 */
        	if (this.extComponent.rendered) {
	            this.extComponent.reconfigure(
	              this._makeStore(),
	              this.component.get("columnModel")
	            );
        	}
        	else {
        		/*
        		 * Make a note to reconfigure when the grid is rendered.
        		 */
        		this._reconfigureOnRender = true;
        	}
        }

        this._handleServerSelections();

        // resume event handling
        this._handleSortEvents = true;
        this._handleSelectionEvents = true;
    },

    _updateRowSelection: function() {
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
    }

});

/**
 * Custom ext DataProxy implementation which notifies
 * the sync peer of sort change events.
 */
EchoExt20.GridPanelDataProxy = function(data, syncPeer) {
    Ext.data.MemoryProxy.superclass.constructor.call(this);
    this.data = data;
    this.syncPeer = syncPeer;
}

Ext.extend(EchoExt20.GridPanelDataProxy, Ext.data.DataProxy, {
    
    syncPeer: null,
    
    $construct: function(syncPeer) {
        this.syncPeer = syncPeer;
    },

    load : function(params, reader, callback, scope, arg){
        params = params || {};
		
        var result;
		
        if (params.sort) {
            var sortField = params.sort;
            var direction = params.dir;
            if (this.syncPeer._handleSortEvents) {
                this.syncPeer.doSort(sortField, direction);
            }
        }
		
        try {
            result = reader.readRecords(this.data);
        }
        catch(e) {
            this.fireEvent("loadexception", this, arg, null, e);
            callback.call(scope, null, arg, false);
            return;
        }
        callback.call(scope, result, arg, true);
    }
	
});



EchoExt20.ColumnModel = function(attributes)  {
	EchoExt20.ColumnModel.superclass.constructor.call(this, attributes);
	this.className = "Ext20ColumnModel";
};

Ext.extend(EchoExt20.ColumnModel, Ext.grid.ColumnModel, {
});

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
    },

    doColumnRemove: function(colIndex) {
        this.fireEvent({type: "columnRemove", source: this, data: colIndex});
    },

    doColumnAdd: function(colIndex) {
        this.fireEvent({type: "columnAdd", source: this});
    },

    doGroup: function() {
        this.fireEvent({type: "group", source: this});
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
    _model: null,
    cellContextMenu: null,
    rowContextMenu: null,
    headerContextMenu: null,
    _sm: null,
    _selectionTask: null,
    _loadMask: null,
    _storedSelections: null,
    _showColAddRemove: true,
    
    
    $virtual: {
        _renderColumn: function(value, metadata, record, rowIndex, colIndex, store) {
            var dataIndex = this.extComponent.getColumnModel().getDataIndex(colIndex);
            var actualIndex = record.fields.indexOfKey(dataIndex);
            var renderFuncIndex = this._model.renderedData[rowIndex][actualIndex];
            
            var renderedValue = null;
            eval(this._model.renderFunctions[renderFuncIndex]);
            return renderedValue;
        },
        
        _getContextMenuIndices: function() {
            var cellContextMenuIndex = -1;
            var rowContextMenuIndex = -1;
            var headerContextMenuIndex = -1;
            
            var menus = new Array();
            for (var i = 0; i < this.component.getComponentCount(); i++) {
                if (this.component.getComponent(i) instanceof EchoExt20.Menu) {
                    menus[menus.length] = i;
                }
            }
            var menuIndex = 0;
            
            if (this.component.get("hasCellContextMenu") == true) {
                cellContextMenuIndex = menus[menuIndex++];
            }
            if (this.component.get("hasRowContextMenu") == true) {
                rowContextMenuIndex = menus[menuIndex++];
            }
            if (this.component.get("hasHeaderContextMenu") == true) {
                headerContextMenuIndex = menus[menuIndex++];
            }
            
            var ret = new Array();
            ret[0] = cellContextMenuIndex;
            ret[1] = rowContextMenuIndex;
            ret[2] = headerContextMenuIndex;
            
            return ret;
        },
        
        /**
         * Performs post-render updating
         */
        _handleOnRender: function() {
            if (this._reconfigureOnRender) {
                this.extComponent.reconfigure(
                  this._makeStore(),
                  this.component.get("columnModel")
                );
            }
        
            if (this.cellContextMenu != null) {
                this.extComponent.un('cellcontextmenu', this._handleCellContextMenu, this);
                this.extComponent.on('cellcontextmenu', this._handleCellContextMenu, this);
            }
            if (this.rowContextMenu != null) {
                this.extComponent.un('rowcontextmenu', this._handleRowContextMenu, this);
                this.extComponent.on('rowcontextmenu', this._handleRowContextMenu, this);
            }
            if (this.headerContextMenu != null) {
                this.extComponent.un('headercontextmenu', this._handleHeaderContextMenu, this);
                this.extComponent.on('headercontextmenu', this._handleHeaderContextMenu, this);
            }
            
            var loadMaskOptions = {};
            loadMaskOptions.msg = this.component.get("loadingMsg");
            
            this._loadMask = new Ext.LoadMask(this.extComponent.el, loadMaskOptions);
            
            /*
             * Defer this call because the ExtJS grid uses innerHTML in order to render itself.
             * 
             * The use of innerHTML means that the dom nodes will not be created until after the 
             * current thread of execution completes. Since the selection of rows requires that 
             * the dom nodes be available, we defer this call for a few moments to allow the 
             * browser to create the dom nodes, ready to be updated with the selection.
             */
            this._handleServerSelections.defer(50, this);
            if (this.component.get("showCheckbox")) {
                this._applyHeaderCheckedIfNeeded.defer(55, this);
            }
        },
        
        /**
         * Configures a column
         */
        _configureColumn: function(thisCol, options) {
            if (thisCol instanceof Ext.grid.CheckColumn || thisCol.id == 'checker') {
                if (options != null) {
                	if (!options["plugins"]) {
                        options["plugins"] = [];
                        if (this._showColAddRemove) {
                            options["plugins"][0] = new EchoExt20.GridColAddRemove();
                        }
                	}
                	if (this._showColAddRemove) {
                	    options["plugins"][1] = thisCol;
                	} else {
                        options["plugins"][0] = thisCol;
                	}
                }
            } else {
                thisCol.renderer = this._renderColumn.createDelegate(this);
            }
        },
        
        updateCompleted: function(update) {
            
        }
    },
    
    createExtComponent: function(update, options) {
        this._handleSortEvents = false;
        this._handleSelectionEvents = false;
        this._showColAddRemove = this.component._listenerList == null || this.component._listenerList.getListenerCount("columnAdd") > 0;
        
        this._selectedRows = {};
        
        this._selectionTask = new Ext.util.DelayedTask(this._doSelect, this);
        
        options["plugins"] = [];
        if (this._showColAddRemove) {
            options["plugins"][0] = new EchoExt20.GridColAddRemove();
        }

        this._model = this.component.get("model");
        options["store"] = this._makeStore();
                
        var view = null;
        if (this.component.get('allowGrouping')) {
            view = new Ext.grid.GroupingView({
                autoFill:this.component.get("autoFill"),
                forceFit:this.component.get("forceFit"),
                enableGroupingMenu:true,
                enableNoGroups:true,
                groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Items" : "Item"]})'
            });
        } else {
            view = new Ext.grid.GridView({
                autoFill:this.component.get("autoFill"),
                forceFit:this.component.get("forceFit")
            });
        }
        options["view"] = view;
        
        if (this.component.get('autoExpandColumn')) {
        	options.autoExpandColumn = this.component.get('autoExpandColumn');
        }
        
        // ext does not support multiple interval selection
        var smode = this.component.get("selectionMode");
        var ss = true;
        if (smode != "S") {
            ss = false;
        }
        if (this.component.get("showCheckbox")){
        	sm = new Ext.grid.CheckboxSelectionModel({singleSelect: ss});
        }else{
        	sm = new Ext.grid.RowSelectionModel({singleSelect: ss});
        }
        this._sm = sm;
        if (this.component.get("stripeRows")){
            options["stripeRows"] = this.component.get("stripeRows");
        }
        
        // configure the column model
        options["cm"] = this.component.get("columnModel");
        this._configureColumnModel(options.cm, sm, options, false);
        
        if (this.component.get("hideHeaders")) {
            view.templates = {};
            view.templates.master = new Ext.Template(
                    '<div class="x-grid3" hidefocus="true">',
                    '<div class="x-grid3-viewport">',
                        '<div class="x-grid3-header" style="display:none;"><div class="x-grid3-header-inner"><div class="x-grid3-header-offset">{header}</div></div><div class="x-clear"></div></div>',
                        '<div class="x-grid3-scroller"><div class="x-grid3-body">{body}</div><a href="#" class="x-grid3-focus" tabIndex="-1"></a></div>',
                    "</div>",
                    '<div class="x-grid3-resize-marker">&#160;</div>',
                    '<div class="x-grid3-resize-proxy">&#160;</div>',
                    "</div>"
                );
        }
        
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
        ret.on("columnremove", this._handleColumnRemove, this);
        ret.on("columnadd", this._handleColumnAdd, this);

        if (this.component.get("editcellcontents") == true) {
            ret.on("afteredit", this._handleModelChanged, this);
        }
        options["cm"].on("hiddenchange", this._handleColumnHide, this);
        
        return ret;
    },
    
    _applyHeaderCheckedIfNeeded: function() {
        var header = Ext.fly(this.extComponent.getView().innerHd).child(".x-grid3-hd-checker");
        if (!header) {
            header = Ext.fly(this.extComponent.getView().innerHd).child(".x-grid3-hd-checker-on");
        }
        // header should only be null if a grid that has been removed 
        // was previously scheduled for layout and it ran a deferred render
        if (header == null) {
            return;
        }
        if (this.extComponent.getSelectionModel().getCount() == this.extComponent.getStore().getCount()) {
            header.addClass("x-grid3-hd-checker-on");
        } else {
            header.removeClass("x-grid3-hd-checker-on");
        }
        header.addClass("x-grid3-hd-checker");
    },
    
    /**
     * Overridden handling of children; we only care about toolbars
     */
    _createChildComponentArrayFromComponent: function() {
        var componentCount = this.component.getComponentCount();
        var children = new Array();
        var childIndex = 0;
        for (var i = 0; i < componentCount; i++) {
            var thisChild = this.component.getComponent(i);
            if (thisChild instanceof EchoExt20.Toolbar || thisChild instanceof EchoExt20.Menu) {
                children[childIndex++] = this.component.getComponent(i);
            }
        }
        return children;
    },
    
    _createChildItems: function(update, children) {
        var indices = this._getContextMenuIndices();
        
        if (this.component.get("hasCellContextMenu") == true) {
            var child = this.component.getComponent(indices[0]);
            if (this.cellContextMenu == null || this.cellContextMenu != child.peer.extComponent) {
                Echo.Render.renderComponentAdd(update, child, null);
                this.cellContextMenu = child.peer.extComponent;
                if (this.cellContextMenu == null) {
                    throw new Error("Cell Context Menu not created for Grid Panel");
                }
            }
        }
        
        if (this.component.get("hasRowContextMenu") == true) {
            var child = this.component.getComponent(indices[1]);
            if (this.rowContextMenu == null || this.rowContextMenu != child.peer.extComponent) {
                Echo.Render.renderComponentAdd(update, child, null);
                this.rowContextMenu = child.peer.extComponent;
                if (this.rowContextMenu == null) {
                    throw new Error("Row Context Menu not created for Grid Panel");
                }
            }
        }
        
        if (this.component.get("hasHeaderContextMenu") == true) {
            var child = this.component.getComponent(indices[2]);
            if (this.headerContextMenu == null || this.headerContextMenu != child.peer.extComponent) {
                Echo.Render.renderComponentAdd(update, child, null);
                this.headerContextMenu = child.peer.extComponent;
                if (this.headerContextMenu == null) {
                    throw new Error("Header Context Menu not created for Grid Panel");
                }
            }
        }
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

    doGroup: function(fieldName) {
        if (this.extComponent == null)
            return;
        
        if (this.getGroupByField() == fieldName)
            return;
        
        var colModel = this.extComponent.getColumnModel();
        var columns = colModel.getColumnsBy(function(columnConfig, index) { return true; });
        for (var i = 0; i < columns.length; i++) {
            if (columns[i].dataIndex == fieldName)
                columns[i].grouping = true;
            else
                columns[i].grouping = false;
        }
        this.component.set("columnModel", this.extComponent.getColumnModel());
    },
    
    getGroupByField: function() {
        if (this.extComponent == null)
            return null;
        var colModel = this.extComponent.getColumnModel();
        var columns = colModel.getColumnsBy(function(columnConfig, index) { return true; });
        for (var i = 0; i < columns.length; i++) {
            if (columns[i].grouping == true)
                return columns[i].dataIndex;
        }
        return null;
    },
    
    _doSelect: function() {
        if (this.component) {
            if (this.client && this.client._transactionInProgress) {
                this._selectionTask.delay(250, this._doSelect, this);
                return;
            } else {
                if (this.component._listenerList != null && this.component._listenerList.hasListeners("select")) {
                    this._loadMask.enable();
                    this._loadMask.show();
                    this.component.doSelect();
                }
            }
        }
    },

    _handleColumnMove: function(oldIndex, newIndex) {
        this.component.set("columnModel", this.extComponent.getColumnModel());
        this.component.doSort();
    },

    _handleColumnResize: function(colIndex, newSize) {
        this.component.set("columnModel", this.extComponent.getColumnModel());
    },
    
    _handleColumnHide: function(columnIndex, hidden) {
        this.component.set("columnModel", this.extComponent.getColumnModel());
    },
    
    _handleColumnRemove : function(columnIndex) {
    	// columnIndex is the original index of the column in the model;
    	// we need to convert it to the current index.
    	var cm = this.extComponent.colModel;
    	var dataIndex = -1;
    	for (var i = 0; i < cm.columns.length; i++) {
    		if (cm.columns[i].id == columnIndex) {
    			dataIndex = i;
    		}
    	}
    	this.component.doColumnRemove(dataIndex - 1);
    },
    
    _handleColumnAdd : function() {
    	this.component.doColumnAdd();
    },

    _handleKeyDownEvent: function(evt) {
        if (evt.keyCode == 17) {
            this._ctrlKeyDown = true;
        }
    },

    _handleKeyUpEvent: function(evt) {
        this._ctrlKeyDown = false;
    },

    _handleCellContextMenu: function(grid, rowIndex, cellIndex, evt) {
        evt.stopEvent();
        this.cellContextMenu.showAt(evt.getXY());
    },
    
    _handleRowContextMenu: function(grid, rowIndex, evt) {
        evt.stopEvent();
        this.extComponent.getSelectionModel().selectRow(rowIndex);
        this.rowContextMenu.showAt(evt.getXY());
    },
    
    _handleHeaderContextMenu: function(grid, colIndex, evt) {
        evt.stopEvent();
        this.headerContextMenu.showAt(evt.getXY());
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
            this._selectionTask.delay(350, this._doSelect, this);
        }
    },
    
    _handleRowSelectEvent: function(selectionModel, rowIndex, record) {
        if (this._handleSelectionEvents) {
            var pageOffset = this.component.get("pageOffset");
            var index = pageOffset + rowIndex;
            var needsCtrlForMultiple = !(this.extComponent.getSelectionModel() instanceof Ext.grid.CheckboxSelectionModel);
            if (selectionModel.singleSelect == true || (needsCtrlForMultiple && !this._ctrlKeyDown)) {
                this._selectedRows = {};
            }
            this._selectedRows[index] = true;
            this._updateRowSelection();
            this._selectionTask.delay(350, this._doSelect, this);
        }
    },

    /**
     * Handles the selection sent by the server for the component.
     */
    _handleServerSelections: function() {
    	/*
    	 * check that dom element this.extComponent.getView().mainBody 
         * has at least one child node, to avoid attempting to set the
         * server selections before we're rendered
         */
    	if (!(this.extComponent.getView().mainBody) || !this.extComponent.getView().mainBody.dom.hasChildNodes()) {
    		this._handleServerSelections.defer(50, this);
    		return;
    	}
        this._handleSelectionEvents = false;
        // clear select first
        this.extComponent.getSelectionModel().clearSelections();
        this._extractSelectedRowsFromComponent();

        var first = this.component.get("pageOffset");
        var last = first + this.extComponent.getStore().getCount();
        this._applySelectionToModel(this.extComponent.getSelectionModel(), first, last);
        this._handleSelectionEvents = true;
    },
    
    /**
     * Retrieves the selection sent by the server, and sets the (sparse) array
     * this._selectedRows to have value true for each index that is selected
     */
    _extractSelectedRowsFromComponent: function() {
        this._selectedRows = {};
        var selectionString = this.component.get("selection");
        if (selectionString) {
            var rows = selectionString.split(",");
            for (var i = 0; i < rows.length; i++) {
                var row = parseInt(rows[i]);
                this._selectedRows[row] = true;
            }
        }
    },
    
    /**
     * Applies the selections where first <= selection < last in this._selectedRows 
     * to the specified selection model, replacing any existing selections.
     */
    _applySelectionToModel: function(selModel, first, last) {
        var selectedRowIndices = [];
        for (var row in this._selectedRows) {
            if (row >= first && row < last) {
                selectedRowIndices[selectedRowIndices.length] = row;
            }
        }
        selModel.selectRows(selectedRowIndices, false);
    },
    
    _handleModelChanged: function(editEvent) {
        var store = this.extComponent.getStore();
        var data = [];
        for (var i = 0; i < store.getCount(); i++) {
            data[i] = [];
            var thisRecord = store.getAt(i);
            var recordKeys = thisRecord.fields.keys;
            for (var j = 0; j < recordKeys.length; j++) {
                data[i][j] = thisRecord.get(recordKeys[j]);
            }
        }
        var oldModel = this._model;
        this._model = {
            data : data,
            renderedData : oldModel.renderedData,
            fields : oldModel.fields,
            size: oldModel.size,
            className: "E2SS"
        };
        this.component.set("model", this._model);
    },
    
    /**
     * Creates an ext store from the component's model representation.
     */
    _makeStore: function() {
        var model = this.component.get("model");
        this._model = model;

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
        

        var colModel = this.component.get("columnModel");
        var sortField = this.component.get("sortField");
        var sortDirection = this.component.get("sortDirection");
        if (sortField) {
            // nothing special to do
        } else {
            var firstCol = colModel.getColumnsBy(function(columnConfig, index) { return true; })[0];
            sortField = firstCol.header;
            sortDirection = firstCol.sortDirection;
        }
        if (sortDirection == null) {
            sortDirection = "ASC";
        }
        var sortInfo = {
            field: sortField,
            direction: sortDirection
        };

        var groupField = null;
        if (this.component.get('allowGrouping')) {
            var columns = colModel.getColumnsBy(function(columnConfig, index) { return true; });
            for (var i = 0; i < columns.length; i++) {
                if (columns[i].grouping == true)
                    groupField = columns[i].dataIndex;
            }
        }
        if (groupField != null) {
            store = new Ext.data.GroupingStore({
                groupField: groupField,
                proxy: proxy,
                reader: reader,
                sortInfo: sortInfo,
                remoteSort: true,
                remoteGroup: true
            });
        }
        else {
            store = new Ext.data.GroupingStore({
                reader: reader,
                proxy: proxy,
                sortInfo: sortInfo,
                remoteSort: true,
                remoteGroup: true
            });
        }

        var parms = {
            groupBy: groupField,
            sort: sortField,
            dir: sortDirection
        };
        var options = {params:parms};
        store.load(options);
        
        return store;
    },
    
    newExtComponentInstance: function(options) {
        if (this.component.get("editcellcontents") == true) {
            options["clicksToEdit"] = 1;
            return new Ext.grid.EditorGridPanel(options);
        } else {
            var ret = new Ext.grid.GridPanel(options);
            return ret;
        }
    },

    renderDispose: function(update) {
        EchoExt20.PanelSync.prototype.renderDispose.call(this, update);
        Ext.Element.get("approot").un("keydown",
                this._handleKeyDownEvent);
        Ext.Element.get("approot").un("keyup",
                this._handleKeyUpEvent);
    },
    
    _configureColumnModel: function(colModel, selectionModel, options, checkboxChanged) {
        // apply the column renderers!
        for (var i = 0; i < colModel.config.length; i++) {
            var thisCol = colModel.config[i];
            
            this._configureColumn(thisCol, options);
        }
        
        if (this.component.get("showCheckbox")){
            if (((colModel.config[0] instanceof Ext.grid.AbstractSelectionModel)) && checkboxChanged == false && this.extComponent != null)
                colModel.config.shift();
            colModel.config.unshift(selectionModel);
        } else if (checkboxChanged) {
            colModel.config.shift();
        }
    },
    
    renderUpdate: function(update) {
        EchoExt20.PanelSync.prototype.renderUpdate.call(this, update);

        // suspend event handling while we are manipulating
        this._handleSortEvents = false;
        this._handleSelectionEvents = false;
        
        // clear select first
        this.extComponent.getSelectionModel().grid = this.extComponent;
        this.extComponent.getSelectionModel().clearSelections();
        
        // reconfigure the column model if it has been updated
        var updatedColumnModel = update.getUpdatedProperty("columnModel");
        if (updatedColumnModel != null) {
        	this._configureColumnModel(this.component.get("columnModel"), this._sm, null, update.getUpdatedProperty("showCheckbox") != null);
        }
        
        var updatedStore = update.getUpdatedProperty("model");
        if (updatedStore != null) {
            /*
             * The grid can only reconfigure itself if it is rendered.
             */
            if (this.extComponent.rendered) {
                this.extComponent.getColumnModel().removeListener("hiddenchange", this._handleColumnHide, this);
                var colModel = this.extComponent.getColumnModel();

                if (updatedColumnModel != null) {
                    colModel = this.component.get("columnModel");
                }
                // apply the column renderers!
                for (var i = 0; i < colModel.config.length; i++) {
                    var thisCol = colModel.config[i];
                    // the col model may have changed - if checkboxes are needed
                    // then put the selection model back in as first column
                    if (!(thisCol instanceof Ext.grid.CheckColumn)
                            && !(thisCol instanceof Ext.grid.AbstractSelectionModel)) {
                        thisCol.renderer = this._renderColumn.createDelegate(this);
                    }
                }
                this.extComponent.reconfigure(
                  this._makeStore(),
                  colModel
                );
                this.extComponent.getColumnModel().addListener("hiddenchange", this._handleColumnHide, this);
                this.extComponent.getView().scrollToTop();
                this.updateCompleted(update);
            }
            else {
                /*
                 * Make a note to reconfigure when the grid is rendered.
                 */
                this._reconfigureOnRender = true;
            }
        }

        this._handleServerSelections();
        if (this.component.get("showCheckbox")) {
            this._applyHeaderCheckedIfNeeded();
        }
        if (this._loadMask != null){
            this._loadMask.hide();
        }

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

        var groupField = params.groupBy ? params.groupBy : null;
        
        if (groupField != this.syncPeer.getGroupByField()) {
            this.syncPeer.doGroup(groupField);
            
            if (this.syncPeer._handleSortEvents) {
                var sortField = groupField;
                var direction = "ASC";
                if ((sortField == null || sortField == "") && params.sort) {
                    sortField = params.sort;
                    direction = params.dir;
                }
                this.syncPeer.doSort(sortField, direction);
            }
        } else if (params.sort) {
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

// plugin for the grid that adds menu items 'add column' and 'remove column'
// to the header context menu
EchoExt20.GridColAddRemove = function(config) {
    Ext.apply(this, config);
};

Ext.extend(EchoExt20.GridColAddRemove, Ext.util.Observable, {
    init : function(gridPanel) {
        var view = gridPanel.getView();
        Ext.apply(view, {
            renderUI: view.renderUI.createSequence(function() {
                this.hmenu.add('-');
                this.hmenu.add({id:'....removecol', text:'Remove Column'});
                this.hmenu.add({id:'....addcol', text:'Add Column'});
            }),

            handleHdMenuClick : view.handleHdMenuClick.createInterceptor(function(item) {
                var index = this.hdCtxIndex;
                var cm = this.cm;
                switch(item.id) {
                    case "....removecol":
                        this.grid.fireEvent('columnremove', cm.getColumnId(index));
                        break;
                    case "....addcol":
                        this.grid.fireEvent('columnadd');
                        break;
                }
            })
        });
        
        gridPanel.addEvents(
            "columnremove",
            "columnadd"
        );
    }
});


// check column code copied from http://extjs.com/learn/Ext_FAQ_Grid#How_to_make_a_check_box_column_in_grid
// on 13th Feb 2009
Ext.grid.CheckColumn = function(config){
    Ext.apply(this, config);
    if(!this.id){
        this.id = Ext.id();
    }
    this.renderer = this.renderer.createDelegate(this);
};

Ext.grid.CheckColumn.prototype ={
    init : function(grid){
        this.grid = grid;
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
        }, this);
    },
    onMouseDown : function(e, t){
        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
            e.stopEvent();
            var index = this.grid.getView().findRowIndex(t);
            var record = this.grid.store.getAt(index);
            var value = false;
            if (record.data[this.dataIndex] === "true" || record.data[this.dataIndex] === true)
                value = true;
            
            record.set(this.dataIndex, !value);

            var editEvent = {
                grid: this.grid,
                record: record,
                field: this.dataIndex,
                originalValue:value,
                value: !value,
                row: index,
                column: this,
                cancel:false
            };
            this.grid.fireEvent("afteredit", editEvent);
        }
    },
    renderer : function(v, p, record){
        var isChecked = v === "true" || v === true;
        p.css += ' x-grid3-check-col-td'; 
        return '<div class="x-grid3-check-col' +
                (isChecked?'-on':'') +
               ' x-grid3-cc-' + 
               this.id + 
               '"> </div>';
    }
};
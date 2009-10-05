EchoExt20.TreeSync = Core.extend(EchoExt20.ExtComponentSync, {

    $static: {
        _BORDER_SIDE_STYLE_NAMES: ["borderTop", "borderRight", "borderBottom", "borderLeft"],

        _SIDE_TO_PADDING_MAP: { "top": "paddingTop", "right" : "paddingRight", "bottom" : "paddingBottom", "left" : "paddingLeft" },
        
        LINE_STYLE_NONE: 0,
        LINE_STYLE_SOLID: 1,
        LINE_STYLE_DOTTED: 2,
    
        _supportedPartialProperties: ["treeStructure", "selection"],
        
        TREE_IMAGES: {
            0: {
                open: "image/tree/Open.gif",
                openBottom: "image/tree/Open.gif",
                closed: "image/tree/Closed.gif",
                closedBottom: "image/tree/Closed.gif"
            },
            1: {
                vertical: "image/tree/VerticalSolid.gif",
                open: "image/tree/OpenSolid.gif",
                openBottom: "image/tree/OpenBottomSolid.gif",
                closed: "image/tree/ClosedSolid.gif",
                closedBottom: "image/tree/ClosedBottomSolid.gif",
                join: "image/tree/JoinSolid.gif",
                joinBottom: "image/tree/JoinBottomSolid.gif"
            },
            2: {
                vertical: "image/tree/VerticalDotted.gif",
                open: "image/tree/OpenDotted.gif",
                openBottom: "image/tree/OpenBottomDotted.gif",
                closed: "image/tree/ClosedDotted.gif",
                closedBottom: "image/tree/ClosedBottomDotted.gif",
                join: "image/tree/JoinDotted.gif",
                joinBottom: "image/tree/JoinBottomDotted.gif"
            } 
        }
    },
    
    $load: function() {
        Echo.Render.registerPeer("Ext20Tree", this);
    },
    
    /**
     * The Tree's context menu
     */
    contextMenu: null,

    $virtual: {
        newExtComponentInstance: function(options) {
            return new Ext.tree.ColumnTree(options);
        }
    },
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
    
    	// default options
    	options['autoScroll'] = true;
    	options['animate'] = false;
    	options['containerScroll'] = true;
    	
    	if (this.component.get("hasBorder") != null)
    		options['border'] = this.component.get("hasBorder");
    	
    	if (this.component.get("lines") != null)
    		options['lines'] = this.component.get("lines") == 1;
    	
        options['headerVisible'] = this.component.render("headerVisible", true);
        this._headerVisible = options['headerVisible'];
        options['rootVisible'] = this.component.render("rootVisible", true);
        
        if (this.component.get("showCheckBoxes") != null) {
            options['showCheckBoxes'] = this.component.get("showCheckBoxes");
        }
    	

        this._selectionEnabled = this.component.render("selectionEnabled", true);
        if (this._selectionEnabled) {
            this.selectionModel = new Extras.TreeSelectionModel(parseInt(this.component.get("selectionMode"), 10));
        }
        
        if (!this._treeStructure) {
            this._treeStructure = this.component.get("treeStructure")[0];
        }
        this.columnCount = this.component.get("columnCount");
        
        options['columns'] = this._renderColumns(update, this._treeStructure.getHeaderNode());
        var rootNode = this._treeStructure.getRootNode();
    	
    	this.extComponent = this.newExtComponentInstance(options);
    	this.extComponent.columns = options['columns'];
        this.extComponent.on("render", this._doOnExtRender, this);
    	
        var ext20RootNode = this._renderNode(update, rootNode);
        this.extComponent.setRootNode(ext20RootNode);
    
        var selection = this.component.render("selection");
        if (selection && this._selectionEnabled) {
            this._setSelectedFromProperty(selection);
        }
        
        var children = this._createChildComponentArrayFromComponent();
        if (children.length > 0) {
            this._createChildItems(update, children);
        }
    	
    	return this.extComponent;
    },
    
    _doOnExtRender: function() {
        
        this.extComponent.un('contextmenu', this._doOnContextMenu, this);
        if (this.contextMenu != null) {
            this.extComponent.on('contextmenu', this._doOnContextMenu, this);
        }
    },
    
    _doOnContextMenu: function(node, evt) {
        evt.stopEvent();
        this.contextMenu.showAt(evt.getXY());
    },
    
    _renderColumns: function(update, headerNode) {
    	var columns = [];
    	for (var c = 0; c < this.columnCount; ++c) {
    		// determine the width from the column width
    		// FIXME - cache this to improve rendering speed for table
    		var width = this.component.renderIndex("columnWidth", c); 
    		var widthPx = 100;
            if (width != null) {
                if (Echo.Sync.Extent.isPercent(width)) {
                	alert("Cannot render percent width for tree column");
                } else {
                    var columnPixels = Echo.Sync.Extent.toPixels(width, true);
                    widthPx = columnPixels;
                }
            }
    		if (c == 0) {
    			columns[c] = {
    				width: widthPx,
	            	update: update,
	            	columnComponent:  this.component.application.getComponentByRenderId(headerNode.getId()),
	            	dataIndex: c + 'DataIndex'
	            };
    		} else {
	            
	            columns[c] = {
	            	width: widthPx,
	            	update: update,
	            	columnComponent:  this.component.application.getComponentByRenderId(headerNode.getColumn(c - 1)),
	            	dataIndex: c + 'DataIndex'
	            };
    		}
        }
    	return columns;
    },
    
    _renderNode: function(update, node) {
        var nodeDepth = this._treeStructure.getNodeDepth(node);
        return this._renderNodeRecursive(update, node, nodeDepth);
    },
    
    _renderNodeRecursive: function(update, node, depth, visible) {
        if (visible == null) {
            visible = true;
        }
        if (!this._rootVisible && node == this._treeStructure.getRootNode()) {
            visible = false;
        }
        
        if (this.extComponent == null || this.extComponent.getNodeById(node.getId()) == null) {
        	// rendering a new node
	        var cols = [];
	        cols[0] = node.getId();
	        for (var c = 1; c < this.columnCount; ++c) {
	        	var colComp = node.getColumn(c - 1);
	        	cols[c] = colComp;
	        }
	        var children = [];
	        // render child nodes
	        var childCount = node.getChildNodeCount();
	        for (var i = 0; i < childCount; ++i) {
	            var childNode = node.getChildNode(i);
	            children[i] = this._renderNodeRecursive(update, childNode, depth + 1);
	        }
	        var expandFuncRef = this._expansionHandler.createDelegate(this);
	        var thisExtNode = {
	        		update: update,
	        		application: this.component.application,
	        		id: node.getId(),
	        		expanded: node.isExpanded(),
	        		columns: cols,
	        		isLeaf : node.isLeaf(),
	        		echoNode : node
	        };
	        
	        if (node.getChecked() != null) {
	           thisExtNode.checked = node.getChecked();
	        }
	        
	        var extNode = new Ext.tree.ColumnNode(thisExtNode);
	        for (var i = 0; i < children.length; i++) {
	        	extNode.appendChild(children[i]);
	        }
	        extNode.on('beforeexpand', this._expansionHandler, this);
	        extNode.on('beforecollapse', this._expansionHandler, this);
	        extNode.on('click', this._selectionHandler, this);
	        
	        return extNode;
        } else if (this.extComponent != null && this.extComponent.getNodeById(node.getId()) != null) {
        	// updating an existing node
        	var renderedExtNode = this.extComponent.getNodeById(node.getId());
	        for (var c = 0; c < this.columnCount - 1; ++c) {
	        	renderedExtNode.attributes.columns[c] = node.getColumn(c);
	        }
	        
	        // the list of nodes to add, remove and update
	        var addChildren = [];
	        var removeChildren = [];
	        var updateChildren = [];
	        var childCount = node.getChildNodeCount();
	        for (var i = 0; i < childCount; ++i) {
	            var childNode = node.getChildNode(i);
	            var found = false;
	            for (var j = 0; j < renderedExtNode.childNodes.length; j++) {
	            	var renderedChild = renderedExtNode.childNodes[j];
	            	if (renderedChild.id == childNode.getId()) {
	            		updateChildren[updateChildren.length] = childNode;
	            		found = true;
	            	}
	            }
	            if (!found) {
	            	addChildren[addChildren.length] = childNode;
	            }
	        }
	        for (var i = 0; i < renderedExtNode.childNodes.length; i++) {
	        	var renderedChild = renderedExtNode.childNodes[i];
	        	var found = false;
	        	for (var j = 0; j < childCount && !found; j++) {
	        		var childNode = node.getChildNode(j);
	            	if (renderedChild.id == childNode.getId())
	            		found = true;
	        	}
	        	if (!found)
	        		removeChildren[removeChildren.length] = renderedChild;
	        }
	        
	        for (var i = 0; i < removeChildren.length; i++) {
	        	renderedExtNode.removeChild(removeChildren[i]);
	        }
	        
	        for (var i = 0; i < addChildren.length; i++) {
	        	var newChild = addChildren[i];
	        	renderedChild = this._renderNodeRecursive(update, newChild, depth + 1);
	        	renderedExtNode.appendChild(renderedChild);
	        }
	        
	        for (var i = 0; i < updateChildren.length; i++) {
	        	var updateChild = updateChildren[i];
	        	this._renderNodeRecursive(update, updateChild, depth + 1);
	        }
	        return renderedExtNode;
        }
	    return null;
    },
    
    /**
     * Sets the selection state based on the given selection property value.
     *
     * @param {String} value the value of the selection property
     * @param {Boolean} clearPrevious if the previous selection state should be overwritten
     */
    _setSelectedFromProperty: function(value, clearPrevious, force) {
        var selectedIds = value.split(",");
        if (this.selectionModel.equalsSelectionIdArray(selectedIds) && !force) {
            return;
        }
        if (clearPrevious) {
            this._clearSelected();
        }
        for (var i = 0; i < selectedIds.length; i++) {
            if (selectedIds[i] === "") {
                continue;
            }
            var node = this._treeStructure.getNode(selectedIds[i]);
            this._setSelectionState(node, true);
        }
    },
    
    /**
     * Sets the selection state for the given node.
     * 
     * @param {Extras.RemoteTree.TreeNode} node the node to set the selection state for
     * @param {Boolean} selectionState the new selection state of node
     */
    _setSelectionState: function(node, selectionState) {
        this.selectionModel.setSelectionState(node, selectionState);
        var nodeId = node.getId();
        var extNode = this.extComponent.getNodeById(nodeId);
        // if we're doing a full update then this won't work
        if (typeof extNode == 'undefined')
        	return;
        if (selectionState)
        	extNode.select();
        else
        	extNode.unselect();
        this.extComponent.doLayout();
    },
    
    /**
     * Deselects all selected rows.
     */
    _clearSelected: function() {
        var selected = this.selectionModel.getSelectedNodes();
        while (selected.length > 0) {
            this._setSelectionState(selected[0], false);
        }
    },
    
    _expansionHandler: function(extNode) {
        if (!this.client || !this.client.verifyInput(this.component)) {
            return;
        }
    	
    	var echoNode = extNode.getEchoNode();

        if (echoNode.isLeaf()) {
            return false;
        }
        
        // if we are rendering a node initially as expanded, we
        // receive notification of it - we don't want to collapse it
        if (extNode.isExpanded() != echoNode.isExpanded())
        	return true;
        
        if (echoNode.isExpanded()) {
            echoNode.setExpanded(false);
            // no other peers will be called, so update may be null
            this._renderNode(extNode.attributes.update, echoNode);
        } else if (echoNode.getChildNodeCount() > 0) {
            echoNode.setExpanded(true);
            // no other peers will be called, so update may be null
            this._renderNode(extNode.attributes.update, echoNode);
        }
        
        var rowIndex = this._getRowIndexForNode(echoNode);
        this.component.set("expansion", rowIndex);
        this.component.doAction();
        return true;
    },
    
    /**
     * Gets the visible row index of node. If node is not visible, -1 is returned.
     * 
     * @param {Extras.RemoteTree.TreeNode} node the node to get the row index for
     * 
     * @return the row index
     * @type Integer 
     */
    _getRowIndexForNode: function (node) {
    	var parentNodeId = node.getParentId();
    	if (parentNodeId == null)
    		return 0;
    	var parentNode = this._treeStructure.getNode(parentNodeId);
    	
    	var parentNodeIndex = this._getRowIndexForNode(parentNode);
    	if (parentNode.indexOf(node) == 0) {
    		return parentNodeIndex + 1;
    	} else {
    		var siblingRows = 0;
    		for (var i = 0; i < parentNode.indexOf(node); i++) {
    			siblingRows++;
    			siblingRows = siblingRows + this._expandedNodesBelow(parentNode.getChildNode(i));
    		}
    		return parentNodeIndex + siblingRows + 1;
    	}
    },
    
    _expandedNodesBelow: function(parent) {
    	if (parent.isLeaf() || !parent.isExpanded())
    		return 0;
    	
    	var nodesBelow = 0;
    	var childCount = parent.getChildNodeCount();
    	for (var i = 0; i < childCount; i++) {
    		// this child is one row
    		nodesBelow = nodesBelow + 1;
    		// add the nodes below the child
    		nodesBelow = nodesBelow + this._expandedNodesBelow(parent.getChildNode(i));
    	}
    	return nodesBelow;
    },
    
    _selectionHandler: function(extNode, event) {
        if (!this.client || !this.client.verifyInput(this.component)) {
            return;
        }
        var echoNode = extNode.getEchoNode();
        this._doSelection(echoNode, event);
        this.component.doAction();
        return true;
    },
    
    _doSelection: function(node, e) {
        var rowIndex = this._getRowIndexForNode(node);
                
        var update = new Extras.RemoteTree.SelectionUpdate();
        
        var specialKey = e.shiftKey || e.ctrlKey || e.metaKey || e.altKey;    
        if (!this.selectionModel.isSelectionEmpty() && (this.selectionModel.isSingleSelection() || !(specialKey))) {
            update.clear = true;
            this._clearSelected();
        }
    
        if (!this.selectionModel.isSingleSelection() && e.shiftKey && this.lastSelectedNode) {
            if (this.lastSelectedNode.equals(node)) {
                return;
            }
            var startNode;
            var endNode;
            var lastSelectedIndex = this._getRowIndexForNode(this.lastSelectedNode);
            if (lastSelectedIndex < rowIndex) {
                startNode = this.lastSelectedNode;
                endNode = node;
            } else {
                startNode = node;
                endNode = this.lastSelectedNode;
            }
            
            var iterator = this._treeStructure.iterator(startNode, false, endNode);
            var i = lastSelectedIndex < rowIndex ? lastSelectedIndex : rowIndex;
            trElement = this._getRowElementForNode(startNode);
            while (iterator.hasNext()) {
                node = iterator.nextNode();
                this._setSelectionState(node, true);
                update.addSelection(i++);
            }
        } else {
            this.lastSelectedNode = node;
            var selected = !this.selectionModel.isNodeSelected(node);
            if (selected || !update.clear) {
                this._setSelectionState(node, selected);
            }
            if (selected) {
                update.addSelection(rowIndex);
            } else if (!update.clear) {
                update.removeSelection(rowIndex);
            }
        }
        
        this.component.set("selectionUpdate", update);
        return true;
    },
    
    renderUpdate: function(update) {
        var propertyNames = update.getUpdatedPropertyNames();
        // remove properties that are only changed on the client
        Core.Arrays.remove(propertyNames, "expansion");
        Core.Arrays.remove(propertyNames, "selectionUpdate");
        if (propertyNames.length === 0 && !update.getRemovedChildren()) {
            return false;
        }
        // end of the hack
        
        var treeStructureUpdate = update.getUpdatedProperty("treeStructure");
        var fullStructure = (treeStructureUpdate && treeStructureUpdate.newValue && 
                treeStructureUpdate.newValue.fullRefresh);
        if (!fullStructure) {
            // removal of children indicates that the tree was invalidated, 
            // and thus all components are re-rendered, and the tree structure we have at the client 
            // is no longer valid.
            if (treeStructureUpdate && treeStructureUpdate.newValue) {
                // tree structure updates are always partial, even when there are other updates we can't handle
                this._renderTreeStructureUpdate(treeStructureUpdate.newValue, update);
            }
            
            if (Core.Arrays.containsAll(Extras.Sync.RemoteTree._supportedPartialProperties, 
                    propertyNames, true)) {
                var selection = update.getUpdatedProperty("selection");
                if (selection && this._selectionEnabled) {
                    this._setSelectedFromProperty(selection.newValue, true);
                }
                
                // partial update
                return false;
            }
        }
        
        if (fullStructure) {
        	this._treeStructure = treeStructureUpdate.newValue[0];
        	var ownerCt = this.extComponent.ownerCt;
        	ownerCt.remove(this.extComponent);
        	ownerCt.doLayout();
        	this.renderAdd(update, ownerCt.getEl());
        	ownerCt.add(this.extComponent);
        	ownerCt.doLayout();
        }
        
        // check the added and removed children, in case the context menu has been changed
        if (update.hasRemovedChildren()) {
            var removedChildren = update.getRemovedChildren();
            for (var i = 0; i < removedChildren.length; i++) {
                var child = removedChildren[i];
                
                // if the removed child is our context menu, hide it and then unset it
                if (child instanceof EchoExt20.Menu) {
                    if (this.contextMenu.isVisible()) {
                        this.contextMenu.hide();
                        this.contextMenu.destroy();
                    }
                    this.contextMenu = null;
                }
            }
        }
       
        if (update.hasAddedChildren()) {
            var addedChildren = update.getAddedChildren();
            this._createChildItems(update, addedChildren);
        }
        
        return true;
    },
    
    _renderSelectionUpdate : function() {
    	this.extComponent.registerAllNodes();
    	var selection = this.component.render("selection");
        if (selection && this._selectionEnabled) {
            this._setSelectedFromProperty(selection, false, true);
        }
    },
    
    _renderTreeStructureUpdate: function(treeStructureUpdate, update) {
        var structs = treeStructureUpdate;
        for (var i = 0; i < structs.length; ++i) {
            var struct = structs[i]; 
            var updateRootNode = struct.getRootNode();
            var node = this._treeStructure.getNode(updateRootNode.getId());
            if (node) {
                this._treeStructure.addOrUpdateChildNodes(updateRootNode);
                node.setExpanded(updateRootNode.isExpanded());
            } else {
                node = this._treeStructure.getNode(updateRootNode.getParentId());
                node.setExpanded(true);
                this._treeStructure.addOrUpdateNode(updateRootNode);
            }
            this._renderNode(update, node);
        }
    },
    
    /**
     * Creates an array of the children of the current component.
     */
    _createChildComponentArrayFromComponent: function() {
        var componentCount = this.component.getComponentCount();
        var children = new Array(componentCount);
        for (var i = 0; i < componentCount; i++) {
            children[i] = this.component.getComponent(i);
        }
        return children;
    },
    
    _createChildItems: function(update, children) {
        
        for (var i = 0; i < children.length; i++) {
            var child = children[i];
            
            if (child instanceof EchoExt20.Menu && (!child.peer || child.peer.extComponent == null)) {
                Echo.Render.renderComponentAdd(update, child, null);
                this.contextMenu = child.peer.extComponent;
                if (this.contextMenu == null) {
                    throw new Error("Context Menu not created for Tree");
                }
            }
        }
    }
});
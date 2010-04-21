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
 * Component implementation for Ext.Container, a lightweight
 * container without all the extra divs that panel has for
 * headers, footers etc.
 */
EchoExt20.Container = Core.extend(EchoExt20.ExtComponent, {
    
    /**
     * Registers this component on initial load.
     */
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20Container", this);
        Echo.ComponentFactory.registerType("E2C", this);
    },
    
    componentType: "Ext20Container"
    
});

/**
* Sync peer for panels.
*/
EchoExt20.ContainerSync = Core.extend(EchoExt20.ExtComponentSync, {
    
    /**
     * Registers the sync peer on initial load.
     */
    $load: function() {
        Echo.Render.registerPeer("Ext20Container", this);
    },
	
    /**
     * Reference to a method to invoke when the server
     * update is complete, to make the component visible again if it 
     * had to be hidden to prevent progressive rendering flicker
     * as children were added.
     */
    _makeVisibleRef: null,
    
    $virtual: {
		
        /**
         * whether the component should be hidden when adding children, to avoid
         * noticeable progressive updates on slower rendering browsers such as FF.
         */
        hideWhenAddingChildren: true,
        /**
         * Actually creates the ext component instance.  Overriden by
         * classes such as EchoExt20.GridPanelSync to create their
         * relevant Ext subclass.
         */
        newExtComponentInstance: function(options) {
            return new Ext.Container(options);
        },
        /**
         * Informs this panel that children had their layout update (by having
         * components added to or removed from them).  This will trigger
         * a call to doLayout once the rendering phase has finished.
         */
        notifyChildLayoutUpdate: function() {
            this._childLayoutUpdatesOccurred = true;
        },
    
        /**
         * Creates the passed children and adds them to the Ext.Panel.
         * Overridden by classes such as EchoExt20.GridPanelSync that handle
         * children differently.
         */
        _createChildItems: function(update, children) {
            
            for (var i = 0; i < children.length; i++) {
                var child = children[i];
                var childIndex = this.component.indexOf(child);
                this._createChildItem(update, child, childIndex);
            }
    
            // now make sure that the child indexes are set, so that, for instance, when a certain
            // tab is selected in a tabbed pane, it can work out what the index of the displayed component
            // is.
            for (var i = 0; i < this.component.getComponentCount(); i++) {
                var child = this.component.getComponent(i);
                child.childIndex = i;
            }
        },
        
        _createChildItem: function(update, child, childIndex) {
            /*
             *  if this is not an ext20 component, we need to wrap it
             *  so that it can operate within an ext container.
             */
            if (! (child instanceof EchoExt20.ExtComponent) ) {
                // we don't renderAdd here - ext does it lazily
                var childExtComponent = new EchoExt20.Echo3SyncWrapper(update, child);
                this._addExtComponentChild(child, childExtComponent, childIndex);
            }
            else if (child instanceof EchoExt20.Window) {
                this._createWindow(update, child);
            }
            else if (child instanceof EchoExt20.Menu) {
                Echo.Render.renderComponentAdd(update, child, null);
                this.contextMenu = child.peer.extComponent;
                if (this.contextMenu == null) {
                    throw new Error("Context Menu not created for Panel");
                }
            }
            else if ( 
                  (
                    !(child instanceof EchoExt20.Button)
                    || (child instanceof EchoExt20.Button && child.get("addToButtonBar") == false)
                  )
                  && !(child instanceof EchoExt20.Toolbar) // toolbars handled separately
                ) {
                Echo.Render.renderComponentAdd(update, child, null); 
                
                // add the ext component created by the peer to the child items array
                var childExtComponent = child.peer.extComponent;
                if (childExtComponent == null) {
                    throw new Error("No child ext component was created during renderAdd for component type: " + child.componentType);
                } 
                else {
                    // make sure we can get back to the echo component from the ext component
                    childExtComponent.echoComponent = child;
                    this._addExtComponentChild(child, childExtComponent, childIndex);
                }
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
        }
    },
    
    /**
     * Render update implementation.  Supports update of the panel title,
     * and adding and removing children.
     */
    renderUpdate: function(update){
        EchoExt20.ExtComponentSync.prototype.renderUpdate.call(this, update);
        if (update.getUpdatedProperty("width") != null) {
                this.extComponent.setWidth(this.component.get("width"));
        }
        
        if (update.getUpdatedProperty("height") != null) {
                this.extComponent.setHeight(this.component.get("height"));
        }
        
        var needsLayout = false;
        
        if (update.hasRemovedChildren()) {
            var removedChildren = update.getRemovedChildren();
            for (var i = 0; i < removedChildren.length; i++) {
                // all children have to be ext components anyway
                var child = removedChildren[i];
                /*
                 * Not if it is a window, because it was never
                 * added to the parent ext container in the first place.
                 */
                if (!(child instanceof EchoExt20.Window)) {
                    if (child.peer && child.peer.extComponent) {
                        var childExtComponent = child.peer.extComponent;
                        this.extComponent.remove(childExtComponent);
                        // FIXME - is this necessary?
                        childExtComponent.rendered = false;
                    }
                }
            }
            needsLayout = true;
        }
       
        if (update.hasAddedChildren()) {
        	/*
        	 * Firefox has some particularly appalling issues
        	 * with rendering additions using a table layout,
        	 * so make the panel invisible whils rendering,
        	 * and then make it visible again.
        	 * We still get an unpleasant flash, but it's better
        	 * than what happens otherwise :(
        	 */
        	var doHide = false;
            if (Core.Web.Env.BROWSER_FIREFOX && this.component.get("layout") instanceof EchoExt20.TableLayout) {
                /*
                 * If the only children added were windows, then there's no need to hide
                 * ourselves, since it does not involve changes to our own div.
                 */
                var addedChildren = update.getAddedChildren();
                for (var i = 0; i < addedChildren.length && !doHide; i++) {
                    var componentType = addedChildren[i].componentType;
                    if ( !(componentType == "Ext20Window") ) {
                        doHide = true;
                    }
                }
                if (doHide) {
                    // and add a server update complete listener to show ourselves again, if we haven't already
                    if (this._makeVisibleRef == null) {
                        this._makeVisibleRef = Core.method(this, this._makeVisible);
                        this.client.addServerUpdateCompleteListener(this._makeVisibleRef);
                    }
                }
            }
            
            var addedChildren = update.getAddedChildren();
            //Needs layout if any components have been added to the panel.
            needsLayout = true;
            this._createChildItems(update, addedChildren);
            needsLayout = needsLayout || this._conditionalDoLayout(addedChildren);
        }
        
        /*
         * If we determined that we need to hide the panel
         * while adding, then we must defer the layout of
         * the ext component until the ._makeVisible
         * method is called below.
         * Otherwise we will get the horrible firefox
         * progressive rendering visible to the user.
         */
        if (!doHide && needsLayout) {
            this.extComponent.doLayout();
        }
    },
    
    /**
     * Render dispose implementation.
     */
    renderDispose: function(update) {
        EchoExt20.ExtComponentSync.prototype.renderDispose.call(this, update);
        if (this._makeVisibleRef != null) {
            this.client.removeServerUpdateCompleteListener(this._makeVisibleRef);
        }
    },
    
    /**
     * Re-shows the component after being hidden during an update.
     */
    _makeVisible: function() {
    	this.extComponent.doLayout();
    	if (this.extComponent.rendered) {
    		this.extComponent.getEl().dom.style.visibility = 'visible';
    	}
    },
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
        // process basic properties
        
        options['autoEl'] = {tag: 'div'};
        
        var html = this.component.get("html");
        if (html != null) {
            options['autoEl'].html = html;
        }

        if (this.component.render("padding")) {
            options.style.padding = this.component.render("padding");
        }

    	if (this.component.render("background") != null) {
    	    options.style.backgroundColor =  this.component.render("background");
    	}
        
        var height = this.component.render("height");
        if (height != null) {
            options['height'] = height;
        }
        
        var width = this.component.render("width");
        if (width != null) {
            options['width'] = width;
        }

        var layout = this.component.get("layout");
        if (layout != null) {
            if (layout instanceof EchoExt20.AccordionLayout) {
                options['layout'] = 'accordion';
                options.layoutConfig = {};
                options.layoutConfig.hideCollapseTool = layout.hideCollapseTool;
                //options['layoutConfig'] = {titleCollapse: true, animate: true};
            }
            else if (layout instanceof EchoExt20.BorderLayout) {
                options['layout'] = 'border';
            }
            else if (layout instanceof EchoExt20.FormLayout) {
                options['layout'] = 'form';
                options['labelAlign'] = 'right'; // FIXME - configure the form layout properly
            }
            else if (layout instanceof EchoExt20.FitLayout) {
                options['layout'] = 'fit';
            }
            else if (layout instanceof EchoExt20.ColumnLayout) {
                options['layout'] = 'column';
            }
            else if (layout instanceof EchoExt20.FitColumnLayout) {
                options['layout'] = 'fitcolumn';
                options.layoutConfig = {};
                if (layout.fitHeight) {
                    options.layoutConfig.fitHeight = true;
                } else if (!layout.fitHeight) {
                    options.layoutConfig.fitHeight = false;
                }
                if (layout.split) {
                    options.layoutConfig.split = true;
                } else if (!layout.split) {
                    options.layoutConfig.split = false;
                }
                if (layout.margin) {
                    options.layoutConfig.margin = layout.margin;
                }
            }
            else if (layout instanceof EchoExt20.TableLayout) {
                options.layout = 'table';
                options.layoutConfig = {};
                if (layout.columns) {
                    options.layoutConfig.columns = layout.columns;
                }
                if (layout.border) {
                    options.layoutConfig.tableBorder = layout.border;
                }
                if (layout.cellSpacing) {
                    options.layoutConfig.cellSpacing = layout.cellSpacing;
                }
                //if (layout.cellPadding) {
                //    options.layoutConfig.cellPadding = layout.cellPadding;
                //}
                options.layoutConfig.tableStyle = {};
                if (layout.fullWidth) {
                    options.layoutConfig.tableStyle.width = "100%";
                }
                if (layout.fullHeight) {
                    options.layoutConfig.tableStyle.height = "100%";
                }
                if (layout.columnWidths) {
                    options.layoutConfig.tableStyle.columnWidths = layout.columnWidths;
                }
            }
            else {
                throw new Error("Unsupported layout");
            }
        }
        
        var children = this._createChildComponentArrayFromComponent();
        
        this.extComponent = this.newExtComponentInstance(options);
        
        if (children.length > 0) {
            this._createChildItems(update, children);
            if (this._conditionalDoLayout(children))
            	this.extComponent.doLayout();
        }
        
        return this.extComponent;
    },   
    
    /**
     * Calls doLayout on the ext panel under the following
     * conditions:
     * 1) we are not the top container
     * 2) any of our children
     * has a border layout (since they can size their north regions
     * wrongly).
     * <p>
     * FIXME - this is confused.  Check it for purpose and correctness.
     * <p/>
     */
    _conditionalDoLayout: function(children) {
        var doLayout = false;
        if (this.extComponent.getLayout() instanceof Ext.layout.ColumnLayout || this.extComponent.getLayout() instanceof Ext.ux.ColumnLayout)
            doLayout = true;
        for (var i = 0; i < children.length && !doLayout; i++) {
            var layout = children[i].get("layout");
            if ( layout != null && layout instanceof EchoExt20.BorderLayout ) {
                doLayout = true;
            }
        }
        return doLayout;
    },
    
    _addExtComponentChild: function( child, childExtComponent, childIndex) {
        var layoutData = child.render("layoutData");
        if (layoutData) {
            var locationName = layoutData["locationName"];
            if ( locationName) {
                // It's a HtmlLayoutData, don't add it now. It will be added at renderUpdate.
                return;
            }
        }
        if (this.extComponent.items && childIndex < this.extComponent.items.getCount()) {
            this.extComponent.insert(childIndex, childExtComponent);
        }
        else {
        	this.extComponent.add(childExtComponent);
        }
    },
    
    /**
     * Creates and shows a pop-up window.
     */
    _createWindow: function(update, child) {
        Echo.Render.renderComponentAdd(update, child, null);
        child.peer.extComponent.doLayout();
        child.peer.extComponent.show();
    }
    
});

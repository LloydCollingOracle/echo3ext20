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
 * Component implementation for Ext.Panel.
 */
EchoExt20.Panel = Core.extend(EchoExt20.ExtComponent, {
    
    /**
     * Registeres this component on initial load.
     */
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20Panel", this);
        Echo.ComponentFactory.registerType("E2P", this);
    },
    
    componentType: "Ext20Panel",

    /**
     * Fires a key-press event.
     */
    doKeyPress: function() {
        this.fireEvent({type: "keyPress", source: this});
    },

    /**
     * Fires an event when one of the tools is clicked.
     */
    doToolClick: function() {
            this.fireEvent({type: "toolClick", source: this});
    }
    
});

/**
* Sync peer for panels.
*/
EchoExt20.PanelSync = Core.extend(EchoExt20.ExtComponentSync, {
    
    /**
     * Registers the sync peer on initial load.
     */
    $load: function() {
        Echo.Render.registerPeer("Ext20Panel", this);
    },
	
    /**
     * Reference to a method to invoke when the server
     * update is complete, to make the component visible again if it 
     * had to be hidden to prevent progressive rendering flicker
     * as children were added.
     */
    _makeVisibleRef: null,
    
    /**
     * The x position to set the panel to (used in absolute positioning only)
     */
    positionX: null,

    /**
     * The y position to set the panel to (used in absolute positioning only)
     */
    positionY: null,
    
    /**
     * The relative (to the panel's container) x position to set the panel to (used in absolute positioning only)
     */
    relativePositionX: null,

    /**
     * The relative (to the panel's container) y position to set the panel to (used in absolute positioning only)
     */
    relativePositionY: null,

    /**
     * The corner of the parent container to calculate the panel co-ordinates from (used in absolute positioning only)
     */
    relativeAnchorPosition: null,
    
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
            return new Ext.Panel(options);
        },
        /**
         * Informs this panel that children had their layout update (by having
         * components added to or removed from them).  This will trigger
         * a call to doLayout once the rendering phase has finished.
         */
        notifyChildLayoutUpdate: function() {
            this._childLayoutUpdatesOccurred = true;
        }
    },
    
    /**
     * Render update implementation.  Supports update of the panel title,
     * and adding and removing children.
     */
    renderUpdate: function(update){

        //EchoExt20.ExtComponentSync.prototype.renderUpdate.call(this, update);

        // check for any property updates
        if (update.getUpdatedProperty("title") != null) {
                this.extComponent.setTitle(this.component.get("title"));
        }
        
        if (update.getUpdatedProperty("width") != null) {
                this.extComponent.setWidth(this.component.get("width"));
        }
        
        if (update.getUpdatedProperty("height") != null) {
                this.extComponent.setHeight(this.component.get("height"));
        }
        if (update.getUpdatedProperty("iconCssClass") != null) {
            this.extComponent.setIconClass(this.component.get("iconCssClass"));
        }
        
        var needsLayout = false;
        
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
                	var dom = this.extComponent.getEl().dom; 
//                    dom.style.visibility = 'hidden';

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
            var buttons = this._createButtons(update, addedChildren);
            if (buttons.length > 0) {
                var footer = this.extComponent.footer;
                if (!footer) {
                    footer = this.extComponent.el.createChild();
                    footer.addClass('x-panel-footer');
                    footer.addClass('x-panel-footer-noborder');
                    this.extComponent.footer = footer;
                    var tb = footer.createChild({cls:'x-panel-btns-ct', cn: {
                        cls:"x-panel-btns x-panel-btns-"+this.extComponent.buttonAlign,
                        html:'<table cellspacing="0"><tbody><tr></tr></tbody></table><div class="x-clear"></div>'
                    }}, null, true);
                    var tr = tb.getElementsByTagName('tr')[0];
                    for(var i = 0, len = buttons.length; i < len; i++) {
                        var b = buttons[i];
                        var td = document.createElement('td');
                        td.className = 'x-panel-btn-td';
                        b.render(tr.appendChild(td));
                    }
                } else {
                    var existingButtonTd = footer.child("td.x-panel-btn-td");
                    var tableRow = Ext.Element.get(existingButtonTd.dom.parentNode);
                    for(var i = 0, len = buttons.length; i < len; i++) {
                        var b = buttons[i];
                        var td = document.createElement('td');
                        td.className = 'x-panel-btn-td';
                        b.render(tableRow.appendChild(td));
                    }
                }
            }
            needsLayout = needsLayout || this._conditionalDoLayout(addedChildren);
        }
        
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
                    }
                }
            }
            needsLayout = true;
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
        } else if (!doHide) {
            this._doChildAddEffects();
        }
        
        this.syncExtComponent(update);
    },
    
    /**
     * Render display implementation.  This is called when all rendering
     * has finished.  The only thing this does is to synchronise the size
     * of this panel to the containing div if the parent component is not
     * an ext layout.
     */
    renderDisplay: function(update) {
        EchoExt20.ExtComponentSync.prototype.renderDisplay.call(this, update);
        this.syncExtComponent();
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
     * Syncs the size of the ext panel to the size of the containing div,
     * but only if the parent of this panel is not an ext container (because
     * ext containers deal with that all themslves).
     */
    syncExtComponent: function() {
        if (this._parentElement != null) {
            this.extComponent.setHeight(this._parentElement.offsetHeight);
            this.extComponent.setWidth(this._parentElement.offsetWidth);
        }
        if (this.extComponent.getLayout() instanceof Ext.layout.BorderLayout) {
            this.extComponent.doLayout();
        }
    },
    
    /**
     * Re-shows the component after being hidden during an update.
     */
    _makeVisible: function() {
    	this.extComponent.doLayout();
        this.extComponent.getEl().dom.style.visibility = 'visible';
    },
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
        // process basic properties
        
        if (this.component.render("frame")) {
            options.frame = this.component.render("frame");
            if (options.frame == true)
                options.baseCls = 'x-box';
        }
        
        if (this.component.render("titlePosition")) {
            options.titlePosition = this.component.render("titlePosition");
        }

        if (this.component.render("padding")) {
            options.style.padding = this.component.render("padding");
        }

        if (this.component.render("bodyPadding")) {
            options.bodyStyle.padding = this.component.render("bodyPadding");
        }

    	if (this.component.render("background") != null
    	   && this.component.render("bodyTransparent") == null) {
    	    options.style.backgroundColor =  this.component.render("background");
    	}

    	if (this.component.render("bodyBackground")) {
    	    options.bodyStyle.backgroundColor =  this.component.render("bodyBackground");
    	}
    
    	if (this.component.render("bodyTransparent")) {
    	    options.bodyStyle.background = "transparent";
    	}
        
        options.border = this.component.render("border", false);
        
        var collapsible = this.component.get("collapsible");
        if (collapsible != null) {
            options['collapsible'] = collapsible;
        }
        
        var height = this.component.render("height");
        if (height != null) {
            options['height'] = height;
        }
        
        var html = this.component.get("html");
        if (html != null) {
            options['html'] = html;
        }
        
        var title = this.component.get("title");
        if (title != null) {
            options['title'] = title;
        }
        
        var tabTip = this.component.get( "tabTip");
        if ( tabTip != null) {
            options['tabTip'] = tabTip;
        }
        
        var width = this.component.render("width");
        if (width != null) {
            options['width'] = width;
        }
        
        var baseCssClass = this.component.get("baseCssClass");
        if (baseCssClass != null){
            options['baseCls'] = baseCssClass;
        }
        
        if (this.component.get("iconCssClass")) {
            options['iconCls'] = this.component.get("iconCssClass");
        }
        
        if (this.component.render("autoScroll")) {
            options.autoScroll = this.component.render("autoScroll");
        }
        
        if (this.component.get("floating")) {
            options['floating'] = this.component.get("floating");
            options['shadow'] = false;
            this.positionX = this.component.get("positionX");
            this.positionY = this.component.get("positionY");
            this.relativePositionX = this.component.get("relativePositionX");
            this.relativePositionY = this.component.get("relativePositionY");
            this.relativeAnchorPosition = this.component.get("relativeAnchorPosition");
        }

        /*
         * Tool ids are passed as a comma-separated string.
         * These are the little tool icons that appear in the title
         * bar of the panel.
         * For a list of allowed tool names, see the documentation
         * for Ext.Panel
         */
        var toolIds = this.component.get("toolIds");
        if (toolIds != null) {
                
            toolConfigs = [];
            
            var toolIdArray = [];
            if (toolIds.indexOf(',') != -1) {
                    toolIdArray = toolIds.split(',');
            }
            else {
                    toolIdArray.push(toolIds);
            }

            for (var i = 0; i < toolIdArray.length; i++) {
                    var toolId = toolIdArray[i];
                    
                    var scopeHolder = new Object();
                    scopeHolder.component = this.component;
                    scopeHolder.toolId = toolId;
                    
                    var toolConfig = {
                            id: toolId,
                            handler: function() {
                                    this.component.set("toolIdClicked", this.toolId);
                                    this.component.doToolClick();
                            },
                            scope: scopeHolder
                    }
                    toolConfigs.push(toolConfig);
            }
            
            options['tools'] = toolConfigs;
        }

        // now handle the layout
        var layout = this.component.get("layout");
        if (layout != null) {
            if (layout instanceof EchoExt20.AccordionLayout) {
                options['layout'] = 'accordion';
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
        
        var buttons = this._createButtons(update, children);
        if (buttons.length > 0) {
            options['buttons'] = buttons;
        }
        
        this._makeToolbar(update, children, "top", options);
        this._makeToolbar(update, children, "bottom", options);
        
        this._registerKeyPresses(update, options);
        
        this.extComponent = this.newExtComponentInstance(options);
        this.extComponent.on("render", this._doOnExtRender, this);
        this.extComponent.on("afterlayout", this._doChildAddEffects, this);
        this.extComponent.on("beforeremove", this._doChildRemoveEffects, this);
        if (this.component.get("floating")) {
            this.extComponent.setPagePosition(this.positionX, this.positionY);
        }
        
        if (children.length > 0) {
            this._createChildItems(update, children);
            if (this._conditionalDoLayout(children))
            	this.extComponent.doLayout();
        }
        
        return this.extComponent;
    },
    
    _doChildAddEffects: function() {
        for (var i = 0; i < this.component.getComponentCount(); i++) {
            var thisChild = this.component.getComponent(i);
            if (thisChild.peer) {
                if (thisChild.peer.hasQueuedAddFx) {
                    thisChild.peer.runAddFx();
                }
            }
        }

        if (this.relativePositionX != null && this.relativePositionY != null) {
            this._setRelativePosition();
        }
    },
    
    _doChildRemoveEffects: function(container, thisChild) {
        if (thisChild.echoComponent && thisChild.echoComponent.peer) {
            if (thisChild.echoComponent.peer.hasQueuedRemoveFx) {
                thisChild.echoComponent.peer.runRemoveFx(this);
                return false;
            }
        }
        return true;
    },
    
    _doOnExtRender: function() {
        if (this.positionX != null && this.positionY != null) {
            if (this.extComponent.el) {
                var xyPos = [];
                xyPos[0] = this.positionX;
                xyPos[1] = this.positionY;
                this.extComponent.el.setXY(xyPos);
            }
        }
        if (this.relativePositionX != null && this.relativePositionY != null) {
            this._setRelativePosition();
            
            this.extComponent.ownerCt.on("resize", this._doOnExtParentResize, this);
            this.extComponent.ownerCt.on("move", this._doOnExtParentMove, this);
        }
    },
    
    _doOnExtParentResize: function () {
        this._setRelativePosition();
    },
    
    _doOnExtParentMove: function() {
        this._setRelativePosition();
    },
    
    _setRelativePosition: function() {
        if (this.extComponent.el) {
            var xyPos = [];
            
            var ownerCt = this.extComponent.ownerCt;

            var position = ownerCt.getPosition();
            xyPos[0] = position[0] + this.relativePositionX;
            xyPos[1] = position[1] + this.relativePositionY;
            
            if (this.relativeAnchorPosition == 'TR') {
                xyPos[0] = xyPos[0] + ownerCt.getSize().width;
            } else if (this.relativeAnchorPosition == 'BL') {
                xyPos[1] = xyPos[1] + ownerCt.getSize().height;
            } else if (this.relativeAnchorPosition == 'BR') {
                xyPos[0] = xyPos[0] + ownerCt.getSize().width;
                xyPos[1] = xyPos[1] + ownerCt.getSize().height;
            }
            
            this.extComponent.el.setXY(xyPos);
        }
    },
    
    /**
     * Adds the necessary options to ensure that the requested key
     * bindings are registered.
     * <p>
     * FIXME - this needs completing.
     * </p>
     */
    _registerKeyPresses: function(update, options) {
        if (this.component.get("registeredKeyPresses") != null) {
            // FIXME - implement this properly
            options['keys'] = [];
            
            var keyString = this.component.get("registeredKeyPresses");
            if (keyString == "enter") {
                options['keys'].push({
                    key: Ext.EventObject.ENTER,
                    fn: this._handleKeyPress,
                    scope: this
                });
            }
            else if (keyString == "esc") {
                options['keys'].push({
                    key: Ext.EventObject.ESC,
                    fn: this._handleKeyPress,
                    scope: this
                });
            }
        }
    },
    
    /**
     * Handles a particular key press by setting that key as pressed
     * on the component, and asking the component to fire the key press
     * event.
     */
    _handleKeyPress: function(key, evt) {
        if (key == Ext.EventObject.ENTER) {
            this.component.set("keyPressed", "enter");
        }
        else if (key == Ext.EventObject.ESC) {
            this.component.set("keyPressed", "esc");
        }
        evt.stopEvent();
        this.component.doKeyPress();
    },
    
    /**
     * Creates the requested toolbar for the panel, if there is a
     * child which is a toolbar requested be shown in that position.
     * @param update the echo server update.
     * @param children the children to search through to find the toolbar.
     * @param position either 'top' or 'bottom', to say which toolbar
     * to create.
     * @param options the options to which we should add the toolbar should
     * we find it.
     */
    _makeToolbar: function(update, children, position, options) {
        var done = false;
        
        for (var i = 0; i < children.length && !done; i++) {
            if (children[i] instanceof EchoExt20.Toolbar) {
                var childPosition = children[i].get("position");
                if (childPosition == position) {
                    // create the child
                    Echo.Render.renderComponentAdd(update, children[i], null);
                    var tbar = children[i].peer.extComponent;
                    if (tbar == null) {
                        throw new Error("No toobar ext component was created during renderAdd");
                    }
                    if (position == "top") {
                        options["tbar"] = tbar;
                    }
                    else if (position == "bottom") {
                        options["bbar"] = tbar;
                    }
                }
            }
        }
        
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
        if (this.extComponent.getLayout() instanceof Ext.layout.ColumnLayout)
            doLayout = true;
        for (var i = 0; i < children.length && !doLayout; i++) {
            var layout = children[i].get("layout");
            if ( layout != null && layout instanceof EchoExt20.BorderLayout ) {
                doLayout = true;
            }
        }
        return doLayout;
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
    
    /**
     * Creates and adds any buttons which are to be shown in the panel's
     * button bar.
     */
    _createButtons: function(update, children) {
        var buttons = [];
        for (var i = 0; i < children.length; i++) {
            var child = children[i];
            if (child instanceof EchoExt20.Button
                    && child.get("addToButtonBar") == true) {
                Echo.Render.renderComponentAdd(update, child, null);
                var button = child.peer.extComponent;
                if (button == null) {
                    throw new Error("No child ext component was created during renderAdd for component type: " + child.componentType);
                }
                else {
                    buttons.push(button)
                }
            }
        }
        return buttons;
    },
    
    _addExtComponentChild: function( child, extComponent) {
        var layoutData = child.render("layoutData");
        if (layoutData) {
            var locationName = layoutData["locationName"];
            if ( locationName) {
                // It's a HtmlLayoutData, don't add it now. It will be added at renderUpdate.
                return;
            }
        }
        if (child.parent != null) {
            if (child.parent instanceof EchoExt20.TabbedPane) {
                var tabTitle = extComponent.title.toString();
                var maxLength = 20;
                if(tabTitle.length > maxLength) {
                    extComponent.setTitle(this._getShortTitle(tabTitle, maxLength));
                }
            }
        }
        this.extComponent.add(extComponent);
    },

    /**
     * Returns a tab header friendly version of the title.
     */
    _getShortTitle: function(title, maxLength) {
        if(maxLength < 3)
            return title;

        var fullLength = title.length;
        var trimmedLength = (maxLength - 2);

        if( (fullLength - 2) > trimmedLength) {
            return title.substr(0, trimmedLength) + "..";
        }
        else {
            return title;
        }
    },
    
    /**
     * Creates the passed children and adds them to the ext panel.
     */
    _createChildItems: function(update, children) {
        
        for (var i = 0; i < children.length; i++) {
            var child = children[i];
            
            var childIndex = this.component.indexOf(child);
            /*
             *  if this is not an ext20 component, we need to wrap it
             *  so that it can operate within an ext container.
             */
            if (! (child instanceof EchoExt20.ExtComponent) ) {
                // we don't renderAdd here - ext does it lazily
                var wrapper = new EchoExt20.Echo3SyncWrapper(update, child);
                if (this.extComponent.items) {
                    var inserted = false;
                    for (var h = 0; h < this.extComponent.items.getCount() && !inserted; h++) {
                        var extChild = this.extComponent.items.itemAt(h);
                        var echoComp = extChild.echoComponent;
                        
                        var extChildIndex = this.component.indexOf(extChild.echoComponent);
                        if (extChildIndex > childIndex) {
                            this.extComponent.insert(h, wrapper);
                            inserted = true;
                        }
                    }
                    if (!inserted)
                        this._addExtComponentChild( child, wrapper);
                } else {
                    this._addExtComponentChild( child, wrapper);
                }
            }
            else if (child instanceof EchoExt20.Window) {
                this._createWindow(update, child);
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
                    if (this.extComponent.items) {
                        var inserted = false;
                        for (var h = 0; h < this.extComponent.items.getCount() && !inserted; h++) {
                            var extChild = this.extComponent.items.itemAt(h);
                            var echoComp = extChild.echoComponent;
                            
                            var extChildIndex = this.component.indexOf(extChild.echoComponent);
                            if (extChildIndex > childIndex) {
                                this.extComponent.insert(h, childExtComponent);
                                inserted = true;
                            }
                        }
                        if (!inserted)
                            this._addExtComponentChild( child, childExtComponent);
                    } else {
                        this._addExtComponentChild( child, childExtComponent);
                    }
                }
            }
        }

        // now make sure that the child indexes are set, so that, for instance, when a certain
        // tab is selected in a tabbed pane, it can work out what the index of the displayed component
        // is.
        for (var i = 0; i < this.component.getComponentCount(); i++) {
            var child = this.component.getComponent(i);
            child.childIndex = i;
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

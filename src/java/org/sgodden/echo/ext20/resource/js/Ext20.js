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
 * @fileoverview
 * Ext20 framework main module.
 */

// Ext initialisation
Ext.BLANK_IMAGE_URL="resources/ext/images/default/s.gif";
Ext.QuickTips.init();


/**
 * Namespace for application framework.
 * @namespace
 */
EchoExt20 = {};

/**
 * Provides a wrapper allowing echo3 sync peers to work within
 * extjs.
 * <p>
 * TODO - work out whether this should extend Ext.BoxCompoent
 * rather than Ext.Component.
 * <p/>
 * @constructor
 */
EchoExt20.Echo3SyncWrapper = function(update, wrappedComponent) {
    EchoExt20.Echo3SyncWrapper.superclass.constructor.call(this);

    this.wrappedComponent = wrappedComponent;
    this.wrappedRootElement = document.createElement("div");

    var options = {};
    options.style = {};
    options.bodyStyle = {};
    
    /*
     * Add the necessary layout options.
     */
    EchoExt20.LayoutProcessor.addLayoutOptions(options, wrappedComponent);
    Ext.apply(this, options);

    /*
     * We need to call render add immediately, but defer
     * adding it until this wrapper is rendered.
     */
    Echo.Render.renderComponentAdd(update, this.wrappedComponent, this.wrappedRootElement);
   
    /*
     * Intercept the wrapped component's peer's renderDispose to ensure that this wrapper is disposed.
     */
    this.wrappedComponent.peer.renderDispose = this.wrappedComponent.peer.renderDispose.createInterceptor(this.onRenderDispose, this);
}

Ext.extend(EchoExt20.Echo3SyncWrapper, Ext.Component, {
    /**
     * Invokes the echo3 sync peer lazily on render.
     * @param {Object} ct the container div.
     * @param {Object} position the child div to which we should render the echo3 component.
     */
    onRender: function(ct, position) {

        this.el = new Ext.Element(this.wrappedRootElement);

        if (position != null) {
        	if(position.nodeType != 3){
        		position.appendChild(this.wrappedRootElement);
        	}
        	else{
        		position.parentNode.appendChild(this.wrappedRootElement);
        	}
            
        }
        else {
            ct.appendChild(this.el);
        }
    },

    /**
     * Removes the component from the ext container.
     */
    onRenderDispose: function(update) {
    	if (typeof this.ownerCt != 'undefined')
    		this.ownerCt.remove(this);
    },
    
    /**
     * Method from Ext.BoxComponent which should (but currently does not)
     * size the component accordingly.
     * <p>
     * TODO - work out whether this is required.
     * </p>
     */
    setSize: function() {
    },

    /**
     * Method from Ext.BoxComponent which returns the size of the
     * element.
     */
    getSize: function() {
        return this.el.getSize();
    }
});

/**
 * Base class for all ext20 components.
 */
EchoExt20.ExtComponent = Core.extend(Echo.Component, {
	
    /**
     * Fires a before render event.
     */
    doBeforeRender: function() {
        this.fireEvent({type: "beforeRender", source: this});
    }
});

/**
* Abstract base class for all Ext20 sync peers.
*/
EchoExt20.ExtComponentSync = Core.extend(Echo.Render.ComponentSync, {
    
    $abstract: {
        /**
         * Called during renderAdd to actually create the ext component.
         */
        createExtComponent: function(update, options) {}
    },

    $virtual: {
        renderFocus: function() {

            if (this.extComponent.rendered) {
                try {
                    this.extComponent.focus();
                    if (this.extComponent.selectText) {
                        this.extComponent.selectText();
                    }
                } catch (ex) {
                    this.extComponent.el.focus();
                }
            }
            else {
                this.extComponent.on(
                    "render",
                    function(){ 
                        this.extComponent.focus()
                        if (this.extComponent.selectText) {
                            this.extComponent.selectText();
                        }
                    },
                    this)
            }
        }
    },
    
    /**
     * Simple marker property to identify that this as an ext-related peer.
     */
    isExtComponent: true,
    
    /**
     * The ext component created by the peer.
     */
    extComponent: null,
    
    /**
     * If this is not an ext component, then this references the
     * DOM element we have been asked to render ourself into.
     */
    _parentElement: null,
	
    /**
     * Whether this component is a root container in the page.
     * <p>
     * A component is a root container if it is a) a container,
     * and b) its parent is not an ext container.
     * </p>
     * <p>
     * So if we had Ext container A containing Echo container B containing
     * Ext container C, then A and C are root containers, and will need
     * to have doLayout called on them at the end of the server update
     * cycle.
     * </p>
     * <p>
     * A is also the "root root" container (see below).
     * </p>
     */
    _isARootContainer: false,

    /**
     * Whether this component is the root root container, that is, 
     * whether it is the very first ext container in the DOM.
     * <p>
     * This container is responsible for calling doLayout on itself
     * and other root containers which had chilren added or removed
     * during a server update.
     * <p/>
     */
    _isRootRootContainer: false,

    /**
     * An array of containers requiring layout to be
     * performed on them once the server update is
     * complete.
     */
    _containersRequiringLayout: null,
    
    /**
     * If we are the root ext container, then we have to perform special
     * processing once all rendering has been done.
     */
    renderDisplayCompleteRef: null,

    /**
     * Reference to a method to invoke to update any
     * ext layout containers that require it, once the
     * server update is complete.
     */
     _layoutExtContainersRef: null,

    /**
     * The last server update for the component.
     */
    _update: null,
    
    /**
     * Whether the component has effects queued from being added or removed
     */
    hasQueuedAddFx: false,
    
    /**
     * The queued effects
     */
    queuedAddFx: [],
    
    /**
     * Whether the component has effects queued from being added or removed
     */
    hasQueuedRemoveFx: false,
    
    /**
     * The queued effects
     */
    queuedRemoveFx: [],
    
    /**
     * The container the fx is removing us from
     */
    fxRemoveContainer: null,
    
    _toolTip: null,
    
    /**
     * Notifies the root container that layout changes
     * occurred, and that it therefore needs to redo its
     * layout.
     */
    _notifyLayoutChanges: function() {
        if (this._isARootContainer) {
            /*
             * We are a root container - tell the overall root
             * container that we require layout when the
             * update has finished.
             */
            if (EchoExt20.rootRootContainer._containersRequiringLayout == null) {
                EchoExt20.rootRootContainer._containersRequiringLayout = [];
            }
            if (EchoExt20.rootRootContainer._containersRequiringLayout.indexOf(this) == -1) {
                EchoExt20.rootRootContainer._containersRequiringLayout.push(this);
            }
        }
        else {
            // not a root, tell the parent
            this.component.parent.peer._notifyLayoutChanges();
        }
    },

    /**
     * Called upon initial creation of the component.
     */
    renderAdd: function(update, parentElement) {
        this._update = update;
        
        if (this.component.get("containerAddFx") != null) {
            var effect = this.component.get("containerAddFx");
            this.queuedAddFx[0] = effect;
            this.hasQueuedAddFx = true;
        }
        if (this.component.get("containerRemoveFx") != null) {
            var effect = this.component.get("containerRemoveFx");
            this.queuedRemoveFx[0] = effect;
            this.hasQueuedRemoveFx = true;
        }

        /*
         * Windows, and all components whose parent is not an
         * ext component, must add a render display complete listener
         * to ensure that they create their components after render
         * display has been completed, and the parent elements
         * will be in the DOM.
         */
        if ( (this instanceof EchoExt20.WindowSync)
                  || !(this.component.parent.peer.isExtComponent) 
                ) {

            this.renderDisplayCompleteRef = Core.method(this, this._rootRenderDisplayComplete);
            Echo.Render.addRenderDisplayCompleteListener(this.renderDisplayCompleteRef);
            
            if (this instanceof EchoExt20.PanelSync) {
                this._isARootContainer = true;
                /*
                 * If the root root container is not already set,
                 * then we must be it.
                 */
                if (!(EchoExt20.rootRootContainer)) {
                    EchoExt20.rootRootContainer = this;
                    this._isRootRootContainer = true;
                    /*
                     * Ensure that we are called at the end of every server update
                     * to perform layout on containers that have requested it.
                     */
                    this._layoutExtContainersRef = Core.method(this, this._layoutExtContainers);
                    this.client.addServerUpdateCompleteListener(this._layoutExtContainersRef);
                }
                else {
                    /*
                     * We are not the root root container.  We need to ensure
                     * that the root root container lays us out once
                     * the server update is complete.
                     */
                    this._notifyLayoutChanges();
                }
            }
        }
       
        /*
         * If the parent is an ext component then we are
         * safe to create here.
         */
        if (this.component.parent.peer.isExtComponent) {
            options = {};
            options['id'] = this.component.renderId;

            options.style = {};
            options.bodyStyle = {};
            options.closable = this.component.get("closable");
            if (this.component.get("cssClass")) {
                options.cls = this.component.get("cssClass");
            }
            if (this.component.get("containerCssClass")) {
                options.ctCls = this.component.get("containerCssClass");
            }
           
            /*
             * Add the necessary layout options.
             */
            EchoExt20.LayoutProcessor.addLayoutOptions(options, this.component);
            
            this.extComponent = this.createExtComponent(
                update,
                options
            );
            if (this.component.get("toolTip")) {
                if (this.component.get("showToolTip") != null){
                    if(this.component.get("showToolTip")){
                        this.extComponent.on("render", this._addToolTip, this);
                    }
                }
            }
            
            if (this.component.get("alignTo") != null) {
                this.extComponent.on("render", function(){
                    this.alignTo(this.component.get("alignTo"));
                }, this);
            }
            
            this._addBeforeRenderListener();
            
            this._parentElement = null;
            

            
        }
        else {
            /*
             * Our parent was not an ext container, so we need to just note
             * the parent element here, and defer component creation until
             * renderDisplay, when we are definitely in the DOM.
             */
            this._parentElement = parentElement; 
        }
    },
    
    runAddFx: function() {
        this.hasQueuedAddFx = false;
        if (this.queuedAddFx[0] == "FADE_IN") {
            this.extComponent.getEl().fadeIn();
        } else if (this.queuedAddFx[0] == "SLIDE_IN") {
            this.extComponent.getEl().slideIn();
        }
        this.queuedAddFx = [];
    },
    
    runRemoveFx: function(container) {
        this.hasQueuedRemoveFx = false;
        this.fxRemoveContainer = container;
        if (this.queuedRemoveFx[0] == "FADE_OUT") {
            this.extComponent.getEl().fadeOut({callback: this._removeAfterFx.createDelegate(this), remove: true});
        } else if (this.queuedRemoveFx[0] == "SLIDE_OUT") {
            if (this.extComponent.getEl().dom.parentNode != null)
                this.extComponent.getEl().slideOut('t', {callback: this._removeAfterFx.createDelegate(this), remove: true});
            else
                container.extComponent.remove(this.extComponent);
        } else if (this.queuedRemoveFx[0] == "GHOST") {
                this.extComponent.getEl().ghost('t', {callback: this._removeAfterFx.createDelegate(this), remove: true});
        } else if (this.queuedRemoveFx[0] == "PUFF") {
                this.extComponent.getEl().puff({callback: this._removeAfterFx.createDelegate(this), remove: true});
        } else if (this.queuedRemoveFx[0] == "SWITCH_OFF") {
                this.extComponent.getEl().switchOff({callback: this._removeAfterFx.createDelegate(this), remove: true});
        }
        this.queuedRemoveFx = [];
    },
    
    _removeAfterFx: function() {
//        this.fxRemoveContainer.remove(this.extComponent);
//        this.fxRemoveContainer.ownerCt.doLayout();
//        this.fxRemoveContainer = null;
    },
    
    /**
     * Use by root containers to redo their layout if child
     * layout updates occurred, after the render display
     * phase is complete.
     */
    _rootRenderDisplayComplete: function() {

        this._maybeCreateComponent();
    },
    
    /**
     * Lays out any ext containers that have asked for it.
     */
    _layoutExtContainers: function() {
        /*
         * If we are the root component for the entire
         * page, then loop through any containers that
         * have indicated they need layout, and invoke
         * it on them.
         */
        if (this._containersRequiringLayout != null) {
            for (var i = 0; i < this._containersRequiringLayout.length; i++) {
                var peer = this._containersRequiringLayout[i];
                peer.extComponent.doLayout();
            }
            this._containersRequiringLayout = null;
        }
    },

    _maybeCreateComponent: function() {
        if (this.extComponent == null) {
            var options = {
                id: this.component.renderId,
                style: {},
                bodyStyle: {},
                closable: this.component.get("closable")
            };

            if (this.component.get("cssClass")) {
                options.cls = this.component.get("cssClass");
            }
            if (this.component.get("containerCssClass")) {
                options.ctCls = this.component.get("containerCssClass");
            }
            if (this instanceof EchoExt20.PanelSync) {
                options.renderTo = this._parentElement;
            }
            
            this.extComponent = this.createExtComponent(
                this._update,
                options
            );

        	/*
        	 * If this is not a panel, then we need to surround it
        	 * with a panel, to ensure that child dimensions are set
        	 * correctly.
        	 */
        	if (!(this instanceof EchoExt20.PanelSync)) {
        		var container = new Ext.Panel({
        		    //layout: 'fit',
        		    border: false,
                    renderTo: this._parentElement,
                    items: [this.extComponent]
        		});
        	}
            if (Core.Web.Env.BROWSER_INTERNET_EXPLORER
                    && this._isRootRootContainer) {
                this._parentElement.style.overflow = "visible";
                this._parentElement.parentNode.style.overflow = "visible";
            }
            this._addBeforeRenderListener();
        }
    },
    
    /**
     * Sets the tooltip on this component.
     */
    _addToolTip: function(component) {
        if (this.component.get("toolTip")) {
            var toolTip = new Ext.ToolTip({
                target: component.el,
                dismissDelay: 0,
                html: this.component.get("toolTip")
            });
            toolTip.enable();
        }
    },
    
    /**
     * Convenience method to debug out a set of options.
     */
    _debugOptions: function(prefix, options) {
        var out = prefix += "\n";
        for (var key in options) {
            out += key + ": " + options[key] + "\n";
        }
        alert(out);
    },
    
    renderDisplay: function(update) {
        this._maybeCreateComponent();
        
        // FIXME - only do this if necessary
        if (this.component.renderId == "c_resizePanel") {
            if (this.extComponent.getEl()) {
                Core.Web.VirtualPosition._init();
                Core.Web.VirtualPosition.redraw(this.extComponent.getEl().dom);
            }
        }
    },
    
    _addBeforeRenderListener: function() {
        if (this.component._listenerList != null
                && this.component._listenerList.hasListeners("beforeRender")) {
            this.extComponent.on("beforerender", this._beforeRender, this);
        }
    },
    
    _beforeRender: function() {
        this.component.doBeforeRender();
    },
    
    renderDispose: function(update) {
        if (this._layoutExtContainersRef != null) {
            this.client.removeServerUpdateCompleteListener(this._layoutExtContainersRef);
        }
        if (this._parentElement != null) {
            // we are the top level container, so remove the
            // update listener we added earlier
            Echo.Render.removeRenderDisplayCompleteListener(this.renderDisplayCompleteRef);
        }
    },
    
    renderUpdate: function(update) {

        if (this.component.get("toolTip")) {
            if (this.component.get("showToolTip") != null){
                if(this.component.get("showToolTip")){
                    var toolTip = new Ext.ToolTip({
                       target: this.extComponent.getEl(),
                        dismissDelay: 0,
                        html: this.component.get("toolTip")
                    });
                    this._toolTip = toolTip;
                    toolTip.enable();
                }else{
                	if(this._toolTip!=null){
                		this._toolTip.disable();
                		this._toolTip = null;
                	}
                }
            }
        }
        
        if (update.hasAddedChildren() || update.hasRemovedChildren()) {
                this._notifyLayoutChanges();
        }
		
        if (update.hasUpdatedProperties()) {
            var alignToUpdate = update.getUpdatedProperty("alignTo");
            if (alignToUpdate != null) {
                var alignToString = alignToUpdate.newValue;
                this.alignTo(alignToString);
            }
        }
    },
    
    alignTo: function(alignToString) {
        
        var strings = alignToString.split(",");
        
        var otherId = "c_" + strings[0];
        
        var alignmentString = strings[1];
        
        var offsets = strings[2].split(':');
        var offsetX = offsets[0];
        var offsetY = offsets[1];
        
        var extEl = this.extComponent.getEl();
        extEl.dom.style.position = "absolute";
        extEl.alignTo(otherId, alignmentString, [offsetX,offsetY]);
    }
    
});

/**
 * Static object / namespace which handles layout processing.
 * Do not instantiate.
 * @class
 */
EchoExt20.LayoutProcessor = {
    /**
     * Looks at the parent's layout, and adds
     * appropriate entries to the passed dictionary of options
     * based on the child's layout data.
     * @param options an existing dictionary into which to add the new options.
     * @param child the child component which has the layout data on it.
     */
    addLayoutOptions: function(options, child) {
        // and now handle the layout data
        var layout = child.parent.get("layout");
        if (layout != null) {
            var layoutData = child.get("layoutData");
            // border layout
            if (layout instanceof EchoExt20.BorderLayout) {
                // layout data mandatory for a border layout
                if (layoutData == null) {
                    throw new Error("No layout data provided for component in a border layout");
                }
                var region = EchoExt20.LayoutProcessor._convertToExtRegion(layoutData.region);
                options['region'] = region;
                // if we are in the north, and have no height set, then we need autoHeight true.
                // FIXME - how about handling width in west and east, and height in south?
                if (region == 'north') {
                    var height = child.render("height");
                    if (height == null) {
                        options['autoHeight'] = true;
                    }
                }
                
            }
            else if (layout instanceof EchoExt20.ColumnLayout) {
                if (layoutData != null) {
                    options['columnWidth'] = parseFloat(layoutData.columnWidth);
                }
            }
            else if (layout instanceof EchoExt20.FormLayout) {
                if (layoutData != null) {
                    options['anchor'] = layoutData.anchor;
                }
            }
            else if (layout instanceof EchoExt20.TableLayout) {
                if (layoutData != null) {
                    if (layoutData.cellStyle != null) {
                        options['cellStyle'] = layoutData.cellStyle;
                    }
                    if (layoutData.cellAlign) {
                        options.cellAlign = layoutData.cellAlign;
                    }
                    if (layoutData.cellVAlign) {
                        options.cellVAlign = layoutData.cellVAlign;
                    }
                    if (layoutData.cellCls) {
                        options.cellCls = layoutData.cellCls;
                    }
                    if (layoutData.colSpan) {
                        options.colspan = layoutData.colSpan;
                    }
                    if (layoutData.rowSpan) {
                        options.rowspan = layoutData.rowSpan;
                    }
                }
                //if (layout.cellPadding) {
                //    var childPadding = child.render("padding", "");
                //    if (childPadding == "") {
                //        options.style.padding = layout.cellPadding;
                //    }
                //}
            }
            // other layouts (form layout, fit layout, table layout) do not require layout data on their children
        }
    },

    /**
     * Convenience method to turn a single character region into an
     * ext region equivalent.
     */
    _convertToExtRegion: function(shortRegion) {
        var ret = null;
        
        if (shortRegion == 'n') {
            ret = 'north';
        }
        else if (shortRegion == 'e') {
            ret = 'east';
        }
        else if (shortRegion == 's') {
            ret = 'south';
        }
        else if (shortRegion == 'w') {
            ret = 'west';
        }
        else if (shortRegion == 'c') {
            ret = 'center';
        }
        
        if (ret == null) {
            throw new Error("Unknown short region code: " + shortRegion);
        }
        
        return ret;
    }
}

/**
 * Namespace for property translators.
 * @namespace
 */
EchoExt20.PropertyTranslator = {
    /**
     * Evaluates the JSON character content of the passed xml element
     * into a javascript object.  This caters for the fact that firefox
     * splits long text documents into multiple child nodes (thanks Tod).
     */
    toJsObject: function(client, propertyElement) {
        var jsonArray = new Array();
        for (i = 0; i < propertyElement.childNodes.length; i++) {
            jsonArray.push(propertyElement.childNodes.item(i).data);
        }
        var jsonText = jsonArray.join("");
        return eval("(" + jsonText + ")"); // FIXME - security risk - use parseJSON instead
    }
};

/**
 * An accordion layout (see Ext.layout.Accordion).
 */
EchoExt20.AccordionLayout = Core.extend({
});

/**
 * Property translator for accordion layout.
 */
EchoExt20.PropertyTranslator.AccordionLayout = {
    toProperty: function(client, propertyElement) {
        return new EchoExt20.AccordionLayout();
    }
}

Echo.Serial.addPropertyTranslator("Ext20AccordionLayout", EchoExt20.PropertyTranslator.AccordionLayout);
Echo.Serial.addPropertyTranslator("E2AL", EchoExt20.PropertyTranslator.AccordionLayout);

/**
 * A border layout (see Ext.layout.BorderLayout).
 */
EchoExt20.BorderLayout = Core.extend({
});

/**
 * Property translator for border layout.
 */
EchoExt20.PropertyTranslator.BorderLayout = {
    toProperty: function(client, propertyElement) {
        return new EchoExt20.BorderLayout();
    }
}

Echo.Serial.addPropertyTranslator("Ext20BorderLayout", EchoExt20.PropertyTranslator.BorderLayout);
Echo.Serial.addPropertyTranslator("E2BL", EchoExt20.PropertyTranslator.BorderLayout);

/**
 * A fit layout (see Ext.layout.FitLayout).
 */
EchoExt20.FitLayout = Core.extend({
});

/**
 * Property translator for fit layout.
 */
EchoExt20.PropertyTranslator.FitLayout = {
    toProperty: function(client, propertyElement) {
        return new EchoExt20.FitLayout();
    }
}

Echo.Serial.addPropertyTranslator("Ext20FitLayout", EchoExt20.PropertyTranslator.FitLayout);
Echo.Serial.addPropertyTranslator("E2FL", EchoExt20.PropertyTranslator.FitLayout);

/**
 * A column layout (see Ext.layout.ColumnLayout).
 */
EchoExt20.ColumnLayout = Core.extend({
});

/**
 * Property translator for column layout.
 */
EchoExt20.PropertyTranslator.ColumnLayout = {
    toProperty: function(client, propertyElement) {
        return new EchoExt20.ColumnLayout();
    }
}

Echo.Serial.addPropertyTranslator("Ext20ColumnLayout", EchoExt20.PropertyTranslator.ColumnLayout);
Echo.Serial.addPropertyTranslator("E2CL", EchoExt20.PropertyTranslator.ColumnLayout);

/**
 * A form layout (see Ext.layout.FormLayout).
 */
EchoExt20.FormLayout = Core.extend({
});

/**
 * Property translator for form layout.
 */
EchoExt20.PropertyTranslator.FormLayout = {
    toProperty: function(client, propertyElement) {
        return new EchoExt20.FormLayout();
    }
}

Echo.Serial.addPropertyTranslator("Ext20FormLayout", EchoExt20.PropertyTranslator.FormLayout);
Echo.Serial.addPropertyTranslator("E2FML", EchoExt20.PropertyTranslator.FormLayout);

/**
 * A table layout (see Ext.layout.TableLayout).
 */
EchoExt20.TableLayout = Core.extend({
    columns: 0,
    cellPadding: "0px",
    cellSpacing: 0,
    fullHeight: false,
    fullWidth: false,
    columnWidths : null,
    
    $construct: function(columns, border, fullHeight, fullWidth, cellPadding, cellSpacing, columnWidths) {
        this.columns = columns;
        this.border = border;
        this.fullHeight = fullHeight;
        this.fullWidth = fullWidth;
        this.cellPadding = cellPadding;
        this.cellSpacing = cellSpacing;
        this.columnWidths = columnWidths;
    }
    
});

/**
 * Property translator for table layout.
 */
EchoExt20.PropertyTranslator.TableLayout = {
    toProperty: function(client, propertyElement) {

        var columns = propertyElement.getAttribute('c');
        if (columns == null) {
            columns = '0';
        }

        var cellSpacing = propertyElement.getAttribute('sp');
        if (cellSpacing == null) {
            cellSpacing = '0';
        }

        var cellPadding = propertyElement.getAttribute('p');

        var border = propertyElement.getAttribute('b') == "1" ? true : false;
        var fullHeight = propertyElement.getAttribute('fh') == "1" ? true : false;
        var fullWidth = propertyElement.getAttribute('fw') == "1" ? true : false;
        
        var columnWidths = null;
        var colWidthsStr = propertyElement.getAttribute("cw");
        if (colWidthsStr) {
            var colWidthsArrStr = colWidthsStr.split(",");
            columnWidths = [];
            for (var i = 0;i < colWidthsArrStr.length; i++) {
                columnWidths[i] = parseInt(colWidthsArrStr[i]);
            }
        }

        var ret = new EchoExt20.TableLayout(
            parseInt(columns), border, fullHeight, fullWidth, cellPadding, parseInt(cellSpacing), columnWidths
        );

        return ret;
    }

}

Echo.Serial.addPropertyTranslator("Ext20TableLayout", EchoExt20.PropertyTranslator.TableLayout);
Echo.Serial.addPropertyTranslator("E2TL", EchoExt20.PropertyTranslator.TableLayout);

/**
 * Property translator for a simple store of data (a two-dimensional array), 
 * and field names, from which an Ext store can be created.
 * <p>
 * The data is all JSON-encoded within the passed property element, which is
 * a shortcut way to get the job done, but not very observable.
 * </p>
 * <p>
 * This started life as a class which directly instantiated an Ext.SimpleStore,
 * thus the name, which is now misleading.
 * It became apparent that we needed more control over the store,
 * so this now just returns a simple JS object.
 * <p/>
 */
EchoExt20.PropertyTranslator.SimpleStore = {
    toProperty: function(client, propertyElement) {
        return EchoExt20.PropertyTranslator.toJsObject(client, propertyElement);
    },
    toXml: function(client, propertyElement, propertyValue) {
        var json = Ext.util.JSON.encode(propertyValue);
        var node = propertyElement.ownerDocument.createTextNode(json);
        propertyElement.appendChild(node);
    }
};

Echo.Serial.addPropertyTranslator("Ext20SimpleStore", EchoExt20.PropertyTranslator.SimpleStore);
Echo.Serial.addPropertyTranslator("E2SS", EchoExt20.PropertyTranslator.SimpleStore);

/**
 * Property translator which directly creates an Ext.grid.ColumnModel
 * from the JSON-encoded data in the property element.
 */
EchoExt20.PropertyTranslator.ColumnModel = {
    toProperty: function(client, propertyElement) {
        var obj = EchoExt20.PropertyTranslator.toJsObject(client,
                propertyElement);
        for (var i = 0; i < obj.columns.length; i++) {
            var thisCol = obj.columns[i];
            if (thisCol.editorConfig) {
                var config = thisCol.editorConfig;
                if (thisCol.editorConfig.type == 'Ext.form.TextField') {
                    thisCol.editor = new Ext.form.TextField(config);
                } else if (thisCol.editorConfig.type == 'Ext.form.Checkbox') {
                    delete thisCol.renderer;
                    var checkCol = new Ext.grid.CheckColumn(thisCol);
                    obj.columns[i] = checkCol;
                    
                } else if ( thisCol.editorConfig.type == 'Ext.form.ComboBox') {
                    config['store'] = thisCol.editorConfig.modelValues;
                    config['triggerAction'] = 'all';
                    thisCol.editor = new Ext.form.ComboBox(config);
                } else {
                    eval("thisCol.editor = new " + config.type + "(config);");
                }
            }
        }
        
        return new EchoExt20.ColumnModel({
        	defaultSortable: obj.defaultSortable,
        	defaultWidth: obj.defaultWidth,
        	columns: obj.columns
        });
    },
    toXml: function(client, propertyElement, propertyValue) {
        var colObject = new Object();
        colObject.columns = new Array();
        for(var x = 0; x < propertyValue.config.length; x++){
            var propEl = propertyValue.config[x];
            var config = new Object();
            config.attributePath = propEl.attributePath;
            config.dataIndex = propEl.dataIndex;
            config.displaySequence = propEl.displaySequence;
            config.header = propEl.header;
            config.hidden = propEl.hidden;
            config.sortDirection = propEl.sortDirection;
            config.sortSequence = propEl.sortSequence;
            config.sortable = propEl.sortable;
            config.width = propEl.width;
            config.grouping = propEl.grouping;
            if (propEl.menuDisabled) {
            	config.menuDisabled = true;
            }
            else {
            	config.menuDisabled = false;
            }
            colObject.columns.push(config);
        }
        var json = Ext.util.JSON.encode(colObject);
        var node = propertyElement.ownerDocument.createTextNode(json);
        propertyElement.appendChild(node);
    }
};

Echo.Serial.addPropertyTranslator("Ext20ColumnModel", EchoExt20.PropertyTranslator.ColumnModel);
Echo.Serial.addPropertyTranslator("E2CM", EchoExt20.PropertyTranslator.ColumnModel);

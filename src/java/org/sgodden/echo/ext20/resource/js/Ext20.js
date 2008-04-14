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
* Ext initialisation.
*/
Ext.QuickTips.init();

/**
* Simply defines the namespace.
*/
EchoExt20 = {};

EchoExt20.ExtComponent = Core.extend(EchoApp.Component, {
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
EchoExt20.ExtComponentSync = Core.extend(EchoRender.ComponentSync, {
    
    $abstract: {
        /**
         * Called during renderAdd to actually create the ext component.
         */
        createExtComponent: function(update, options) {}
    },
    
    $static: {
        openWindows: []
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
	 * Whether this panel is the root ext container of
	 * the browser window, or an open window.
	 */
	_isRootContainer: false,

	/**
	 * Whether updates to child layouts occurred
	 * on this server update.
	 */
	_childLayoutUpdatesOccurred: false,
    
    /**
     * If we are the root ext container, then we have to perform special
     * processing once all rendering has been done.
     */
    _rootServerUpdateCompleteRef: null,
	
	/**
	 * Notifies the root container that layout changes
	 * occurred, and that it therefore needs to redo its
	 * layout.
	 */
	_notifyLayoutChanges: function() {
		if (this._isRootContainer) {
			this._childLayoutUpdatesOccurred = true;
		}
		else {
			// not the root, tell the parent
			this.component.parent.peer._notifyLayoutChanges();
		}
	},

    
    renderAdd: function(update, parentElement) {
        /*
        If the component's parent's peer indicates that it is
        an ext component, then we need to create the ext component
        here in the renderAdd phase, so that is available at the start
        of the parent's renderDisplay phase.
        
        If the component's parent's peer does not indicate that it
        is an ext component, then we need to defer creation of
        the ext component until renderDisplay, because we will
        be rendering it direct to a DOM element, and ext can only
        render to a DOM element which has been added to the DOM tree,
        and this is only guaranteed to be true during renderDisplay.
        */
		
		/*
		 * We need to know if we are the root container, so that we can
		 * perform required layout opertaions on server update completion
		 * if we or any of our children have had layout changes.
		 */
		if ( (this instanceof EchoExt20.WindowSync)
			  || !(this.component.parent.peer.isExtComponent) 
			) {
			
			this._isRootContainer = true;
            this._rootServerUpdateCompleteRef = Core.method(this, this._rootServerUpdateComplete);
            this.client.addServerUpdateCompleteListener(this._rootServerUpdateCompleteRef);
		}
        
        if (this.component.parent.peer.isExtComponent) {
            options = {};
            options['id'] = this.component.renderId;
            
            // and now handle the layout data
            var layout = this.component.parent.get("layout");
            if (layout != null) {
                // border layout
                if (layout instanceof EchoExt20.BorderLayout) {
                    var layoutData = this.component.get("layoutData");
                    // layout data mandatory for a border layout
                    if (layoutData == null) {
                        throw new Error("No layout data provided for component in a border layout");
                    }
                    var region = this._convertToExtRegion(layoutData.region);
                    options['region'] = region;
                    // if we are in the north, and have no height set, then we need autoHeight true.
                    // fixme - how about handling width in west and east, and height in south?
                    if (region == 'north') {
                        var height = this.component.get("height");
                        if (height == null) {
                            options['autoHeight'] = true;
                        }
                    }
                    
                }
                else if (layout instanceof EchoExt20.ColumnLayout) {
                    var layoutData = this.component.get("layoutData");
                    // layout data is NOT mandatory for column layout
                    if (layoutData != null) {
                        options['columnWidth'] = parseFloat(layoutData.columnWidth);
                    }
                }
                // other layouts (form layout, fit layout, table layout) do not require layout data on their children
            }
            
            this.extComponent = this.createExtComponent(
                update,
                options
            );
            
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
    
    /**
     * Use by root containers to redo their layout if child
     * layout updates occurred.
     */
    _rootServerUpdateComplete: function() {
        if (this._childLayoutUpdatesOccurred) {
			this.extComponent.doLayout();
			this._childLayoutUpdatesOccurred = false;
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
    },
    
    /**
     * Convenience method to debug out a set of options.
     */
    debugOptions: function(prefix, options) {
        var out = prefix += "\n";
        for (var key in options) {
            out += key + ": " + options[key] + "\n";
        }
        alert(out);
    },
    
    renderDisplay: function(update) {
        // if the parent is not an ext component, then we must be being added to a
        // regular div, which has to happen here, when we can guarantee that the div
        // is in the DOM tree
        if (this.extComponent == null) {
            // we need the parent div to have overflow visible, or we get 
            // rendering bugs in IE
            this._parentElement.style.overflow = "visible";
            this._parentElement.parentNode.style.overflow = "visible";

            var options = {
                id: this.component.renderId,
                renderTo: this._parentElement
            };
            this.extComponent = this.createExtComponent(
                update,
                options
            );
            this._addBeforeRenderListener();
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
        if (this._parentElement != null) {
            // we are the top level container, so remove the
            // update listener we added earlier
            this.client.removeServerUpdateCompleteListener(this._rootServerUpdateCompleteRef);
        }
        this.extComponent.destroy();
    },
    
    renderUpdate: function(update) {
		
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
    },
    
    renderFocus: function() {
//        this.extComponent.on("render", function(){
//            this.extComponent.focus(true);
//        }, this);
        this.extComponent.focus();
    }
    
});

// Abstract component classes and sync peers

// Property translators

EchoExt20.PropertyTranslator = {
    toJsObject: function(client, propertyElement) {
        var jsonArray = new Array();
        for (i = 0; i < propertyElement.childNodes.length; i++) {
            jsonArray.push(propertyElement.childNodes.item(i).data);
        }
        var jsonText = jsonArray.join("");
        return eval("(" + jsonText + ")"); // FIXME - security risk - use parseJSON instead
    }
};

EchoExt20.AccordionLayout = Core.extend({
});

EchoExt20.PropertyTranslator.AccordionLayout = {
    toProperty: function(client, propertyElement) {
        return new EchoExt20.AccordionLayout();
    }
}

EchoSerial.addPropertyTranslator("Ext20AccordionLayout", EchoExt20.PropertyTranslator.AccordionLayout);
EchoSerial.addPropertyTranslator("E2AL", EchoExt20.PropertyTranslator.AccordionLayout);

EchoExt20.BorderLayout = Core.extend({
});

EchoExt20.PropertyTranslator.BorderLayout = {
    toProperty: function(client, propertyElement) {
        return new EchoExt20.BorderLayout();
    }
}

EchoSerial.addPropertyTranslator("Ext20BorderLayout", EchoExt20.PropertyTranslator.BorderLayout);
EchoSerial.addPropertyTranslator("E2BL", EchoExt20.PropertyTranslator.BorderLayout);

EchoExt20.FitLayout = Core.extend({
});

EchoExt20.PropertyTranslator.FitLayout = {
    toProperty: function(client, propertyElement) {
        return new EchoExt20.FitLayout();
    }
}

EchoSerial.addPropertyTranslator("Ext20FitLayout", EchoExt20.PropertyTranslator.FitLayout);
EchoSerial.addPropertyTranslator("E2FL", EchoExt20.PropertyTranslator.FitLayout);

EchoExt20.ColumnLayout = Core.extend({
});

EchoExt20.PropertyTranslator.ColumnLayout = {
    toProperty: function(client, propertyElement) {
        return new EchoExt20.ColumnLayout();
    }
}

EchoSerial.addPropertyTranslator("Ext20ColumnLayout", EchoExt20.PropertyTranslator.ColumnLayout);
EchoSerial.addPropertyTranslator("E2CL", EchoExt20.PropertyTranslator.ColumnLayout);

EchoExt20.FormLayout = Core.extend({
});

EchoExt20.PropertyTranslator.FormLayout = {
    toProperty: function(client, propertyElement) {
        return new EchoExt20.FormLayout();
    }
}

EchoSerial.addPropertyTranslator("Ext20FormLayout", EchoExt20.PropertyTranslator.FormLayout);
EchoSerial.addPropertyTranslator("E2FML", EchoExt20.PropertyTranslator.FormLayout);

EchoExt20.TableLayout = Core.extend({
    columns: 0,
    defaultPadding: '',
    
    $construct: function(columns, defaultPadding) {
        this.columns = columns;
        this.defaultPadding = defaultPadding;
    }
    
});

EchoExt20.PropertyTranslator.TableLayout = {
    toProperty: function(client, propertyElement) {
        var columns = propertyElement.getAttribute('c');
        if (columns == null) {
            columns = '1';
        }
        var defaultPadding = propertyElement.getAttribute('dp');
        if (defaultPadding == null) {
            defaultPadding = '';
        }
        return new EchoExt20.TableLayout(
            parseInt(columns),
            defaultPadding
        );
    }
}

EchoSerial.addPropertyTranslator("Ext20TableLayout", EchoExt20.PropertyTranslator.TableLayout);
EchoSerial.addPropertyTranslator("E2TL", EchoExt20.PropertyTranslator.TableLayout);


EchoExt20.PropertyTranslator.SimpleStore = {
    toProperty: function(client, propertyElement) {
        var obj = EchoExt20.PropertyTranslator.toJsObject(client, propertyElement);
        return new Ext.data.SimpleStore({
            fields: obj.fields,
            id: obj.id,
            data: obj.data
        });
    }
};

EchoSerial.addPropertyTranslator("Ext20SimpleStore", EchoExt20.PropertyTranslator.SimpleStore);
EchoSerial.addPropertyTranslator("E2SS", EchoExt20.PropertyTranslator.SimpleStore);

EchoExt20.PropertyTranslator.ColumnModel = {
    toProperty: function(client, propertyElement) {
        var obj = EchoExt20.PropertyTranslator.toJsObject(client, propertyElement);
        return new Ext.grid.ColumnModel(obj.columns);
    }
};

EchoSerial.addPropertyTranslator("Ext20ColumnModel", EchoExt20.PropertyTranslator.ColumnModel);
EchoSerial.addPropertyTranslator("E2CM", EchoExt20.PropertyTranslator.ColumnModel);

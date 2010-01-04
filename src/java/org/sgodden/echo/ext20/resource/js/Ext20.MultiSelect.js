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
 * Component implementation for Ext.multiselect.MultiSelect.
 */
EchoExt20.MultiSelect = Core.extend(EchoExt20.ExtComponent, {
    
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20MultiSelect", this);
        Echo.ComponentFactory.registerType("E2MS", this);
    },

    focusable: true,

    componentType: "Ext20MultiSelect",

    $virtual: {
        doAction: function() {
            this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
        }
    }
});

/**
 * Synchronisation peer for combo box.
 */
EchoExt20.MultiSelectSync = Core.extend(EchoExt20.FormFieldSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20MultiSelect", this);
    },

    /**
     * Whether event firing is suspended (to avoid infinite client-server
     * event processing loops).
     */
    _suspendEvents: false,
    _selectedRows: null,
    
    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
      _renderedComplex = false;
      options["displayField"] = "display";
      options["valueField"] = "value";
      options["fromLegend"] = this.component.get("fromLegend");
      options["toLegend"] = this.component.get("toLegend");

      if (this.component.get("editable") != null) {
          options["editable"] = this.component.get("editable");
          options["disabled"] = !this.component.get("editable");
          options["readOnly"] = !this.component.get("editable");
      }
      if (this.component.get("forceSelection") != null) {
          options["forceSelection"] = this.component.get("forceSelection");
      }
      if (this.component.get("height") != null) {
          options["height"] = this.component.get("height");
      }
      if (this.component.get("width") != null) {
          options["width"] = this.component.get("width");
      }
      if (this.component.get("allowBlank") != null) {
          options['allowBlank'] = this.component.get("allowBlank");
          options["plugins"] = [Ext.ux.MandatoryField];
      }      
      if (this.component.get("model") != null) {
          var store = this.component.get("model");
            
          options.dataFields = store.fields;
          if (this.component.get("complex")) {
              this._selectedRows = this.component.get("selection").split(",");
              var toData = [[]];
              var fromData = [[]];
              var fromIndex = 0;
              var toIndex = 0;
                for( var z=0 ; z<store.data.length; z++ ) {
                    found = false;
                  for( var x=0; x<this._selectedRows.length; x++ ) {
                        if (this._selectedRows[x].toString() == store.data[z][1].toString()) {
                              toData[toIndex++] = store.data[z];
                            found = true;
                          break;
                        }
                    }
                    if (!found) {
                        fromData[fromIndex++] = store.data[z];
                  }
              }
              options.fromData = fromData;
              options.toData = toData;
          }
          else {
              options.data = store.data
          }
      }
        
      var extComponent = this.newExtComponentInstance(options);

      if( this.component.get("complex") ) {
                    
          if(!_renderedComplex) {
              this._setSelectionComplex
              _renderedComplex = true;
          }
                    
          extComponent.on(
            "change",
            this._handleSelectEventComplex,
            this);
      }
      else{
          extComponent.on(
            "change",
            this._handleSelectEventSimple,
            this);
      }

      extComponent.on(
            "render",
            this._setSelection,
            this);

            
      return extComponent;
    },
       
    /**
     * Callled by super.createExtComponent to actually create the 
     * ext component of the correct type.
     */
    newExtComponentInstance: function(options) {
        if( this.component.get("complex") ) {
            return new Ext.ux.ItemSelector(options);
        }
        else{
            return new Ext.ux.Multiselect(options);
        }
    },

    renderUpdate: function(update) {
        if (this.component.get("editable") != null) {
            this.extComponent.setDisabled(!this.component.get("editable"));
        }
        EchoExt20.FormFieldSync.prototype.renderUpdate.call(this, update);
        if(_renderedComplex) {
            var selectionString = this.component.get("selection");
            if(selectionString == "") {
                this.extComponent.reset();
            }
            else if(selectionString) {
                this._handleSelectEventComplex(this, selectionString);
            }
        }
        else {
            this._handleSelectEventSimple(this, this.component.get("selection"));
        }
    },
    
    _handleSelectEventSimple: function(ms, val) {
        if (!this._suspendEvents) {
            this._selectedRows = {};
            
            var rows = val.split(",");
            
            for (var x=0; x<rows.length; x++) {
                this._selectedRows[rows[x]] = true;
            }
            
            this._updateRowSelection();            
            this._setSelection();
            this.component.doAction();
        }
    },
    
    _handleSelectEventComplex: function(ms, val) {
        if (!this._suspendEvents) {
            this._selectedRows = {};
            var rows = val.split(",");
            
            for (var x=0; x<rows.length; x++) {
                    if(rows[x]!="") {
                    this._selectedRows[rows[x]] = true;
                    }
                    else{
                        delete this._selectedRows[rows[x]];
                    }
            }
            this._updateRowSelection();   
            this._setSelectionComplex();
            this.component.doAction();
        }
    },

    _setSelection: function() {
        var selectionString = this.component.get("selection");
        if (selectionString) {
            var rows = selectionString.split(",");
            this.extComponent.setValue(rows);
            this._selectedRows = rows;
        }
    },
    
    _setSelectionComplex: function() {
        var selectionString = this.component.get("selection");
        if (selectionString) {
            var rows = selectionString.split(",");
            this.extComponent.setValue(rows);
            this._selectedRows = rows;
        }
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
    },
    
    renderFocus: function() {
        if( this.component.get("complex") ) {
            this.extComponent.fromMultiselect.focus.defer(100, this.extComponent.fromMultiselect);
        }
        else{
            this.extComponent.focus.defer(100, this.extComponent);
        }
    }

});
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
EchoExt20.ComponentGridPanel = Core.extend(EchoExt20.ExtComponent, {
    
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20ComponentGridPanel", this);
        Echo.ComponentFactory.registerType("E2CGP", this);
    },

    componentType: "Ext20ComponentGridPanel",
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
 * TODO - see Ext20.GridPanel.js
 */
EchoExt20.ComponentGridPanelSync = Core.extend(EchoExt20.GridPanelSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20ComponentGridPanel", this);
    },
    
    _renderColumn: function(value, metadata, record, rowIndex, colIndex, store) {
        var renderedValue = '<div id="' + this.component.renderId + '' + rowIndex + ':' + (colIndex - 1) + '"></div>';
        return renderedValue;
    },

    _handleOnRender: function() {
        this._handleServerSelections();
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
        this._renderGridContents.defer(5, this);
    },
    
    updateCompleted: function(update) {
        if (update.hasAddedChildren())
            this._renderGridContents.defer(5, this);
    },
    
    _renderGridContents: function() {
        if (this.extComponent.getStore().getCount() == 0)
            return;
        
        // if the contents haven't been rendered yet, defer again
        if (document.getElementById(this.component.renderId + '0:0') == null) {
            this._renderGridContents.defer(5, this);
        }
        
        Echo.Render._disposedComponents = {};
        var componentIndex = 0;
        for (var row = 0; row < this.extComponent.getStore().getCount(); row++) {
            for (var col = 0; col < this.extComponent.getColumnModel().getColumnCount(); col++) {
                var cellDiv = document.getElementById(this.component.renderId + '' + row + ':' + col);
                if (cellDiv) {
                    var child = this.component.getComponent(componentIndex++);
                    Echo.Render.renderComponentAdd(this.update, child, cellDiv);
                    child.peer.extComponent.render(cellDiv);
                }
            }
        }
        Echo.Render._disposedComponents = null;
    },
    
    /**
     * Overridden configure column as with components we don't
     * want to use the check column renderer function
     */
    _configureColumn: function(thisCol, options) {
        thisCol.renderer = this._renderColumn.createDelegate(this);
    }
});
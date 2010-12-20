Ext.override(Ext.form.ComboBox, {
    initList : function() {
        if (!this.list) {
            var cls = 'x-combo-list';

            this.list = new Ext.Layer( {
                shadow : this.shadow,
                cls : [ cls, this.listClass ].join(' '),
                constrain : false
            });

            // var lw = this.listWidth || Math.max(this.wrap.getWidth(),
            // this.minListWidth);
        // this.list.setWidth(lw);
        this.list.swallowEvent('mousewheel');
        this.assetHeight = 0;

        if (this.title) {
            this.header = this.list.createChild( {
                cls : cls + '-hd',
                html : this.title
            });
            this.assetHeight += this.header.getHeight();
        }

        this.innerList = this.list.createChild( {
            cls : cls + '-inner'
        });
        this.innerList.on('mouseover', this.onViewOver, this);
        this.innerList.on('mousemove', this.onViewMove, this);
        // this.innerList.setWidth(lw - this.list.getFrameWidth('lr'));
        var lw;
        // Set to width if width has been set to not the default width of 150
        if (this.width && this.width != 150) {
            lw = this.width;
        } else if (this.listWidth) {
            lw = this.listWidth;
        } else {
            lw = Math.max(this.wrap.getWidth(), this.minListWidth);
        }
        if (lw != "auto") {
            this.innerList.dom.style.minWidth = lw + "px";
            this.list.setSize(lw, 0);
        }

        if (this.pageSize) {
            this.footer = this.list.createChild( {
                cls : cls + '-ft'
            });
            this.pageTb = new Ext.PagingToolbar( {
                store : this.store,
                pageSize : this.pageSize,
                renderTo : this.footer
            });
            this.assetHeight += this.footer.getHeight();
        }

        if (!this.tpl) {
            /**
             * @cfg {String/Ext.XTemplate} tpl The template string, or
             *      {@link Ext.XTemplate} instance to use to display each item
             *      in the dropdown list. Use this to create custom UI layouts
             *      for items in the list.
             *      <p>
             *      If you wish to preserve the default visual look of list
             *      items, add the CSS class name
             *
             * <pre>
             * x - combo - list - item
             * </pre>
             *
             * to the template's container element.
             *      <p>
             *      <b>The template must contain one or more substitution
             *      parameters using field names from the Combo's</b>
             *      {@link #store Store}. An example of a custom template would
             *      be adding an
             *
             * <pre>
             * ext: qtip
             * </pre>
             *
             * attribute which might display other fields from the Store.
             *      <p>
             *      The dropdown list is displayed in a DataView. See
             *      {@link Ext.DataView} for details.
             */
            this.tpl = '<tpl for="."><div class="' + cls + '-item">{'
                    + this.displayField + '}</div></tpl>';
            /**
             * @cfg {String} itemSelector <b>This setting is required if a
             *      custom XTemplate has been specified in {@link #tpl} which
             *      assigns a class other than
             *
             * <pre>
             * 'x-combo-list-item'
             * </pre>
             *
             * to dropdown list items</b>. A simple CSS selector (e.g.
             *      div.some-class or span:first-child) that will be used to
             *      determine what nodes the DataView which handles the dropdown
             *      display will be working with.
             */
        }

        /**
         * The {@link Ext.DataView DataView} used to display the ComboBox's
         * options.
         *
         * @type Ext.DataView
         */
        this.view = new Ext.DataView( {
            applyTo : this.innerList,
            tpl : this.tpl,
            autoWidth : true,
            singleSelect : true,
            selectedClass : this.selectedClass,
            itemSelector : this.itemSelector || '.' + cls + '-item'
        });

        this.view.on('click', this.onViewClick, this);

        this.bindStore(this.store, true);

        if (this.resizable || this.listWidth === 'auto') {
            var rect = 0;
            if (Ext.isIE) {
                // list-auto-width-IE class contains one line -->
                // display:inline;
                // This will allow us to get the actual width of innerList div
                this.innerList.addClass('list-auto-width-IE');
                rect = this.list.getBox();
                // Remove this class once we got the width
                this.innerList.removeClass('list-auto-width-IE');
            } else {
                rect = this.list.getBox();
            }
            lw = Math.max(this.wrap.getWidth(), rect.width);
            this.list.setSize(lw, 0);
        } else if (this.listWidth) {
            lw = this.listWidth
                    || Math.max(this.wrap.getWidth(), this.minListWidth);
            this.list.setSize(lw, 0);
        }

        if (this.resizable) {
            this.resizer = new Ext.Resizable(this.list, {
                pinned : true,
                handles : 'se'
            });
            this.resizer.on('resize', function(r, w, h) {
                this.maxHeight = h - this.handleHeight
                        - this.list.getFrameWidth('tb') - this.assetHeight;
                this.listWidth = w;
                this.innerList.setWidth(w - this.list.getFrameWidth('lr'));
                this.restrictHeight();
            }, this);
            this[this.pageSize ? 'footer' : 'innerList'].setStyle(
                    'margin-bottom', this.handleHeight + 'px');
        }
    }
}
});

Ext.override(Ext.form.ComboBox, {
    // ability to delete value with keyboard, lastSelectedText check removed due
    // to causing problems
        doForce : function() {
            if (this.el.dom.value.length > 0) {
                if (this.el.dom.value == this.emptyText) {
                    this.clearValue();
                } else if (!this.multiSelect) {
                    this.applyEmptyText();
                }
            }

        }
    });

/*
 * ================================================================= # This
 * library is free software; you can redistribute it and/or # modify it under
 * the terms of the GNU Lesser General Public # License as published by the Free
 * Software Foundation; either # version 2.1 of the License, or (at your option)
 * any later version. # # This library is distributed in the hope that it will
 * be useful, # but WITHOUT ANY WARRANTY; without even the implied warranty of #
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU # Lesser
 * General Public License for more details. # # You should have received a copy
 * of the GNU Lesser General Public # License along with this library; if not,
 * write to the Free Software # Foundation, Inc., 51 Franklin Street, Fifth
 * Floor, Boston, MA 02110-1301 USA # #
 * =================================================================
 */
/**
 * Component implementation for Ext.form.ComboBox.
 */
EchoExt20.ComboBox = Core.extend(EchoExt20.ExtComponent, {

    $load : function() {
        Echo.ComponentFactory.registerType("Ext20ComboBox", this);
        Echo.ComponentFactory.registerType("E2CB", this);
    },

    focusable : true,

    componentType : "Ext20ComboBox",

    $virtual : {
        doAction : function() {
            this.fireEvent( {
                type : "action",
                source : this,
                actionCommand : this.get("actionCommand")
            });
        }
    }
});

/**
 * Synchronisation peer for combo box.
 */
EchoExt20.ComboBoxSync = Core
        .extend(
                EchoExt20.TextFieldSync,
                {

                    $load : function() {
                        Echo.Render.registerPeer("Ext20ComboBox", this);
                    },

                    /**
                     * The ext record object used to add and remove records from
                     * the store.
                     */
                    _record : null,

                    /**
                     * Whether event firing is suspended (to avoid infinite
                     * client-server event processing loops).
                     */
                    _suspendEvents : false,

                    /**
                     * The store used for the ext combo.
                     */
                    _store : null,

                    _lastServerSelection : null,

                    /**
                     * Called by the base class to create the ext component.
                     */
                    createExtComponent : function(update, options) {
                        options["stateful"] = false;
                        options["displayField"] = "display";
                        options["valueField"] = "value";
                        options["iconField"] = "icon";
                        options["triggerAction"] = "all";
                        options["autoWidth"] = this.component.get("autoWidth");
                        options["resizable"] = this.component.get("resizable");

                        if (this.component.get("editable") != null) {
                            options["editable"] = this.component
                                    .get("editable");
                        }
                        if (this.component.get("forceSelection") != null) {
                            options["forceSelection"] = this.component
                                    .get("forceSelection");
                        }
                        if (this.component.get("emptyText") != null) {
                            options['emptyText'] = this.component
                                    .get("emptyText");
                        }
                        if (this.component.get("rawValue") != null) {
                            options['rawValue'] = this.component
                                    .get("rawValue");
                        }
                        /*
                         * Get the model.
                         */
                        if (this.component.get("model") != null) {
                            var model = this.component.get("model");
                            if (model.data.length > 0
                                    && model.data[0].length == 3) {
                                // if (model.fields[2] == "icon") {
                                // // create the constructor of a record object
                                // to parse the model data
                                // this._record = Ext.data.Record.create([
                                // {name:'display', mapping:'display'},
                                // {name:'value', mapping:'value'},
                                // {name:'icon', mapping:'icon'}
                                // ]);
                                // this._store = new Ext.data.SimpleStore({
                                // fields: ["display","value", "icon"],
                                // id: 1
                                // });
                                // options["plugins"] = new
                                // EchoExt20.IconCombo();
                                // } else {
                                // create the constructor of a record object to
                                // parse the model data
                                this._record = Ext.data.Record.create( [ {
                                    name : 'display',
                                    mapping : 'display'
                                }, {
                                    name : 'value',
                                    mapping : 'value'
                                }, {
                                    name : 'popup',
                                    mapping : 'popup'
                                } ]);
                                this._store = new Ext.data.SimpleStore( {
                                    fields : [ "display", "value", "popup" ],
                                    id : 1
                                });
                                options['tpl'] = '<tpl for="."><div class="x-combo-list-item">{popup}</div></tpl>';
                                // }
                            } else {
                                // create the constructor of a record object to
                                // parse the model data
                                this._record = Ext.data.Record.create( [ {
                                    name : 'display',
                                    mapping : 'display'
                                }, {
                                    name : 'value',
                                    mapping : 'value'
                                } ]);
                                this._store = new Ext.data.SimpleStore( {
                                    fields : [ "display", "value" ],
                                    id : 1
                                });
                            }
                            this._updateStore(model);
                            options["store"] = this._store;
                        } else {
                            throw new Error(
                                    "A combo box must have an initial model specified");
                        }

                        if (this.component.get("typeAhead") != null) {
                            options["typeAhead"] = this.component
                                    .get("typeAhead");
                        }
                        if (this.component.get("width") != null) {
                            options["width"] = this.component.get("width");
                        }
                        if (this.component.get("listWidth") != null) {
                            options["listWidth"] = this.component
                                    .get("listWidth");
                        }
                        if (this.component.get("allowBlank") != null) {
                            options["allowBlank"] = this.component
                                    .get("allowBlank");
                            options["plugins"] = [ Ext.ux.MandatoryField ];
                        }
                        options['mode'] = 'local';

                        this._lastServerSelection = this.component
                                .get("selection");

                        // and then call the superclass method
                        var ret = EchoExt20.TextFieldSync.prototype.createExtComponent
                                .call(this, update, options);

                        ret.on("blur", this._handleRawValueChangeEvent, this);

                        ret.on("render", this._setSelection, this);

                        ret.on("render", this._setSelectorWidth, this);

                        ret.on("select", this._handleValueChangeEvent, this);
                        ret.on("select", this._handleSelectEvent, this);
                        ret.on("expand", this._handleExpandEvent, this);
                        ret.on("collapse", this._handleCollapseEvent, this);
                        ret.on("focus", this._handleFocus, this);
                        return ret;
                    },

                    _getComponentValue : function() {
                        return this.component.get('selection');
                    },

                    _getExtComponentValue : function() {
                        var record = null;
                        var v = this.extComponent.getValue();
                        var recordIndex = this.extComponent.valueField;
                        if (this.extComponent.store.getCount() > 0) {
                            this.extComponent.store.each(function(r) {
                                if (r.data[recordIndex] == v) {
                                    record = r;
                                }
                            });
                        }
                        if (record != null)
                            return this.extComponent.store.indexOf(record);
                        else
                            return -1;
                    },

                    /**
                     * Ensures the application is notified that this component
                     * has been focused
                     */
                    _handleFocus : function() {
                        var focusedComponent = this.component.application
                                .getFocusedComponent();
                        if (!(focusedComponent == this.component)) {
                            this.component.application
                                    .setFocusedComponent(this.component);
                        }
                    },

                    /**
                     * Update the component's value from the value in the ext
                     * text field.
                     */
                    _handleRawValueChangeEvent : function() {
                        var localValue = this.extComponent.getRawValue();
                        var remoteValue = this.component.get("rawValue");
                        if (typeof remoteValue == 'undefined') {
                            remoteValue = '';
                        }
                        if (localValue == remoteValue)
                            return;
                        this.component.set("rawValue", this.extComponent
                                .getRawValue());
                        this.component.set("selection", -1);
                        for ( var i = 0; i < this._store.getCount(); i++) {
                            if (this._store.getAt(i).get("display") == this.extComponent
                                    .getRawValue()) {
                                this.component.set("selection", this._store
                                        .getAt(i).get("value"));
                            }
                        }
                        if (this.component.get("selection") != -1
                                && this.component.get("selection") == this._lastServerSelection)
                            return;
                        this.component.doAction();
                    },

                    /**
                     * Handles the collapse event which fires a select event if
                     * the selected value is not null.
                     */
                    _handleCollapseEvent : function() {
                        if (this.extComponent.value == null) {
                            this._handleSelectEvent();
                        }
                    },

                    /**
                     * Handles the expand event by requesting the component to
                     * fire its action event.
                     */
                    _handleExpandEvent : function() {
                    },

                    /**
                     * Handles the select event by requesting the component to
                     * fire its action event.
                     */
                    _handleSelectEvent : function(combo, record, index) {
                        if (!this._suspendEvents) {
                            if (typeof index != "undefined") {
                                this.component.set("selection", record
                                        .get("value"));
                                this.component.set("rawValue",
                                        this.extComponent.getRawValue());
                                this.component.doAction();
                                combo.focus(true, true);
                            }
                        }
                    },

                    /**
                     * Called by super.createExtComponent to actually create the
                     * ext component of the correct type.
                     */
                    newExtComponentInstance : function(options) {
                        return new Ext.form.ComboBox(options);
                    },

                    renderUpdate : function(update) {
                        EchoExt20.TextFieldSync.prototype.renderUpdate.call(
                                this, update);
                        this._suspendEvents = true;
                        if (update.getUpdatedProperty("model")) {
                            this._updateStore(this.component.get("model"))
                        }
                        this._lastServerSelection = this.component
                                .get("selection");
                        this._doSetSelection(false);

                        if (this.component.get("rawValue")) {
                            this.extComponent.setRawValue(this.component
                                    .get("rawValue"));
                        }

                        this.extComponent.clearInvalid();
                        if (this.component.get("isValid") != null
                                && !(this.component.get("isValid"))) {
                            this.extComponent.markInvalid(this.component
                                    .get("invalidText"));
                        }
                        this._suspendEvents = false;
                    },

                    _setSelection : function() {
                        this._doSetSelection.defer(50, this, [ true ]);
                    },

                    /**
                     * Set the width of the combo box selector field as opposed
                     * to the width of the drop down list of values to select
                     * which is dealt with by the listWidth property
                     */
                    _setSelectorWidth : function() {
                        if (this.component.get("width")) {
                            var textFieldWidth = this.component.get("width")
                                    + "px";
                            this.extComponent.getEl().dom.style.minWidth = textFieldWidth;
                        }
                    },

                    _doSetSelection : function(resetInvalid) {
                        if (this.component.get("selection") > -1) {
                            this.extComponent.setValue(this.component
                                    .get("selection"));
                        } else {
                            if (this.component.get("rawValue")) {
                                this.extComponent.setRawValue(this.component
                                        .get("rawValue"));
                            } else {
                                if (this.extComponent.rendered) {
                                    this.extComponent.clearValue();
                                }
                                this.extComponent.value = null;
                            }
                        }

                        if (resetInvalid) {
                            this.extComponent.clearInvalid();
                            if (this.component.get("isValid") === false) {
                                this.extComponent.markInvalid(this.component
                                        .get("invalidText"));
                            }
                        }
                    },

                    _updateStore : function(model) {
                        this._store.removeAll();
                        for ( var i = 0; i < model.data.length; i++) {
                            var row = model.data[i];
                            if (row.length == 2) {
                                var newRecord = new this._record( {
                                    display : row[0],
                                    value : row[1]
                                });
                                this._store.add(newRecord);
                            } else {
                                // if (model.fields[2] == "icon") {
                    // var newRecord = new this._record({
                    // display: row[0],
                    // value: row[1],
                    // icon: row[2]
                    // });
                    // } else {
                    var newRecord = new this._record( {
                        display : row[0],
                        value : row[1],
                        popup : row[2]
                    });
                    // }
                    this._store.add(newRecord);
                }
            }
        }

                });

/**
 * EchoExt20.IconCombo plugin for Ext.form.Combobox
 *
 * @author Ing. Jozef Sakalos
 * @date January 7, 2008
 *
 * @class EchoExt20.IconCombo
 * @extends Ext.util.Observable
 */
EchoExt20.IconCombo = function(config) {
    Ext.apply(this, config);
};

// plugin code
Ext
        .extend(
                EchoExt20.IconCombo,
                Ext.util.Observable,
                {
                    init : function(combo) {
                        Ext
                                .apply(
                                        combo,
                                        {
                                            tpl : '<tpl for=".">'
                                                    + '<div class="x-combo-list-item">'
                                                    + '<img src="{'
                                                    + combo.iconField + '}"/>'
                                                    + '{' + combo.displayField
                                                    + '}' + '</div></tpl>',

                                            onRender : combo.onRender
                                                    .createSequence(function(
                                                            ct, position) {
                                                        // adjust styles
                                                        this.wrap
                                                                .applyStyles( {
                                                                    position : 'relative'
                                                                });
                                                        // this.el.addClass('ux-icon-combo-input');

                                                        // add div for icon
                                                        this.icon = Ext.DomHelper
                                                                .insertFirst(
                                                                        this.el
                                                                                .up('div.x-form-field-wrap'),
                                                                        {
                                                                            tag : 'div',
                                                                            style : 'display: inline; vertical-align: middle'
                                                                        });

                                                        this.iconImg = Ext.DomHelper
                                                                .append(
                                                                        this.icon,
                                                                        "<img src=\"resources/ext/images/slate/s.gif\" style=\"position: relative; right: 3px;\"/>");
                                                    }), // end of function
                                                        // onRender

                                            setIconCls : function() {
                                                var rec = this.store.query(
                                                        this.valueField,
                                                        this.getValue())
                                                        .itemAt(0);
                                                if (rec) {
                                                    this.iconImg.src = rec
                                                            .get(this.iconField);
                                                }
                                            }, // end of function setIconCls

                                            setValue : combo.setValue
                                                    .createSequence(function(
                                                            value) {
                                                        this.setIconCls();
                                                    })
                                        });
                    } // end of function init
                }); // end of extend

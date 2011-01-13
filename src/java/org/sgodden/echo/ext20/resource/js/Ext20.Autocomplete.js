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
 * Component implementation for Ext.form.Combobox with remote data loading.
 */
EchoExt20.AutocompleteCombo = Core.extend(EchoExt20.ExtComponent, {

    $load: function() {
        Echo.ComponentFactory.registerType("Ext20AutocompleteCombo", this);
        Echo.ComponentFactory.registerType("E2ACP", this);
    },

    focusable: true,

    componentType: "Ext20AutocompleteCombo"
});

/**
 * Synchronisation peer for Ext.form.Combobox with remote data loading.
 */
EchoExt20.AutocompleteComboSync = Core.extend(EchoExt20.FormFieldSync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20AutocompleteCombo", this);
    },

    $virtual: {
        /**
         * Overridable method to actually create the correct type of
         * component.
         */
        newExtComponentInstance: function(options) {
            if (this.component.get("autoCreate")) {
                eval('options.autoCreate = {' + this.component.get("autoCreate") + '};');
            }
            return new Ext.form.ComboBox(options);
        },

        _getComponentValue: function() {
            return this.component.get('value');
        },

        _getExtComponentValue: function() {
            return this.extComponent.getValue();
        }
    },

    /**
     * Called by the base class to create the ext component.
     */
    createExtComponent: function(update, options) {
        options.stateful = false;

        if (this.component.get('value') != null) {
            options.value = this.component.get("value");
        }
        if ( !(this.component.isEnabled()) ) {
            options.disabled = true;
        }
        if (this.component.get("width")) {
            options.width = this.component.get("width");
        }
        if (this.component.get("minChars")) {
            options.minChars = this.component.get("minChars");
        }
        if (this.component.get("hideTrigger") != null && typeof this.component.get("hideTrigger") != undefined) {
            options.hideTrigger = this.component.get("hideTrigger");
        }
        if (this.component.get("allowBlank") != null && typeof this.component.get("allowBlank") != undefined) {
            options.allowBlank = this.component.get("allowBlank");
        }
        if (this.component.get("blankText")) {
            options.blankText = this.component.get("blankText");
        }
        if (this.component.get("forceSelection") != null && typeof this.component.get("forceSelection") != undefined) {
            options.forceSelection = this.component.get("forceSelection");
        }
        if (this.component.get("listCssClass")) {
            options.listClass = this.component.get("listCssClass");
        }
        if (this.component.get("listWidth")) {
            options.listWidth = this.component.get("listWidth");
        }
        if (this.component.get("loadingText")) {
            options.width = this.component.get("loadingText");
        }
        if (this.component.get("listMaxHeight")) {
            options.maxHeight = this.component.get("listMaxHeight");
        }
        if (this.component.get("minListWidth")) {
            options.minListWidth = this.component.get("minListWidth");
        }
        if (this.component.get("listPageSize")) {
            options.pageSize = this.component.get("listPageSize");
        }
        if (this.component.get("queryDelay")) {
            options.queryDelay = this.component.get("queryDelay");
        }
        if (this.component.get("listTitle")) {
            options.title = this.component.get("listTitle");
        }
        if (this.component.get("triggerClass")) {
            options.triggerClass = this.component.get("triggerClass");
        }

        var serverStore = this.component.get('remoteModel');

        var autoProxy = new Ext.data.HttpProxy({url: serverStore});

        var autoRecord = Ext.data.Record.create([
            {name: 'id',    type: 'string'},
            {name: 'popup', type: 'string'},
            {name: 'value', type: 'string'}
        ]);

        var autoReader = new Ext.data.JsonReader({
            root: 			"data",
            totalProperty:	"size"
        }, autoRecord);

        var autoStore = new Ext.data.Store({
            proxy: autoProxy,
            reader: autoReader
        });


        options.store = autoStore;

        options.displayField='value';
        options['tpl'] = '<tpl for="."><div class="x-combo-list-item">{popup}</div></tpl>';
        options.typeAhead=true;

        options.selectOnFocus = true;

        var extComponent = this.newExtComponentInstance(options);

        extComponent.on(
            "render",
            this._doOnRender,
            this);
        extComponent.on(
                "change",
                this._handleChange,
                this);

        return extComponent;
    },

    _doOnRender: function() {
        this.extComponent.setValue(this.component.get("value"));
    },

    _handleChange: function() {
        this.component.set("value", this.extComponent.getValue());
    },

    /**
     * Handles a server update of the field value.
     */
    renderUpdate: function(update){
        EchoExt20.ExtComponentSync.prototype.renderUpdate.call(this, update);

        if ( !(this.component.isEnabled()) ) {
            this.extComponent.setDisabled(true);
        } else {
            this.extComponent.setDisabled(false);
        }
        this.extComponent.setValue(this.component.get("value"));

        // TODO - handle the remote store changing
    }

});
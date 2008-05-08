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
EchoExt20.DateField = Core.extend(EchoExt20.ExtComponent, {
    
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20DateField", this);
        Echo.ComponentFactory.registerType("E2DF", this);
    },

    focusable: true,
    
    componentType: "Ext20DateField"
    
});

EchoExt20.DateFieldSync = Core.extend(EchoExt20.ExtComponentSync, {
    
    $load: function() {
        Echo.Render.registerPeer("Ext20DateField", this);
    },
    
    _handleBlurEventRef: null,
    
    $construct: function() {
        this._handleBlurEventRef = Core.method(this, this._handleBlurEvent);
    },
    
    createExtComponent: function(update, options) {
        var date = this.component.get("date");
        options['value'] = date;
        
        var dateFormat = this.component.get("dateFormat");
        if (dateFormat == null) {
            throw new Error("property 'dateFormat' must be specified on date fields");
        }
        options['format'] = dateFormat;

        options['fieldLabel'] = this.component.get("fieldLabel");
        if (this.component.get("allowBlank") != null) {
            options['allowBlank'] = this.component.get("allowBlank");
        }
        if ( !(this.component.isEnabled()) ) {
            options['disabled'] = true;
        }
        
        var extComponent = new Ext.form.DateField(options);
        extComponent.on('valid', this._handleBlurEventRef);
        extComponent.on('invalid', this._handleBlurEventRef);
        
        return extComponent;
    },
    
    _handleBlurEvent: function() {
		var value = this.extComponent.getValue();
		if (value == "") {
			value = "null"; // prevent NPE in echo date peer
		}
        this.component.set("date", value);
    },
    
    renderDisplay: function(update) {
        this.extComponent.setValue(this.component.get("date"));
    }
    
});

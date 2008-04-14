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
EchoExt20.HtmlEditor = Core.extend(EchoExt20.ExtComponent, {
	
    $load: function() {
        EchoApp.ComponentFactory.registerType("Ext20HtmlEditor", this);
        EchoApp.ComponentFactory.registerType("E2HE", this);
    },

    focusable: true,

    componentType: "Ext20HtmlEditor"
	
});

EchoExt20.HtmlEditorSync = Core.extend(EchoExt20.ExtComponentSync, {

    $load: function() {
        EchoRender.registerPeer("Ext20HtmlEditor", this);
    },
    
    _handleSyncEventRef: null,
    
    $construct: function() {
    	this._handleSyncEventRef = Core.method(this, this._handleSyncEvent);
    },
    
    createExtComponent: function(update, options) {
    	this._text = this.component.get("text");
    	options['value'] = this._text;
    	options['title'] = 'Bogus title'; // FIXME - implement title
    
    	var extComponent = new Ext.form.HtmlEditor(options);
    	extComponent.on('sync', this._handleSyncEventRef);
    	
    	return extComponent;
    },
    
    _handleSyncEvent: function(htmlEditor, html) {
    	this.component.set("text", html);
    },
    
    renderUpdate: function(update){
        EchoExt20.ExtComponentSync.prototype.renderUpdate.call(this, update);
		this.extComponent.setValue(this.component.get("text"));
    }

});
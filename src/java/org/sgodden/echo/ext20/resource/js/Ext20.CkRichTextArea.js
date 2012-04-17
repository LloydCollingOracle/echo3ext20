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
 * Component implementation for Ext.form.RichTextArea.
 */
EchoExt20.CkRichTextArea = Core.extend(Aar.Ckeditor, {

    $load: function() {
        Echo.ComponentFactory.registerType("Ext20CkRichTextArea", this);
        Echo.ComponentFactory.registerType("E2CKRTA", this);
    },

    componentType: "Ext20CkRichTextArea"
});

/**
 * Synchronisation peer for text area.
 */
EchoExt20.CkRichTextAreaSync = Core.extend(Aar.Ckeditor.Sync, {

    $load: function() {
        Echo.Render.registerPeer("Ext20CkRichTextArea", this);
    },

    renderAdd : function(update, parentElement) {
        // invoke super class method
        Aar.Ckeditor.Sync.prototype.renderAdd.call(this, update, parentElement);
        
        // if not allowed blank show a "*" next to the rich text area at the top right
        var allowBlank = this.component.get("allowBlank");
        if (false == allowBlank){
            Ext.fly(this._div).addClass("ux-mandatory");
            Ext.DomHelper.append(Ext.fly(this._div), {
                tag: "label",
                cls: " ux-mandatory",
                htmlFor: Ext.fly(this._div),
                style: "float:right;position:absolute;",
                html: "*"
            });
        }
    }

});

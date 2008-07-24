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
 * Abstract synchronisation peer for all ext form components.
 */
EchoExt20.FormFieldSync = Core.extend(EchoExt20.ExtComponentSync, {

    $abstract: true,
    
    _handleOnRender: function() {
        if (this.component.parent.get("layout") != null) {
            if (this.component.parent.get("layout") instanceof EchoExt20.TableLayout) {
	            /*
	             * For some reason, ext decides to set IE TDs to relative -1px which
	             * screws up the positioning of form inputs in relation to their trigger
	             * buttons.  If the parent is not a form layout, then we need to remove
	             * this.
	             */
	            this.extComponent.getEl().dom.style.top = "0px";
            }
        }
        else if (this.extComponent.width == NaN || this.extComponent.width == 0){
           /*
            * The parent layout was null, so the width of this form wrapper
            * will not have been set.  Set it to the sum of the two children's
            * clientWidth.
            */
            alert("Setting to auto (not currently supported!)");
            this.extComponent.getEl().parent().dom.style.width = "auto";
        }
    },
    
    renderDisplay: function(update) {
        EchoExt20.ExtComponentSync.prototype.renderDisplay.call(this, update);
        this.extComponent.on("render", this._handleOnRender, this);
    }

});
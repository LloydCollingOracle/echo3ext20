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
package org.sgodden.echo.ext20;

/**
 * An ext button.
 * 
 * @author goddens
 *
 */
public class Button 
        extends AbstractButton {

    private static final long serialVersionUID = 20080102L;
    
    /**
     * When set to true, tells the client side rendering engine that this
     * button should go into the container's button bar, rather than being
     * added directly to its layout.
     */
    public static final String ADD_TO_BUTTON_BAR_PROPERTY = "addToButtonBar";
    
    private Menu menu;
    
    {
        setAddToButtonBar(false);
    }

    /**
     * Creates a button with the specific text and icon.
     * @param text the text.
     * @param iconUrl the url of the icon.
     */
    public Button(String text, String iconUrl) {
        super(text, iconUrl);
    }

    /**
     * Creates a button with the specific text and icon.
     * @param text the text.
     */
    public Button(String text) {
        super(text);
    }

    /**
     * Creates a new button with no text or icon.
     */
    public Button() {
        super();
    }
    
    /**
     * See {@link #ADD_TO_BUTTON_BAR_PROPERTY}.
     * @param addToButtonBar whether to add this button to the button bar rather than the main panel body.
     */
    void setAddToButtonBar(boolean addToButtonBar) {
        setProperty(ADD_TO_BUTTON_BAR_PROPERTY, addToButtonBar);
    }
    
    /**
     * Sets a menu to be opened when this button is clicked.
     * @param menu the menu to open.
     */        
    public void setMenu(Menu menu) {
        if (this.menu != null) {
            remove(this.menu);
        }
        
        add(menu);
        
        this.menu = menu;
    }
}
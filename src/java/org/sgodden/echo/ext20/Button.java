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

import nextapp.echo.app.ImageReference;

/**
 * An ext button.
 * 
 * @author goddens
 * 
 */
public class Button extends AbstractButton {

    private static final long serialVersionUID = 20080102L;

    /**
     * When set to true, tells the client side rendering engine that this button
     * should go into the container's button bar, rather than being added
     * directly to its layout.
     */
    public static final String ADD_TO_BUTTON_BAR_PROPERTY = "addToButtonBar";

    /**
     * The group that this toggle button is a member of
     */
    public static final String TOGGLE_GROUP = "toggleGroup";

    /**
     * When set to true, tells the client to show the menu assoicated with the
     * button.
     */
    public static final String HOVER_MENU = "hoverMenu";
    /**
     * set the button's template
     */
    public static final String TEMPLATE_PROPERTY = "template";
    
    private Menu menu;

    {
        setAddToButtonBar(false);
    }

    public Button() {
        super();
    }

    public Button(String text, ImageReference icon) {
        super(text, icon);
    }

    public Button(String text) {
        super(text);
    }

    /**
     * See {@link #ADD_TO_BUTTON_BAR_PROPERTY}.
     * 
     * @param addToButtonBar
     *            whether to add this button to the button bar rather than the
     *            main panel body.
     */
    void setAddToButtonBar(boolean addToButtonBar) {
        set(ADD_TO_BUTTON_BAR_PROPERTY, addToButtonBar);
    }

    /**
     * See {@link #ADD_TO_BUTTON_BAR_PROPERTY}.
     * 
     * @return whether this button is added to the button bar rather than the
     *         main panel body.
     */
    boolean isAddToButtonBar() {
        return (Boolean) get(ADD_TO_BUTTON_BAR_PROPERTY);
    }

    /**
     * Sets a menu to be opened when this button is clicked.
     * 
     * @param menu
     *            the menu to open.
     */
    public void setMenu(Menu menu) {
        if (this.menu != null) {
            remove(this.menu);
        }

        add(menu);

        this.menu = menu;
    }

    /**
     * Set the toggle group which this button belongs to. This also sets the
     * extjs property enableToggle to true for this button.
     */
    public void setToggleGroup(String toggleGroup) {
        set(TOGGLE_GROUP, toggleGroup);
    }

    public void setTemplate( String template) {
    	set( TEMPLATE_PROPERTY, template);
    }
    
    public static String getToggleGroup() {
        return (String) TOGGLE_GROUP;
    }

    /**
     * Set this button to display the menu.
     * 
     * @param true to show the menu on mouse over.
     */
    public void isHoverMenu(boolean hoverMenu) {
        set(HOVER_MENU, hoverMenu);
    }
}
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

import nextapp.echo.app.Component;

import org.sgodden.echo.ext20.layout.Layout;

/**
 * A lightweight container.
 * 
 * @author goddens
 *
 */
public class Container extends ExtComponent {

    private static final long serialVersionUID = 20090324L;
    
    /**
     * Padding for the overall container.
     * <p>
     * Type: String in CSS padding specification style.
     * </p>
     */
    public static final String PROPERTY_PADDING = "padding";
    /**
     * The height of the panel, in pixels.
     * <p>
     * Type: Integer.
     * </p>
     */
    public static final String PROPERTY_HEIGHT = "height";
    
    /**
     * The width of the panel, in pixels.
     * <p>
     * Type: Integer.
     * </p>
     */
    public static final String PROPERTY_WIDTH = "width";
    
    public static final String PROPERTY_LAYOUT = "layout";

    public static final String PROPERTY_HTML = "html";
    

    /**
     * Creates a new empty panel with the default container layout.
     */
    public Container() {
        this(null);
    }

    /**
     * Creates a new panel.
     * @param layout the layout for the panel.
     */
    public Container(Layout layout) {
        super();
        set(PROPERTY_LAYOUT, layout);
    }
    
    /**
     * Sets the padding of the overall panel, in CSS style.
     * @param padding the padding of the overall panel, in CSS style.
     */
    public void setPadding(String padding) {
        set(PROPERTY_PADDING, padding);
    }
    
    /**
     * Sets the height of the panel in pixels.
     * @param pixels the height of the panel in pixels.
     */
    public void setHeight(int pixels) {
        set(PROPERTY_HEIGHT, pixels);
    }
    
    /**
     * Sets the width of the panel in pixels.
     * @param pixels the width of the panel in pixels.
     */
    public void setWidth(int pixels) {
        set(PROPERTY_WIDTH, pixels);
    }
    
    /**
     * Returns the layout in use by this panel.
     * @return the layout in use by this panel.
     */
    public Layout getLayout() {
        return (Layout) get(PROPERTY_LAYOUT);
    }
    
    /**
     * Sets the layout on the panel.
     * @param layout the layout to use in the panel.
     */
    public void setLayout(Layout layout) {
        set(PROPERTY_LAYOUT, layout);
    }

    /**
     * Specified some arbitrary HTML to show as the panel's contents.
     * <p/>
     * Note that this cannot be used in conjunction with the {@link #add(Component)} method.
     * @param html the HTML to show as the panel's contents.
     */
    public void setHtml(String html) {
        set(PROPERTY_HTML, html);
    }

}
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

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Component;
import nextapp.echo.app.LayoutData;

import org.sgodden.echo.ext20.layout.BorderLayout;
import org.sgodden.echo.ext20.layout.BorderLayoutData;
import org.sgodden.echo.ext20.layout.Layout;

/**
 * <p>A lightweight container.</p>
 * <p>Containers may be used simply to contain other components, 
 * or to show a piece of html text - not both.</p>
 * <p>Containers may not be used as the first component in a ContentPane
 * as they will not size correctly.</p>
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

    @Override
    public void validate() {
        super.validate();
        if (getLayout() instanceof BorderLayout) {
            boolean hasCenter = false;
            List<Integer> missingLayouts = new ArrayList<Integer>();
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponent(i).isVisible()) {
                    LayoutData ld = getComponent(i).getLayoutData();
                    if (ld == null || !(ld instanceof BorderLayoutData)) {
                        missingLayouts.add(Integer.valueOf(i));
                    } else if (ld != null && ld instanceof BorderLayoutData) {
                        hasCenter = hasCenter || "c".equals(((BorderLayoutData)ld).getRegion());
                    }
                }
            }
            if (!hasCenter || missingLayouts.size() > 0) {
                String error = "";
                if (!hasCenter) {
                    error += "No visible child with CENTER layout";
                }
                for(Integer i : missingLayouts) {
                    if (error.length() > 0) {
                        error += "\n";
                    }
                    error += "Child at index " + i + " is missing BorderLayoutData";
                }
                throw new IllegalStateException(error);
            }
        }
    }
}
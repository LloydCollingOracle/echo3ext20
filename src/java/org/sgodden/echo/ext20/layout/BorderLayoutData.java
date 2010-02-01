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
package org.sgodden.echo.ext20.layout;

import nextapp.echo.app.LayoutData;

/**
 * Used internally to communicate border layout data
 * between server and client.
 * 
 * @author goddens
 *
 */
@SuppressWarnings({"serial"})
public class BorderLayoutData
        implements LayoutData {

    private int region;
    private boolean split;

    /**
     * Creates a new border layout data object, with the region, as specified
     * by one of the constants defined in the {@link BorderLayout} class.
     * @param region
     */
    public BorderLayoutData(int region) {
        this.region = region;
    }

    public String getRegion() {
        switch (region) {
            case BorderLayout.NORTH:
                return "n";
            case BorderLayout.EAST:
                return "e";
            case BorderLayout.SOUTH:
                return "s";
            case BorderLayout.WEST:
                return "w";
            case BorderLayout.CENTER:
                return "c";
            default:
                throw new IllegalArgumentException("Unknown border region: " + region);
        }
    }

    public boolean getSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }
}

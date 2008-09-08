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
 * A multi-line text area.
 * 
 * @author sgodden
 */
@SuppressWarnings({"serial"})
public class TextArea 
        extends TextField {
	
	public static final String PROPERTY_HEIGHT = "height";
	public static final String PROPERTY_WIDTH = "width";

	/**
	 * Creates a new text area.
	 * @param text the text contents.
	 * @param fieldLabel the field label to be used in a form.
	 */
    public TextArea(String text, String fieldLabel) {
        super(text, fieldLabel);
    }

    /**
     * Creates a new text area.
     * @param text the text contents.
     */
    public TextArea(String text) {
        super(text);
    }

    /**
     * Creates a new empty text area.
     */
    public TextArea() {
        super();
    }
    
    /**
     * Sets the width and height of the text area.
     * @param width
     * @param height
     */
    public void setSize(int width, int height){
    	set(PROPERTY_WIDTH, width);
    	set(PROPERTY_HEIGHT, height);
    }

}

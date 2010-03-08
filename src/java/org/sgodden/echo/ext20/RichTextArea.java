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
 * Rich text are text area.
 * 
 * @author pkysela
 */
@SuppressWarnings({"serial"})
public class RichTextArea 
        extends echopoint.Fckeditor {
    
    public static final String ALLOW_BLANK_PROPERTY = "allowBlank";

    /**
     * Creates a new empty text area.
     */
    public RichTextArea() {
        super();
    }
    
    /**
     * Gets allow blank property
     */
    public boolean getAllowBlank(){
        return (Boolean) get(ALLOW_BLANK_PROPERTY);
        
    }
    
    /**
     * Sets whether a blank value is allowed.
     * 
     * @param allowBlank
     *            whether a blank value is allowed.
     */
    public void setAllowBlank(boolean allowBlank) {
        set(ALLOW_BLANK_PROPERTY, allowBlank);
    }
}

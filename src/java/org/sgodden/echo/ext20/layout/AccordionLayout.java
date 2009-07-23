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


/**
 * A layout that contains multiple panels in an expandable accordion 
 * style such that only one panel can be open at any given time.
 * @author sgodden
 */
@SuppressWarnings({"serial"})
public class AccordionLayout 
        implements Layout {

	/**
	 * True to hide the contained panels' collapse/expand toggle buttons, false to display them (defaults to false).
	 */
	private boolean hideCollapseTool = false;

	public AccordionLayout() {}
	
	public AccordionLayout(boolean hideCollapseTool) {
		this.hideCollapseTool = hideCollapseTool;
	}

	public boolean isHideCollapseTool() {
		return hideCollapseTool;
	}

	public void setHideCollapseTool(boolean hideCollapseTool) {
		this.hideCollapseTool = hideCollapseTool;
	}
	
}

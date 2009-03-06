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

import java.util.EventListener;

import nextapp.echo.app.Component;

/**
 * Listener to be notified before a tab is removed from a tabbed pane, allowing
 * the listener to veto the removal,
 * @author Lloyd Colling
 */
public interface TabClosingListener 
    extends EventListener {

    /**
     * Method called when a tab is going to be closed. Returning
     * false will veto the removal of the tab.
     * @param pane
     * @param tabContents
     * @param tabIndex
     * @return true to remove the tab, false to veto the removal
     */
    public boolean tabClosing(TabbedPane pane, Component tabContents, int tabIndex);
}

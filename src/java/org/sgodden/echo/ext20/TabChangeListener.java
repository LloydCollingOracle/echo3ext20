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
import java.util.EventObject;

/**
 * Listener to be notified after the active tab index changes on a tabbed pane.
 * @author sgodden
 */
public interface TabChangeListener 
    extends EventListener {

    /**
     * Notification that a tab pane wishes to change the displayed tab.
     * Returning false will veto the tab change.
     * @param e
     * @return
     */
    public boolean tabChanged(TabChangeEvent e);
    
    /**
     * Event generated when changing tab
     * @author Lloyd Colling
     *
     */
    public static class TabChangeEvent extends EventObject {
        
        private static final long serialVersionUID = 20091019;
        
        int oldIndex;
        int newIndex;
        
        public TabChangeEvent(Object source, int oldTab, int newTab) {
            super(source);
            this.oldIndex = oldTab;
            this.newIndex = newTab;
        }

        public int getOldIndex() {
            return oldIndex;
        }

        public int getNewIndex() {
            return newIndex;
        }
    }
}

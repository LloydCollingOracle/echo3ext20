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
package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.DeferredUiCreate;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.FitLayout;

/**
 * A panel which toggles between a list of users, and an edit panel
 * for the individual user.
 * @author sgodden
 */
public class UserPanel 
        extends Panel
        implements DeferredUiCreate {
    
    private static final transient Log log = LogFactory.getLog(UserPanel.class);
    
    public UserPanel(){
        super(new FitLayout());
        setTitle("Users");
    }

    /*
     * (non-Javadoc)
     * @see org.sgodden.echo.ext20.DeferredUiCreate#createUI()
     */
	public void createUI() {
   		switchToListPanel();
	}
    
    /**
     * Switches to the list panel.
     */
    private void switchToListPanel() {
        if (getComponentCount() > 0) {
            remove(0);
        }
        
        final UserListPanel listPanel = new UserListPanel();
        add(listPanel);
        
        listPanel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                switchToEditPanel(listPanel.getSelectedRow());
            }
        });
    }
    
    /**
     * Switched to the edit panel.
     * 
     * @param data the data representing the user to edit.
     */
    private void switchToEditPanel(Object[] data) {
        remove(0);
        
        UserEditPanel editPanel = new UserEditPanel(data);
        add(editPanel);
        
        editPanel.addCancelListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                switchToListPanel();
            }
        });
        
        editPanel.addSaveListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                switchToListPanel();
            }
        });
    }
    
}

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

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Component;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.ActionListenable;
import org.sgodden.echo.ext20.DeferredUiCreate;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.FitLayout;

/**
 * A panel which toggles between a list of users, and an edit panel
 * for the individual user.
 * @author sgodden
 */
@SuppressWarnings({"serial","unchecked"})
public class UserPanel 
        extends Panel
        implements DeferredUiCreate {
    
    /**
     * The list panel.
     */
    private UserListPanel listPanel;
    
    public UserPanel(boolean setTitle){
        super(new FitLayout());
        if (setTitle) {
        	setTitle("Grid and form");
        }
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
        
        if (listPanel == null) {
            listPanel = new UserListPanel();
            listPanel.getGridPanel().addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    switchToEditPanel(listPanel.getSelectedRow());
                    listPanel.getGridPanel().getSelectionModel().clearSelection();
                }
            });
        }
        
        add(listPanel);
        
    }
    
    /**
     * Switched to the edit panel.
     * 
     * @param data the data representing the user to edit.
     */
    private void switchToEditPanel(Object[] data) {
        remove(0);
        
        AppInstance app = (AppInstance) ApplicationInstance.getActive();
        Component editPanel = (Component) app.getGroovyObjectInstance(
                "org.sgodden.echo.ext20.testapp.groovy.UserEditPanel");
        add(editPanel);
        try {
           Method m = editPanel.getClass().getMethod("setData", Object[].class);
           m.invoke(editPanel, new Object[]{listPanel.getSelectedRow()});
        } catch (Exception ex) {
            throw new Error(ex);
        }
        
        ((ActionListenable)editPanel).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                switchToListPanel();
            }
        });
    }
    
}

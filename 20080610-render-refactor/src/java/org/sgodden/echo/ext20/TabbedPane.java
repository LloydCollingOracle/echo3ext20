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
import java.util.HashSet;
import java.util.Set;

import nextapp.echo.app.Component;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A tabbed pane.
 * <p/>
 * Note that if a child is added to a tabbed pane, and it implemnets the
 * {@link DeferredUiCreate} interface, then the tabbed pane will wait until
 * that tab is selected before calling the {@link DeferredUiCreate#createUI()}
 * method.
 * <p/>
 * This allows for more performant user interfaces, that render their contents
 * lazily when required.
 * 
 * @author goddens
 *
 */
public class TabbedPane extends ExtComponent {

	private static final long serialVersionUID = 20080102L;
	
	private static final transient Log log = LogFactory.getLog(TabbedPane.class);
	
	public static final String ACTIVE_TAB_INDEX_PROPERTY = "activeTabIndex";

	public static final String ACTIVE_TAB_CHANGE_EVENT = "activeTabChangeEvent";
	public static final String TAB_CHANGE_LISTENERS_CHANGED_PROPERTY = "tabChangeListeners";
	
	private Set<String> initialisedChildIds = new HashSet<String>();
	
	/**
	 * Creates a new tabbed pane.
	 */
	public TabbedPane() {
		super();
		setActiveTabIndex(0);
		
		addTabChangeListener(new TabChangeListener(){
			public void actionPerformed(ActionEvent arg0) {
				// get the component at the new index
				Component c = getComponent(getActiveTabIndex());
				// if it's a deferred ui component, tell it to create it now
				if (c instanceof DeferredUiCreate) {
					// ensure we don't ask children to create their UI more than once.
					if (!(initialisedChildIds.contains(c.getRenderId()))) {
						((DeferredUiCreate)c).createUI();
						initialisedChildIds.add(c.getRenderId());
					}
				}
			}
		});
	}
	
	/**
	 * Sets the index of the active tab.
	 * @param tabIndex the index of the active tab.
	 */
	public void setActiveTabIndex(int tabIndex) {
		setProperty(ACTIVE_TAB_INDEX_PROPERTY, tabIndex);
		fireTabChangeEvent();
	}

	/**
	 * Returns the index of the active tab.
	 * @return the index of the active tab.
	 */
	public int getActiveTabIndex() {
		return (Integer) getProperty(ACTIVE_TAB_INDEX_PROPERTY);
	}
	

    @Override
    public void processInput(String inputName, Object inputValue) {
        if (ACTIVE_TAB_INDEX_PROPERTY.equals(inputName)) {
            setActiveTabIndex((Integer)inputValue);
        }
        /* 
         * we don't handle the change event because listeners are
         * already notified in setActiveIndex.
         */
        
    }
    
    
    /**
     * Returns whether any <code>TabChangeListener</code>s are registered.
     * 
     * @return true if any action listeners are registered
     */
    public boolean hasTabChangeListeners() {
        return getEventListenerList().getListenerCount(TabChangeListener.class) != 0;
    }

    
    /**
     * Adds a <code>TabChangeListener</code> to the pane.
     * <code>TabChangeListener</code>s will be invoked after the
     * active tab index changes.
     * 
     * @param l the <code>TabChangeListener</code> to add.
     */
    public void addTabChangeListener(TabChangeListener l) {
        getEventListenerList().addListener(TabChangeListener.class, l);
        firePropertyChange(TAB_CHANGE_LISTENERS_CHANGED_PROPERTY, null, l);
    }

    
    /**
     * Fires an action event to all listeners.
     */
    private void fireTabChangeEvent() {
        if (!hasEventListenerList()) {
            return;
        }
        EventListener[] listeners = getEventListenerList().getListeners(TabChangeListener.class);
        ActionEvent e = null;
        for (int i = 0; i < listeners.length; ++i) {
            if (e == null) {
                e = new ActionEvent(this, null);
            }
            ((ActionListener) listeners[i]).actionPerformed(e);
        }
    }
    
    @Override
    public void remove(Component c) {
    	super.remove(c);
    	initialisedChildIds.remove(c.getRenderId());
    }

}

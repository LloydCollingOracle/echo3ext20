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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nextapp.echo.app.Component;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.Layout;
import org.sgodden.echo.ext20.layout.TableLayout;

/**
 * The main container component for Ext.
 * 
 * @author goddens
 *
 */
public class Panel extends ExtComponent {

    private static final long serialVersionUID = 20080102L;
    
    private static final transient Log log = LogFactory.getLog(Panel.class);
    
    public static final String PROPERTY_BORDER = "border";
    public static final String PROPERTY_PADDING = "padding";
    public static final String PROPERTY_TITLE = "title";
    
    public static final String COLLAPSIBLE_PROPERTY = "collapsible";
    public static final String HEIGHT_PROPERTY = "height";
    public static final String HTML_PROPERTY = "html";
    public static final String LAYOUT_PROPERTY = "layout";
    public static final String TRANSPARENT_PROPERTY = "transparent";
    public static final String SPLIT_PROPERTY = "split";
    public static final String WIDTH_PROPERTY = "width";
    public static final String TOOL_IDS_PROPERTY = "toolIds";
    
    public static final String INPUT_KEY_PRESSED = "keyPressed";
    public static final String INPUT_KEYPRESS_ACTION = "keyPress";
    public static final String INPUT_TOOLCLICK_ACTION = "toolClick";
    public static final String INPUT_TOOLID_CLICKED = "toolIdClicked";
    public static final String KEYPRESS_LISTENERS_CHANGED_PROPERTY = "keyPressListeners";
    public static final String REGISTERED_KEY_PRESSES_PROPERTY="registeredKeyPresses";
    public static final String TOOLCLICK_LISTENERS_CHANGED_PROPERTY = "toolclickListeners";
    
    private Map<String, Set<ActionListener>> keyPressListeners;
    private Map<Tool, Set<ActionListener>> toolListeners;
    
    private Toolbar topToolbar;
    private Toolbar bottomToolbar;
    
    private String keyPressed;
    private String toolIdClicked;
    
    private int nonButtonBarChildCount = 0;

    /**
     * Creates a new empty panel with the default container layout.
     */
    public Panel() {
        this(null, null);
    }
    
    /**
     * Creates a new panel.
     * @param title the title for the panel.
     */
    public Panel(String title) {
        this(null, title);
    }

    /**
     * Creates a new panel.
     * @param layout the layout for the panel.
     */
    public Panel(Layout layout) {
        this(layout, null);
    }

    /**
     * Creates a new panel.
     * @param layout the layout for the panel.
     * @param title the title for the panel.
     */
    public Panel(Layout layout, String title) {
        super();
        setBorder(false);
        setProperty(LAYOUT_PROPERTY, layout);
        setTitle(title);
    }

    /**
     * Sets the title of the panel.
     * @param title the title of the panel.
     */
    public void setTitle(String title) {
        setProperty(PROPERTY_TITLE, title);
    }
    
    /**
     * Returns the panel's title.
     * @return the panel's title.
     */
    public String getTitle() {
        return (String) getProperty(PROPERTY_TITLE);
    }

    /**
     * Sets the padding, in CSS style.
     * @param padding
     */
    public void setPadding(String padding) {
        setProperty(PROPERTY_PADDING, padding);
    }

    /**
     * Sets whether the panel's border should be shown.
     * <p/>
     * (Ext panels have a default 1 pixel border).
     * @param border
     */
    public void setBorder(Boolean border) {
        setProperty(PROPERTY_BORDER, border);
    }
    
    /**
     * Sets the height of the panel in pixels.
     * @param pixels the height of the panel in pixels.
     */
    public void setHeight(int pixels) {
        setProperty(HEIGHT_PROPERTY, pixels);
    }
    
    /**
     * Specified some arbitrary HTML to show as the panel's contents.
     * <p/>
     * Note that this cannot be used in conjunction with the {@link #add(Component)} method.
     * @param html the HTML to show as the panel's contents.
     */
    public void setHtml(String html) {
        setProperty(HTML_PROPERTY, html);
    }
    
    /**
     * Sets the width of the panel in pixels.
     * @param pixels the width of the panel in pixels.
     */
    public void setWidth(int pixels) {
        setProperty(WIDTH_PROPERTY, pixels);
    }
    
    /**
     * Sets whether the panel should be collapsible.
     * @param collapsible whether the panel should be collapsible.
     */
    public void setCollapsible(boolean collapsible) {
        setProperty(COLLAPSIBLE_PROPERTY, collapsible);
    }
    
    /**
     * Returns the layout in use by this panel.
     * @return the layout in use by this panel.
     */
    public Layout getLayout() {
        return (Layout) getProperty(LAYOUT_PROPERTY);
    }
    
    /**
     * Sets the layout on the panel.
     * @param layout the layout to use in the panel.
     */
    public void setLayout(Layout layout) {
    	setProperty(LAYOUT_PROPERTY, layout);
    }
    
    /**
     * If set to <code>true</code>, renders the panel's background
     * as transparent.
     * @param transparent whether the panel's background should be transparent.
     */
    public void setTransparent(boolean transparent) {
    	setProperty(TRANSPARENT_PROPERTY, transparent);
    }
    
    /**
     * Adds a component to this container.
     * <p/>
     * Note that if this panel's layout is an instance of
     * {@link org.sgodden.echo.ext20.layout.TableLayout}, and the component
     * being added is not a panel, then it will be wrapped in a panel.  This
     * is necessary in order for table layout padding defaults to take effect.
     * @param comp the component to add.
     */
    @Override
    public void add(Component comp) {
        if (getLayout() instanceof TableLayout && !(comp instanceof Panel)) {
            Panel panel = new Panel();
            panel.setBorder(false);
            super.add(panel);
            panel.addNoWrapCheck(comp);
        }
        else if (getLayout() instanceof FitLayout
                && nonButtonBarChildCount > 0) {
           throw new Error("Only one non-button component is allowed for fit layout");
        }
        else {
            super.add(comp);
            nonButtonBarChildCount++;
        }
    }
    
    /**
     * Removes the specified component from the container.
     * <p/>
     * Contains special processing in case this panel has a table layout
     * and the component was wrapped in a panel.
     * @param comp
     */
    @Override
    public void remove(Component comp) {
        
        if (getLayout() instanceof TableLayout && !(comp instanceof Panel)) {
            // Loop through the children.  If we find the passed component
            // inside the child, remove the child.
            for (Component child : getComponents()) {
                if (child instanceof Panel) {
                    if (child.getComponent(0) == comp) {
                        super.remove(child);
                    }
                }
            }
        }
        else if ( !(comp instanceof Button)
                || (comp instanceof Button && !((Button)comp).isAddToButtonBar()) ) {
            nonButtonBarChildCount--;
            super.remove(comp);
        }
        else {
            super.remove(comp);
        }
    }
    
    /**
     * See {@link Component#removeAll()}.
     */
    @Override
    public void removeAll() {
        nonButtonBarChildCount = 0;
        super.removeAll();
    }
    
    /**
     * Adds a component without checking whether it needs to be
     * wrapped in an outer panel.
     * @param comp
     */
    private void addNoWrapCheck(Component comp) {
        super.add(comp);
    }
    
    /**
     * Adds a button to the panel's button bar, rather than directly to its
     * layout.
     * @param button
     */
    public void addButton(Button button) {
        button.setAddToButtonBar(true);
        super.add(button);
    }
    
    /**
     * Adds a listener to repond to the click event of the specified tool,
     * also adding the tool to the panel if it does not already exist.
     * <p/>
     * Tools are small (8x8) icons added to the title bar of a panel.
     * <p/>
     * See the documentation for Ext.Panel.tools for further information.
     * 
     * @param tool the tool to add.
     * @param listener the listener to respond to the click event.
     */
    public void addToolListener(Tool tool, ActionListener listener) {
    	if (toolListeners == null) {
    		toolListeners = new HashMap<Tool, Set<ActionListener>>();
    	}
    	
    	Set<ActionListener> listeners = toolListeners.get(tool);
    	if (listeners == null) {
    		listeners = new HashSet<ActionListener>();
    		toolListeners.put(tool, listeners);
    	}
    	
    	listeners.add(listener);
    	
    	StringBuffer sb = new StringBuffer();
    	for (Tool theTool : toolListeners.keySet()) {
    		if (sb.length() > 0) {
    			sb.append(',');
    		}
    		sb.append(theTool.toString().toLowerCase());
    	}
    	setProperty(TOOL_IDS_PROPERTY, sb.toString());
    	firePropertyChange(TOOLCLICK_LISTENERS_CHANGED_PROPERTY, null, listener);
    }
    
    /**
     * Sets the panel's top tool bar.
     * @param toolbar the tool bar to put at the top of the panel.
     */
    public void setToolbar(Toolbar toolbar) {
        if (topToolbar != null) {
            remove(topToolbar);
        }
        
        toolbar.setPosition(Toolbar.Position.TOP);
        add(toolbar);
        
        this.topToolbar = toolbar;
    }
    
    /**
     * Sets the panel's bottom tool bar.
     * @param toolbar the tool bar to put at the bottom of the panel.
     */
    public void setBottomToolbar(Toolbar toolbar) {
        if (bottomToolbar != null) {
            remove(bottomToolbar);
        }
        
        toolbar.setPosition(Toolbar.Position.BOTTOM);
        add(toolbar);
        
        this.bottomToolbar = toolbar;
    }
    
    @Override
    public void processInput(String inputName, Object inputValue) {
        if (INPUT_KEY_PRESSED.equals(inputName)) {
            this.keyPressed = (String) inputValue;
        }
        else if (INPUT_TOOLID_CLICKED.equals(inputName)) {
            this.toolIdClicked = (String) inputValue;
        }
        else if (INPUT_KEYPRESS_ACTION.equals(inputName)) {
            fireKeyEvent();
        }
        else if (INPUT_TOOLCLICK_ACTION.equals(inputName)) {
            fireToolClickEvent();
        }
        else {
            super.processInput(inputName, inputValue);
        }
    }
        
    /**
     * Adds a listener to be notified when the passed key is pressed and this
     * component either has the focus, or is the ancestor of the focused
     * component.
     * <p/>
     * TODO - document the keyPress format.
     * 
     * @param keyPress the key press the listener wants to be notified of.
     * @param listener the listener to notify.
     */
    public void addKeyPressListener(String keyPress, ActionListener listener) {
        if (keyPressListeners == null) {
            keyPressListeners = new HashMap<String, Set<ActionListener>>();
        }
        
        Set<ActionListener> listeners = keyPressListeners.get(keyPress);
        if (listeners == null) {
            listeners = new HashSet<ActionListener>();
            keyPressListeners.put(keyPress, listeners);
        }
        
        listeners.add(listener);
        updateRegisteredKeyPresses();
        firePropertyChange(KEYPRESS_LISTENERS_CHANGED_PROPERTY, null, listener);
    }
    
    private void updateRegisteredKeyPresses(){
        StringBuffer sb = new StringBuffer();
        
        for (String keyPress : keyPressListeners.keySet()) {
            if (sb.length() > 0) {
                sb.append(':');
            }
            sb.append(keyPress);
        }
        
        setProperty(REGISTERED_KEY_PRESSES_PROPERTY, sb.toString());
    }

    private void fireKeyEvent() {
        if (!(keyPressListeners.containsKey(keyPressed))) {
            throw new IllegalStateException("Key press fired when no listener registered: " + keyPressed);
        }
        
        ActionEvent e = new ActionEvent(this, keyPressed);
        
        for (ActionListener listener : keyPressListeners.get(keyPressed)) {
            listener.actionPerformed(e);
        }
    }
    
    /**
     * Returns whether any key press listeners are registered on this component.
     * @return true if there are any listeners registered, false if not.
     */
    public boolean hasKeyPressListeners() {
        return (keyPressListeners != null && keyPressListeners.size() > 0);
    }
    
    /**
     * Returns whether any key tool listeners are registered on this component.
     * @return true if there are any listeners registered, false if not.
     */
    public boolean hasToolListeners() {
    	return (toolListeners != null && toolListeners.size() > 0);
    }

    /**
     * Fires tool click events to registered listeners.
     */
    private void fireToolClickEvent() {
    	Tool tool = Tool.valueOf(toolIdClicked.toUpperCase());
        if (!(toolListeners.containsKey(tool))) {
            throw new IllegalStateException("Too click event fired when no listener registered: " + toolIdClicked);
        }
        
        ActionEvent e = new ActionEvent(this, toolIdClicked);
        
        for (ActionListener listener : toolListeners.get(tool)) {
            listener.actionPerformed(e);
        }
    }

}
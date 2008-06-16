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
import java.util.HashMap;
import java.util.Map;

import nextapp.echo.app.Component;
import nextapp.echo.app.event.ActionEvent;

/**
 * Abstract superclass for all ext components.
 * 
 * @author sgodden
 */
@SuppressWarnings("unchecked")
public abstract class ExtComponent 
        extends Component {
    
    //private static final transient Log log = LogFactory.getLog(ExtComponent.class);
    
    public static final String ALIGNTO_PROPERTY = "alignTo";
    
    public static final String INPUT_BEFORE_RENDER_PROPERTY = "beforeRender";
    public static final String BEFORE_RENDER_LISTENERS_CHANGED_PROPERTY = "beforeRenderListeners";
    
    private static final String CSS_STYLES_PROPERTY = "cssStyles";

    private Component alignToOtherComponent;
    private Alignment sourceAlignment;
    private Alignment targetAlignment;
    private int xAlignOffset;
    private int yAlignOffset;
    
    /**
     * BROKEN - Aligns this component to another component, anchoring the source
     * alignment to the target alignment.
     * <p/>
     * For example, to position the bottom right corner of comp1 to the
     * bottom left corner of comp2, with a horizontal
     * shift of 5 pixels to the left, you would do the following:
     * <pre class="code">comp1.alignTo(
     *     comp2,
     *     Alignment.BOTTOM_RIGHT,
     *     Alignment.BOTTOM_LEFT,
     *     -5,
     *     0
     *   );</pre>
     * @param other the component relative to which this component should
     * be positioned.
     * @param sourceAlignment the alignment (edge) of the source component.
     * @param targetAlignment the alignment (edge) of the target component.
     */
    public void alignTo(
            Component other, 
            Alignment sourceAlignment,
            Alignment targetAlignment,
            int xOffset,
            int yOffset) {
        
        if (other == null) {
            throw new NullPointerException("other component");
        }
        
        if (sourceAlignment == null) {
            throw new NullPointerException("source alignment");            
        }
        
        if (targetAlignment == null) {
            throw new NullPointerException("target alignment");
        }
        
        this.alignToOtherComponent = other;
        this.sourceAlignment = sourceAlignment;
        this.targetAlignment = targetAlignment;
        this.xAlignOffset = xOffset;
        this.yAlignOffset = yOffset;
    }
    
    /**
     * For internal use - returns the alignTo property as a string suitable
     * for transmission to the client.
     * @return the alignTo property.
     */
    public String getAlignToPropertyString() {
        
        String ret = null;
        
        if (alignToOtherComponent != null) {
            StringBuffer sb = new StringBuffer(alignToOtherComponent.getRenderId());
            sb.append(',');
            sb.append(convertToExtAlignment(sourceAlignment));
            sb.append('-');
            sb.append(convertToExtAlignment(targetAlignment));
            sb.append(',');
            sb.append(String.valueOf(xAlignOffset));
            sb.append(':');
            sb.append(String.valueOf(yAlignOffset));

            ret = sb.toString();
        }
        
        return ret;
    }
    
    private String convertToExtAlignment(Alignment alignment) {
        switch(alignment) {
            case TOP_LEFT:
                return "tl";
            case TOP:
                return "t";
            case TOP_RIGHT:
                return "tr";
            case BOTTOM_LEFT:
                return "bl";
            case BOTTOM:
                return "b";
            case BOTTOM_RIGHT:
                return "br";
            case LEFT:
                return "l";
            case RIGHT:
                return "r";
            default:
                throw new IllegalArgumentException("Unrecognised alignment constant");
        }
    }
    
        
    /**
     * Returns whether any listeners for the beforeRender event are registered.
     * 
     * @return true if any before render listeners are registered
     */
    public boolean hasBeforeRenderListeners() {
        return getEventListenerList().getListenerCount(BeforeRenderListener.class) != 0;
    }
    
        
    /**
     * Adds a listener to be notified before a component is rendered.
     * 
     * @param l the <code>BeforeRenderListener</code> to add.
     */
    public void addBeforeRenderListener(BeforeRenderListener l) {
        getEventListenerList().addListener(BeforeRenderListener.class, l);
        // Notification of action listener changes is provided due to 
        // existence of hasActionListeners() method. 
        firePropertyChange(BEFORE_RENDER_LISTENERS_CHANGED_PROPERTY, null, l);
    }
    
    /**
     * @see nextapp.echo.app.Component#processInput(java.lang.String, java.lang.Object)
     */
    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (INPUT_BEFORE_RENDER_PROPERTY.equals(inputName)) {
            fireBeforeRenderEvent();
        }
    }
    
        
    /**
     * Fires an action event to all listeners.
     */
    private void fireBeforeRenderEvent() {
        if (!hasEventListenerList()) {
            return;
        }
        EventListener[] listeners = getEventListenerList().getListeners(BeforeRenderListener.class);
        ActionEvent e = null;
        for (int i = 0; i < listeners.length; ++i) {
            if (e == null) {
                e = new ActionEvent(this, null);
            }
            ((BeforeRenderListener) listeners[i]).actionPerformed(e);
        }
    }
    
    /**
     * Sets the specified css property to the specified value.
     * @param propertyName the css property name.
     * @param value the css property value.
     */
    public void setCssStyle(String propertyName, String value) {
        Map cssStyleMap = (Map) getProperty(CSS_STYLES_PROPERTY);
        if (cssStyleMap == null) {
            cssStyleMap = new HashMap ();
            setProperty(CSS_STYLES_PROPERTY, cssStyleMap);
        }
        cssStyleMap.put(propertyName, value);
    }
    
}
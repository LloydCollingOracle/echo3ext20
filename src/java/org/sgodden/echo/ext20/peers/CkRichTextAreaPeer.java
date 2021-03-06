package org.sgodden.echo.ext20.peers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.sgodden.echo.ext20.CkRichTextArea;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ComponentSynchronizePeer;
import nextapp.echo.webcontainer.SynchronizePeerFactory;

@SuppressWarnings({"unchecked"})
public class CkRichTextAreaPeer extends echopoint.CkeditorPeer {
    
	/** Set of <code>Component</code> <code>Class</code>es whose peers must be initialized in order for the component to render. */
    private Set requiredComponentClasses;
    
    /**
     * Adds a required component class that must also be initialized before this
     * component can be rendered.
     * 
     * @param componentClass
     */
    public void addRequiredComponentClass(Class componentClass) {
        if (requiredComponentClasses == null) {
            requiredComponentClasses = new HashSet();
        }
        requiredComponentClasses.add(componentClass);
    }
    
    /**
     * Invokes the init() methods of peers of required component classes (added via
     * addRequiredComponentClass()). 
     * Implementations requiring initialization should override this method and invoke the
     * super-implementation out of convention (even if they do not presently have any
     * dependencies on other components). 
     * 
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(Context, Component)
     */
    public void init(Context context, Component component) {
        if (requiredComponentClasses == null) {
            return;
        }
        Iterator componentClassIt = requiredComponentClasses.iterator();
        while (componentClassIt.hasNext()) {
            Class componentClass = (Class) componentClassIt.next();
            ComponentSynchronizePeer syncPeer = SynchronizePeerFactory.getPeerForComponent(componentClass);
            syncPeer.init(context, component);
        }
    }
    
	public Class getComponentClass() {
        return CkRichTextArea.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2CKRTA" : "Ext20CkRichTextArea";
    }

    @Override
    public Object getOutputProperty(Context context, Component component,
            String propertyName, int propertyIndex) {
        Object ret = null;

        if (propertyName.equals(CkRichTextArea.PROPERTY_TEXT)) {
            CkRichTextArea richTextArea = (CkRichTextArea) component;
            String value = richTextArea.getText();
            if (value == null) {
                return "";
            }
            else {
                return value;
            }
        }
        else {
            ret = super.getOutputProperty(context, component, propertyName,
                    propertyIndex);
        }

        return ret;
    }
}

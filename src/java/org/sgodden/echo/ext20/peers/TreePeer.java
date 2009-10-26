package org.sgodden.echo.ext20.peers;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import org.sgodden.echo.ext20.Tree;
import org.sgodden.echo.ext20.peers.tree.TreeStructure;

public class TreePeer extends
        nextapp.echo.extras.webcontainer.sync.component.TreePeer {
    
    private static final Service TREE_SERVICE = JavaScriptService.forResources("Ext20Tree",  
            new String[]{ "org/sgodden/echo/ext20/resource/js/Ext20.Tree.Application.js",
                    "org/sgodden/echo/ext20/resource/js/Ext20.Tree.Serial.js",
                    "org/sgodden/echo/ext20/resource/js/Ext20.Tree.Sync.js" });

    static {
        WebContainerServlet.getServiceRegistry().add(TREE_SERVICE);
    }
    
    public TreePeer() {
        super();
        addOutputProperty(Tree.HAS_BORDER_PROPERTY);
        addOutputProperty(Tree.PROPERTY_SHOW_CHECKBOXES);
        addOutputProperty(Tree.PROPERTY_SHOW_ROOT_NODE);
        addOutputProperty(Tree.PROPERTY_SELECT_ON_CONTEXT);
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType(boolean)
     */
    public String getClientComponentType(boolean shortType) {
        return "Ext20Tree";
    }
    
    public Class getComponentClass() {
        return Tree.class;
    }
    
    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(nextapp.echo.app.util.Context, Component)
     */
    public void init(Context context, Component component) {
        super.init(context, component);
        ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
        serverMessage.addLibrary(TREE_SERVICE.getId());
    }
    
    public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
        Tree tree = (Tree) component;
        if ("treeStructure".equals(propertyName)) {
            return new TreeStructure(tree);
        } else {
            return super.getOutputProperty(context, component, propertyName, propertyIndex);
        }
    }
}

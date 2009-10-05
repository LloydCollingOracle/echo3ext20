package org.sgodden.echo.ext20.peers.tree;

import nextapp.echo.app.Window;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.util.Context;
import nextapp.echo.extras.app.Tree;
import nextapp.echo.extras.app.serial.property.SerialPropertyPeerConstants;
import nextapp.echo.extras.webcontainer.sync.component.tree.TreeRenderState;

import org.w3c.dom.Element;

public class TreeStructurePeer extends
        nextapp.echo.extras.webcontainer.sync.component.tree.TreeStructurePeer {

    /**
     * @see nextapp.echo.app.serial.SerialPropertyPeer#toXml(nextapp.echo.app.util.Context, 
     *      java.lang.Class, org.w3c.dom.Element, java.lang.Object)
     */
    public void toXml(Context context, Class objectClass, Element propertyElement, Object propertyValue) 
    throws SerialException {
        Tree tree = ((TreeStructure) propertyValue).getTree();
        TreeStructureRenderer renderer = getRenderer(context, objectClass, propertyElement, propertyValue);
        propertyElement.setAttribute("t", "EchoExt20.Serial.Tree.TreeStructure");
        Window containingWindow = tree.getContainingWindow();
        TreeRenderState renderState = (TreeRenderState) containingWindow.getRenderState(tree);
        if (renderState == null) {
            renderState = new TreeRenderState(tree);
            containingWindow.setRenderState(tree, renderState);
        }
        renderer.render(context, renderState);
        renderState.clearChangedPaths();
    }
    
    protected TreeStructureRenderer getRenderer(Context context, Class objectClass, Element propertyElement, Object propertyValue) {
        Tree tree = ((TreeStructure) propertyValue).getTree();
        return new TreeStructureRenderer(propertyElement, tree);
    }
}

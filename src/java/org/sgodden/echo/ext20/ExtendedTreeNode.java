package org.sgodden.echo.ext20;

import nextapp.echo.extras.app.tree.TreeNode;

/**
 * Defines a tree node as being able to decide if it is
 * selectable.
 * @author Lloyd Colling
 *
 */
public interface ExtendedTreeNode extends TreeNode {

    /**
     * Whether this node can be selected
     * @return
     */
    public boolean canBeSelected();
}

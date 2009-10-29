package org.sgodden.echo.ext20;

import nextapp.echo.extras.app.tree.DefaultMutableTreeNode;

public class DefaultExtendedMutableTreeNode extends DefaultMutableTreeNode
        implements ExtendedTreeNode {
    
    private boolean canBeSelected = true;

    public boolean canBeSelected() {
        return canBeSelected;
    }

    public void setCanBeSelected(boolean canBeSelected) {
        this.canBeSelected = canBeSelected;
    }
}

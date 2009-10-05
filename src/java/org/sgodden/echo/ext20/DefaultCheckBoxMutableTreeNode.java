package org.sgodden.echo.ext20;

import nextapp.echo.extras.app.tree.DefaultMutableTreeNode;

/**
 * Tree node that may have a checkbox displayed next to it
 * @author Lloyd Colling
 *
 */
public class DefaultCheckBoxMutableTreeNode extends DefaultMutableTreeNode
        implements CheckboxTreeNode {
    
    Boolean checked;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

}

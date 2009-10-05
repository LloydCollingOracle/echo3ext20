package org.sgodden.echo.ext20;

import nextapp.echo.extras.app.tree.TreeNode;

/**
 * A tree node that may be rendered with a checkbox
 * if it's 'checked' value is set to true or false.
 * @author Lloyd Colling
 *
 */
public interface CheckboxTreeNode extends TreeNode {

    public Boolean getChecked();
    
    public void setChecked(Boolean checked);
}

package org.sgodden.echo.ext20;

import nextapp.echo.app.Component;
import nextapp.echo.app.HttpImageReference;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Label;
import nextapp.echo.extras.app.Tree;
import nextapp.echo.extras.app.tree.TreeCellRenderer;
import nextapp.echo.extras.app.tree.TreePath;

/**
 * A default implementation of {@link TreeCellRenderer}.
 * @author llcolling
 *
 */
public class DefaultTreeCellRenderer implements TreeCellRenderer {
    
    private ImageReference leafIcon;
    private ImageReference folderIcon;
    private ImageReference folderOpenIcon;

    public DefaultTreeCellRenderer() {
        leafIcon = new HttpImageReference("resources/ext/images/default/tree/leaf.gif");
        folderIcon = new HttpImageReference("resources/ext/images/default/tree/folder.gif");
        folderOpenIcon = new HttpImageReference("resources/ext/images/default/tree/folder-open.gif");
    }
    
    /**
     * @see nextapp.echo.extras.app.tree.TreeCellRenderer#getTreeCellRendererComponent(nextapp.echo.extras.app.Tree, nextapp.echo.extras.app.tree.TreePath, java.lang.Object, int, int, boolean)
     */
    public Component getTreeCellRendererComponent(Tree tree, TreePath treePath, Object value, int column, int row, boolean leaf) {
        ImageReference icon = null;
        if (column == 0 && row != -1) {
            if (leaf) {
                icon = leafIcon;
            } else {
                if (tree.isExpanded(treePath)) {
                    icon = folderOpenIcon;
                } else {
                    icon = folderIcon;
                }
            }
        }
        return value == null ? new Label(icon) : new Label(value.toString(), icon);
    }
    
    public void setLeafIcon(ImageReference newValue) {
        this.leafIcon = newValue;
    }
    
    public void setFolderIcon(ImageReference newValue) {
        this.folderIcon = newValue;
    }
    
    public void setFolderOpenIcon(ImageReference newValue) {
        this.folderOpenIcon = newValue;
    }

}

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
package org.sgodden.echo.ext20.testapp;

import java.util.HashMap;
import java.util.Map;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.ChangeEvent;
import nextapp.echo.app.event.ChangeListener;
import nextapp.echo.extras.app.tree.DefaultMutableTreeNode;
import nextapp.echo.extras.app.tree.TreeNodeModel;
import nextapp.echo.extras.app.tree.TreePath;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.Container;
import org.sgodden.echo.ext20.DefaultExtendedMutableTreeNode;
import org.sgodden.echo.ext20.Menu;
import org.sgodden.echo.ext20.MenuItem;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.Tree;
import org.sgodden.echo.ext20.layout.FitLayout;

/**
 * Provides tests for the {@link Tree} component.
 * 
 * @author Lloyd Colling
 * 
 */
@SuppressWarnings({"serial"})
public class TreeTest extends Panel implements ChangeListener, ActionListener {

    private static final transient Log log = LogFactory
            .getLog(TreeTest.class);

    public TreeTest() {
        super(new FitLayout(), "Tree");
        setRenderId("treeTest");
        createUI();
    }

    public void createUI() {
        Container outer = new Panel(new FitLayout());
        add(outer);

        DefaultExtendedMutableTreeNode root = new DefaultExtendedMutableTreeNode();
        DefaultMutableTreeNode foo = new DefaultMutableTreeNode();
        DefaultMutableTreeNode bar = new DefaultMutableTreeNode();
        DefaultMutableTreeNode blah = new DefaultMutableTreeNode();
        
        root.addChild(blah);
        root.addChild(foo);
        foo.addChild(bar);
        
        root.setCanBeSelected(false);
        
        root.setColumnValues(getMap(new String[] {"0", "1"}, new Object[] {"ROOT", "ROOT"}));
        foo.setColumnValues(getMap(new String[] {"0", "1"}, new Object[] {"FOO", "FOO"}));
        bar.setColumnValues(getMap(new String[] {"0", "1"}, new Object[] {"BAR", "BAR"}));
        blah.setColumnValues(getMap(new String[] {"0", "1"}, new Object[] {"BLAH", "BLAH"}));
        
        TreeNodeModel treeModel = new TreeNodeModel(root);
        Tree testTree = new Tree(treeModel);
        testTree.getSelectionModel().addChangeListener(this);
        testTree.addActionListener(this);
        testTree.setShowCheckBoxes(true);
        outer.add(testTree);
        
        Menu treeMenu = new Menu();
        MenuItem mi1 = new MenuItem("Tree Menu Item 1");
        MenuItem mi2 = new MenuItem("Tree Menu Item 2");
        treeMenu.add(mi1);
        treeMenu.add(mi2);
        testTree.setContextMenu(treeMenu);
        
        mi1.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("Select 1");
            }
        });
        mi2.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("Select 2");
            }
        });
        
        TreePath path = new TreePath(new Object[] {root, foo});
        testTree.getSelectionModel().setSelectionPath(path);
    }

    public void stateChanged(ChangeEvent arg0) {
        System.out.println("Tree Selection Changed");
    }

    public void actionPerformed(ActionEvent arg0) {
        System.out.println("Tree action event");
    }
    
    private Map getMap(String[] columns, Object[] values) {
        Map m = new HashMap();
        for (int i = 0; i < values.length; i++) {
            m.put(columns[i], values[i]);
        }
        return m;
    }

}

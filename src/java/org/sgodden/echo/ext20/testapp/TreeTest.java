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

import nextapp.echo.app.Extent;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.ChangeEvent;
import nextapp.echo.app.event.ChangeListener;
import nextapp.echo.extras.app.tree.AbstractTreeModel;
import nextapp.echo.extras.app.tree.TreeModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.DeferredUiCreate;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TabbedPane;
import org.sgodden.echo.ext20.Tree;
import org.sgodden.echo.ext20.layout.BorderLayout;
import org.sgodden.echo.ext20.layout.BorderLayoutData;
import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.TableLayout;

/**
 * Provides tests for the {@link Tree} component.
 * 
 * @author Lloyd Colling
 * 
 */
@SuppressWarnings({"serial"})
public class TreeTest extends Panel implements DeferredUiCreate, ChangeListener, ActionListener {

	private static final transient Log log = LogFactory
			.getLog(TreeTest.class);

	public TreeTest() {
		super(new FitLayout(), "Tree");
		setRenderId("treeTest");
	}

	public void createUI() {
		Panel outer = new Panel(new FitLayout());
		add(outer);


        final Object treeBlah = "BLAH";
		final Object treeFoo = "FOO";
		final Object treeBar = "BAR";
		final Object treeRoot = "ROOT";
		TreeModel treeModel = new AbstractTreeModel() {

			public Object getChild(Object parent, int index) {
				if (parent.equals(treeFoo))
					return treeBar;
				else if (parent.equals(treeRoot)) {
					switch (index) {
					case 0:
						return treeBlah;
					case 1:
						return treeFoo;
					}
				}
				return null;
			}

			public int getChildCount(Object parent) {
				if (parent.equals(treeFoo))
					return 1;
				else if (parent.equals(treeRoot))
					return 2;
				else
					return 0;
			}

			public int getColumnCount() {
				return 2;
			}

			public int getIndexOfChild(Object parent, Object child) {
				if (parent.equals(treeFoo) && child.equals(treeBar))
					return 0;
				else if (parent.equals(treeRoot) && child.equals(treeBlah))
					return 0;
				else if (parent.equals(treeRoot) && child.equals(treeFoo))
					return 1;
				return -1;
			}

			public Object getRoot() {
				return treeRoot;
			}

			public Object getValueAt(Object node, int column) {
				if (treeRoot.equals(node)) {
					return "root";
				} else if (treeBlah.equals(node)) {
					return "blah";
				} else if (treeFoo.equals(node)) {
					return "foo";
				} else if (treeBar.equals(node)) {
					return "bar";
				}
				return null;
			}

			public boolean isLeaf(Object node) {
				return node.equals(treeBlah) || node.equals(treeBar);
			}
		};
        Tree testTree = new Tree(treeModel);
        testTree.getSelectionModel().addChangeListener(this);
        testTree.addActionListener(this);
		outer.add(testTree);
	}

	public void stateChanged(ChangeEvent arg0) {
		System.out.println("Tree Selection Changed");
	}

	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Tree action event");
	}

}

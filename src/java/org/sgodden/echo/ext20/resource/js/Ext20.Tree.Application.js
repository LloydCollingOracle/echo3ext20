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
/**
 * Component implementation for Ext.Tree.
 */
EchoExt20.Tree = Core.extend(EchoExt20.ExtComponent, {
	
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20Tree", this);
        Echo.ComponentFactory.registerType("E2TRE", this);
    },

    componentType: "Ext20Tree",
    
    $virtual: {
        doAction: function() {
            this.fireEvent({type: "action", source: this, actionCommand: this.get("actionCommand")});
        }
    }
	
});

/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
Ext.tree.ColumnTree = Ext.extend(Ext.tree.TreePanel, {
    lines:false,
    borderWidth: Ext.isBorderBox ? 0 : 2, // the combined left/right border for each cell
    cls:'x-column-tree',
    
    onRender : function(){
        Ext.tree.ColumnTree.superclass.onRender.apply(this, arguments);
        this.headers = this.body.createChild(
            {cls:'x-tree-headers'},this.innerCt.dom);

        var cols = this.columns, c;
        var totalWidth = 0;

        for(var i = 0, len = cols.length; i < len; i++){
             c = cols[i];
             totalWidth += c.width;
             var headerDiv = this.headers.createChild({
                 cls:'x-tree-hd ' + (c.cls?c.cls+'-hd':''),
                 cn: {
                     cls:'x-tree-hd-text'
                 },
                 style:'width:'+(c.width-this.borderWidth)+'px'
             });
             if (! (c.columnComponent instanceof EchoExt20.ExtComponent) ) {
                 // we don't renderAdd here - ext does it lazily
                 var wrapper = new EchoExt20.Echo3SyncWrapper(c.update, c.columnComponent);
                 headerDiv.dom.firstChild.appendChild(wrapper.wrappedRootElement);
             } else {
            	 Echo.Render.renderComponentAdd(c.update, c.columnComponent, headerDiv.dom.firstChild);
             }
        }
        this.headers.createChild({cls:'x-clear'});
        // prevent floats from wrapping when clipped
        this.headers.setWidth(totalWidth);
        this.innerCt.setWidth(totalWidth);
    },

	getColumns : function() {
    	return this.columns;
    },
    
    registerAllNodes : function () {
    	var rootNode = this.getRootNode();
    	rootNode.cascade(this.registerNode, this);
    }
});

Ext.tree.ColumnNode = function(attributes)  {
	Ext.tree.ColumnNode.superclass.constructor.call(this, attributes);
	var uiClass = this.attributes.uiProvider || this.defaultUI || Ext.tree.ColumnNodeUI;
	this.childNodesExist = attributes.isLeaf == false;
	this.echoNode = attributes.echoNode;
	/*
     * Read-only. The UI for this node
     * @type TreeNodeUI
     */
    this.ui = new uiClass(this);
};

Ext.extend(Ext.tree.ColumnNode, Ext.tree.TreeNode, {
	isLeaf : function () {
		if (this.childNodesExist != null) {
			if (this.childNodesExist == true)
				return false;
			else
				return true;
		} else {
			return Ext.tree.ColumnNode.superclass.isLeaf.call(this);
		}
	},
	
	hasChildNodes : function() {
		return this.isLeaf() == false;
	},
	
	getEchoNode : function() {
		return this.echoNode;
	}
});

Ext.tree.ColumnNodeUI = Ext.extend(Ext.tree.TreeNodeUI, {
    focus: Ext.emptyFn, // prevent odd scrolling behavior

    renderElements : function(n, a, targetNode, bulkRender){
        this.indentMarkup = n.parentNode ? n.parentNode.ui.getChildIndent() : '';

        var t = n.getOwnerTree();
        var bw = t.borderWidth;
        var c = t.getColumns()[0];

        var buf = [
             '<li class="x-tree-node"><div ext:tree-node-id="',n.id,'" class="x-tree-node-el x-tree-node-leaf ', a.cls,'">',
                '<div class="x-tree-col" style="width:',c.width-bw,'px;">',
                    '<span class="x-tree-node-indent">',this.indentMarkup,"</span>",
                    '<img src="', this.emptyIcon, '" class="x-tree-ec-icon x-tree-elbow">',
                    '<table style="display:inline;" id="tbl',n.id,'"><tr><td id="t',n.id,'">', n.text ? n.text : '',"</td></tr></table>",
                "</div>"];
         for(var i = 1, len = t.getColumns().length; i < len; i++){
             c = t.getColumns()[i];

             buf.push('<div class="x-tree-col ',(c.cls?c.cls:''),'" style="width:',c.width-bw,'px;">',
                        '<div class="x-tree-col-text" id="',n.id,i,'"></div>',
                      "</div>");
         }
         buf.push(
            '<div class="x-clear"></div></div>',
            '<ul class="x-tree-node-ct" style="display:none;"></ul>',
            "</li>");

        if(bulkRender !== true && n.nextSibling && n.nextSibling.ui.getEl()){
            this.wrap = Ext.DomHelper.insertHtml("beforeBegin",
                                n.nextSibling.ui.getEl(), buf.join(""));
        }else{
            this.wrap = Ext.DomHelper.insertHtml("beforeEnd", targetNode, buf.join(""));
        }
        
        var nodeTextSpan = document.getElementById("t" + n.id);
        var textComponent = a['application'].getComponentByRenderId(a.columns[0]);
        if (! (textComponent instanceof EchoExt20.ExtComponent) ) {
            // we don't renderAdd here - ext does it lazily
            var wrapper = new EchoExt20.Echo3SyncWrapper(c.update, textComponent);
            nodeTextSpan.appendChild(wrapper.wrappedRootElement);
        } else {
       	 	Echo.Render.renderComponentAdd(c.update, textComponent, nodeTextSpan);
        }

        for(var i = 1, len = t.getColumns().length; i < len; i++){
        	var thisColDiv = document.getElementById('' + n.id + i);
        	var columnComponent = a['application'].getComponentByRenderId(a.columns[i]);
            if (! (columnComponent instanceof EchoExt20.ExtComponent) ) {
                // we don't renderAdd here - ext does it lazily
                var wrapper = new EchoExt20.Echo3SyncWrapper(c.update, columnComponent);
                thisColDiv.appendChild(wrapper.wrappedRootElement);
            } else {
            	Echo.Render.renderComponentAdd(a['update'], columnComponent, thisColDiv);
            }
        }
        
        var tableEl = Ext.get('tbl' + n.id);
        tableEl.on('click', this.onClick, this);

        this.elNode = this.wrap.childNodes[0];
        this.ctNode = this.wrap.childNodes[1];
        var cs = this.elNode.firstChild.childNodes;
        this.indentNode = cs[0];
        this.ecNode = cs[1];
        this.iconNode = cs[2];
        this.anchor = cs[2];
        this.textNode = cs[2].firstChild;
        
        if (n.isSelected()) {
        	this.addClass("x-tree-selected");
        }
    }
});
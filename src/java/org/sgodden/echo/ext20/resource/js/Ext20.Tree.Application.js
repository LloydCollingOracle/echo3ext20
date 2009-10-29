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
    showCheckBoxes : false,
    
    onRender : function(){
        Ext.tree.ColumnTree.superclass.onRender.apply(this, arguments);
        this.headers = this.body.createChild(
            {cls:'x-tree-headers'},this.innerCt.dom);

        var cols = this.columns, c;
        var totalWidth = 0;
        
        if (this.showCheckBoxes) {
            totalWidth = 17;
            this.headers.createChild({
                 cls:'x-tree-hd',
                 cn: {
                     cls:'x-tree-hd-text'
                 },
                 style:'width:15px'
            });
        }

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
	},
	
	/**
	 * Handles notification that the user changed the
	 * value of the checkbox by syncing the selection
	 */
	checkBoxChanged: function() {
	   if (this.getUI().checkbox.checked) {
	       this.select();
	       this.getUI().checkbox.checked = true;
	   } else {
	       this.unselect();
           this.getUI().checkbox.checked = false;
	   }
	},
	
	/**
	 * Updates the checkbox to match the nodes selection
	 * status
	 */
	updateCheckBox: function(selected) {
	   if (this.getUI() && this.getUI().checkbox) {
	       this.getUI().checkbox.checked = selected;
	   }
	}
});

Ext.tree.ColumnNodeUI = Ext.extend(Ext.tree.TreeNodeUI, {
    focus: Ext.emptyFn, // prevent odd scrolling behavior

    renderElements : function(n, a, targetNode, bulkRender){
        this.indentMarkup = n.parentNode ? n.parentNode.ui.getChildIndent() : '';

        var t = n.getOwnerTree();
        var bw = t.borderWidth;
        var c = t.getColumns()[0];
        
        var cb = n.isSelected();
        var canBeSelected = n.getEchoNode().getCheckable();

        var buf = [
             '<li class="x-tree-node"><div ext:tree-node-id="',n.id,'" class="x-tree-node-el x-tree-node-leaf ', a.cls,'">',
                t.showCheckBoxes && canBeSelected ? ('<div class="x-tree-col" style="width:15px"><input class="x-tree-node-cb" type="checkbox" ' + (cb ? 'checked="checked" /></div>' : '/></div>')) : '',
                t.showCheckBoxes && !canBeSelected? '<div class="x-tree-col" style="width:15px; height: 1px;">&nbsp;</div>' : '',
                '<div class="x-tree-col" style="width:',c.width-bw,'px;" style="vertical-align:middle">',
                    '<span class="x-tree-node-indent">',this.indentMarkup,"</span>",
                    '<img src="', this.emptyIcon, '" class="x-tree-ec-icon x-tree-elbow">',
                    '<span class="x-tree-col-text" ', ' id="tbl',n.id,'"></span>',
                "</div>"];
         for(var i = 1, len = t.getColumns().length; i < len; i++){
             c = t.getColumns()[i];

             buf.push('<div class="x-tree-col ',(c.cls?c.cls:''),'" style="width:',c.width-bw,'px;">',
                        '<div class="x-tree-col-text" ', Ext.isSafari ? 'style="padding: 1px 3px 3px 5px;"' : 'style="padding: 2px 3px 3px 5px;"',' id="',n.id,i,'"></div>',
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
        
        var nodeTextSpan = document.getElementById("tbl" + n.id);
        var textComponent = a['application'].getComponentByRenderId(a.columns[0]);
        if (! (textComponent instanceof EchoExt20.ExtComponent) ) {
            // we don't renderAdd here - ext does it lazily
            var wrapper = new EchoExt20.Echo3SyncWrapper(c.update, textComponent);
            wrapper.wrappedRootElement.style.display='inline';
            nodeTextSpan.appendChild(wrapper.wrappedRootElement);
        } else {
            Echo.Render.renderComponentAdd(c.update, textComponent, nodeTextSpan);
            textComponent.peer.extComponent.render(nodeTextSpan);
        }

        for(var i = 1, len = t.getColumns().length; i < len; i++){
            var thisColDiv = document.getElementById('' + n.id + i);
            var columnComponent = a['application'].getComponentByRenderId(a.columns[i]);
            if (! (columnComponent instanceof EchoExt20.ExtComponent) ) {
                // we don't renderAdd here - ext does it lazily
                var wrapper = new EchoExt20.Echo3SyncWrapper(c.update, columnComponent);
                wrapper.wrappedRootElement.style.display='inline';
                thisColDiv.appendChild(wrapper.wrappedRootElement);
            } else {
                Echo.Render.renderComponentAdd(a['update'], columnComponent, thisColDiv);
                textComponent.peer.extComponent.render(thisColDiv);
            }
        }
        
        var tableEl = Ext.get('tbl' + n.id);
        tableEl.on('click', this.onClick, this);

        this.elNode = this.wrap.childNodes[0];
        this.ctNode = this.wrap.childNodes[1];
        var cs = this.elNode.childNodes[0].childNodes;
        if (t.showCheckBoxes) {
            cs = this.elNode.childNodes[1].childNodes;
        }
        this.indentNode = cs[0];
        this.ecNode = cs[1];
        this.iconNode = cs[2];
        this.anchor = cs[2];
        this.textNode = cs[2].firstChild;
        
        
        if(t.showCheckBoxes && canBeSelected){
            this.checkbox = this.elNode.childNodes[0].childNodes[0];
            Ext.fly(this.checkbox).on('click', n.checkBoxChanged, n);
        }
        
        if (n.isSelected()) {
        	this.addClass("x-tree-selected");
        }
    }
});
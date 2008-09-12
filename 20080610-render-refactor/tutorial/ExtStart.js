
Ext.onReady(function() {

	// Note: For the purposes of following along with the tutorial, all 
	// new code should be placed inside this method.  Delete the following
	// line after you have verified that Ext is installed correctly.
	
	//alert("Congratulations!  You have Ext configured correctly!");

    var viewport = new Ext.Viewport({
    layout: 'border',
    items: [{
        region: 'north',
        html: 'Page Title',
        autoHeight: true,
        border: false,
        margins: '0 0 5 0'
    }, 
    {
        region: 'center',
        xtype: 'panel',
        layout: 'fit',
    },
    createWestPanel(),
    {
        region: 'south',
        title: 'Information',
        collapsible: true,
        html: 'Information goes here',
        split: true,
        height: 100,
        minHeight: 100
    }]
});

    var tabPanel = new Ext.TabPanel({
        split: true,
        deferredRender: false,
        items: {
            title: 'Default Tab',
            html: 'The first tab\'s content. Others may be added dynamically'
        }
    });
    tabPanel.setActiveTab(0);
    
    tabPanel.add({
        title: 'Tab 2',
        html: 'The second tab<p/>asdasd'
    });

    // create the button panel and add it to the tab panel
    var buttonsPanel = new Ext.Panel({
        title: 'Tab 3',
        layout: 'table',
        defaults: {
            // applied to each contained panel
            bodyStyle:'padding:20px'
        },
        layoutConfig: {
            // The total column count must be specified here
            columns: 2
        },
    });

    // create a panel for the first button and add it to the button panel
    var button1Panel = new Ext.Panel({layout: 'fit'});
    buttonsPanel.add(button1Panel);
    
    var button = new Ext.Button({
        text: 'My first button'
    });
    button1Panel.add(button);
    button1Panel.doLayout();

    // create a panel for the first button and add it to the button panel
    var button2Panel = new Ext.Panel({layout: 'fit'});
    buttonsPanel.add(button2Panel);
    
    var button2 = new Ext.Button({
        text: 'My second button'
    });
    button2Panel.add(button2);
    button2Panel.doLayout();
    
    buttonsPanel.doLayout();
    
    tabPanel.add(buttonsPanel);
    tabPanel.doLayout();

    // and add the tab panel to the center of the viewport
    var center = viewport.getComponent(1);
    center.add(tabPanel);
    center.doLayout();
    
    viewport.syncSize();

});

function createWestPanel() {
	var options = {
		region: 'west',
		title: 'West panel',
		width: 200,
		split: true,
		collapsible: true,
		autoScroll: true,
		bodyStyle: {background: '#aaaaaa'}
	};
	
	var childItems = [];
	childItems.push(createInnerWestPanel());
	
	options['items'] = childItems;
	
	var ret = new Ext.Panel(options);
	
	return ret;
}

function createInnerWestPanel() {
	var mainPanel = new Ext.Panel({
		layout: 'table',
		layoutConfig: {columns: 1},
		border: false,
		bodyStyle: {background: '#aaaaaa'}
	});
	
	var outerPanel1 = new Ext.Panel({
		layout: 'fit',
		border: false,
		bodyStyle: {
			padding: '20px',
			background: '#aaaaaa'
		}
	});
	mainPanel.add(outerPanel1);
	
	var panel1 = new Ext.Panel({
		title: 'Panel 1',
		frame: true,
		collapsible: true,
		width: 100,
		minWidth: 100,
		html: '<p>asd</p>'
	});
	outerPanel1.add(panel1);
	
	var outerPanel2 = new Ext.Panel({
		layout: 'fit',
		border: false,
		bodyStyle: {
			padding: '20px',
			background: '#aaaaaa'
		}
	});
	mainPanel.add(outerPanel2);
	
	var panel2 = new Ext.Panel({
		title: 'Panel 2',
		frame: true,
		collapsible: true,
		width: 100,
		minWidth: 100,
		html: '<p>asd</p>'
	});
	outerPanel2.add(panel2);
	
	return mainPanel;
}

/*
function deadStuff() {
{
        region: 'west',
        collapsible: true,
        title: 'Navigation',
        xtype: 'treepanel',
        width: 200,
        autoScroll: true,
        split: true,
        loader: new Ext.tree.TreeLoader(),
        root: new Ext.tree.AsyncTreeNode({
            expanded: true,
            children: [{
                text: 'Menu Option 1',
                leaf: true
            }, {
                text: 'Menu Option 2',
                leaf: true
            }, {
                text: 'Menu Option 3',
                leaf: true
            }]
        }),
        rootVisible: false,
        listeners: {
            click: function(n) {
                Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
            }
        }
    }, 
}
*/
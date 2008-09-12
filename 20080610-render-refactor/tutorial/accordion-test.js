
Ext.onReady(function() {
	
	var viewport = new Ext.Panel({
        renderTo: 'content',
		items: [
			{
				html: 'North'
			},
			makeAccordion()
		]
	});

});

function makeAccordion() {
    
	var acc = new Ext.Panel({
		layout: 'accordion'
	});
	
	acc.add(makePanel("asd"));
	acc.add(makePanel("wer"));
    
    var ret = new Ext.Panel({
        layout: 'fit',
        height: 400,
        items: [
            acc
        ]
    });
	
	return ret;
	
}

function makePanel(title) {
	var ret = new Ext.Panel({
		title: title,
		html: title
	});
	
	return ret;
}

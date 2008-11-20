//version 3.0

Ext.ux.MandatoryField={
    	init:function(field) {
    		// install event handlers on field render
        field.on({
            render:{fn:function() {
                if(field.mandatory){
                	this.el.dom.className += " ux-mandatory"
                }
            }}
        });
    	}
    }
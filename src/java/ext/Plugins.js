Ext.ux.MandatoryField = {
    init: function(field){
        // if field is mandatory, append an asterisk to the right hand side of the field
        field.on({
            render: {
                fn: function(){
                    if (!field.allowBlank) {
                        switch (field.getXType()) {
                        	case "multiselect":
                        		// use this in plugins.css to apply a float:left to the fieldset
                        		field.el.addClass("ux-mandatory");
                        		Ext.DomHelper.append(field.el, {
                        			tag: "label",
                        			cls: " ux-mandatory",
                                	htmlFor: field.id,
                                	html: "*"
                        		});
                        		break;
                            case "textarea":
								// ensure asterisk appears at the top right of a TextArea
                            	var el = field.el.parent();
                                Ext.DomHelper.append(el, {
                                    tag: "label",
                                    cls: " ux-mandatory",
									style: "position:relative;top:-" + ((field.height || el.getHeight()) - 17) + "px;",
                                    htmlFor: field.id,
                                    html: "*"
                                });
                                break;
                            default:
                                Ext.DomHelper.append(field.el.parent(), {
                                    tag: "label",
                                    cls: " ux-mandatory",
                                    htmlFor: field.id,
                                    html: "*"
                                });
                        }
                    }
                }
            }
        });
    }
};

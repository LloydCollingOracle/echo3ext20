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
                            	if (Ext.isIE) {

                                    Ext.DomHelper.append(el, {
                                        tag: "label",
                                        cls: " ux-mandatory",
    									style: "position:absolute;margin-left: 0;",
                                        htmlFor: field.id,
                                        html: "*"
                                    });
                            	} else {

                                    Ext.DomHelper.append(el, {
                                        tag: "label",
                                        cls: " ux-mandatory",
    									style: "position:absolute;margin-left: -8px;",
                                        htmlFor: field.id,
                                        html: "*"
                                    });
                            	}
                                field.el.addClass("text-area-mandatory");
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
            },
            beforedestroy: {
                fn: function(){
                    if (field && field.rendered && !field.allowBlank) {
                        switch (field.getXType()) {
                            case "multiselect":
                                // use this in plugins.css to apply a float:left to the fieldset
                                Ext.DomHelper.overwrite(field.el, "");
                                break;
                            case "textarea":
                                // ensure asterisk appears at the top right of a TextArea
                                var el = field.el.parent();
                                Ext.DomHelper.overwrite(el, "");
                                break;
                            default:
                            	if (field && field.el && field.el.parent()) {
                            		Ext.DomHelper.overwrite(field.el.parent(), "");
                            	}
                        }
                    }
                }
            }
        });
    }
};

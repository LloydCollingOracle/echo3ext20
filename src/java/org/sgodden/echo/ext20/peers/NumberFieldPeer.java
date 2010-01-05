package org.sgodden.echo.ext20.peers;

import org.sgodden.echo.ext20.NumberField;

@SuppressWarnings({"unchecked"})
public class NumberFieldPeer extends TextFieldPeer {
	public Class getComponentClass() {
		return NumberField.class;
	}
	
	public String getClientComponentType(boolean shortType) {
		return shortType ? "E2NF" : "Ext20NumberField";
	}
}

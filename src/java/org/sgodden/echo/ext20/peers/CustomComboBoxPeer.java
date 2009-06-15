package org.sgodden.echo.ext20.peers;

import org.sgodden.echo.ext20.CustomComboBox;

public class CustomComboBoxPeer extends ExtComponentPeer {
	/*
	protected static final Service DATE_FIELD_SERVICE = JavaScriptService
			.forResource("EchoExt20.CustomComboBox",
					"org/sgodden/echo/ext20/resource/js/Ext20.CustomComboBox.js");

	static {
		WebContainerServlet.getServiceRegistry().add(DATE_FIELD_SERVICE);
	}
	*/
	public CustomComboBoxPeer() {
		super();
		addOutputProperty( CustomComboBox.PROPERTY_VALUE);
	}

	@Override
	public Class getComponentClass() {
		return CustomComboBox.class;
	}

	@Override
	public String getClientComponentType(boolean shortType) {
		return shortType ? "E2CCB" : "Ext20CustomComboBox";
	}
/*
	@Override
	public void init(Context context, Component c) {
		super.init(context, c);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(DATE_FIELD_SERVICE.getId());
	}
*/
}

package org.sgodden.echo.ext20.peers;

import org.sgodden.echo.ext20.HtmlPanel;

public class HtmlPanelPeer extends ExtComponentPeer {
/*	private static final Service HTML_SERVICE = JavaScriptService.forResource(
			"EchoExt20.HtmlPanel", "org/sgodden/echo/ext20/resource/js/Ext20.HtmlPanel.js");

	static {
		WebContainerServlet.getServiceRegistry().add(HTML_SERVICE);
	}
*/
	@Override
	public Class getComponentClass() {
		return HtmlPanel.class;
	}

	public String getClientComponentType(boolean shortType) {
		return shortType ? "E2HP" : "Ext20HtmlPanel";
	}

/*	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get( ServerMessage.class);
		serverMessage.addLibrary( HTML_SERVICE.getId());
	}
*/
}

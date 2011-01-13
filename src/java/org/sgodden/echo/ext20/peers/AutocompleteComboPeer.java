package org.sgodden.echo.ext20.peers;

import nextapp.echo.app.Component;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import org.sgodden.echo.ext20.AutocompleteCombo;

/**
 * Peer for the AutocompleteCombo component
 *
 * @author Lloyd Colling
 */
public class AutocompleteComboPeer extends ExtComponentPeer {

  protected static final Service AUTOCOMPLETE_FIELD_SERVICE = JavaScriptService.forResource("EchoExt20.Autocomplete",
          "org/sgodden/echo/ext20/resource/js/Ext20.Autocomplete.js");

  static {
      WebContainerServlet.getServiceRegistry().add(AUTOCOMPLETE_FIELD_SERVICE);
  }

  public AutocompleteComboPeer() {
      super();
      addOutputProperty(AutocompleteCombo.PROPERTY_VALUE);
  }

  public Class getComponentClass() {
      return AutocompleteCombo.class;
  }

  public String getClientComponentType(boolean shortType) {
      return shortType ? "E2ACP" : "Ext20AutocompleteCombo";
  }

  /**
   * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
   */
  public Class getInputPropertyClass(String propertyName) {
      if (AutocompleteCombo.PROPERTY_VALUE.equals(propertyName)) {
          return String.class;
      } else {
          return super.getInputPropertyClass(propertyName);
      }
  }

  @Override
    public Object getOutputProperty(Context context, Component component,
            String propertyName, int propertyIndex) {
        if (AutocompleteCombo.PROPERTY_REMOTE_MODEL.equals(propertyName)) {
            return ((AutocompleteCombo)component).getRemoteModel();
        }
        if (AutocompleteCombo.PROPERTY_VALUE.equals(propertyName)) {
            return ((AutocompleteCombo)component).getValue();
        }
        return super.getOutputProperty(context, component, propertyName, propertyIndex);
    }

  /**
   * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#storeInputProperty(Context, Component, String, int, Object)
   */
  public void storeInputProperty(Context context, Component component, String propertyName, int propertyIndex, Object newValue) {
      if (propertyName.equals(AutocompleteCombo.PROPERTY_VALUE)) {
          ((AutocompleteCombo)component).setValue((String)newValue);
      } else {
          super.storeInputProperty(context, component, propertyName, propertyIndex, newValue);
      }
  }

  private ClientUpdateManager getClientUpdateManager(Context context){
      return (ClientUpdateManager) context.get(ClientUpdateManager.class);
  }

  @Override
  public void init(Context context, Component c) {
      super.init(context, c);
      ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
      serverMessage.addLibrary(AUTOCOMPLETE_FIELD_SERVICE.getId());
  }

}

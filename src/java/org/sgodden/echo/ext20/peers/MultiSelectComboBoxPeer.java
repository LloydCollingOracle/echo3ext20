package org.sgodden.echo.ext20.peers;

import nextapp.echo.app.Component;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;

import org.sgodden.echo.ext20.MultiSelectComboBox;
import org.sgodden.echo.ext20.data.ListModelAdapter;

public class MultiSelectComboBoxPeer extends ExtComponentPeer {
/*	protected static final Service MULTI_SELECT_SERVICE = JavaScriptService
		.forResource("EchoExt20.MultiSelect", "org/sgodden/echo/ext20/resource/js/Select.js");
	protected static final Service MULTI_SELECT_COMBO_BOX_SERVICE = JavaScriptService
			.forResource("EchoExt20.MultiSelectComboBox",
					"org/sgodden/echo/ext20/resource/js/Ext20.MultiSelectComboBox.js");
	static {
		WebContainerServlet.getServiceRegistry().add(MULTI_SELECT_SERVICE);
		WebContainerServlet.getServiceRegistry().add(MULTI_SELECT_COMBO_BOX_SERVICE);
	}
*/

	public MultiSelectComboBoxPeer() {
        setOutputPropertyReferenced(MultiSelectComboBox.PROPERTY_MODEL_CHANGED, true);
        addEvent(new AbstractComponentSynchronizePeer.EventPeer(MultiSelectComboBox.INPUT_ACTION, MultiSelectComboBox.PROPERTY_ACTION_LISTENERS_CHANGED) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return ((MultiSelectComboBox)component).hasActionListeners();
            }
        });

	}

    @Override
    public Class getComponentClass() {
        return MultiSelectComboBox.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2MSCB" : "Ext20MultiSelectComboBox";
    }
	
	@Override
	public void init(Context context, Component c) {
		super.init(context, c);
/*		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary( MULTI_SELECT_SERVICE.getId());
		serverMessage.addLibrary( MULTI_SELECT_COMBO_BOX_SERVICE.getId());
*/	}
	
	
    @Override
	public Class getInputPropertyClass(String propertyName) {
        if (MultiSelectComboBox.PROPERTY_VALUE_CHANGED.equals(propertyName)) {
            return String.class;
        }
        if (MultiSelectComboBox.PROPERTY_RAW_VALUE_CHANGED.equals(propertyName)) {
        	return String.class;
        }
		return super.getInputPropertyClass(propertyName);
	}

	@Override
    public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
    	MultiSelectComboBox comboBox = (MultiSelectComboBox)component;
        if (MultiSelectComboBox.PROPERTY_VALUE_CHANGED.equals(propertyName)) {
        	if ( comboBox.getMultiSelect()) {
	        	int min = comboBox.getSelectionModel().getMinSelectedIndex();
	        	int max = comboBox.getSelectionModel().getMaxSelectedIndex();
	        	if ( max < 0) {
	        		return null;
	        	}
	        	if ( min == max) {
	        		return ""+min;
	        	}
	        	String result = "";
	        	for ( int i=min; i<=max; i++) {
	        		if ( comboBox.getSelectionModel().isSelectedIndex( i)) {
	        			result += i;
	            		if ( i<max) result += comboBox.getSeparator();
	        		}
	        	}
	            return result;
        	} else {
        		return comboBox.getRawValue();
        	}
        }
        if ( MultiSelectComboBox.PROPERTY_RAW_VALUE_CHANGED.equals(propertyName)) {
        	return comboBox.getRawValue();
        }
        if ( MultiSelectComboBox.PROPERTY_MODEL_CHANGED.equals(propertyName)) {
            return new ListModelAdapter( comboBox);
        }
        return super.getOutputProperty(context, component, propertyName, propertyIndex);
    }
	
    @Override
    public void storeInputProperty(Context context, Component component, String propertyName, int index, Object newValue) {
            ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
        super.storeInputProperty(context, component, propertyName, index, newValue);
        if ( MultiSelectComboBox.PROPERTY_VALUE_CHANGED.equals(propertyName)) {
            clientUpdateManager.setComponentProperty(component, MultiSelectComboBox.PROPERTY_VALUE_CHANGED, newValue);
        } else if ( MultiSelectComboBox.PROPERTY_RAW_VALUE_CHANGED.equals(propertyName)) {
        	clientUpdateManager.setComponentProperty(component, MultiSelectComboBox.PROPERTY_RAW_VALUE_CHANGED, newValue);
        }
    }


}

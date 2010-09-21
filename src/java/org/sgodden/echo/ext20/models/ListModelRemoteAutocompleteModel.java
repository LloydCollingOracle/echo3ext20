package org.sgodden.echo.ext20.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.list.ListModel;

/**
 * An implementation of a RemoteAutocompleteModel that simply uses the String.valueOf of the entries
 * in an existing List Model.
 * 
 * @author Lloyd Colling
 */
public class ListModelRemoteAutocompleteModel implements
        RemoteAutocompleteModel {
    
    private static final long serialVersionUID = 1L;
    
    private ListModel listModel;
    private String renderId;
    
    public ListModelRemoteAutocompleteModel(ListModel model) {
        this.listModel = model;
        this.renderId = ApplicationInstance.generateSystemId();
    }
    
    @Override
    public String getRenderId() {
        return renderId;
    }

    @Override
    public String[] getHits(String startsWith, Integer maxResults, Integer startIndex) {
        List<String> hitsList = new ArrayList<String>();
        int missedValues = 0;
        for (int i = 0; i < listModel.size() && (maxResults == -1 || hitsList.size() < maxResults); i++) {
            Object val = listModel.get(i);
            if (startsWith == null || (val != null && String.valueOf(val).startsWith(startsWith))) {
                if (missedValues >= startIndex) {
                    hitsList.add(String.valueOf(val));
                } else {
                    missedValues++;
                }
            }
        }
        return hitsList.toArray(new String[hitsList.size()]);
    }

    @Override
    public Integer getHitCount(String startsWith) {
        int results = 0;
        for (int i = 0; i < listModel.size(); i++) {
            Object val = listModel.get(i);
            if (startsWith == null || (val != null && String.valueOf(val).startsWith(startsWith))) {
                results++;
            }
        }
        return results;
    }

    @Override
    public boolean isValidValue(String value) {
        for (int i = 0; i < listModel.size(); i++) {
            Object val = listModel.get(i);
            if (value == null && val == null) {
                return true;
            }
            if (ObjectUtils.equals(value, String.valueOf(val))) {
                return true;
            }
        }
        return false;
    }

}

package org.sgodden.echo.ext20.models;

import java.io.Serializable;

import nextapp.echo.app.RenderIdSupport;

/**
 * A model of Strings that is used for remote auto-completion fields only.
 * 
 * @author Lloyd Colling
 */
public interface RemoteAutocompleteModel extends Serializable, RenderIdSupport {

    /**
     * Gets the selectable values for the given text
     * @param startsWith the text to subset the values for
     * @param maxResults the maximum number of results to return, -1 for no limit
     * @param startIndex the index of the first result to return
     * @return
     */
    public String[] getHits(String startsWith, Integer maxResults, Integer startIndex);

    public boolean isValidValue(String value);

    /**
     * Gets the total number of matching values for the given text
     * @param startsWith
     * @return
     */
    public Integer getHitCount(String startsWith);
}

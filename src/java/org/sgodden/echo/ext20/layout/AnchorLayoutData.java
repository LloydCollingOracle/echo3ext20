package org.sgodden.echo.ext20.layout;

import nextapp.echo.app.LayoutData;

/**
 * Provides layout data for anchoring, to be used
 * with the form layout and anchor layout.
 * @author sgodden
 */
@SuppressWarnings("serial")
public class AnchorLayoutData 
        implements LayoutData {

    private String anchorData;
    
    /**
     * Creates a new anchor layout data with the
     * specified anchor data.
     * <p>
     * See the ext documentation for anchor layout
     * to understand the format of the string.
     * @param anchorData the anchor data.
     */
    public AnchorLayoutData(String anchorData) {
        this.anchorData = anchorData;
    }
    
    /**
     * Returns the anchor data.
     * @return the anchor data.
     */
    public String getAnchorData() {
        return anchorData;
    }
    
}
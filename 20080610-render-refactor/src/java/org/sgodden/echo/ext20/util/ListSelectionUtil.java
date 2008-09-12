package org.sgodden.echo.ext20.util;

import java.util.StringTokenizer;

import nextapp.echo.app.list.ListSelectionModel;

/**
 * Utilities for serializing <code>ListSelectionModel</code> state between client and server.
 * <p/>
 * NOTE - this code is lifted straight from echo3.  It is package private there.
 */
public class ListSelectionUtil {
	
    
    /**
     * Creates a selection string representation of a <code>ListSelectionModel</code>.
     * 
     * @param selectionModel the <code>ListSelectionModel</code>
     * @param size the size of the <strong>data</strong> model of which items are selected
     * @return a selection string, e.g., "1,2,3,4", "5", or ""
     */
    public static String toString(ListSelectionModel selectionModel, int size) {
        int minimumIndex = selectionModel.getMinSelectedIndex();
        
        if (minimumIndex == -1) {
            // Nothing selected: return empty String.
            return "";
        }
        int maximumIndex = selectionModel.getMaxSelectedIndex();
        
        if (minimumIndex == maximumIndex || selectionModel.getSelectionMode() != ListSelectionModel.MULTIPLE_SELECTION) {
            // Single selection mode or only one index selected: return it directly. 
            return Integer.toString(minimumIndex);
        }
        
        if (maximumIndex > size - 1) {
            maximumIndex = size - 1;
        }
        StringBuffer out = new StringBuffer();
        boolean commaRequired = false;
        for (int i = minimumIndex; i <= maximumIndex; ++i) {
            if (selectionModel.isSelectedIndex(i)) {
                if (commaRequired) {
                    out.append(",");
                } else {
                    commaRequired = true;
                }
                out.append(i);
            }
        }
        return out.toString();
    }
    
    /**
     * Converts a selection String to an int[] array.
     * 
     * @param selectionString the selection string, e.g., "1,2,3,4", "5", or ""
     * @return the integer array
     */
    public static int[] toIntArray(String selectionString) {
        int[] selection;
        int selectionStringLength  = selectionString.length();
        if (selectionStringLength == 0) {
            selection = new int[0];
        } else {
            int itemCount = 1;
            for (int i = 1; i < selectionStringLength - 1; ++i) {
                if (selectionString.charAt(i) == ',') {
                    ++itemCount;
                }
            }
            selection = new int[itemCount];
        }
        StringTokenizer st = new StringTokenizer(selectionString, ",");
        for (int i = 0; i < selection.length; ++i) {
            selection[i] = Integer.parseInt(st.nextToken());
        }        
        return selection;
    }
    
    /** Non-instantiable class. */
    private ListSelectionUtil() { }


}

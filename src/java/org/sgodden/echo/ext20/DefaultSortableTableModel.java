package org.sgodden.echo.ext20;

import java.util.Arrays;
import java.util.Comparator;

import javax.swing.table.DefaultTableModel;

/**
 * An extension of 
 * @author sgodden
 */
@SuppressWarnings("serial")
public class DefaultSortableTableModel 
        extends DefaultTableModel 
        implements SortableTableModel {

    /**
     * See {@link SortableTableModel#sort(int, SortOrder)}.
     * @param columnIndex the one-indexed column index.
     * @param sortOrder the sort sequence.
     */
    public void sort(int columnIndex, SortOrder sortOrder) {
        Object[] columnData = new Object[getColumnCount()];
        for (int i = 0; i < columnData.length; i++) {
            columnData[i] = getColumnName(i);
        }
        
        Object[][] rowData = new Object[getRowCount()][getColumnCount()];
        for (int row = 0; row < getRowCount(); row++) {
            for (int col = 0; col < getColumnCount(); col++) {
                rowData[row][col] = getValueAt(row, col);
            }
        }
        
        /*
         * Remember columnIndex has been passed to us one-indexed.
         */
        Arrays.sort(rowData, new ArrayColumnComparator(columnIndex - 1));
        
        setDataVector(rowData, columnData);
    }
    
    /**
     * Compares the specified column of an array.
     * @author sgodden
     */
    private static class ArrayColumnComparator 
            implements Comparator < Object[] > {
        
        /**
         * The column index to compare.
         */
        private int colIndex;
                
        /**
         * Creates a new comparator, comparing the
         * object arrays on the specified index.
         * @param columnIndex the column index to compare.
         */
        private ArrayColumnComparator(int columnIndex) {
            this.colIndex = columnIndex;
        }

        /**
         * See {@link Comparator#compare(Object, Object)}.
         * @param oa1 the first object array.
         * @param oa2 the second object array.
         */
        @SuppressWarnings("unchecked")
        public int compare(Object[] oa1, Object[] oa2) {
            Comparable c1 = (Comparable) oa1[colIndex];
            Comparable c2 = (Comparable) oa2[colIndex];
            return c1.compareTo(c2);
        }
        
    }

}

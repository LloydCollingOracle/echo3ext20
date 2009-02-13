package org.sgodden.echo.ext20.grid;

import nextapp.echo.app.Component;

/**
 *  <p>A Grid Cell Renderer performs two functions for a Grid - it renders the server
 *  side model into an String[][] and provides JavaScript fragments for rendering
 *  those values meaningfully on the client.</p>
 *  
 *  <p>The String returned by the <pre>getModelValue</pre> is the value that will be stored
 *  on the client side in the Ext.data.Store instance.</p>
 *  
 *  <p>The String returned by the <pre>getClientSideValueRendererScript</pre> method is
 *  a JavaScript fragment that will be evaluated on the client side. This will have access
 *  to the following variables:</p>
 *  
 *  <ul>
 *  <li><b>value</b> The value from the client side model that has to be rendered</li>
 *  <li><b>renderedValue</b> The variable that the rendered value must be assigned to</li>
 *  <li><b>metadata</b> An Object on which the following values may be set:</li><ul>
 *      <li><b>css</b> A CSS class name to add to the grid cell's TD element</li>
 *      <li><b>attr</b> An HTML attribute definition string to apply to the data container element within the table cell (e.g. 'style="color:red;"')</li></ul>
 *  <li><b>record</b> The Ext.data.Record from which the data was extracted</li>
 *  <li><b>rowIndex</b> The row of the grid</li>
 *  <li><b>colIndex</b> The column of the grid</li>
 *  <li><b>store</b> The Ext.data.Store object that contains the <i>record</i></li>
 *  </ul>
 *  
 *  <p><i>The dangers of rendering values<i><br/>
 *  Let's assume we have two grid cell renderers, and they they return the following as their 
 *  ClientSideValueRendererScript:
 *  </p>
 *  <ol>
 *  <li>"renderedValue = '" + getModelValue(gridPanel, valueAt, colIndex, rowIndex) + "';"</li>
 *  <li>"renderedValue = Ext.util.Format.htmlEncode(value);"</li>
 *  </ol>
 *  <p>
 *  In the first case, the value is rendered as a constant on the server-side. In the second case, the value is 
 *  rendered from the client-side model. The second case is the preferred solution, as it will still work as expected
 *  when the value is modified on the client side.
 *  </p><p>
 *  If a value is modified on the client side, the view of the grid will not be updated when the first case is being used.
 *  This would mean that if a user clicked on an editable grid and changed a value, after pressing enter the would not
 *  show the updated value.
 *  </p>
 *
 * @author Lloyd Colling
 */
public interface GridCellRenderer {

    /**
     * Renders the value at the given co-ordinates into a value for transmission
     * to the client as part of the client-side store.
     * 
     * This value is used as the parameter passed to the rendering function that
     * will be returned by the getRenderedValue method.
     * @param gridPanel
     * @param valueAt
     * @param colIndex
     * @param rowIndex
     * @return
     */
    public String getModelValue(Component gridPanel, Object valueAt,
            int colIndex, int rowIndex);
    
    /**
     * Returns the code used to render the model value into something that will
     * be displayed on the client. Returning null from this will result in the
     * standard renderer being used, which converts special characters into HTML
     * entities.
     * @param gridPanel
     * @param valueAt
     * @param colIndex
     * @param rowIndex
     * @return
     */
    public String getClientSideValueRendererScript(Component gridPanel, Object valueAt,
            int colIndex, int rowIndex);

}

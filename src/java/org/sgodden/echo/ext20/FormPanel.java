/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sgodden.echo.ext20;

import org.sgodden.echo.ext20.layout.FormLayout;

/**
 * A panel used to display forms.
 * @author sgodden
 */
public class FormPanel extends Panel {
    
    public FormPanel(){
        super();
        setLayout(new FormLayout());
    }

}

package org.sgodden.echo.ext20.fieldgroup;

import java.io.Serializable;

import nextapp.echo.app.Component;

/**
 * A factory for field groups that will be added to a 
 * FieldGroupContainer.
 * 
 * @author Lloyd Colling
 *
 */
public interface FieldGroupFactory extends Serializable {

    public Component getFieldGroup(int index);
}

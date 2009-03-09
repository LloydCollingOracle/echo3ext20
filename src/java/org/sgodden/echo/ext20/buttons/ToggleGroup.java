package org.sgodden.echo.ext20.buttons;

import nextapp.echo.app.ApplicationInstance;

import org.sgodden.echo.ext20.Button;

/**
 * A toggle group that takes buttons and adds those buttons to its group.
 * 
 * @author rcharlton
 */
public class ToggleGroup {

    private String toggleGroupName;

    /**
     * Default toggle group name that is unique within this application
     * instance.
     */
    private static final String DEFAULT_TGROUP_NAME = ApplicationInstance
            .getActive().generateId();

    /**
     * Constructs a toggle group with a default toggle group name
     */
    public ToggleGroup() {
        setToggleGroupName(DEFAULT_TGROUP_NAME);
    }

    /**
     * Constructs a toggle group with a toggle group name provided.
     * 
     * @param toggleGroupName
     */
    public ToggleGroup(String toggleGroupName) {
        setToggleGroupName(toggleGroupName);
    }

    private void setToggleGroupName(String toggleGroupName) {
        this.toggleGroupName = toggleGroupName;
    }

    /**
     * Adds the button to this toggle group by setting the toggle group name on
     * the button.
     */
    public void addButton(Button button) {
        button.setToggleGroup(toggleGroupName);
    }
}

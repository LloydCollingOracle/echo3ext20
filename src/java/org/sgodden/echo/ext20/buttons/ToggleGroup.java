package org.sgodden.echo.ext20.buttons;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

import org.sgodden.echo.ext20.AbstractButton;
import org.sgodden.echo.ext20.Button;

/**
 * A toggle group that takes buttons and adds those buttons to its group.
 * 
 * @author rcharlton
 */
public class ToggleGroup {

    private String toggleGroupName;

    private static final String DEFAULT_TGROUP_NAME = "toggle_group_";

    private List<AbstractButton> buttons = new ArrayList<AbstractButton>();

    /**
     * Constructs a toggle group with a default toggle group name that is unique
     * to the Application Instance.
     */
    public ToggleGroup() {
        setToggleGroupName(DEFAULT_TGROUP_NAME
                + Window.getActive().generateId());
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
        buttons.add(button);
    }

    /**
     * @return buttons All the buttons that have been added to this toggle
     *         group.
     */
    public List<AbstractButton> getButtons() {
        return buttons;
    }
}
package org.sgodden.echo.ext20;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import nextapp.echo.app.Component;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;

import org.sgodden.echo.ext20.buttons.AddButton;
import org.sgodden.echo.ext20.buttons.RemoveButton;
import org.sgodden.echo.ext20.fieldgroup.FieldGroupEvent;
import org.sgodden.echo.ext20.fieldgroup.FieldGroupFactory;
import org.sgodden.echo.ext20.fieldgroup.FieldGroupListener;
import org.sgodden.echo.ext20.layout.ColumnLayout;
import org.sgodden.echo.ext20.layout.ColumnLayoutData;

/**
 * This component allows the entry of values in one or more field groups. It
 * initially displays one field group with a button to add another field group.
 * When more than one field group is active, all groups have a remove button and
 * the last group has an add button.
 * 
 * The field groups are supplied by the FieldGroupFactory that is applied to the
 * FieldGroupContainer, and the factory will be called each time the add button
 * is pressed.
 * 
 * Whenever the add button or remove buttons are pressed, the registered
 * FieldGroupListener instances will be notified that the action is about to
 * take place, optionally cancelling the action.
 * 
 * @author Lloyd Colling
 * 
 */
public class FieldGroupContainer extends Panel {

    FieldGroupFactory factory = null;
    List<Panel> containedGroups = new ArrayList<Panel>();
    EventListenerList listenerList = new EventListenerList();
    int initialFieldGroups = 1;
    AddButton addButton;
    boolean initted = false;
    String removeButtonText = null;
    
    public FieldGroupContainer() {
        super(new ColumnLayout());
    }
    
    public Panel getContainedGroup(int index) {
        return containedGroups.get(index);
    }
    
    public int getContainedGroupCount() {
        return containedGroups.size();
    }

    @Override
    public void init() {
        super.init();
        if (factory == null)
            throw new IllegalStateException(
                    "Field group factory must be set before initialistion");
        if (initted)
            return;
        initted = true;
        Panel groupedPanel = new Panel();
        groupedPanel.setLayoutData(new ColumnLayoutData(1.0));
        Panel contentPanel = new Panel();
        contentPanel.setRoundedBorders(Boolean.TRUE);
        contentPanel.setPadding("5px");

        contentPanel.add(factory.getFieldGroup(0));
        groupedPanel.add(contentPanel);

        add(groupedPanel);
        containedGroups.add(groupedPanel);

        if (addButton == null)
            addButton = new AddButton();
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int index = containedGroups.size();
                if (fireWillAdd(index)) {
                    doAddFieldGroup(index);
                }
            }
        });
        addButton(addButton);
        
        for (int i = 1; i < initialFieldGroups; i++)
            doAddFieldGroup(i);
    }

    protected void doAddFieldGroup(int index) {
        // if we're going from one group to more then add remove button to first
        // group
        if (index == 1) {
            Panel firstGroup = containedGroups.get(0);
            RemoveButton removeButton = new RemoveButton();
            if (removeButtonText != null)
                removeButton.setText(removeButtonText);
            removeButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent arg0) {
                    if (fireWillRemove(0)) {
                        doRemoveGroup(0);
                    }
                }
            });
            firstGroup.addButton(removeButton);
        }

        Panel groupedPanel = new Panel();
        groupedPanel.setLayoutData(new ColumnLayoutData(1.0));
        Panel contentPanel = new Panel();
        contentPanel.setRoundedBorders(Boolean.TRUE);
        contentPanel.setPadding("5px");
        contentPanel.add(factory.getFieldGroup(index));
        groupedPanel.add(contentPanel);

        RemoveButton removeButton = new RemoveButton();
        if (removeButtonText != null)
            removeButton.setText(removeButtonText);
        removeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                if (fireWillRemove(0)) {
                    doRemoveGroup(0);
                }
            }
        });
        groupedPanel.addButton(removeButton);

        add(groupedPanel);
        containedGroups.add(groupedPanel);
    }

    protected void doRemoveGroup(int index) {
        // if we're going down to one group, remove the remove button from the
        // remaining group
        if (containedGroups.size() == 2) {
            Container lastGroup = containedGroups.get(index == 0 ? 1 : 0);
            for (Component c : lastGroup.getComponents()) {
                if (c instanceof RemoveButton)
                    lastGroup.remove(c);
            }
        }
        
        Container p = containedGroups.remove(index);
        remove(p);
    }

    protected boolean fireWillAdd(int index) {
        FieldGroupEvent e = new FieldGroupEvent(this, index);
        for (EventListener listener : listenerList
                .getListeners(FieldGroupListener.class)) {
            ((FieldGroupListener) listener).willAddFieldGroup(e);
        }
        return !e.isActionCancelled();
    }

    protected boolean fireWillRemove(int index) {
        FieldGroupEvent e = new FieldGroupEvent(this, index);
        for (EventListener listener : listenerList
                .getListeners(FieldGroupListener.class)) {
            ((FieldGroupListener) listener).willRemoveFieldGroup(e);
        }
        return !e.isActionCancelled();
    }

    public void setFieldGroupFactory(FieldGroupFactory factory) {
        this.factory = factory;
    }
    
    public void addFieldGroupListener(FieldGroupListener listener) {
        listenerList.addListener(FieldGroupListener.class, listener);
    }
    
    public void removeFieldGroupListener(FieldGroupListener listener) {
        listenerList.removeListener(FieldGroupListener.class, listener);
    }
    
    /**
     * Sets the number of field groups to display initially
     * @param number
     */
    public void setNumberInitialFieldGroups(int number) {
        if (number < 1)
            throw new IllegalArgumentException("Number of initial field groups must be at least one");
        
        this.initialFieldGroups = number;
    }
    
    public void setAddButtonText(String text) {
        if (addButton == null)
            addButton = new AddButton();
        addButton.setText(text);
    }
    
    public void setRemoveButtonText(String text) {
        this.removeButtonText = text;
    }
}

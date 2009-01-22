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
import org.sgodden.echo.ext20.layout.FitLayout;

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
    
    public FieldGroupContainer() {
        super(new ColumnLayout());
    }

    @Override
    public void init() {
        if (factory == null)
            throw new IllegalStateException(
                    "Field group factory must be set before initialistion");
        Panel groupedPanel = new Panel();
        groupedPanel.setLayoutData(new ColumnLayoutData(1.0));
        groupedPanel.setRenderId("groupedPanel0");
        Panel contentPanel = new Panel();
        contentPanel.setRenderId("contentPanel0");
        contentPanel.setRoundedBorders(Boolean.TRUE);
        contentPanel.setPadding("5px");

        if (!fireWillAdd(0)) {
            throw new IllegalStateException(
                    "Addition of first panel was cancelled");
        }
        contentPanel.add(factory.getFieldGroup(0));
        groupedPanel.add(contentPanel);

        add(groupedPanel);
        containedGroups.add(groupedPanel);

        AddButton addButton = new AddButton();
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int index = containedGroups.size();
                if (fireWillAdd(index)) {
                    doAddFieldGroup(index);
                }
            }
        });
        addButton(addButton);
    }

    protected void doAddFieldGroup(int index) {
        // if we're going from one group to more then add remove button to first
        // group
        if (index == 1) {
            Panel firstGroup = containedGroups.get(0);
            RemoveButton removeButton = new RemoveButton();
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
            Panel lastGroup = containedGroups.get(index == 0 ? 1 : 0);
            for (Component c : lastGroup.getComponents()) {
                if (c instanceof RemoveButton)
                    lastGroup.remove(c);
            }
        }
        
        Panel p = containedGroups.remove(index);
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
}

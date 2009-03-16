package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.AddComponentFx;
import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.RemoveComponentFx;
import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.TableLayout;

/**
 * Test tab to demonstrate the client-side effects
 * @author Lloyd Colling
 */
@SuppressWarnings("serial")
public class EffectsTest extends Panel {
    
    /**
     * Creates a new effects test.
     */
    public EffectsTest() {
        super(new FitLayout(), "Effects");
        setAutoScroll(true);
        
        final Panel container = new Panel();
        add(container);
        
        final Panel buttonPanel = new Panel(new TableLayout());
        buttonPanel.setRenderId("EffectsButtonPanel");
        buttonPanel.setHeight(100);
        container.add(buttonPanel);
        
        final Panel panelToAdd = new Panel();
        panelToAdd.setRoundedBorders(Boolean.TRUE);
        panelToAdd.add(new Label("Effected Panel"));
        
        Button addRemoveButton = new Button("Fade In / Fade Out");
        addRemoveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                boolean alreadyAdded = false;
                for(int i = 0; i < container.getComponentCount() && !alreadyAdded; i++) {
                    if (container.getComponent(i) == panelToAdd)
                        alreadyAdded = true;
                }
                if (alreadyAdded) {
                    container.remove(panelToAdd);
                } else {
                    panelToAdd.setAddEffect(AddComponentFx.FADE_IN);
                    panelToAdd.setRemoveEffect(RemoveComponentFx.FADE_OUT);
                    container.add(panelToAdd);
                }
            }});
        buttonPanel.add(addRemoveButton);
        
        addRemoveButton = new Button("Slide In / Slide Out");
        addRemoveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                boolean alreadyAdded = false;
                for(int i = 0; i < container.getComponentCount() && !alreadyAdded; i++) {
                    if (container.getComponent(i) == panelToAdd)
                        alreadyAdded = true;
                }
                if (alreadyAdded) {
                    container.remove(panelToAdd);
                } else {
                    panelToAdd.setAddEffect(AddComponentFx.SLIDE_IN);
                    panelToAdd.setRemoveEffect(RemoveComponentFx.SLIDE_OUT);
                    container.add(panelToAdd);
                }
            }});
        buttonPanel.add(addRemoveButton);
        
        addRemoveButton = new Button("Fade In / Ghost");
        addRemoveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                boolean alreadyAdded = false;
                for(int i = 0; i < container.getComponentCount() && !alreadyAdded; i++) {
                    if (container.getComponent(i) == panelToAdd)
                        alreadyAdded = true;
                }
                if (alreadyAdded) {
                    container.remove(panelToAdd);
                } else {
                    panelToAdd.setAddEffect(AddComponentFx.FADE_IN);
                    panelToAdd.setRemoveEffect(RemoveComponentFx.GHOST);
                    container.add(panelToAdd);
                }
            }});
        buttonPanel.add(addRemoveButton);
        
        addRemoveButton = new Button("Fade In / Puff");
        addRemoveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                boolean alreadyAdded = false;
                for(int i = 0; i < container.getComponentCount() && !alreadyAdded; i++) {
                    if (container.getComponent(i) == panelToAdd)
                        alreadyAdded = true;
                }
                if (alreadyAdded) {
                    container.remove(panelToAdd);
                } else {
                    panelToAdd.setAddEffect(AddComponentFx.FADE_IN);
                    panelToAdd.setRemoveEffect(RemoveComponentFx.PUFF);
                    container.add(panelToAdd);
                }
            }});
        buttonPanel.add(addRemoveButton);
        
        addRemoveButton = new Button("Appear / Switch Off");
        addRemoveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                boolean alreadyAdded = false;
                for(int i = 0; i < container.getComponentCount() && !alreadyAdded; i++) {
                    if (container.getComponent(i) == panelToAdd)
                        alreadyAdded = true;
                }
                if (alreadyAdded) {
                    container.remove(panelToAdd);
                } else {
                    panelToAdd.setRemoveEffect(RemoveComponentFx.SWITCH_OFF);
                    container.add(panelToAdd);
                }
            }});
        buttonPanel.add(addRemoveButton);
    }

}

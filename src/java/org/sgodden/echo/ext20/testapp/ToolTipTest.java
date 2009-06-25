package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;

public class ToolTipTest extends Panel {

    public ToolTipTest() {

        super("Tool Tips Test");

        final TextField textField = new TextField();

        final Button applyTootipButton = new Button("Enable or Disable tooltip");
        applyTootipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (applyTootipButton.getPressed()) {
                    textField.setToolTip("A Tool tip");
                    textField.setShowToolTip(true);
                } else {
                    textField.setShowToolTip(false);
                }
            }
        });

        add(textField);
        add(applyTootipButton);
    }

}

package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.Window;
import org.sgodden.echo.ext20.WindowListener;
import org.sgodden.echo.ext20.layout.TableLayout;

@SuppressWarnings("serial")
public class KeyPressTest extends Panel {

	private TextField tf;
	
	public KeyPressTest() {
		super("Keypress test");
	}
	
	@Override
	public void init() {
		super.init();
		TableLayout tl = new TableLayout(1);
		tl.setCellPadding(10);
		tl.setCellSpacing(10);
		setLayout(tl);
		
		Label l = new Label("Both the enter key and the escape key are registered on this panel, as well as Ctrl-S and Ctrl-F1 and Page Up - press them and you should" +
				" get messages indicating which key you pressed");
		add(l);
		
		l = new Label("The following text field is focused so that the key presses on this panel will be in effect");
		add(l);
		
		tf = new TextField("Focused so that the key presses will fire");
		add(tf);
		tf.focus();
		
		registerKeyPress("enter");
		registerKeyPress("esc");
		registerKeyPress("ctrl+s");
		registerKeyPress("ctrl+f1");
		registerKeyPress("page_up");

	}
	
	private void registerKeyPress(final String keyString) {
		addKeyPressListener(keyString, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				add(new MessageDialog(keyString));
				
			}
		});
	}
	
	private class MessageDialog extends Window {
		private MessageDialog(String key) {
			super("Key pressed");
			setHtml("<p>You pressed: " + key + "</p>" +
					"<p>You can press enter or escape to close this window.</p>");
			
			final Button cancelButton = new Button("Close");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					close();
				}
			});
			addButton(cancelButton);
			cancelButton.focus();
			
			addKeyPressListener("esc", new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cancelButton.doClick();
				}
			});
			
			addWindowListener(new WindowListener() {
				public void windowClosing(ActionEvent event) {
					tf.focus();
				}
			});
		}
	}
}
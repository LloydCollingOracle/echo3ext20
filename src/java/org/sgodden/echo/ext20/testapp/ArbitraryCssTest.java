package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.Container;
import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.TableLayout;

/**
 * Tests the use of the arbitary CSS on a component.
 * @author sgodden
 *
 */
@SuppressWarnings("serial")
public class ArbitraryCssTest extends Panel {
	
	public ArbitraryCssTest() {
		super("Arbitrary CSS test");
		TableLayout tl = new TableLayout(1);
		tl.setCellPadding(10);
		tl.setCellSpacing(10);
		setLayout(tl);
		
		Label intro = new Label("This demonstrates the use of a label placed inside a container " +
				"which has arbitary CSS styles applied to hide the overflow and set the width" +
				". The result is that the label is chopped off, and does not affect layout when the screen is resized");
		add(intro);

		Container c = new Container();
		c.setCssString("white-space: nowrap; width: 100px; overflow: hidden;");
		add(c);
		
		Label l = new Label("Long value 123123123123123123123123123123123123");
		c.add(l);
	}
}

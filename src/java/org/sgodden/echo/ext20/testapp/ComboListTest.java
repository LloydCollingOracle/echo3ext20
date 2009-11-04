package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.list.DefaultListModel;

import org.sgodden.echo.ext20.ComboBox;
import org.sgodden.echo.ext20.Container;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.TableLayout;

public class ComboListTest extends Panel{
	
	private Container p = new Panel(new TableLayout(1));
	private ComboBox cb;
    private ComboBox cb2;
	public ComboListTest(){
		
		setLayout(new FitLayout());
		setTitle("Combo List Test");
		
		cb = new ComboBox();
		cb.setRenderId("comboBox1");
		String[] roles = {"test 1","test 2","test 3"};
		cb.setModel(new DefaultListModel(roles));
		//cb.setCellRenderer(new RoleListCellRenderer());
		cb.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
			    if (cb.getSelectedItem() != null)
			        p.add(new Label(cb.getSelectedItem().toString()));
			    else
			        p.add(new Label("Raw Value: " + cb.getRawValue()));
			    cb2.setSelectedItem(null);
			}
		});
		cb.setTypeAhead(true);
		p.add(cb);
		
		cb2 = new ComboBox();
		cb2.setRenderId("comboBox2");
		cb2.setModel(cb.getModel());
		p.add(cb2);
		
		add(p);
		
	}

}

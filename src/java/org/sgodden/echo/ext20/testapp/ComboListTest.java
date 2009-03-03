package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.list.DefaultListModel;

import org.sgodden.echo.ext20.ComboBox;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.FitLayout;
import org.sgodden.echo.ext20.layout.TableLayout;

public class ComboListTest extends Panel{
	
	private Panel p = new Panel(new TableLayout(1));
	private ComboBox cb;
	public ComboListTest(){
		
		setLayout(new FitLayout());
		setTitle("Combo List Test");
		
		cb = new ComboBox();
		String[] roles = {"test 1","test 2","test 3"};
		cb.setModel(new DefaultListModel(roles));
		//cb.setCellRenderer(new RoleListCellRenderer());
		cb.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				p.add(new Label(cb.getSelectedItem().toString()));
		
			}
		});
		p.add(cb);
		add(p);
		
	}

}

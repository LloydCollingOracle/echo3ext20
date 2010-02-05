package org.sgodden.echo.ext20.testapp;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Component;
import nextapp.echo.app.table.DefaultTableModel;

import org.sgodden.echo.ext20.grid.ColumnConfiguration;
import org.sgodden.echo.ext20.grid.DefaultColumnConfiguration;
import org.sgodden.echo.ext20.grid.DefaultColumnModel;
import org.sgodden.echo.ext20.grid.GridCellRenderer;
import org.sgodden.echo.ext20.grid.GridPanel;

@SuppressWarnings("serial")
public class GridPanelForceFitTest extends GridPanel {
	
	//private static final transient Logger LOG = Logger.getLogger(GridPanelForceFitTest.class);
	
	public GridPanelForceFitTest() {
		super(new MyColumnModel(), new MyTableModel());
		setGridCellRenderer(new MyGridCellRenderer());
		setForceFit(true);
	}
	
	private static class MyColumnModel extends DefaultColumnModel {
		private MyColumnModel() {
			super();
			setColumns(makeColumns());
		}
		private List<ColumnConfiguration> makeColumns() {
			List<ColumnConfiguration> ret = new ArrayList<ColumnConfiguration>();
			ret.add(new DefaultColumnConfiguration("Column 1", "col1"));
			ret.add(new DefaultColumnConfiguration("Column 2", "col2"));
			ret.add(new DefaultColumnConfiguration("Column 3", "col3"));
			ret.add(new DefaultColumnConfiguration("Column 4", "col4"));
			return ret;
		}
	}
	
	private static class MyTableModel extends DefaultTableModel {
		private MyTableModel() {
			super(makeData(), makeColumnHeaders());
		}
		private static Object[][] makeData() {
			String[][] ret = new String[50][4];
			for (int i = 0; i < ret.length; i++) {
				for (int j = 0; j < ret[i].length; j++) {
					ret[i][j] = "Row " + i + " col " + j;
					if (j == 0) {
						ret[i][j] += " really really really long value";
					}
				}
			}
			return ret;
		}
		private static Object[] makeColumnHeaders() {
			return new String[]{"col1","col2","col3","col4"};
		}
	}
	
	/**
	 * A grid cell renderer which forces column 0 to wrap.
	 */
	private static class MyGridCellRenderer implements GridCellRenderer {

		@Override
		public String getClientSideValueRendererScript(Component gridPanel,
				Object valueAt, int colIndex, int rowIndex) {
			if (colIndex == 0) {
				return "renderedValue='<div style=\"white-space: normal;\">' + value + '</div>'";
			}
			else {
				return "renderedValue=value";
			}
		}

		@Override
		public String getModelValue(Component gridPanel, Object valueAt,
				int colIndex, int rowIndex) {
			return (String) valueAt;
		}
		
	}
}

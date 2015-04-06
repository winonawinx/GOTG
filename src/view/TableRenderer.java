package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableRenderer extends DefaultTableCellRenderer {
	private int row, col;

	public TableRenderer(int i, int j) {
		super();
		row = j;
		col = i;
//		System.out.println(row + " " + col);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean selected, boolean focused, int row, int column) {
		super.getTableCellRendererComponent(table, value, selected, focused,
				row, column);

		if(this.row==row&&this.col==column){
        	setBackground(Color.LIGHT_GRAY);
        }
        else if ((column == 0 || column == 6))
            setBackground(new Color(220,220,255));
        else 
            setBackground(Color.WHITE);

		this.repaint();
		this.revalidate();
		return this;
	}
}

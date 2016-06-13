package ownerFunctionality;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.sun.istack.internal.FinalArrayList;

public class MenuItemTable extends AbstractTableModel{
	
	public static final int OBJECT_COL = -1;
	private static final int ID_COL = 0;
	private static final int NAME_COL = 1;
	private static final int TYPE_COL = 2;
	private static final int PRICE_COL = 3;

	private String[] columnNames = { "MENUITEMID", "NAME", "ITEMTYPE", "PRICE"};
	private List<MenuItem> menuItems;

	public MenuItemTable(List<MenuItem> theMenuItems) {
		menuItems = theMenuItems;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return menuItems.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		MenuItem tempMenuItem = menuItems.get(row);

		switch (col) {
		case ID_COL:
			return tempMenuItem.getId();
		case NAME_COL:
			return tempMenuItem.getItemName();
		case TYPE_COL:
			return tempMenuItem.getItemType();
		case PRICE_COL:
			return tempMenuItem.getPrice();
		case OBJECT_COL:
			return tempMenuItem;
		default:
			return tempMenuItem.getItemName();
	}
	}
}

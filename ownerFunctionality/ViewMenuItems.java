package ownerFunctionality;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import menus.OwnerMenu;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class ViewMenuItems extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JTable menuItemTable;
	private JPanel MenuItemsButtonPanel;
	private JButton addMenuItembutton;
	private JButton updateMenuItemButton;
	private JButton deleteMenuItemButton;
	private OwnerMenu ownerMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewMenuItems frame = new ViewMenuItems();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewMenuItems() {
		setTitle("Menu Items");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		menuItemTable = new JTable();
		panel.add(menuItemTable);
		
		List<MenuItem> menuItems = null;
		menuItems = ownerMenu.getAllMenuItems();
		MenuItemTable model = new MenuItemTable(menuItems);
		
		menuItemTable.setModel(model);
		
		MenuItemsButtonPanel = new JPanel();
		contentPane.add(MenuItemsButtonPanel, BorderLayout.SOUTH);
		
		addMenuItembutton = new JButton("Add Menu Item");
		MenuItemsButtonPanel.add(addMenuItembutton);
		
		updateMenuItemButton = new JButton("Update Price");
		MenuItemsButtonPanel.add(updateMenuItemButton);
		
		deleteMenuItemButton = new JButton("Delete Item");
		MenuItemsButtonPanel.add(deleteMenuItemButton);
		
	
	}

}

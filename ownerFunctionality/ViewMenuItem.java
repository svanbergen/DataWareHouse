package ownerFunctionality;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import utility.TableFromResultSet;

public class ViewMenuItem {
	Connection con;
	String username;
	JFrame menuFrame;
	JTable menuItems;

	public ViewMenuItem(Connection con, String username){
		this.con = con;
		this.username = username;

		menuFrame = new JFrame("Menu Items");
		JLabel menuPage = new JLabel("Menu Items");

		JPanel contentPane = new JPanel();
		menuFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();

		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		GridBagConstraints buttonC = new GridBagConstraints();
		buttonC.insets = new Insets(0, 0, 0, 10);
		buttonC.ipadx = 50;
		buttonC.anchor = GridBagConstraints.WEST;
		buttonC.gridx = 1;
		buttonC.gridy = 1;
		buttonC.gridwidth = 1;
		buttonC.fill = GridBagConstraints.HORIZONTAL;

		GridBagConstraints tableC = new GridBagConstraints();
		tableC.insets = new Insets(0, 0, 0, 0);
		tableC.fill = GridBagConstraints.NONE;
		tableC.gridy = 5;
		tableC.gridx = 1;
		tableC.ipadx = 500;
		tableC.gridwidth = 3;
		tableC.gridheight = 15;

		buttonC.gridy = 1;
		gb.setConstraints(menuPage, buttonC);
		GridBagConstraints gbc_menuPage = new GridBagConstraints();
		gbc_menuPage.insets = new Insets(0, 0, 5, 5);
		gbc_menuPage.gridx = 0;
		gbc_menuPage.gridy = 0;
		contentPane.add(menuPage, gbc_menuPage);

		menuItems = new JTable();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(menuItems);

		gb.setConstraints(scrollPane, tableC);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
//		JButton deleteButton = new JButton("Delete Menu Item");
//		deleteButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				try {
//					// get the selected row
//					int row = menuItems.getSelectedRow();
//					//System.out.println(row);
//
//					// make sure a row is selected
//					if (row < 0) {
//						JOptionPane.showMessageDialog(null, 
//								"You must select a menu item", "Error", JOptionPane.ERROR_MESSAGE);				
//						return;
//					}
//
//					// prompt the user
//					int response = JOptionPane.showConfirmDialog(
//							null, "Delete this menu item?", "Confirm", 
//							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//
//					if (response != JOptionPane.YES_OPTION) {
//						return;
//					}
//
//					// get the current menuitem
//
//					MenuItem temp = (MenuItem) menuItems.getValueAt(row, MenuItemTable.OBJECT_COL);
//
//					int selected = temp.getId();
//
//
//					// delete it
//					try{
//					
//						PreparedStatement stmt = con.prepareStatement("delete from MenuItem where menuItemID = ?");
//						stmt.setInt(1, selected);
//						ResultSet rs = stmt.executeQuery();
//						ResultSetMetaData rsmd = rs.getMetaData();
//						TableFromResultSet.replaceTable(menuItems, rs, rsmd);
//						
//
//					}
//					catch(SQLException ex){
//						System.out.println("Message: " + ex.getMessage());
//					}
//
//					// show success message
//					JOptionPane.showMessageDialog(null,
//							"Menu Item deleted succesfully.", "Menu Item Deleted",
//							JOptionPane.INFORMATION_MESSAGE);
//
//				} catch (Exception exc) {
//					JOptionPane.showMessageDialog(null,
//							"Error deleting menu item: " + exc.getMessage(), "Error",
//							JOptionPane.ERROR_MESSAGE);
//				}
//
//
//
//			}
//		});
//		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
//		gbc_deleteButton.gridx = 1;
//		gbc_deleteButton.gridy = 1;
//		contentPane.add(deleteButton, gbc_deleteButton);
//		
		
		
		
		try{

			PreparedStatement stmt = con.prepareStatement("select menuItem.menuitemid, menuitem.name, menuitem.itemtype, menuitem.price from menuitem, business where business.BusinessID = menuitem.businessid and business.ownerUsername = ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			TableFromResultSet.replaceTable(menuItems, rs, rsmd);
			

		}
		catch(SQLException ex){
			System.out.println("Message: " + ex.getMessage());
		}


		// Resize window
		menuFrame.pack();

		// Centre window
		Dimension d = menuFrame.getToolkit().getScreenSize();
		Rectangle r = menuFrame.getBounds();
		menuFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// Set window visible
		menuFrame.setVisible(true);


		// Attempt to load the Oracle JDBC driver
		try 
		{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			System.exit(-1);
		}

	}
}

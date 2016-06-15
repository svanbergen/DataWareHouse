package ownerFunctionality;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

import utility.TableFromResultSet;

// Class to view business menu items
public class ViewMenuItem {
	Connection con;
	String username;
	JFrame menuFrame;
	JTable menuItems;

	public ViewMenuItem(Connection con, String username){
		this.con = con;
		this.username = username;

		// Create frame
		menuFrame = new JFrame("Menu Items");
		
		// Create elements
		JLabel menuPage = new JLabel("Menu Items");
		JButton refreshButton = new JButton("Refresh Menu Items");

		// Create content panel and define layout
		JPanel contentPane = new JPanel();
		menuFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();

		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Constraint sets
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
		tableC.gridy = 3;
		tableC.gridx = 1;
		tableC.ipadx = 500;
		tableC.gridwidth = 3;
		tableC.gridheight = 15;
		
		// Populate layout
		gb.setConstraints(menuPage, buttonC);
		contentPane.add(menuPage);
		
		buttonC.gridy = 2;
		gb.setConstraints(refreshButton, buttonC);
		contentPane.add(refreshButton);

		// Create table
		menuItems = new JTable();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(menuItems);

		gb.setConstraints(scrollPane, tableC);
		contentPane.add(scrollPane);
		
		// Listener to generate table
		ActionListener refreshButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try{
					PreparedStatement stmt = con.prepareStatement("select menuitem.businessid, menuItem.menuitemid, menuitem.name, menuitem.itemtype, menuitem.price from menuitem, business where business.BusinessID = menuitem.businessid and business.ownerUsername = ?");
					stmt.setString(1, username);
					ResultSet rs = stmt.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					TableFromResultSet.replaceTable(menuItems, rs, rsmd);
				}
				catch(SQLException ex){
					System.out.println("Message: " + ex.getMessage());
				}
			}
		};
		refreshButton.addActionListener(refreshButtonListener);

		try{

			PreparedStatement stmt = con.prepareStatement("select menuitem.businessid, menuItem.menuitemid, menuitem.name, menuitem.itemtype, menuitem.price from menuitem, business where business.BusinessID = menuitem.businessid and business.ownerUsername = ?");
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

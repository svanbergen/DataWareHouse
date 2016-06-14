package ownerFunctionality;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import utility.TableFromResultSet;

// Class to delete a menu item
public class DeleteMenuItem {
	private Connection con;
	private JFrame addFrame;
	private String businessName;
	private String businessID;
	private String menuItemID;

	private JTextField idField;
	private JTextField bidField;
	private JTable results;

	// Constructor: builds the functionality window, handles the button press
	public DeleteMenuItem(Connection con, String username){
		this.con = con;
		this.businessName = username;

		// initialize parts of frame
		addFrame = new JFrame("Delete Menu Item");
		// Labels
		JLabel addLabel = new JLabel("Delete Menu Item");
		JLabel idLabel = new JLabel("Enter Menu Item ID: ");
		JLabel bidLabel = new JLabel("Your Business ID: ");


		// Text fields
		// Note: setMinimumSize prevents the fields from resizing on update

		idField = new JTextField(10);
		idField.setMinimumSize(idField.getPreferredSize());
		bidField = new JTextField(10);
		bidField.setMinimumSize(bidField.getPreferredSize());

		// Button
		JButton addButton = new JButton("Delete");

		// Create and populate the panel using GridBag for layout
		JPanel contentPane = new JPanel();
		addFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();

		// Preset constraint sets
		GridBagConstraints titleC = new GridBagConstraints();
		titleC.insets = new Insets(10, 10, 5, 0);
		titleC.anchor = GridBagConstraints.WEST;

		GridBagConstraints labelC = new GridBagConstraints();
		labelC.insets = new Insets(10, 10, 5, 0);
		labelC.anchor = GridBagConstraints.WEST;

		GridBagConstraints buttonC = new GridBagConstraints();
		buttonC.insets = new Insets(5, 10, 10, 10);
		buttonC.weightx=0;
		buttonC.fill=GridBagConstraints.NONE;
		buttonC.anchor = GridBagConstraints.WEST;

		GridBagConstraints fieldC = new GridBagConstraints();
		fieldC.insets = new Insets(10, 0, 5, 10);
		fieldC.anchor = GridBagConstraints.WEST;
		//fieldC.weightx=1.;
		//fieldC.fill=GridBagConstraints.HORIZONTAL;


		GridBagConstraints tableC = new GridBagConstraints();
		tableC.insets = new Insets(0, 0, 0, 0);
		tableC.fill = GridBagConstraints.NONE;
		tableC.gridy = 1;
		tableC.gridx = 3;
		tableC.ipadx = 500;
		tableC.gridwidth = 3;
		tableC.gridheight = 15;


		// Set layout and border
		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Add menu item label
		titleC.gridy = 1;
		titleC.gridx = 1;
		gb.setConstraints(addLabel, titleC);
		contentPane.add(addLabel);

		// name label
		GridBagConstraints nbhC = new GridBagConstraints();
		nbhC.gridy = 2;
		nbhC.gridx = 1;
		nbhC.insets = new Insets(10, 10, 5, 0);
		nbhC.anchor = GridBagConstraints.WEST;
		gb.setConstraints(idLabel, nbhC);
		contentPane.add(idLabel);

		// name field
		fieldC.gridy = 3;
		fieldC.gridx = 1;
		fieldC.insets = new Insets(5, 10, 10, 10);
		gb.setConstraints(idField, fieldC);
		contentPane.add(idField);


		// type label
		labelC.gridy = 6;
		labelC.gridx = 1;
		gb.setConstraints(bidLabel, labelC);
		contentPane.add(bidLabel);

		// type field
		fieldC.gridy = 7;
		fieldC.gridx = 1;
		gb.setConstraints(bidField, fieldC);
		contentPane.add(bidField);

		// Add button label 
		buttonC.gridy = 18;
		buttonC.gridx = 1;
		gb.setConstraints(addButton, buttonC);
		contentPane.add(addButton);

		// Error message
		JLabel errorMessage = new JLabel(" ");
		titleC.gridy = 19;
		titleC.gridx = 1;
		errorMessage.setForeground (Color.red);
		gb.setConstraints(errorMessage, titleC);
		contentPane.add(errorMessage);

		results = new JTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(results);
		scrollPane.setMinimumSize(scrollPane.getPreferredSize());

		gb.setConstraints(scrollPane, tableC);
		contentPane.add(scrollPane);
		try{
			PreparedStatement stmt = con.prepareStatement("select menuItem.businessid, menuItem.menuitemid, menuitem.name, menuitem.itemtype, menuitem.price from menuitem, business where business.BusinessID = menuitem.businessid and business.ownerUsername = ?");
			stmt.setString(1,username);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			TableFromResultSet.replaceTable(results, rs, rsmd);
		}
		catch(SQLException ex){
			System.out.println("Message: " + ex.getMessage());
			errorMessage.setText("Unexpected database error");
		}
		// Anonymous class to listen to delete button
		ActionListener buttonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				// Retrieve values from the fields

				try {
					if (idField.getText().equals("") || bidField.getText().equals("")) {
						errorMessage.setText("Please complete all fields");
						return;
					}
					else {

						// Attempt deletion
						try{
							String id = idField.getText();
							int i = Integer.parseInt(id);

							checkID();
							checkMenuItemExists();
							String Query2 = "delete from MenuItem where menuItemID = ?";
							PreparedStatement stmt2 = con.prepareStatement(Query2); 

							stmt2.setInt(1, i);

							stmt2.executeQuery();

							PreparedStatement stmt3 = con.prepareStatement("select menuItem.menuitemid, menuitem.name, menuitem.itemtype, menuitem.price from menuitem, business where business.BusinessID = menuitem.businessid and business.ownerUsername = ?");
							stmt3.setString(1,username);
							ResultSet rs = stmt3.executeQuery();
							ResultSetMetaData rsmd = rs.getMetaData();
							TableFromResultSet.replaceTable(results, rs, rsmd);

						}
						catch (SQLException ex){
							System.out.println("Message: " + ex.getMessage());
							errorMessage.setText("Invalid input");
						}
					}
				}
				catch (Exception e2) {
					errorMessage.setText("DENIED: " + e2.getMessage());
					return;
				}
			}
		};addButton.addActionListener(buttonListener);

		// Resize window
		addFrame.pack();

		// Centre window
		Dimension d = addFrame.getToolkit().getScreenSize();
		Rectangle r = addFrame.getBounds();
		addFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// Set window visible
		addFrame.setVisible(true);


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


	// Method to check that item exists
	protected void checkMenuItemExists() throws Exception {
		String Query1 = "select menuItemID from MenuItem";
		try {
			menuItemID = idField.getText();
			businessID = bidField.getText();

		} catch (Exception e) {

			System.out.println("Invalid format for MenuItemID");
			System.out.println("Message: " + e.getMessage());

		}

		PreparedStatement stmt = con.prepareStatement(Query1);
		//check if menuitem exists
		ResultSet rSet = stmt.executeQuery();

		while (rSet.next()) {
			if(rSet.getString("menuItemID").equals(menuItemID)) {
				return;
			}
		}
		throw new Exception("No menu items associated with ID entered");

	}



	// Method to check that id is valid and matches the owner
	protected void checkID() throws Exception {
		try {
			businessID = bidField.getText(); 
		} catch (Exception e) {

			System.out.println("Invalid format for BusinessID");
			System.out.println("Message: " + e.getMessage());

		}

		//System.out.print("BusinessID parsed is: " + businessID);

		PreparedStatement pstmd = con.prepareStatement("select ownerUsername from business where business.businessid = ?");
		pstmd.setString(1, businessID);


		ResultSet rs = pstmd.executeQuery();

		// Check if there is an owner attached to the id

		// if there isn't any, return false
		if (!rs.next()) {
			throw new Exception("No business associated with ID entered");

		}

		if (rs.getString("ownerUserName").equals(businessName)) {
			return;
		}

		throw new Exception("BusinessID does not match Owner");		
	}
}


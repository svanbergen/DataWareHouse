package ownerFunctionality;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import utility.TableFromResultSet;

// Class to an a new menu item to the database
public class AddMenuItem {
	private Connection con;
	private JFrame addFrame;
	private String businessID;
	private String businessName;
	private JTable results;

	private JTextField nameField;
	private JTextField typeField;
	private JTextField priceField;
	private JTextField businessIdField;

	// Constructor: builds the functionality window, handles the button press
	public AddMenuItem(Connection con, String username){
		this.con = con;
		this.businessName = username;

		// /initialize parts of frame
		addFrame = new JFrame("Add Menu Item");
		// Labels
		JLabel addLabel = new JLabel("Add New Menu Item");
		JLabel nameLabel = new JLabel("Enter Menu Item name: ");
		JLabel typeLabel = new JLabel("Enter Type/Category: ");
		JLabel priceLabel = new JLabel("Enter Price: ");
		JLabel businessIdLabel = new JLabel("Your Business ID: ");


		// Text fields
		// Note: setMinimumSize prevents the fields from resizing on update

		nameField = new JTextField(10);
		nameField.setMinimumSize(nameField.getPreferredSize());
		typeField = new JTextField(10);
		typeField.setMinimumSize(typeField.getPreferredSize());
		priceField = new JTextField(10);
		priceField.setMinimumSize(priceField.getPreferredSize());
		businessIdField = new JTextField(10);
		businessIdField.setMinimumSize(businessIdField.getPreferredSize());

		// Button
		JButton addButton = new JButton("Save");


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
		gb.setConstraints(nameLabel, nbhC);
		contentPane.add(nameLabel);

		// name field
		fieldC.gridy = 3;
		fieldC.gridx = 1;
		fieldC.insets = new Insets(5, 10, 10, 10);
		gb.setConstraints(nameField, fieldC);
		contentPane.add(nameField);


		// type label
		labelC.gridy = 6;
		labelC.gridx = 1;
		gb.setConstraints(typeLabel, labelC);
		contentPane.add(typeLabel);

		// type field
		fieldC.gridy = 7;
		fieldC.gridx = 1;
		gb.setConstraints(typeField, fieldC);
		contentPane.add(typeField);

		// Price label 
		labelC.gridy = 8;
		labelC.gridx = 1;
		gb.setConstraints(priceLabel, labelC);
		contentPane.add(priceLabel);

		// price field
		fieldC.gridy = 9;
		fieldC.gridx = 1;
		gb.setConstraints(priceField, fieldC);
		contentPane.add(priceField);

		// businessid label 
		labelC.gridy = 10;
		labelC.gridx = 1;
		gb.setConstraints(businessIdLabel, labelC);
		contentPane.add(businessIdLabel);

		// businessid field
		fieldC.gridy = 11;
		fieldC.gridx = 1;
		gb.setConstraints(businessIdField, fieldC);
		contentPane.add(businessIdField);


		// Add button label 
		buttonC.gridy = 18;
		buttonC.gridx = 1;
		gb.setConstraints(addButton, buttonC);
		contentPane.add(addButton);

		// Error message
		JLabel errorMessage = new JLabel(" ");
		titleC.gridy = 19;
		titleC.gridx = 1;
		titleC.gridwidth = 2;
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
			PreparedStatement stmt = con.prepareStatement("select menuitem.businessid, menuItem.menuitemid, menuitem.name, menuitem.itemtype, menuitem.price from menuitem, business where business.BusinessID = menuitem.businessid and business.ownerUsername = ?");
			stmt.setString(1,username);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			TableFromResultSet.replaceTable(results, rs, rsmd);
		}
		catch(SQLException ex){
			System.out.println("Message: " + ex.getMessage());
			errorMessage.setText("Unexpected database error");
		}
		// Anonymous class to listen to add business button
		ActionListener buttonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				// Retrieve values from the fields
				String name = nameField.getText();
				String type = typeField.getText();
				String price = priceField.getText();


				// Construct insertion 
				String loginQuery = "insert into menuItem values (1, ?, ?, ?, ?)";

				// Attempt insertion
				try {			
					checkID();
					PreparedStatement stmt = con.prepareStatement(loginQuery);
					float p = Float.parseFloat(price);

					stmt.setFloat(1, p);


					if(type.equals("")){
						stmt.setNull(2, Types.VARCHAR);
					}
					else{
						stmt.setString(2, type);
					}

					stmt.setString(3, name);	

					int i = Integer.parseInt(businessID);
					stmt.setInt(4, i);
					stmt.executeQuery();

					PreparedStatement stmt2 = con.prepareStatement("select menuitem.businessid, select menuItem.menuitemid, menuitem.name, menuitem.itemtype, menuitem.price from menuitem, business where business.BusinessID = menuitem.businessid and business.ownerUsername = ?");
					stmt2.setString(1,username);
					ResultSet rs = stmt2.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					TableFromResultSet.replaceTable(results, rs, rsmd);
					errorMessage.setText("");

				}
				catch (SQLException ex) {
					System.out.println("Message: " + ex.getMessage());
					errorMessage.setText("DENIED: Invalid input");
				} catch (Exception e1) {
					errorMessage.setText("DENIED: " + e1.getMessage());
					return;
				}			
			}
		}; 
		addButton.addActionListener(buttonListener);

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


	// Method to check ID
	protected void checkID() throws Exception{
		try {
			businessID = businessIdField.getText(); 
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

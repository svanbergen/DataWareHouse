package ownerFunctionality;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.*;

import utility.TableFromResultSet;

public class UpdateMenuItemPrice {
	private Connection con;
	private JFrame updateFrame;
	private JTextField idField;
	private JTextField priceField;

	private JTable results;

	private String businessName;
	private String businessID;
	private JTextField bidField;

	public UpdateMenuItemPrice(Connection con, String username){
		this.con = con;
		this.businessName = username;

		// Define/initialize parts of frame
		updateFrame = new JFrame("Set New Menu Item Price");
		// Labels
		JLabel titleLabel = new JLabel("Update Menu Item Price");
		JLabel idLabel = new JLabel("Enter Menu Item ID: ");
		JLabel priceLabel = new JLabel("Enter New Price: ");
		JLabel bidLabel = new JLabel("Your Business ID: ");


		idField = new JTextField(10);
		idField.setMinimumSize(idField.getPreferredSize());
		priceField = new JTextField(10);
		priceField.setMinimumSize(priceField.getPreferredSize());
		bidField = new JTextField(10);
		bidField.setMinimumSize(bidField.getPreferredSize());

		// Button
		JButton updateButton = new JButton("Update");

		// Create and populate the panel using GridBag for layout
		JPanel contentPane = new JPanel();
		updateFrame.setContentPane(contentPane);
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

		// Add Business label 
		titleC.gridy = 1;
		titleC.gridx = 1;
		gb.setConstraints(titleLabel, titleC);
		contentPane.add(titleLabel);

		// Unit label
		titleC.gridy = 5;
		gb.setConstraints(idLabel, titleC);
		contentPane.add(idLabel);

		// Unit field
		fieldC.gridy = 6;
		fieldC.gridx = 1;
		gb.setConstraints(idField, fieldC);
		contentPane.add(idField);

		// Address label
		titleC.gridy = 7;
		gb.setConstraints(priceLabel, titleC);
		contentPane.add(priceLabel);

		// Address field
		fieldC.gridy = 8;
		fieldC.gridx = 1;
		gb.setConstraints(priceField, fieldC);
		contentPane.add(priceField);
		
		// businessid label 
		labelC.gridy = 10;
		labelC.gridx = 1;
		gb.setConstraints(bidLabel, labelC);
		contentPane.add(bidLabel);

		// businessid field
		fieldC.gridy = 11;
		fieldC.gridx = 1;
		gb.setConstraints(bidField, fieldC);
		contentPane.add(bidField);

		// Add button label 
		buttonC.gridy = 15;
		buttonC.gridx = 1;
		gb.setConstraints(updateButton, buttonC);
		contentPane.add(updateButton);

		// Error message
		JLabel errorMessage = new JLabel(" ");
		titleC.gridy = 16;
		titleC.gridx = 3;
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
			PreparedStatement stmt = con.prepareStatement("select menuItem.menuitemid, menuitem.name, menuitem.itemtype, menuitem.price from menuitem, business where business.BusinessID = menuitem.businessid and business.ownerUsername = ?");
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
				try {
					if (idField.getText().equals("") || bidField.getText().equals("")) {
						errorMessage.setText("Please complete all fields");
						return;
					}

					else{
				try {
						// Check if missing
						String id = idField.getText();
						String price = priceField.getText();
						if(id.equals("") || price.equals("")){
							errorMessage.setText("Must enter values");
							return;
						}
						else{
					
							// Construct deletion
							String loginQuery = "update menuItem set Price = ? where menuItemID = ?";

							// Attempt deletion
				
								PreparedStatement stmt = con.prepareStatement(loginQuery);

								float p = Float.parseFloat(price);

								stmt.setFloat(1, p);

								int i = Integer.parseInt(id);

								stmt.setInt(2, i);

								stmt.executeQuery();
						}
						
								PreparedStatement stmt2 = con.prepareStatement("select menuItem.menuitemid, menuitem.name, menuitem.itemtype, menuitem.price from menuitem, business where business.BusinessID = menuitem.businessid and business.ownerUsername = ?");
								stmt2.setString(1,username);
								ResultSet rs = stmt2.executeQuery();
								ResultSetMetaData rsmd = rs.getMetaData();
								TableFromResultSet.replaceTable(results, rs, rsmd);
						
				}
							catch(SQLException ex){
								System.out.println("Message: " + ex.getMessage());
								errorMessage.setText("Invalid input");
							}
						}
				}
					catch(Exception e2) {
						errorMessage.setText("DENIED: " + e2.getMessage());
						return;
					}
						}
					};
				
			
			


		updateButton.addActionListener(buttonListener);

		// Resize window
		updateFrame.pack();

		// Centre window
		Dimension d = updateFrame.getToolkit().getScreenSize();
		Rectangle r = updateFrame.getBounds();
		updateFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// Set window visible
		updateFrame.setVisible(true);
	}
	

	protected void checkID() throws Exception {
		// TODO Auto-generated method stub
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

package ownerFunctionality;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

// Class to delete a business
public class DeleteBusiness {
	private JFrame delFrame;

	private JTextField iDField;

	// Constructor: builds the functionality window, handles the button press
	public DeleteBusiness(Connection con, String username){

		// Define/initialize parts of frame
		delFrame = new JFrame("Delete Business");
		// Labels
		JLabel deleteLabel = new JLabel("Delete a Business");
		JLabel warningLabel = new JLabel("WARNING: All information about a deleted business will be lost");
		JLabel idLabel = new JLabel("Enter id: ");

		// Text fields
		// Note: setMinimumSize prevents the fields from resizing on update
		iDField = new JTextField(10);
		iDField.setMinimumSize(iDField.getPreferredSize());

		JButton deleteButton = new JButton("Confirm Delete");

		// Create and populate the panel using GridBag for layout
		JPanel contentPane = new JPanel();
		delFrame.setContentPane(contentPane);
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

		// Set layout and border
		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		titleC.gridy = 1;
		gb.setConstraints(deleteLabel, titleC);
		contentPane.add(deleteLabel);

		titleC.gridy = 2;
		gb.setConstraints(warningLabel, titleC);
		contentPane.add(warningLabel);

		labelC.gridy = 3;
		gb.setConstraints(idLabel, labelC);
		contentPane.add(idLabel);

		fieldC.gridy = 4;
		gb.setConstraints(iDField, fieldC);
		contentPane.add(iDField);

		// Add button label 
		buttonC.gridy = 5;
		buttonC.gridx = 1;
		gb.setConstraints(deleteButton, buttonC);
		contentPane.add(deleteButton);

		// Error message
		JLabel errorMessage = new JLabel(" ");
		titleC.gridy = 6;
		titleC.gridx = 1;
		errorMessage.setForeground (Color.red);
		gb.setConstraints(errorMessage, titleC);
		contentPane.add(errorMessage);

		// Button listener
		ActionListener submitButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try{
					// Check that user owns business
					String id = iDField.getText();
					PreparedStatement check = con.prepareStatement("select ownerUsername from business where businessid = ?");
					check.setInt(1, Integer.parseInt(id));
					ResultSet checkRS = check.executeQuery();
					checkRS.next();
					if(!checkRS.getString("ownerUsername").equals(username)){
						errorMessage.setText("You do not own this business");
						return;
					}

					// Delete business
					check = con.prepareStatement("delete from business where businessid = ?");
					check.setInt(1, Integer.parseInt(id));
					checkRS = check.executeQuery();

					delFrame.dispose();
				}
				catch(SQLException ex){
					System.out.println("Message: " + ex.getMessage());
					errorMessage.setText("Unexpected database error");
				}
			}
		};
		deleteButton.addActionListener(submitButtonListener);

		// Resize window
		delFrame.pack();

		// Centre window
		Dimension d = delFrame.getToolkit().getScreenSize();
		Rectangle r = delFrame.getBounds();
		delFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// Set window visible
		delFrame.setVisible(true);


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

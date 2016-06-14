package ownerFunctionality;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;


public class DeleteMenuItem {
	private Connection con;
	private String username;
	private JFrame addFrame;


	private JTextField idField;
	private JTextField usernameField;
	private JTextField bidField;



	// Constructor: builds the functionality window, handles the button press
	public DeleteMenuItem(Connection con, String username){
		this.con = con;
		this.username = username;

		// /initialize parts of frame
		addFrame = new JFrame("Delete Menu Item");
		JLabel idLabel = new JLabel("Enter Menu Item ID: ");


		// Create and populate the panel using GridBag for layout
		JPanel contentPane = new JPanel();
		addFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();
		gb.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0};

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

		// delete menu item label
		titleC.gridy = 1;
		titleC.gridx = 1;

		// id label
		GridBagConstraints nbhC = new GridBagConstraints();
		nbhC.gridy = 2;
		nbhC.gridx = 1;
		nbhC.insets = new Insets(10, 10, 5, 0);
		nbhC.anchor = GridBagConstraints.WEST;
		// Labels
		JLabel deleteLabel = new JLabel("Delete Menu Item");
		deleteLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		gb.setConstraints(deleteLabel, titleC);
		GridBagConstraints gbc_deleteLabel = new GridBagConstraints();
		gbc_deleteLabel.insets = new Insets(0, 0, 5, 5);
		gbc_deleteLabel.gridx = 2;
		gbc_deleteLabel.gridy = 0;
		contentPane.add(deleteLabel, gbc_deleteLabel);
		gb.setConstraints(idLabel, nbhC);
		GridBagConstraints gbc_idLabel = new GridBagConstraints();
		gbc_idLabel.insets = new Insets(0, 0, 5, 5);
		gbc_idLabel.gridx = 2;
		gbc_idLabel.gridy = 1;
		contentPane.add(idLabel, gbc_idLabel);

		// id field
		fieldC.gridy = 3;
		fieldC.gridx = 1;
		fieldC.insets = new Insets(5, 10, 10, 10);



		// Add button label 
		buttonC.gridy = 18;
		buttonC.gridx = 1;

		// Error message
		JLabel errorMessage = new JLabel(" ");
		titleC.gridy = 19;
		titleC.gridx = 1;
		
		
		// Text fields
		// Note: setMinimumSize prevents the fields from resizing on update

		idField = new JTextField(10);
		idField.setMinimumSize(idField.getPreferredSize());
		gb.setConstraints(idField, fieldC);
		GridBagConstraints gbc_idField = new GridBagConstraints();
		gbc_idField.insets = new Insets(0, 0, 5, 5);
		gbc_idField.gridx = 3;
		gbc_idField.gridy = 1;
		contentPane.add(idField, gbc_idField);
		errorMessage.setForeground (Color.red);
		gb.setConstraints(errorMessage, titleC);
		GridBagConstraints gbc_errorMessage = new GridBagConstraints();
		gbc_errorMessage.insets = new Insets(0, 0, 5, 0);
		gbc_errorMessage.gridx = 7;
		gbc_errorMessage.gridy = 1;
		contentPane.add(errorMessage, gbc_errorMessage);
		
		JLabel lblEnterUsername = new JLabel("Enter Username:");
		GridBagConstraints gbc_lblEnterUsername = new GridBagConstraints();
		gbc_lblEnterUsername.anchor = GridBagConstraints.EAST;
		gbc_lblEnterUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterUsername.gridx = 2;
		gbc_lblEnterUsername.gridy = 2;
		contentPane.add(lblEnterUsername, gbc_lblEnterUsername);
		
		usernameField = new JTextField();
		GridBagConstraints gbc_usernameField = new GridBagConstraints();
		gbc_usernameField.insets = new Insets(0, 0, 5, 5);
		gbc_usernameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_usernameField.gridx = 3;
		gbc_usernameField.gridy = 2;
		contentPane.add(usernameField, gbc_usernameField);
		usernameField.setColumns(10);
		
		JLabel lblEnterBusinessId = new JLabel("Enter Business ID:");
		GridBagConstraints gbc_lblEnterBusinessId = new GridBagConstraints();
		gbc_lblEnterBusinessId.anchor = GridBagConstraints.EAST;
		gbc_lblEnterBusinessId.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterBusinessId.gridx = 2;
		gbc_lblEnterBusinessId.gridy = 3;
		contentPane.add(lblEnterBusinessId, gbc_lblEnterBusinessId);
				
				bidField = new JTextField();
				GridBagConstraints gbc_bidField = new GridBagConstraints();
				gbc_bidField.insets = new Insets(0, 0, 5, 5);
				gbc_bidField.fill = GridBagConstraints.HORIZONTAL;
				gbc_bidField.gridx = 3;
				gbc_bidField.gridy = 3;
				contentPane.add(bidField, gbc_bidField);
				bidField.setColumns(10);
				
			

		// Anonymous class to listen to add business button
		ActionListener buttonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				// Retrieve values from the fields
				String id = idField.getText();

				// Construct deletion 
				String loginQuery = "delete from menuItem where menuItemID = ?";

				// Attempt deletion
				try{
					PreparedStatement stmt = con.prepareStatement(loginQuery);
						
						int i = Integer.parseInt(id);
						
						stmt.setInt(1, i);
						stmt.executeQuery();
					addFrame.dispose();

				}
				catch (SQLException ex)
				{
					System.out.println("Message: " + ex.getMessage());
					errorMessage.setText("Invalid input");
				}	


			}
		};
		
		// Button
		JButton addButton = new JButton("Delete");
		gb.setConstraints(addButton, buttonC);
		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.insets = new Insets(0, 0, 0, 5);
		gbc_addButton.gridx = 3;
		gbc_addButton.gridy = 4;
		contentPane.add(addButton, gbc_addButton);
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


	}


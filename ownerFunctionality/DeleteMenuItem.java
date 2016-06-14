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


public class DeleteMenuItem {
	private Connection con;
	private String username;
	private JFrame addFrame;


	private JTextField idField;



	// Constructor: builds the functionality window, handles the button press
	public DeleteMenuItem(Connection con, String username){
		this.con = con;
		this.username = username;

		// /initialize parts of frame
		addFrame = new JFrame("Delete Menu Item");
		// Labels
		JLabel deleteLabel = new JLabel("Delete Menu Item");
		JLabel idLabel = new JLabel("Enter Menu Item ID: ");
		
		
		// Text fields
		// Note: setMinimumSize prevents the fields from resizing on update

		idField = new JTextField(10);
		idField.setMinimumSize(idField.getPreferredSize());

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

		// Set layout and border
		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// delete menu item label
		titleC.gridy = 1;
		titleC.gridx = 1;
		gb.setConstraints(deleteLabel, titleC);
		contentPane.add(deleteLabel);

		// id label
		GridBagConstraints nbhC = new GridBagConstraints();
		nbhC.gridy = 2;
		nbhC.gridx = 1;
		nbhC.insets = new Insets(10, 10, 5, 0);
		nbhC.anchor = GridBagConstraints.WEST;
		gb.setConstraints(idLabel, nbhC);
		contentPane.add(idLabel);

		// id field
		fieldC.gridy = 3;
		fieldC.gridx = 1;
		fieldC.insets = new Insets(5, 10, 10, 10);
		gb.setConstraints(idField, fieldC);
		contentPane.add(idField);



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


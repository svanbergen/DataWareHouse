package customerFunctionality;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class WriteReview {

	String username;
	Integer[] ratings = {1,2,3,4,5};
	Connection con;
	JFrame reviewFrame;
	JTable reviews;

	public WriteReview(Connection con, String username){
		this.con = con;
		this.username = username;

		// Create frame
		reviewFrame = new JFrame("Write Review");

		// Create GUI elements
		JLabel reviewPage = new JLabel("Write Review");
		JLabel idFieldLabel = new JLabel("Enter ID of business to review");
		JTextField bIdField = new JTextField(10);
		JLabel ratingLabel = new JLabel("Select rating");
		JComboBox<Integer> ratingBox = new JComboBox<Integer>(ratings);
		JLabel writeComment = new JLabel("Write comment");
		JTextArea commentArea = new JTextArea(4, 50);
		JButton submitButton = new JButton("Submit");

		// Create content panel and define layout
		JPanel contentPane = new JPanel();
		reviewFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();

		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Create constraint sets
		GridBagConstraints buttonC = new GridBagConstraints();
		buttonC.insets = new Insets(0, 0, 0, 10);
		buttonC.ipadx = 50;
		buttonC.anchor = GridBagConstraints.WEST;
		buttonC.gridx = 1;
		buttonC.gridy = 1;
		buttonC.gridwidth = 1;
		buttonC.fill = GridBagConstraints.HORIZONTAL;

		GridBagConstraints areaC = new GridBagConstraints();
		areaC.insets = new Insets(0, 0, 0, 10);
		areaC.ipadx = 50;
		areaC.anchor = GridBagConstraints.WEST;
		areaC.gridx = 1;
		areaC.gridy = 7;
		areaC.gridwidth = 2;
		areaC.fill = GridBagConstraints.HORIZONTAL;

		// Populate content panel
		gb.setConstraints(reviewPage, buttonC);
		contentPane.add(reviewPage);

		buttonC.gridy = 2;
		gb.setConstraints(idFieldLabel, buttonC);
		contentPane.add(idFieldLabel);

		buttonC.gridy = 3;
		gb.setConstraints(bIdField, buttonC);
		contentPane.add(bIdField);

		buttonC.gridy = 4;
		gb.setConstraints(ratingLabel, buttonC);
		contentPane.add(ratingLabel);

		buttonC.gridy = 5;
		gb.setConstraints(ratingBox, buttonC);
		contentPane.add(ratingBox);

		buttonC.gridy = 6;
		gb.setConstraints(writeComment, buttonC);
		contentPane.add(writeComment);

		gb.setConstraints(commentArea, areaC);
		contentPane.add(commentArea);

		buttonC.gridy = 8;
		gb.setConstraints(submitButton, buttonC);
		contentPane.add(submitButton);

		// Error message
		JLabel errorMessage = new JLabel(" ");
		buttonC.gridy = 9;
		errorMessage.setForeground (Color.red);
		gb.setConstraints(errorMessage, buttonC);
		contentPane.add(errorMessage);

		// Listener for submit button to add review
		ActionListener submitButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try{
					String id = bIdField.getText();
					String comment = commentArea.getText();
					// Check id valid
					if(!id.matches("[0-9]+")){
						errorMessage.setText("Invalid id");
						return;
					}
					PreparedStatement stmt = con.prepareStatement("insert into review values(1, ?, ?, ?, ?, ?)");
					stmt.setInt(1, (int) ratingBox.getSelectedItem());

					// Check if comment should be null
					if(comment.equals("")){
						stmt.setNull(2, Types.VARCHAR);
					}
					else{
						stmt.setString(2, comment);
					}

					java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
					stmt.setDate(3, sqlDate);
					stmt.setInt(4, Integer.parseInt(id));
					stmt.setString(5, username);
					stmt.executeQuery();
					reviewFrame.dispose();
				}
				catch(SQLException ex){
					System.out.println("Message: " + ex.getMessage());
					errorMessage.setText("Unexpected database error. Business may not exist.");
				}
			}
		};
		submitButton.addActionListener(submitButtonListener);

		// Resize window
		reviewFrame.pack();

		// Centre window
		Dimension d = reviewFrame.getToolkit().getScreenSize();
		Rectangle r = reviewFrame.getBounds();
		reviewFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// Set window visible
		reviewFrame.setVisible(true);


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

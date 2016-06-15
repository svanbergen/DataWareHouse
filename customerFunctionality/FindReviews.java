package customerFunctionality;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import utility.TableFromResultSet;

// Class to find reviews written about a particular business
public class FindReviews {

	Connection con;
	JFrame reviewSearchFrame;
	JTable reviews;

	public FindReviews(Connection con){
		this.con = con;

		// Create frame
		reviewSearchFrame = new JFrame("View Reviews");
		
		// Create labels
		JLabel reviewPage = new JLabel("Search for Reviews");
		JLabel idFieldLabel = new JLabel("Enter ID of business");
		JTextField bIdField = new JTextField(10);
		JButton searchButton = new JButton("Search for Reviews");

		// Create content panel and define layout
		JPanel contentPane = new JPanel();
		reviewSearchFrame.setContentPane(contentPane);
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

		GridBagConstraints tableC = new GridBagConstraints();
		tableC.insets = new Insets(0, 0, 0, 0);
		tableC.fill = GridBagConstraints.NONE;
		tableC.gridy = 5;
		tableC.gridx = 1;
		tableC.ipadx = 500;
		tableC.gridwidth = 3;
		tableC.gridheight = 15;

		// Populate content panel
		buttonC.gridy = 1;
		gb.setConstraints(reviewPage, buttonC);
		contentPane.add(reviewPage);

		buttonC.gridy = 2;
		gb.setConstraints(idFieldLabel, buttonC);
		contentPane.add(idFieldLabel);

		buttonC.gridy = 3;
		gb.setConstraints(bIdField, buttonC);
		contentPane.add(bIdField);

		buttonC.gridy = 4;
		gb.setConstraints(searchButton, buttonC);
		contentPane.add(searchButton);

		// Create table
		reviews = new JTable();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(reviews);

		gb.setConstraints(scrollPane, tableC);
		contentPane.add(scrollPane);

		// Button listener to search
		ActionListener searchButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try{
					String id = bIdField.getText();
					PreparedStatement stmt = con.prepareStatement("select * from review left outer join reply on review.reviewid = reply.reviewid where businessid = ?");
					stmt.setString(1, id);
					ResultSet rs = stmt.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					TableFromResultSet.replaceTable(reviews, rs, rsmd);
				}
				catch(SQLException ex){
					System.out.println("Message: " + ex.getMessage());
				}
			}
		};
		searchButton.addActionListener(searchButtonListener);
		bIdField.addActionListener(searchButtonListener);

		// Resize window
		reviewSearchFrame.pack();

		// Centre window
		Dimension d = reviewSearchFrame.getToolkit().getScreenSize();
		Rectangle r = reviewSearchFrame.getBounds();
		reviewSearchFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// Set window visible
		reviewSearchFrame.setVisible(true);

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

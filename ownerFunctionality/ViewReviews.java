package ownerFunctionality;

import java.awt.*;
import java.sql.*;

import javax.swing.*;

import utility.TableFromResultSet;

// Class to view reviews for owner's business
public class ViewReviews {
	Connection con;
	String username;
	JFrame reviewFrame;
	JTable reviews;

	public ViewReviews(Connection con, String username){
		this.con = con;
		this.username = username;

		// Create frame
		reviewFrame = new JFrame("Reviews of your Businesses");

		// Create label
		JLabel reviewPage = new JLabel("Reviews of your Businesses");

		// Create content panel and define layout
		JPanel contentPane = new JPanel();
		reviewFrame.setContentPane(contentPane);
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
		tableC.gridy = 5;
		tableC.gridx = 1;
		tableC.ipadx = 500;
		tableC.gridwidth = 3;
		tableC.gridheight = 15;

		// Populate layout
		buttonC.gridy = 1;
		gb.setConstraints(reviewPage, buttonC);
		contentPane.add(reviewPage);

		// Create table
		reviews = new JTable();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(reviews);

		gb.setConstraints(scrollPane, tableC);
		contentPane.add(scrollPane);
		try{

			PreparedStatement stmt = con.prepareStatement("select ReviewID, Rating, Comments, ReviewDate, business.BusinessID, CustomerUsername from review, business where review.BusinessID = business.BusinessID and business.ownerUsername = ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			TableFromResultSet.replaceTable(reviews, rs, rsmd);
		}
		catch(SQLException ex){
			System.out.println("Message: " + ex.getMessage());
		}

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

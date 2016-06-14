package ownerFunctionality;

import java.awt.*;
import java.sql.*;

import javax.swing.*;

import utility.TableFromResultSet;

// Class to display "big spenders" - customers that have made orders for >100 dollars
public class ViewBigSpenders {

	Connection con;
	String username;
	JFrame bigSpenderFrame;
	JTable spenders;

	public ViewBigSpenders(Connection con, String username){
		this.con = con;
		this.username = username;

		// Create frame
		bigSpenderFrame = new JFrame("Big Spenders at Your Businesses");
		// Create label
		JLabel spenderPage = new JLabel("Big Spenders at Your Businesses");

		// Create content panel and define layout
		JPanel contentPane = new JPanel();
		bigSpenderFrame.setContentPane(contentPane);
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

		buttonC.gridy = 1;
		gb.setConstraints(spenderPage, buttonC);
		contentPane.add(spenderPage);

		// Create table
		spenders = new JTable();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(spenders);

		gb.setConstraints(scrollPane, tableC);
		contentPane.add(scrollPane);
		try{

			PreparedStatement stmt = con.prepareStatement("select bigspenders.businessid, bigspenders.customerusername, bigspenders.orderid, bigspenders.price from bigspenders, business where business.BusinessID = bigspenders.businessid and business.ownerUsername = ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			TableFromResultSet.replaceTable(spenders, rs, rsmd);
		}
		catch(SQLException ex){
			System.out.println("Message: " + ex.getMessage());
		}


		// Resize window
		bigSpenderFrame.pack();

		// Centre window
		Dimension d = bigSpenderFrame.getToolkit().getScreenSize();
		Rectangle r = bigSpenderFrame.getBounds();
		bigSpenderFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// Set window visible
		bigSpenderFrame.setVisible(true);
	}
}

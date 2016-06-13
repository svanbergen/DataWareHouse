package ownerFunctionality;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import utility.TableFromResultSet;

public class ViewBigSpenders {
	
	Connection con;
	String username;
	JFrame bigSpenderFrame;
	JTable reviews;

	public ViewBigSpenders(Connection con, String username){
		this.con = con;
		this.username = username;

		bigSpenderFrame = new JFrame("Big Spenders at Your Businesses");
		JLabel spenderPage = new JLabel("Big Spenders at Your Businesses");

		JPanel contentPane = new JPanel();
		bigSpenderFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();

		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

		reviews = new JTable();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(reviews);

		gb.setConstraints(scrollPane, tableC);
		contentPane.add(scrollPane);
		try{

			PreparedStatement stmt = con.prepareStatement("select bigspenders.businessid, bigspenders.customerusername, bigspenders.orderid, bigspenders.price from bigspenders, business where business.BusinessID = bigspenders.businessid and business.ownerUsername = ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			TableFromResultSet.replaceTable(reviews, rs, rsmd);
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

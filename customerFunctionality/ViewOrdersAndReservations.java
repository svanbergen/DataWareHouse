package customerFunctionality;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.sql.*;
import utility.TableFromResultSet;

// Class to view orders and reservations
public class ViewOrdersAndReservations {
	Connection con;
	String username;
	JFrame viewFrame;
	JTable reservations;
	JTable orders;

	public ViewOrdersAndReservations(Connection con, String username){
		this.con = con;
		this.username = username;

		// Create frame
		viewFrame = new JFrame("Your Orders and Reservations");

		// Create labels
		JLabel pageLabel = new JLabel("Your Orders and Reservations");
		JLabel orderLabel = new JLabel("Your Orders:");
		JLabel reservationLabel = new JLabel("Your Reservations:");

		// Create button
		JButton refreshButton = new JButton("Refresh");

		// Create content pane and define layout
		JPanel contentPane = new JPanel();
		JScrollPane scrollContentPane = new JScrollPane(contentPane);
		viewFrame.setContentPane(scrollContentPane);
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

		GridBagConstraints titleC = new GridBagConstraints();
		titleC.insets = new Insets(10, 10, 5, 0);
		titleC.anchor = GridBagConstraints.WEST;

		// Populate layout
		buttonC.gridy = 1;
		gb.setConstraints(pageLabel, buttonC);
		contentPane.add(pageLabel);

		buttonC.gridy = 2;
		gb.setConstraints(refreshButton, buttonC);
		contentPane.add(refreshButton);

		buttonC.gridy = 4;
		gb.setConstraints(orderLabel, buttonC);
		contentPane.add(orderLabel);

		// Error message
		JLabel errorMessage = new JLabel(" ");
		titleC.gridy = 3;
		titleC.gridx = 1;
		errorMessage.setForeground (Color.red);
		gb.setConstraints(errorMessage, titleC);
		contentPane.add(errorMessage);

		// Create orders table
		orders = new JTable();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(orders);

		gb.setConstraints(scrollPane, tableC);
		contentPane.add(scrollPane);
		try{

			PreparedStatement stmt = con.prepareStatement("select * from orders where customerUsername = ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			TableFromResultSet.replaceTable(orders, rs, rsmd);
		}
		catch(SQLException ex){
			System.out.println("Message: " + ex.getMessage());
			errorMessage.setText("An unexpected database error occurred");
		}

		buttonC.gridy = 21;
		gb.setConstraints(reservationLabel, buttonC);
		contentPane.add(reservationLabel);

		// Create reservations table
		reservations = new JTable();

		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setViewportView(reservations);

		tableC.gridy = 22;
		gb.setConstraints(scrollPane2, tableC);
		contentPane.add(scrollPane2);
		try{

			PreparedStatement stmt = con.prepareStatement("select * from reservation where customerUsername = ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			TableFromResultSet.replaceTable(reservations, rs, rsmd);
		}
		catch(SQLException ex){
			System.out.println("Message: " + ex.getMessage());
			errorMessage.setText("An unexpected database error occurred");
		}

		// Create refresh button listener - repopulates tables on button press
		ActionListener refreshButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				errorMessage.setText("");
				try{
					PreparedStatement stmt = con.prepareStatement("select * from orders where customerUsername = ?");
					stmt.setString(1, username);
					ResultSet rs = stmt.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					TableFromResultSet.replaceTable(orders, rs, rsmd);
				}
				catch(SQLException ex){
					System.out.println("Message: " + ex.getMessage());
					errorMessage.setText("An unexpected database error occurred");
				}
				try{
					PreparedStatement stmt = con.prepareStatement("select * from reservation where customerUsername = ?");
					stmt.setString(1, username);
					ResultSet rs = stmt.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					TableFromResultSet.replaceTable(reservations, rs, rsmd);
				}
				catch(SQLException ex){
					System.out.println("Message: " + ex.getMessage());
					errorMessage.setText("An unexpected database error occurred");
				}
			}
		};
		refreshButton.addActionListener(refreshButtonListener);


		// Resize window
		viewFrame.pack();

		// Centre window
		Dimension d = viewFrame.getToolkit().getScreenSize();
		Rectangle r = viewFrame.getBounds();
		viewFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// Set window visible
		viewFrame.setVisible(true);


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

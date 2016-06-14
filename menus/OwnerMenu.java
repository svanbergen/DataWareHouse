package menus;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import customerFunctionality.ReservationMakingDialog;
import org.omg.CORBA.PUBLIC_MEMBER;

//import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;

import ownerFunctionality.*;
import utility.TableFromResultSet;

public class OwnerMenu {
	Connection con;
	String username;

	JFrame menuFrame;
	JTable businesses;
	

	public OwnerMenu(Connection con, String username){
		this.con = con;
		this.username = username;

		menuFrame = new JFrame("Owner Menu");

		JLabel menuPage = new JLabel("Owner Menu Page");
		JLabel sn = new JLabel("Current User : " + username);
		JButton refreshButton = new JButton("Refresh Businesses");
		JButton addBusinessButton = new JButton("Add Business");
		JButton updateBusinessButton = new JButton("Update Business");
		JButton viewReviewbutton = new JButton("View Reviews");
		JButton writeReplyButton = new JButton("Reply to Review");
		JButton QueryReservationButton = new JButton("Query Reservation");
		JButton bigSpenderButton = new JButton("View Big Spenders");

		JButton businessStatButton = new JButton("Business Statistics");
		

		JButton QueryOrderButton = new JButton("Query Order");
		JButton menuItemsButton = new JButton("Menu Items");
		JButton addMenuItemButton = new JButton("Add Menu Item");
		JButton deleteMenuItemButton = new JButton("Delete Menu Item");
		JButton updateMenuItemButton = new JButton("Update Menu Item Price");
		
		JPanel contentPane = new JPanel();
		menuFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();

		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		GridBagConstraints buttonC = new GridBagConstraints();
		buttonC.insets = new Insets(0, 0, 0, 10);
		buttonC.ipadx = 50;
		buttonC.anchor = GridBagConstraints.WEST;
		buttonC.gridx = 1;
		buttonC.gridy = 1;
		buttonC.fill = GridBagConstraints.HORIZONTAL;
		
		GridBagConstraints tableC = new GridBagConstraints();
		tableC.insets = new Insets(0, 0, 0, 0);
		tableC.fill = GridBagConstraints.NONE;
		tableC.gridy = 2;
		tableC.gridx = 3;
		tableC.ipadx = 400;
		tableC.gridheight = 15;

		buttonC.gridy = 1;
		gb.setConstraints(menuPage, buttonC);
		contentPane.add(menuPage);
		
		buttonC.gridy = 2;
		gb.setConstraints(sn, buttonC);
		contentPane.add(sn);
		
		buttonC.gridy = 3;
		gb.setConstraints(refreshButton, buttonC);
		contentPane.add(refreshButton);
		
		buttonC.gridy = 4;
		gb.setConstraints(addBusinessButton, buttonC);
		contentPane.add(addBusinessButton);

		buttonC.gridy = 5;
		gb.setConstraints(updateBusinessButton, buttonC);
		contentPane.add(updateBusinessButton);
		
		buttonC.gridy = 6;
		gb.setConstraints(viewReviewbutton, buttonC);
		contentPane.add(viewReviewbutton);
		
		buttonC.gridy = 7;
		gb.setConstraints(writeReplyButton, buttonC);
		contentPane.add(writeReplyButton);
		
		buttonC.gridy = 8;
		gb.setConstraints(QueryReservationButton, buttonC);
		contentPane.add(QueryReservationButton);
		
		buttonC.gridy = 10;
		gb.setConstraints(businessStatButton, buttonC);
		contentPane.add(businessStatButton);
		
		buttonC.gridy = 9;
		gb.setConstraints(QueryOrderButton, buttonC);
		contentPane.add(QueryOrderButton);
		
		buttonC.gridy = 11;
		gb.setConstraints(menuItemsButton, buttonC);
		contentPane.add(menuItemsButton);
		
		buttonC.gridy = 12;
		gb.setConstraints(bigSpenderButton, buttonC);
		contentPane.add(bigSpenderButton);
		
		buttonC.gridy = 13;
		gb.setConstraints(addMenuItemButton, buttonC);
		contentPane.add(addMenuItemButton);
		
		buttonC.gridy = 14;
		gb.setConstraints(deleteMenuItemButton, buttonC);
		contentPane.add(deleteMenuItemButton);
		
		buttonC.gridy = 15;
		gb.setConstraints(updateMenuItemButton, buttonC);
		contentPane.add(updateMenuItemButton);
		
		// Add all buttons before here
		// Create table of all businesses associated with owner

		businesses = new JTable();
		try{
		PreparedStatement stmt = con.prepareStatement("select * from business where ownerUsername = ?");
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		businesses = TableFromResultSet.convert(rs, rsmd);
		}
		catch(SQLException ex){
			System.out.println("Message: " + ex.getMessage());
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(businesses);
		
		gb.setConstraints(scrollPane, tableC);
		contentPane.add(scrollPane);
		
		// Create and register button listeners
		
		ActionListener refreshButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try{
					PreparedStatement stmt = con.prepareStatement("select * from business where ownerUsername = ?");
					stmt.setString(1, username);
					ResultSet rs = stmt.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					TableFromResultSet.replaceTable(businesses, rs, rsmd);
					}
					catch(SQLException ex){
						System.out.println("Message: " + ex.getMessage());
					}
			}
		};
		refreshButton.addActionListener(refreshButtonListener);
		
		ActionListener bigSpenderButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new ViewBigSpenders(con,username);
			}
		};
		bigSpenderButton.addActionListener(bigSpenderButtonListener);
		
				ActionListener addBusinessButtonListener = new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
						AddBusiness ab = new AddBusiness(con,username);
					}
				};
				addBusinessButton.addActionListener(addBusinessButtonListener);
				
				
				
				ActionListener updateBusinessButtonListener = new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
						new UpdateBusiness(username, con);
					}
				};
				updateBusinessButton.addActionListener(updateBusinessButtonListener);
				
				ActionListener viewReviewButtonListener = new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
						ViewReviews vr = new ViewReviews(con, username);
					}
				};
				viewReviewbutton.addActionListener(viewReviewButtonListener);

				
				ActionListener replyReviewButtonListener = new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
						WriteReply wr = new WriteReply(con,username);
					}
				};
				writeReplyButton.addActionListener(replyReviewButtonListener);
				
				ActionListener queryReservationButtonListener = new ActionListener() {
					
					
					public void actionPerformed(ActionEvent e) {
						QueryReservation qr = new QueryReservation(username, con);
						
					}
				};
				QueryReservationButton.addActionListener(queryReservationButtonListener);
				
				ActionListener queryOrderButtonListener = new ActionListener() {
					
					
					public void actionPerformed(ActionEvent e) {
						QueryOrder qo = new QueryOrder(username, con);
						
					}
				};
				QueryOrderButton.addActionListener(queryOrderButtonListener);
				



				menuItemsButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//displays list of all menu items
		
						ViewMenuItem vm = new ViewMenuItem(con, username);
					}
				});
				
				
				
				ActionListener businessStatButtonListener = new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
						BusinessStatistics bs = new BusinessStatistics(con, username);
					}
				};
				businessStatButton.addActionListener(businessStatButtonListener);
				
				addMenuItemButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						AddMenuItem addMenuItem = new AddMenuItem(con, username);
					}
				});
				
				deleteMenuItemButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						DeleteMenuItem deleteMenuItem = new DeleteMenuItem(con, username);
					}
				});
				
				updateMenuItemButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						UpdateMenuItemPrice updateMenuItemPrice = new UpdateMenuItemPrice(con, username);
					}
				});
				
				
		// On window close
		menuFrame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{ 
				System.exit(0); 
			}
		});

		// Resize window
		menuFrame.pack();

		// Centre window
		Dimension d = menuFrame.getToolkit().getScreenSize();
		Rectangle r = menuFrame.getBounds();
		menuFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// Set window visible
		menuFrame.setVisible(true);

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

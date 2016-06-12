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

import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;

import ownerFunctionality.*;
import ownerFunctionality.MenuItem;
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
		JButton QueryOrderButton = new JButton("Query Order");
		JButton menuItemsButton = new JButton("Menu Items");
		
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
		GridBagConstraints gbc_menuPage = new GridBagConstraints();
		gbc_menuPage.insets = new Insets(0, 0, 0, 5);
		gbc_menuPage.gridx = 0;
		gbc_menuPage.gridy = 0;
		contentPane.add(menuPage, gbc_menuPage);
		
		buttonC.gridy = 2;
		gb.setConstraints(sn, buttonC);
		GridBagConstraints gbc_sn = new GridBagConstraints();
		gbc_sn.insets = new Insets(0, 0, 0, 5);
		gbc_sn.gridx = 1;
		gbc_sn.gridy = 0;
		contentPane.add(sn, gbc_sn);
		
		buttonC.gridy = 3;
		gb.setConstraints(refreshButton, buttonC);
		GridBagConstraints gbc_refreshButton = new GridBagConstraints();
		gbc_refreshButton.insets = new Insets(0, 0, 0, 5);
		gbc_refreshButton.gridx = 2;
		gbc_refreshButton.gridy = 0;
		contentPane.add(refreshButton, gbc_refreshButton);
		
		buttonC.gridy = 4;
		gb.setConstraints(addBusinessButton, buttonC);
		GridBagConstraints gbc_addBusinessButton = new GridBagConstraints();
		gbc_addBusinessButton.insets = new Insets(0, 0, 0, 5);
		gbc_addBusinessButton.gridx = 3;
		gbc_addBusinessButton.gridy = 0;
		contentPane.add(addBusinessButton, gbc_addBusinessButton);

		buttonC.gridy = 5;
		gb.setConstraints(updateBusinessButton, buttonC);
		GridBagConstraints gbc_updateBusinessButton = new GridBagConstraints();
		gbc_updateBusinessButton.insets = new Insets(0, 0, 0, 5);
		gbc_updateBusinessButton.gridx = 4;
		gbc_updateBusinessButton.gridy = 0;
		contentPane.add(updateBusinessButton, gbc_updateBusinessButton);
		
		buttonC.gridy = 6;
		gb.setConstraints(viewReviewbutton, buttonC);
		GridBagConstraints gbc_viewReviewbutton = new GridBagConstraints();
		gbc_viewReviewbutton.insets = new Insets(0, 0, 0, 5);
		gbc_viewReviewbutton.gridx = 5;
		gbc_viewReviewbutton.gridy = 0;
		contentPane.add(viewReviewbutton, gbc_viewReviewbutton);
		
		buttonC.gridy = 7;
		gb.setConstraints(writeReplyButton, buttonC);
		GridBagConstraints gbc_writeReplyButton = new GridBagConstraints();
		gbc_writeReplyButton.insets = new Insets(0, 0, 0, 5);
		gbc_writeReplyButton.gridx = 6;
		gbc_writeReplyButton.gridy = 0;
		contentPane.add(writeReplyButton, gbc_writeReplyButton);
		
		buttonC.gridy = 8;
		gb.setConstraints(QueryReservationButton, buttonC);
		GridBagConstraints gbc_QueryReservationButton = new GridBagConstraints();
		gbc_QueryReservationButton.insets = new Insets(0, 0, 0, 5);
		gbc_QueryReservationButton.gridx = 7;
		gbc_QueryReservationButton.gridy = 0;
		contentPane.add(QueryReservationButton, gbc_QueryReservationButton);
		
		buttonC.gridy = 9;
		gb.setConstraints(QueryOrderButton, buttonC);
		GridBagConstraints gbc_QueryOrderButton = new GridBagConstraints();
		gbc_QueryOrderButton.insets = new Insets(0, 0, 0, 5);
		gbc_QueryOrderButton.gridx = 8;
		gbc_QueryOrderButton.gridy = 0;
		contentPane.add(QueryOrderButton, gbc_QueryOrderButton);
		
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
		
		GridBagConstraints gbc_menuItemsButton = new GridBagConstraints();
		gbc_menuItemsButton.insets = new Insets(0, 0, 0, 5);
		gbc_menuItemsButton.gridx = 9;
		gbc_menuItemsButton.gridy = 0;
		contentPane.add(menuItemsButton, gbc_menuItemsButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(businesses);
		
		gb.setConstraints(scrollPane, tableC);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridx = 10;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
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


	public List<MenuItem> getAllMenuItems() throws Exception {
		List<MenuItem> list = new ArrayList<MenuItem>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("select * from MenuItem order by Name");
			
			while (myRs.next()) {
				MenuItem tempMenuItem = convertRowToMenuItem(myRs);
				list.add(tempMenuItem);
			}

			return list;		
		}
		catch (Exception e){
			System.out.println();
		}
		return list;
	}


	private MenuItem convertRowToMenuItem(ResultSet myRs) throws SQLException {
		int id = myRs.getInt("menuItemID");
		String name = myRs.getString("Name");
		String type = myRs.getString("ItemType");
		int businessID = myRs.getInt("BusinessID");
		BigDecimal price = myRs.getBigDecimal("Price");
		
		MenuItem tempMenuItem = new MenuItem(id, name, type, price, businessID);
		
		return tempMenuItem;
	}

}

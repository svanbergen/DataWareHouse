package menus;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import customerFunctionality.*;

// Class to provide customers access to all functionality
public class CustomerMenu {
	Connection con;
	String username;

	JFrame menuFrame;


	public CustomerMenu(Connection con, String username){
		this.con = con;
		this.username = username;

		// Create frame
		menuFrame = new JFrame("Customer Menu");

		// Create labels and buttons
		JLabel menuPage = new JLabel("Customer Menu Page");
		JLabel sn = new JLabel("Current User : " + username);
		JButton updateButton = new JButton("Update Your Information");
		JButton findReviewsButton = new JButton("Find Reviews");
		JButton writeReviewButton = new JButton("Write a Review");
		JButton searchForBusinessesButton = new JButton("Search for Businesses");
		JButton searchForBusinessesByItemButton = new JButton("Search for Businesses by Item");
		JButton makeReservationButton = new JButton("Make or Cancel a Reservation");
		JButton getTheMenuForABusinessButton = new JButton("Get the Menu for a Business");
		JButton viewOAndRButton = new JButton("View Your Orders and Reservations");
		JButton createAnOrderButton = new JButton("Create an Order");

		// Create content panel and new layout
		JPanel contentPane = new JPanel();
		menuFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();

		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Create constraint set
		GridBagConstraints buttonC = new GridBagConstraints();
		buttonC.insets = new Insets(0, 0, 0, 10);
		buttonC.ipadx = 50;
		buttonC.anchor = GridBagConstraints.WEST;
		buttonC.gridx = 1;
		buttonC.gridy = 1;
		buttonC.fill = GridBagConstraints.HORIZONTAL;

		// Add buttons to layout
		buttonC.gridy = 1;
		gb.setConstraints(menuPage, buttonC);
		contentPane.add(menuPage);

		buttonC.gridy = 2;
		gb.setConstraints(sn, buttonC);
		contentPane.add(sn);

		buttonC.gridy = 3;
		gb.setConstraints(updateButton, buttonC);
		contentPane.add(updateButton);

		buttonC.gridy = 4;
		gb.setConstraints(makeReservationButton, buttonC);
		contentPane.add(makeReservationButton);

		buttonC.gridy = 5;
		gb.setConstraints(findReviewsButton, buttonC);
		contentPane.add(findReviewsButton);

		buttonC.gridy = 6;
		gb.setConstraints(writeReviewButton, buttonC);
		contentPane.add(writeReviewButton);

		buttonC.gridy = 7;
		gb.setConstraints(searchForBusinessesButton, buttonC);
		contentPane.add(searchForBusinessesButton);

		buttonC.gridy = 8;
		gb.setConstraints(searchForBusinessesByItemButton, buttonC);
		contentPane.add(searchForBusinessesByItemButton);

		buttonC.gridy = 9;
		gb.setConstraints(getTheMenuForABusinessButton, buttonC);
		contentPane.add(getTheMenuForABusinessButton);

		buttonC.gridy = 10;
		gb.setConstraints(createAnOrderButton, buttonC);
		contentPane.add(createAnOrderButton);

		buttonC.gridy = 11;
		gb.setConstraints(viewOAndRButton, buttonC);
		contentPane.add(viewOAndRButton);

		// Create and register button listeners

		ActionListener orderViewbuttonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new ViewOrdersAndReservations(con,username);
			}
		};
		viewOAndRButton.addActionListener(orderViewbuttonListener);

		ActionListener updateButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new UpdateCustomer(con,username);

			}
		};
		updateButton.addActionListener(updateButtonListener);

		ActionListener findReviewsButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new FindReviews(con);
			}
		};
		findReviewsButton.addActionListener(findReviewsButtonListener);

		ActionListener writeReviewButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new WriteReview(con, username);
			}
		};
		writeReviewButton.addActionListener(writeReviewButtonListener);


		ActionListener businessSearchButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new BusinessSearch(con);
			}
		};
		searchForBusinessesButton.addActionListener(businessSearchButtonListener);

		ActionListener businessMenuSearchButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new BusinessSearchByMenuItem(con);
			}
		};
		searchForBusinessesByItemButton.addActionListener(businessMenuSearchButtonListener);

		ActionListener createAnOrderButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new OrderWindow(con,username);
			}
		};
		createAnOrderButton.addActionListener(createAnOrderButtonListener);

		ActionListener makeReservationButtonListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("reserve button pressed!");

				try {
					ReservationMakingDialog rmw = new ReservationMakingDialog( username , con);
					rmw.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					rmw.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		makeReservationButton.addActionListener(makeReservationButtonListener);

		ActionListener getTheMenuForABusinessListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new BusinessMenu(con);
			}
		};
		getTheMenuForABusinessButton.addActionListener(getTheMenuForABusinessListener);

		// On window close
		menuFrame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{ 
				try
				{
					con.close();
				} 
				catch (SQLException e1) 
				{
					System.out.print("Error occurred when closing connection.");
				}
				//System.exit(0); 
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

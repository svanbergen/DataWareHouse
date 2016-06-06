package menus;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import utility.TableFromResultSet;

public class OwnerMenu {
	Connection con;
	String username;

	JFrame menuFrame;
	

	public OwnerMenu(Connection con, String username){
		this.con = con;
		this.username = username;

		menuFrame = new JFrame("Owner Menu");

		JLabel menuPage = new JLabel("Owner Menu Page");
		JLabel sn = new JLabel("Current User : " + username);
		JButton addBusinessButton = new JButton("Add Business");
		JButton updateBusinessButton = new JButton("Update Business");


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
		gb.setConstraints(addBusinessButton, buttonC);
		contentPane.add(addBusinessButton);

		buttonC.gridy = 4;
		gb.setConstraints(updateBusinessButton, buttonC);
		contentPane.add(updateBusinessButton);
		
		// Add all buttons before here
		// Create table of all businesses associated with owner

		JTable businesses = new JTable();
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
				ActionListener addBusinessButtonListener = new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
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

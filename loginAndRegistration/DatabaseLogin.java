package loginAndRegistration;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DatabaseLogin implements ActionListener{
	/* Code based on the branch.java program provided in the CPSC304 JDBC tutorial 1 */

	private Connection con;

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JFrame loginFrame;

	public DatabaseLogin(){
		// Create frame, labels, etc. for connection login window
		loginFrame = new JFrame("Oracle Connection Login");

		JLabel usernameLabel = new JLabel("Enter Oracle username: ");
		JLabel passwordLabel = new JLabel("Enter Oracle password: ");

		usernameField = new JTextField(10);
		passwordField = new JPasswordField(10);
		passwordField.setEchoChar('*');

		JButton loginButton = new JButton("Connect");

		JPanel contentPane = new JPanel();
		loginFrame.setContentPane(contentPane);

		// Layout components using the GridBag layout manager

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Username label 
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(10, 10, 5, 0);
		gb.setConstraints(usernameLabel, c);
		contentPane.add(usernameLabel);

		// Username field
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 0, 5, 10);
		gb.setConstraints(usernameField, c);
		contentPane.add(usernameField);

		// Password label
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 10, 10, 0);
		gb.setConstraints(passwordLabel, c);
		contentPane.add(passwordLabel);

		// Password field 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 10, 10);
		gb.setConstraints(passwordField, c);
		contentPane.add(passwordField);

		// Login button
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(5, 10, 10, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(loginButton, c);
		contentPane.add(loginButton);

		// Register with event handler
		passwordField.addActionListener(this);
		loginButton.addActionListener(this);

		// On window close
		loginFrame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{ 
				System.exit(0); 
			}
		});

		// Resize window
		loginFrame.pack();

		// Centre window
		Dimension d = loginFrame.getToolkit().getScreenSize();
		Rectangle r = loginFrame.getBounds();
		loginFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// Set window visible
		loginFrame.setVisible(true);

		// Place cursor in username text field
		usernameField.requestFocus();

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


	// Connect to Oracle database named ug using user supplied username and password
	// Note: code written to connect from a machine SSHing to the CS servers
	private boolean connect(String username, String password)
	{
		// FOR AWS DATABASE USE
		/*
		String connectURL = "jdbc:oracle:thin:@dwh-micro1.clrwdshwtrbl.us-west-1.rds.amazonaws.com:1521:DWH";
		*/
		String connectURL = "jdbc:oracle:thin:@localhost:1522:ug";
		try 
		{
			// FOR AWS DATABASE USE
			/*
			con = DriverManager.getConnection(connectURL,"reggie","registration");
			*/
			con = DriverManager.getConnection(connectURL,username,password);
			
			System.out.println("\nConnected to Oracle!");
			return true;
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}


	// Database login window event handler
	public void actionPerformed(ActionEvent e) 
	{
		if ( connect(usernameField.getText(), String.valueOf(passwordField.getPassword())) )
		{
			// If details are valid remove database login window and go to application login window
			loginFrame.dispose();
			AppLogin apl = new AppLogin(con);
		}
		else
		{
			passwordField.setText("");
		}             

	}

	public static void main(String[] args) {
		DatabaseLogin dbl = new DatabaseLogin();
	}
}

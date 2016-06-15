package loginAndRegistration;
import java.awt.*;

import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import menus.CustomerMenu;
import menus.OwnerMenu;

// Class that handles user registration and login to application
public class AppLogin {

	Connection con;
	String connectURL = "jdbc:oracle:thin:@dwh-micro1.clrwdshwtrbl.us-west-1.rds.amazonaws.com:1521:DWH";
	private JTextField cUsernameField;
	private JPasswordField cPasswordField;
	private JTextField oUsernameField;
	private JPasswordField oPasswordField;
	private JFrame loginFrame;

	public AppLogin(Connection connection){
		this.con = connection;

		// Create frame, labels, etc. for login window
		loginFrame = new JFrame("Login Window");

		JLabel cLoginLabel = new JLabel("Customer Login");
		JLabel cUsernameLabel = new JLabel("Enter customer username: ");
		JLabel cPasswordLabel = new JLabel("Enter customer password: ");
		cUsernameField = new JTextField(10);
		cPasswordField = new JPasswordField(10);
		cPasswordField.setEchoChar('*');
		JButton cLoginButton = new JButton("Login as Customer");
		JButton cRegButton = new JButton("Register as Customer");

		JLabel oLoginLabel = new JLabel("Owner Login");
		JLabel oUsernameLabel = new JLabel("Enter owner username: ");
		JLabel oPasswordLabel = new JLabel("Enter owner password: ");
		oUsernameField = new JTextField(10);
		oPasswordField = new JPasswordField(10);
		oPasswordField.setEchoChar('*');
		JButton oLoginButton = new JButton("Login as Owner");
		JButton oRegButton = new JButton("Register as Owner");

		// Create and populate the window

		JPanel contentPane = new JPanel();
		loginFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();

		GridBagConstraints titleC = new GridBagConstraints();
		titleC.gridwidth = GridBagConstraints.REMAINDER;
		titleC.insets = new Insets(10, 10, 5, 0);

		GridBagConstraints labelC = new GridBagConstraints();
		labelC.gridwidth = GridBagConstraints.RELATIVE;
		labelC.insets = new Insets(10, 10, 5, 0);

		GridBagConstraints buttonC = new GridBagConstraints();
		buttonC.gridwidth = GridBagConstraints.REMAINDER;
		buttonC.insets = new Insets(5, 10, 10, 10);
		buttonC.weightx=0;
		buttonC.fill=GridBagConstraints.NONE;
		buttonC.anchor = GridBagConstraints.CENTER;

		GridBagConstraints fieldC = new GridBagConstraints();
		fieldC.gridwidth = GridBagConstraints.REMAINDER;
		fieldC.insets = new Insets(10, 0, 5, 10);
		fieldC.weightx=1.;
		fieldC.fill=GridBagConstraints.HORIZONTAL;

		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Customer login label 
		gb.setConstraints(cLoginLabel, titleC);
		contentPane.add(cLoginLabel);

		// Customer username label 
		gb.setConstraints(cUsernameLabel, labelC);
		contentPane.add(cUsernameLabel);

		// Customer username field
		gb.setConstraints(cUsernameField, fieldC);
		contentPane.add(cUsernameField);

		// Customer password label
		gb.setConstraints(cPasswordLabel, labelC);
		contentPane.add(cPasswordLabel);

		// Customer password field 
		gb.setConstraints(cPasswordField, fieldC);
		contentPane.add(cPasswordField);

		// Customer login button
		gb.setConstraints(cLoginButton, buttonC);
		contentPane.add(cLoginButton);

		// Customer reg button
		gb.setConstraints(cRegButton, buttonC);
		contentPane.add(cRegButton);

		// Owner login label 
		gb.setConstraints(oLoginLabel, titleC);
		contentPane.add(oLoginLabel);

		// Owner username label
		gb.setConstraints(oUsernameLabel, labelC);
		contentPane.add(oUsernameLabel);

		// Owner username field
		gb.setConstraints(oUsernameField, fieldC);
		contentPane.add(oUsernameField);

		// Owner password label
		gb.setConstraints(oPasswordLabel, labelC);
		contentPane.add(oPasswordLabel);

		// Owner password field 
		gb.setConstraints(oPasswordField, fieldC);
		contentPane.add(oPasswordField);

		// Owner login button
		gb.setConstraints(oLoginButton, buttonC);
		contentPane.add(oLoginButton);

		// Owner reg button
		gb.setConstraints(oRegButton, buttonC);
		contentPane.add(oRegButton);

		// Error message
		JLabel errorMessage = new JLabel("");
		errorMessage.setForeground (Color.red);
		gb.setConstraints(errorMessage, titleC);
		contentPane.add(errorMessage);

		// Anonymous class to listen to customer login button
		ActionListener customerListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				String userName = cUsernameField.getText();
				String password = String.valueOf(cPasswordField.getPassword());

				// Construct query to search for specified user
				String loginQuery = "select customerUsername, password from customer where customerUsername = '";
				loginQuery = loginQuery.concat(userName.concat("'"));

				// Attempt query of customer table
				try{
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(loginQuery);

					if(rs.next())
					{
						String dBPassword = rs.getString("password");
						// If details are valid remove database login window and go to customer menu window
						if(dBPassword.equals(password)){
							// FOR AWS DATABASE USE
							/*
							Connection con1;
							try{
								con1 = DriverManager.getConnection(connectURL,userName+"_customer",password);
								Statement statement = con1.createStatement();
								statement.executeQuery("set role customer_user identified by customer");
							}
							catch(SQLException ex){
								System.out.println("Message: " + ex.getMessage());
								errorMessage.setText("Invalid input");
								return;
							}

							con.close();
							con = con1;
							 */
							new CustomerMenu(con,userName);
						}
						else{
							cPasswordField.setText("");
							errorMessage.setText("Invalid password");
						}
					}
					else
					{
						errorMessage.setText("Invalid username");
						cPasswordField.setText("");
						cUsernameField.setText("");
					}        
				}
				catch (SQLException ex)
				{
					System.out.println("Message: " + ex.getMessage());
					errorMessage.setText("Invalid input");
				}	


			}
		};

		// Register customer login with event handler
		cPasswordField.addActionListener(customerListener);
		cLoginButton.addActionListener(customerListener);

		// Anonymous class to listen to owner login button
		ActionListener ownerListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				String userName = oUsernameField.getText();
				String password = String.valueOf(oPasswordField.getPassword());

				// Construct query to search for specified user
				String loginQuery = "select ownerUserName, password from BusinessOwner where ownerUserName = '";
				loginQuery = loginQuery.concat(userName.concat("'"));

				// Attempt query of BusinessOwner table
				try{
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(loginQuery);

					if(rs.next())
					{
						String dBPassword = rs.getString("password");
						// If details are valid remove database login window and go to customer menu window
						if(dBPassword.equals(password)){
							// FOR AWS DATABASE USE
							/*
							Connection con1;
							try{
								con1 = DriverManager.getConnection(connectURL,userName+"_owner",password);
								Statement statement = con1.createStatement();
								statement.executeQuery("set role owner_user identified by owner");
							}
							catch(SQLException ex){
								System.out.println("Message: " + ex.getMessage());
								errorMessage.setText("Invalid input");
								return;
							}

							con.close();
							con = con1;
							 */
							new OwnerMenu(con, userName);
						}
						else{
							oPasswordField.setText("");
							errorMessage.setText("Invalid password");
						}
					}
					else
					{
						errorMessage.setText("Invalid username");
						oUsernameField.setText("");
						oPasswordField.setText("");
					} 
				}
				catch (SQLException ex)
				{
					System.out.println("Message: " + ex.getMessage());
					errorMessage.setText("Invalid input");
				}	

			}
		};

		oPasswordField.addActionListener(ownerListener);
		oLoginButton.addActionListener(ownerListener);

		// Anonymous class to listen to customer registration button
		ActionListener customerRegListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new CustomerRegistration(con);
			}
		};
		// Register customer reg button with event handler
		cRegButton.addActionListener(customerRegListener);


		// Anonymous class to listen to owner registration button
		ActionListener ownerRegListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new OwnerRegistration(con);
			}
		};
		// Register owner reg button with event handler
		oRegButton.addActionListener(ownerRegListener);

		// On window close
		loginFrame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{ 
				try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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
		cUsernameField.requestFocus();

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

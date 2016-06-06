package loginAndRegistration;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class CustomerRegistration {

	Connection con;

	private JTextField UsernameField;
	private JPasswordField PasswordField;
	private JFrame regFrame;

	public CustomerRegistration(Connection con){

		this.con = con;
		regFrame = new JFrame("Customer Registration");

		JLabel oRegLabel = new JLabel("Customer Registration");
		JLabel oUsernameLabel = new JLabel("Enter desired username: ");
		JLabel oPasswordLabel = new JLabel("Enter desired password: ");
		UsernameField = new JTextField(10);
		PasswordField = new JPasswordField(10);
		PasswordField.setEchoChar('*');
		JButton regButton = new JButton("Register as Customer");


		// Create and populate the window
		JPanel contentPane = new JPanel();
		regFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();

		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
		
		// Customer login label 
		gb.setConstraints(oRegLabel, titleC);
		contentPane.add(oRegLabel);

		// Customer username label 
		gb.setConstraints(oUsernameLabel, labelC);
		contentPane.add(oUsernameLabel);

		// Customer username field
		gb.setConstraints(UsernameField, fieldC);
		contentPane.add(UsernameField);

		// Customer password label
		gb.setConstraints(oPasswordLabel, labelC);
		contentPane.add(oPasswordLabel);

		// Customer password field 
		gb.setConstraints(PasswordField, fieldC);
		contentPane.add(PasswordField);

		// Customer login button
		gb.setConstraints(regButton, buttonC);
		contentPane.add(regButton);

		// Error message
		JLabel errorMessage = new JLabel("");
		errorMessage.setForeground (Color.red);
		gb.setConstraints(errorMessage, titleC);
		contentPane.add(errorMessage);

		// Anonymous class to listen to customer login button
		ActionListener regListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				String userName = UsernameField.getText();
				String password = String.valueOf(PasswordField.getPassword());

				// Construct query to search for specified user
				String loginQuery = "select customerUsername from customer where customerUsername = ?";

				// Attempt query of customer table
				try{
					PreparedStatement stmt = con.prepareStatement(loginQuery);
					stmt.setString(1, userName);
					ResultSet rs = stmt.executeQuery();

					if(rs.next())
					{
						PasswordField.setText("");
						UsernameField.setText("");
						errorMessage.setText("Username already in use");
					}
					else
					{
						String regQuery = "insert into customer values(?,?,?)";
						stmt = con.prepareStatement(regQuery);
						stmt.setString(1, userName);
						stmt.setString(2, password);//System.out.println(regQuery);
						stmt.setNull(3, Types.VARCHAR);
						rs = stmt.executeQuery();
						regFrame.dispose();
					}        
				}
				catch (SQLException ex)
				{
					System.out.println("Message: " + ex.getMessage());
					errorMessage.setText("Invalid input");
				}	


			}
		};

		PasswordField.addActionListener(regListener);
		regButton.addActionListener(regListener);

		// On window close
		regFrame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{ 
			}
		});

		// Resize window
		regFrame.pack();

		// Centre window
		Dimension d = regFrame.getToolkit().getScreenSize();
		Rectangle r = regFrame.getBounds();
		regFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// Set window visible
		regFrame.setVisible(true);

		// Place cursor in username text field
		UsernameField.requestFocus();

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

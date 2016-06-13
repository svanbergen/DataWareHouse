package customerFunctionality;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.BorderFactory;
import javax.swing.*;

import utility.TableFromResultSet;

public class UpdateCustomer {
	Connection con;
	private JFrame updateFrame;
	private JTextField addressField;
	private JTextField postalCodeField;
	private JTextField cityField;
	private JTextField unitField;

	private JComboBox<String> provBox;

	private JTextField PhoneField1;
	private JTextField PhoneField2;
	private JTextField PhoneField3;

	private JTable results;


	String username;

	public UpdateCustomer(Connection con, String username){
		this.con = con;
		this.username = username;
		String province[] = {"", "AB", "BC", "MB", "NB","NL","NT","NS","NU","ON","PE","QC","SK","YT"};

		// Define/initialize parts of frame
		updateFrame = new JFrame("Set User Information");
		// Labels
		JLabel titleLabel = new JLabel("Update your user information");

		JLabel locationLabel = new JLabel("Enter location:");
		JLabel addressLabel = new JLabel("Street address: ");
		JLabel cityLabel = new JLabel("City: ");
		JLabel provinceLabel = new JLabel("Province: ");
		JLabel unitLabel = new JLabel("Unit: ");
		JLabel postalCodeLabel = new JLabel("Postal code: ");
		JLabel phoneLabel = new JLabel("Enter phone number: ");

		addressField = new JTextField(10);
		addressField.setMinimumSize(addressField.getPreferredSize());
		unitField = new JTextField(10);
		unitField.setMinimumSize(unitField.getPreferredSize());
		postalCodeField = new JTextField(10);
		postalCodeField.setMinimumSize(postalCodeField.getPreferredSize());
		cityField = new JTextField(10);
		cityField.setMinimumSize(cityField.getPreferredSize());

		PhoneField1 = new JTextField(3);
		PhoneField1.setMinimumSize(PhoneField1.getPreferredSize());
		PhoneField2 = new JTextField(3);
		PhoneField2.setMinimumSize(PhoneField2.getPreferredSize());
		PhoneField3 = new JTextField(4);
		PhoneField3.setMinimumSize(PhoneField3.getPreferredSize());

		provBox = new JComboBox<String>(province);

		// Button
		JButton updateButton = new JButton("Update");

		// Create and populate the panel using GridBag for layout
		JPanel contentPane = new JPanel();
		updateFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();

		// Preset constraint sets
		GridBagConstraints titleC = new GridBagConstraints();
		titleC.insets = new Insets(10, 10, 5, 0);
		titleC.anchor = GridBagConstraints.WEST;

		GridBagConstraints labelC = new GridBagConstraints();
		labelC.insets = new Insets(10, 10, 5, 0);
		labelC.anchor = GridBagConstraints.WEST;

		GridBagConstraints buttonC = new GridBagConstraints();
		buttonC.insets = new Insets(5, 10, 10, 10);
		buttonC.weightx=0;
		buttonC.fill=GridBagConstraints.NONE;
		buttonC.anchor = GridBagConstraints.WEST;

		GridBagConstraints fieldC = new GridBagConstraints();
		fieldC.insets = new Insets(10, 0, 5, 10);
		fieldC.anchor = GridBagConstraints.WEST;
		//fieldC.weightx=1.;
		//fieldC.fill=GridBagConstraints.HORIZONTAL;

		GridBagConstraints tableC = new GridBagConstraints();
		tableC.insets = new Insets(0, 0, 0, 0);
		tableC.fill = GridBagConstraints.NONE;
		tableC.gridy = 1;
		tableC.gridx = 3;
		tableC.ipadx = 500;
		tableC.gridwidth = 3;
		tableC.gridheight = 15;

		// Set layout and border
		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Add Business label 
		titleC.gridy = 1;
		titleC.gridx = 1;
		gb.setConstraints(titleLabel, titleC);
		contentPane.add(titleLabel);

		// Phone label 
		GridBagConstraints phoneC = new GridBagConstraints();
		phoneC.gridy = 2;
		phoneC.gridx = 1;
		phoneC.insets = new Insets(10, 10, 5, 0);
		phoneC.anchor = GridBagConstraints.WEST;
		gb.setConstraints(phoneLabel, phoneC);
		contentPane.add(phoneLabel);

		// Phone field panel
		fieldC.gridy = 3;
		fieldC.gridx = 1;
		fieldC.insets = new Insets(5, 10, 10, 10);
		JPanel phonePanel = createPhonePanel();
		gb.setConstraints(phonePanel, fieldC);
		contentPane.add(phonePanel);

		// Location title
		titleC.gridy = 4;
		gb.setConstraints(locationLabel, titleC);
		contentPane.add(locationLabel);

		// Unit label
		titleC.gridy = 5;
		gb.setConstraints(unitLabel, titleC);
		contentPane.add(unitLabel);

		// Unit field
		fieldC.gridy = 6;
		fieldC.gridx = 1;
		gb.setConstraints(unitField, fieldC);
		contentPane.add(unitField);

		// Address label
		titleC.gridy = 7;
		gb.setConstraints(addressLabel, titleC);
		contentPane.add(addressLabel);

		// Address field
		fieldC.gridy = 8;
		fieldC.gridx = 1;
		gb.setConstraints(addressField, fieldC);
		contentPane.add(addressField);

		// City label
		titleC.gridy = 9;
		gb.setConstraints(cityLabel, titleC);
		contentPane.add(cityLabel);

		// City field
		fieldC.gridy = 10;
		fieldC.gridx = 1;
		gb.setConstraints(cityField, fieldC);
		contentPane.add(cityField);

		// Province title
		titleC.gridy = 11;
		gb.setConstraints(provinceLabel, titleC);
		contentPane.add(provinceLabel);

		// Province field
		GridBagConstraints BoxC = new GridBagConstraints();
		BoxC.anchor = GridBagConstraints.WEST;
		BoxC.gridy = 12;
		BoxC.gridx = 1;
		BoxC.gridwidth = GridBagConstraints.REMAINDER;
		BoxC.insets = new Insets(10, 10, 5, 0);
		gb.setConstraints(provBox, BoxC);
		contentPane.add(provBox);

		// Location title
		titleC.gridy = 13;
		gb.setConstraints(postalCodeLabel, titleC);
		contentPane.add(postalCodeLabel);

		// Province field
		fieldC.gridy = 14;
		fieldC.gridx = 1;
		gb.setConstraints(postalCodeField, fieldC);
		contentPane.add(postalCodeField);

		// Add button label 
		buttonC.gridy = 15;
		buttonC.gridx = 1;
		gb.setConstraints(updateButton, buttonC);
		contentPane.add(updateButton);

		// Error message
		JLabel errorMessage = new JLabel(" ");
		titleC.gridy = 16;
		titleC.gridx = 3;
		errorMessage.setForeground (Color.red);
		gb.setConstraints(errorMessage, titleC);
		contentPane.add(errorMessage);

		results = new JTable();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(results);
		scrollPane.setMinimumSize(scrollPane.getPreferredSize());

		gb.setConstraints(scrollPane, tableC);
		contentPane.add(scrollPane);

		try{
			PreparedStatement stmt = con.prepareStatement("select * from customer natural left outer join residesin natural left outer join location natural left outer join postalcode where customerusername = ?");
			stmt.setString(1,username);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			TableFromResultSet.replaceTable(results, rs, rsmd);
		}
		catch(SQLException ex){
			System.out.println("Message: " + ex.getMessage());
			errorMessage.setText("Unexpected database error");
		}
		catch(Exception ex){
			System.out.println("Message: " + ex.getMessage());
			errorMessage.setText("Something bad happened");
		}

		// Anonymous class to listen to add business button
		ActionListener buttonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try{
					// Retrieve values from the fields
					String phone1 = PhoneField1.getText();
					String phone2 = PhoneField2.getText();
					String phone3 = PhoneField3.getText();
					if(!(phone1.equals("") && phone2.equals("") && phone3.equals(""))){
						String phone = phone1.concat("-").concat(phone2).concat("-").concat(phone3);
						if(!phone.matches("[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]")){
							errorMessage.setText("Invalid phone number");
							return;
						}
						else{
							PreparedStatement stmt = con.prepareStatement("update customer set phonenum = ? where customerusername = ?");
							stmt.setString(1, phone);
							stmt.setString(2, username);
							stmt.executeUpdate();
						}
					}


					// START updating location
					String postalCode = postalCodeField.getText();
					String city = cityField.getText();
					String province = (String) provBox.getSelectedItem();
					// Check error conditions for postalCode and create postalcode if missing from database
					if(!postalCode.equals("")){
						if(!postalCode.matches("[A-Z][0-9][A-Z] [0-9][A-Z][0-9]")){
							errorMessage.setText("Invalid postal code (must be of the form X1X 1X1)");
							return;
						}
						else if(city.equals("") || province.equals("")){
							errorMessage.setText("Must specify city and province");
							return;
						}
						else{
							PreparedStatement check = con.prepareStatement("select * from postalcode where postalcode = ?");
							check.setString(1, postalCode);
							ResultSet rs = check.executeQuery();
							if(rs.next()){
								if(!rs.getString(2).equals(city) || !rs.getString(3).equals(province)){
									errorMessage.setText("Province and city do not match postal code");
									return;
								}
							}
							else{
								PreparedStatement postalAdd = con.prepareStatement("insert into postalcode values(?,?,?)");
								postalAdd.setString(1, postalCode);
								postalAdd.setString(2, city);
								postalAdd.setString(3, province);
								postalAdd.executeUpdate();
							}
						}


						// Check address errors and add address to database if missing
						String address = addressField.getText();
						String unit = unitField.getText();
						if(address.equals("")){
							errorMessage.setText("Must enter street address");
							return;
						}
						else{
							String statement = "select * from location where postalcode = '";
							statement = statement.concat(postalCode).concat("' and streetadd = '").concat(address);

							if(unit.equals("")){
								statement = statement.concat("' and unitnum is null");
							}
							else{
								statement = statement.concat("' and unitnum = '").concat(unit).concat("'");
							}
							PreparedStatement check = con.prepareStatement(statement);
							ResultSet rs = check.executeQuery();
							System.out.println(statement);
							if(!rs.next()){
								PreparedStatement locationAdd = con.prepareStatement("insert into location values(1,?,?,?)");
								if(unit.equals("")){
									locationAdd.setNull(1, Types.VARCHAR);
								}
								else{
									locationAdd.setString(1, unit);
								}
								locationAdd.setString(2, address);
								locationAdd.setString(3, postalCode);
								locationAdd.executeUpdate();

							}
						}


						String statement = "select * from location where postalcode = '";
						statement = statement.concat(postalCode).concat("' and streetadd = '").concat(address);

						if(unit.equals("")){
							statement = statement.concat("' and unitnum is null");
						}
						else{
							statement = statement.concat("' and unitnum = '").concat(unit).concat("'");
						}
						PreparedStatement getidstmt = con.prepareStatement(statement);
						ResultSet locid = getidstmt.executeQuery();
						locid.next();
						int locationid = locid.getInt(1);
						PreparedStatement deleteStmt = con.prepareStatement("delete from residesin where customerusername = ?");
						deleteStmt.setString(1, username);
						deleteStmt.executeUpdate();
						PreparedStatement updateStmt = con.prepareStatement("insert into residesin values(?, ?)");
						updateStmt.setString(2, username);
						updateStmt.setInt(1,locationid);
						updateStmt.executeUpdate();
					}
					// FINISH updating location

					// Refresh the table
					PreparedStatement stmt = con.prepareStatement("select * from customer natural left outer join residesin natural left outer join location natural left outer join postalcode where customerusername = ?");
					System.out.println(stmt);
					stmt.setString(1, username);
					ResultSet rs = stmt.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					TableFromResultSet.replaceTable(results, rs, rsmd);
					errorMessage.setText("");
				}
				catch(SQLException ex){
					System.out.println("Message: " + ex.getMessage());
					errorMessage.setText("Unexpected database error");
				}
			}
		};
		updateButton.addActionListener(buttonListener);

		// Resize window
		updateFrame.pack();

		// Centre window
		Dimension d = updateFrame.getToolkit().getScreenSize();
		Rectangle r = updateFrame.getBounds();
		updateFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// Set window visible
		updateFrame.setVisible(true);
	}

	// Method to create phone JPanel
	JPanel createPhonePanel(){
		JPanel phonePanel = new JPanel();

		GridBagLayout gb = new GridBagLayout();
		phonePanel.setLayout(gb);
		phonePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		// Phone fields 
		GridBagConstraints phoneFieldC = new GridBagConstraints();
		phoneFieldC.gridy = 1;
		phoneFieldC.gridx = 1;
		phoneFieldC.weightx=1.;
		phoneFieldC.fill=GridBagConstraints.HORIZONTAL;
		phoneFieldC.anchor = GridBagConstraints.WEST;
		gb.setConstraints(PhoneField1, phoneFieldC);
		phonePanel.add(PhoneField1);
		phoneFieldC.gridx = 2;
		gb.setConstraints(PhoneField2, phoneFieldC);
		phonePanel.add(PhoneField2);
		phoneFieldC.gridx = 3;
		phoneFieldC.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(PhoneField3, phoneFieldC);
		phonePanel.add(PhoneField3);

		return phonePanel;
	}
}

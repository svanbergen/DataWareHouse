package ownerFunctionality;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

// Class to update a business
public class UpdateBusiness {

	JFrame mainframe; 

	private Connection connection;
	private String businessName;
	private String businessID;

	private JTextField businessIDField;
	private JTextField websiteField;
	private JTextField typeField;

	private JTextField PhoneField1;
	private JTextField PhoneField2;
	private JTextField PhoneField3;

	// Everything else is a field except checkbox
	private JCheckBox m,t,w,r,f,s,u;

	private JTextField startHourField;
	private JTextField startMinField;
	private JTextField finHourField;
	private JTextField finMinField;

	private JTextField unitField;
	private JTextField addressField;
	private JTextField postalCodeField;
	private JTextField cityField;
	private JTextField provinceField;

	public UpdateBusiness(String username, Connection con) {

		// initialize global variables
		this.connection = con;
		this.businessName = username;

		mainframe = new JFrame("Update Your Business");

		// initialize Textfields
		businessIDField = new JTextField(10);
		businessIDField.setMinimumSize(businessIDField.getPreferredSize());
		websiteField = new JTextField(10);
		websiteField.setMinimumSize(websiteField.getPreferredSize());
		typeField = new JTextField(10);
		typeField.setMinimumSize(typeField.getPreferredSize());

		PhoneField1 = new JTextField(10);
		PhoneField1.setMinimumSize(PhoneField1.getMaximumSize());
		PhoneField2 = new JTextField(10);
		PhoneField2.setMinimumSize(PhoneField2.getPreferredSize());
		PhoneField3 = new JTextField(10);
		PhoneField3.setMinimumSize(PhoneField3.getPreferredSize());

		startHourField = new JTextField(10);
		startHourField.setMinimumSize(startHourField.getPreferredSize());
		startMinField = new JTextField(10);
		startMinField.setMinimumSize(startMinField.getMaximumSize());
		finHourField = new JTextField(10);
		finHourField.setMinimumSize(finHourField.getPreferredSize());
		finMinField = new JTextField(10);
		finMinField.setMinimumSize(finMinField.getPreferredSize());

		unitField = new JTextField(10);
		unitField.setMinimumSize(unitField.getPreferredSize());
		addressField = new JTextField(10);
		addressField.setMinimumSize(addressField.getPreferredSize());
		postalCodeField = new JTextField(10);
		postalCodeField.setMinimumSize(postalCodeField.getPreferredSize());
		cityField = new JTextField(10);
		cityField.setMinimumSize(cityField.getPreferredSize());
		provinceField = new JTextField(10);
		provinceField.setMinimumSize(provinceField.getPreferredSize());

		// Check boxes
		m = new JCheckBox();
		t = new JCheckBox();
		w = new JCheckBox();
		r = new JCheckBox();
		f = new JCheckBox();
		s = new JCheckBox();
		u = new JCheckBox();

		// Labels
		JLabel titleLabel = new JLabel("Please enter new Business Information");
		JLabel businessIDLabel = new JLabel("BusinessID: ");
		JLabel websiteLabel = new JLabel("Website: ");
		JLabel typeLabel = new JLabel("Type: ");

		JLabel phoneLabel = new JLabel("Phone Number: ");

		JLabel operationLabel = new JLabel("Days of Operation: ");
		JLabel startLabel = new JLabel("Opening Time: ");
		JLabel finiLabel = new JLabel("Closing Time: ");

		JLabel locationLabel = new JLabel("Location");

		JLabel unitLabel = new JLabel("Unit: ");
		JLabel addressLabel = new JLabel("Address: ");
		JLabel cityLabel = new JLabel("City: ");
		JLabel provLabel = new JLabel("Province: ");
		JLabel postalLabel = new JLabel("Postal Code: ");

		// Button
		JButton searchButton = new JButton("Update");

		// Panel
		JPanel contentpane = new JPanel();
		contentpane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// only time Scroll Content Pane is used
		JScrollPane scrollContentPane = new JScrollPane(contentpane);
		mainframe.setContentPane(scrollContentPane);

		// Constraints
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.WEST;

		// Set content pane
		contentpane.setLayout(gb);

		// Placing Components

		// Title Label
		constraints.gridy = 1;
		gb.setConstraints(titleLabel, constraints);
		contentpane.add(titleLabel);

		// BusinessID Label
		constraints.gridy = 2;
		gb.setConstraints(businessIDLabel, constraints);
		contentpane.add(businessIDLabel);

		// BusinessID field
		constraints.gridy = 3;
		gb.setConstraints(businessIDField, constraints);
		contentpane.add(businessIDField);

		// Website Label
		constraints.gridy = 4;
		gb.setConstraints(websiteLabel, constraints);
		contentpane.add(websiteLabel);

		// Website field
		constraints.gridy = 5;
		gb.setConstraints(websiteField, constraints);
		contentpane.add(websiteField);

		// Type Label
		constraints.gridy = 6;
		gb.setConstraints(typeLabel, constraints);
		contentpane.add(typeLabel);

		// Type Field
		constraints.gridy = 7;
		gb.setConstraints(typeField, constraints);
		contentpane.add(typeField);

		// Phone Label
		constraints.gridy = 8;
		gb.setConstraints(phoneLabel, constraints);
		contentpane.add(phoneLabel);

		// Phone Panel/Fields
		constraints.gridy = 9;
		JPanel phonePanel = createPhonePanel();
		gb.setConstraints(phonePanel, constraints);
		contentpane.add(phonePanel);

		// Operation Label
		constraints.gridy = 10;
		gb.setConstraints(operationLabel, constraints);
		contentpane.add(operationLabel);

		// Days of Operation checkboxes/panel
		constraints.gridy = 11;
		JPanel dayBoxPanel = createDayPanel();
		gb.setConstraints(dayBoxPanel, constraints);
		contentpane.add(dayBoxPanel);

		// Open By Label
		constraints.gridy = 12;
		gb.setConstraints(startLabel, constraints);
		contentpane.add(startLabel);

		// Open time fields
		JPanel openPanel = createOpeningPanel();
		constraints.gridy = 13;
		gb.setConstraints(openPanel, constraints);
		contentpane.add(openPanel);

		// Close by Label
		constraints.gridy = 14;
		gb.setConstraints(finiLabel, constraints);
		contentpane.add(finiLabel);

		// Close time fields
		JPanel closePanel = createClosingPanel();
		constraints.gridy = 15;
		gb.setConstraints(closePanel, constraints);
		contentpane.add(closePanel);

		// Location Label
		constraints.gridy = 16;
		gb.setConstraints(locationLabel, constraints);
		contentpane.add(locationLabel);

		// Unit Label
		constraints.gridy = 17;
		gb.setConstraints(unitLabel, constraints);
		contentpane.add(unitLabel);

		// Unit Field
		constraints.gridy = 18;
		gb.setConstraints(unitField, constraints);
		contentpane.add(unitField);

		// Street Label
		constraints.gridy = 19;
		gb.setConstraints(addressLabel, constraints);
		contentpane.add(addressLabel);

		// Street field
		constraints.gridy = 20;
		gb.setConstraints(addressField, constraints);
		contentpane.add(addressField);

		// City Label
		constraints.gridy = 21;
		gb.setConstraints(cityLabel, constraints);
		contentpane.add(cityLabel);

		// City fields
		constraints.gridy = 22;
		gb.setConstraints(cityField, constraints);
		contentpane.add(cityField);

		// Prov Label
		constraints.gridy = 23;
		gb.setConstraints(provLabel, constraints);
		contentpane.add(provLabel);

		// Prov field
		constraints.gridy = 24;
		gb.setConstraints(provinceField, constraints);
		contentpane.add(provinceField);

		// Postal Label
		constraints.gridy = 25;
		gb.setConstraints(postalLabel, constraints);
		contentpane.add(postalLabel);

		// Postal Field
		constraints.gridy = 26;
		gb.setConstraints(postalCodeField, constraints);
		contentpane.add(postalCodeField);

		// Error Message
		JLabel errorMessage = new JLabel(" ");
		errorMessage.setForeground (Color.red);
		constraints.gridy = 27;
		gb.setConstraints(errorMessage, constraints);
		contentpane.add(errorMessage);

		// Search Button
		constraints.gridy = 29;
		gb.setConstraints(searchButton, constraints);
		contentpane.add(searchButton);

		// End of GUI

		// Action Listener for search button
		ActionListener searchListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// parse data
				try {
					checkID();				
				} catch (Exception ex) {
					errorMessage.setText("Denied: " + ex.getMessage());
					return;
				}
				// generate query


				try {

					// Update statement head, update every field that is not empty by string append.
					String statement = "update business set business.ownerUsername = " + "'" + businessName + "'";

					// Website

					String website = websiteField.getText();

					if (!(website.equals(""))) {
						statement = statement.concat("business.website = " + "'" + website + "'" );
					}

					String type = typeField.getText();

					if (!(type.equals(""))) {
						statement = statement.concat(", business.type = " + "'" + type + "'");
					}

					// Phone
					String phone1 = PhoneField1.getText();
					String phone2 = PhoneField2.getText();
					String phone3 = PhoneField3.getText();

					// Check if Phone number is correct format or not
					if(!(phone1.equals("") && phone2.equals("") && phone3.equals(""))){
						String phone = phone1.concat("-").concat(phone2).concat("-").concat(phone3);
						if(!phone.matches("[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]")){
							errorMessage.setText("Invalid phone number");
							return;
						}
						else{
							statement = statement.concat(", business.phonenum = " + "'" + phone + "'");
						}
					}

					// Days
					String days = "";

					// Determine value of days
					if(m.isSelected()){
						days = days.concat("M");
					}
					if(t.isSelected()){
						days = days.concat("T");
					}
					if(w.isSelected()){
						days = days.concat("W");
					}
					if(r.isSelected()){
						days = days.concat("R");
					}
					if(f.isSelected()){
						days = days.concat("F");
					}
					if(s.isSelected()){
						days = days.concat("S");
					}
					if(u.isSelected()){
						days = days.concat("U");
					}
					if (!(days.equals(""))) {
						statement = statement.concat(", business.dayOfOperation = " + "'" + days + "'");
					}

					// Open / Close 
					String startHour = startHourField.getText();
					String startMin = startMinField.getText();
					String finHour = finHourField.getText();
					String finMin = finMinField.getText();

					if(!startHour.equals("") || !startMin.equals("")){
						if(!(startHour.matches("[0-9]+") && startMin.matches("[0-9]+"))){
							errorMessage.setText("Invalid time");
							return;
						}
						else{
							int sH = Integer.parseInt(startHour);
							int sM = Integer.parseInt(startMin);
							int startTime;
							if(sH < 24 && sH >= 0 && sM >=0 && sM < 60){
								startTime = sH*100 + sM;

								statement = statement.concat(", business.starttime = " + startTime);
							}
							else{
								errorMessage.setText("Invalid opening time");
								return;
							}
						}
					}

					if(!finHour.equals("") || !finMin.equals("")){
						if(!(finHour.matches("[0-9]+") && finMin.matches("[0-9]+"))){
							errorMessage.setText("Invalid time");
							return;
						}
						else{
							int fH = Integer.parseInt(finHour);
							int fM = Integer.parseInt(finMin);
							int finTime;
							if(fH < 24 && fH >= 0 && fM >=0 && fM < 60){
								finTime = fH*100 + fM;

								statement = statement.concat(", business.finishtime = " + finTime);
							}
							else{
								errorMessage.setText("Invalid closing time");
								return;
							}
						}
					}

					statement = statement.concat(" where business.ownerUsername = " + "'" + businessName + "'");
					System.out.println(statement);

					// Make query for business
					PreparedStatement preparedStatement = connection.prepareStatement(statement);

					int updateResult = preparedStatement.executeUpdate();
					System.out.println("This many rows are updated: " + updateResult);

					// Unit - Null
					// Street
					// City
					// Prov
					// Postal 
				} catch (SQLException e1) {
					errorMessage.setText(e1.getMessage());
				} catch (Exception exception) {
					errorMessage.setText("Business Parsing probably went wrong");
				}


				try {

					String postalCode = postalCodeField.getText();
					String city = cityField.getText();
					String province = provinceField.getText();
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


						String statement1 = "select * from location where postalcode = '";
						statement1 = statement1.concat(postalCode).concat("' and streetadd = '").concat(address);

						if(unit.equals("")){
							statement1 = statement1.concat("' and unitnum is null");
						}
						else{
							statement1 = statement1.concat("' and unitnum = '").concat(unit).concat("'");
						}

						System.out.println(statement1);

						PreparedStatement getidstmt = con.prepareStatement(statement1);
						ResultSet locid = getidstmt.executeQuery();
						locid.next();
						int locationid = locid.getInt(1);
						PreparedStatement deleteStmt = con.prepareStatement("delete from located where businessid = ?");
						deleteStmt.setString(1, businessID);
						deleteStmt.executeUpdate();
						PreparedStatement updateStmt = con.prepareStatement("insert into located values(?, ?)");
						updateStmt.setString(1, businessID);
						updateStmt.setInt(2,locationid);
						updateStmt.executeUpdate();
					}
				} 
				catch (SQLException e1) {
					errorMessage.setText(e1.getMessage());
				} 
				catch (Exception exception) {
					errorMessage.setText("Location Parsing probably went wrong");
				} 

				// show message
				errorMessage.setText("Update Accepted");
			}
		}; 
		searchButton.addActionListener(searchListener);

		// Window stuff
		mainframe.pack();
		mainframe.setVisible(true);


	}

	private void checkID() throws Exception {
		try {
			businessID = businessIDField.getText(); 
			if(businessID.equals("")) {
				throw new Exception("Must enter BusinessID");
			}

		} 
		catch (Exception e) {
			System.out.println("BusinessID was not the correct format");
			System.out.println("Message: " + e.getMessage());
		}
		//System.out.println("BusinessID parsed is: " + businessID);

		PreparedStatement pstmd = connection.prepareStatement("select ownerUsername from business where business.businessid = ?");
		pstmd.setString(1, businessID);

		ResultSet rs = pstmd.executeQuery();

		// Check if there is an owner attached to the id

		// if there isn't any, return false
		if (!rs.next()) {
			throw new Exception("No Business Matching ID");
		}

		if (rs.getString("ownerUserName").equals(businessName)) {
			return;
		}

		throw new Exception("BusinessID does not match Owner");


	}

	// Method to create phone panel
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

	// Method to create day panel
	JPanel createDayPanel(){
		JPanel dayPanel = new JPanel();

		GridBagLayout gb = new GridBagLayout();
		dayPanel.setLayout(gb);
		dayPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		// Day boxes
		GridBagConstraints l = new GridBagConstraints();
		l.anchor = GridBagConstraints.WEST;
		l.gridy = 1;
		l.gridx = 1;
		JLabel ml = new JLabel(" M: ");
		gb.setConstraints(ml, l);
		dayPanel.add(ml);
		l.gridx = 2;
		gb.setConstraints(m, l);
		dayPanel.add(m);
		l.gridx = 3;
		JLabel tl = new JLabel(" T: ");
		gb.setConstraints(tl, l);
		dayPanel.add(tl);
		l.gridx = 4;
		gb.setConstraints(t, l);
		dayPanel.add(t);
		l.gridx = 5;
		JLabel wl = new JLabel(" W: ");
		gb.setConstraints(wl, l);
		dayPanel.add(wl);
		l.gridx = 6;
		gb.setConstraints(w, l);
		dayPanel.add(w);
		l.gridx = 7;
		JLabel rl = new JLabel(" TH: ");
		gb.setConstraints(rl, l);
		dayPanel.add(rl);
		l.gridx = 8;
		gb.setConstraints(r, l);
		dayPanel.add(r);
		l.gridx = 9;
		JLabel fl = new JLabel(" F: ");
		gb.setConstraints(fl, l);
		dayPanel.add(fl);
		l.gridx = 10;
		gb.setConstraints(f, l);
		dayPanel.add(f);
		l.gridx = 11;
		JLabel sl = new JLabel(" S: ");
		gb.setConstraints(sl, l);
		dayPanel.add(sl);
		l.gridx = 12;
		gb.setConstraints(s, l);
		dayPanel.add(s);
		l.gridx = 13;
		JLabel ul = new JLabel(" SU: ");
		gb.setConstraints(ul, l);
		dayPanel.add(ul);
		l.gridx = 14;
		l.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(u, l);
		dayPanel.add(u);

		return dayPanel;
	}

	// Method to create opening time panel
	JPanel createOpeningPanel(){
		JPanel openPanel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		openPanel.setLayout(gb);
		openPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		GridBagConstraints of = new GridBagConstraints();
		of.anchor = GridBagConstraints.WEST;
		of.weightx=1.;
		of.fill=GridBagConstraints.HORIZONTAL;
		of.gridy = 1;
		of.gridx = 1;

		JLabel oH = new JLabel(" H: ");
		gb.setConstraints(oH, of);
		openPanel.add(oH);
		of.gridx = 2;
		gb.setConstraints(startHourField, of);
		openPanel.add(startHourField);
		of.gridx = 3;
		JLabel oM = new JLabel(" M: ");
		gb.setConstraints(oM, of);
		openPanel.add(oM);
		of.gridx = 4;
		gb.setConstraints(startMinField, of);
		openPanel.add(startMinField);

		return openPanel;
	}

	// Method to create closing time panel
	JPanel createClosingPanel(){
		JPanel closePanel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		closePanel.setLayout(gb);
		closePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		GridBagConstraints cf = new GridBagConstraints();
		cf.anchor = GridBagConstraints.WEST;
		cf.weightx=1.;
		cf.fill=GridBagConstraints.HORIZONTAL;
		cf.gridx = 1;
		cf.gridy = 12;

		JLabel cH = new JLabel(" H: ");
		gb.setConstraints(cH, cf);
		closePanel.add(cH);
		cf.gridx = 2;
		gb.setConstraints(finHourField, cf);
		closePanel.add(finHourField);
		cf.gridx = 3;
		JLabel cM = new JLabel(" M: ");
		gb.setConstraints(cM, cf);
		closePanel.add(cM);
		cf.gridx = 4;
		gb.setConstraints(finMinField, cf);
		closePanel.add(finMinField);

		return closePanel;
	}
}

package ownerFunctionality;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AddBusiness {
	private Connection con;
	private String username;
	private JFrame addFrame;

	private JTextField PhoneField1;
	private JTextField PhoneField2;
	private JTextField PhoneField3;

	private JTextField siteField;
	private JTextField typeField;
	private JTextField nbhField;

	private JCheckBox resBox;

	private JCheckBox m,t,w,r,f,s,u;

	private JTextField startHourField;
	private JTextField startMinField;
	private JTextField finHourField;
	private JTextField finMinField;

	// Constructor: builds the functionality window, handles the button press
	public AddBusiness(Connection con, String username){
		this.con = con;
		this.username = username;

		// Definie/initialize parts of frame
		addFrame = new JFrame("Owner Registration");
		// Labels
		JLabel addBLabel = new JLabel("Add New Business");
		JLabel phoneLabel = new JLabel("Enter phone number: ");
		JLabel siteLabel = new JLabel("Enter website: ");
		JLabel typeLabel = new JLabel("Enter type of business: ");
		JLabel nbhLabel = new JLabel("Enter neighbourhood: ");
		JLabel resLabel = new JLabel("Are reservations available? ");
		JLabel opLabel = new JLabel("Specify days of operation: ");
		JLabel startLabel = new JLabel("Specify opening time: ");
		JLabel finiLabel = new JLabel("Specify closing time: ");
		
		// Text fields
		// Note: setMinimumSize prevents the fields from resizing on update
		PhoneField1 = new JTextField(3);
		PhoneField1.setMinimumSize(PhoneField1.getPreferredSize());
		PhoneField2 = new JTextField(3);
		PhoneField2.setMinimumSize(PhoneField2.getPreferredSize());
		PhoneField3 = new JTextField(4);
		PhoneField3.setMinimumSize(PhoneField3.getPreferredSize());
		siteField = new JTextField(10);
		siteField.setMinimumSize(siteField.getPreferredSize());
		typeField = new JTextField(10);
		typeField.setMinimumSize(typeField.getPreferredSize());
		nbhField = new JTextField(10);
		nbhField.setMinimumSize(nbhField.getPreferredSize());
		startHourField = new JTextField(10);
		startHourField.setMinimumSize(startHourField.getPreferredSize());
		startMinField = new JTextField(10);
		startMinField.setMinimumSize(startMinField.getPreferredSize());
		finHourField = new JTextField(10);
		finHourField.setMinimumSize(finHourField.getPreferredSize());
		finMinField = new JTextField(10);
		finMinField.setMinimumSize(finMinField.getPreferredSize());
		
		// Check boxes
		resBox = new JCheckBox();
		m = new JCheckBox();
		t = new JCheckBox();
		w = new JCheckBox();
		r = new JCheckBox();
		f = new JCheckBox();
		s = new JCheckBox();
		u = new JCheckBox();

		// Button
		JButton addButton = new JButton("Add Business");


		// Create and populate the panel using GridBag for layout
		JPanel contentPane = new JPanel();
		addFrame.setContentPane(contentPane);
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

		// Set layout and border
		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Add Business label 
		titleC.gridy = 1;
		titleC.gridx = 1;
		gb.setConstraints(addBLabel, titleC);
		contentPane.add(addBLabel);

		// nbh label
		GridBagConstraints nbhC = new GridBagConstraints();
		nbhC.gridy = 2;
		nbhC.gridx = 1;
		nbhC.insets = new Insets(10, 10, 5, 0);
		nbhC.anchor = GridBagConstraints.WEST;
		gb.setConstraints(nbhLabel, nbhC);
		contentPane.add(nbhLabel);

		// Neighbourhood field
		fieldC.gridy = 3;
		fieldC.gridx = 1;
		fieldC.insets = new Insets(5, 10, 10, 10);
		gb.setConstraints(nbhField, fieldC);
		contentPane.add(nbhField);

		// Phone label 
		GridBagConstraints phoneC = new GridBagConstraints();
		phoneC.gridy = 4;
		phoneC.gridx = 1;
		phoneC.insets = new Insets(10, 10, 5, 0);
		phoneC.anchor = GridBagConstraints.WEST;
		gb.setConstraints(phoneLabel, phoneC);
		contentPane.add(phoneLabel);

		// Phone field panel
		fieldC.gridy = 5;
		fieldC.gridx = 1;
		fieldC.insets = new Insets(5, 10, 10, 10);
		JPanel phonePanel = createPhonePanel();
		gb.setConstraints(phonePanel, fieldC);
		contentPane.add(phonePanel);

		// Site label
		labelC.gridy = 6;
		labelC.gridx = 1;
		gb.setConstraints(siteLabel, labelC);
		contentPane.add(siteLabel);

		// Site field
		fieldC.gridy = 7;
		fieldC.gridx = 1;
		gb.setConstraints(siteField, fieldC);
		contentPane.add(siteField);

		// Type label 
		labelC.gridy = 8;
		labelC.gridx = 1;
		gb.setConstraints(typeLabel, labelC);
		contentPane.add(typeLabel);

		// Type field
		fieldC.gridy = 9;
		fieldC.gridx = 1;
		gb.setConstraints(typeField, fieldC);
		contentPane.add(typeField);

		// Reservation label
		labelC.gridy = 10;
		labelC.gridx = 1;
		gb.setConstraints(resLabel, labelC);
		contentPane.add(resLabel);

		// Reservation box
		GridBagConstraints resBoxC = new GridBagConstraints();
		resBoxC.anchor = GridBagConstraints.WEST;
		resBoxC.gridy = 11;
		resBoxC.gridx = 1;
		resBoxC.gridwidth = GridBagConstraints.REMAINDER;
		resBoxC.insets = new Insets(10, 10, 5, 0);
		gb.setConstraints(resBox, resBoxC);
		contentPane.add(resBox);

		// Days of operation label 
		titleC.gridy = 12;
		titleC.gridx = 1;
		gb.setConstraints(opLabel, titleC);
		contentPane.add(opLabel);

		// Day boxes
		GridBagConstraints l = new GridBagConstraints();
		l.anchor = GridBagConstraints.WEST;
		l.gridy = 13;
		l.gridx = 1;
		l.insets = new Insets(5, 10, 10, 10);
		JPanel dayBoxPanel = createDayPanel();
		gb.setConstraints(dayBoxPanel, l);
		contentPane.add(dayBoxPanel);

		// Opening time label 
		titleC.gridy = 14;
		gb.setConstraints(startLabel, titleC);
		contentPane.add(startLabel);

		// Opening time fields
		GridBagConstraints of = new GridBagConstraints();
		of.anchor = GridBagConstraints.WEST;
		of.gridy = 15;
		of.gridx = 1;
		of.insets = new Insets(5, 10, 10, 10);
		JPanel openPanel = createOpeningPanel();
		gb.setConstraints(openPanel, of);
		contentPane.add(openPanel);

		// Closing time label 
		titleC.gridy = 16;
		gb.setConstraints(finiLabel, titleC);
		contentPane.add(finiLabel);

		// Closing time fields
		GridBagConstraints cf = new GridBagConstraints();
		cf.anchor = GridBagConstraints.WEST;
		cf.gridx = 1;
		cf.gridy = 17;
		cf.insets = new Insets(5, 10, 10, 10);
		JPanel closePanel = createClosingPanel();
		gb.setConstraints(closePanel, cf);
		contentPane.add(closePanel);

		// Add button label 
		buttonC.gridy = 18;
		buttonC.gridx = 1;
		gb.setConstraints(addButton, buttonC);
		contentPane.add(addButton);

		// Error message
		JLabel errorMessage = new JLabel(" ");
		titleC.gridy = 19;
		titleC.gridx = 1;
		errorMessage.setForeground (Color.red);
		gb.setConstraints(errorMessage, titleC);
		contentPane.add(errorMessage);

		// Anonymous class to listen to add business button
		ActionListener buttonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				// Retrieve values from the fields
				String website = siteField.getText();
				String type = typeField.getText();
				String phone = PhoneField1.getText().concat("-").concat(PhoneField2.getText()).concat("-").concat(PhoneField3.getText());
				String res;
				String days = "";
				String nbh = nbhField.getText();

				// Check that phone number matches form of phone number
				if(!phone.matches("[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]")){
					errorMessage.setText("Invalid phone number");
					return;
				}

				// Determine value of res
				if(resBox.isSelected()){
					res = "Y";
				}
				else{
					res = "N";
				}

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
				if(s.isSelected()){
					days = days.concat("U");
				}
				
				// Check non-null days
				if(days.equals("")){
					errorMessage.setText("Must enter days of operation");
					return;
				}

				// Check that time fields are numbers
				if(!(startHourField.getText().matches("[0-9]+") && startMinField.getText().matches("[0-9]+") 
						&& finHourField.getText().matches("[0-9]+") && finMinField.getText().matches("[0-9]+"))){
					errorMessage.setText("Invalid time");
					return;
				}

				// Calculate the start and end time, check that times are valid
				int sH = Integer.parseInt(startHourField.getText());
				int sM = Integer.parseInt(startMinField.getText());
				int fH = Integer.parseInt(finHourField.getText());
				int fM = Integer.parseInt(finMinField.getText());
				int startTime;
				int finTime;
				
				if(sH < 24 && sH >= 0 && sM >=0 && sM < 60){
					startTime = sH*100 + sM;
				}
				else{
					errorMessage.setText("Invalid opening time");
					return;
				}

				if(fH < 24 && fH >= 0 && fM >=0 && fM < 60){
					finTime = fH*100 + fM;
				}
				else{
					errorMessage.setText("Invalid closing time");
					return;
				}

				// Construct insertion of business
				String loginQuery = "insert into business values (1, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				// Attempt insertion
				try{
					PreparedStatement stmt = con.prepareStatement(loginQuery);
					if(nbh.equals("")){
						stmt.setNull(1, Types.VARCHAR);
					}
					else{
						stmt.setString(1, nbh);
					}
					stmt.setString(2, phone);
					if(website.equals("")){
						stmt.setNull(3, Types.VARCHAR);
					}
					else{
						stmt.setString(3, website);
					}
					if(type.equals("")){
						stmt.setNull(4, Types.VARCHAR);
					}
					else{
						stmt.setString(4, type);
					}
					stmt.setString(5, username);
					stmt.setString(6, res);
					if(days.equals("")){
						stmt.setNull(7, Types.VARCHAR);
					}
					else{
						stmt.setString(7, days);
					}
					stmt.setInt(8, startTime);
					stmt.setInt(9, finTime);
					stmt.executeQuery();

					addFrame.dispose();

				}
				catch (SQLException ex)
				{
					System.out.println("Message: " + ex.getMessage());
					errorMessage.setText("Invalid input");
				}	


			}
		};
		addButton.addActionListener(buttonListener);

		// Resize window
		addFrame.pack();

		// Centre window
		Dimension d = addFrame.getToolkit().getScreenSize();
		Rectangle r = addFrame.getBounds();
		addFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// Set window visible
		addFrame.setVisible(true);


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

	// Method to create day selection panel
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

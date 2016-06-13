package ownerFunctionality;

import java.sql.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import utility.*;


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
		JLabel startLabel = new JLabel("Opens by: ");
		JLabel finiLabel = new JLabel("Closes after: ");

		JLabel locationLabel = new JLabel("Location");
		
		JLabel unitLabel = new JLabel("Unit: ");
		JLabel addressLabel = new JLabel("Address: ");
		JLabel cityLabel = new JLabel("City: ");
		JLabel provLabel = new JLabel("Province: ");
		JLabel postalLabel = new JLabel("Postal Code: ");
		
		// Button
		JButton searchButton = new JButton("Search");
		
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
		//constraints.anchor = GridBagConstraints.WEST;
		
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
		
		
		
		/* 
		constraints.gridy = 7;
		gb.setConstraints(, constraints);
		contentpane.add();
		 */
		
		
		
		
		
		
		// Window stuff
		mainframe.pack();
		mainframe.setVisible(true);
		
		mainframe.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{ 
				System.exit(0); 
			}
		});
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

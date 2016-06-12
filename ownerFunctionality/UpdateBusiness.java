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
		JLabel businessIDLabel = new JLabel("BusinessID: ");
		JLabel websiteLabel = new JLabel("Website: ");
		JLabel typeLabel = new JLabel("Type: ");
		
		JLabel phoneLabel = new JLabel("Phone Number: ");
		
		JLabel operationLabel = new JLabel("Days of Operation: ");
		JLabel startLabel = new JLabel("Opens by: ");
		JLabel finiLabel = new JLabel("Closes after: ");
		
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
		
		// Set content pane
		contentpane.setLayout(gb);
		
		// Placing Components
		
		
		
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
	
	

}

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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import utility.TableFromResultSet;

public class BusinessSearch {
	Connection con;
	JFrame searchFrame;
	JTable results;
	
	private JTextField typeField;
	private JTextField nbhField;

	private JCheckBox resBox;

	private JCheckBox m,t,w,r,f,s,u;

	private JTextField startHourField;
	private JTextField startMinField;
	private JTextField finHourField;
	private JTextField finMinField;
	
	private JTextField addressField;
	private JTextField postalCodeField;
	private JTextField cityField;
	private JTextField provinceField;
	
	private JTextField usernameField;
	
	public BusinessSearch(Connection con){
		this.con = con;
		String res[] = {"Yes", "No", "Don't Care"};
		
		// Define/initialize parts of frame
		searchFrame = new JFrame("Business Search");
				// Labels
				JLabel titleLabel = new JLabel("Search for Businesses by Attributes");
				JLabel ownerLabel = new JLabel("Business owner: ");
				JLabel typeLabel = new JLabel("Type of business: ");
				JLabel nbhLabel = new JLabel("Neighbourhood: ");
				JLabel resLabel = new JLabel("Reservations available? ");
				JLabel opLabel = new JLabel("Days of operation: ");
				JLabel startLabel = new JLabel("Opens by: ");
				JLabel finiLabel = new JLabel("Closes after: ");
				
				JLabel locationLabel = new JLabel("Enter location:");
				JLabel addressLabel = new JLabel("Street address: ");
				JLabel cityLabel = new JLabel("City: ");
				JLabel provinceLabel = new JLabel("Province: ");
				JLabel postalCodeLabel = new JLabel("Postal code: ");
				
				
				// Text fields
				// Note: setMinimumSize prevents the fields from resizing on update
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
				
				addressField = new JTextField(10);
				addressField.setMinimumSize(addressField.getPreferredSize());
				postalCodeField = new JTextField(10);
				postalCodeField.setMinimumSize(postalCodeField.getPreferredSize());
				cityField = new JTextField(10);
				cityField.setMinimumSize(cityField.getPreferredSize());
				provinceField = new JTextField(10);
				provinceField.setMinimumSize(provinceField.getPreferredSize());
				usernameField = new JTextField(10);
				usernameField.setMinimumSize(usernameField.getPreferredSize());
				

				JComboBox<String> resBox = new JComboBox<String>(res);
				
				// Check boxes
				m = new JCheckBox();
				t = new JCheckBox();
				w = new JCheckBox();
				r = new JCheckBox();
				f = new JCheckBox();
				s = new JCheckBox();
				u = new JCheckBox();

				// Button
				JButton searchButton = new JButton("Search");


				// Create and populate the panel using GridBag for layout
				JPanel contentPane = new JPanel();
				searchFrame.setContentPane(contentPane);
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

				// Add title label 
				titleC.gridy = 1;
				titleC.gridx = 1;
				gb.setConstraints(titleLabel, titleC);
				contentPane.add(titleLabel);
				
				// owner label
				labelC.gridy = 2;
				labelC.gridx = 1;
				gb.setConstraints(ownerLabel, labelC);
				contentPane.add(ownerLabel);
				
				// owner username field
				fieldC.gridy = 3;
				fieldC.gridx = 1;
				fieldC.insets = new Insets(5, 10, 10, 10);
				gb.setConstraints(usernameField, fieldC);
				contentPane.add(usernameField);

				// nbh label
				GridBagConstraints nbhC = new GridBagConstraints();
				nbhC.gridy = 4;
				nbhC.gridx = 1;
				nbhC.insets = new Insets(10, 10, 5, 0);
				nbhC.anchor = GridBagConstraints.WEST;
				gb.setConstraints(nbhLabel, nbhC);
				contentPane.add(nbhLabel);

				// Neighbourhood field
				fieldC.gridy = 5;
				fieldC.gridx = 1;
				gb.setConstraints(nbhField, fieldC);
				contentPane.add(nbhField);
				
				// Type label 
				labelC.gridy = 6;
				labelC.gridx = 1;
				gb.setConstraints(typeLabel, labelC);
				contentPane.add(typeLabel);

				// Type field
				fieldC.gridy = 7;
				fieldC.gridx = 1;
				gb.setConstraints(typeField, fieldC);
				contentPane.add(typeField);

				// Reservation label
				labelC.gridy = 8;
				labelC.gridx = 1;
				gb.setConstraints(resLabel, labelC);
				contentPane.add(resLabel);

				// Reservation box
				GridBagConstraints resBoxC = new GridBagConstraints();
				resBoxC.anchor = GridBagConstraints.WEST;
				resBoxC.gridy = 9;
				resBoxC.gridx = 1;
				resBoxC.gridwidth = GridBagConstraints.REMAINDER;
				resBoxC.insets = new Insets(10, 10, 5, 0);
				gb.setConstraints(resBox, resBoxC);
				contentPane.add(resBox);

				// Days of operation label 
				titleC.gridy = 10;
				titleC.gridx = 1;
				gb.setConstraints(opLabel, titleC);
				contentPane.add(opLabel);

				// Day boxes
				GridBagConstraints l = new GridBagConstraints();
				l.anchor = GridBagConstraints.WEST;
				l.gridy = 11;
				l.gridx = 1;
				l.insets = new Insets(5, 10, 10, 10);
				JPanel dayBoxPanel = createDayPanel();
				gb.setConstraints(dayBoxPanel, l);
				contentPane.add(dayBoxPanel);

				// Opening time label 
				titleC.gridy = 12;
				gb.setConstraints(startLabel, titleC);
				contentPane.add(startLabel);

				// Opening time fields
				GridBagConstraints of = new GridBagConstraints();
				of.anchor = GridBagConstraints.WEST;
				of.gridy = 13;
				of.gridx = 1;
				of.insets = new Insets(5, 10, 10, 10);
				JPanel openPanel = createOpeningPanel();
				gb.setConstraints(openPanel, of);
				contentPane.add(openPanel);

				// Closing time label 
				titleC.gridy = 14;
				gb.setConstraints(finiLabel, titleC);
				contentPane.add(finiLabel);

				// Closing time fields
				GridBagConstraints cf = new GridBagConstraints();
				cf.anchor = GridBagConstraints.WEST;
				cf.gridx = 1;
				cf.gridy = 15;
				cf.insets = new Insets(5, 10, 10, 10);
				JPanel closePanel = createClosingPanel();
				gb.setConstraints(closePanel, cf);
				contentPane.add(closePanel);
				
				
				// Location title
				titleC.gridy = 16;
				gb.setConstraints(locationLabel, titleC);
				contentPane.add(locationLabel);
				
				// Address label
				titleC.gridy = 17;
				gb.setConstraints(addressLabel, titleC);
				contentPane.add(addressLabel);
				
				// Address field
				fieldC.gridy = 18;
				fieldC.gridx = 1;
				gb.setConstraints(addressField, fieldC);
				contentPane.add(addressField);
				
				// City label
				titleC.gridy = 19;
				gb.setConstraints(cityLabel, titleC);
				contentPane.add(cityLabel);
				
				// City field
				fieldC.gridy = 20;
				fieldC.gridx = 1;
				gb.setConstraints(cityField, fieldC);
				contentPane.add(cityField);
				
				// Province title
				titleC.gridy = 21;
				gb.setConstraints(provinceLabel, titleC);
				contentPane.add(provinceLabel);
				
				// Province field
				fieldC.gridy = 22;
				fieldC.gridx = 1;
				gb.setConstraints(provinceField, fieldC);
				contentPane.add(provinceField);
				
				// Location title
				titleC.gridy = 23;
				gb.setConstraints(postalCodeLabel, titleC);
				contentPane.add(postalCodeLabel);
				
				// Province field
				fieldC.gridy = 24;
				fieldC.gridx = 1;
				gb.setConstraints(postalCodeField, fieldC);
				contentPane.add(postalCodeField);

				// Add button label 
				buttonC.gridy = 25;
				buttonC.gridx = 1;
				gb.setConstraints(searchButton, buttonC);
				contentPane.add(searchButton);

				// Error message
				JLabel errorMessage = new JLabel(" ");
				titleC.gridy = 26;
				titleC.gridx = 1;
				errorMessage.setForeground (Color.red);
				gb.setConstraints(errorMessage, titleC);
				contentPane.add(errorMessage);
				
				results = new JTable();
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setViewportView(results);
				
				gb.setConstraints(scrollPane, tableC);
				contentPane.add(scrollPane);
				
				ActionListener searchButtonListener = new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
						try{
							boolean first = true;
							Vector<PreparedStatement> statements = new Vector<PreparedStatement>();
							String statement = "select * from business natural left outer join located natural left outer join location natural left outer join postalcode where";
							
							// Check type;
							String type = typeField.getText();
							if(!type.equals("")){
								statement = statement.concat(" type = '");
								statement = statement.concat(type);
								statement = statement.concat("'");
								first = false;
							}
							String username = usernameField.getText();
							if(!username.equals("")){
								if(!first){
									statement = statement.concat(" and");
								}
								else{
									first = false;
								}

								statement = statement.concat(" ownerUsername = '");
								statement = statement.concat(username);
								statement = statement.concat("'");
							}
							String res = (String) resBox.getSelectedItem();
							// Determine value of res
							if(resBox.equals("Yes")){
								res = "Y";
							}
							else if(resBox.equals("No")){
								res = "N";
							}
							
							if(res.equals("Y") || res.equals("N")){
								if(!first){
									statement = statement.concat(" and");
								}
								else{
									first = false;
								}

								statement = statement.concat(" reservationflag = '");
								statement = statement.concat(res);
								statement = statement.concat("'");
							}
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
							if(s.isSelected()){
								days = days.concat("U");
							}
							if(!days.equals("")){
								if(!first){
									statement = statement.concat(" and");
								}
								else{
									first = false;
								}

								statement = statement.concat(" dayofOperation = '");
								statement = statement.concat(res);
								statement = statement.concat("'");
							}

							String nbh = nbhField.getText();
							if(!nbh.equals("")){
								if(!first){
									statement = statement.concat(" and");
								}
								else{
									first = false;
								}

								statement = statement.concat(" Neighborhood = '");
								statement = statement.concat(nbh);
								statement = statement.concat("'");
							}
							
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
										if(!first){
											statement = statement.concat(" and");
										}
										else{
											first = false;
										}

										statement = statement.concat(" startTime <= ");
										statement = statement.concat(Integer.toString(startTime));
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
										if(!first){
											statement = statement.concat(" and");
										}
										else{
											first = false;
										}

										statement = statement.concat(" finishTime > ");
										statement = statement.concat(Integer.toString(finTime));
									}
									else{
										errorMessage.setText("Invalid closing time");
										return;
									}
								}
							}
							
							String address = addressField.getText();
							String city = cityField.getText();
							String province = provinceField.getText();
							String postalCode = postalCodeField.getText();
							
							if(!address.equals("")){
								if(!first){
									statement = statement.concat(" and");
								}
								else{
									first = false;
								}

								statement = statement.concat(" streetAdd = '");
								statement = statement.concat(address);
								statement = statement.concat("'");
							}
							if(!city.equals("")){
								if(!first){
									statement = statement.concat(" and");
								}
								else{
									first = false;
								}

								statement = statement.concat(" city = '");
								statement = statement.concat(city);
								statement = statement.concat("'");
							}
							if(!province.equals("")){
								if(!first){
									statement = statement.concat(" and");
								}
								else{
									first = false;
								}

								statement = statement.concat(" province = '");
								statement = statement.concat(province);
								statement = statement.concat("'");
							}
							if(!postalCode.equals("")){
								if(!first){
									statement = statement.concat(" and");
								}
								else{
									first = false;
								}

								statement = statement.concat(" postalCode = '");
								statement = statement.concat(postalCode);
								statement = statement.concat("'");
							}
							
							PreparedStatement stmt = con.prepareStatement(statement);
							System.out.println(statement);
							
							ResultSet rs = stmt.executeQuery();
							ResultSetMetaData rsmd = rs.getMetaData();
							TableFromResultSet.replaceTable(results, rs, rsmd);
							}
							catch(SQLException ex){
								System.out.println("Message: " + ex.getMessage());
							}
					}
				};
				searchButton.addActionListener(searchButtonListener);

		
		// Resize window
				searchFrame.pack();

				// Centre window
				Dimension d = searchFrame.getToolkit().getScreenSize();
				Rectangle r = searchFrame.getBounds();
				searchFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

				// Set window visible
				searchFrame.setVisible(true);


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

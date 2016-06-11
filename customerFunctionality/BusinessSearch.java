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

	private JComboBox<String> resBox;

	private JCheckBox m,t,w,r,f,s,u;
	private JCheckBox ownerbox, typebox, nbhbox, resbox, opbox, startbox, finbox, addbox, citybox, provbox, postbox;

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
				JLabel searchLabel = new JLabel("Enter values to search by:");
				JLabel selectLabel = new JLabel("Select attributes to display:");
				JPanel selectPanel = new JPanel();
				
				
				ownerbox = new JCheckBox();
				typebox = new JCheckBox();
				nbhbox = new JCheckBox();
				resbox = new JCheckBox();
				opbox = new JCheckBox();
				startbox = new JCheckBox();
				finbox = new JCheckBox();
				addbox = new JCheckBox();
				citybox = new JCheckBox();
				provbox = new JCheckBox();
				postbox = new JCheckBox();
				
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
				

				resBox = new JComboBox<String>(res);
				
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
				JScrollPane scrollContentPane = new JScrollPane(contentPane);
				searchFrame.setContentPane(scrollContentPane);
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
				
				// Selection label
				labelC.gridy = 2;
				labelC.gridx = 1;
				gb.setConstraints(selectLabel, labelC);
				contentPane.add(selectLabel);
				
				GridBagConstraints sc = new GridBagConstraints();
				sc.anchor = GridBagConstraints.WEST;
				sc.gridy = 3;
				sc.gridx = 1;
				sc.insets = new Insets(5, 10, 10, 10);
				selectPanel = createSelectPanel();
				gb.setConstraints(selectPanel, sc);
				contentPane.add(selectPanel);
				
				// Search label
				labelC.gridy = 4;
				labelC.gridx = 1;
				gb.setConstraints(searchLabel, labelC);
				contentPane.add(searchLabel);
				
				// owner label
				labelC.gridy = 5;
				labelC.gridx = 1;
				gb.setConstraints(ownerLabel, labelC);
				contentPane.add(ownerLabel);
				
				// owner username field
				fieldC.gridy = 6;
				fieldC.gridx = 1;
				fieldC.insets = new Insets(5, 10, 10, 10);
				gb.setConstraints(usernameField, fieldC);
				contentPane.add(usernameField);

				// nbh label
				GridBagConstraints nbhC = new GridBagConstraints();
				nbhC.gridy = 7;
				nbhC.gridx = 1;
				nbhC.insets = new Insets(10, 10, 5, 0);
				nbhC.anchor = GridBagConstraints.WEST;
				gb.setConstraints(nbhLabel, nbhC);
				contentPane.add(nbhLabel);

				// Neighbourhood field
				fieldC.gridy = 8;
				fieldC.gridx = 1;
				gb.setConstraints(nbhField, fieldC);
				contentPane.add(nbhField);
				
				// Type label 
				labelC.gridy = 9;
				labelC.gridx = 1;
				gb.setConstraints(typeLabel, labelC);
				contentPane.add(typeLabel);

				// Type field
				fieldC.gridy = 10;
				fieldC.gridx = 1;
				gb.setConstraints(typeField, fieldC);
				contentPane.add(typeField);

				// Reservation label
				labelC.gridy = 11;
				labelC.gridx = 1;
				gb.setConstraints(resLabel, labelC);
				contentPane.add(resLabel);

				// Reservation box
				GridBagConstraints resBoxC = new GridBagConstraints();
				resBoxC.anchor = GridBagConstraints.WEST;
				resBoxC.gridy = 12;
				resBoxC.gridx = 1;
				resBoxC.gridwidth = GridBagConstraints.REMAINDER;
				resBoxC.insets = new Insets(10, 10, 5, 0);
				gb.setConstraints(resBox, resBoxC);
				contentPane.add(resBox);

				// Days of operation label 
				titleC.gridy = 13;
				titleC.gridx = 1;
				gb.setConstraints(opLabel, titleC);
				contentPane.add(opLabel);

				// Day boxes
				GridBagConstraints l = new GridBagConstraints();
				l.anchor = GridBagConstraints.WEST;
				l.gridy = 14;
				l.gridx = 1;
				l.insets = new Insets(5, 10, 10, 10);
				JPanel dayBoxPanel = createDayPanel();
				gb.setConstraints(dayBoxPanel, l);
				contentPane.add(dayBoxPanel);

				// Opening time label 
				titleC.gridy = 15;
				gb.setConstraints(startLabel, titleC);
				contentPane.add(startLabel);

				// Opening time fields
				GridBagConstraints of = new GridBagConstraints();
				of.anchor = GridBagConstraints.WEST;
				of.gridy = 16;
				of.gridx = 1;
				of.insets = new Insets(5, 10, 10, 10);
				JPanel openPanel = createOpeningPanel();
				gb.setConstraints(openPanel, of);
				contentPane.add(openPanel);

				// Closing time label 
				titleC.gridy = 17;
				gb.setConstraints(finiLabel, titleC);
				contentPane.add(finiLabel);

				// Closing time fields
				GridBagConstraints cf = new GridBagConstraints();
				cf.anchor = GridBagConstraints.WEST;
				cf.gridx = 1;
				cf.gridy = 18;
				cf.insets = new Insets(5, 10, 10, 10);
				JPanel closePanel = createClosingPanel();
				gb.setConstraints(closePanel, cf);
				contentPane.add(closePanel);
				
				
				// Location title
				titleC.gridy = 19;
				gb.setConstraints(locationLabel, titleC);
				contentPane.add(locationLabel);
				
				// Address label
				titleC.gridy = 20;
				gb.setConstraints(addressLabel, titleC);
				contentPane.add(addressLabel);
				
				// Address field
				fieldC.gridy = 21;
				fieldC.gridx = 1;
				gb.setConstraints(addressField, fieldC);
				contentPane.add(addressField);
				
				// City label
				titleC.gridy = 22;
				gb.setConstraints(cityLabel, titleC);
				contentPane.add(cityLabel);
				
				// City field
				fieldC.gridy = 23;
				fieldC.gridx = 1;
				gb.setConstraints(cityField, fieldC);
				contentPane.add(cityField);
				
				// Province title
				titleC.gridy = 24;
				gb.setConstraints(provinceLabel, titleC);
				contentPane.add(provinceLabel);
				
				// Province field
				fieldC.gridy = 25;
				fieldC.gridx = 1;
				gb.setConstraints(provinceField, fieldC);
				contentPane.add(provinceField);
				
				// Location title
				titleC.gridy = 26;
				gb.setConstraints(postalCodeLabel, titleC);
				contentPane.add(postalCodeLabel);
				
				// Province field
				fieldC.gridy = 27;
				fieldC.gridx = 1;
				gb.setConstraints(postalCodeField, fieldC);
				contentPane.add(postalCodeField);

				// Add button label 
				buttonC.gridy = 28;
				buttonC.gridx = 1;
				gb.setConstraints(searchButton, buttonC);
				contentPane.add(searchButton);

				// Error message
				JLabel errorMessage = new JLabel(" ");
				titleC.gridy = 29;
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
							//private JCheckBox ownerbox, typebox, nbhbox, resbox, opbox, startbox, finbox, addbox, citybox, provbox, postbox;
							String statement = "select BusinessID";
							if(ownerbox.isSelected()){
								statement = statement.concat(", ownerUsername");
							}
							if(typebox.isSelected()){
								statement = statement.concat(", type");
							}
							if(nbhbox.isSelected()){
								statement = statement.concat(", neighborhood");
							}
							if(resbox.isSelected()){
								statement = statement.concat(", reservationflag");
							}
							if(opbox.isSelected()){
								statement = statement.concat(", dayofoperation");
							}
							if(startbox.isSelected()){
								statement = statement.concat(", starttime");
							}
							if(finbox.isSelected()){
								statement = statement.concat(", finishtime");
							}
							if(addbox.isSelected()){
								statement = statement.concat(", streetadd");
							}
							if(citybox.isSelected()){
								statement = statement.concat(", city");
							}
							if(provbox.isSelected()){
								statement = statement.concat(", province");
							}
							if(postbox.isSelected()){
								statement = statement.concat(", postalcode");
							}
							
							
							boolean first = true;
							statement = statement.concat(" from business natural left outer join located natural left outer join location natural left outer join postalcode");
							
							// Check type;
							String type = typeField.getText();
							if(!type.equals("")){
								statement = statement.concat(" where");
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
									statement = statement.concat(" where");
									first = false; 
								}

								statement = statement.concat(" ownerUsername = '");
								statement = statement.concat(username);
								statement = statement.concat("'");
							}
							String res = (String) resBox.getSelectedItem();
							// Determine value of res
							System.out.println("Yes");
							if(res.equals("Yes")){
								res = "Y";
							}
							else if(res.equals("No")){
								res = "N";
							}
							
							if(res.equals("Y") || res.equals("N")){
								if(!first){
									statement = statement.concat(" and");
								}
								else{
									statement = statement.concat(" where");
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
									statement = statement.concat(" where");
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
									statement = statement.concat(" where");
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
											statement = statement.concat(" where");
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
											statement = statement.concat(" where");
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
									statement = statement.concat(" where");
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
									statement = statement.concat(" where");
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
									statement = statement.concat(" where");
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
									statement = statement.concat(" where");
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
	
	
	// Method to create attribute selection panel
	JPanel createSelectPanel(){
		JPanel selectPanel = new JPanel();

		GridBagLayout gb = new GridBagLayout();
		selectPanel.setLayout(gb);
		selectPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		// Attribute boxes
		GridBagConstraints l = new GridBagConstraints();
		l.anchor = GridBagConstraints.WEST;
		l.gridx = 1;
		l.gridy = 1;
		JLabel ml = new JLabel("Owner: ");
		gb.setConstraints(ml, l);
		selectPanel.add(ml);
		l.gridx = 2;
		gb.setConstraints(ownerbox, l);
		selectPanel.add(ownerbox);
		l.gridx = 1;
		l.gridy = 2;
		JLabel tl = new JLabel("Type: ");
		gb.setConstraints(tl, l);
		selectPanel.add(tl);
		l.gridx = 2;
		gb.setConstraints(typebox, l);
		selectPanel.add(typebox);
		l.gridx = 1;
		l.gridy = 3;
		JLabel wl = new JLabel("Neighborhood: ");
		gb.setConstraints(wl, l);
		selectPanel.add(wl);
		l.gridx = 2;
		gb.setConstraints(nbhbox, l);
		selectPanel.add(nbhbox);
		l.gridx = 1;
		l.gridy = 4;
		JLabel rl = new JLabel("Reservations: ");
		gb.setConstraints(rl, l);
		selectPanel.add(rl);
		l.gridx = 2;
		gb.setConstraints(resbox, l);
		selectPanel.add(resbox);
		l.gridx = 1;
		l.gridy = 5;
		JLabel fl = new JLabel("Days of Operation: ");
		gb.setConstraints(fl, l);
		selectPanel.add(fl);
		l.gridx = 2;
		gb.setConstraints(opbox, l);
		selectPanel.add(opbox);
		l.gridx = 1;
		l.gridy = 6;
		JLabel sl = new JLabel("Opening Time: ");
		gb.setConstraints(sl, l);
		selectPanel.add(sl);
		l.gridx = 2;
		gb.setConstraints(startbox, l);
		selectPanel.add(startbox);
		l.gridx = 1;
		l.gridy = 7;
		JLabel ul = new JLabel("Closing Time: ");
		gb.setConstraints(ul, l);
		selectPanel.add(ul);
		l.gridx = 2;
		gb.setConstraints(finbox, l);
		selectPanel.add(finbox);
		l.gridx = 1;
		l.gridy = 8;
		JLabel al = new JLabel("Address: ");
		gb.setConstraints(al, l);
		selectPanel.add(al);
		l.gridx = 2;
		gb.setConstraints(addbox, l);
		selectPanel.add(addbox);
		l.gridx = 1;
		l.gridy = 9;
		JLabel cl = new JLabel("City: ");
		gb.setConstraints(cl, l);
		selectPanel.add(cl);
		l.gridx = 2;
		gb.setConstraints(citybox, l);
		selectPanel.add(citybox);
		l.gridx = 1;
		l.gridy = 10;
		JLabel pl = new JLabel("Province: ");
		gb.setConstraints(pl, l);
		selectPanel.add(pl);
		l.gridx = 2;
		gb.setConstraints(provbox, l);
		selectPanel.add(provbox);
		l.gridx = 1;
		l.gridy = 11;
		JLabel pol = new JLabel("Postal Code: ");
		gb.setConstraints(pol, l);
		selectPanel.add(pol);
		l.gridx = 2;
		gb.setConstraints(postbox, l);
		selectPanel.add(postbox);

		return selectPanel;
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

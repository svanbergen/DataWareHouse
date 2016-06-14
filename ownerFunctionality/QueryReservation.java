package ownerFunctionality;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.management.Query;
import javax.swing.*;

import utility.*;

public class QueryReservation {

	private Timestamp beginTime = null;
	private Timestamp endTime = null;
	private Connection connection;
	
	private String businessName;
	private String businessID;
	
	// Frame and Textfields
	private JFrame mainframe;
	private JTable resultTable;
	
	private JTextField bussinessIdTextField;
	
	private JTextField yearStart;
	private JTextField monthStart;
	private JTextField dayStart;
	private JTextField hourStart;
	private JTextField minStart;
	
	private JTextField yearEnd;
	private JTextField monthEnd;
	private JTextField dayEnd;
	private JTextField hourEnd;
	private JTextField minEnd;
	
	private JTextField businessIDfield;
	
	
	public QueryReservation(String username, Connection con) {
		
		// Initialize global variables
		this.connection = con;
		this.businessName = username;
		
		// Initialize mainframe
		mainframe = new JFrame("Query Reservation by Time");
		
		
		// initialize textfield
		yearStart = new JTextField(10);
		yearStart.setMinimumSize(yearStart.getPreferredSize());
		monthStart = new JTextField(10);
		monthStart.setMinimumSize(monthStart.getPreferredSize());
		dayStart = new JTextField(10);
		dayStart.setMinimumSize(dayStart.getPreferredSize());
		hourStart = new JTextField(10);
		hourStart.setMinimumSize(hourStart.getPreferredSize());
		minStart = new JTextField(10);
		minStart.setMinimumSize(minStart.getPreferredSize());
		
		yearEnd = new JTextField(10);
		yearEnd.setMinimumSize(yearEnd.getPreferredSize());
		monthEnd = new JTextField(10);
		monthEnd.setMinimumSize(monthEnd.getPreferredSize());
		dayEnd = new JTextField(10);
		dayEnd.setMinimumSize(dayEnd.getPreferredSize());
		hourEnd = new JTextField(10);
		hourEnd.setMinimumSize(hourEnd.getPreferredSize());
		minEnd = new JTextField(10);
		minEnd.setMinimumSize(minEnd.getPreferredSize());
		
		businessIDfield = new JTextField(10);
		businessIDfield.setMinimumSize(businessIDfield.getPreferredSize());
		
		
		// Labels
		
		JLabel yearStartLabel = new JLabel("Start Year: ");
		JLabel monthStartLabel = new JLabel("Start Month: ");
		JLabel dayStartLabel = new JLabel("Start Day: ");
		JLabel hourStartLabel = new JLabel("Start Hour: ");
		JLabel minStartLabel = new JLabel("Start Minute: ");
		
		JLabel yearEndLabel = new JLabel("End Year: ");
		JLabel monthEndLabel = new JLabel("End Month: ");
		JLabel dayEndLabel = new JLabel("End Day: ");
		JLabel hourEndLabel = new JLabel("End Hour: ");
		JLabel minEndLabel = new JLabel("End Minute: ");
		
		JLabel businessIDLabel = new JLabel("BusinessID: ");
		
		// Button
		JButton searchButton = new JButton("Search");
		
		// Panel
		
		JPanel contentpane = new JPanel();
		
		// only time Scroll Content Pane is used
		JScrollPane scrollContentPane = new JScrollPane(contentpane);
		mainframe.setContentPane(scrollContentPane);
		
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		// set content pane 
		contentpane.setLayout(gb);
		contentpane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// add panel to frame
		mainframe.setContentPane(scrollContentPane);
		
		// Constraint sets
		
		// label
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(5, 5, 5, 0);
		
		GridBagConstraints fieldc = new GridBagConstraints();
		fieldc.anchor = GridBagConstraints.WEST;
		fieldc.insets = new Insets(10, 10, 10, 10);
		
		
		// textfield
		// button
		// table
		
		
		// Result Table
		resultTable = new JTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(resultTable);
		
		
		// YearStartLabel
		constraints.gridx = 1;
		constraints.gridy = 1;
		gb.setConstraints(yearStartLabel, constraints);
		contentpane.add(yearStartLabel);
		
		// YearStart
		constraints.gridx = 2;
		constraints.gridy = 1;
		gb.setConstraints(yearStart, constraints);
		contentpane.add(yearStart);
		
		// MonthStartLabel
		constraints.gridx = 1;
		constraints.gridy = 2;
		gb.setConstraints(monthStartLabel, constraints);
		contentpane.add(monthStartLabel);
		
		// MonthStart
		constraints.gridx = 2;
		constraints.gridy = 2;
		gb.setConstraints(monthStart, constraints);
		contentpane.add(monthStart);
		
		// DayStartLabel
		constraints.gridx = 1;
		constraints.gridy = 3;
		gb.setConstraints(dayStartLabel,constraints);
		contentpane.add(dayStartLabel);
		
		// DayStart
		constraints.gridx = 2;
		constraints.gridy = 3;
		gb.setConstraints(dayStart, constraints);
		contentpane.add(dayStart);
		
		// HourStartLabel
		constraints.gridx = 1;
		constraints.gridy = 4;
		gb.setConstraints(hourStartLabel, constraints);
		contentpane.add(hourStartLabel);
		
		// HourStart
		constraints.gridx = 2;
		constraints.gridy = 4;
		gb.setConstraints(hourStart, constraints);
		contentpane.add(hourStart);
		
		// MinStartLabel
		constraints.gridx = 1;
		constraints.gridy = 5;
		gb.setConstraints(minStartLabel, constraints);
		contentpane.add(minStartLabel);
		
		// MinStart
		constraints.gridx = 2;
		constraints.gridy = 5;
		gb.setConstraints(minStart, constraints);
		contentpane.add(minStart);
		
		// Placeholder
		JLabel holder = new JLabel("Ending Date");
		constraints.gridy = 6;
		gb.setConstraints(holder, constraints);
		contentpane.add(holder);
		
		// YearStartLabel
		constraints.gridx = 1;
		constraints.gridy = 7;
		gb.setConstraints(yearEndLabel, constraints);
		contentpane.add(yearEndLabel);

		// YearStart
		constraints.gridx = 2;
		constraints.gridy = 7;
		gb.setConstraints(yearEnd, constraints);
		contentpane.add(yearEnd);

		// MonthStartLabel
		constraints.gridx = 1;
		constraints.gridy = 8;
		gb.setConstraints(monthEndLabel, constraints);
		contentpane.add(monthEndLabel);

		// MonthStart
		constraints.gridx = 2;
		constraints.gridy = 8;
		gb.setConstraints(monthEnd, constraints);
		contentpane.add(monthEnd);

		// DayStartLabel
		constraints.gridx = 1;
		constraints.gridy = 9;
		gb.setConstraints(dayEndLabel,constraints);
		contentpane.add(dayEndLabel);

		// DayStart
		constraints.gridx = 2;
		constraints.gridy = 9;
		gb.setConstraints(dayEnd, constraints);
		contentpane.add(dayEnd);

		// HourStartLabel
		constraints.gridx = 1;
		constraints.gridy = 10;
		gb.setConstraints(hourEndLabel, constraints);
		contentpane.add(hourEndLabel);

		// HourStart
		constraints.gridx = 2;
		constraints.gridy = 10;
		gb.setConstraints(hourEnd, constraints);
		contentpane.add(hourEnd);

		// MinStartLabel
		constraints.gridx = 1;
		constraints.gridy = 11;
		gb.setConstraints(minEndLabel, constraints);
		contentpane.add(minEndLabel);

		// MinStart
		constraints.gridx = 2;
		constraints.gridy = 11;
		gb.setConstraints(minEnd, constraints);
		contentpane.add(minEnd);
		
		// businessID holder
		JLabel bHolder = new JLabel("Business ID");
		constraints.gridy = 12;
		gb.setConstraints(bHolder, constraints);
		contentpane.add(bHolder);
		
		// Business ID
		constraints.gridx = 1;
		constraints.gridy = 13;
		gb.setConstraints(businessIDLabel, constraints);
		contentpane.add(businessIDLabel);
		
		constraints.gridx = 2;
		constraints.gridy = 13;
		gb.setConstraints(businessIDfield, constraints);
		contentpane.add(businessIDfield);
		
		
		
		// Error Message
		JLabel errorMessage = new JLabel(" ");
		errorMessage.setForeground (Color.red);
		GridBagConstraints ErrorConstraints = new GridBagConstraints();
		ErrorConstraints.anchor = GridBagConstraints.CENTER;
		ErrorConstraints.gridy = 14;
		gb.setConstraints(errorMessage, ErrorConstraints);
		contentpane.add(errorMessage);
		
		// Button
		constraints.gridy = 15;
		gb.setConstraints(searchButton, constraints);
		contentpane.add(searchButton);
		
		// Jtable
		
		GridBagConstraints tableC = new GridBagConstraints();
		tableC.insets = new Insets(0, 10, 0, 0);
		tableC.fill = GridBagConstraints.NONE;
		tableC.gridy = 1;
		tableC.gridx = 3;
		tableC.ipadx = 500;
		tableC.gridwidth = 3;
		tableC.gridheight = 15;
		gb.setConstraints(scrollPane, tableC);
		contentpane.add(scrollPane);
		
		// End of UI
		// Action
		
		ActionListener searchListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				// Check BusinessID has same owner name
				// make statement
				
				try {
					checkID();				
				} catch (Exception ex) {
					errorMessage.setText("Denied: " + ex.getMessage());
					return;
				}
								
				
				// Parse dates into timestamps
				
				try {
					parse();
				} catch (Exception ex) {
					errorMessage.setText("Denied: " + ex.getMessage());
					return;
				}
				
				// Query
				
				try {
					PreparedStatement stmd = connection.prepareStatement("select * from reservation where reservation.businessid = ? AND reservation.dates BETWEEN ? and ?");
					stmd.setString(1, businessID);
					stmd.setTimestamp(2, beginTime);
					stmd.setTimestamp(3, endTime);
					
					ResultSet rs = stmd.executeQuery();
					ResultSetMetaData rsmd = stmd.getMetaData();
					
				
					
					
					TableFromResultSet.replaceTable(resultTable, rs, rsmd);
					
					
					
					
					
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
				}
				
				
				
				
				// Display
				
			}
		}; searchButton.addActionListener(searchListener);
		
		
		
		mainframe.pack();
		mainframe.setVisible(true);
		
		
	}
	
	// Check if Username is the owner of businessID
	
	private void checkID() throws Exception {
		
		// BusinessID must be integer
		try {
		businessID = businessIDfield.getText(); 
		} catch (Exception e) {
			
			System.out.println("BusinessID was not the correct format");
			System.out.println("Message: " + e.getMessage());
					
		}
		
		System.out.print("BusinessID parsed is: " + businessID);
		
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

	
	private void parse() throws Exception {
		
		try {
			int yearS = Integer.parseInt(yearStart.getText());
			int monthS = Integer.parseInt(monthStart.getText());
			int dayS = Integer.parseInt(dayStart.getText());
			int hourS = Integer.parseInt(hourStart.getText());
			int minS = Integer.parseInt(minStart.getText());
			
			beginTime = timeAndDate.makeTimestamp(yearS, monthS, dayS, hourS, minS);
			System.out.println(beginTime);
			
			int yearE = Integer.parseInt(yearEnd.getText());
			int monthE = Integer.parseInt(monthEnd.getText());
			int dayE = Integer.parseInt(dayEnd.getText());
			int hourE = Integer.parseInt(hourEnd.getText());
			int minE = Integer.parseInt(minEnd.getText());
			
			endTime = timeAndDate.makeTimestamp(yearE, monthE, dayE, hourE, minE);
			System.out.println(endTime);
	
		} catch (Exception ex) {
			throw new Exception("Incorrect Time format");
			
		}
	}
	
	
	
	
	
	
}

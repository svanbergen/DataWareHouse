package customerFunctionality;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import utility.TableFromResultSet;

// Class to search for businesses that serve specified sets of menu items
public class BusinessSearchByMenuItem {
	Connection con;
	JFrame searchFrame;
	JTable results;

	private JTextField nameField;
	private JTextField lowerPrice;
	private JTextField upperPrice;

	public BusinessSearchByMenuItem(Connection con){
		this.con = con;

		// Define/initialize parts of frame
		searchFrame = new JFrame("Business Search");
		// Labels
		JLabel titleLabel = new JLabel("Search for Businesses Serving Items");
		JLabel itemLabel = new JLabel("Enter a list of item names seperated by commas:");
		JLabel priceLabel = new JLabel("Specify (optional) price range in whole dollars: ");
		JLabel betweenLabel = new JLabel("Between ");
		JLabel andLabel = new JLabel(" and ");

		// Fields
		nameField = new JTextField(40);
		nameField.setMinimumSize(nameField.getPreferredSize());
		lowerPrice = new JTextField(10);
		lowerPrice.setMinimumSize(lowerPrice.getPreferredSize());
		upperPrice = new JTextField(10);
		upperPrice.setMinimumSize(upperPrice.getPreferredSize());

		// Button
		JButton searchButton = new JButton("Search");

		// Create and populate the panel using GridBag for layout
		JPanel contentPane = new JPanel();
		JScrollPane scrollContentPane = new JScrollPane(contentPane);
		searchFrame.setContentPane(scrollContentPane);
		GridBagLayout gb = new GridBagLayout();

		// Preset constraint sets
		GridBagConstraints titleC = new GridBagConstraints();
		titleC.insets = new Insets(0, 5, 5, 0);
		titleC.anchor = GridBagConstraints.WEST;

		GridBagConstraints labelC = new GridBagConstraints();
		labelC.insets = new Insets(5, 5, 5, 0);
		labelC.anchor = GridBagConstraints.WEST;

		GridBagConstraints buttonC = new GridBagConstraints();
		buttonC.insets = new Insets(0, 5, 10, 10);
		buttonC.weightx=0;
		buttonC.fill=GridBagConstraints.NONE;
		buttonC.anchor = GridBagConstraints.WEST;

		GridBagConstraints fieldC = new GridBagConstraints();
		fieldC.insets = new Insets(5, 5, 0, 0);
		fieldC.anchor = GridBagConstraints.WEST;
		//fieldC.weightx=1.;
		//fieldC.fill=GridBagConstraints.HORIZONTAL;

		GridBagConstraints tableC = new GridBagConstraints();
		tableC.insets = new Insets(0, 5, 0, 0);
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

		// Add item list label
		titleC.gridy = 2;
		titleC.gridx = 1;
		gb.setConstraints(itemLabel, titleC);
		contentPane.add(itemLabel);

		// Add item list field
		fieldC.gridy = 3;
		fieldC.gridx = 1;
		gb.setConstraints(nameField, fieldC);
		contentPane.add(nameField);

		// Add price bound label
		titleC.gridy = 4;
		titleC.gridx = 1;
		gb.setConstraints(priceLabel, titleC);
		contentPane.add(priceLabel);

		// Add price bound fields
		JPanel priceBoundPanel = new JPanel();
		GridBagLayout pbgb = new GridBagLayout();
		priceBoundPanel.setLayout(pbgb);
		// Add between
		titleC.gridy = 1;
		titleC.gridx = 1;
		pbgb.setConstraints(betweenLabel, titleC);
		priceBoundPanel.add(betweenLabel);
		// Add lowerPrice
		fieldC.gridy = 1;
		fieldC.gridx = 2;
		pbgb.setConstraints(lowerPrice, fieldC);
		priceBoundPanel.add(lowerPrice);
		// Add and
		titleC.gridy = 1;
		titleC.gridx = 3;
		pbgb.setConstraints(andLabel, titleC);
		priceBoundPanel.add(andLabel);
		// Add upperPrice
		fieldC.gridy = 1;
		fieldC.gridx = 4;
		pbgb.setConstraints(upperPrice, fieldC);
		priceBoundPanel.add(upperPrice);

		titleC.gridy = 5;
		titleC.gridx = 1;
		gb.setConstraints(priceBoundPanel, titleC);
		contentPane.add(priceBoundPanel);

		// Add submit button
		buttonC.gridy = 6;
		buttonC.gridx = 1;
		gb.setConstraints(searchButton, buttonC);
		contentPane.add(searchButton);

		// Error message
		JLabel errorMessage = new JLabel(" ");
		titleC.gridy = 7;
		titleC.gridx = 1;
		errorMessage.setForeground (Color.red);
		gb.setConstraints(errorMessage, titleC);
		contentPane.add(errorMessage);

		// Create table
		results = new JTable();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(results);

		gb.setConstraints(scrollPane, tableC);
		contentPane.add(scrollPane);

		// Button listener to make search
		ActionListener searchButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try{
					if(nameField.getText().equals("")){
						errorMessage.setText("Must enter at least one item");
						return;
					}
					String statement = "select * from business b natural join menuItem where";

					String names[] = nameField.getText().split(",");
					String denominator = "(select distinct name from menuItem where";
					for(int i = 0; i < names.length; i++){
						if(i!= 0){
							denominator = denominator.concat(" or");
							statement = statement.concat(" or");
						}
						denominator = denominator.concat(" name = '").concat(names[i].trim()).concat("'");
						statement = statement.concat(" name = '").concat(names[i].trim()).concat("'");
					}
					denominator = denominator.concat(")");
					statement = statement.concat(" and businessid in (select businessid from business b where not exists (");

					String numerator = "(select name from menuItem join business b1 on b1.businessid = menuItem.businessid where b1.businessid = b.businessid";
					String lp = lowerPrice.getText();
					int lb = -1;
					int ub = -1;
					if(!lp.equals("")){
						if(!lp.matches("[0-9]+")){
							errorMessage.setText("Invalid lower price");
							return;
						}
						else{
							numerator = numerator.concat(" and price > ").concat(lp);
							lb = Integer.parseInt(lp);
						}
					}

					String up = upperPrice.getText();
					if(!up.equals("")){
						if(!up.matches("[0-9]+")){
							errorMessage.setText("Invalid upper price");
							return;
						}
						else{
							numerator = numerator.concat(" and price < ").concat(up);
							ub = Integer.parseInt(up);
						}
					}
					numerator = numerator.concat(")");
					if(lb > ub){
						errorMessage.setText("Invalid price bounds");
						return;
					}

					statement = statement.concat(denominator).concat(" minus ").concat(numerator).concat("))");

					PreparedStatement stmt = con.prepareStatement(statement);
					System.out.println(statement);

					ResultSet rs = stmt.executeQuery();
					System.out.println(rs);
					ResultSetMetaData rsmd = rs.getMetaData();
					TableFromResultSet.replaceTable(results, rs, rsmd);

				}
				catch(SQLException ex){
					System.out.println("Message: " + ex.getMessage());
					errorMessage.setText("Unexpected database error");
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
	}
}

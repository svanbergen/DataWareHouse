package customerFunctionality;

import java.sql.*;

import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.*;

import utility.TableFromResultSet;

import java.awt.*;
import java.awt.event.*;

public class BusinessMenu extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel buttonPane;
	private JTextField businessIdTextField;
	JLabel statusLabel;

	private int businessId;

	private JTable menuTable;
	private JButton btnFindMenu;
	private JButton okButton;

	private JCheckBox chckbxMinPrice;
	private JCheckBox chckbxMaxPrice;
	private JCheckBox chckbxAvePrice;

	private JLabel lblMinPrice;
	private JLabel lblMaxPrice;
	private JLabel lblAveragePrice;

	Connection con;


	/**
	 * Create the dialog.
	 */
	public BusinessMenu(Connection con) {
		setTitle("Menu");
		this.con = con;

		try {
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.setVisible(true);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		setBounds(100, 100, 650, 400);
		{
			buttonPane = new JPanel();
			{
				// Add buttons
				okButton = new JButton("Cancel");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialog();
					}
				});

				btnFindMenu = new JButton("Find Menu");
				btnFindMenu.addActionListener(new ActionListener() {

					// Listener for find menu button
					public void actionPerformed(ActionEvent e) {

						//Find the menu

						lblMinPrice.setText("");
						lblMaxPrice.setText("");
						lblAveragePrice.setText("");

						try{
							businessId = Integer.parseInt(businessIdTextField.getText());
						}catch(Exception ex){
							statusLabel.setText("invalid ID");
							return;
						}

						String getMenuQuery = "select menuItemID, Name, Price, ItemType From MenuItem WHERE BusinessID = ?";
						try{
							PreparedStatement stmt = con.prepareStatement(getMenuQuery);
							stmt.setString(1, ""+ businessId);
							ResultSet rs = stmt.executeQuery();
							ResultSetMetaData rsmd = rs.getMetaData();
							TableFromResultSet.replaceTable(menuTable, rs, rsmd);
							if(menuTable.getRowCount() == 0)
							{
								statusLabel.setText("The specified business does not exist");
								return;	
							}
							else
							{
								//System.out.println("The business entered exists in the database");
							}     
						}catch(SQLException ex){
							System.out.println(ex.getMessage());
						}
						if (chckbxMinPrice.isSelected()){
							setTheAggregateValue("Min" , lblMinPrice);
						}
						if(chckbxMaxPrice.isSelected()){
							setTheAggregateValue("Max" , lblMaxPrice);
						}
						if(chckbxAvePrice.isSelected()){
							setTheAggregateValue("Avg" , lblAveragePrice);
						}
					}
				});
				okButton.setActionCommand("");
				getRootPane().setDefaultButton(okButton);
			}
		}

		// Labels and fields
		JLabel lblBusinessId = new JLabel("Business ID:");

		businessIdTextField = new JTextField();
		businessIdTextField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();

		menuTable = new JTable();
		scrollPane.setViewportView(menuTable);

		statusLabel = new JLabel("");
		statusLabel.setForeground(Color.RED);
		JLabel lblToViewThe = new JLabel("To view the menu, please enter a valid business ID");

		lblMinPrice = new JLabel("");

		lblMaxPrice = new JLabel("");

		lblAveragePrice = new JLabel("");

		chckbxMinPrice = new JCheckBox("Min Price");

		chckbxMaxPrice = new JCheckBox("Max Price");

		chckbxAvePrice = new JCheckBox("Ave Price");
		// Nobody knows what this auto-generated code does
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(32)
										.addComponent(lblToViewThe))
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(27)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(lblBusinessId)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(businessIdTextField, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
												.addComponent(chckbxMinPrice)
												.addComponent(chckbxMaxPrice)
												.addComponent(chckbxAvePrice)
												.addComponent(lblMinPrice)
												.addComponent(lblMaxPrice)
												.addComponent(lblAveragePrice))
										.addGap(30)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(statusLabel)
												.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
														.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 432, GroupLayout.PREFERRED_SIZE)))))
						.addContainerGap(40, Short.MAX_VALUE))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(7)
						.addComponent(lblToViewThe)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(18)
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(statusLabel)
										.addGap(18)
										.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(52)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblBusinessId)
												.addComponent(businessIdTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(chckbxMinPrice)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(chckbxMaxPrice)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(chckbxAvePrice)
										.addGap(18)
										.addComponent(lblMinPrice)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(lblMaxPrice)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(lblAveragePrice)))
						.addGap(23))
				);
		GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
		gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(245)
						.addComponent(btnFindMenu)
						.addGap(5)
						.addComponent(okButton))
				);
		gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnFindMenu)
								.addComponent(okButton)))
				);
		buttonPane.setLayout(gl_buttonPane);
		getContentPane().setLayout(groupLayout);
	}
	
	public void closeDialog(){
		this.dispose();
	}

	// Method to get aggregate value
	public void setTheAggregateValue(String agg, JLabel label){
		String getAggPrice = "select " + agg  + "(Price) From MenuItem WHERE BusinessID =" + businessId;
		//select Min(Price) from MenuItem where BusinessID = 1;
		try{
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(getAggPrice);

			if(rs.next())
			{
				label.setText(agg+ " Price: " + rs.getDouble(1));
			}
			else
			{
				//System.out.println("the business entered exists in the database");
			}     
		}catch(SQLException ex){
			System.out.println(ex.getMessage());
		}
	}
}

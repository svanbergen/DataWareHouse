package ownerFunctionality;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.PopUp;
import utility.TableFromResultSet;

import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class BusinessStatistics extends JDialog {

	private final JPanel mainPane = new JPanel();
	private JTextField businessIdTextField;

	private String username;
	private Connection con;
	
	private int businessId;
		
	private JLabel statusLabel;
	
	
	private JTable customerTable;
	
	

	
	public BusinessStatistics(Connection con, String username) {
		setTitle("Business Statistics");
		this.con = con;
		this.username = username;
		try {
			
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		customerTable = new JTable();
		setBounds(100, 100, 900, 500);
		getContentPane().setLayout(new BorderLayout());
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(mainPane, BorderLayout.CENTER);
		
		JLabel lblBusinessId = new JLabel("Business ID: ");
		lblBusinessId.setBounds(11, 16, 81, 16);
		
		businessIdTextField = new JTextField();
		businessIdTextField.setBounds(98, 11, 46, 26);
		businessIdTextField.setColumns(10);
		
		JLabel MaxOrderedLabel = new JLabel("Most Popular Menu Item:");
		MaxOrderedLabel.setBounds(11, 55, 156, 16);
		
		JLabel lblLeastPopularMenu = new JLabel("Least Popular Menu Item:");
		lblLeastPopularMenu.setBounds(11, 77, 158, 16);
		
		JScrollPane menuItemScrollPane = new JScrollPane();
		menuItemScrollPane.setBounds(11, 149, 373, 256);
		
		JLabel lblAverageNumberOf = new JLabel("Average Number Of Orders By Customers:");
		lblAverageNumberOf.setBounds(437, 55, 263, 16);
		
		JScrollPane customersScrollPane = new JScrollPane();
		customersScrollPane.setBounds(437, 149, 452, 256);
		
		JLabel lblMaximumNumberOf = new JLabel("Maximum Number Of Orders:");
		lblMaximumNumberOf.setBounds(437, 77, 185, 16);
		
		JLabel lblLeastNumberOf = new JLabel("Least Number Of Orders:");
		lblLeastNumberOf.setBounds(437, 99, 156, 16);
		
		JLabel maxMenuItemResultLabel = new JLabel("m");
		maxMenuItemResultLabel.setBounds(179, 55, 12, 16);
		
		JLabel minMenuItemResultLabel = new JLabel("min");
		minMenuItemResultLabel.setBounds(175, 77, 24, 16);
		
		JLabel lblTotalNumberOf = new JLabel("Total Number of Orders:");
		lblTotalNumberOf.setBounds(437, 121, 153, 16);
		
		JLabel AveNumOrdersResultLabel = new JLabel("ave");
		AveNumOrdersResultLabel.setBounds(751, 55, 21, 16);
		
		JLabel leastNumOfOrdersResult = new JLabel("x");
		leastNumOfOrdersResult.setBounds(764, 99, 8, 16);
		
		JLabel totalNumOrdersResultLabel = new JLabel("t");
		totalNumOrdersResultLabel.setBounds(764, 121, 5, 16);
		mainPane.setLayout(null);
		mainPane.add(MaxOrderedLabel);
		mainPane.add(maxMenuItemResultLabel);
		mainPane.add(lblBusinessId);
		mainPane.add(businessIdTextField);
		mainPane.add(lblLeastPopularMenu);
		mainPane.add(minMenuItemResultLabel);
		mainPane.add(menuItemScrollPane);
		mainPane.add(lblAverageNumberOf);
		mainPane.add(lblTotalNumberOf);
		mainPane.add(lblMaximumNumberOf);
		mainPane.add(customersScrollPane);
		mainPane.add(AveNumOrdersResultLabel);
		mainPane.add(lblLeastNumberOf);
		mainPane.add(totalNumOrdersResultLabel);
		mainPane.add(leastNumOfOrdersResult);
		
		JLabel MaxNumOrdersResult = new JLabel("max");
		MaxNumOrdersResult.setBounds(751, 77, 61, 16);
		mainPane.add(MaxNumOrdersResult);
		
		statusLabel = new JLabel("New label");
		statusLabel.setForeground(Color.RED);
		statusLabel.setBounds(11, 417, 878, 16);
		mainPane.add(statusLabel);
		
		
		customersScrollPane.setViewportView(customerTable);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						
						System.out.println("ok button pressed");
						
						
						try{
							businessId = Integer.parseInt(businessIdTextField.getText());
						}catch (Exception ex){
							ex.printStackTrace();
							statusLabel.setText("Invalid Input");
							return;
						}
						
						
						String ownerUserNameFromDB;
						String checkThatOwnerOwnsBusiness = "select OwnerUserName from business where BusinessID=?";
						try{
							PreparedStatement stmt = con.prepareStatement(checkThatOwnerOwnsBusiness);
							
							stmt.setString(1, "" + businessId);;
							ResultSet rs = stmt.executeQuery();
							
							
							if(rs.next()){
								ownerUserNameFromDB = rs.getString(1);
							}else{
								statusLabel.setText("The Business ID does not Exist in the database.");
								return;
							}		
					}catch (SQLException ex) {
						System.out.println(ex.getMessage());
						statusLabel.setText("Database error");
						return;
					}
						
						if(!ownerUserNameFromDB.equals(username)){
							statusLabel.setText("You can only view the statistics for one of your own businesses");
							return;
						}
						
						
						
						String totalOrdersByEachCustomer = "select customerUserName, COUNT(*) AS numOrders from orders where businessID=? GROUP BY customerUserName ";
						
						try{
							PreparedStatement stmt = con.prepareStatement(totalOrdersByEachCustomer);
							
							stmt.setString(1, "" + businessId);;
							ResultSet rs = stmt.executeQuery();
							
							ResultSetMetaData rsmd = rs.getMetaData();
							TableFromResultSet.replaceTable(customerTable, rs, rsmd);
							
						}catch (SQLException ex) {
							System.out.println(ex.getMessage());
							statusLabel.setText("Database error");
							return;
						}
						
						
						
						
						
						
						
						
						
					}
					}
				);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialog();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	
	
	private void parseInput() throws Exception{
		businessId = Integer.parseInt(businessIdTextField.getText());
	}
	
	
	private void closeDialog(){
		this.dispose();
	}
	
	
	
}

package customerFunctionality;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.*;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.PopUp;
import utility.TableFromResultSet;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class OrderItemSelectionWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private String orderId;
	private Connection con;
	private JTextField addMenuItemTextField;
	
	private JTable itemsTable;
	private JLabel totalPriceLabel;
	
	JLabel statusLabel;
	


	/**
	 * Create the dialog.
	 */
	public OrderItemSelectionWindow(Connection con, String orderId) {
		setTitle("Add Menu Items to your order");
		
		try {
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.con = con;
		this.orderId = orderId;
		
		
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel orderNumberLabel = new JLabel("order Number: " + orderId);
			orderNumberLabel.setBounds(6, 16, 196, 16);
			contentPanel.add(orderNumberLabel);
		}
		
		
		itemsTable = new JTable();
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(6, 82, 322, 126);
			contentPanel.add(scrollPane);
			scrollPane.setViewportView(itemsTable);
		}
		{
			totalPriceLabel = new JLabel("Total Price:");
			totalPriceLabel.setBounds(247, 16, 102, 16);
			contentPanel.add(totalPriceLabel);
		}
		{
			JLabel addItemLabel = new JLabel("add Item:");
			addItemLabel.setBounds(6, 54, 61, 16);
			contentPanel.add(addItemLabel);
		}
		{
			addMenuItemTextField = new JTextField();
			addMenuItemTextField.setBounds(81, 49, 61, 26);
			contentPanel.add(addMenuItemTextField);
			addMenuItemTextField.setColumns(10);
		}
		{
			JButton addToOrder = new JButton("Add To Order");
			addToOrder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					int menuItemID;
					try{
						menuItemID = Integer.parseInt(addMenuItemTextField.getText());
					}
					catch(Exception ex){
						ex.printStackTrace();
						statusLabel.setText("Invalid Input");
						return;
					}
					
					
					System.out.println("order ID: " + orderId);
					System.out.println("menuItemID: " + menuItemID);
					
					String insertIntoOrderString = "INSERT INTO Includes values(" + orderId + "," + menuItemID + ")";
					
					System.out.println(insertIntoOrderString);
					try{
						Statement stmt = con.createStatement();
						int rows = stmt.executeUpdate(insertIntoOrderString);
						
						if(rows >0 ){
							updateTable();
						}else{
							System.out.println("no rows affected after the update");
						}
						
					}catch(SQLException ex){
						ex.printStackTrace();
						statusLabel.setText("Something went wrong. Make sure the ID is valid.");
						return;
					}
					
					
					
					
					
				}
			});
			addToOrder.setBounds(152, 49, 128, 29);
			contentPanel.add(addToOrder);
			addToOrder.setActionCommand("OK");
			getRootPane().setDefaultButton(addToOrder);
		}
		{
			 statusLabel = new JLabel("");
			statusLabel.setForeground(Color.RED);
			statusLabel.setBounds(6, 220, 414, 16);
			contentPanel.add(statusLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton submitOrderButton = new JButton("Submit Order");
				submitOrderButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						Timestamp currentTimestamp = new Timestamp((Calendar.getInstance()).getTime().getTime());
						
						
						String timeStampOrderQuery = "Update Order set timeMade=? where orderID = ?";
						try{
							PreparedStatement ps = con.prepareStatement(timeStampOrderQuery);
							
							ps.setTimestamp(1, currentTimestamp);
							ps.setString(2, orderId);
							
							PopUp pp = new PopUp("order submitted succesfully");
							
							closeDialog();
							
						}catch(SQLException ex){
							ex.printStackTrace();
						}
						
						
						
					}
				});
				buttonPane.add(submitOrderButton);
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
	
	
	public void updateTable(){
		
		System.out.println("in updateTable()");
		
		String getItemsOnMenuQuery = 
				"SELECT M.MenuItemID, M.name FROM MenuItem M,Includes I WHERE I.menuItemID = M.menuItemID AND I.orderID = "+ orderId;
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(getItemsOnMenuQuery);
			ResultSetMetaData rsmd = rs.getMetaData();
			
			TableFromResultSet.replaceTable(itemsTable, rs, rsmd);
			
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
		
		
		
		String getTotalPriceQuery = "select price FROM orders where orderid=" + orderId;
				
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(getTotalPriceQuery);
			
			if(rs.next()){
				totalPriceLabel.setText("Total Price: "+ rs.getFloat(1));
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
		
				
	}
	
	
	public void closeDialog(){
		this.dispose();
	}
	
	
}

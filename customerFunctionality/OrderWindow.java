package customerFunctionality;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class OrderWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	
	private Connection con;
	private String username;
	private JTextField businessIDTextField;

	private int  businessId;;
	
	public OrderWindow(Connection con, String username) {
		setTitle("Create a New Order");
		
		this.username = username;
		this.con = con;
		
		try {
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblBusinessId = new JLabel("Business ID: ");
			lblBusinessId.setBounds(6, 32, 81, 16);
			contentPanel.add(lblBusinessId);
		}
		
		businessIDTextField = new JTextField();
		businessIDTextField.setBounds(99, 27, 73, 26);
		contentPanel.add(businessIDTextField);
		businessIDTextField.setColumns(10);
		
		JLabel statusLabel = new JLabel("");
		statusLabel.setForeground(Color.RED);
		statusLabel.setBounds(6, 197, 421, 16);
		contentPanel.add(statusLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						
						
						try{
							businessId = Integer.parseInt(businessIDTextField.getText());
						}catch(Exception ex){
							ex.printStackTrace();
							statusLabel.setText("Invalid Input");
							return;
						}
						
						
						
						String makeSureBusinessExistsQuery = "select * from Business where BusinessID = ?";
						try{
							PreparedStatement stmt = con.prepareStatement(makeSureBusinessExistsQuery);
							stmt.setString(1, ""+ businessId);
							
							ResultSet rs = stmt.executeQuery();
							
							if(rs.next())
							{
								createOrder();
							}
							else
							{
								statusLabel.setText("the specified business does not exist");
								return;
							}  
						}catch(SQLException ex){
							ex.printStackTrace();
						}
						
					}
					
				});
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
	
	
	
	public void createOrder(){
		
		String createOrderQuery = "insert into orders(businessID,customerUserName, Price) values(?,?,?)";
		
		
		try{
			
		System.out.println("about to prepare the statement that returns the generated key");
		System.out.println("businessID:" + businessId);
		System.out.println("username " + username );
			
		PreparedStatement ps = con.prepareStatement(createOrderQuery , new String[] { "OrderID" });
		ps.setInt(1, businessId);
		ps.setString(2, username);
		ps.setFloat(3, 0);
		
		
		System.out.println("stmt created");
		
		
	
		
		
		if(ps.executeUpdate()>0){
			ResultSet generatedKeys = ps.getGeneratedKeys(); 
			if (null != generatedKeys && generatedKeys.next()) 
			{ 
				Long orderId = generatedKeys.getLong(1);
				
				System.out.println("returned order id is: " + orderId);
				
				OrderItemSelectionWindow oisw = new OrderItemSelectionWindow(con, ""+orderId);
				}
		}
		
		
		
		
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
	}
	
	public void closeDialog(){
		this.dispose();
	}
	
	
}

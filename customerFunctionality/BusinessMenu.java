package customerFunctionality;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.TableFromResultSet;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class BusinessMenu extends JDialog {
	private JPanel buttonPane;
	private JTextField businessIdTextField;
	JLabel statusLabel;
	
	private int businessId;

	private JTable menuTable;


	/**
	 * Create the dialog.
	 */
	public BusinessMenu(Connection con) {
		setTitle("Menu");
		
		try {
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		setBounds(100, 100, 500, 400);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton okButton = new JButton("Cancel");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialog();
					}
				});
				
				JButton btnFindMenu = new JButton("Find Menu");
				btnFindMenu.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						
						//find the menu
						
						try{
							businessId = Integer.parseInt(businessIdTextField.getText());
						}catch(Exception ex){
							statusLabel.setText("invalid ID");
							return;
						}
						
						String getMenuQuery = "select menuItemID, Name, Price, Type From MenuItem WHERE BusinessID = ?";
						try{
							PreparedStatement stmt = con.prepareStatement(getMenuQuery);
							
							stmt.setString(1, ""+ businessId);
							
							
							ResultSet rs = stmt.executeQuery();
							ResultSetMetaData rsmd = rs.getMetaData();
							
							
							
							
							TableFromResultSet.replaceTable(menuTable, rs, rsmd);
								
								
							if(menuTable.getRowCount() == 0)
							{
								statusLabel.setText("the specified business does not exist");
								return;	
							}
							else
							{
								System.out.println("the business entered exists in the database");
							}     
						}catch(SQLException ex){
							System.out.println(ex.getMessage());
						}
						
						
						
						
						
						
						
						
						
						
					}
				});
				buttonPane.add(btnFindMenu);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		JLabel lblBusinessId = new JLabel("Business ID:");
		
		businessIdTextField = new JTextField();
		businessIdTextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		
		menuTable = new JTable();
		scrollPane.setViewportView(menuTable);
		
		statusLabel = new JLabel("");
		statusLabel.setForeground(Color.RED);
		JLabel lblToViewThe = new JLabel("To view the menu, please enter a valid business ID");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(32)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(statusLabel)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblBusinessId)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
										.addComponent(businessIdTextField, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))
								.addComponent(lblToViewThe)))
						.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(50, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(7)
					.addComponent(lblToViewThe)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBusinessId)
						.addComponent(businessIdTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(statusLabel)
					.addGap(31)
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}
	
	
	
//	public void parseBusinessID(){
//		
//		businessID = Integer.parseInt(businessIdTextField.getText());
//		
//	}
	
	
	
	
	
	public void closeDialog(){
		this.dispose();
	}
}

package ownerFunctionality;

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
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WriteReply {
	
	String username;
	Integer[] ratings = {1,2,3,4,5};
	Connection con;
	JFrame replyFrame;
	JTable reviews;
	
	public WriteReply(Connection con, String username){
		this.con = con;
		this.username = username;
		
		replyFrame = new JFrame("Write Reply to Review");
		JLabel replyPage = new JLabel("Write Reply to Review");
		JLabel idFieldLabel = new JLabel("Enter ID of review to reply to:");
		JTextField bIdField = new JTextField(10);
		JLabel writeComment = new JLabel("Write comment");
		JTextArea commentArea = new JTextArea(4, 50);
		JButton submitButton = new JButton("Submit");
		
		JPanel contentPane = new JPanel();
		replyFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();
		
		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		GridBagConstraints buttonC = new GridBagConstraints();
		buttonC.insets = new Insets(0, 0, 0, 10);
		buttonC.ipadx = 50;
		buttonC.anchor = GridBagConstraints.WEST;
		buttonC.gridx = 1;
		buttonC.gridy = 1;
		buttonC.gridwidth = 1;
		buttonC.fill = GridBagConstraints.HORIZONTAL;
		
		GridBagConstraints areaC = new GridBagConstraints();
		areaC.insets = new Insets(0, 0, 0, 10);
		areaC.ipadx = 50;
		areaC.anchor = GridBagConstraints.WEST;
		areaC.gridx = 1;
		areaC.gridy = 5;
		areaC.gridwidth = 2;
		areaC.fill = GridBagConstraints.HORIZONTAL;
		
		gb.setConstraints(replyPage, buttonC);
		contentPane.add(replyPage);
		
		buttonC.gridy = 2;
		gb.setConstraints(idFieldLabel, buttonC);
		contentPane.add(idFieldLabel);
		
		buttonC.gridy = 3;
		gb.setConstraints(bIdField, buttonC);
		contentPane.add(bIdField);
		
		buttonC.gridy = 4;
		gb.setConstraints(writeComment, buttonC);
		contentPane.add(writeComment);
		
		gb.setConstraints(commentArea, areaC);
		contentPane.add(commentArea);
		
		buttonC.gridy = 6;
		gb.setConstraints(submitButton, buttonC);
		contentPane.add(submitButton);
		
		// Error message
		JLabel errorMessage = new JLabel(" ");
		buttonC.gridy = 7;
		errorMessage.setForeground (Color.red);
		gb.setConstraints(errorMessage, buttonC);
		contentPane.add(errorMessage);
		
		ActionListener submitButtonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try{
					String id = bIdField.getText();
					PreparedStatement check = con.prepareStatement("select ownerUsername from business, review where review.ReviewID = ? and business.BusinessID = review.BusinessID");
					check.setInt(1, Integer.parseInt(id));
					ResultSet checkRS = check.executeQuery();
					checkRS.next();
					if(!checkRS.getString("ownerUsername").equals(username)){
						errorMessage.setText("Review does not match your business");
						return;
					}
					
					check = con.prepareStatement("select * from reply where reply.reviewID = ?");
					check.setInt(1, Integer.parseInt(id));
					checkRS = check.executeQuery();
					if(checkRS.next()){
						errorMessage.setText("Reply already written");
						return;
					}
					String comment = commentArea.getText();
					if(!id.matches("[0-9]+")){
						errorMessage.setText("Invalid id");
						return;
					}
					
					PreparedStatement stmt = con.prepareStatement("insert into reply values(?, ?, ?, ?)");
					
					if(comment.equals("")){
						errorMessage.setText("Must enter comment");
						return;
					}
					else{
						stmt.setString(4, comment);
					}
					
					java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
					stmt.setDate(3, sqlDate);
					stmt.setInt(1, Integer.parseInt(id));
					stmt.setString(2, username);
					stmt.executeQuery();
					replyFrame.dispose();
					}
					catch(SQLException ex){
						System.out.println("Message: " + ex.getMessage());
						errorMessage.setText("Unexpected database error");
					}
			}
		};
		submitButton.addActionListener(submitButtonListener);
		
		// Resize window
		replyFrame.pack();

				// Centre window
				Dimension d = replyFrame.getToolkit().getScreenSize();
				Rectangle r = replyFrame.getBounds();
				replyFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

				// Set window visible
				replyFrame.setVisible(true);


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
}

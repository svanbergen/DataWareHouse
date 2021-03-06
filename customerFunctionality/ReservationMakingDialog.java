package customerFunctionality;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import utility.*;
import java.awt.Color;
import javax.swing.SwingConstants;

// Class to make or cancel a reservation
public class ReservationMakingDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	String username;
	private JTextField businessIdTextField;
	private JTextField HourTextField;
	private JLabel statusLabel;
	private JTextField monthTextField;
	private JTextField dayTextField;
	private JTextField yearTextField;
	private JTextField minuteTextField;

	int businessId;
	Timestamp ts;
	
	private JButton makeReservationButton;


	/**
	 * Create the dialog.
	 */
	public ReservationMakingDialog(String username, Connection con) {
		setTitle("Booking a Table");
		this.username = username;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel businessIdLabel = new JLabel("Business ID: ");
			GridBagConstraints gbc_businessIdLabel = new GridBagConstraints();
			gbc_businessIdLabel.anchor = GridBagConstraints.WEST;
			gbc_businessIdLabel.insets = new Insets(0, 0, 5, 5);
			gbc_businessIdLabel.gridx = 2;
			gbc_businessIdLabel.gridy = 2;
			contentPanel.add(businessIdLabel, gbc_businessIdLabel);
		}
		{
			businessIdTextField = new JTextField();
			GridBagConstraints gbc_businessIdTextField = new GridBagConstraints();
			gbc_businessIdTextField.insets = new Insets(0, 0, 5, 5);
			gbc_businessIdTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_businessIdTextField.gridx = 4;
			gbc_businessIdTextField.gridy = 2;
			contentPanel.add(businessIdTextField, gbc_businessIdTextField);
			businessIdTextField.setColumns(10);
		}
		{
			JLabel yearLabel = new JLabel("Year: ");
			GridBagConstraints gbc_yearLabel = new GridBagConstraints();
			gbc_yearLabel.anchor = GridBagConstraints.WEST;
			gbc_yearLabel.insets = new Insets(0, 0, 5, 5);
			gbc_yearLabel.gridx = 2;
			gbc_yearLabel.gridy = 3;
			contentPanel.add(yearLabel, gbc_yearLabel);
		}
		{
			statusLabel = new JLabel("");
			statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
			statusLabel.setForeground(Color.RED);


			{
				yearTextField = new JTextField();
				GridBagConstraints gbc_yearTextField = new GridBagConstraints();
				gbc_yearTextField.insets = new Insets(0, 0, 5, 5);
				gbc_yearTextField.fill = GridBagConstraints.HORIZONTAL;
				gbc_yearTextField.gridx = 4;
				gbc_yearTextField.gridy = 3;
				contentPanel.add(yearTextField, gbc_yearTextField);
				yearTextField.setColumns(10);
			}
			{
				JLabel lblMonth = new JLabel("Month: ");
				GridBagConstraints gbc_lblMonth = new GridBagConstraints();
				gbc_lblMonth.anchor = GridBagConstraints.WEST;
				gbc_lblMonth.insets = new Insets(0, 0, 5, 5);
				gbc_lblMonth.gridx = 2;
				gbc_lblMonth.gridy = 4;
				contentPanel.add(lblMonth, gbc_lblMonth);
			}
			{
				monthTextField = new JTextField();
				GridBagConstraints gbc_monthTextField = new GridBagConstraints();
				gbc_monthTextField.insets = new Insets(0, 0, 5, 5);
				gbc_monthTextField.fill = GridBagConstraints.HORIZONTAL;
				gbc_monthTextField.gridx = 4;
				gbc_monthTextField.gridy = 4;
				contentPanel.add(monthTextField, gbc_monthTextField);
				monthTextField.setColumns(10);
			}
			{
				JLabel lblDay = new JLabel("Day: ");
				GridBagConstraints gbc_lblDay = new GridBagConstraints();
				gbc_lblDay.anchor = GridBagConstraints.WEST;
				gbc_lblDay.insets = new Insets(0, 0, 5, 5);
				gbc_lblDay.gridx = 2;
				gbc_lblDay.gridy = 5;
				contentPanel.add(lblDay, gbc_lblDay);
			}
			{
				dayTextField = new JTextField();
				GridBagConstraints gbc_dayTextField = new GridBagConstraints();
				gbc_dayTextField.insets = new Insets(0, 0, 5, 5);
				gbc_dayTextField.fill = GridBagConstraints.HORIZONTAL;
				gbc_dayTextField.gridx = 4;
				gbc_dayTextField.gridy = 5;
				contentPanel.add(dayTextField, gbc_dayTextField);
				dayTextField.setColumns(10);
			}
			{
				JLabel timeLabel = new JLabel("Hour: ");
				GridBagConstraints gbc_timeLabel = new GridBagConstraints();
				gbc_timeLabel.anchor = GridBagConstraints.WEST;
				gbc_timeLabel.insets = new Insets(0, 0, 5, 5);
				gbc_timeLabel.gridx = 2;
				gbc_timeLabel.gridy = 6;
				contentPanel.add(timeLabel, gbc_timeLabel);
			}
			{
				HourTextField = new JTextField();
				GridBagConstraints gbc_HourTextField = new GridBagConstraints();
				gbc_HourTextField.insets = new Insets(0, 0, 5, 5);
				gbc_HourTextField.fill = GridBagConstraints.HORIZONTAL;
				gbc_HourTextField.gridx = 4;
				gbc_HourTextField.gridy = 6;
				contentPanel.add(HourTextField, gbc_HourTextField);
				HourTextField.setColumns(10);
			}
			{
				JLabel lblMinute = new JLabel("Minute:");
				GridBagConstraints gbc_lblMinute = new GridBagConstraints();
				gbc_lblMinute.anchor = GridBagConstraints.WEST;
				gbc_lblMinute.insets = new Insets(0, 0, 5, 5);
				gbc_lblMinute.gridx = 2;
				gbc_lblMinute.gridy = 7;
				contentPanel.add(lblMinute, gbc_lblMinute);
			}
			{
				minuteTextField = new JTextField();
				GridBagConstraints gbc_minuteTextField = new GridBagConstraints();
				gbc_minuteTextField.insets = new Insets(0, 0, 5, 5);
				gbc_minuteTextField.fill = GridBagConstraints.HORIZONTAL;
				gbc_minuteTextField.gridx = 4;
				gbc_minuteTextField.gridy = 7;
				contentPanel.add(minuteTextField, gbc_minuteTextField);
				minuteTextField.setColumns(10);
			}
			GridBagConstraints gbc_statusLabel = new GridBagConstraints();
			gbc_statusLabel.insets = new Insets(0, 0, 0, 5);
			gbc_statusLabel.gridx = 2;
			gbc_statusLabel.gridy = 10;
			gbc_statusLabel.gridwidth = 7;
			contentPanel.add(statusLabel, gbc_statusLabel);
		}
		{

			// Button to make a reservation
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCancelReservation = new JButton("Cancel Reservation");
				btnCancelReservation.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("cancel btn pressed");
						try{
							parseInput();
						}catch(Exception ex){
							return;
						}


						String makeSureReservationExists = "select businessID from Reservation where dates =? AND customerUsername = ? AND businessID=?";
						try{
							PreparedStatement stmt = con.prepareStatement(makeSureReservationExists);
							stmt.setTimestamp(1, ts);
							stmt.setString(2, username);
							stmt.setString(3, ""+ businessId);

							ResultSet rs = stmt.executeQuery();

							if(rs.next())
							{
								System.out.println("the reservation entered exists in the database");
							}
							else
							{
								statusLabel.setText("the specified reservation does not exist");
								return;
							}     
						}catch(SQLException ex){
							System.out.println(ex.getMessage());
						}

						String removeReservationQuery = "delete from Reservation where dates =? AND customerUsername = ? AND businessID=?";
						try{
							PreparedStatement stmt = con.prepareStatement(removeReservationQuery);

							stmt.setTimestamp(1, ts);
							stmt.setString(2, username);
							stmt.setString(3, ""+ businessId);
							stmt.executeUpdate();


							new PopUp("Reservation Cancelled successfully");
							closeDialog();
						}catch (SQLException ex) {

							System.out.println(ex.getMessage());

						}
					}
				});
				buttonPane.add(btnCancelReservation);
			}
			{
				makeReservationButton = new JButton("make Reservation");
				makeReservationButton.setActionCommand("OK");
				buttonPane.add(makeReservationButton);
				getRootPane().setDefaultButton(makeReservationButton);
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

		// Button to make reservation
		ActionListener makeReservationButtonListener = new ActionListener(){

			public void actionPerformed(ActionEvent e){
				System.out.println("makee reservation button pressed");

				try{
					parseInput();
				}catch(Exception ex){
					return;
				}

				String businessFlagQuery = "select reservationFlag from Business where BusinessID = ?";
				try{
					PreparedStatement stmt = con.prepareStatement(businessFlagQuery);
					stmt.setString(1, ""+ businessId);

					ResultSet rs = stmt.executeQuery();

					char flag;
					if(rs.next())
					{
						flag = rs.getString("reservationFlag").charAt(0);
						if(flag == 'N'){
							statusLabel.setText("Unfortunately you cannot make a reservation at this business :( ");
							return;
						}
					}
					else
					{
						statusLabel.setText("The specified business does not exist");
						return;
					}      
				}
				catch (SQLException ex)
				{
					System.out.println("Message: " + ex.getMessage());
					statusLabel.setText("Business ID invlid");
				}
				String reservationInsertion = "INSERT INTO Reservation values(?,?,?)";

				try{
					PreparedStatement stmt = con.prepareStatement(reservationInsertion);
					stmt.setTimestamp(1, ts);
					stmt.setString(2, username);
					stmt.setString(3, ""+businessId );
					stmt.executeUpdate();
					new PopUp("Reservation made successfully. Hope you have a great Experience!");
					closeDialog();

				}
				catch (SQLException ex)
				{
					System.out.println("Message: " + ex.getMessage());
					statusLabel.setText("Invalid input");
				}
			};
		};
		makeReservationButton.addActionListener(makeReservationButtonListener);
	}

	// Method for parsing input
	public void parseInput() throws Exception{

		try{
			int year = Integer.parseInt(yearTextField.getText());
			int month = Integer.parseInt(monthTextField.getText());
			int day = Integer.parseInt(dayTextField.getText());
			int hour = Integer.parseInt(HourTextField.getText());
			int minute = Integer.parseInt(minuteTextField.getText());
			ts = timeAndDate.makeTimestamp(year,month,day,hour,minute);
			//System.out.println(ts.toString());

			businessId = Integer.parseInt(businessIdTextField.getText());
		}
		// Check data is valid
		catch(InvalidDateException ex){
			ex.printStackTrace();
			statusLabel.setText("The time or date entered is not valid.");
			throw new Exception();
		}
		catch(Exception ex){
			statusLabel.setVisible(true);
			statusLabel.setText("Make sure the date and business id are valid");
			throw new Exception();
		}

		Calendar currentTime = Calendar.getInstance();
		Calendar inputTime = Calendar.getInstance();
		inputTime.setTimeInMillis( ts.getTime() );

		//System.out.println("current time: " + currentTime);
		//System.out.println("input time: " + inputTime);

		// Check input time is in the future
		if(inputTime.before(currentTime)){
			statusLabel.setText("You Cannot Book a Reservation in the Past. Move On Man!");
			throw new Exception();
		}

	}
	public void closeDialog(){
		this.dispose();
	}

}

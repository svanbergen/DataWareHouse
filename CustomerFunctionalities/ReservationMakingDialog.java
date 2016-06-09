package CustomerFunctionalities;

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

public class ReservationMakingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	String username;
	private JTextField businessIdTextField;
	private JTextField dateTextField;
	private JTextField timeTextField;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			ReservationMakingDialog dialog = new ReservationMakingDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public ReservationMakingDialog(String username) {
		setTitle("Booking a Table");
		this.username = username;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel businessIdLabel = new JLabel("Business ID: ");
			GridBagConstraints gbc_businessIdLabel = new GridBagConstraints();
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
			JLabel lblDate = new JLabel("Date: ");
			GridBagConstraints gbc_lblDate = new GridBagConstraints();
			gbc_lblDate.insets = new Insets(0, 0, 5, 5);
			gbc_lblDate.gridx = 2;
			gbc_lblDate.gridy = 3;
			contentPanel.add(lblDate, gbc_lblDate);
		}
		{
			dateTextField = new JTextField();
			GridBagConstraints gbc_dateTextField = new GridBagConstraints();
			gbc_dateTextField.insets = new Insets(0, 0, 5, 5);
			gbc_dateTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_dateTextField.gridx = 4;
			gbc_dateTextField.gridy = 3;
			contentPanel.add(dateTextField, gbc_dateTextField);
			dateTextField.setColumns(10);
		}
		{
			JLabel timeLabel = new JLabel("Time:");
			GridBagConstraints gbc_timeLabel = new GridBagConstraints();
			gbc_timeLabel.insets = new Insets(0, 0, 5, 5);
			gbc_timeLabel.gridx = 2;
			gbc_timeLabel.gridy = 4;
			contentPanel.add(timeLabel, gbc_timeLabel);
		}
		{
			timeTextField = new JTextField();
			GridBagConstraints gbc_timeTextField = new GridBagConstraints();
			gbc_timeTextField.insets = new Insets(0, 0, 5, 5);
			gbc_timeTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_timeTextField.gridx = 4;
			gbc_timeTextField.gridy = 4;
			contentPanel.add(timeTextField, gbc_timeTextField);
			timeTextField.setColumns(10);
		}
		{
			JLabel statusLabel = new JLabel("status");
			statusLabel.setEnabled(false);
			GridBagConstraints gbc_statusLabel = new GridBagConstraints();
			gbc_statusLabel.insets = new Insets(0, 0, 0, 5);
			gbc_statusLabel.gridx = 2;
			gbc_statusLabel.gridy = 6;
			contentPanel.add(statusLabel, gbc_statusLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton makeReservationButton = new JButton("make Reservation");
				makeReservationButton.setActionCommand("OK");
				buttonPane.add(makeReservationButton);
				getRootPane().setDefaultButton(makeReservationButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}

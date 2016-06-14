package utility;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

// Class to create popup windows
public class PopUp extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	JLabel statusLabel;

	/**
	 * Create the dialog.
	 */
	public PopUp(String message) {

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
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{205, 0, 39, 0};
		gbl_contentPanel.rowHeights = new int[]{16, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			statusLabel = new JLabel("wefwe");
			GridBagConstraints gbc_statusLabel = new GridBagConstraints();
			gbc_statusLabel.gridwidth = 3;
			gbc_statusLabel.insets = new Insets(0, 0, 0, 5);
			gbc_statusLabel.anchor = GridBagConstraints.NORTHWEST;
			gbc_statusLabel.gridx = 0;
			gbc_statusLabel.gridy = 4;
			contentPanel.add(statusLabel, gbc_statusLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialog();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		statusLabel.setText(message);
	}

	public void closeDialog(){
		this.dispose();
	}

}

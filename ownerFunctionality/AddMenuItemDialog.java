package ownerFunctionality;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import menus.OwnerMenu;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class AddMenuItemDialog extends JDialog {
	String username;
	private Connection con;
	private final JPanel contentPanel = new JPanel();
	private JTextField menuItemTextField;
	private JTextField typeTextField;
	private JTextField priceTextField;
	
	private OwnerMenu ownerMenu;
	String menuItemID;
	int businessId;
	private MenuItem previousMenuItem = null;
	private boolean updateMode = false;

	/**
	 * Create the dialog.
	 */
	public AddMenuItemDialog(OwnerMenu theOwnerMenu, MenuItem thePreviousMenuItem, boolean theUpdateMode, int theBusinessId) {	
		this();
		ownerMenu = theOwnerMenu;
		previousMenuItem = thePreviousMenuItem;
		updateMode = theUpdateMode;
		businessId = theBusinessId;
		
		if(updateMode) {
			setTitle("Add new menu item");
			populateGui(previousMenuItem);
		}
	}
	
	private void populateGui(MenuItem theMenuItem) {
		menuItemTextField.setText(theMenuItem.getItemName());
		priceTextField.setText(theMenuItem.getPrice().toString());
		typeTextField.setText(theMenuItem.getItemType());		
	}
	
	public AddMenuItemDialog(OwnerMenu theOwnerMenu, int theBusinessId) {	
				this(theOwnerMenu, null, false, theBusinessId);
			}
	
	public AddMenuItemDialog() {
		setTitle("Add Menu Item");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblMenuItemName = new JLabel("Menu Item Name");
			contentPanel.add(lblMenuItemName, "2, 2, right, default");
		}
		{
			menuItemTextField = new JTextField();
			contentPanel.add(menuItemTextField, "4, 2, fill, default");
			menuItemTextField.setColumns(10);
		}
		{
			JLabel lblMenuItemType = new JLabel("Type/Category");
			contentPanel.add(lblMenuItemType, "2, 4, right, default");
		}
		{
			typeTextField = new JTextField();
			contentPanel.add(typeTextField, "4, 4, fill, default");
			typeTextField.setColumns(10);
		}
		{
			JLabel lblPrice = new JLabel("Price");
			contentPanel.add(lblPrice, "2, 6, right, default");
		}
		{
			priceTextField = new JTextField();
			contentPanel.add(priceTextField, "4, 6, fill, default");
			priceTextField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							saveMenuItem();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void saveMenuItem() throws SQLException {
		
		String menuItemName = menuItemTextField.getText();
		String menuItemType = typeTextField.getText();
		BigDecimal menuItemPrice = convertStringToBigDecimal(priceTextField.getText());
		
		MenuItem tempMenuItem = null;
		
		if (updateMode) {
			tempMenuItem = previousMenuItem;
			
			tempMenuItem.setItemName(menuItemName);
			tempMenuItem.setBusinessId(businessId);
			tempMenuItem.setItemType(menuItemType);
			tempMenuItem.setPrice(menuItemPrice);
		}
		else {
			tempMenuItem = new MenuItem(menuItemName, menuItemType, menuItemPrice, businessId);
		}
		
		try {
			// update price
			if(updateMode) {
				PreparedStatement myStmt = null;
				
				myStmt = con.prepareStatement("update MenuItem"
						+ " set price=?"
						+ " where id=?");
				
				// set params
				myStmt.setBigDecimal(1, tempMenuItem.getPrice());
				myStmt.setInt(2, tempMenuItem.getId());
				
				// execute SQL
				myStmt.executeUpdate();
				
			}
			else {
				//add menuItem
				PreparedStatement myStmt = null;
				myStmt = con.prepareStatement("insert into MenuItem"
						+ " (menuItemPrice, menuItemType, menuItemName, businessId)"
						+ " values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			// set params
			myStmt.setBigDecimal(1, menuItemPrice);
			myStmt.setString(2, menuItemType);
			myStmt.setString(3, menuItemName);
			myStmt.setInt(4, businessId);
			
			// execute SQL
			myStmt.executeUpdate();	
			
			// get the generated employee id
			ResultSet generatedKeys = myStmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				tempMenuItem.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Error generating key for menu item");
				}
			
			myStmt.executeUpdate();
			}
		}
		catch (SQLException exception){
			System.out.println(ERROR);
		}
	}
		

	protected BigDecimal convertStringToBigDecimal(String priceStr) {

		BigDecimal result = null;

		try {
			double priceDouble = Double.parseDouble(priceStr);

			result = BigDecimal.valueOf(priceDouble);
		} catch (Exception exc) {
			System.out.println("Invalid value. Defaulting to 0.0");
			result = BigDecimal.valueOf(0.0);
		}

		return result;
	}
}

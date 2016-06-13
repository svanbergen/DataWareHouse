package ownerFunctionality;

import java.math.BigDecimal;

public class MenuItem {
	private int menuItemID;
	private float Price;
	String ItemType;
	String Name;
	int BusinessID;
	

	public MenuItem(float Price, String ItemType, String Name, int BusinessID) {
		this(0, Price, ItemType, Name, BusinessID);
	}
	
	public MenuItem(int id, Float price, String itemType, String name, int businessId) {
		// TODO Auto-generated constructor stub
		super();
		this.menuItemID = id;
		this.Price = price;
		this.ItemType = itemType;
		this.Name = name;
		this.BusinessID = businessId;
	}


	public int getId() {
		return menuItemID;
	}

	public void setId(int id) {
		this.menuItemID = id;
	}

	public String getItemName() {
		return Name;
	}

	public void setItemName(String itemName) {
		this.Name = itemName;
	}

	public String getItemType() {
		return ItemType;
	}

	public void setItemType(String itemType) {
		this.ItemType = itemType;
	}

	public float getPrice() {
		return Price;
	}

	public void setPrice(Float price) {
		this.Price = price;
	}
	
	public int getBusinessId() {
		return BusinessID;
	}
	
	public void setBusinessId(int businessId) {
		this.BusinessID = businessId;
	}

}

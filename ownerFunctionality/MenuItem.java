package ownerFunctionality;

import java.math.BigDecimal;

public class MenuItem {
	private int id;
	private BigDecimal price;
	String itemType;
	String itemName;
	int businessId;


	public MenuItem(BigDecimal price, String itemType, String itemName, int businessId) {
		this(0, price, itemType, itemName, businessId);
	}
	
	public MenuItem(int id, BigDecimal price, String itemType, String itemName, int businessId) {
		// TODO Auto-generated constructor stub
		super();
		this.id = id;
		this.price = price;
		this.itemType = itemType;
		this.itemName = itemName;
		this.businessId = businessId;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public int getBusinessId() {
		return businessId;
	}
	
	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}

}

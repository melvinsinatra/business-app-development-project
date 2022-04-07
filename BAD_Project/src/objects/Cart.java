package objects;

public class Cart {
	
	private String BeverageID;
	private String BeverageName;
	private String BeverageType;
	private Integer BeveragePrice;
	private Integer BeverageStock;
	private Integer BeverageQuantity;
	private Integer SubTotal;

	public Cart(String beverageID, String beverageName, String beverageType, Integer beveragePrice,
			Integer beverageStock, Integer beverageQuantity, Integer subTotal) {
		super();
		BeverageID = beverageID;
		BeverageName = beverageName;
		BeverageType = beverageType;
		BeveragePrice = beveragePrice;
		BeverageStock = beverageStock;
		BeverageQuantity = beverageQuantity;
		SubTotal = subTotal;
	}

	public String getBeverageID() {
		return BeverageID;
	}

	public void setBeverageID(String beverageID) {
		BeverageID = beverageID;
	}

	public String getBeverageName() {
		return BeverageName;
	}

	public void setBeverageName(String beverageName) {
		BeverageName = beverageName;
	}

	public String getBeverageType() {
		return BeverageType;
	}

	public void setBeverageType(String beverageType) {
		BeverageType = beverageType;
	}

	public Integer getBeveragePrice() {
		return BeveragePrice;
	}

	public void setBeveragePrice(Integer beveragePrice) {
		BeveragePrice = beveragePrice;
	}

	public Integer getBeverageStock() {
		return BeverageStock;
	}

	public void setBeverageStock(Integer beverageStock) {
		BeverageStock = beverageStock;
	}

	public Integer getBeverageQuantity() {
		return BeverageQuantity;
	}

	public void setBeverageQuantity(Integer beverageQuantity) {
		BeverageQuantity = beverageQuantity;
	}

	public Integer getSubTotal() {
		return SubTotal;
	}

	public void setSubTotal(Integer subTotal) {
		SubTotal = subTotal;
	}
	
}

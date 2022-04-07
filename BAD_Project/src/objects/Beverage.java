package objects;

public class Beverage {

	private String beverageId;
	private String beverageName;
	private String beverageType;
	private Integer beveragePrice;
	private Integer beverageStock;

	public Beverage(String beverageId, String beverageName, String beverageType, Integer beveragePrice,
			Integer beverageStock) {
		super();
		this.beverageId = beverageId;
		this.beverageName = beverageName;
		this.beverageType = beverageType;
		this.beveragePrice = beveragePrice;
		this.beverageStock = beverageStock;
	}

	public String getBeverageId() {
		return beverageId;
	}

	public void setBeverageId(String beverageId) {
		this.beverageId = beverageId;
	}

	public String getBeverageName() {
		return beverageName;
	}

	public void setBeverageName(String beverageName) {
		this.beverageName = beverageName;
	}

	public String getBeverageType() {
		return beverageType;
	}

	public void setBeverageType(String beverageType) {
		this.beverageType = beverageType;
	}

	public Integer getBeveragePrice() {
		return beveragePrice;
	}

	public void setBeveragePrice(Integer beveragePrice) {
		this.beveragePrice = beveragePrice;
	}

	public Integer getBeverageStock() {
		return beverageStock;
	}

	public void setBeverageStock(Integer beverageStock) {
		this.beverageStock = beverageStock;
	}

}

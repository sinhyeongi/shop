package vo;


public class Item {
	private String name;
	private int price;
	private String category; // 카테고리 // 육류 , 과자 , 어류 , 과일 등등
	public Item() {	}
	public Item(String name, int price, String category) {
		super();
		this.name = name;
		this.price = price;
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return name + "/" +price+"/"+category;
	}
	
	
}

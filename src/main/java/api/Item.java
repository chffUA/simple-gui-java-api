package api;

public class Item {

	private double price;
	private String name;
	
	public Item(String name, double price) {
		this.name = name;
		this.price = price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}

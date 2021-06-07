package be.pxl.auctions.rest.resource;

public class BidCreateResource {

	private String email;
	private double price;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}

package com.nico.websraper;

public class Product {

	private String brand;
	private String model;
	private String price;
	private String category;
	private String seller;
	private String url;
	
	public Product(String seller, String brand, String model, String price, String category, String url) {
		super();
		this.seller = seller;
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.category = category;
		this.url = url;
	}
	public String getBrand() {
		return brand;
	}
	public String getModel() {
		return model;
	}
	public String getPrice() {
		return price;
	}
	public String getCategory() {
		return category;
	}
	public String getSeller() {
		return seller;
	}
	public String getUrl() {
		return url;
	}
	
	@Override
	public String toString(){
		return seller + " - " + category + " - " + brand + " - " + model + " - " + price;
	}
}

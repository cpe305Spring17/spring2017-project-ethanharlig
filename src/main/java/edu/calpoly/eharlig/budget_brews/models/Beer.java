package edu.calpoly.eharlig.budget_brews.models;

import java.util.ArrayList;

public class Beer {
	private String name;
	private double price;
	private int quantity;
	private String storeName;
	private long timestamp;
	private ArrayList<BeerHistory> history;
	
	public Beer() {
		this.history = new ArrayList<BeerHistory>();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public ArrayList<BeerHistory> getHistory() {
		if (history == null)
			return new ArrayList<BeerHistory>();
		return history;
	}

	public void setHistory(ArrayList<BeerHistory> history) {
		this.history = history;
	}

}

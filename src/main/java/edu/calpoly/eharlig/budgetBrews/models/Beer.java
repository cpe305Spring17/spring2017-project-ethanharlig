package edu.calpoly.eharlig.budgetbrews.models;

import java.util.ArrayList;
import java.util.List;

public class Beer {
	private String name;
	private double price;
	private int quantity;
	private String storeName;
	private long timestamp;
	private ArrayList<BeerHistory> history;
	private int upvotes;
	private int downvotes;
	
	public Beer() {
		this.history = new ArrayList();
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

	public List<BeerHistory> getHistory() {
		if (history == null)
			return new ArrayList();
		return history;
	}

	public void setHistory(ArrayList<BeerHistory> history) {
		this.history = history;
	}

  public int getUpvotes() {
    return upvotes;
  }

  public void setUpvotes(int upvotes) {
    this.upvotes = upvotes;
  }

  public int getDownvotes() {
    return downvotes;
  }

  public void setDownvotes(int downvotes) {
    this.downvotes = downvotes;
  }

}

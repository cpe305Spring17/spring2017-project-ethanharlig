package edu.calpoly.eharlig.budget_brews.models;

import java.util.ArrayList;

public class Store {
	private String address;
	private ArrayList<Beer> beers;
	private String name;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<Beer> getBeers() {
		return beers;
	}

	public void setBeers(ArrayList<Beer> beers) {
		this.beers = beers;
	}
	
	public void addBeer(Beer beer) {
		beers.add(beer);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

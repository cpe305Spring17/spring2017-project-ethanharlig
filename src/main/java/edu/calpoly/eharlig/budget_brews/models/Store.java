package edu.calpoly.eharlig.budget_brews.models;

import java.util.ArrayList;

public class Store {
	private ArrayList<Beer> beers;

	public ArrayList<Beer> getBeers() {
		return beers;
	}

	public void setBeers(ArrayList<Beer> beers) {
		this.beers = beers;
	}
	
	public void addBeer(Beer beer) {
		beers.add(beer);
	}

}

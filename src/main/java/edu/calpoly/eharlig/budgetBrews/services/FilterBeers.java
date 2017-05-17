package edu.calpoly.eharlig.budgetBrews.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetBrews.models.Beer;

public class FilterBeers implements RequestHandler<Map<String, String>, ArrayList<ArrayList>>{

  public ArrayList<ArrayList> handleRequest(Map<String, String> request, Context context) {
    List<Beer> beer12 = new GetAll().getAllQuantity(12);
    List<Beer> beer30 = new GetAll().getAllQuantity(30);
    
    ArrayList<ArrayList> byBeer12 = new ArrayList<ArrayList>();
    ArrayList<ArrayList> byBeer30 = new ArrayList<ArrayList>();
    ArrayList<Beer> eachBeer12 = new ArrayList<Beer>();
    ArrayList<Beer> eachBeer30 = new ArrayList<Beer>();

    for (Map.Entry<String, String> entry : request.entrySet()) {
      byBeer12.add(filterBeer(beer12, entry.getValue()));
    }

    for (ArrayList<Beer> beers : byBeer12)
      eachBeer12.addAll(beers);

    for (Map.Entry<String, String> entry : request.entrySet()) {
      byBeer30.add(filterBeer(beer30, entry.getValue()));
    }

    for (ArrayList<Beer> beers : byBeer30)
      eachBeer30.addAll(beers);


    Collections.sort(eachBeer12, new Comparator<Beer>() {
      public int compare(Beer b1, Beer b2) {
        return Double.compare(b1.getPrice(), b2.getPrice());
      }
    });

    Collections.sort(eachBeer30, new Comparator<Beer>() {
      public int compare(Beer b1, Beer b2) {
        return Double.compare(b1.getPrice(), b2.getPrice());
      }
    });
    
    ArrayList<ArrayList> byBeer = new ArrayList<ArrayList>();

    byBeer.add(eachBeer12);
    byBeer.add(eachBeer30);
    
    return byBeer;
  }
  
  public static ArrayList<Beer> filterBeer(List<Beer> beers, String beerName) {
    ArrayList<Beer> allBeers = new ArrayList<Beer>();

    for (Beer beer : beers) {
      if (beer.getName().equals(beerName)) {
        allBeers.add(beer);
      }
    }

    return allBeers;
  }

}

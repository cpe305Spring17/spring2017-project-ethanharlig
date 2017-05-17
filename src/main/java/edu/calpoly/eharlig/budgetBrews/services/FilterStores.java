package edu.calpoly.eharlig.budgetBrews.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetBrews.models.Beer;
import edu.calpoly.eharlig.budgetBrews.util.Sorting;


public class FilterStores implements RequestHandler<Map<String, String>, List<List<Beer>>>{

  public List<List<Beer>> handleRequest(Map<String, String> request, Context context) {
    List<Beer> beer12 = new GetAll().getAllQuantity(12);
    List<Beer> beer30 = new GetAll().getAllQuantity(30);
    
    List<List<Beer>> byBeer12 = new ArrayList<List<Beer>>();
    List<List<Beer>> byBeer30 = new ArrayList<List<Beer>>();
    List<Beer> eachBeer12 = new ArrayList<Beer>();
    List<Beer> eachBeer30 = new ArrayList<Beer>();

    for (Map.Entry<String, String> entry : request.entrySet()) {
      byBeer12.add(filterStore(beer12, entry.getValue()));
    }

    for (List<Beer> beers : byBeer12) {
      eachBeer12.addAll(beers);
    }

    for (Map.Entry<String, String> entry : request.entrySet()) {
      byBeer30.add(filterStore(beer30, entry.getValue()));
    }

    for (List<Beer> beers : byBeer30) {
      eachBeer30.addAll(beers);
    }

    eachBeer12 = Sorting.sort(eachBeer12);
    eachBeer30 = Sorting.sort(eachBeer30);
    
    List<List<Beer>> byStore = new ArrayList<List<Beer>>();

    byStore.add(eachBeer12);
    byStore.add(eachBeer30);
    
    return byStore;
  }
  
  public static List<Beer> filterStore(List<Beer> beers, String storeName) {
    List<Beer> allBeers = new ArrayList<Beer>();

    for (Beer beer : beers) {
      if (beer.getStoreName().equals(storeName)) {
        allBeers.add(beer);
      }
    }

    return allBeers;
  }

}

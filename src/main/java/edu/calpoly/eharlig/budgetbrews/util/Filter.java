package edu.calpoly.eharlig.budgetbrews.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.models.Beer;
import edu.calpoly.eharlig.budgetbrews.services.GetAll;

public class Filter implements RequestHandler<Map<String, String>, List<List<Beer>>> {

  public List<List<Beer>> handleRequest(Map<String, String> request, Context context) {
    if (request.isEmpty()) {
      return new ArrayList<>();
    }
    String toFilter = request.get("filter");
    request.remove("filter");

    List<Beer> beer12 = GetAll.getAllQuantity(12);
    List<Beer> beer30 = GetAll.getAllQuantity(30);

    List<List<Beer>> allBeer12 = new ArrayList<>();
    List<List<Beer>> allBeer30 = new ArrayList<>();
    List<Beer> eachBeer12 = new ArrayList<>();
    List<Beer> eachBeer30 = new ArrayList<>();

    for (Map.Entry<String, String> entry : request.entrySet()) {
      allBeer12.add(filter(toFilter, beer12, entry.getValue()));
    }

    for (List<Beer> beers : allBeer12) {
      eachBeer12.addAll(beers);
    }

    for (Map.Entry<String, String> entry : request.entrySet()) {
      allBeer30.add(filter(toFilter, beer30, entry.getValue()));
    }

    for (List<Beer> beers : allBeer30) {
      eachBeer30.addAll(beers);
    }

    eachBeer12 = Sorting.sort(eachBeer12);
    eachBeer30 = Sorting.sort(eachBeer30);

    List<List<Beer>> filtered = new ArrayList<>();

    filtered.add(eachBeer12);
    filtered.add(eachBeer30);

    return filtered;
  }

  public static List<Beer> filter(String toFilter, List<Beer> beers, String toFind) {
    List<Beer> allBeers = new ArrayList<>();
    if (toFilter == null || beers == null || toFind == null)
      return new ArrayList<>();

    if (toFilter.equals("stores")) {
      for (Beer beer : beers) {
        if (beer.getStoreName().equals(toFind)) {
          allBeers.add(beer);
        }
      }
    }

    if (toFilter.equals("beers")) {
      for (Beer beer : beers) {
        if (beer.getName().equals(toFind)) {
          allBeers.add(beer);
        }
      }

    }
    return allBeers;
  }

}

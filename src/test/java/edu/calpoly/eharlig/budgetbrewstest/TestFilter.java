package edu.calpoly.eharlig.budgetbrewstest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.calpoly.eharlig.budgetbrews.models.Beer;
import edu.calpoly.eharlig.budgetbrews.util.Filter;

public class TestFilter {
  Map<String, String> filterMap = new HashMap<>();
  Filter filter = new Filter();

  public void testFilter() {
    filterMap.put("filter", "stores");
    List<List<Beer>> listOfBeers = filter.handleRequest(filterMap, null);

    for (List<Beer> beers : listOfBeers) {
      for (Beer b : beers) {
        System.out.println(b.getName());
      }
    }
  }
}

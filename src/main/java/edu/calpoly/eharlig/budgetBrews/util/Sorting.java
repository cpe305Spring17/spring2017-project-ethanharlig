package edu.calpoly.eharlig.budgetBrews.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.calpoly.eharlig.budgetBrews.models.Beer;

public class Sorting {
  public static List<Beer> sort(List<Beer> beers) {

    Collections.sort(beers, new Comparator<Beer>() {
      public int compare(Beer b1, Beer b2) {
        return Double.compare(b1.getPrice(), b2.getPrice());
      }
    });
    
    return beers;
    
  }

}
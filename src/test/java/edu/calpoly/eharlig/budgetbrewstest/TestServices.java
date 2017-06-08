package edu.calpoly.eharlig.budgetbrewstest;

import edu.calpoly.eharlig.budgetbrews.models.Beer;
import edu.calpoly.eharlig.budgetbrews.services.UpdateBeer;
import junit.framework.TestCase;

public class TestServices extends TestCase {
  Beer beer = new Beer();
  UpdateBeer updateBeer = new UpdateBeer();
  
  public void testUpdateBeer() {
    beer.setName("sonar-test-IPA");
    beer.setPrice(1234.56);
    beer.setQuantity(12);
    beer.setStoreName("sonar-test-store");
    
    updateBeer.handleRequest(beer, null);
    
  }

}

package edu.calpoly.eharlig.budgetbrewstest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import edu.calpoly.eharlig.budgetbrews.models.Beer;
import edu.calpoly.eharlig.budgetbrews.models.Subscription;
import edu.calpoly.eharlig.budgetbrews.models.User;

public class TestModels {
  
  @Test
  public void testBeer() {
    Beer beer = new Beer();

    assertTrue(beer.getHistory().equals(new ArrayList<>()));
    
    beer.setDownvotes(5);
    assertTrue(beer.getDownvotes() == 5);
    
    beer.setHistory(new ArrayList<>());
    assertTrue(beer.getHistory().equals(new ArrayList<>()));
    
    beer.setName("myBeer");
    assertTrue(beer.getName().equals("myBeer"));
    
    beer.setObservers(new ArrayList<>());
    assertTrue(beer.getObservers().equals(new ArrayList<>()));
    
    beer.setPrice(24.31);
    assertTrue(Double.compare(beer.getPrice(), 24.31) == 0);
    
    beer.setQuantity(40);
    assertTrue(beer.getQuantity() == 40);
    
    beer.setStoreName("myStoreName");
    assertTrue(beer.getStoreName().equals("myStoreName"));
    
    beer.setTimestamp(1234567890);
    assertTrue(beer.getTimestamp() == 1234567890);
    
    beer.setUpvotes(55);
    assertTrue(beer.getUpvotes() == 55);
  }
  
  @Test
  public void testSubscription() {
    Subscription sub = new Subscription();
    
    sub.setBeerName("myBeer");
    assertTrue(sub.getBeerName().equals("myBeer"));
    
    sub.setEmail("testemail123@email.com");
    assertTrue(sub.getEmail().equals("testemail123@email.com"));
    
    sub.setQuantity(36);
    assertTrue(sub.getQuantity() == 36);
  }
  
  @Test
  public void testUser() {
    User user = new User();
    
    user.setEmail("testemail321@email.com");
    assertTrue(user.getEmail().equals("testemail321@email.com"));
    
    user.setPassword("password");
    assertTrue(user.getPassword().equals("password"));
    
    user.setUsername("myusername");
    assertTrue(user.getUsername().equals("myusername"));
  }

}

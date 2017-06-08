package edu.calpoly.eharlig.budgetbrewstest;

import java.util.ArrayList;
import java.util.List;

import edu.calpoly.eharlig.budgetbrews.models.Beer;
import edu.calpoly.eharlig.budgetbrews.models.Subscription;
import edu.calpoly.eharlig.budgetbrews.models.User;
import junit.framework.TestCase;

public class TestModels extends TestCase {
  Beer beer = new Beer();
  Subscription sub = new Subscription();
  User user = new User();
  

  public void testDownvotes() {
    beer.setDownvotes(5);
    assertTrue(beer.getDownvotes() == 5);
  }

  public void testName() {
    beer.setName("myBeer");
    assertTrue(beer.getName().equals("myBeer"));
  }

  public void testObservers() {
    beer.setObservers(new ArrayList<>());
    assertTrue(beer.getObservers().equals(new ArrayList<>()));
  }

  public void testObserversNull() {
    beer.setObservers(null);
    assertTrue(beer.getObservers().equals(new ArrayList<>()));
  }

  public void testPrice() {
    beer.setPrice(24.31);
    assertTrue(Double.compare(beer.getPrice(), 24.31) == 0);
  }

  public void testQuantity() {
    beer.setQuantity(40);
    assertTrue(beer.getQuantity() == 40);
  }

  public void testStoreName() {
    beer.setStoreName("myStoreName");
    assertTrue(beer.getStoreName().equals("myStoreName"));
  }

  public void testTimestamp() {
    beer.setTimestamp(1234567890);
    assertTrue(beer.getTimestamp() == 1234567890);
  }

  public void testUpvotes() {
    beer.setUpvotes(55);
    assertTrue(beer.getUpvotes() == 55);
  }
  
  public void testNotify() {
    beer.notifyObservers();
    
    List<String> obs = new ArrayList<>();
    obs.add("ethan.harlig@gmail.com");
    beer.setObservers(obs);
    beer.notifyObservers();
  }

  public void testSubBeerName() {
    sub.setBeerName("myBeer");
    assertTrue(sub.getBeerName().equals("myBeer"));
  }
  
  public void testSubEmail() {
    sub.setEmail("testemail123@email.com");
    assertTrue(sub.getEmail().equals("testemail123@email.com"));
  }
  
  public void testSubQuantity() {
    sub.setQuantity(36);
    assertTrue(sub.getQuantity() == 36);
  }

  public void testSubUsername() {
    sub.setUsername("myusername");
    assertTrue(sub.getUsername().equals("myusername"));
  }

  public void testUserEmail() {
    user.setEmail("testemail321@email.com");
    assertTrue(user.getEmail().equals("testemail321@email.com"));
  }
  
  public void testUserPassword() {
    user.setPassword("password");
    assertTrue(user.getPassword().equals("password"));
  }
  
  public void testUserUsername() {
    user.setUsername("myusername");
    assertTrue(user.getUsername().equals("myusername"));
  }

}

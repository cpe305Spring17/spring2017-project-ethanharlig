package edu.calpoly.eharlig.budgetbrews.models;

public class Subscription {
  private String email;
  private String beerName;
  private int quantity;

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getBeerName() {
    return beerName;
  }
  public void setBeerName(String beerName) {
    this.beerName = beerName;
  }
  public int getQuantity() {
    return quantity;
  }
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

}

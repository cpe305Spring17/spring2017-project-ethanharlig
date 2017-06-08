package edu.calpoly.eharlig.budgetbrews.models;

import java.util.List;

public class User {
  private String username;
  private String password;
  private String email;
  private List<String> upvotes;
  private List<String> downvotes;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<String> getUpvotes() {
    return upvotes;
  }

  public void setUpvotes(List<String> upvotes) {
    this.upvotes = upvotes;
  }

  public List<String> getDownvotes() {
    return downvotes;
  }

  public void setDownvotes(List<String> downvotes) {
    this.downvotes = downvotes;
  }

}

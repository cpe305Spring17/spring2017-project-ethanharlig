package edu.calpoly.eharlig.budgetbrews.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.amazonaws.services.simpleemail.AWSJavaMailTransport;

//import edu.calpoly.eharlig.budgetbrews.util.Credentials;

public class Beer {
  private String name;
  private double price;
  private int quantity;
  private String storeName;
  private long timestamp;
  private List<BeerHistory> history;
  private int upvotes;
  private int downvotes;
  private List<String> observers;

  public Beer() {
    this.history = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getStoreName() {
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public List<BeerHistory> getHistory() {
    if (history == null)
      return new ArrayList<>();
    return history;
  }

  public void setHistory(List<BeerHistory> history) {
    this.history = history;
  }

  public int getUpvotes() {
    return upvotes;
  }

  public void setUpvotes(int upvotes) {
    this.upvotes = upvotes;
  }

  public int getDownvotes() {
    return downvotes;
  }

  public void setDownvotes(int downvotes) {
    this.downvotes = downvotes;
  }

  public List<String> getObservers() {
    if (observers == null)
      return new ArrayList<>();
    return observers;
  }

  public void setObservers(List<String> observers) {
    this.observers = observers;
  }

  public void notifyObservers() {
    String from = "budgetbrews305@gmail.com";
    /*
     * Setup JavaMail to use Amazon SES by specifying the "aws" protocol and our AWS credentials.
     */
    Properties props = new Properties();
    props.setProperty("mail.transport.protocol", "aws");
//    props.setProperty("mail.aws.user", Credentials.getAwsKey());
//    props.setProperty("mail.aws.password", Credentials.getSecretKey());
    props.setProperty("mail.aws.user", "");
    props.setProperty("mail.aws.password", "");

    Session session = Session.getInstance(props);

    // Create a new Message
    Message msg = new MimeMessage(session);
    try {
      msg.setFrom(new InternetAddress(from));
      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(from));

      for (String obs : observers) {
        msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(obs));
      }
      msg.setSubject("Update from Budget Brews");
      msg.setText("Your subscribed beer " + name + " has had its price updated to " + price + " at "
          + storeName + ".");

      msg.saveChanges();

      Transport t = new AWSJavaMailTransport(session, null);
      t.connect();
      t.sendMessage(msg, null);

      t.close();
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

}

package edu.calpoly.eharlig.budgetbrews.util;

import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import edu.calpoly.eharlig.budgetbrews.dataaccess.DBAccess;
import edu.calpoly.eharlig.budgetbrews.models.Subscription;

public class Vote {
  private Vote() {}

  public static void vote(Subscription sub, String voteType) {
    Table beerTable = DBAccess.getTable("beer-" + sub.getQuantity());
    Item beerItem = beerTable.getItem("name", sub.getBeerName());
    int upvotes = beerItem.getInt(voteType);
    beerItem.withInt(voteType, upvotes + 1);
    beerTable.putItem(beerItem);
    
    Table usersTable = DBAccess.getTable("users");
    Item userItem = usersTable.getItem("username", sub.getUsername());
    
    List<String> userUpvotes = userItem.getList(voteType);
    userUpvotes.add(sub.getBeerName() + "-" + sub.getQuantity() + "-" + beerItem.getString("timestamp"));
    usersTable.putItem(userItem);
  }
}

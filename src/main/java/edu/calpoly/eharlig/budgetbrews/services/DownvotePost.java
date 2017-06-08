package edu.calpoly.eharlig.budgetbrews.services;

import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.dataaccess.DBAccess;
import edu.calpoly.eharlig.budgetbrews.models.Subscription;

public class DownvotePost implements RequestHandler<Subscription, Object> {
  
  public Object handleRequest(Subscription request, Context context) {
    Table beerTable = DBAccess.getTable("beer-" + request.getQuantity());
    Item beerItem = beerTable.getItem("name", request.getBeerName());
    int upvotes = beerItem.getInt("downvotes");
    beerItem.withInt("downvotes", upvotes + 1);
    beerTable.putItem(beerItem);
    
    Table usersTable = DBAccess.getTable("users");
    Item userItem = usersTable.getItem("username", request.getUsername());
    
    List<String> userUpvotes = userItem.getList("downvotes");
    userUpvotes.add(request.getBeerName() + "-" + request.getQuantity() + "-" + beerItem.getString("timestamp"));
    usersTable.putItem(userItem);

    return null;
  }

}


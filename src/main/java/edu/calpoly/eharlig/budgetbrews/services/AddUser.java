package edu.calpoly.eharlig.budgetbrews.services;

import java.util.ArrayList;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.dataaccess.DBAccess;
import edu.calpoly.eharlig.budgetbrews.models.User;

public class AddUser implements RequestHandler<User, PutItemOutcome> {

  public PutItemOutcome handleRequest(User request, Context context) {
    Table table = DBAccess.getTable("users");
    
    if (table.getItem("username", request.getUsername()) != null)
      return null;

    Item item = new Item();
    item.withPrimaryKey("username", request.getUsername());
    item.withString("password", request.getPassword());
    item.withString("email", request.getEmail());
    item.withList("subscriptions", new ArrayList<String>());
    item.withList("upvotes", new ArrayList<String>());
    item.withList("downvotes", new ArrayList<String>());

    return table.putItem(item);
  }

}

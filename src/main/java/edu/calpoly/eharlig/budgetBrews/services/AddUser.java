package edu.calpoly.eharlig.budgetbrews.services;

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

    Item item = new Item();
    item.withPrimaryKey("username", request.getUsername());
    item.withString("password", request.getPassword());
    item.withString("email", request.getEmail());

    return table.putItem(item);
  }

}

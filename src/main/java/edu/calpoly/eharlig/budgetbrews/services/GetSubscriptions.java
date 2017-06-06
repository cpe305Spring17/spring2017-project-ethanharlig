package edu.calpoly.eharlig.budgetbrews.services;

import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.dataaccess.DBAccess;
import edu.calpoly.eharlig.budgetbrews.models.User;

public class GetSubscriptions implements RequestHandler<User, List<String>>{

  @Override
  public List<String> handleRequest(User request, Context context) {
    Table usersTable = DBAccess.getTable("users");
    Item userItem = usersTable.getItem("username", request.getUsername());
    
    return userItem.getList("subscriptions");
  }

}

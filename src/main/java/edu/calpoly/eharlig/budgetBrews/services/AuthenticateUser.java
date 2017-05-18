package edu.calpoly.eharlig.budgetbrews.services;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.dataaccess.DBAccess;
import edu.calpoly.eharlig.budgetbrews.models.User;

public class AuthenticateUser implements RequestHandler<User, Boolean> {

  public Boolean handleRequest(User request, Context context) {
    Table table = DBAccess.getTable("users");
    Item item = table.getItem("username", request.getUsername());
    
    if (item == null)
      return false;

    return item.getString("password").equals(request.getPassword());
  }
  

}

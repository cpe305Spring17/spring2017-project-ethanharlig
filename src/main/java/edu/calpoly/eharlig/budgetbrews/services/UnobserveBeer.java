package edu.calpoly.eharlig.budgetbrews.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.dataaccess.DBAccess;
import edu.calpoly.eharlig.budgetbrews.models.Subscription;

public class UnobserveBeer implements RequestHandler<Subscription, Object> {

  @Override
  public Object handleRequest(Subscription request, Context context) {
    Table beerTable = DBAccess.getTable("beer-" + request.getQuantity());
    Item beerItem = beerTable.getItem("name", request.getBeerName());

    List<String> observers = beerItem.getList("observers");

    observers.remove(request.getEmail());

    Map<String, String> expressionAttributeNames = new HashMap<>();
    expressionAttributeNames.put("#O", "observers");

    Map<String, Object> expressionAttributeValues = new HashMap<>();
    expressionAttributeValues.put(":val1", observers);

    beerTable.updateItem("name", // key attribute name
        request.getBeerName(), // key attribute value
        "set #O = :val1", // UpdateExpression
        expressionAttributeNames, expressionAttributeValues);

    Table usersTable = DBAccess.getTable("users");
    Item userItem = usersTable.getItem("username", request.getUsername());

    List<String> subscriptions = userItem.getList("subscriptions");

    if (subscriptions.contains(request.getBeerName() + "-" + request.getQuantity()))
      subscriptions.remove(request.getBeerName() + "-" + request.getQuantity());

    Map<String, Object> userExpressionAttributeValues = new HashMap<>();
    userExpressionAttributeValues.put(":s", subscriptions);

    usersTable.updateItem("username", request.getUsername(), "set subscriptions = :s", null,
        userExpressionAttributeValues);

    return null;
  }

}

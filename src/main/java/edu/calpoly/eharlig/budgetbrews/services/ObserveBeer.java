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

public class ObserveBeer implements RequestHandler<Subscription, Object> {

  @Override
  public Object handleRequest(Subscription request, Context context) {
    Table table = DBAccess.getTable("beer-" + request.getQuantity());
    Item item = table.getItem("name", request.getBeerName());

    List<String> observers = item.getList("observers");

    observers.add(request.getEmail());

    Map<String, String> expressionAttributeNames = new HashMap<>();
    expressionAttributeNames.put("#O", "observers");

    Map<String, Object> expressionAttributeValues = new HashMap<>();
    expressionAttributeValues.put(":val1", observers);

    table.updateItem("name", // key attribute name
        request.getBeerName(), // key attribute value
        "set #O = :val1", // UpdateExpression
        expressionAttributeNames, expressionAttributeValues);

    return null;
  }

}

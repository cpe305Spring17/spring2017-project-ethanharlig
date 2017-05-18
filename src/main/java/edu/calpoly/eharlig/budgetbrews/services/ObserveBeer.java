package edu.calpoly.eharlig.budgetbrews.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.models.Subscription;
import edu.calpoly.eharlig.budgetbrews.util.Credentials;

public class ObserveBeer implements RequestHandler<Subscription, Object> {
  private static String AWS_KEY = Credentials.getAwsKey();
  private static String SECRET_KEY = Credentials.getSecretKey();
  // static final String AWS_KEY = "";
  // static final String SECRET_KEY = "";

  private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
      new BasicAWSCredentials(AWS_KEY, SECRET_KEY)).withRegion(Regions.US_WEST_2);

  private static DynamoDB dynamoDB = new DynamoDB((AmazonDynamoDB) client);

  @Override
  public Object handleRequest(Subscription request, Context context) {
    Table table = dynamoDB.getTable("beer-" + request.getQuantity());
    Item item = table.getItem("name", request.getBeerName());

    List<String> observers = item.getList("observers");

    observers.add(request.getEmail());

    Map<String, String> expressionAttributeNames = new HashMap<String, String>();
    expressionAttributeNames.put("#O", "observers");

    Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
    expressionAttributeValues.put(":val1", observers);

    UpdateItemOutcome outcome = table.updateItem("name", // key attribute name
        request.getBeerName(), // key attribute value
        "set #O = :val1", // UpdateExpression
        expressionAttributeNames, expressionAttributeValues);

    return null;
  }

}

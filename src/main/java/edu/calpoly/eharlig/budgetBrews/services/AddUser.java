package edu.calpoly.eharlig.budgetbrews.services;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.models.User;

public class AddUser implements RequestHandler<User, PutItemOutcome> {
//  private static String AWS_KEY = Credentials.getAwsKey();
//  private static String SECRET_KEY = Credentials.getSecretKey();
  static final String AWS_KEY = "";
  static final String SECRET_KEY = "";

  private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
      new BasicAWSCredentials(AWS_KEY, SECRET_KEY)).withRegion(Regions.US_WEST_2);

  private static DynamoDB dynamoDB = new DynamoDB((AmazonDynamoDB) client);

  public PutItemOutcome handleRequest(User request, Context context) {
    Table table = dynamoDB.getTable("users");

    Item item = new Item();
    item.withPrimaryKey("username", request.getUsername());
    item.withString("password", request.getPassword());
    return table.putItem(item);
  }

}

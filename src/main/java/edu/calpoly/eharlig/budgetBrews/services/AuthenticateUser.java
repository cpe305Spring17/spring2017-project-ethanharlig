package edu.calpoly.eharlig.budgetBrews.services;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetBrews.models.User;
import edu.calpoly.eharlig.budgetBrews.util.Credentials;

public class AuthenticateUser implements RequestHandler<User, Boolean> {
  private static String AWS_KEY = Credentials.getAwsKey();
  private static String SECRET_KEY = Credentials.getSecretKey();

  private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
      new BasicAWSCredentials(AWS_KEY, SECRET_KEY)).withRegion(Regions.US_WEST_2);

  private static DynamoDB dynamoDB = new DynamoDB((AmazonDynamoDB) client);

  public Boolean handleRequest(User request, Context context) {
    Table table = dynamoDB.getTable("users");
    Item item = table.getItem("username", request.getUsername());
    
    if (item == null)
      return false;
    if (item.getString("password").equals(request.getPassword()))
      return true;
    return false;
  }
  

}

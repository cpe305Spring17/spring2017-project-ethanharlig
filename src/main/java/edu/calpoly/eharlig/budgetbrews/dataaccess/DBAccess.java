package edu.calpoly.eharlig.budgetbrews.dataaccess;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import edu.calpoly.eharlig.budgetbrews.util.Credentials;

public class DBAccess {
  private static final String AWS_KEY = Credentials.getAwsKey();
  private static final String SECRET_KEY = Credentials.getSecretKey();

  private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
      new BasicAWSCredentials(AWS_KEY, SECRET_KEY)).withRegion(Regions.US_WEST_2);

  private static DynamoDB dynamoDB = new DynamoDB((AmazonDynamoDB) client);
  
  public static Table getTable(String tableName) {
    return dynamoDB.getTable(tableName);
  }
  
  public static ScanResult scan(ScanRequest scanRequest) {
    return client.scan(scanRequest);
  }

}

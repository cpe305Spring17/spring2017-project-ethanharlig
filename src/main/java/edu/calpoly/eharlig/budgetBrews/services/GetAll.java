package edu.calpoly.eharlig.budgetBrews.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetBrews.models.Beer;
import edu.calpoly.eharlig.budgetBrews.util.Credentials;

public class GetAll implements RequestHandler<Object, List<ArrayList<Beer>>> {
  // these are commented so that travis can pass
  // need to think of a way to keep credentials here but have travis pass
  private static String AWS_KEY = Credentials.getAwsKey();
  private static String SECRET_KEY = Credentials.getSecretKey();

  private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
      new BasicAWSCredentials(AWS_KEY, SECRET_KEY)).withRegion(Regions.US_WEST_2);

  public List<ArrayList<Beer>> handleRequest(Object request, Context context) {
    List<ArrayList<Beer>> beers = new ArrayList();
    beers.add(getAllQuantity(12));
    beers.add(getAllQuantity(30));

    return beers;
  }

  private ArrayList<Beer> getAllQuantity(int quantity) {
    ScanRequest scanRequest = new ScanRequest().withTableName("beer-" + quantity);

    ScanResult result = client.scan(scanRequest);

    ArrayList<Beer> allBeers = new ArrayList<Beer>();

    for (Map<String, AttributeValue> item : result.getItems()) {
      Beer current = new Beer();
      current.setName(item.get("name").getS());
      current.setPrice(Double.parseDouble(item.get("price").getN()));
      current.setStoreName(item.get("storeName").getS());
      current.setTimestamp(Long.parseLong(item.get("timestamp").getN()));
      current.setQuantity(quantity);
      current.setUpvotes(Integer.parseInt(item.get("upvotes").getN()));
      current.setDownvotes(Integer.parseInt(item.get("downvotes").getN()));

      allBeers.add(current);
    }

    Collections.sort(allBeers, new Comparator<Beer>() {
      public int compare(Beer b1, Beer b2) {
        return Double.compare(b1.getPrice(), b2.getPrice());
      }
    });

    return allBeers;
  }

}

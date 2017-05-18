package edu.calpoly.eharlig.budgetbrews.services;

import java.util.ArrayList;
import java.util.List;

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

import edu.calpoly.eharlig.budgetbrews.models.Beer;
import edu.calpoly.eharlig.budgetbrews.models.BeerHistory;
import edu.calpoly.eharlig.budgetbrews.util.Credentials;

public class UpdateBeer implements RequestHandler<Beer, PutItemOutcome> {
  private static String AWS_KEY = Credentials.getAwsKey();
  private static String SECRET_KEY = Credentials.getSecretKey();
//  static final String AWS_KEY = "";
//  static final String SECRET_KEY = "";

  private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
      new BasicAWSCredentials(AWS_KEY, SECRET_KEY)).withRegion(Regions.US_WEST_2);

  private static DynamoDB dynamoDB = new DynamoDB((AmazonDynamoDB) client);

  private static final String PRICE = "price";
  private static final String STORE_NAME = "storeName";
  private static final String TIMESTAMP = "timestamp";

  public PutItemOutcome handleRequest(Beer beer, Context context) {
    putItemQuantity(beer);

    return null;
  }

  private static void putItemQuantity(Beer beer) {
    // TODO add lowest price to table and set history with ordered
    // timestamps

    Table table = dynamoDB.getTable("beer-" + beer.getQuantity());

    Item item = table.getItem("name", beer.getName());

    Item toUpdate = new Item();

    if (item != null) {
      List<BeerHistory> bHistory = new ArrayList<>();

      BeerHistory currentBeer = new BeerHistory();
      currentBeer.setPrice(item.getDouble(PRICE));
      currentBeer.setStoreName(item.getString(STORE_NAME));
      currentBeer.setTimestamp(item.getLong(TIMESTAMP));
      currentBeer.setUpvotes(item.getInt("upvotes"));
      currentBeer.setDownvotes(item.getInt("downvotes"));

      bHistory.add(currentBeer);

      List<Item> history = item.getList("history");

      if (history != null) {
        for (Item i : history) {
          BeerHistory bh = new BeerHistory();
          bh.setPrice(i.getDouble(PRICE));
          bh.setStoreName(i.getString(STORE_NAME));
          bh.setTimestamp(i.getLong(TIMESTAMP));

          bHistory.add(bh);
        }
      }
      
      List<String> observers = item.getList("observers");
      if (observers != null) {
        List<String> obs = new ArrayList<>();
        for (String i : observers) {
          obs.add(i);
        }
        beer.setObservers(obs);
        toUpdate.withList("observers", obs);
        System.out.println("Notifying people");
        beer.notifyObservers();
      }
    }
    else {
      toUpdate.withList("observers", new ArrayList<String>());
    }


    toUpdate.withPrimaryKey("name", beer.getName());
    toUpdate.withDouble(PRICE, beer.getPrice());
    toUpdate.withString(STORE_NAME, beer.getStoreName());
    toUpdate.withLong(TIMESTAMP, System.currentTimeMillis());
    toUpdate.withInt("upvotes", 1);
    toUpdate.withInt("downvotes", 0);

    table.putItem(toUpdate);

  }

}

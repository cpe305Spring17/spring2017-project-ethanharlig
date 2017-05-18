package edu.calpoly.eharlig.budgetbrews.services;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.dataaccess.DBAccess;
import edu.calpoly.eharlig.budgetbrews.models.Beer;
import edu.calpoly.eharlig.budgetbrews.models.BeerHistory;

public class UpdateBeer implements RequestHandler<Beer, PutItemOutcome> {

  private static final String PRICE = "price";
  private static final String STORE_NAME = "storeName";
  private static final String TIMESTAMP = "timestamp";
  private static final String OBSERVERS = "observers";

  public PutItemOutcome handleRequest(Beer beer, Context context) {
    putItemQuantity(beer);

    return null;
  }

  private static void putItemQuantity(Beer beer) {
    // TODO add lowest price to table and set history with ordered
    // timestamps

    Table table = DBAccess.getTable("beer-" + beer.getQuantity());

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
      
      List<String> observers = item.getList(OBSERVERS);
      if (observers != null) {
        List<String> obs = new ArrayList<>();
        for (String i : observers) {
          obs.add(i);
        }
        beer.setObservers(obs);
        toUpdate.withList(OBSERVERS, obs);
        beer.notifyObservers();
      }
    }
    else {
      toUpdate.withList(OBSERVERS, new ArrayList<String>());
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

package edu.calpoly.eharlig.budgetbrews.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.dataaccess.DBAccess;
import edu.calpoly.eharlig.budgetbrews.models.Beer;

public class GetCheapest implements RequestHandler<Object, List<Beer>> {

  public List<Beer> handleRequest(Object request, Context context) {
    ArrayList<Beer> beers = new ArrayList<>();
    beers.add(getCheapestQuantity(12));
    beers.add(getCheapestQuantity(30));

    return beers;
  }

  public static Beer getCheapestQuantity(int quantity) {
    ScanRequest scanRequest = new ScanRequest().withTableName("beer-" + quantity);

    ScanResult result = DBAccess.scan(scanRequest);

    Beer cheapest = null;
    for (Map<String, AttributeValue> item : result.getItems()) {
      Beer current = new Beer();
      current.setName(item.get("name").getS());
      current.setPrice(Double.parseDouble(item.get("price").getN()));
      current.setStoreName(item.get("storeName").getS());
      current.setTimestamp(Long.parseLong(item.get("timestamp").getN()));
      current.setQuantity(quantity);
      current.setUpvotes(Integer.parseInt(item.get("upvotes").getN()));
      current.setDownvotes(Integer.parseInt(item.get("downvotes").getN()));

      if (cheapest == null) {
        cheapest = current;
      }

      if (current.getPrice() < cheapest.getPrice()
          || (Double.compare(current.getPrice(), cheapest.getPrice()) == 0)
              && current.getTimestamp() > cheapest.getTimestamp()) {
        cheapest = current;
      }
    }

    return cheapest;
  }

}

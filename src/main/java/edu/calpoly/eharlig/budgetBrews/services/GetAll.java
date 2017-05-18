package edu.calpoly.eharlig.budgetbrews.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.dataaccess.DBAccess;
import edu.calpoly.eharlig.budgetbrews.models.Beer;

public class GetAll implements RequestHandler<Object, List<List<Beer>>> {

  public List<List<Beer>> handleRequest(Object request, Context context) {
    List<List<Beer>> beers = new ArrayList<>();
    beers.add(getAllQuantity(12));
    beers.add(getAllQuantity(30));

    return beers;
  }

  public List<Beer> getAllQuantity(int quantity) {
    ScanRequest scanRequest = new ScanRequest().withTableName("beer-" + quantity);

    ScanResult result = DBAccess.scan(scanRequest);

    ArrayList<Beer> allBeers = new ArrayList<>();

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

    Collections.sort(allBeers, (Beer b1, Beer b2) -> Double.compare(b1.getPrice(), b2.getPrice()));

    return allBeers;
  }

}

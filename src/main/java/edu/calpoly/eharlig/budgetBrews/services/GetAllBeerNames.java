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


public class GetAllBeerNames implements RequestHandler<Object, List<String>> {

  public List<String> handleRequest(Object request, Context context) {
    List<String> beerNames = new ArrayList<>();

    for (String beer : getAllQuantity(12)) {
      if (!beerNames.contains(beer))
        beerNames.add(beer);
    }

    for (String beer : getAllQuantity(30)) {
      if (!beerNames.contains(beer))
        beerNames.add(beer);
    }

    return beerNames;
  }

  public List<String> getAllQuantity(int quantity) {
    ScanRequest scanRequest = new ScanRequest().withTableName("beer-" + quantity);

    ScanResult result = DBAccess.scan(scanRequest);

    ArrayList<String> allBeerNames = new ArrayList<>();

    for (Map<String, AttributeValue> item : result.getItems()) {
      allBeerNames.add(item.get("name").getS());
    }

    return allBeerNames;
  }


}

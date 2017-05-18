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


public class GetAllStores implements RequestHandler<Object, List<String>> {

  public List<String> handleRequest(Object request, Context context) {
    List<String> stores = new ArrayList<>();

    for (String store : getAllQuantity(12)) {
      if (!stores.contains(store))
        stores.add(store);
    }

    for (String store : getAllQuantity(30)) {
      if (!stores.contains(store))
        stores.add(store);
    }

    return stores;
  }

  public List<String> getAllQuantity(int quantity) {
    ScanRequest scanRequest = new ScanRequest().withTableName("beer-" + quantity);

    ScanResult result = DBAccess.scan(scanRequest);

    ArrayList<String> allStores = new ArrayList<>();

    for (Map<String, AttributeValue> item : result.getItems()) {
      allStores.add(item.get("storeName").getS());
    }

    return allStores;
  }


}

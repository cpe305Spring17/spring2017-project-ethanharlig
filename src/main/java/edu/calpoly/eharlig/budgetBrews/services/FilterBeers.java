package edu.calpoly.eharlig.budgetBrews.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetBrews.models.Beer;
import edu.calpoly.eharlig.budgetBrews.util.Credentials;

public class FilterBeers implements RequestHandler<Map<String, String>, ArrayList<ArrayList>>{
  private static String AWS_KEY = Credentials.getAwsKey();
  private static String SECRET_KEY = Credentials.getSecretKey();

  private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
      new BasicAWSCredentials(AWS_KEY, SECRET_KEY)).withRegion(Regions.US_WEST_2);

  public ArrayList<ArrayList> handleRequest(Map<String, String> request, Context context) {
    List<Beer> beer12 = new GetAll().getAllQuantity(12);
    List<Beer> beer30 = new GetAll().getAllQuantity(30);
    
    ArrayList<ArrayList> byBeer = new ArrayList<ArrayList>();

    for (Map.Entry<String, String> entry : request.entrySet()) {
      byBeer.add(filterStore(beer12, entry.getValue()));
    }

    for (Map.Entry<String, String> entry : request.entrySet()) {
      byBeer.add(filterStore(beer30, entry.getValue()));
    }

    return byBeer;
  }
  
  public static ArrayList<Beer> filterStore(List<Beer> beers, String beerName) {
    ArrayList<Beer> allBeers = new ArrayList<Beer>();

    for (Beer beer : beers) {
      if (beer.getName().equals(beerName)) {
        allBeers.add(beer);
      }
    }
    
    if (allBeers.size() == 0)
      return null;
    return allBeers;
  }

}

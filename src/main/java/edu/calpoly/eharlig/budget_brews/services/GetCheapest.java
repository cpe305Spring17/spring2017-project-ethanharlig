package edu.calpoly.eharlig.budget_brews.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budget_brews.models.Beer;
import edu.calpoly.eharlig.budget_brews.util.Credentials;

public class GetCheapest implements RequestHandler<Object, List<Beer>> {
	// these are commented so that travis can pass
	// need to think of a way to keep credentials here but have travis pass
//	private static String AWS_KEY = new Credentials().getAwsAccessKey();
//	private static String SECRET_KEY = new Credentials().getAwsSecretKey();
	 private static String AWS_KEY;
	 private static String SECRET_KEY;

	private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(new BasicAWSCredentials(AWS_KEY, SECRET_KEY))
			.withRegion(Regions.US_WEST_2);

	private static DynamoDB dynamoDB = new DynamoDB((AmazonDynamoDB) client);

	public List<Beer> handleRequest(Object request, Context context) {
		ArrayList<Beer> beers = new ArrayList<Beer>();
		beers.add(getCheapestQuantity(12));
		beers.add(getCheapestQuantity(30));

		return beers;
	}

	private Beer getCheapestQuantity(int quantity) {
		ScanRequest scanRequest = new ScanRequest().withTableName("beer-" + quantity);

		ScanResult result = client.scan(scanRequest);

		Beer cheapest = null;
		for (Map<String, AttributeValue> item : result.getItems()) {
			Beer current = new Beer();
			current.setName(item.get("name").getS());
			current.setPrice(Double.parseDouble(item.get("price").getN()));
			current.setStoreName(item.get("storeName").getS());
			current.setTimestamp(Long.parseLong(item.get("timestamp").getN()));
			current.setQuantity(quantity);

			if (cheapest == null) {
				cheapest = current;
			}

			if (current.getPrice() < cheapest.getPrice() || (current.getPrice() == cheapest.getPrice()
					&& current.getTimestamp() > cheapest.getTimestamp())) {
				cheapest = current;
			}
		}

		return cheapest;
	}

}

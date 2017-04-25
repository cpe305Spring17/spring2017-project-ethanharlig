package edu.calpoly.eharlig.budget_brews.services;

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

import edu.calpoly.eharlig.budget_brews.models.Beer;
import edu.calpoly.eharlig.budget_brews.models.BeerHistory;
//import edu.calpoly.eharlig.budget_brews.util.Credentials;

public class UpdateBeer implements RequestHandler<Beer, PutItemOutcome> {
	// these are commented so that travis can pass
	// need to think of a way to keep credentials here but have travis pass
//	private static String AWS_KEY = new Credentials().getAwsAccessKey();
//	private static String SECRET_KEY = new Credentials().getAwsSecretKey();
	private static String AWS_KEY;
	private static String SECRET_KEY;

	private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(new BasicAWSCredentials(AWS_KEY, SECRET_KEY))
			.withRegion(Regions.US_WEST_2);

	private static DynamoDB dynamoDB = new DynamoDB((AmazonDynamoDB) client);

	public PutItemOutcome handleRequest(Beer beer, Context context) {
		Table table = dynamoDB.getTable("beer-" + beer.getQuantity());

		Item item = table.getItem("name", beer.getName());

		List<Item> history = item.getList("history");
		List<BeerHistory> bHistory = new ArrayList<BeerHistory>();

		BeerHistory currentBeer = new BeerHistory();
		currentBeer.setPrice(item.getDouble("price"));
		currentBeer.setStoreName(item.getString("storeName"));
		currentBeer.setTimestamp(item.getLong("timestamp"));

		bHistory.add(currentBeer);

		for (Item i : history) {
			BeerHistory bh = new BeerHistory();
			bh.setPrice(i.getDouble("price"));
			bh.setStoreName(i.getString("storeName"));
			bh.setTimestamp(i.getLong("timestamp"));

			bHistory.add(bh);
		}

		Item toUpdate = new Item();

		for (BeerHistory bh : bHistory) {
			System.out.println(bh.getPrice());
		}

		toUpdate.withPrimaryKey("name", beer.getName());
		toUpdate.withDouble("price", beer.getPrice());
		toUpdate.withString("storeName", beer.getStoreName());
		toUpdate.withLong("timestamp", System.currentTimeMillis());
		// toUpdate.withList("history", bHistory);

		return table.putItem(toUpdate);
	}

}

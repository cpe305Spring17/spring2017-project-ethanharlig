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
import edu.calpoly.eharlig.budget_brews.util.Credentials;

public class UpdateBeer implements RequestHandler<Beer, PutItemOutcome> {
	private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
			new BasicAWSCredentials(new Credentials().getAwsAccessKey(), new Credentials().getAwsSecretKey()))
					.withRegion(Regions.US_WEST_2);

	private static DynamoDB dynamoDB = new DynamoDB((AmazonDynamoDB) client);

	public PutItemOutcome handleRequest(Beer beer, Context context) {
		Table table = dynamoDB.getTable("beer-" + beer.getQuantity());
		
		Item item = table.getItem("name", beer.getName());
		Beer thisBeer = new Beer();
		thisBeer.setName(item.getString("name"));
		List<Item> history = item.getList("history");
		
		ArrayList<BeerHistory> bHistory = new ArrayList<BeerHistory>();
		
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

		thisBeer.setHistory(bHistory);
		
		Item toUpdate = new Item();
		
		toUpdate.withPrimaryKey("name", beer.getName());
		toUpdate.withDouble("price", beer.getPrice());
		toUpdate.withString("storeName", beer.getStoreName());
		toUpdate.withLong("timestamp", System.currentTimeMillis());
		toUpdate.withList("history", bHistory);
		
		return table.putItem(toUpdate);
	}

}

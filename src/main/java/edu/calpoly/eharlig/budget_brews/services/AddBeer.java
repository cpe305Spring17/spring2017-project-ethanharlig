package edu.calpoly.eharlig.budget_brews.services;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budget_brews.models.Beer;
import edu.calpoly.eharlig.budget_brews.util.Credentials;

public class AddBeer implements RequestHandler<Beer, Object> {
	private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
			new BasicAWSCredentials(new Credentials().getAwsAccessKey(), new Credentials().getAwsSecretKey()))
					.withRegion(Regions.US_WEST_2);

	private static DynamoDB dynamoDB = new DynamoDB((AmazonDynamoDB) client);

	// need to think of how to add beer to store?
	public Object handleRequest(Beer beer, Context context) {
		Table table = dynamoDB.getTable(beer.getStore().getName().replaceAll(" ", "_"));
		
		Item item = new Item();
		item.withPrimaryKey("beerId", beer.getId());
		item.withString("name", beer.getName());
		item.withInt("quantity", beer.getQuantity());
		item.withDouble("price", beer.getPrice());

		return table.putItem(item);
	}

}

package edu.calpoly.eharlig.budget_brews.services;

import java.util.ArrayList;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budget_brews.models.Store;
import edu.calpoly.eharlig.budget_brews.util.Credentials;


public class AddStore implements RequestHandler<Store, Object> {
	private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
			new BasicAWSCredentials(new Credentials().getAwsAccessKey(), new Credentials().getAwsSecretKey()))
					.withRegion(Regions.US_WEST_2);

	private static DynamoDB dynamoDB = new DynamoDB((AmazonDynamoDB) client);

	public Object handleRequest(Store store, Context context) {
		createTable(store.getName().replaceAll(" ", "_"), 5L, 5L, "beerId", "S");
		return null;
	}

	private static String createTable(String tableName, long readCapacityUnits, long writeCapacityUnits,
			String partitionKeyName, String partitionKeyType) {
		try {

			ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			keySchema.add(new KeySchemaElement().withAttributeName(partitionKeyName).withKeyType(KeyType.HASH)); // Partition
																													// key

			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions
					.add(new AttributeDefinition().withAttributeName(partitionKeyName).withAttributeType("S"));

			CreateTableRequest request = new CreateTableRequest().withTableName(tableName)
					.withKeySchema(keySchema).withProvisionedThroughput(new ProvisionedThroughput()
							.withReadCapacityUnits(readCapacityUnits).withWriteCapacityUnits(writeCapacityUnits))
					.withAttributeDefinitions(attributeDefinitions);

			System.out.println("Issuing CreateTable request for " + tableName);
			Table table = dynamoDB.createTable(request);
			System.out.println("Waiting for " + tableName + " to be created...this may take a while...");
			table.waitForActive();
			return table.getTableName();
		} catch (Exception e) {
			System.err.println("CreateTable request failed for " + tableName);
			System.err.println(e.getMessage());
		}
		return "Failed to create table: " + tableName;
	}

}

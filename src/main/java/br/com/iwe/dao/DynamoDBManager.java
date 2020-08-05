package br.com.iwe.dao;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import br.com.iwe.model.Study;

public class DynamoDBManager {

	private static DynamoDBMapper mapper;

	static {

		AmazonDynamoDB ddb = null;
		final String endpoint = System.getenv("ENDPOINT_OVERRIDE");
		
        if (endpoint != null && !endpoint.isEmpty()) {
        	EndpointConfiguration endpointConfiguration = new EndpointConfiguration(endpoint, "us-east-1");
        	ddb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpointConfiguration).build();
        } else {
        	ddb = AmazonDynamoDBClientBuilder.defaultClient();
        }

		mapper = new DynamoDBMapper(ddb);
		
		final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS("DynamoDB"));
		eav.put(":val2", new AttributeValue().withS("RCU"));

		final DynamoDBQueryExpression<Study> queryExpression = new DynamoDBQueryExpression<Study>()
				.withIndexName("tagIndex").withConsistentRead(false)
				.withKeyConditionExpression("topic = :val1 and tag=:val2").withExpressionAttributeValues(eav);

		mapper.query(Study.class, queryExpression);

	}

	public DynamoDBMapper mapper() {
		return mapper;
	}

}
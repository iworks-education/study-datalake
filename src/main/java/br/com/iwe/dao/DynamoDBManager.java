package br.com.iwe.dao;

import java.net.URI;
import java.net.URISyntaxException;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDBManager {

	private static DynamoDbEnhancedClient dbEnhancedClient;

	static {
		String endpoint = System.getenv("ENDPOINT_OVERRIDE");

		if (endpoint == null || endpoint.isEmpty()) {
			endpoint = "https://dynamodb.us-east-1.amazonaws.com";
		}

		URI uri;
		try {
			uri = new URI(endpoint);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e.getCause());
		}

		final Region region = Region.US_EAST_1;
		final DynamoDbClient ddb = DynamoDbClient.builder().region(region).endpointOverride(uri)
				.httpClient(UrlConnectionHttpClient.builder().build())
				.credentialsProvider(EnvironmentVariableCredentialsProvider.create())
				.overrideConfiguration(ClientOverrideConfiguration.builder().build()).build();

		dbEnhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(ddb).build();
	}

	public static DynamoDbEnhancedClient mapper() {
		return dbEnhancedClient;
	}
}
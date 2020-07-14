package br.com.iwe.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import br.com.iwe.model.Study;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class StudyRepository {

	private static DynamoDbTable<Study> studyTable = null;

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

		studyTable = DynamoDbEnhancedClient.builder().dynamoDbClient(ddb).build().table("Study",
				TableSchema.fromBean(Study.class));
		
	
		studyTable.getItem(Key.builder().partitionValue("Boost").sortValue("2020-06-29T14:40:59Z").build());		
	}

	public Study save(final Study study) {
		studyTable.putItem(study);
		return study;
	}

	public Stream<Page<Study>> findByPeriod(final String topic, final String starts, final String ends) {

		final QueryConditional sortValueBetween = QueryConditional.sortBetween(
				k -> k.partitionValue(topic).sortValue(starts), k -> k.partitionValue(topic).sortValue(ends));

		return studyTable.query(r -> r.queryConditional(sortValueBetween)).stream();

	}

	public Stream<Page<Study>> findByTag(final String topic, final String tag) {

		final DynamoDbIndex<Study> index = studyTable.index("tagIndex");

		final QueryConditional conditional = QueryConditional.keyEqualTo(k -> k.partitionValue(topic).sortValue(tag));

		return index.query(r -> r.queryConditional(conditional)).stream();
	}

	public Stream<Page<Study>> findByIsConsumed(final String topic, final String isConsumed) {

		final DynamoDbIndex<Study> index = studyTable.index("consumedIndex");

		final QueryConditional conditional = QueryConditional
				.keyEqualTo(k -> k.partitionValue(topic).sortValue(isConsumed));

		return index.query(r -> r.queryConditional(conditional)).stream();
	}
}
package br.com.iwe.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import br.com.iwe.model.Study;

public class StudyRepository {

	private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

	public Study save(final Study study) {
		mapper.save(study);
		return study;
	}

	public List<Study> findByPeriod(final String topic, final String starts, final String ends) {

		final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(topic));
		eav.put(":val2", new AttributeValue().withS(starts));
		eav.put(":val3", new AttributeValue().withS(ends));

		final DynamoDBQueryExpression<Study> queryExpression = new DynamoDBQueryExpression<Study>()
				.withKeyConditionExpression("topic = :val1 and dateTimeCreation between :val2 and :val3")
				.withExpressionAttributeValues(eav);

		final List<Study> studies = mapper.query(Study.class, queryExpression);

		return studies;
	}

	public List<Study> findByTag(final String topic, final String tag) {

		final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(topic));
		eav.put(":val2", new AttributeValue().withS(tag));

		final DynamoDBQueryExpression<Study> queryExpression = new DynamoDBQueryExpression<Study>()
				.withIndexName("tagIndex").withConsistentRead(false)
				.withKeyConditionExpression("topic = :val1 and tag=:val2").withExpressionAttributeValues(eav);

		final List<Study> studies = mapper.query(Study.class, queryExpression);

		return studies;
	}

	public List<Study> findByIsConsumed(final String topic, final String isConsumed) {

		final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(topic));
		eav.put(":val2", new AttributeValue().withS(isConsumed));

		final Map<String, String> expression = new HashMap<>();

		// consumed is a reserver word in DynamoDB
		expression.put("#consumed", "consumed");

		final DynamoDBQueryExpression<Study> queryExpression = new DynamoDBQueryExpression<Study>()
				.withIndexName("consumedIndex").withConsistentRead(false)
				.withKeyConditionExpression("topic = :val1 and #consumed=:val2").withExpressionAttributeValues(eav)
				.withExpressionAttributeNames(expression);

		final List<Study> studies = mapper.query(Study.class, queryExpression);

		return studies;
	}
}
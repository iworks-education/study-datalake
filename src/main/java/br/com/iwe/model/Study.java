package br.com.iwe.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "study")
public class Study {

	@DynamoDBHashKey(attributeName = "topic")
	private String topic;
	@DynamoDBRangeKey(attributeName = "dateTimeCreation")
	private String dateTimeCreation;

	@DynamoDBIndexRangeKey(attributeName = "tag", localSecondaryIndexName = "tagIndex")
	private String tag;

	@DynamoDBAttribute(attributeName = "url")
	private String url;
	@DynamoDBAttribute(attributeName = "description")
	private String description;

	@DynamoDBIndexRangeKey(attributeName = "consumed", localSecondaryIndexName = "consumedIndex")
	private String consumed;

	public Study(String topic, String dateTimeCreation, String tag, String url, String description, String consumed) {
		super();
		this.topic = topic;
		this.dateTimeCreation = dateTimeCreation;
		this.tag = tag;
		this.url = url;
		this.description = description;
		this.consumed = consumed;
	}

	public Study() {
		super();
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getDateTimeCreation() {
		return dateTimeCreation;
	}

	public void setDateTimeCreation(String dateTimeCreation) {
		this.dateTimeCreation = dateTimeCreation;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getConsumed() {
		return consumed;
	}

	public void setConsumed(String consumed) {
		this.consumed = consumed;
	}

}

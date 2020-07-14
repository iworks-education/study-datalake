package br.com.iwe.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Study {

	private String topic;
	private String dateTimeCreation;
	private String tag;
	private String url;
	private String description;
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
	
	public Study(final String topic) {
		this.topic = topic;
	}

	public Study() {
		super();
	}

	@DynamoDbPartitionKey
	public String getTopic() {
		return topic;
	}

	@DynamoDbSortKey
	public String getDateTimeCreation() {
		return dateTimeCreation;
	}

	@DynamoDbSecondarySortKey(indexNames = "tagIndex")
	public String getTag() {
		return tag;
	}

	@DynamoDbAttribute(value = "url")
	public String getUrl() {
		return url;
	}

	@DynamoDbAttribute(value = "description")
	public String getDescription() {
		return description;
	}

	@DynamoDbSecondarySortKey(indexNames = "consumedIndex")
	public String getConsumed() {
		return consumed;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setConsumed(String consumed) {
		this.consumed = consumed;
	}

	public void setDateTimeCreation(String dateTimeCreation) {
		this.dateTimeCreation = dateTimeCreation;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
}
package br.com.iwe.model;

import java.util.Map;

public class HandlerRequest {

	private String body;
	private String path;
	private Map<String, String> pathParameters;
	private Map<String, String> queryStringParameters;

	public HandlerRequest(String body, String path, Map<String, String> pathParameters,
			Map<String, String> queryStringParameters) {
		super();
		this.body = body;
		this.path = path;
		this.pathParameters = pathParameters;
		this.queryStringParameters = queryStringParameters;
	}

	public HandlerRequest() {
		super();
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getPathParameters() {
		return pathParameters;
	}

	public void setPathParameters(Map<String, String> pathParameters) {
		this.pathParameters = pathParameters;
	}

	public Map<String, String> getQueryStringParameters() {
		return queryStringParameters;
	}

	public void setQueryStringParameters(Map<String, String> queryStringParameters) {
		this.queryStringParameters = queryStringParameters;
	}

}

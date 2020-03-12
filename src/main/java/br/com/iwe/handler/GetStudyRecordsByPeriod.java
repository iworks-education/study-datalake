package br.com.iwe.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.iwe.model.HandlerRequest;
import br.com.iwe.model.HandlerResponse;
import br.com.iwe.model.Study;

public class GetStudyRecordsByPeriod implements RequestHandler<HandlerRequest, HandlerResponse> {

	@Override
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {

		final String topic = request.getPathParameters().get("topic");
		final String starts = request.getQueryStringParameters().get("starts");
		final String ends = request.getQueryStringParameters().get("ends");

		context.getLogger().log("Searching for registered studies for " + topic + " topic between " + starts + " and " + ends);

		return HandlerResponse.builder().setStatusCode(200).setObjectBody(new Study()).build();
	}

}

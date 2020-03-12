package br.com.iwe.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.iwe.model.HandlerRequest;
import br.com.iwe.model.HandlerResponse;
import br.com.iwe.model.Study;

public class GetStudyRecordsByIsConsumed implements RequestHandler<HandlerRequest, HandlerResponse> {

	@Override
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {
		
		final String topic = request.getPathParameters().get("topic");
		final String isConsumed = request.getQueryStringParameters().get("isconsumed");

		context.getLogger().log("Searching for registered studies for " + topic + " thas is Consumed equals " + isConsumed);

		return HandlerResponse.builder().setStatusCode(200).setObjectBody(new Study()).build();
	}

}

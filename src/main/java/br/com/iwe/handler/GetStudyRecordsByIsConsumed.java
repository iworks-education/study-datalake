package br.com.iwe.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.iwe.dao.StudyRepository;
import br.com.iwe.model.HandlerRequest;
import br.com.iwe.model.HandlerResponse;
import br.com.iwe.model.Study;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;

public class GetStudyRecordsByIsConsumed implements RequestHandler<HandlerRequest, HandlerResponse> {

	private final StudyRepository repository = new StudyRepository();

	@Override
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {

		final String topic = request.getPathParameters().get("topic");
		final String isConsumed = request.getQueryStringParameters().get("isconsumed");

		context.getLogger()
				.log("Searching for registered studies for " + topic + " thas is Consumed equals " + isConsumed);
		
		final Stream<Page<Study>> studies = this.repository.findByIsConsumed(topic, isConsumed);

		final List<Study> jsonResult = new ArrayList<>();

		studies.forEach(s -> s.items().forEach(k -> jsonResult.add(k)));

		return HandlerResponse.builder().setStatusCode(200).setObjectBody(jsonResult).build();
	}
}
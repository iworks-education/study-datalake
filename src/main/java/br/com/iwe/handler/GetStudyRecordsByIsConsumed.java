package br.com.iwe.handler;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.iwe.dao.StudyRepository;
import br.com.iwe.model.HandlerRequest;
import br.com.iwe.model.HandlerResponse;
import br.com.iwe.model.Study;

public class GetStudyRecordsByIsConsumed implements RequestHandler<HandlerRequest, HandlerResponse> {

	private final StudyRepository repository = new StudyRepository();

	@Override
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {

		final String topic = request.getPathParameters().get("topic");
		final String isConsumed = request.getQueryStringParameters().get("isconsumed");

		context.getLogger()
				.log("Searching for registered studies for " + topic + " thas is Consumed equals " + isConsumed);
		final List<Study> studies = this.repository.findByIsConsumed(topic, isConsumed);

		if (studies == null || studies.isEmpty()) {
			return HandlerResponse.builder().setStatusCode(404).build();
		}

		return HandlerResponse.builder().setStatusCode(200).setObjectBody(studies).build();
	}
}
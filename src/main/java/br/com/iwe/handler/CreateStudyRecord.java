package br.com.iwe.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.iwe.dao.StudyRepository;
import br.com.iwe.model.HandlerRequest;
import br.com.iwe.model.HandlerResponse;
import br.com.iwe.model.Study;

public class CreateStudyRecord implements RequestHandler<HandlerRequest, HandlerResponse> {
	
	private final StudyRepository repository = new StudyRepository();

	@Override
	public HandlerResponse handleRequest(final HandlerRequest request, final Context context) {

		Study study = null;
		try {
			study = new ObjectMapper().readValue(request.getBody(), Study.class);
		} catch (IOException e) {
			return HandlerResponse.builder().setStatusCode(400).setRawBody("There is a error in your Study!").build();
		}
		context.getLogger().log("Creating a new study record for the topic " + study.getTopic());
		final Study studyRecorded = repository.save(study);
		
		final Map<String, String> headers = new HashMap<>();
		headers.put("message", "study record created");
		
		
		return HandlerResponse.builder().setStatusCode(201).setObjectBody(studyRecorded).setHeaders(headers).build();
	}
}
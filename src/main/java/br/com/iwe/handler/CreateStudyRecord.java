package br.com.iwe.handler;

import java.io.IOException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.iwe.model.HandlerRequest;
import br.com.iwe.model.HandlerResponse;
import br.com.iwe.model.Study;

public class CreateStudyRecord implements RequestHandler<HandlerRequest, HandlerResponse> {

	@Override
	public HandlerResponse handleRequest(final HandlerRequest request, final Context context) {

		final ObjectMapper mapper = new ObjectMapper();

		Study study = null;
		try {
			study = mapper.readValue(request.getBody(), Study.class);
		} catch (IOException e) {
			return HandlerResponse.builder().setStatusCode(400).setRawBody("There is a error in your Study!").build();
		}

		context.getLogger().log("Creating a new study record for the topic " + study.getTopic());

		return HandlerResponse.builder().setStatusCode(201).setObjectBody(study).build();

	}

}

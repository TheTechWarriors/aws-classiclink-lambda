package com.thetechwarriors.awsclassiclinklambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class CloudFormationHandler implements RequestStreamHandler {

	protected ObjectMapper mapper = new ObjectMapper();

	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		CloudFormationRequest req = null;

		// parse the request 
		try {
			req = mapper.readValue(input, CloudFormationRequest.class);
			logIt(context, "Request Received:\n" + mapper.writeValueAsString(req));
		} catch (JsonProcessingException e) {
			logIt(context, "ERROR: Parsing Request, Message=" + e.getMessage());
		}

		String lambdaResponse = "FAILED";
		if (req != null) {
			// validate the request
			if (validate(req, context)) {
				logIt(context, "Request is valid");
				// get the response for sending to CFN
				CloudFormationResponse cfnResponse = process(req, context);
				if (cfnResponse != null) {
					// send the response to CFN
					if (sendCloudFormationResponse(req.getResponseURL(), cfnResponse, context)) {
						lambdaResponse = "SUCCESS";
					}
				}
			} else {
				logIt(context, "Request is not valid");
			}
		}

		sendLambdaResponse(lambdaResponse, output);
	}

	public abstract CloudFormationResponse process(CloudFormationRequest req, Context context);
	public abstract boolean validate(CloudFormationRequest req, Context context);
	
	protected void sendLambdaResponse(String resp, OutputStream output) throws IOException {
		output.write(resp.getBytes());
		output.flush();
		output.close();
	}

	protected boolean sendCloudFormationResponse(String url, CloudFormationResponse resp, Context context) {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			
			String json = mapper.writeValueAsString(resp);
			HttpPut put = new HttpPut(url);
			put.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
			
			logIt(context, "About to send response to CloudFormation: Message=" + json);
			CloseableHttpResponse httpResponse = httpclient.execute(put);
			return httpResponse.getStatusLine().getStatusCode() == 200;
			
		} catch (Exception e) {
			logIt(context, "Error sending response to CloudFormation: Error=" + e.getMessage());
			return false;
		}
	}
	
	protected void logIt(Context context, String msg) {
		context.getLogger().log(msg);
	}
}

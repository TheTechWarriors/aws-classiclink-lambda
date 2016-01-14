/*
 * Copyright 2016 Tech Warriors, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thetechwarriors.awslambda.cloudformation;

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

/**
 * A base handler that takes care of processing requests from CloudFormation
 * custom resources. Requests made during Create/Update/Delete phase of
 * CloudFormation custom resources will invoke this lambda function. The
 * response of this lambda function does not really matter, instead a
 * CloudFormation endpoint needs to be informated of the results. The endpoint
 * URL is provided by CloudFormation in the request. Extenders of this object
 * need to
 * <ul>
 * <li>validate that the request has all the information they need to process
 * the request</li>
 * <li>process the request
 * <li></li>and generate a response to be sent to Cloudformation</li>
 */

public abstract class CloudFormationHandler implements RequestStreamHandler {

	protected ObjectMapper mapper = new ObjectMapper();

	/**
	 * Processes a request made by CloudFormation custom resource.
	 * 
	 * @param req the request from CloudFormation
	 * @param context the lambda execution context
	 * @return the response that should be sent to CloudFormation
	 */
	public abstract CloudFormationResponse process(CloudFormationRequest req, Context context);
	
	/**
	 * Validates if the CloudFormation request has all the needed parameters to
	 * process this request. This handler will not even attempt to process the
	 * request if it is not valid.
	 * 
	 * @param req the request from CloudFormation
	 * @param context the lambda execution context
	 * @return <code>true</code> if request is valid, <code>false</code>
	 *         otherwise
	 */
	public abstract boolean validate(CloudFormationRequest req, Context context);


	/**
	 * The main entry point for all AWS Lambda functions that want to process
	 * requests from CloudFormation custom resources
	 * 
	 * @see com.amazonaws.services.lambda.runtime.RequestStreamHandler#handleRequest(java.io.InputStream,
	 *      java.io.OutputStream, com.amazonaws.services.lambda.runtime.Context)
	 */
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		CloudFormationRequest req = null;

		// parse the request 
		try {
			req = mapper.readValue(input, CloudFormationRequest.class);
			logIt(context, "Received: Request=" + mapper.writeValueAsString(req));
		} catch (JsonProcessingException e) {
			logIt(context, "Error parsing request: Error=" + e.getMessage());
		}

		String lambdaResponse = "FAILED";
		if (req != null) {
			// validate the request
			if (validate(req, context)) {
				logIt(context, "Request is valid, attempting to process request");
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
			
			logIt(context, "About to notify CloudFormation: Message=" + json);
			CloseableHttpResponse httpResponse = httpclient.execute(put);
			return httpResponse.getStatusLine().getStatusCode() == 200;
			
		} catch (Exception e) {
			logIt(context, "Error notifying CloudFormation: Error=" + e.getMessage());
			return false;
		}
	}
	
	protected void logIt(Context context, String msg) {
		context.getLogger().log(msg);
	}
}

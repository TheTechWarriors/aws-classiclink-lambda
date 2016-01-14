package com.thetechwarriors.awsclassiclinklambda;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJson {

	public static void main(String[] args) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		CloudFormationRequest req = new CloudFormationRequest();
		req.setLogicalResourceId("id");
		req.setRequestId("id");
		req.setRequestType("type");
		req.getResourceProperties().put("msg", "Hello World!");
		req.setResponseURL("url");
		req.setResourceType("type");
		req.setServiceToken("token");
		req.setStackId("id");
		
		System.out.println(mapper.writeValueAsString(req));
	}
}

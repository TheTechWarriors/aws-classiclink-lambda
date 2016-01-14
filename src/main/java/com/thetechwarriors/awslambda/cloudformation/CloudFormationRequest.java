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

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CloudFormationRequest {

    private String requestType;
    private String serviceToken;
    private String responseURL;
    private String stackId;
    private String requestId;
    private String logicalResourceId;
    private String resourceType;
    private Map<String, Object> resourceProperties = new HashMap<>();

    @JsonProperty("RequestType")
    public String getRequestType() {
		return requestType;
	}
    @JsonProperty("ServiceToken")
    public String getServiceToken() {
		return serviceToken;
	}
	@JsonProperty("ResponseURL")
	public String getResponseURL() {
		return responseURL;
	}
	
    @JsonProperty("StackId")
	public String getStackId() {
		return stackId;
	}
    @JsonProperty("RequestId")
	public String getRequestId() {
		return requestId;
	}
    @JsonProperty("LogicalResourceId")
	public String getLogicalResourceId() {
		return logicalResourceId;
	}
    @JsonProperty("ResourceType")
	public String getResourceType() {
		return resourceType;
	}
    @JsonProperty("ResourceProperties")
	public Map<String, Object> getResourceProperties() {
		return resourceProperties;
	}

    public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}
	public void setResponseURL(String responseURL) {
		this.responseURL = responseURL;
	}
	public void setStackId(String stackId) {
		this.stackId = stackId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public void setLogicalResourceId(String logicalResourceId) {
		this.logicalResourceId = logicalResourceId;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public void setResourceProperties(Map<String, Object> resourceProperties) {
		this.resourceProperties = resourceProperties;
	}
}

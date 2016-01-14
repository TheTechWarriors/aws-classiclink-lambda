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
public class CloudFormationResponse {

    private String status;
    private String reason;
    private String physicalResourceId;
    private Map<String, Object> data = new HashMap<String, Object>();

    private String stackId;
    private String requestId;
    private String logicalResourceId;
    
    public CloudFormationResponse() { }
    
    public CloudFormationResponse(CloudFormationRequest request) {
    	setStackId(request.getStackId());
    	setRequestId(request.getRequestId());
    	setLogicalResourceId(request.getLogicalResourceId());
    }
    
    @JsonProperty("Status")
    public String getStatus() {
		return status;
	}
	@JsonProperty("Reason")
	public String getReason() {
		return reason;
	}
	@JsonProperty("PhysicalResourceId")
	public String getPhysicalResourceId() {
		return physicalResourceId;
	}
	@JsonProperty("Data")
	public Map<String, Object> getData() {
		return data;
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

	public void setStatus(String status) {
		this.status = status;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public void setPhysicalResourceId(String physicalResourceId) {
		this.physicalResourceId = physicalResourceId;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
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
}

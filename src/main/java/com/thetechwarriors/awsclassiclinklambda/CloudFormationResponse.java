package com.thetechwarriors.awsclassiclinklambda;

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

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

package com.thetechwarriors.awsclassiclinklambda;

import java.util.Map;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DisableVpcClassicLinkDnsSupportRequest;
import com.amazonaws.services.ec2.model.DisableVpcClassicLinkRequest;
import com.amazonaws.services.ec2.model.EnableVpcClassicLinkDnsSupportRequest;
import com.amazonaws.services.ec2.model.EnableVpcClassicLinkRequest;
import com.amazonaws.services.lambda.runtime.Context;

public class EnableClassicLinkHanlder extends CloudFormationHandler {

	
	public CloudFormationResponse process(CloudFormationRequest req, Context context) {
		
		CloudFormationResponse resp = new CloudFormationResponse(req);
		String vpcId = (String) req.getResourceProperties().get("VpcId");
		
		try {
			
			AmazonEC2 ec2 = new AmazonEC2Client();
			if ("Delete".equals(req.getRequestType())) {

				ec2.disableVpcClassicLinkDnsSupport(new DisableVpcClassicLinkDnsSupportRequest().withVpcId(vpcId));
				logIt(context, "Disabled VPC classiclink DNS support: VpcId=" + vpcId);
				ec2.disableVpcClassicLink(new DisableVpcClassicLinkRequest().withVpcId(vpcId));
				logIt(context, "Disabled VPC classiclink: VpcId=" + vpcId);

			} else {
				
				ec2.enableVpcClassicLink(new EnableVpcClassicLinkRequest().withVpcId(vpcId));
				logIt(context, "Enabled VPC classiclink: VpcId=" + vpcId);
				ec2.enableVpcClassicLinkDnsSupport(new EnableVpcClassicLinkDnsSupportRequest().withVpcId(vpcId));
				logIt(context, "Enabled VPC classiclink DNS support: VpcId=" + vpcId);
			}
			
			resp.setStatus("SUCCESS");
			resp.setPhysicalResourceId(vpcId + "-classiclink");

		} catch (Exception e) {
			resp.setStatus("FAILED");
			resp.setReason(e.getMessage());
			logIt(context, "Error in EnableClassicLinkHanlder: Message=" + e.getMessage());
		}
		
		return resp;
	}
	
	public boolean validate(CloudFormationRequest req, Context context) {
		Map<String, Object> properties = req.getResourceProperties();
		return properties.containsKey("VpcId");
	}
}

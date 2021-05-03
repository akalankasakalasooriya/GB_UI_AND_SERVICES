package com;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RequestValidator {
	public boolean validate(String key) {

		// request validation

		Client client;
		String baseuri = "http://localhost:8081/authentication-service/API/requestvalidator";
		ClientResponse response;
		String output = null;

		try {

			client = Client.create();
			WebResource webResource = client.resource(baseuri);
			String input = "{key:" + key + "}";

			// POST method
			response = webResource.accept("application/json").type("application/json").post(ClientResponse.class,
					input);

			// check response status code
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			// response
			output = response.getEntity(String.class);
			System.out.print(output);
			try {
				JsonObject itemObject = new JsonParser().parse(output).getAsJsonObject();
				String status = itemObject.get("validation_status").getAsString();
				if (status.equals("valid")) {
					return true;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// end of validation

		return false;
	}

}

package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.AuthenticationModel;

@Path("/")
public class AuthenticationService {
	AuthenticationModel auth = new AuthenticationModel();

	@POST
	@Path("/session") // login
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String newLoginRequest(String data) {
		JsonObject itemObject = new JsonParser().parse(data).getAsJsonObject();
		String username = itemObject.get("username").getAsString();
		String password = itemObject.get("password").getAsString();
		return auth.userValidation(username, password);
	}

	// -----------------------------------------
	@DELETE
	@Path("/session") // logout
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String logOut(String data) {
		JsonObject itemObject = new JsonParser().parse(data).getAsJsonObject();
		JsonObject result = new JsonObject();
		result.addProperty("status", "error");
		String key = itemObject.get("key").getAsString();
		String status = auth.logOut(key);
		result.addProperty("status", status);
		return result.toString();
	}

	// -------------------------------------------

	@POST
	@Path("/requestvalidator")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String requestvalidator(String data) {
		JsonObject itemObject = new JsonParser().parse(data).getAsJsonObject();
		JsonObject result = new JsonObject();
		result.addProperty("status", "error");
		String key = itemObject.get("key").getAsString();
		String status = auth.requestValidation(key);
		result.addProperty("validation_status", status);
		return result.toString();
	}

	// -------------------------------------------

	@PUT
	@Path("/credentials") // update user credentials
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String credentialUpdate(String data) {
		JsonObject result = new JsonObject();
		result.addProperty("status", "error");
		String status = "error";
		JsonObject itemObject = new JsonParser().parse(data).getAsJsonObject();
		String key = itemObject.get("key").getAsString();
		String id = itemObject.get("user_id").getAsString();
		String username = itemObject.get("username").getAsString();
		String password = itemObject.get("password").getAsString();
		// validating request
		if (auth.requestValidation(key).equals("valid")) {
			status = auth.credentialUpdate(Integer.valueOf(id), username, password);
		}
		result.addProperty("status", status);
		return result.toString();
	}

	// -------------------------------------------

	@POST
	@Path("/credentials") // add new user credentials
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addUser(String data) {
		JsonObject itemObject = new JsonParser().parse(data).getAsJsonObject();
		JsonObject result = new JsonObject();
		result.addProperty("status", "error");
		int id = itemObject.get("user_id").getAsInt();
		String username = itemObject.get("username").getAsString();
		String password = itemObject.get("password").getAsString();
		String status = "";
		try {
			status = auth.addUser(id, username, password);
		} catch (Exception e) {
			// TODO: handle exception
		}
		result.addProperty("status", status);
		return result.toString();
	}

	// ------------------------------------------------------------

	@DELETE
	@Path("/credentials") // remove user credentials
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String removeUser(String data) {
		JsonObject itemObject = new JsonParser().parse(data).getAsJsonObject();
		String status = "error";
		JsonObject result = new JsonObject();
		result.addProperty("status", "error");
		String key = itemObject.get("key").getAsString();
		int id = itemObject.get("user_id").getAsInt();

		// validating request
		if (auth.requestValidation(key).equals("valid")) {

			if (auth.removeCredentials(id)) {
				status = auth.logOut(key);
				result.addProperty("status", status);
			}
		}

		return result.toString();
	}
}

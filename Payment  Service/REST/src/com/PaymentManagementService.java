package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.PaymentManagementModel;

@Path("/")
public class PaymentManagementService {
	PaymentManagementModel cart = new PaymentManagementModel();
	RequestValidator requestValidator =  new RequestValidator();

	@POST
	@Path("/cartItems")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// insert item/s
	public String addItem(String data) {
		JsonObject result = new JsonObject();
		result.addProperty("status", "error");
		int userId = 0;
		int productId = 0;
		int quantity = 0;

		try {
			JsonObject itemObject = new JsonParser().parse(data).getAsJsonObject();
			// request validation
			if (!requestValidator.validate(itemObject.get("key").getAsString())) {
				return result.toString();
			}
			userId = itemObject.get("user_id").getAsInt();

			if (itemObject.has("items")) {

				for (JsonElement singleItem : itemObject.get("items").getAsJsonArray()) {
					JsonObject itemObj = singleItem.getAsJsonObject();
					productId = itemObj.get("product_id").getAsInt();
					quantity = itemObj.get("quantity").getAsInt();
					cart.addItemToCart(userId, productId, quantity);

				}
				result.addProperty("status", "done_all");

			} else if (itemObject.has("user_id")) {
				productId = itemObject.get("product_id").getAsInt();
				quantity = itemObject.get("quantity").getAsInt();
				if (cart.addItemToCart(userId, productId, quantity)) {
					result.addProperty("status", "done");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.addProperty("status", "error");
		}

		return result.toString();
	}

	@DELETE // delete cart item of user one by one or all
	@Path("/cartItems")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String removeItem(String data) {
		JsonObject result = new JsonObject();
		result.addProperty("status", "error");
		String returnValue = "failed";

		JsonObject itemObject = new JsonParser().parse(data).getAsJsonObject();
		String key = itemObject.get("key").getAsString();
		// request validation
		if (!requestValidator.validate(key)) {
			return result.toString();
		}
		int userId = itemObject.get("user_id").getAsInt();
		if (!itemObject.has("product_id")) {
			// delete all
			returnValue = cart.removeItemFromCart(userId);
			result.addProperty("status", returnValue);
			return result.toString();
		}
		int productId = itemObject.get("product_id").getAsInt();
		// delete one by one
		returnValue = cart.removeItemFromCart(userId, productId);
		result.addProperty("status", returnValue);
		return result.toString();
	}

	@GET
	@Path("/cartItems")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	// read cart
	public String readItem(@DefaultValue("0") @QueryParam("user_id") Integer user,
			@DefaultValue("") @QueryParam("key") String key) {
		// request validation
		if (!requestValidator.validate(key)) {
			JsonObject result = new JsonObject();
			result.addProperty("status", "error_unauthorized");
			return result.toString();
		}

		String returnValue = "";
		returnValue = cart.returnCartDetails(user);
		return returnValue;
	}

	// when shopping cart is done
	@DELETE
	@Path("/buyCart")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String buyCart(String data) {

		JsonObject itemObject = new JsonParser().parse(data).getAsJsonObject();
		String key = itemObject.get("key").getAsString();
		// request validation
		if (!requestValidator.validate(key)) {
			JsonObject result = new JsonObject();
			result.addProperty("status", "error_unauthorized");
			return result.toString();
		}

		int userId = itemObject.get("user_id").getAsInt();

		return cart.endShopping(userId);
	}
	
	//read payment history
	@GET
	@Path("/history")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String readHistory(@DefaultValue("") @QueryParam("key") String key) {

		// request validation
		if (!requestValidator.validate(key)) {
			JsonObject result = new JsonObject();
			result.addProperty("status", "error_unauthorized");
			return result.toString();
		}

		return cart.readHistory();
	}

}

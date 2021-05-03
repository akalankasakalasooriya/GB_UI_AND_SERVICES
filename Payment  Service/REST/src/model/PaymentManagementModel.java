package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PaymentManagementModel {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf_payment_management", "paf_user",
					"^paf_user_pw_000");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public boolean addItemToCart(int user_id, int product_id, int quantity) {

		try {

			Connection con = connect();
			if (con == null) {
				return false;
			}
			// create a prepared statement
			String query = " insert into shopping_cart values(? , ? , ?) ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setInt(1, user_id);
			preparedStmt.setInt(2, product_id);
			preparedStmt.setInt(3, quantity);
			preparedStmt.executeUpdate();

		} catch (Exception e) {

			System.err.println(e.getMessage());
			return false;

		}

		return true;
	}

//delete one one one per user
	public String removeItemFromCart(int user_id, int product_id) {
		String output = "error";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = " delete FROM shopping_cart where user_id = ? and product_id = ? ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setInt(1, user_id);
			preparedStmt.setInt(2, product_id);
			// execute the statement

			int result = preparedStmt.executeUpdate();

			if (result >= 1) {
				output = "done";

			}
			con.close();

		} catch (Exception e) {
			output = "Exception";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// delete all per user
	public String removeItemFromCart(int user_id) {
		String output = "error";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = " delete FROM shopping_cart where user_id = ? ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setInt(1, user_id);

			// execute the statement

			int result = preparedStmt.executeUpdate();

			if (result >= 1) {
				output = "done";

			}
			con.close();

		} catch (Exception e) {
			output = "Exception";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String returnCartDetails(int user_id) {
		JsonObject cartData = new JsonObject();

		try {

			Connection con = connect();
			if (con == null) {
				return cartData.toString();
			}
			// create a prepared statement
			String query = " select * from shopping_cart where user_id = ? ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setInt(1, user_id);
			ResultSet rs = preparedStmt.executeQuery();

			JsonArray array = new JsonArray();

			while (rs.next()) {
				JsonObject innerItems = new JsonObject();
				innerItems.addProperty("product_id", rs.getInt(2));
				innerItems.addProperty("quantity", rs.getInt(3));
				array.add(innerItems);
				innerItems = null;

			}
			cartData.add("items", array);

		} catch (Exception e) {

			e.printStackTrace();
			cartData.addProperty("status", "error");
			return cartData.toString();

		}

		return cartData.toString();
	}

	// when cart is done
	public String endShopping(int user_id) {
		JsonObject cartData = new JsonObject();
		cartData.addProperty("status", "error");
		try {

			Connection con = connect();
			if (con == null) {
				return cartData.toString();
			}
			// create a prepared statement
			String query = " select * from shopping_cart where user_id = ? ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setInt(1, user_id);
			ResultSet rs = preparedStmt.executeQuery();
			int counter = 0;
			while (rs.next()) {
				counter++;
				if (!this.addToHistory(rs.getInt(1), rs.getInt(2), rs.getInt(3))) {
					System.out.println("error while inserting");
				}

			}
			if (counter > 0) {
				this.removeItemFromCart(user_id);
				cartData.addProperty("status", "done");
			}

		} catch (Exception e) {

			e.printStackTrace();
			cartData.addProperty("status", "error");
			return cartData.toString();

		}

		return cartData.toString();
	}

	// insert to history
	// when cart is done
	public boolean addToHistory(int user_id, int product_id, int quantity) {

		try {

			Connection con = connect();
			if (con == null) {
				return false;
			}
			// create a prepared statement
			String query = " insert into payment_history(user_id , product_id , quantity , timestap) values(? , ? , ? , ?) ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			preparedStmt.setInt(1, user_id);
			preparedStmt.setInt(2, product_id);
			preparedStmt.setInt(3, quantity);
			preparedStmt.setString(4, formatter.format(date));
			preparedStmt.executeUpdate();

		} catch (Exception e) {

			System.err.println(e.getMessage());
			return false;

		}

		return true;
	}

	public String readHistory() { // payment history

		JsonObject cartData = new JsonObject();

		try {

			Connection con = connect();
			if (con == null) {
				return cartData.toString();
			}
			// create a prepared statement
			String query = " select * from payment_history ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			JsonArray array = new JsonArray();

			while (rs.next()) {
				JsonObject innerItems = new JsonObject();
				innerItems.addProperty("user_id", rs.getInt(2));
				innerItems.addProperty("product_id", rs.getInt(3));
				innerItems.addProperty("quantity", rs.getInt(4));
				innerItems.addProperty("date", rs.getString(5));
				array.add(innerItems);
				innerItems = null;

			}
			cartData.add("history", array);

		} catch (Exception e) {

			e.printStackTrace();
			cartData.addProperty("status", "error");
			return cartData.toString();

		}
		return cartData.toString();
	}

}

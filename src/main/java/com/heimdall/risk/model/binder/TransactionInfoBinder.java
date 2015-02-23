package com.heimdall.risk.model.binder;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.heimdall.risk.model.TransactionInfo;

/**
 * Bind the transaction info from JSON to model and vice versa.
 *
 */
public class TransactionInfoBinder implements JsonSerializer<TransactionInfo>,
		JsonDeserializer<TransactionInfo> {

	@Override
	public JsonElement serialize(TransactionInfo transactionInfo, Type type,
			JsonSerializationContext context) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add("email", new JsonPrimitive(transactionInfo.email.get()));
		jsonObject.add("first_name", new JsonPrimitive(
				transactionInfo.firstName.get()));
		jsonObject.add("last_name",
				new JsonPrimitive(transactionInfo.lastName.get()));
		jsonObject.add("amount",
				new JsonPrimitive(transactionInfo.amount.get()));
		return jsonObject;
	}

	@Override
	public TransactionInfo deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {

		final JsonObject jsonObject = json.getAsJsonObject();
		final TransactionInfo info = new TransactionInfo();
		try {
			info.email.set(jsonObject.get("email").getAsString());
			info.firstName.set(jsonObject.get("first_name").getAsString());
			info.lastName.set(jsonObject.get("last_name").getAsString());
			info.amount.set(jsonObject.get("amount").getAsDouble());
		} catch (final NumberFormatException e) {
			throw new JsonParseException("Invalid amount");
		} catch (final Exception e) {
			throw new JsonParseException("Invalid input");
		}

		return info;
	}
}

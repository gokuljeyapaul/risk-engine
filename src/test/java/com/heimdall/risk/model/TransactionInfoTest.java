package com.heimdall.risk.model;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heimdall.risk.model.binder.TransactionInfoBinder;

public class TransactionInfoTest {

	@Test
	public void testTransactionInfoHashCode() {
		final TransactionInfo info = new TransactionInfo();
		info.email.set("casper@friendlyghosts.com");
		info.firstName.set("Casper");
		info.lastName.set("McFadden");
		info.amount.set(10.0);
		assertNotNull(info.hashCode());
		assertThat(info.hashCode(), instanceOf(Integer.class));
	}

	@Test
	public void testTransactionInfoEquals() {
		final TransactionInfo info1 = new TransactionInfo();
		info1.email.set("casper@friendlyghosts.com");
		info1.firstName.set("Casper");
		info1.lastName.set("McFadden");
		info1.amount.set(10.0);

		final TransactionInfo info2 = new TransactionInfo();
		info2.email.set("dasper@friendlyghosts.com");
		info2.firstName.set("Casper");
		info2.lastName.set("McFadden");
		info2.amount.set(10.0);

		final TransactionInfo info3 = new TransactionInfo();
		info3.email.set("casper@friendlyghosts.com");
		info3.firstName.set("Dasper");
		info3.lastName.set("McFadden");
		info3.amount.set(10.0);

		assertTrue(info1.equals(info3));
		assertFalse(info1.equals(info2));
		assertTrue(info1.equals(info1));
		assertTrue(info2.equals(info2));
		assertFalse(info1.equals(new String("Test")));
		assertFalse(info2.equals(new String("Test")));

	}

	@Test
	public void testGsonMarshalling() {
		final Gson gson = new GsonBuilder().registerTypeAdapter(
				TransactionInfo.class, new TransactionInfoBinder()).create();
		final TransactionInfo info = new TransactionInfo();
		info.email.set("casper@friendlyghosts.com");
		info.firstName.set("Casper");
		info.lastName.set("McFadden");
		info.amount.set(10.0);
		final String json = gson.toJson(info);
		assertFalse(json == null);
		assertFalse(json.isEmpty());
		assertEquals(
				json,
				"{\"email\":\"casper@friendlyghosts.com\",\"first_name\":\"Casper\",\"last_name\":\"McFadden\",\"amount\":10.0}");
	}

	@Test
	public void testGsonUnmarshalling() {
		final Gson gson = new GsonBuilder().registerTypeAdapter(
				TransactionInfo.class, new TransactionInfoBinder()).create();
		final String json = "{\"email\":\"casper@friendlyghosts.com\",\"first_name\":\"Casper\",\"last_name\":\"McFadden\",\"amount\":10.0}";
		final TransactionInfo info1 = gson
				.fromJson(json, TransactionInfo.class);

		final TransactionInfo info2 = new TransactionInfo();
		info2.email.set("casper@friendlyghosts.com");
		info2.firstName.set("Casper");
		info2.lastName.set("McFadden");
		info2.amount.set(10.0);

		assertTrue(info1.equals(info2));

	}

	@Test
	public void testMapToJson() {
		final Map<String, String> resultMap = new LinkedHashMap<String, String>();
		resultMap.put("NAME", "GOKUL");
		resultMap.put("ORG", "KLARNA AB");
		assertEquals("{\"NAME\":\"GOKUL\",\"ORG\":\"KLARNA AB\"}",
				new Gson().toJson(resultMap));

	}

}

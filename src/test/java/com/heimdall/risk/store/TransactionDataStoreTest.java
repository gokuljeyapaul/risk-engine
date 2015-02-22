package com.heimdall.risk.store;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.heimdall.risk.model.TransactionInfo;

public class TransactionDataStoreTest {

	@Test
	public void testCrud() {
		// Testing create
		final DataStore store = DataStoreFactory
				.getDataStore(TransactionDataStore.TYPE);
		final TransactionInfo info1 = new TransactionInfo();
		info1.email.set("casper@friendlyghosts.com");
		info1.firstName.set("Casper");
		info1.lastName.set("McFadden");
		info1.amount.set(10.0);
		final Integer id1 = store.saveOrUpdate(info1);
		assertNotNull(id1);
		assertThat(info1.hashCode(), instanceOf(Integer.class));

		// Testing create to make sure two different emaild's create two
		// different transactions
		info1.email.set("casper@ghostland.com");
		info1.firstName.set("Casper");
		info1.lastName.set("McFadden");
		info1.amount.set(10.0);
		final Integer id2 = store.saveOrUpdate(info1);
		assertNotNull(id2);
		assertThat(info1.hashCode(), instanceOf(Integer.class));
		assertFalse(id1.equals(id2));

		// Test if create does an update if an email id is already existing
		info1.email.set("casper@ghostland.com");
		info1.firstName.set("Dasper");
		info1.lastName.set("McFadden");
		info1.amount.set(10.0);
		final Integer id3 = store.saveOrUpdate(info1);
		assertNotNull(id3);
		assertThat(info1.hashCode(), instanceOf(Integer.class));
		assertTrue(id3.equals(id2));
		assertFalse(id3.equals(id1));

		// Read the last saved model
		final TransactionInfo info2 = store.read(id3);
		assertTrue(info2.equals(info1));
		assertEquals(info2.firstName.get(), info1.firstName.get());
		assertFalse(info2.firstName.get().equalsIgnoreCase("casper"));

		// Delete the last saved model
		final boolean result = store.delete(info2.hashCode());
		assertTrue(result);
		final TransactionInfo info3 = store.read(id3);
		assertNull(info3);

	}
}

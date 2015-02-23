package com.heimdall.risk.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.heimdall.risk.model.TransactionInfo;
import com.heimdall.risk.store.DataStore;
import com.heimdall.risk.store.DataStoreFactory;
import com.heimdall.risk.store.TransactionDataStore;

public class RulesTest {

	@Test
	public void testNormalScenarios() {
		final DataStore store = DataStoreFactory
				.getDataStore(TransactionDataStore.TYPE);

		// History transaction 50
		// Current transaction 10
		// is Safe : true
		final TransactionInfo info1 = new TransactionInfo();
		info1.email.set("casper@ghostland.com");
		info1.firstName.set("Casper");
		info1.lastName.set("McFadden");
		info1.amount.set(10.0);

		final TransactionInfo historyInfo1 = new TransactionInfo();
		historyInfo1.email.set("casper@ghostland.com");
		historyInfo1.firstName.set("Casper");
		historyInfo1.lastName.set("McFadden");
		historyInfo1.amount.set(50.0);

		final Integer id1 = store.saveOrUpdate(historyInfo1);

		final TransactionRules transactionRules1 = new TransactionRules();
		final boolean result1 = transactionRules1.isSuspiciousTransaction(info1);

		assertTrue(result1);

		// History transactions 50
		// Current transaction 50
		// is Safe : true
		final TransactionInfo info2 = new TransactionInfo();
		info2.email.set("casper@ghostland.com");
		info2.firstName.set("Casper");
		info2.lastName.set("McFadden");
		info2.amount.set(50.0);

		final TransactionRules transactionRules2 = new TransactionRules();
		final boolean result2 = transactionRules2.isSuspiciousTransaction(info2);
		assertTrue(result2);

		// History transactions 50
		// Current transaction 1000
		// is Safe : false
		final TransactionInfo info3 = new TransactionInfo();
		info3.email.set("casper@ghostland.com");
		info3.firstName.set("Casper");
		info3.lastName.set("McFadden");
		info3.amount.set(1000.0);

		final TransactionRules transactionRules3 = new TransactionRules();
		final boolean result3 = transactionRules3.isSuspiciousTransaction(info3);
		assertFalse(result3);
	}

	@Test
	public void testBoundryConditions() {
		final DataStore store = DataStoreFactory
				.getDataStore(TransactionDataStore.TYPE);

		final TransactionInfo historyInfo1 = new TransactionInfo();
		historyInfo1.email.set("dasper@ghostland.com");
		historyInfo1.firstName.set("Dasper");
		historyInfo1.lastName.set("McFadden");
		historyInfo1.amount.set(990.0);

		final Integer id1 = store.saveOrUpdate(historyInfo1);

		// History transaction 990
		// Current transaction 10
		// is Safe : true
		final TransactionInfo info1 = new TransactionInfo();
		info1.email.set("dasper@ghostland.com");
		info1.firstName.set("Dasper");
		info1.lastName.set("McFadden");
		info1.amount.set(10.0);

		final TransactionRules transactionRules1 = new TransactionRules();
		final boolean result1 = transactionRules1.isSuspiciousTransaction(info1);
		assertTrue(result1);

		// History transaction 990
		// Current transaction 11
		// is Safe : true
		final TransactionInfo info2 = new TransactionInfo();
		info2.email.set("dasper@ghostland.com");
		info2.firstName.set("Dasper");
		info2.lastName.set("McFadden");
		info2.amount.set(11.0);

		final TransactionRules transactionRules2 = new TransactionRules();
		final boolean result2 = transactionRules2.isSuspiciousTransaction(info2);
		assertFalse(result2);
	}

}

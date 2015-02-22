package com.heimdall.risk.rule;

import com.heimdall.risk.model.TransactionInfo;
import com.heimdall.risk.model.TransactionStatus;
import com.heimdall.risk.store.DataStore;
import com.heimdall.risk.store.DataStoreFactory;
import com.heimdall.risk.store.TransactionDataStore;

/**
 * Simple transaction rules class.
 *
 */
public class TransactionRules {

	private static Double maxAmountThreshold = 1000.00;

	private static Double minAmountThreshold = 10.00;

	private final synchronized boolean isSafeAmount(
			TransactionInfo currentTransactionInfo,
			TransactionInfo historyTransactionInfo) {
		return (!historyTransactionInfo.status.get().equals(
				TransactionStatus.BLOCKED))
				&& (!historyTransactionInfo.status.get().equals(
						TransactionStatus.REVIEW))
						/*
						 * && (currentTransactionInfo.amount.get() <=
						 * minAmountThreshold)
						 */
						&& (currentTransactionInfo.amount.get() <= maxAmountThreshold)
						&& (historyTransactionInfo.amount.get() <= maxAmountThreshold)
						&& ((currentTransactionInfo.amount.get() + historyTransactionInfo.amount
								.get()) <= maxAmountThreshold);
	}

	private final synchronized boolean isSuspectedAmount(
			TransactionInfo currentTransactionInfo,
			TransactionInfo historyTransactionInfo) {
		return ((historyTransactionInfo.status.get()
				.equals(TransactionStatus.BLOCKED)) || (historyTransactionInfo.status
						.get().equals(TransactionStatus.REVIEW)))
						/*
						 * && ((currentTransactionInfo.amount.get() >=
						 * minAmountThreshold) || (currentTransactionInfo.amount .get()
						 * >= maxAmountThreshold))
						 */
						&& (historyTransactionInfo.amount.get() >= maxAmountThreshold)
						&& ((currentTransactionInfo.amount.get() + historyTransactionInfo.amount
								.get()) > maxAmountThreshold);
	}

	private final synchronized boolean isNewTransactionSafeAmount(
			TransactionInfo currentTransactionInfo) {
		return (currentTransactionInfo.amount.get() <= maxAmountThreshold);
	}

	public synchronized boolean isSafeTransaction(
			TransactionInfo currentTransactionInfo) {
		final DataStore dataStore = DataStoreFactory
				.getDataStore(TransactionDataStore.TYPE);
		final TransactionInfo historyTransactionInfo = dataStore
				.read(currentTransactionInfo.hashCode());
		if (historyTransactionInfo == null) {
			return this.isNewTransactionSafeAmount(currentTransactionInfo);
		} else {
			final boolean isSuspicious = this.isSuspectedAmount(
					currentTransactionInfo, historyTransactionInfo);
			final boolean isSafe = this.isSafeAmount(currentTransactionInfo,
					historyTransactionInfo);
			return isSuspicious ? false : isSafe;
		}
	}

}

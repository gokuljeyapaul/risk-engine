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

	/**
	 * Max threshold amount
	 */
	private static Double maxAmountThreshold = 1000.00;

	/**
	 * Min threshold amount. Not used anywhere as of now. But can be used if we
	 * want a stingy rate limit on each transaction
	 */
	private static Double minAmountThreshold = 10.00;

	private final synchronized boolean isSuspectedAmount(
			TransactionInfo currentTransactionInfo,
			TransactionInfo historyTransactionInfo) {
		return ((historyTransactionInfo.status.get()
				.equals(TransactionStatus.BLOCKED)) || (historyTransactionInfo.status
						.get().equals(TransactionStatus.REVIEW)))
						|| ((historyTransactionInfo.amount.get() >= maxAmountThreshold) || ((currentTransactionInfo.amount
						.get() + historyTransactionInfo.amount.get()) > maxAmountThreshold));
	}

	private final synchronized boolean isNewTransactionSuspectedAmount(
			TransactionInfo currentTransactionInfo) {
		return (currentTransactionInfo.amount.get() > maxAmountThreshold);
	}

	public synchronized boolean isSuspiciousTransaction(
			TransactionInfo currentTransactionInfo) {
		final DataStore dataStore = DataStoreFactory
				.getDataStore(TransactionDataStore.TYPE);
		final TransactionInfo historyTransactionInfo = dataStore
				.read(currentTransactionInfo.hashCode());
		if (historyTransactionInfo == null) {
			return !this
					.isNewTransactionSuspectedAmount(currentTransactionInfo);
		} else {
			return !this.isSuspectedAmount(currentTransactionInfo,
					historyTransactionInfo);
		}
	}

}

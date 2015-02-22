package com.heimdall.risk.store;

/**
 * Data store factory For now, just uses in memory data store. But with a
 * factory and this implementation it can support more data store variants
 */
public class DataStoreFactory {

	public static DataStore getDataStore(String type) {
		switch (type) {
		case TransactionDataStore.TYPE:
			return TransactionDataStore.getInstance();
		default:
			return TransactionDataStore.getInstance();
		}
	}
}

package com.heimdall.risk.store;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.heimdall.risk.model.Model;
import com.heimdall.risk.model.TransactionInfo;

/**
 * A simple in memory data store for processing transactions. 
 * CAUTION : All data is lost during restarts
 *
 */
public final class TransactionDataStore implements DataStore {

	private static volatile DataStore _instance = null;

	private static volatile ConcurrentMap<Integer, Model> _store = new ConcurrentHashMap<Integer, Model>();

	public static final String TYPE = "In-Memory";

	private TransactionDataStore() {
		// To avoid new object creation
	}

	public static DataStore getInstance() {
		DataStore dataStore = _instance;
		if (dataStore == null) {
			synchronized (DataStore.class) {
				dataStore = _instance;
				// Another thread could have created an object while we wait for
				// the lock
				if (dataStore == null) {
					dataStore = new TransactionDataStore();
					_instance = dataStore;
				}
			}
		}
		return dataStore;
	}

	@Override
	public Integer saveOrUpdate(Model model) {
		final TransactionInfo requestModel = (TransactionInfo) model;
		if (_store.containsKey(model.hashCode())) {
			final TransactionInfo storedModel = (TransactionInfo) _store
					.get(model.hashCode());
			requestModel.amount.set(requestModel.amount.get()
					+ storedModel.amount.get());
			requestModel.status.set(storedModel.status.get());
		}
		_store.put(model.hashCode(), requestModel);
		return model.hashCode();
	}

	@Override
	public <T extends Model> T read(Integer id) {
		return (T) _store.get(id);
	}

	@Override
	public boolean delete(Integer id) {
		if (_store.containsKey(id)) {
			_store.remove(id);
			return true;
		}
		return false;
	}
}

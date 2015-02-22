package com.heimdall.risk.request.processor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.validator.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.heimdall.risk.model.TransactionInfo;
import com.heimdall.risk.model.TransactionStatus;
import com.heimdall.risk.model.binder.TransactionInfoBinder;
import com.heimdall.risk.request.validator.DecisionRequestValidator;
import com.heimdall.risk.rule.TransactionRules;
import com.heimdall.risk.store.DataStoreFactory;
import com.heimdall.risk.store.TransactionDataStore;

/**
 * Process Decision requests
 *
 */
public class DecisionRequestProcessor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private DecisionRequestProcessor() {

	}

	public static DecisionRequestProcessor newInstance() {
		System.setProperty("org.mortbay.log.class", "spark.JettyLogger");
		return new DecisionRequestProcessor();
	}

	public synchronized spark.Response processDecisionRequest(
			spark.Request request, spark.Response response) {
		final Map<String, String> responseMap = new LinkedHashMap<String, String>();
		final Gson gson = new GsonBuilder().registerTypeAdapter(
				TransactionInfo.class, new TransactionInfoBinder()).create();
		try {
			final TransactionRules transactionRules = new TransactionRules();
			Integer id = 0;
			final TransactionInfo currentTransactionInfo = gson.fromJson(
					request.body(), TransactionInfo.class);
			final boolean isValidRequest = DecisionRequestValidator
					.newInstance().isValidRequest(currentTransactionInfo);
			if (isValidRequest
					&& transactionRules
							.isSafeTransaction(currentTransactionInfo)) {
				this.logger.info("Inside if");
				currentTransactionInfo.status.set(TransactionStatus.SUCCESS);
				id = DataStoreFactory.getDataStore(TransactionDataStore.TYPE)
						.saveOrUpdate(currentTransactionInfo);
				responseMap.put("accepted", String.valueOf(true));
				responseMap.put("reason", "ok");
			} else {
				this.logger.info("Inside else");
				currentTransactionInfo.status.set(TransactionStatus.BLOCKED);
				id = DataStoreFactory.getDataStore(TransactionDataStore.TYPE)
						.saveOrUpdate(currentTransactionInfo);
				responseMap.put("accepted", String.valueOf(false));
				responseMap.put("reason", "debt");
			}
			response.status(200);
			response.type("application/json");
			response.body(gson.toJson(responseMap));
			return response;
		} catch (ValidatorException | JsonSyntaxException e) {
			e.printStackTrace();
			responseMap.put("accepted", String.valueOf(false));
			responseMap.put("reason", e.getMessage());
			response.status(200);
			response.type("application/json");
			response.body(gson.toJson(responseMap));
			return response;
		} catch (final Exception e) {
			e.printStackTrace();
			responseMap.put("accepted", String.valueOf(false));
			responseMap.put("reason", e.getMessage());
			response.status(500);
			response.type("application/json");
			response.body(gson.toJson(responseMap));
			return response;
		} finally {

		}
	}
}

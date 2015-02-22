package com.heimdall.risk;

import static spark.Spark.post;
import static spark.SparkBase.port;

import com.heimdall.risk.request.processor.DecisionRequestProcessor;

/**
 * Http Request Response handler
 *
 */
public class App {
	public static void main(String[] args) {
		port(9000);
		post("/v1/decision", "application/json", (request, response) -> {
			return DecisionRequestProcessor.newInstance()
					.processDecisionRequest(request, response).body();
		});

	}
}

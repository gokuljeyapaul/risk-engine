package com.heimdall.risk.model;

/**
 * Enum holding transaction status
 *
 * NEW- Whenever a transaction comes in for the first time. 
 * SUCCESS - When a new transaction succeeds after applying all rules. 
 * BLOCKED - The transaction gets blocked, once an transaction for an email ID is blocked 
 * no other transaction for that email ID goes through. 
 * REVIEW - Not used as of now, but will be
 * useful for rate limiting features
 */
public enum TransactionStatus {

	NEW, SUCCESS, BLOCKED, REVIEW

}

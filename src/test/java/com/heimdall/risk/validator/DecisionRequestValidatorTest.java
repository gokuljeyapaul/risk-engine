package com.heimdall.risk.validator;

import static org.junit.Assert.assertTrue;

import org.apache.commons.validator.ValidatorException;
import org.junit.Test;

import com.heimdall.risk.model.TransactionInfo;
import com.heimdall.risk.request.validator.DecisionRequestValidator;

public class DecisionRequestValidatorTest {

	@Test
	public void validateGoodRequest() {
		final TransactionInfo info = new TransactionInfo();
		info.email.set("casper@friendlyghosts.com");
		info.firstName.set("Casper");
		info.lastName.set("McFadden");
		info.amount.set(10.0);

		try {
			final boolean result = DecisionRequestValidator.newInstance()
					.isValidRequest(info);
			assertTrue(result);
		} catch (final ValidatorException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = org.apache.commons.validator.ValidatorException.class)
	public void validateTransactionInfoWithoutEmail()
			throws org.apache.commons.validator.ValidatorException {
		final TransactionInfo info = new TransactionInfo();
		info.email.set("");
		info.firstName.set("Casper");
		info.lastName.set("McFadden");
		info.amount.set(10.0);
		final boolean result = DecisionRequestValidator.newInstance()
				.isValidRequest(info);
	}

	@Test(expected = org.apache.commons.validator.ValidatorException.class)
	public void validateTransactionInfoWithoutFirstName()
			throws org.apache.commons.validator.ValidatorException {
		final TransactionInfo info = new TransactionInfo();
		info.email.set("casper@ghostland.com");
		info.firstName.set("");
		info.lastName.set("McFadden");
		info.amount.set(10.0);
		final boolean result = DecisionRequestValidator.newInstance()
				.isValidRequest(info);
	}

	@Test(expected = org.apache.commons.validator.ValidatorException.class)
	public void validateTransactionInfoWithoutLastName()
			throws org.apache.commons.validator.ValidatorException {
		final TransactionInfo info = new TransactionInfo();
		info.email.set("casper@ghostland.com");
		info.firstName.set("Casper");
		info.lastName.set("");
		info.amount.set(10.0);
		final boolean result = DecisionRequestValidator.newInstance()
				.isValidRequest(info);
	}

	@Test(expected = org.apache.commons.validator.ValidatorException.class)
	public void validateTransactionInfoWithoutAmount()
			throws org.apache.commons.validator.ValidatorException {
		final TransactionInfo info = new TransactionInfo();
		info.email.set("casper@ghostland.com");
		info.firstName.set("Casper");
		info.lastName.set("McFadden");
		info.amount.set(null);
		final boolean result = DecisionRequestValidator.newInstance()
				.isValidRequest(info);
	}

	@Test(expected = org.apache.commons.validator.ValidatorException.class)
	public void validateTransactionInfoWithInvalidEmail()
			throws org.apache.commons.validator.ValidatorException {

		// Not recreating a whole bunch of tests as apache validator is already
		// been tested
		// Refer :
		// http://commons.apache.org/proper/commons-validator/xref-test/org/apache/commons/validator/routines/EmailValidatorTest.html
		final TransactionInfo info = new TransactionInfo();
		info.email.set("casper+personal@gmail@.com");
		info.firstName.set("Casper");
		info.lastName.set("McFadden");
		info.amount.set(10.0);
		final boolean result = DecisionRequestValidator.newInstance()
				.isValidRequest(info);
	}
}

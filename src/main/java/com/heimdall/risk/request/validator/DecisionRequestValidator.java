package com.heimdall.risk.request.validator;

import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.EmailValidator;

import com.heimdall.risk.model.TransactionInfo;

/**
 * Validate incoming request parameters
 *
 *
 */
public class DecisionRequestValidator {

	private DecisionRequestValidator() {

	}

	public static DecisionRequestValidator newInstance() {
		return new DecisionRequestValidator();
	}

	public boolean isValidRequest(TransactionInfo transactionInfo)
			throws ValidatorException {
		if ((transactionInfo.email.get() == null)
				|| (transactionInfo.email.get().trim().length() == 0)) {
			throw new ValidatorException("Email is required");
		}
		if ((transactionInfo.firstName.get() == null)
				|| (transactionInfo.firstName.get().trim().length() == 0)) {
			throw new ValidatorException("First name is required");
		}
		if ((transactionInfo.lastName.get() == null)
				|| (transactionInfo.lastName.get().trim().length() == 0)) {
			throw new ValidatorException("Last name is required");
		}
		if ((transactionInfo.amount.get() == null)
				|| (transactionInfo.amount.get() == 0)) {
			throw new ValidatorException("Amount is required and can't be zero");
		}

		final EmailValidator emailValidator = EmailValidator.getInstance();
		if (!emailValidator.isValid(transactionInfo.email.get())) {
			throw new ValidatorException("Invalid email");
		}

		final DoubleValidator doubleValidator = DoubleValidator.getInstance();
		if (!doubleValidator.isValid(String.valueOf(transactionInfo.amount
				.get()))) {
			throw new ValidatorException("Invalid amount");
		}
		if (!doubleValidator.maxValue(transactionInfo.amount.get(), 1000)) {
			throw new ValidatorException("amount");
		}
		return true;
	}

}

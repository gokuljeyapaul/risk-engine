package com.heimdall.risk.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Transaction info model. Email ID is used as the primary identifier for the
 * transaction info
 *
 */
public class TransactionInfo implements Model {

	/**
	 * Email of the customer, assumed as the main reference(unique) for the
	 * transaction.
	 */
	public Property<String> email = new Property<String>();

	/**
	 * First name of the customer
	 */
	public Property<String> firstName = new Property<String>();

	/**
	 * Last name of the customer
	 */
	public Property<String> lastName = new Property<String>();

	/**
	 * Current transaction amount
	 */
	public Property<Double> amount = new Property<Double>();

	/**
	 * Internal status of the transaction
	 */
	public Property<TransactionStatus> status = new Property<TransactionStatus>();

	public TransactionInfo() {
		this.status.set(TransactionStatus.NEW);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.email.get()).toHashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof TransactionInfo) {
			final TransactionInfo info = (TransactionInfo) object;
			return new EqualsBuilder().append(info.email.get(),
					this.email.get()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				new RecursiveToStringStyle());
	}
}

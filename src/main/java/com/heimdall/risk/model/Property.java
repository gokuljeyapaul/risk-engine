package com.heimdall.risk.model;

/**
 * Using a simple property class instead of getter setters
 *
 * @param <T>
 */
public class Property<T> {

	/**
	 * The property value
	 */
	private T value;

	/**
	 * Set the value for the property
	 *
	 * @param value
	 */
	public void set(T value) {
		this.value = value;
	}

	/**
	 * Get the value of the property
	 * 
	 * @return
	 */
	public T get() {
		return this.value;
	}
}

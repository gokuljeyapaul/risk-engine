package com.heimdall.risk.model;

/**
 * Using a simple property class instead of getter setters
 *
 * @param <T>
 */
public class Property<T> {
	private T value;

	public void set(T value) {
		this.value = value;
	}

	public T get() {
		return this.value;
	}
}

package com.veterinaria.exceptions;

public class StringLengthException extends VeterinariaException {

	private static final long serialVersionUID = -3191692811696627195L;

	public StringLengthException(String message) {
		super(message);
	}
}
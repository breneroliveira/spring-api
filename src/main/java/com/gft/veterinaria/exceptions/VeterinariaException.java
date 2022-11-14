package com.gft.veterinaria.exceptions;

public class VeterinariaException extends RuntimeException {

	private static final long serialVersionUID = 7961392734644977870L;

	private String message;

	public VeterinariaException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
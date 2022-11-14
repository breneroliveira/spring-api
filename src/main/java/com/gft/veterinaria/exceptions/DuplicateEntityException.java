package com.gft.veterinaria.exceptions;

public class DuplicateEntityException extends VeterinariaException {

	private static final long serialVersionUID = -5374342716892008859L;

	public DuplicateEntityException(String message) {
		super(message);
	}
}
package com.veterinaria.exceptions;

public class EntityNotFoundException extends VeterinariaException {

	private static final long serialVersionUID = 1517199347030263295L;

	public EntityNotFoundException(String message) {
		super(message);
	}
}
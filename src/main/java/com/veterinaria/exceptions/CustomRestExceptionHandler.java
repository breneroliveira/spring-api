package com.veterinaria.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.veterinaria.dto.ApiErrorDTO;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler( { VeterinariaException.class } )
	public ResponseEntity<ApiErrorDTO> handleVeterinariaException(VeterinariaException ex, WebRequest request) {
		
		String error = "Erro no sistema";
		
		ApiErrorDTO apiError = new ApiErrorDTO(ex.getMessage(), error, HttpStatus.INTERNAL_SERVER_ERROR);
		
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@ExceptionHandler( { EntityNotFoundException.class } )
	public ResponseEntity<ApiErrorDTO> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
		
		String error = "Recurso não encontrado";
		
		ApiErrorDTO apiError = new ApiErrorDTO(ex.getMessage(), error, HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@ExceptionHandler( { SQLIntegrityConstraintViolationException.class } )
	public ResponseEntity<ApiErrorDTO> handleConstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
		
		String error = "Recurso não disponível";
		
		ApiErrorDTO apiError = new ApiErrorDTO(ex.getMessage(), error, HttpStatus.INTERNAL_SERVER_ERROR);
		
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}
}
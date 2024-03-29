package com.veterinaria.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veterinaria.dtos.AutenticacaoDTO;
import com.veterinaria.dtos.TokenDTO;
import com.veterinaria.servicesImpl.AutenticacaoServiceImpl;

@RestController
@RequestMapping("v1/auth")
public class AutenticacaoController {

	private final AutenticacaoServiceImpl autenticacaoServiceImpl;

	public AutenticacaoController(AutenticacaoServiceImpl autenticacaoServiceImpl) {
		this.autenticacaoServiceImpl = autenticacaoServiceImpl;
	}
	
	@PostMapping
	public ResponseEntity<TokenDTO> autenticar(@RequestBody AutenticacaoDTO authForm) {
		
		try {
			return ResponseEntity.ok(autenticacaoServiceImpl.autenticar(authForm));
		} catch (AuthenticationException ae) {
			ae.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
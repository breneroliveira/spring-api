package com.veterinaria.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veterinaria.dto.auth.AutenticacaoDTO;
import com.veterinaria.dto.auth.TokenDTO;
import com.veterinaria.services.AutenticacaoService;

@RestController
@RequestMapping("v1/auth")
public class AutenticacaoController {

	private final AutenticacaoService autenticacaoService;

	public AutenticacaoController(AutenticacaoService autenticacaoService) {
		this.autenticacaoService = autenticacaoService;
	}
	
	@PostMapping
	public ResponseEntity<TokenDTO> autenticar(@RequestBody AutenticacaoDTO authForm) {
		
		try {
			return ResponseEntity.ok(autenticacaoService.autenticar(authForm));
		} catch (AuthenticationException ae) {
			ae.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
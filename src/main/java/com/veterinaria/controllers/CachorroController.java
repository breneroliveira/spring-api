package com.veterinaria.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veterinaria.mappers.CachorroMapper;
import com.veterinaria.dto.cachorro.ConsultaCachorroDTO;
import com.veterinaria.dto.cachorro.RegistroCachorroDTO;
import com.veterinaria.entities.Cachorro;
import com.veterinaria.services.CachorroService;

@RestController
@RequestMapping("v1/cachorros")
public class CachorroController {

	private final CachorroService cachorroService;

	public CachorroController(CachorroService cachorroService) {
		this.cachorroService = cachorroService;
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<Page<ConsultaCachorroDTO>> buscarTodosOsCachorros(@PageableDefault Pageable pageable) {
		
		return ResponseEntity.ok(cachorroService.listarTodosOsCachorros(pageable).map(CachorroMapper::fromEntity)); 
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<ConsultaCachorroDTO> salvarCachorro(@RequestBody RegistroCachorroDTO dto) {
		
		Cachorro cachorro = cachorroService.salvarCachorro(CachorroMapper.fromDTO(dto));
		
		return ResponseEntity.ok(CachorroMapper.fromEntity(cachorro));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<ConsultaCachorroDTO> buscarCachorro(@PathVariable Long id) {

		Cachorro cachorro = cachorroService.buscarCachorro(id);
		
		return ResponseEntity.ok(CachorroMapper.fromEntity(cachorro));
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<ConsultaCachorroDTO> atualizarCachorro(@RequestBody RegistroCachorroDTO dto,
			@PathVariable Long id) {
				
		Cachorro cachorro = cachorroService.atualizarCachorro(CachorroMapper.fromDTO(dto), id);
		
		return ResponseEntity.ok(CachorroMapper.fromEntity(cachorro));
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<ConsultaCachorroDTO> excluirCachorro(@PathVariable Long id) {
		
			cachorroService.excluirCachorro(id);
			
			return ResponseEntity.ok().build();
	}
}
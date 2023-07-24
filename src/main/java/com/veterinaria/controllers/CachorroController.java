package com.veterinaria.controllers;

import com.veterinaria.dtos.requests.CachorroRequestDTO;
import com.veterinaria.dtos.responses.CachorroResponseDTO;
import com.veterinaria.entities.Cachorro;
import com.veterinaria.mappers.CachorroMapper;
import com.veterinaria.services.CachorroService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/cachorros")
public class CachorroController {

	private final CachorroService cachorroService;

	public CachorroController(CachorroService cachorroService) {
		this.cachorroService = cachorroService;
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<Page<CachorroResponseDTO>> buscarTodosOsCachorros(@PageableDefault Pageable pageable) {
		
		return ResponseEntity.ok(cachorroService.listarTodosOsCachorros(pageable).map(CachorroMapper::fromEntity));
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<CachorroResponseDTO> salvarCachorro(@RequestBody CachorroRequestDTO dto) {
		
		Cachorro cachorro = cachorroService.salvarCachorro(CachorroMapper.fromDTO(dto));
		
		return ResponseEntity.ok(CachorroMapper.fromEntity(cachorro));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<CachorroResponseDTO> buscarCachorro(@PathVariable Long id) {

		Cachorro cachorro = cachorroService.buscarCachorro(id);
		
		return ResponseEntity.ok(CachorroMapper.fromEntity(cachorro));
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<CachorroResponseDTO> atualizarCachorro(@RequestBody CachorroRequestDTO dto,
                                                                 @PathVariable Long id) {
				
		Cachorro cachorro = cachorroService.atualizarCachorro(CachorroMapper.fromDTO(dto), id);
		
		return ResponseEntity.ok(CachorroMapper.fromEntity(cachorro));
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<CachorroResponseDTO> excluirCachorro(@PathVariable Long id) {

		cachorroService.excluirCachorro(id);
			
		return ResponseEntity.ok().build();
	}
}
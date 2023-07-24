package com.veterinaria.controllers;

import com.veterinaria.dtos.requests.AtendimentoRequestDTO;
import com.veterinaria.dtos.responses.AtendimentoResponseDTO;
import com.veterinaria.entities.Atendimento;
import com.veterinaria.mappers.AtendimentoMapper;
import com.veterinaria.services.AtendimentoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/atendimentos")
public class AtendimentoController {

	private final AtendimentoService atendimentoService;

	public AtendimentoController(AtendimentoService atendimentoService) {
		this.atendimentoService = atendimentoService;
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<Page<AtendimentoResponseDTO>> buscarTodosOsAtendimentos(@PageableDefault Pageable pageable) {
		
		return ResponseEntity.ok(atendimentoService.listarTodosOsAtendimentos(pageable).map(AtendimentoMapper::fromEntity));
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<AtendimentoResponseDTO> salvarAtendimento(@RequestBody AtendimentoRequestDTO dto) {
		
		Atendimento atendimento = atendimentoService.salvarAtendimento(AtendimentoMapper.fromDTO(dto));
		
		return ResponseEntity.ok(AtendimentoMapper.fromEntity(atendimento));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<AtendimentoResponseDTO> buscarAtendimento(@PathVariable Long id) {

		Atendimento atendimento = atendimentoService.buscarAtendimento(id);
		
		return ResponseEntity.ok(AtendimentoMapper.fromEntity(atendimento));
	}
}
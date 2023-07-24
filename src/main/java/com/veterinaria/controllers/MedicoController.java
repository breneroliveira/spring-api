package com.veterinaria.controllers;

import com.veterinaria.dtos.requests.MedicoRequestDTO;
import com.veterinaria.dtos.responses.MedicoResponseDTO;
import com.veterinaria.entities.Medico;
import com.veterinaria.mappers.MedicoMapper;
import com.veterinaria.services.MedicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/medicos")
public class MedicoController {

	private final MedicoService medicoService;

	public MedicoController(MedicoService medicoService) {
		this.medicoService = medicoService;
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<Page<MedicoResponseDTO>> buscarTodosOsMedicos(@PageableDefault Pageable pageable) {
		
		return ResponseEntity.ok(medicoService.listarTodosOsMedicos(pageable).map(MedicoMapper::fromEntity));
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<MedicoResponseDTO> salvarMedico(@RequestBody MedicoRequestDTO dto) {
		
		Medico medico = medicoService.salvarMedico(MedicoMapper.fromDTO(dto));
		
		return ResponseEntity.ok(MedicoMapper.fromEntity(medico));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<MedicoResponseDTO> buscarMedico(@PathVariable Long id) {

		Medico medico = medicoService.buscarMedico(id);
		
		return ResponseEntity.ok(MedicoMapper.fromEntity(medico));
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<MedicoResponseDTO> atualizarMedico(@RequestBody MedicoRequestDTO dto,
                                                             @PathVariable Long id) {
				
		Medico medico = medicoService.atualizarMedico(MedicoMapper.fromDTO(dto), id);
		
		return ResponseEntity.ok(MedicoMapper.fromEntity(medico));
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<MedicoResponseDTO> excluirMedico(@PathVariable Long id) {

		medicoService.excluirMedico(id);
			
		return ResponseEntity.ok().build();
	}
}
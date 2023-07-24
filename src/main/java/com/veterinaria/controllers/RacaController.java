package com.veterinaria.controllers;

import com.veterinaria.dtos.requests.RacaRequestDTO;
import com.veterinaria.dtos.responses.ImagemResponseDTO;
import com.veterinaria.dtos.responses.RacaResponseDTO;
import com.veterinaria.entities.Raca;
import com.veterinaria.mappers.RacaMapper;
import com.veterinaria.services.RacaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/racas")
public class RacaController {

	private final RacaService racaService;

	public RacaController(RacaService racaService) {
		this.racaService = racaService;
	}
	
	@GetMapping("dog-api")
	public ResponseEntity<List<Raca>> getRacaDogApi() {
		List<Raca> raca = this.racaService.getRacaDogApi();
		 
		 return ResponseEntity.ok(raca);
	}
	
	@GetMapping("dog-api/{nomeRaca}")
	public ResponseEntity<List<Raca>> buscarRacaDogApi(@PathVariable String nomeRaca) {
		List<Raca> raca = this.racaService.buscarRacaDogApi(nomeRaca);
		 
		 return ResponseEntity.ok(raca);
	}
	
	@GetMapping("dog-api/imagens")
	public ResponseEntity<List<ImagemResponseDTO>> getImagemDogApi() {
		List<ImagemResponseDTO> imagem = this.racaService.getImagemDogApi();
		 
		 return ResponseEntity.ok(imagem);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<Page<RacaResponseDTO>> buscarTodasAsRacas(@PageableDefault Pageable pageable) {
		
		return ResponseEntity.ok(racaService.listarTodosAsRacas(pageable).map(RacaMapper::fromEntity));
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<RacaResponseDTO> salvarRaca(@RequestBody RacaRequestDTO dto) {
		
		Raca raca = racaService.salvarRaca(RacaMapper.fromDTO(dto));
		
		return ResponseEntity.ok(RacaMapper.fromEntity(raca));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<RacaResponseDTO> buscarRaca(@PathVariable Long id) {

		Raca raca = racaService.buscarRaca(id);
		
		return ResponseEntity.ok(RacaMapper.fromEntity(raca));
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<RacaResponseDTO> atualizarRaca(@RequestBody RacaRequestDTO dto,
														 @PathVariable Long id) {
				
		Raca raca = racaService.atualizarRaca(RacaMapper.fromDTO(dto), id);
		
		return ResponseEntity.ok(RacaMapper.fromEntity(raca));
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<RacaResponseDTO> excluirRaca(@PathVariable Long id) {

		racaService.excluirRaca(id);
			
		return ResponseEntity.ok().build();
	}
}
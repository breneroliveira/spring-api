package com.veterinaria.servicesImpl;

import com.veterinaria.dtos.responses.ImagemResponseDTO;
import com.veterinaria.entities.Raca;
import com.veterinaria.exceptions.DuplicateEntityException;
import com.veterinaria.exceptions.EntityNotFoundException;
import com.veterinaria.repositories.RacaRepository;
import com.veterinaria.services.RacaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RacaServiceImpl implements RacaService {
	
	@Autowired
	private WebClient webClient;

	private final RacaRepository racaRepository;

	public RacaServiceImpl(RacaRepository racaRepository) {
		this.racaRepository = racaRepository;
	}
	
	public List<Raca> getRacaDogApi() {
		
		Mono<List<Raca>> monoRaca =  this.webClient
			.method(HttpMethod.GET)
			.uri("/breeds")
			.retrieve()
			.bodyToFlux(Raca.class).collect(Collectors.toList());
		
		List<Raca> raca = monoRaca.block();
		
		return raca;
	}
	
	public List<Raca> buscarRacaDogApi(String nomeRaca) {
		
		Mono<List<Raca>> monoRaca = this.webClient
			.method(HttpMethod.GET)
			.uri("/breeds/search?q={nomeRaca}", nomeRaca)
			.retrieve()
			.bodyToFlux(Raca.class).collect(Collectors.toList());
		
		List<Raca> raca = monoRaca.block();
		
		return raca;
	}
	
	public List<ImagemResponseDTO> getImagemDogApi() {
		
		Mono<List<ImagemResponseDTO>> monoRaca =  this.webClient
			.method(HttpMethod.GET)
			.uri("/images/search")
			.retrieve()
			.bodyToFlux(ImagemResponseDTO.class).collect(Collectors.toList());
		
		List<ImagemResponseDTO> imagem = monoRaca.block();
		
		return imagem;
	}
	
	public Raca salvarRaca(Raca raca) throws DuplicateEntityException {
		
		Optional<Raca> optional = racaRepository.findByNome(raca.getNome());
		
		if(optional.isEmpty())
			return racaRepository.save(raca);
		else
			throw new DuplicateEntityException("Raça já cadastrada.");
	}
	
	public Page<Raca> listarTodosAsRacas(Pageable pageable) {
		
		return racaRepository.findAll(pageable);
	}
	
	public Raca buscarRaca(Long id) {

		Optional<Raca> optional = racaRepository.findById(id);
		
		return optional.orElseThrow(() -> new EntityNotFoundException("Raça não encontrada."));
	}
	
	public Raca atualizarRaca(Raca raca, Long id) {
		
		Raca racaOriginal = this.buscarRaca(id);
		
		raca.setId(racaOriginal.getId());
		
		return racaRepository.save(raca);
	}
	
	public void excluirRaca(Long id) {
		Raca racaOriginal = this.buscarRaca(id);
		
		racaRepository.delete(racaOriginal);
	}
}
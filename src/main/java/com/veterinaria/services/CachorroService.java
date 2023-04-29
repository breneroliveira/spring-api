package com.veterinaria.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.veterinaria.entities.Cachorro;
import com.veterinaria.exceptions.DuplicateEntityException;
import com.veterinaria.exceptions.EntityNotFoundException;
import com.veterinaria.exceptions.StringLengthException;
import com.veterinaria.repositories.CachorroRepository;
import com.veterinaria.repositories.ClienteRepository;
import com.veterinaria.repositories.RacaRepository;

@Service
public class CachorroService {

	private final CachorroRepository cachorroRepository;
	private final ClienteRepository clienteRepository;
	private final RacaRepository racaRepository;
	
	public CachorroService(CachorroRepository cachorroRepository, ClienteRepository clienteRepository,
						   RacaRepository racaRepository) {
		this.cachorroRepository = cachorroRepository;
		this.clienteRepository = clienteRepository;
		this.racaRepository = racaRepository;
	}

	public Cachorro salvarCachorro(Cachorro cachorro) {
		
		Optional<Cachorro> optional = cachorroRepository.findByNumeroRegistro(cachorro.getNumeroRegistro());
		
		if(cachorro.getNumeroRegistro().length() == 5) {
			if(racaRepository.findById(cachorro.getRaca().getId()).isEmpty()) {
				throw new EntityNotFoundException("Raça inexistente.");
			} else {
				if(clienteRepository.findById(cachorro.getCliente().getId()).isEmpty()) {
					throw new EntityNotFoundException("Cliente inexistente.");
				} else {
					if(optional.isEmpty())
						return cachorroRepository.save(cachorro);
					else
						throw new DuplicateEntityException("Cachorro já cadastrado.");
				}
			}
		} else
			throw new StringLengthException("O Número de Registro deve conter 5 dígitos.");
	}
	
	public Page<Cachorro> listarTodosOsCachorros(Pageable pageable) {
		
		return cachorroRepository.findAll(pageable);
	}
	
	public Cachorro buscarCachorro(Long id) {

		Optional<Cachorro> optional = cachorroRepository.findById(id);
		
		return optional.orElseThrow(() -> new EntityNotFoundException("Cachorro não encontrado."));
	}
	
	public Page<Cachorro> buscaCachorroPorCliente(Pageable pageable, String nomeCliente) throws EntityNotFoundException {
		
		Page<Cachorro> optional = cachorroRepository.findByClienteNomeContains(pageable, nomeCliente);
		
		if(optional != null)
			return optional;
		else
			throw new EntityNotFoundException("Cachorro não encontrada.");
	}
	
	public Cachorro atualizarCachorro(Cachorro cachorro, Long id) {
		
		Cachorro cachorroOriginal = this.buscarCachorro(id);
		
		cachorro.setId(cachorroOriginal.getId());
		
		Optional<Cachorro> optional = cachorroRepository.findByNumeroRegistro(cachorro.getNumeroRegistro());
		
		if(cachorro.getNumeroRegistro().length() == 5) {
			if(racaRepository.findById(cachorro.getRaca().getId()).isEmpty()) {
				throw new EntityNotFoundException("Raça inexistente.");
			} else {
				if(clienteRepository.findById(cachorro.getCliente().getId()).isEmpty()) {
					throw new EntityNotFoundException("Cliente inexistente.");
				} else {
					if(optional.isEmpty())
						return cachorroRepository.save(cachorro);
					else
						throw new DuplicateEntityException("Cachorro já cadastrado.");
				}
			}
		} else
			throw new StringLengthException("O Número de Registro deve conter 5 dígitos.");
	}
	
	public void excluirCachorro(Long id) {
		Cachorro cachorroOriginal = this.buscarCachorro(id);
		
		cachorroRepository.delete(cachorroOriginal);
	}
}
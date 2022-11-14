package com.gft.veterinaria.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gft.veterinaria.entities.Atendimento;
import com.gft.veterinaria.entities.Cachorro;
import com.gft.veterinaria.exceptions.DuplicateEntityException;
import com.gft.veterinaria.exceptions.EntityNotFoundException;
import com.gft.veterinaria.exceptions.StringLengthException;
import com.gft.veterinaria.repositories.AtendimentoRepository;
import com.gft.veterinaria.repositories.CachorroRepository;
import com.gft.veterinaria.repositories.MedicoRepository;

@Service
public class AtendimentoService {

	private final AtendimentoRepository atendimentoRepository;
	private final MedicoRepository medicoRepository;
	private final CachorroRepository cachorroRepository;

	public AtendimentoService(AtendimentoRepository atendimentoRepository, CachorroRepository cachorroRepository,
						      MedicoRepository medicoRepository) {
		this.atendimentoRepository = atendimentoRepository;
		this.cachorroRepository = cachorroRepository;
		this.medicoRepository = medicoRepository;
	}
	
	public Cachorro buscarCachorro(Long id) {

		Optional<Cachorro> optional = cachorroRepository.findById(id);
		
		return optional.orElseThrow(() -> new EntityNotFoundException("Cachorro não encontrado."));
	}
	
	public Atendimento salvarAtendimento(Atendimento atendimento) {
		
		Optional<Atendimento> optional = atendimentoRepository.findByTicket(atendimento.getTicket());
		
		if(atendimento.getTicket().length() == 4) {
			if(medicoRepository.findById(atendimento.getMedico().getId()).isEmpty()) {
				throw new EntityNotFoundException("Médico inexistente.");
			} else {
				if(cachorroRepository.findById(atendimento.getCachorro().getId()).isEmpty()) {
					throw new EntityNotFoundException("Cachorro inexistente.");
				} else {
					if(optional.isEmpty()) {
						return atendimentoRepository.save(atendimento);
					} else {
						throw new DuplicateEntityException("Atendimento já cadastrado.");
					}
				}
			}
			
		} else
			throw new StringLengthException("O Ticket deve conter 4 dígitos.");
	}
	
	public Page<Atendimento> listarTodosOsAtendimentos(Pageable pageable) {
		
		return atendimentoRepository.findAll(pageable);
	}
	
	public Atendimento buscarAtendimento(Long id) {

		Optional<Atendimento> optional = atendimentoRepository.findById(id);
		
		return optional.orElseThrow(() -> new EntityNotFoundException("Atendimento não encontrado."));
	}
}
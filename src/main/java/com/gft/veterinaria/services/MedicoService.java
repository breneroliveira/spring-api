package com.gft.veterinaria.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gft.veterinaria.entities.Medico;
import com.gft.veterinaria.exceptions.DuplicateEntityException;
import com.gft.veterinaria.exceptions.EntityNotFoundException;
import com.gft.veterinaria.exceptions.StringLengthException;
import com.gft.veterinaria.repositories.MedicoRepository;

@Service
public class MedicoService {

	private final MedicoRepository medicoRepository;

	public MedicoService(MedicoRepository medicoRepository) {
		this.medicoRepository = medicoRepository;
	}
	
	public Medico salvarMedico(Medico medico) throws DuplicateEntityException {
		
		Optional<Medico> optional = medicoRepository.findByCrmv(medico.getCrmv());
		
		if(medico.getCrmv().length() == 5) {
			if(medico.getTelefone().length() == 11) {
				if(medico.getEndereco().getCep().length() == 8) {
					if(optional.isEmpty())
						return medicoRepository.save(medico);
					else
						throw new DuplicateEntityException("Médico já cadastrado.");
				} else {
					throw new StringLengthException("O CEP deve conter 8 dígitos.");
				}
			} else {
				throw new StringLengthException("O Telefone deve conter 11 dígitos.");
			}
		} else
			throw new StringLengthException("O CRMV deve conter 5 dígitos.");
	}
	
	public Page<Medico> listarTodosOsMedicos(Pageable pageable) {
		
		return medicoRepository.findAll(pageable);
	}
	
	public Medico buscarMedico(Long id) {

		Optional<Medico> optional = medicoRepository.findById(id);
		
		return optional.orElseThrow(() -> new EntityNotFoundException("Médico não encontrado."));
	}
	
	public Medico atualizarMedico(Medico medico, Long id) {
		
		Medico medicoOriginal = this.buscarMedico(id);
		
		medico.setId(medicoOriginal.getId());
		
		Optional<Medico> optional = medicoRepository.findByCrmv(medico.getCrmv());
		
		if(medico.getCrmv().length() == 5) {
			if(medico.getTelefone().length() == 11) {
				if(medico.getEndereco().getCep().length() == 8) {
					if(optional.isEmpty())
						return medicoRepository.save(medico);
					else
						throw new DuplicateEntityException("Médico já cadastrado.");
				} else {
					throw new StringLengthException("O CEP deve conter 8 dígitos.");
				}
			} else {
				throw new StringLengthException("O Telefone deve conter 11 dígitos.");
			}
		} else
			throw new StringLengthException("O CRMV deve conter 5 dígitos.");
	}
	
	public void excluirMedico(Long id) throws DuplicateEntityException {
		Medico medicoOriginal = this.buscarMedico(id);
		
		medicoRepository.delete(medicoOriginal);
	}
}
package com.veterinaria.servicesImpl;

import com.veterinaria.entities.Medico;
import com.veterinaria.exceptions.DuplicateEntityException;
import com.veterinaria.exceptions.EntityNotFoundException;
import com.veterinaria.exceptions.StringLengthException;
import com.veterinaria.repositories.MedicoRepository;
import com.veterinaria.services.MedicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicoServiceImpl implements MedicoService {

	private final MedicoRepository medicoRepository;

	public MedicoServiceImpl(MedicoRepository medicoRepository) {
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
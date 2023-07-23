package com.veterinaria.mappers;

import com.veterinaria.dtos.ConsultaMedicoDTO;
import com.veterinaria.dtos.RegistroMedicoDTO;
import com.veterinaria.entities.Medico;

public class MedicoMapper {

	public static Medico fromDTO(RegistroMedicoDTO dto) {
		return new Medico(null, dto.getCrmv(), dto.getNome(), 
						   dto.getTelefone(), dto.getDataNascimento(),
						   EnderecoMapper.fromDTO(dto.getEndereco()), 
						   EspecialidadeMapper.fromDTO(dto.getEspecialidade()), dto.getAtendimentos());
	}
	
	public static ConsultaMedicoDTO fromEntity(Medico medico) {
		return new ConsultaMedicoDTO(medico.getId(), medico.getCrmv(), medico.getNome(), 
						   medico.getTelefone(), medico.getDataNascimento(),
						   EnderecoMapper.fromEntity(medico.getEndereco()), 
						   EspecialidadeMapper.fromEntity(medico.getEspecialidade()), medico.getAtendimentos());
	}
}
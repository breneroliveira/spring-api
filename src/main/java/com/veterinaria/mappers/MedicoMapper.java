package com.veterinaria.mappers;

import com.veterinaria.dtos.responses.MedicoResponseDTO;
import com.veterinaria.dtos.requests.MedicoRequestDTO;
import com.veterinaria.entities.Medico;

public class MedicoMapper {

	public static Medico fromDTO(MedicoRequestDTO dto) {
		return new Medico(null, dto.getCrmv(), dto.getNome(), 
						   dto.getTelefone(), dto.getDataNascimento(),
						   EnderecoMapper.fromDTO(dto.getEndereco()), 
						   EspecialidadeMapper.fromDTO(dto.getEspecialidade()), dto.getAtendimentos());
	}
	
	public static MedicoResponseDTO fromEntity(Medico medico) {
		return new MedicoResponseDTO(medico.getId(), medico.getCrmv(), medico.getNome(),
						   medico.getTelefone(), medico.getDataNascimento(),
						   EnderecoMapper.fromEntity(medico.getEndereco()), 
						   EspecialidadeMapper.fromEntity(medico.getEspecialidade()), medico.getAtendimentos());
	}
}
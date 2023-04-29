package com.veterinaria.dto.medico;

import com.veterinaria.dto.endereco.EnderecoMapper;
import com.veterinaria.dto.especialidade.EspecialidadeMapper;
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
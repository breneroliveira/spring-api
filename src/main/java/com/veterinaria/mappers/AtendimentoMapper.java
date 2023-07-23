package com.veterinaria.mappers;

import com.veterinaria.dtos.responses.AtendimentoResponseDTO;
import com.veterinaria.dtos.requests.AtendimentoRequestDTO;
import com.veterinaria.entities.Atendimento;

public class AtendimentoMapper {

	public static Atendimento fromDTO(AtendimentoRequestDTO dto) {
		return new Atendimento(null, dto.getTicket(), dto.getMedico(), dto.getCachorro(),
						   dto.getPesoAtual(), dto.getAlturaAtual(), dto.getDataHora(),
						   dto.getDiagnostico(), dto.getComentario());
	}
	
	public static AtendimentoResponseDTO fromEntity(Atendimento atendimento) {
		return new AtendimentoResponseDTO(atendimento.getId(), atendimento.getTicket(), atendimento.getMedico(),
							atendimento.getCachorro(), atendimento.getPesoAtual(), atendimento.getAlturaAtual(), 
							atendimento.getDataHora(), atendimento.getDiagnostico(), atendimento.getComentario());
	}
}
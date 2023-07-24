package com.veterinaria.services;

import com.veterinaria.entities.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicoService {

    Medico salvarMedico(Medico medico);
    Page<Medico> listarTodosOsMedicos(Pageable pageable);
    Medico buscarMedico(Long id);
    Medico atualizarMedico(Medico medico, Long id);
    void excluirMedico(Long id);
}
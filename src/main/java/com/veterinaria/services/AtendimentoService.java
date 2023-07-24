package com.veterinaria.services;

import com.veterinaria.entities.Atendimento;
import com.veterinaria.entities.Cachorro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AtendimentoService {

    Cachorro buscarCachorro(Long id);
    Atendimento salvarAtendimento(Atendimento atendimento);
    Page<Atendimento> listarTodosOsAtendimentos(Pageable pageable);
    Atendimento buscarAtendimento(Long id);
}
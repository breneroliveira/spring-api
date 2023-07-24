package com.veterinaria.services;

import com.veterinaria.dtos.responses.ImagemResponseDTO;
import com.veterinaria.entities.Raca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RacaService {

    List<Raca> getRacaDogApi();
    List<Raca> buscarRacaDogApi(String nomeRaca);
    List<ImagemResponseDTO> getImagemDogApi();
    Raca salvarRaca(Raca raca);
    Page<Raca> listarTodosAsRacas(Pageable pageable);
    Raca buscarRaca(Long id);
    Raca atualizarRaca(Raca raca, Long id);
    void excluirRaca(Long id);
}
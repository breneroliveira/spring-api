package com.veterinaria.services;

import com.veterinaria.entities.Cachorro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CachorroService {

    Cachorro salvarCachorro(Cachorro cachorro);
    Page<Cachorro> listarTodosOsCachorros(Pageable pageable);
    Cachorro buscarCachorro(Long id);
    Page<Cachorro> buscaCachorroPorCliente(Pageable pageable, String nomeCliente);
    Cachorro atualizarCachorro(Cachorro cachorro, Long id);
    void excluirCachorro(Long id);
}
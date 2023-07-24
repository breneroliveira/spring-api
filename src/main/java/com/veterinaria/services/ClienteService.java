package com.veterinaria.services;

import com.veterinaria.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {

    Cliente salvarCliente(Cliente cliente);
    Page<Cliente> listarTodosOsClientes(Pageable pageable);
    Cliente buscarCliente(Long id);
    Page<Cliente> buscaClientePorCachorro(Pageable pageable, String nomeCachorro);
    Cliente atualizarCliente(Cliente cliente, Long id);
    void excluirCliente(Long id);
}
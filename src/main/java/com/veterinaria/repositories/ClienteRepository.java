package com.veterinaria.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veterinaria.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Page<Cliente> findAll(Pageable pageable);
	Page<Cliente> findByCachorrosNomeContains(Pageable pageable, String nomeCachorro);
	
	Optional<Cliente> findByCpf(String cpf);
}
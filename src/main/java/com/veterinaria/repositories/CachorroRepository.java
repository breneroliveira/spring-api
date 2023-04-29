package com.veterinaria.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veterinaria.entities.Cachorro;

@Repository
public interface CachorroRepository extends JpaRepository<Cachorro, Long> {

	Page<Cachorro> findAll(Pageable pageable);
	Page<Cachorro> findByClienteNomeContains(Pageable pageable, String nomeCliente);
	
	Optional<Cachorro> findByNumeroRegistro(String numeroRegistro);
}
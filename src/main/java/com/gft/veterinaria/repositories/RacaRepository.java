package com.gft.veterinaria.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.veterinaria.entities.Raca;

@Repository
public interface RacaRepository extends JpaRepository<Raca, Long> {

	Page<Raca> findAll(Pageable pageable);
	
	Optional<Raca> findByNome(String nome);
}
package com.gft.veterinaria.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.veterinaria.entities.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

	Page<Medico> findAll(Pageable pageable);
	
	Optional<Medico> findByCrmv(String crmv);
}
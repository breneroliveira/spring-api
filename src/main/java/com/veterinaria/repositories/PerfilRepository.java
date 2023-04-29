package com.veterinaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veterinaria.entities.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

	Perfil findByNome(String nome);
}
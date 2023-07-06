package com.fisi.proyectocursos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fisi.proyectocursos.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(String name);

}

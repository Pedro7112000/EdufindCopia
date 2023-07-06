package com.fisi.proyectocursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fisi.proyectocursos.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}

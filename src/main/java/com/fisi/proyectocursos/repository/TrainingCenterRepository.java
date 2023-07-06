package com.fisi.proyectocursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fisi.proyectocursos.model.TrainingCenter;

public interface TrainingCenterRepository extends JpaRepository<TrainingCenter, Integer> {
	
	TrainingCenter findByUsername(String username);

}

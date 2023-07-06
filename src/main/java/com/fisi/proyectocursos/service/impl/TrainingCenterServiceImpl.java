package com.fisi.proyectocursos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fisi.proyectocursos.model.TrainingCenter;
import com.fisi.proyectocursos.repository.TrainingCenterRepository;
import com.fisi.proyectocursos.service.interfaces.ITrainingCenterService;

@Service
public class TrainingCenterServiceImpl implements ITrainingCenterService {
	
	@Autowired
	private TrainingCenterRepository repository;

	@Override
	public TrainingCenter save(TrainingCenter tc) {
		return repository.save(tc);
	}

	@Override
	public void deleteById(int id) {
		repository.deleteById(id);
	}

	@Override
	public List<TrainingCenter> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<TrainingCenter> findById(int id) {
		return repository.findById(id);
	}

	@Override
	public TrainingCenter findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public void softDelete(int id) {
		// TODO Auto-generated method stub
	}

}

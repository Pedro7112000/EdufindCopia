package com.fisi.proyectocursos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fisi.proyectocursos.model.Person;
import com.fisi.proyectocursos.repository.PersonRepository;
import com.fisi.proyectocursos.service.interfaces.IPersonService;

@Service
public class PersonServiceImpl implements IPersonService {
	
	@Autowired
	private PersonRepository repository;

	@Override
	public Person save(Person p) {
		return repository.save(p);
	}

	@Override
	public void deleteById(int id) {
		repository.deleteById(id);
	}

	@Override
	public void softDelete(int id) {
		
	}

	@Override
	public List<Person> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Person> findById(int id) {
		return repository.findById(id);
	}

}

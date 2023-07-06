package com.fisi.proyectocursos.service;

import java.util.List;
import java.util.Optional;

public interface IService<T> {
	
	T save(T t);
	void deleteById(int id);
	void softDelete(int id);
	List<T> findAll();
	Optional<T> findById(int id);

}

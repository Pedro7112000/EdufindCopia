package com.fisi.proyectocursos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fisi.proyectocursos.model.Category;
import com.fisi.proyectocursos.repository.CategoryRepository;
import com.fisi.proyectocursos.service.interfaces.ICategoryService;

@Service
public class CategoryService implements ICategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public void deleteById(int id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> findById(int id) {
		return categoryRepository.findById(id);
	}

	@Override
	public void softDelete(int id) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean existsById(Integer id) {
		return categoryRepository.existsById(id);
	}

}

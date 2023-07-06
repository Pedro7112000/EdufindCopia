package com.fisi.proyectocursos.service.interfaces;

import com.fisi.proyectocursos.model.Category;
import com.fisi.proyectocursos.service.IService;

public interface ICategoryService extends IService<Category> {
	
	boolean existsById(Integer id);

}

package com.fisi.proyectocursos.service.interfaces;

import java.util.Optional;

import com.fisi.proyectocursos.model.Role;
import com.fisi.proyectocursos.service.IService;

public interface IRoleService extends IService<Role> {
	
	Optional<Role> findByName(String name);

}

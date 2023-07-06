package com.fisi.proyectocursos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fisi.proyectocursos.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUsername(String username);
	boolean existsByUsername(String username);
	List<User> findByStatus(Integer status);
	User findByResetPasswordToken(String resetPasswordToken);

}

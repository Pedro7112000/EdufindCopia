package com.fisi.proyectocursos.service.interfaces;

import java.util.List;

import com.fisi.proyectocursos.model.User;
import com.fisi.proyectocursos.service.IService;

public interface IUserService extends IService<User> {
	
	User findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsById(Integer id);
	List<User> findByStatus(Integer status);
	void updateResetPasswordToken(String resetPasswordToken, String email);
	User findByResetPasswordToken(String resetPasswordToken);
	void updatePassword(User user, String newPassword)
;
}

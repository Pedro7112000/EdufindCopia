package com.fisi.proyectocursos.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fisi.proyectocursos.model.User;
import com.fisi.proyectocursos.service.impl.UserServiceImpl;

@Component
public class UserValidator implements Validator {
	
	@Autowired
	private UserServiceImpl userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		if (userService.existsByUsername(user.getUsername())) {
			errors.rejectValue("username", "Duplicate.userForm.username");
		}
	}
	
	

}

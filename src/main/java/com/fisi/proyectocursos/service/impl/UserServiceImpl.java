package com.fisi.proyectocursos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fisi.proyectocursos.model.User;
import com.fisi.proyectocursos.repository.UserRepository;
import com.fisi.proyectocursos.service.interfaces.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(int id) {
		return userRepository.findById(id);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public boolean existsById(Integer id) {
		return userRepository.existsById(id);
	}
	
	@Override
	public void softDelete(int id) {
		userRepository.findById(id)
		.ifPresent(userDB -> {
			userDB.setStatus(0);
			userRepository.save(userDB);
		});
	}

	@Override
	public List<User> findByStatus(Integer status) {
		return userRepository.findByStatus(status);
	}

	@Override
	public void updateResetPasswordToken(String resetPasswordToken, String email) {
		User user = userRepository.findByUsername(email);
		user.setResetPasswordToken(resetPasswordToken);
		userRepository.save(user);
	}

	@Override
	public User findByResetPasswordToken(String resetPasswordToken) {
		return userRepository.findByResetPasswordToken(resetPasswordToken);
	}

	@Override
	public void updatePassword(User user, String newPassword) {
		String encodePassword = passwordEncoder.encode(newPassword);
		
		user.setPassword(encodePassword);
		user.setResetPasswordToken(null);
		userRepository.save(user);
	}

	public boolean resetPassword(String token, String newPassword) {
		User user = findByResetPasswordToken(token);
		if(user != null) {
			updatePassword(user, newPassword);
			return true;
		}
		return false;
	}


}

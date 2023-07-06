package com.fisi.proyectocursos.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fisi.proyectocursos.security.entity.UserPrincipal;
import com.fisi.proyectocursos.security.service.UserDetailsServiceImpl;

@Component
public class MyCustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserDetailsServiceImpl userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		UserPrincipal user = (UserPrincipal) userService.loadUserByUsername(name);
		
		if (user.getStatus() == 0) {
			return null;
		}
		
		if (passwordEncoder.matches(password, user.getPassword())) {
			Authentication auth = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
			return auth;
		}
		
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}

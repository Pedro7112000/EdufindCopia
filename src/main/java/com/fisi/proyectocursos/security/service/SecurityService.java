package com.fisi.proyectocursos.security.service;

import java.util.Set;

public interface SecurityService {
	
	boolean isAuthenticated();
	void autoLogin(String username, String password);
	Set<String> getRolesFromUserAuthenticatedUser();

}

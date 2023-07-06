package com.fisi.proyectocursos.utils;

import javax.servlet.http.HttpServletRequest;

public class RecoveryURL {
	
	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

}

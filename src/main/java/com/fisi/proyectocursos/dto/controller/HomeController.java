package com.fisi.proyectocursos.dto.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fisi.proyectocursos.model.Course;
import com.fisi.proyectocursos.security.service.SecurityService;
import com.fisi.proyectocursos.service.interfaces.ICourseService;

@Controller
public class HomeController {
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private ICourseService courseService;
	
	@ModelAttribute("courses")
	public List<Course> courses() {
		List<Course> courses = courseService.findByStatus(1);
		return courses;
	}
		
	@GetMapping("/")
	public String index() {
		if (securityService.isAuthenticated()) {
			Set<String> roles = securityService.getRolesFromUserAuthenticatedUser();
			if (roles.contains("ROLE_USER")) return "redirect:/usuario";
			if (roles.contains("ROLE_CENTER")) return "redirect:/centro";
			if (roles.contains("ROLE_ADMIN")) return "redirect:/admin";
		}
		
		return "public_home";
	}
	
	@GetMapping("/soy_centro")
	public String soyCentro() {
		if (securityService.isAuthenticated()) {
			Set<String> roles = securityService.getRolesFromUserAuthenticatedUser();
			if (roles.contains("ROLE_USER")) return "redirect:/usuario";
			if (roles.contains("ROLE_CENTER")) return "redirect:/centro";
			if (roles.contains("ROLE_ADMIN")) return "redirect:/admin";
		}
		
		return "im_a_center";
	}

}

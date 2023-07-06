package com.fisi.proyectocursos.dto.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fisi.proyectocursos.dto.PersonDTO;
import com.fisi.proyectocursos.dto.TrainingCenterDTO;
import com.fisi.proyectocursos.model.Person;
import com.fisi.proyectocursos.model.Role;
import com.fisi.proyectocursos.model.TrainingCenter;
import com.fisi.proyectocursos.security.service.SecurityService;
import com.fisi.proyectocursos.service.impl.UserServiceImpl;
import com.fisi.proyectocursos.service.interfaces.IRoleService;

@Controller
public class LoginController {

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		try{
			if (securityService.isAuthenticated()) {
				return "redirect:/";
			}

			if (error != null) {
				model.addAttribute("error", "Su correo y contraseña no son válidos.");
			}

			if (logout != null) {
				model.addAttribute("message", "Se ha desconectado con éxito.");
			}

			return "auth/login";
		}catch(Exception ex){
			System.err.println(ex);
			return ex.toString();
		}
	}


	@GetMapping("/registro")
	public String registro(Model model) {
		if (securityService.isAuthenticated()) {
			return "redirect:/";
		}
		
		model.addAttribute("userForm", new Person()); // new User()
		
		return "auth/register";
	}
	
	@PostMapping("/registro")
	public String registro(@Valid @ModelAttribute("userForm") PersonDTO userForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "auth/register";
		}
		
		if (userService.existsByUsername(userForm.getUsername())) {
			model.addAttribute("msg", "El correo ya está en uso");
			return "auth/register";
		}
		
		Person person = new Person();
		person.setUsername(userForm.getUsername());
		person.setFirstName(userForm.getFirstName());
		person.setLastName(userForm.getLastName());
		
		Set<Role> roles = new HashSet<>();
		Role role = roleService.findByName("ROLE_USER").orElse(null);
		
		if (role == null) {
			model.addAttribute("notification", "Ocurrió algo inesperado, intente registrarse más tarde.");
			return "redirect:/"; 
		}
		
		roles.add(role);
		person.setRoles(roles);
		
		person.setPassword(passwordEncoder.encode(userForm.getPassword()));
		person.setStatus(1);
		
		userService.save(person);
		redirectAttributes.addFlashAttribute("notification", "Registro exitoso");
		
		return "redirect:/login";
	}
	
	@GetMapping("/registro/centro")
	public String registroCentro(Model model) {
		if (securityService.isAuthenticated()) {
			return "redirect:/";
		}
		
		model.addAttribute("userForm", new TrainingCenter()); // new User()
		
		return "auth/register_center";
	}
	
	@PostMapping("/registro/centro")
	public String registroCentro(@Valid @ModelAttribute("userForm") TrainingCenterDTO userForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {

		if (bindingResult.hasErrors()) {
			return "auth/register_center";
		}
		
		if (userService.existsByUsername(userForm.getUsername())) {
			model.addAttribute("msg", "El correo ya está en uso");
			return "auth/register_center";
		}
		
		TrainingCenter center = new TrainingCenter();
		center.setUsername(userForm.getUsername());
		center.setName(userForm.getName());
		center.setRuc(userForm.getRuc());
		center.setPhone(userForm.getPhone());
		
		Set<Role> roles = new HashSet<>();
		Role role = roleService.findByName("ROLE_CENTER").orElse(null);
		
		if (role == null) {
			model.addAttribute("notification", "Ocurrió algo inesperado, intente registrarse más tarde.");
			return "redirect:/"; 
		}
		
		roles.add(role);
		center.setRoles(roles);
		
		center.setPassword(passwordEncoder.encode(userForm.getPassword()));
		center.setStatus(1);
		
		userService.save(center);
		redirectAttributes.addFlashAttribute("notification", "Registro exitoso");
		
		return "redirect:/login";
	}
	
}

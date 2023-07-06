package com.fisi.proyectocursos.dto.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fisi.proyectocursos.dto.NewPasswordDTO;
import com.fisi.proyectocursos.model.Course;
import com.fisi.proyectocursos.model.Person;
import com.fisi.proyectocursos.model.User;
import com.fisi.proyectocursos.service.impl.UserServiceImpl;
import com.fisi.proyectocursos.service.interfaces.ICourseService;

@Controller
@RequestMapping("/usuario")
public class UserController {
	
	@Autowired
	private ICourseService courseService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@ModelAttribute("user")
	public User user() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Person person = (Person) userService.findByUsername(auth.getName());
		return person;
	}
	
	@ModelAttribute("passwordDTO")
	public NewPasswordDTO passwordDTO() {
		return new NewPasswordDTO();
	}
	
	@GetMapping
	public String home() {
		return "user/user_home";
	}
	
	@GetMapping("/catalogo")
	public String catalogo(Model model) {
		List<Course> courses = courseService.findByStatus(1);
		model.addAttribute("courses", courses);
		
		return "user/user_catalogo_list";
	}

	@GetMapping("/cuenta")
	public String cuenta() {
		return "user/user_account";
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("user") Person user, BindingResult bindingResult, 
			RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "user/user_account";
		}
		
		userService.save(user);
		redirectAttributes.addFlashAttribute("notification", "Información actualizada");
		
		return "redirect:/usuario/cuenta";
	}
	
	@PostMapping("/update/password")
	public String updatePassword(@Valid @ModelAttribute("passwordDTO") NewPasswordDTO passwordDTO, BindingResult bindingResult,
			@ModelAttribute("user") Person user, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "user/user_account";
		}
		
		String passwordForm = passwordDTO.getCurrentPassword();
		String passwordDB = user.getPassword();
		
		if (passwordEncoder.matches(passwordForm, passwordDB)) {
			/*
			String newPassword = passwordDTO.getNewPassword();
			// SE AÑADE VALIDACIÓN DE CONTRASEÑA CON 8 CARACTERES MÍNIMO Y 1 MAYÚSCULA, 1 MINÚSCULA Y 1 NÚMERO
			//	ESTO  SE HACE CON UNA EXPRESIÓN REGULAR
			// EL MOTIVO  DE ESTA VALIDACIÓN ES QUE LA CONTRASEÑA SEA MÁS SEGURA

			if (newPassword.length() < 8 || !newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")) {
				redirectAttributes.addFlashAttribute("error", "La contraseña debe tener al menos 8 caracteres y contener al menos una mayúscula, una minúscula y un número");
				return "redirect:/usuario/cuenta";
			}
			*/
			//        DIVISION DE  LOGICA ORIGINAL PARA EVITAR SOBREEXTENSION
			//        EJEMPLO DE CODE SMELL: LONG METHOD
			//		  Autor: Edgar Zenobio Pariasca
			if (compruebaPassword(passwordDTO,redirectAttributes,user)){
				return "redirect:/usuario/cuenta";
			}

			redirectAttributes.addFlashAttribute("notification", "Contraseña actualizada");
		} else {
			redirectAttributes.addFlashAttribute("error", "Contraseña actual incorrecta");
		}
		
		return "redirect:/usuario/cuenta";
	}

	// Metodo nuevo
	public boolean compruebaPassword(NewPasswordDTO pdto, RedirectAttributes redirectAttributes, @ModelAttribute("user") Person user){
		String newPassword = pdto.getNewPassword();
		// SE AÑADE VALIDACIÓN DE CONTRASEÑA CON 8 CARACTERES MÍNIMO Y 1 MAYÚSCULA, 1 MINÚSCULA Y 1 NÚMERO
		//	ESTO  SE HACE CON UNA EXPRESIÓN REGULAR
		// EL MOTIVO  DE ESTA VALIDACIÓN ES QUE LA CONTRASEÑA SEA MÁS SEGURA

		if (newPassword.length() < 8 || !newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")) {
			redirectAttributes.addFlashAttribute("error", "La contraseña debe tener al menos 8 caracteres y contener al menos una mayúscula, una minúscula y un número");
			return true;
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		userService.save(user);
		return false;
	}

}

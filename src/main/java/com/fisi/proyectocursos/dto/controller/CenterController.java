package com.fisi.proyectocursos.dto.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fisi.proyectocursos.dto.CourseDTO;
import com.fisi.proyectocursos.dto.NewPasswordDTO;
import com.fisi.proyectocursos.model.Category;
import com.fisi.proyectocursos.model.Course;
import com.fisi.proyectocursos.model.TrainingCenter;
import com.fisi.proyectocursos.model.User;
import com.fisi.proyectocursos.service.impl.UserServiceImpl;
import com.fisi.proyectocursos.service.interfaces.ICategoryService;
import com.fisi.proyectocursos.service.interfaces.ICourseService;

@Controller
@RequestMapping("/centro")
public class CenterController {
	
	@Autowired
	private ICourseService courseService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@ModelAttribute("user")
	public User user() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TrainingCenter tc = (TrainingCenter) userService.findByUsername(auth.getName());
		return tc;
	}
	
	@ModelAttribute("passwordDTO")
	public NewPasswordDTO passwordDTO() {
		return new NewPasswordDTO();
	}
	
	@GetMapping
	public String home() {
		return "center/center_home";
	}
	
	@GetMapping("/catalogo")
	public String catalogo(@ModelAttribute("user") TrainingCenter user, Model model) {
		List<Course> courses = courseService.findByTrainingCenterAndStatus(user, 1);
		model.addAttribute("courses", courses);
		
		return "center/center_catalogo_list";
	}

	@GetMapping("/cuenta")
	public String cuenta() {
		return "center/center_account";
	}
	
	@GetMapping("/catalogo/nuevo")
	public String nuevoCurso(@ModelAttribute("courseDTO") CourseDTO courseDTO, Model model) {
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		return "center/center_catalogo_add";
	}
	//SE IDENTIFICO UN CODE SMELL DE TIPO LONG METHOD Y SE REFACTORIZO CON EXTRACT METHOD
	@PostMapping("/catalogo/nuevo")
	public String nuevoCurso(@Valid @ModelAttribute("courseDTO") CourseDTO courseDTO, BindingResult bindingResult,
							 Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			List<Category> categories = categoryService.findAll();
			model.addAttribute("categories", categories);
			return "center/center_catalogo_add";
		}

		TrainingCenter tc = (TrainingCenter) model.getAttribute("user");

		Course course = modelMapper.map(courseDTO, Course.class);
		course.setStatus(1);
		course.setTrainingCenter(tc);

		courseService.save(course);
		redirectAttributes.addFlashAttribute("notification", "Curso agregado");

		return "redirect:/centro/catalogo";
	}

	@GetMapping("/catalogo/editar/{id}")
	public String editarCurso(@PathVariable Integer id, Model model) {
		Course course = courseService.findById(id).orElse(null);

		if (course == null) {
			return "redirect:/centro/catalogo";
		}

		CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);
		List<Category> categories = categoryService.findAll();

		model.addAttribute("courseDTO", courseDTO);
		model.addAttribute("categories", categories);

		return "center/center_catalogo_edit";
	}

	@PostMapping("/catalogo/editar")
	public String guardarCursoEditado(@ModelAttribute("courseDTO") CourseDTO courseDTO, BindingResult bindingResult,
									  RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "center/center_catalogo_edit";
		}

		Course course = modelMapper.map(courseDTO, Course.class);

		courseService.save(course);
		redirectAttributes.addFlashAttribute("notification", "Curso actualizado");

		return "redirect:/centro/catalogo";
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("user") TrainingCenter user, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "center/center_account";
		}
		
		userService.save(user);
		redirectAttributes.addFlashAttribute("notification", "Información actualizada");
		
		return "redirect:/centro/cuenta";
	}
	
	@PostMapping("/update/password")
	public String updatePassword(@Valid @ModelAttribute("passwordDTO") NewPasswordDTO passwordDTO, BindingResult bindingResult,
			@ModelAttribute("user") TrainingCenter user, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "center/center_account";
		}
		
		String passwordForm = passwordDTO.getCurrentPassword();
		String passwordDB = user.getPassword();
		
		if (passwordEncoder.matches(passwordForm, passwordDB)) {
			String newPassword = passwordDTO.getNewPassword();
			user.setPassword(passwordEncoder.encode(newPassword));
			
			userService.save(user);
			redirectAttributes.addFlashAttribute("notification", "Contraseña actualizada");
		} else {
			redirectAttributes.addFlashAttribute("error", "Contraseña actual incorrecta");
		}
		
		return "redirect:/centro/cuenta";
	}
	
	@GetMapping("/catalogo/eliminar/{id}")
	public String deleteCourse(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		courseService.softDelete(id);
		redirectAttributes.addFlashAttribute("notification", "Curso eliminado");
		return "redirect:/centro/catalogo";
	}
	
	
}

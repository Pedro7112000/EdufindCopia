package com.fisi.proyectocursos.dto.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fisi.proyectocursos.dto.EditUserDTO;
import com.fisi.proyectocursos.dto.NewPasswordDTO;
import com.fisi.proyectocursos.dto.NewUserDTO;
import com.fisi.proyectocursos.dto.converter.EditUserDTOConverter;
import com.fisi.proyectocursos.model.Course;
import com.fisi.proyectocursos.model.Person;
import com.fisi.proyectocursos.model.Role;
import com.fisi.proyectocursos.model.TrainingCenter;
import com.fisi.proyectocursos.model.User;
import com.fisi.proyectocursos.service.impl.UserServiceImpl;
import com.fisi.proyectocursos.service.interfaces.ICourseService;
import com.fisi.proyectocursos.service.interfaces.IRoleService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private ICourseService courseService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private EditUserDTOConverter converter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@ModelAttribute("users")
	public List<User> users() {
		return userService.findAll();
	}
	
	@ModelAttribute("courses")
	public List<Course> courses(){
		return courseService.findByStatus(1);
	}
	
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
	public String home(@ModelAttribute("users") List<User> users, Model model) {
		long totalPersons = users.stream()
								.filter(user -> user.getStatus() == 1)
								.filter(Person.class::isInstance)
								.count();
		
		long totalCenters= users.stream()
								.filter(user -> user.getStatus() == 1)
								.filter(TrainingCenter.class::isInstance)
								.count();
		
		model.addAttribute("totalPersons", totalPersons);
		model.addAttribute("totalCenters", totalCenters);
		
		return "admin/admin_home";
	}
	
	@GetMapping("/usuarios")
	public String usuarios(@ModelAttribute("user") User user, Model model) {
		
		List<User> users = userService.findAll();

		users = users.stream()
				.filter(us -> !us.getUsername().equals(user.getUsername()))
				.collect(Collectors.toList());			

		model.addAttribute("users", users);
		
		return "admin/admin_usuarios_list";
	}
	
	@GetMapping("/usuarios/editar/{id}")
	public String editarUsuario(@PathVariable Integer id, Model model) {	
		User user = userService.findById(id).orElse(null);
		
		if (user == null) return "redirect:/admin/usuarios";
		
		EditUserDTO dto = converter.convertToDTO(user);
		
		model.addAttribute("userDTO", dto);
		
		List<Role> roles = roleService.findAll();
		model.addAttribute("roles", roles);
		
		return "admin/admin_usuarios_edit";
	}
	
	@PostMapping("/usuarios/editar")
	public String editarUsuario(@Valid @ModelAttribute("userDTO") EditUserDTO userDTO, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {
		

		User userDB = userService.findById(userDTO.getId()).orElse(new User());
		
		userDB.setStatus(userDTO.getStatus());
		userDB.setUsername(userDTO.getUsername());
		
		// Mejorar esto usando el converter
		if (userDB instanceof Person) {
			((Person) userDB).setFirstName(userDTO.getFirstName());
			((Person) userDB).setLastName(userDTO.getLastName());
		} else if (userDB instanceof TrainingCenter) {
			((TrainingCenter) userDB).setName(userDTO.getName());
			((TrainingCenter) userDB).setRuc(userDTO.getRuc());
			((TrainingCenter) userDB).setPhone(userDTO.getPhone());
		}

		userService.save(userDB);
		redirectAttributes.addFlashAttribute("notification", "Usuario actualizado");
		
		return "redirect:/admin/usuarios";
	}
	
	@GetMapping("/usuarios/cambiarEstado/{id}")
	public String cambiarEstado(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
		User userDB = userService.findById(id).orElse(null); 
		
		if (userDB == null) return "redirect:/admin/usuarios";
		
		userDB.setStatus(1 - userDB.getStatus());
		userService.save(userDB);
		
		String msg;
		
		if (userDB.getStatus() == 1) {
			msg = "Usuario activado";
		} else {
			msg = "Usuario desactivado";
		}
		
		redirectAttributes.addFlashAttribute("notification", msg);
		
		return "redirect:/admin/usuarios";
	}
	
	@GetMapping("/usuarios/eliminar/{id}")
	public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		userService.deleteById(id);
		redirectAttributes.addFlashAttribute("notification", "Usuario eliminado");
		return "redirect:/admin/usuarios";
	}
	
	@GetMapping("/cuenta")
	public String cuenta(Model model) {
		
		return "admin/admin_account";
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("user") Person user, BindingResult bindingResult, 
			RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "admin/admin_account";
		}
		
		userService.save(user);
		redirectAttributes.addFlashAttribute("notification", "Información actualizada");
		
		return "redirect:/admin/cuenta";
	}
	
	@PostMapping("/update/password")
	public String updatePassword(@Valid @ModelAttribute("passwordDTO") NewPasswordDTO passwordDTO, BindingResult bindingResult,
			@ModelAttribute("user") Person user, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "admin/admin_account";
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
		
		return "redirect:/admin/cuenta";
	}
	
	@GetMapping("/usuarios/nuevo")
	public String nuevoUsuario(Model model) {
		NewUserDTO dto = new NewUserDTO();
		model.addAttribute("userDTO", dto);
		
		List<Role> roles = roleService.findAll();
		model.addAttribute("roles", roles);
		
		return "admin/admin_usuarios_add";
	}

	@PostMapping("/usuarios/nuevo")
	public String nuevoUsuario(@ModelAttribute("userDTO") NewUserDTO dto,
			RedirectAttributes redirectAttributes) {
		//        DIVISION DE  LA LOGICA ORIGINAL EN DOS METODOS PARA QUE EL METODO nuevoUsuario NO SEA TAN LARGO
		//        EJEMPLO DE CODE SMELL: LONG METHOD
//		Autor: Kevin Ramos Rivas
		if (dto.getRole() == 3) {
			createTrainingCenter(dto);
		} else {
			createPerson(dto);
		}

		redirectAttributes.addFlashAttribute("notification", "Usuario creado");

		return "redirect:/admin/usuarios";
	}
// Nuevo metodo para crear un usuario de tipo TrainingCenter
	public void createTrainingCenter(NewUserDTO dto) {
		TrainingCenter tc = new TrainingCenter();

		tc.setUsername(dto.getUsername());
		tc.setPassword(passwordEncoder.encode(dto.getPassword()));
		tc.setName(dto.getName());
		tc.setStatus(dto.getStatus());
		tc.setRuc(dto.getRuc());
		tc.setPhone(dto.getPhone());

		Set<Role> roles = new HashSet<>();
		roles.add(new Role(dto.getRole()));

		tc.setRoles(roles);

		userService.save(tc);
	}
//	Nuevo metodo para crear un usuario de tipo Person
	public void createPerson(NewUserDTO dto) {
		Person person = new Person();

		person.setUsername(dto.getUsername());
		person.setPassword(passwordEncoder.encode(dto.getPassword()));
		person.setStatus(dto.getStatus());
		person.setFirstName(dto.getFirstName());
		person.setLastName(dto.getLastName());

		Set<Role> roles = new HashSet<>();
		roles.add(new Role(dto.getRole()));

		person.setRoles(roles);

		userService.save(person);
	}

}

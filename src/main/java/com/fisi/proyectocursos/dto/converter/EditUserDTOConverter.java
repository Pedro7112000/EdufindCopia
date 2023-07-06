package com.fisi.proyectocursos.dto.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fisi.proyectocursos.dto.EditUserDTO;
import com.fisi.proyectocursos.model.Person;
import com.fisi.proyectocursos.model.Role;
import com.fisi.proyectocursos.model.TrainingCenter;
import com.fisi.proyectocursos.model.User;

@Component
public class EditUserDTOConverter {
	
	public EditUserDTO convertToDTO(User user) {
		EditUserDTO dto = new EditUserDTO();
		
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setPassword(user.getPassword());
		dto.setStatus(user.getStatus());
		
		List<Integer> roles = new ArrayList<>();
		
		user.getRoles()
			.stream()
			.forEach(r -> roles.add(r.getId()));
		
		dto.setRoles(roles);
			
		if (user instanceof Person) {
			dto.setFirstName(((Person) user).getFirstName());
			dto.setLastName(((Person) user).getLastName());
		}
		
		if (user instanceof TrainingCenter) {
			dto.setName(((TrainingCenter) user).getName());
			dto.setRuc(((TrainingCenter) user).getRuc());
			dto.setPhone(((TrainingCenter) user).getPhone());
		}
		
		return dto;
	}
	
	public User convertToEntity(EditUserDTO dto) {
		if (dto.getName() != null) {
			Person person = new Person();
			
			person.setId(dto.getId());
			person.setUsername(dto.getUsername());
			person.setPassword(dto.getPassword());
			person.setStatus(dto.getStatus());
			person.setFirstName(dto.getFirstName());
			person.setLastName(dto.getLastName());
			
			Set<Role> roles = new HashSet<>();
			
			dto.getRoles()
				.stream()
				.forEach(r -> roles.add(new Role(r)));
			
			person.setRoles(roles);
			
			return person;
		} else {
			TrainingCenter tc = new TrainingCenter();
			tc.setId(dto.getId());
			tc.setUsername(dto.getUsername());
			tc.setPassword(dto.getPassword());
			tc.setStatus(dto.getStatus());
			tc.setName(dto.getName());
			tc.setRuc(dto.getRuc());
			tc.setPhone(dto.getPhone());
			
			Set<Role> roles = new HashSet<>();
			
			dto.getRoles()
				.stream()
				.forEach(r -> roles.add(new Role(r)));
			
			tc.setRoles(roles);
			
			return tc;
		}
	}

}
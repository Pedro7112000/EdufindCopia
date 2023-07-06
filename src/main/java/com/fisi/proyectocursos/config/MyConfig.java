package com.fisi.proyectocursos.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fisi.proyectocursos.model.Role;

@Configuration
public class MyConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		
		ModelMapper modelMapper = new ModelMapper();
		
		Converter<String, LocalDate> toStringDate = new AbstractConverter<String, LocalDate>() {
			@Override
			protected LocalDate convert(String source) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate localDate = LocalDate.parse(source, format);
				return localDate;
			}
		};
		
		Converter<LocalDate, String> toDateString = new AbstractConverter<LocalDate, String>() {
			@Override
			protected String convert(LocalDate source) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String stringDate = source.format(format);
				return stringDate;
			}
		};
		
		Converter<Role, String> toRoleString = new AbstractConverter<Role, String>() {
			@Override
			protected String convert(Role source) {
				String rol = source.getName();
				return rol;
			}
		};
		
		Converter<String, Role> toStringRole = new AbstractConverter<String, Role>() {
			@Override
			protected Role convert(String source) {
				Role rol = new Role();
				rol.setName(source);
				return rol;
			}
		};

		modelMapper.createTypeMap(String.class, LocalDate.class);
		modelMapper.addConverter(toStringDate);
		
		modelMapper.createTypeMap(LocalDate.class, String.class);
		modelMapper.addConverter(toDateString);
		
		modelMapper.createTypeMap(Role.class, String.class);
		modelMapper.addConverter(toRoleString);
		
		modelMapper.createTypeMap(String.class, Role.class);
		modelMapper.addConverter(toStringRole);
		
		modelMapper.validate();
		
		return modelMapper;
	}

}

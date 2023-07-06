package com.fisi.proyectocursos.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

public class TrainingCenterDTO {

	private Integer id;
	
	@NotBlank(message = "Campo requerido")
	private String username;
	
	@NotBlank(message = "Campo requerido")
	private String password;
	
	@NotBlank(message = "Campo requerido")
	private String name;
	private Integer status;
	
	@NotBlank(message = "Campo requerido")
	private String ruc;
	
	@NotBlank(message = "Campo requerido")
	private String phone;
	
	private Set<String> roles = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}

package com.fisi.proyectocursos.dto;

import java.util.ArrayList;
import java.util.List;

public class EditUserDTO {

	private Integer id;
	
	private String username;
	
	private String password;
	
	private String name;
	
	private Integer status;
	
	private String firstName;
	
	private String lastName;
	
	private String ruc;
	
	private String phone;
	
	private List<Integer> roles = new ArrayList<>();

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

	public String getFirstName() {
		return firstName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public List<Integer> getRoles() {
		return roles;
	}

	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "EditUserDTO [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", status=" + status + ", firstName=" + firstName + ", lastName=" + lastName + ", roles=" + roles
				+ "]";
	}

}

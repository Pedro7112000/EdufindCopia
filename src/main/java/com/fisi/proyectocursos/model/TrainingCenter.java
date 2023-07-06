package com.fisi.proyectocursos.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("C")
public class TrainingCenter extends User {

	@NotBlank(message = "Campo requerido")
	@Column(name = "name")
	private String name;

	@NotBlank(message = "Campo requerido")
	@Column(name = "ruc")
	private String ruc;

	@NotBlank(message = "Campo requerido")
	@Column(name = "phone")
	private String phone;

	@OneToMany(mappedBy = "trainingCenter", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonIgnore
	private List<Course> courses;

	public TrainingCenter() {
	}

	public TrainingCenter(String username, String password, Integer status, String name) {
		super(username, password, status);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

}

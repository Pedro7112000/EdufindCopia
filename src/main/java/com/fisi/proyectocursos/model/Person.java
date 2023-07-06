package com.fisi.proyectocursos.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "person")
@DiscriminatorValue("P")
public class Person extends User {

	@NotBlank(message = "Campo requerido")
	@Column(name = "first_name")
	private String firstName;

	@NotBlank(message = "Campo requerido")
	@Column(name = "last_name")
	private String lastName;

	public Person() {}

	public Person(String username, String password, Integer status, String firstName, String lastName) {
		super(username, password, status);
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
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

}

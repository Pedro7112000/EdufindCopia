package com.fisi.proyectocursos.dto;

import javax.validation.constraints.NotBlank;

public class NewPasswordDTO {

	@NotBlank(message = "Ingrese su contraseña actual")
	private String currentPassword;
	
	@NotBlank(message = "Ingrese su nueva contraseña")
	private String newPassword;
	
	public NewPasswordDTO() {}

	public NewPasswordDTO(String currentPassword, String newPassword) {
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}

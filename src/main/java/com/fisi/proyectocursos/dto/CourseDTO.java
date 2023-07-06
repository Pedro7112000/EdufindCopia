package com.fisi.proyectocursos.dto;

public class CourseDTO {

	private Integer id;
	private String name;
	private String startDate;
	private Integer duration;
	private String schedule;
	private String url;
	private Integer status;
	private Integer categoryId;
	private Integer trainingCenterId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getTrainingCenterId() {
		return trainingCenterId;
	}

	public void setTrainingCenterId(Integer trainingCenterId) {
		this.trainingCenterId = trainingCenterId;
	}

	@Override
	public String toString() {
		return "CourseDTO [name=" + name + ", startDate=" + startDate + ", duration=" + duration + ", schedule="
				+ schedule + ", url=" + url + ", status=" + status + ", categoryId=" + categoryId
				+ ", trainingCenterId=" + trainingCenterId + "]";
	}

}

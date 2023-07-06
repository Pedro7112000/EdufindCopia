package com.fisi.proyectocursos.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "start_date", columnDefinition = "DATE", nullable = false)
	private LocalDate startDate;

	@Column(name = "duration", nullable = false)
	private Integer duration;

	@Column(name = "schedule", nullable = false)
	private String schedule;

	@Column(name = "url", nullable = false)
	private String url;
	
	@Column(name = "status")
	private Integer status;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = true)
	@JsonIgnoreProperties("courses")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "training_center_id", nullable = false)
	@JsonIgnoreProperties({"password", "status", "roles" , "courses"})
	private TrainingCenter trainingCenter;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "course_tag", 
		joinColumns = @JoinColumn(name = "course_id", nullable = true), 
		inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = true))
	private List<Tag> tags;
	
	public Course() {}

	public Course(String name, LocalDate startDate, Integer duration, String schedule, String url,
			Category category, TrainingCenter trainingCenter) {
		
		this.name = name;
		this.startDate = startDate;
		this.duration = duration;
		this.schedule = schedule;
		this.url = url;
		this.category = category;
		this.trainingCenter = trainingCenter;
	}



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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public TrainingCenter getTrainingCenter() {
		return trainingCenter;
	}

	public void setTrainingCenter(TrainingCenter trainingCenter) {
		this.trainingCenter = trainingCenter;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}


}

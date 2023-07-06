package com.fisi.proyectocursos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fisi.proyectocursos.model.Course;
import com.fisi.proyectocursos.model.TrainingCenter;
import com.fisi.proyectocursos.repository.CourseRepository;
import com.fisi.proyectocursos.service.interfaces.ICourseService;

@Service
public class CourseServiceImpl implements ICourseService {
	
	@Autowired
	private CourseRepository courseRepository;

	@Override
	public Course save(Course course) {
		return courseRepository.save(course);
	}

	@Override
	public void deleteById(int id) {
		courseRepository.deleteById(id);
	}

	@Override
	public List<Course> findAll() {
		return courseRepository.findAll();
	}

	@Override
	public Optional<Course> findById(int id) {
		return courseRepository.findById(id);
	}

	@Override
	public List<Course> findByTrainingCenter(TrainingCenter trainingCenter) {
		return courseRepository.findByTrainingCenter(trainingCenter);
	}

	@Override
	public boolean existsById(Integer id) {
		return courseRepository.existsById(id);
	}

	@Override
	public void softDelete(int id) {
		courseRepository.findById(id)
			.ifPresent(courseDB -> {
				courseDB.setStatus(0);
				courseRepository.save(courseDB);
			});
	}

	@Override
	public List<Course> findByStatus(Integer status) {
		return courseRepository.findByStatus(status);
	}

	@Override
	public List<Course> findByTrainingCenterAndStatus(TrainingCenter trainingCenter, Integer status) {
		return courseRepository.findByTrainingCenterAndStatus(trainingCenter, status);
	}

	
	
}

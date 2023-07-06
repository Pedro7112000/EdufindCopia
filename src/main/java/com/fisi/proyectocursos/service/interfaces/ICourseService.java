package com.fisi.proyectocursos.service.interfaces;

import java.util.List;

import com.fisi.proyectocursos.model.Course;
import com.fisi.proyectocursos.model.TrainingCenter;
import com.fisi.proyectocursos.service.IService;

public interface ICourseService extends IService<Course> {
	
	List<Course> findByTrainingCenter(TrainingCenter trainingCenter);
	List<Course> findByStatus(Integer status);
	List<Course> findByTrainingCenterAndStatus(TrainingCenter trainingCenter, Integer status);
	boolean existsById(Integer id);

}

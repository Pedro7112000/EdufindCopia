package com.fisi.proyectocursos.service.interfaces;

import com.fisi.proyectocursos.model.TrainingCenter;
import com.fisi.proyectocursos.service.IService;

public interface ITrainingCenterService extends IService<TrainingCenter> {
	
	TrainingCenter findByUsername(String username);

}

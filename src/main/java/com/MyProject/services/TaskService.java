package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskEntity;

public interface TaskService {

	void createTask(ProjectEntity projectEntity);
	
	List<TaskEntity> findByProject(ProjectEntity projectEntity);
	
	List<TaskEntity> loadAll();
	
	void delete(TaskEntity task);
	
	TaskEntity findById(Long id); 
	
	void update(TaskEntity taskEntity, ProjectEntity projectEntity);
	
}

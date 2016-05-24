package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskTypeEntity;

public interface TaskTypeService {

	void createTaskType(ProjectEntity projectEntity, TaskTypeEntity taskTypeEntity);
	
	List<TaskTypeEntity> loadAll();
	
	void delete(ProjectEntity projectEntity, TaskTypeEntity taskTypeEntity);
	
	void update(ProjectEntity projectEntity, TaskTypeEntity taskTypeEntity);
	
	TaskTypeEntity findById(Long id);
	
	boolean checkAvailableByProject(ProjectEntity projectEntity, String typeName);
	
}

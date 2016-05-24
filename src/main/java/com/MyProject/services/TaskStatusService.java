package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskStatusEntity;

public interface TaskStatusService {
	
	void createTaskStatus(ProjectEntity projectEntity, TaskStatusEntity taskStatusEntity);
	
	List<TaskStatusEntity> loadAll();
	
	void delete(ProjectEntity projectEntity, TaskStatusEntity taskStatusEntity);
	
	void update(ProjectEntity projectEntity, TaskStatusEntity taskStatusEntity);
	
	TaskStatusEntity findById(Long id);
	
	boolean checkAvailableByProject(ProjectEntity projectEntity, String statusName);
	
}

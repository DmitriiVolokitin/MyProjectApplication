package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskPriorityEntity;

public interface TaskPriorityService {

	void createTaskPriority(ProjectEntity projectEntity, TaskPriorityEntity taskPriorityEntity);
	
	List<TaskPriorityEntity> loadAll();
	
	void delete(ProjectEntity projectEntity, TaskPriorityEntity taskPriorityEntity);
	
	void update(ProjectEntity projectEntity, TaskPriorityEntity taskPriorityEntity);
	
	TaskPriorityEntity findById(Long id);
	
	boolean checkAvailableByProject(ProjectEntity projectEntity, String priorityName);
	
}

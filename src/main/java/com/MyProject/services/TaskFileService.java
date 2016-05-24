package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskFileEntity;

public interface TaskFileService {

	public void createTaskFile(TaskEntity taskID);
	
	List<TaskFileEntity> loadAll();
	
	List<TaskFileEntity> findByTaskID(TaskEntity taskID);
	
	void delete(TaskFileEntity TaskFileEntity, TaskEntity taskID);
	
	void update(TaskFileEntity TaskFileEntity);
	
	TaskFileEntity findById(Long id); 
	
}

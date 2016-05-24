package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskStatusHistoryEntity;

public interface TaskStatusHistoryService {

	void createTaskStatusHistory(TaskEntity taskID);
	
	List<TaskStatusHistoryEntity> loadAll();
	
	void delete(TaskStatusHistoryEntity taskStatusHistoryEntity, TaskEntity taskID);
	
	void update(TaskStatusHistoryEntity taskStatusHistoryEntity);
	
	TaskStatusHistoryEntity findById(Long id); 
	
}

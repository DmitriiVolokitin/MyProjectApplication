package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskHistoryEntity;
import com.MyProject.services.impl.TaskServiceImpl;

public interface TaskHistoryService {

	public void createTaskHistory(TaskEntity taskID);
	
	public void createTaskHistory(TaskServiceImpl taskServiceImpl, ProjectEntity projectEntity);
	
	List<TaskHistoryEntity> loadAll();
	
	void delete(TaskHistoryEntity taskHistoryEntity, TaskEntity taskID);
	
	void update(TaskHistoryEntity taskHistoryEntity);
	
	TaskHistoryEntity findById(Long id); 
	
}

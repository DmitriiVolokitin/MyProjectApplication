package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskEntity;

public interface ProjectService {
	
	void createProjectOnStartNewDataBase();

	void createProject(TaskEntity taskID);
	
	void searchUsersByUserNameOrEmail(String searchText);
	
	List<ProjectEntity> loadAll();
	
	List<ProjectEntity> findByTaskID(TaskEntity taskID);
	
	void delete(ProjectEntity ProjectEntity);
	
	void update(ProjectEntity ProjectEntity);
	
	ProjectEntity findById(Long id); 
	
}

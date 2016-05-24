package com.MyProject.dao;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskStatusEntity;

import java.util.List;

import com.MyProject.commons.dao.GenericDao;

public interface TaskStatusDao extends GenericDao<TaskStatusEntity, Long> {
	
	List<TaskStatusEntity> findByProject(ProjectEntity projectEntity);
	
	boolean checkAvailable(String statusName);
	
	boolean checkAvailableByProject(ProjectEntity projectEntity, String statusName);
	
	Long countEntity();
	
	Long countEntityByProject(ProjectEntity projectEntity);
	
}

package com.MyProject.dao;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskPriorityEntity;

import java.util.List;

import com.MyProject.commons.dao.GenericDao;

public interface TaskPriorityDao extends GenericDao<TaskPriorityEntity, Long> {
	
	List<TaskPriorityEntity> findByProject(ProjectEntity projectEntity);
	
	boolean checkAvailable(String priorityName);
	
	boolean checkAvailableByProject(ProjectEntity projectEntity, String priorityName);
	
	Long countEntity();
	
	Long countEntityByProject(ProjectEntity projectEntity);
	
}

package com.MyProject.dao;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskTypeEntity;

import java.util.List;

import com.MyProject.commons.dao.GenericDao;

public interface TaskTypeDao extends GenericDao<TaskTypeEntity, Long> {
	
	List<TaskTypeEntity> findByProject(ProjectEntity projectEntity);

	public boolean checkAvailable(String typeName);
	
	boolean checkAvailableByProject(ProjectEntity projectEntity, String typeName);

	public Long countEntity();
	
	Long countEntityByProject(ProjectEntity projectEntity);

}

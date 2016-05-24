package com.MyProject.dao;

import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.commons.dao.GenericDao;

public interface ProjectDao extends GenericDao<ProjectEntity, Long> {
	
	List<ProjectEntity> findByTask(TaskEntity taskEntity);
	
	public boolean checkAvailable(String ProjectName);
	
	public Long countEntity();
	
}

package com.MyProject.dao;

import java.util.List;

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskFileEntity;
import com.MyProject.commons.dao.GenericDao;

public interface TaskFileDao extends GenericDao<TaskFileEntity, Long> {
	
	List<TaskFileEntity> findByTaskID(TaskEntity taskID);
	
}

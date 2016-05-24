package com.MyProject.dao;

import java.util.List;

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskStatusHistoryEntity;
import com.MyProject.commons.dao.GenericDao;

public interface TaskStatusHistoryDao extends GenericDao<TaskStatusHistoryEntity, Long> {
	
	List<TaskStatusHistoryEntity> findByTaskID(TaskEntity taskID);
	
}

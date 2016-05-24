package com.MyProject.dao;

import java.util.Date;
import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskHistoryEntity;
import com.MyProject.commons.dao.GenericDao;

public interface TaskHistoryDao extends GenericDao<TaskHistoryEntity, Long> {
	
	List<TaskHistoryEntity> findByTaskID(TaskEntity taskID);
	
	List<Object[]> countTaskActivityByProject(ProjectEntity projectEntity, Date startDate, Date finishDate);
	
}

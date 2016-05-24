package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskStatusHistoryEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class TaskStatusHistoryJpaDao extends GenericJpaDao<TaskStatusHistoryEntity, Long> implements TaskStatusHistoryDao {

	public TaskStatusHistoryJpaDao() {
		super(TaskStatusHistoryEntity.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskStatusHistoryEntity> findByTaskID(TaskEntity taskID) {
		
		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.task = :taskID ORDER BY f.date desc").setParameter("taskID", taskID);
		
		try {
			return (List<TaskStatusHistoryEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}
		
		return null;
	}
	
}

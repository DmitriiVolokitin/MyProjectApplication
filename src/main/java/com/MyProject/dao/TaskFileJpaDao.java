package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskFileEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class TaskFileJpaDao extends GenericJpaDao<TaskFileEntity, Long> implements TaskFileDao {
	
	public TaskFileJpaDao() {
		super(TaskFileEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<TaskFileEntity> findByTaskID(TaskEntity taskID) {
		
		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.task = :taskID").setParameter("taskID", taskID);
		
		try {
			return (List<TaskFileEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}
		
		return null;
	}

}

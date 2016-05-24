package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskPriorityEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class TaskPriorityJpaDao extends GenericJpaDao<TaskPriorityEntity, Long> implements TaskPriorityDao {

	public TaskPriorityJpaDao() {
		super(TaskPriorityEntity.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskPriorityEntity> findByProject(ProjectEntity projectEntity) {
		
		Assert.notNull(projectEntity);
		
		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity").setParameter("projectEntity", projectEntity);
		
		try {
			return (List<TaskPriorityEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}
		
		return null;
	}
	
	public boolean checkAvailable(String priorityName) {
		
		Assert.notNull(priorityName);
		
		Query query = getEntityManager()
			.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
					+ " u where u.task_priority_Name = :priorityName").setParameter("priorityName", priorityName);
		
		Long count = (Long) query.getSingleResult();
		
		return count < 1;
	}
	
	public boolean checkAvailableByProject(ProjectEntity projectEntity, String priorityName) {

		Assert.notNull(projectEntity);
		Assert.notNull(priorityName);

		Query query = getEntityManager().createQuery("select count(*) from " + getPersistentClass().getSimpleName()
				+ " u where (u.project = :projectEntity) and (u.name = :nameFormatTimeTracking)");
		
		query.setParameter("projectEntity", projectEntity);
		query.setParameter("nameFormatTimeTracking", priorityName);
		
		Long count = (Long) query.getSingleResult();

		return count < 1;
	}
	
	public Long countEntity() {

		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
						+ " u");

		return (Long) query.getSingleResult();

	}

	public Long countEntityByProject(ProjectEntity projectEntity) {
		
		Assert.notNull(projectEntity);

		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
						+ " u where u.project = :projectEntity").setParameter("projectEntity", projectEntity);

		return (Long) query.getSingleResult();

	}
	
}

package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskStatusEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class TaskStatusJpaDao extends GenericJpaDao<TaskStatusEntity, Long> implements TaskStatusDao {

	public TaskStatusJpaDao() {
		super(TaskStatusEntity.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskStatusEntity> findByProject(ProjectEntity projectEntity) {
		
		Assert.notNull(projectEntity);
		
		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity").setParameter("projectEntity", projectEntity);
		
		try {
			return (List<TaskStatusEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}
		
		return null;
	}
	
	public boolean checkAvailable(String statusName) {
		Assert.notNull(statusName);
		
		Query query = getEntityManager()
			.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
					+ " u where u.task_status_Name = :statusName").setParameter("statusName", statusName);
		
		Long count = (Long) query.getSingleResult();
		
		return count < 1;
	}
	
	public boolean checkAvailableByProject(ProjectEntity projectEntity, String statusName) {

		Assert.notNull(projectEntity);
		Assert.notNull(statusName);

		Query query = getEntityManager().createQuery("select count(*) from " + getPersistentClass().getSimpleName()
				+ " u where (u.project = :projectEntity) and (u.name = :nameFormatTimeTracking)");
		
		query.setParameter("projectEntity", projectEntity);
		query.setParameter("nameFormatTimeTracking", statusName);
		
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

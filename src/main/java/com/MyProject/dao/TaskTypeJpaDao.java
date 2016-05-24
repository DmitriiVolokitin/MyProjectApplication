package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskTypeEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class TaskTypeJpaDao extends GenericJpaDao<TaskTypeEntity, Long> implements TaskTypeDao {

	public TaskTypeJpaDao() {
		super(TaskTypeEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<TaskTypeEntity> findByProject(ProjectEntity projectEntity) {
		
		Assert.notNull(projectEntity);
		
		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity").setParameter("projectEntity", projectEntity);
		
		try {
			return (List<TaskTypeEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}
		
		return null;
	}
	
	public boolean checkAvailable(String typeName) {

		Assert.notNull(typeName);

		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
						+ " u where u.task_type_Name = :typeName").setParameter("typeName", typeName);

		Long count = (Long) query.getSingleResult();

		return count < 1;
	}
	
	public boolean checkAvailableByProject(ProjectEntity projectEntity, String typeName) {

		Assert.notNull(projectEntity);
		Assert.notNull(typeName);

		Query query = getEntityManager().createQuery("select count(*) from " + getPersistentClass().getSimpleName()
				+ " u where (u.project = :projectEntity) and (u.name = :nameFormatTimeTracking)");
		
		query.setParameter("projectEntity", projectEntity);
		query.setParameter("nameFormatTimeTracking", typeName);
		
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
